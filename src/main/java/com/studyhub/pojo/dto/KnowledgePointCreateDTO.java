package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新建知识点入参。不含 status（由复习系统维护，前端不写）。
 */
@Data
public class KnowledgePointCreateDTO {

    @NotNull(message = "所属学科不能为空")
    private Long subjectId;

    @NotNull(message = "所属分类不能为空")
    private Long categoryId;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String content;

    private String tags;

    private Integer importance;
}
