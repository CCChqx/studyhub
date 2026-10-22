package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 题目返回体（后台管理用，包含答案和解析）。
 * 学员答题视图应单独定义，不能直接复用它。
 */
@Getter
@Builder
public class QuestionVO {

    private final Long id;

    private final Long subjectId;

    private final Long categoryId;

    private final Long kpId;

    /** 1 单选 / 2 多选 / 3 简答 */
    private final Integer type;

    private final String stem;

    private final String options;

    private final String answer;

    private final String analysis;

    private final Integer status;

    private final LocalDateTime createTime;
}
