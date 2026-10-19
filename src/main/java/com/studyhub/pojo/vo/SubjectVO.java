package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

/**
 * 学科返回体。只暴露前端需要的字段，不含 createTime/updateTime。
 */
@Getter
@Builder
public class SubjectVO {

    private final Long id;

    private final String name;

    private final String code;

    private final String description;
}
