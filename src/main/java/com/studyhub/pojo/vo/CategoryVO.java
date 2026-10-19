package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

/**
 * 分类返回体。
 */
@Getter
@Builder
public class CategoryVO {

    private final Long id;

    private final Long subjectId;

    private final Long parentId;

    private final String name;

    private final Integer sort;
}
