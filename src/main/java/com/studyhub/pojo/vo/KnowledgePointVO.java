package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

/**
 * 知识点返回体。不含 status（内部状态，前端无需感知）。
 */
@Getter
@Builder
public class KnowledgePointVO {

    private final Long id;

    private final Long subjectId;

    private final Long categoryId;

    private final String title;

    private final String content;

    private final String tags;

    private final Integer importance;
}
