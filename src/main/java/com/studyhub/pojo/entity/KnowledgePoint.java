package com.studyhub.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识点表（内容核心）。对应表 knowledge_point。
 */
@Data
@TableName("knowledge_point")
public class KnowledgePoint {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属学科 */
    private Long subjectId;

    /** 所属分类 */
    private Long categoryId;

    private String title;

    private String content;

    /** 标签，逗号分隔 */
    private String tags;

    /** 重要度 1-5 */
    private Integer importance;

    /** 状态：1 启用 0 停用（内容侧开关）；是否掌握见 review_state.mastered，不由前端写入 */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
