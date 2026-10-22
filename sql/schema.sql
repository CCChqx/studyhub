-- =============================================
-- StudyHub 建库建表脚本
-- 字符集统一 utf8mb4，引擎 InnoDB
-- 对应 ARCHITECTURE.md §2 领域模型
-- 执行方式：mysql -uroot -p < schema.sql
-- =============================================

CREATE DATABASE IF NOT EXISTS studyhub DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE studyhub;

-- =============================================
-- 1. 用户表
-- =============================================
CREATE TABLE `user` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`        VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password_hash`   VARCHAR(100) NOT NULL COMMENT 'BCrypt 密码哈希（不存明文）',
    `nickname`        VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    `status`          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：1正常 0冻结',
    `last_login_time` DATETIME     DEFAULT NULL COMMENT '最后登录时间',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';

-- =============================================
-- 2. 学科表（多领域的根，从第一天就建；Java/考公等只加数据不改表）
-- =============================================
CREATE TABLE `subject` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50)  NOT NULL COMMENT '学科名称，如 Java、考公行测',
    `code`        VARCHAR(50)  NOT NULL COMMENT '学科唯一标识，如 java、gk-xc',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常 1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '学科表';

-- =============================================
-- 3. 分类表（学科下的章节/专题，parent_id 支持多级）
-- =============================================
CREATE TABLE `category` (
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `subject_id`  BIGINT      NOT NULL COMMENT '所属学科',
    `parent_id`   BIGINT      NOT NULL DEFAULT 0 COMMENT '父分类，0为顶级',
    `name`        VARCHAR(50) NOT NULL COMMENT '分类名称',
    `sort`        INT         NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常 1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_subject` (`subject_id`),
    KEY `idx_parent` (`parent_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '分类表';

-- =============================================
-- 4. 知识点表（内容核心；字段设计成"可全文检索"，为 P2 接 ES 留口子）
-- =============================================
CREATE TABLE `knowledge_point` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `subject_id`  BIGINT       NOT NULL COMMENT '所属学科',
    `category_id` BIGINT       NOT NULL COMMENT '所属分类',
    `title`       VARCHAR(200) NOT NULL COMMENT '标题',
    `content`     TEXT         COMMENT '正文内容',
    `tags`        VARCHAR(200) DEFAULT NULL COMMENT '标签，逗号分隔',
    `importance`  TINYINT      DEFAULT NULL COMMENT '重要度 1-5',
    `status`      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用（内容侧；是否掌握见 review_state.mastered）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常 1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category_id`),
    KEY `idx_subject` (`subject_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '知识点表';

-- =============================================
-- 5. 题目表（单选 + 多选 + 简答；kp_id 可空，题目可只挂分类不挂知识点）
-- =============================================
CREATE TABLE `question` (
    `id`          BIGINT   NOT NULL AUTO_INCREMENT,
    `subject_id`  BIGINT   NOT NULL COMMENT '所属学科',
    `category_id` BIGINT   NOT NULL COMMENT '所属分类',
    `kp_id`       BIGINT   DEFAULT NULL COMMENT '所属知识点，可空',
    `type`        TINYINT  NOT NULL COMMENT '题目类型：1单选 2多选 3简答',
    `stem`        TEXT     NOT NULL COMMENT '题干',
    `options`     TEXT     DEFAULT NULL COMMENT '选择题选项，JSON 数组如 ["A.xx","B.xx"]；简答题为空',
    `answer`      TEXT     NOT NULL COMMENT '答案：选择题存选项字母如 A，简答题存参考答案文本',
    `analysis`    TEXT     DEFAULT NULL COMMENT '解析',
    `status`      TINYINT  NOT NULL DEFAULT 1 COMMENT '状态：1启用 0停用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     TINYINT  NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常 1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_subject` (`subject_id`),
    KEY `idx_category` (`category_id`),
    KEY `idx_kp` (`kp_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '题目表';

-- =============================================
-- 6. 复习状态表（当前状态快照，可更新，乐观锁防并发）
-- =============================================
CREATE TABLE `review_state` (
    `id`                 BIGINT        NOT NULL AUTO_INCREMENT,
    `user_id`            BIGINT        NOT NULL COMMENT '用户',
    `knowledge_point_id` BIGINT        NOT NULL COMMENT '知识点',
    `ef`                 DECIMAL(5, 2) NOT NULL DEFAULT 2.50 COMMENT '难度系数，下限1.3',
    `interval_days`      INT           NOT NULL DEFAULT 0 COMMENT '当前复习间隔(天)',
    `due_at`             DATE          NOT NULL COMMENT '下次复习日期',
    `review_count`       INT           NOT NULL DEFAULT 0 COMMENT '复习次数(等价SM2的repetitions)',
    `lapse_count`        INT           NOT NULL DEFAULT 0 COMMENT '连续遗忘次数',
    `mastered`           TINYINT       NOT NULL DEFAULT 0 COMMENT '是否毕业：0否 1是',
    `algorithm_version`  VARCHAR(20)   NOT NULL DEFAULT 'SM2' COMMENT '算法版本，便于将来切FSRS',
    `version`            INT           NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    `create_time`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_kp` (`user_id`, `knowledge_point_id`),
    KEY `idx_user_due` (`user_id`, `due_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '复习状态表';

-- =============================================
-- 7. 复习日志表（append-only 事实源，只追加，支撑事件溯源/算法回放）
-- =============================================
CREATE TABLE `review_log` (
    `id`                     BIGINT        NOT NULL AUTO_INCREMENT,
    `user_id`                BIGINT        NOT NULL,
    `knowledge_point_id`     BIGINT        NOT NULL,
    `request_id`             VARCHAR(64)   NOT NULL COMMENT '幂等键',
    `quality`                TINYINT       NOT NULL COMMENT '评分 0-5',
    `previous_ef`            DECIMAL(5, 2) DEFAULT NULL,
    `next_ef`                DECIMAL(5, 2) DEFAULT NULL,
    `previous_interval_days` INT           DEFAULT NULL,
    `next_interval_days`     INT           DEFAULT NULL,
    `previous_due_at`        DATE          DEFAULT NULL,
    `next_due_at`            DATE          DEFAULT NULL,
    `response_time_ms`       INT           DEFAULT NULL COMMENT '答题耗时(毫秒)',
    `reviewed_at`            DATETIME      NOT NULL COMMENT '复习时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_request` (`request_id`),
    KEY `idx_user_time` (`user_id`, `reviewed_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '复习日志表(事实源)';

-- =============================================
-- 8. 学习统计日归档表（Redis 热数据定时归档，唯一索引防重复）
-- =============================================
CREATE TABLE `study_record` (
    `id`             BIGINT   NOT NULL AUTO_INCREMENT,
    `user_id`        BIGINT   NOT NULL,
    `record_date`    DATE     NOT NULL COMMENT '记录日期',
    `duration`       INT      NOT NULL DEFAULT 0 COMMENT '学习时长(分钟)',
    `question_count` INT      NOT NULL DEFAULT 0 COMMENT '做题数',
    `create_time`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_date` (`user_id`, `record_date`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '学习统计日归档表';
