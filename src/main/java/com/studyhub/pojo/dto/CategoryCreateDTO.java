package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新建分类入参。parentId 为空则视为顶级分类。
 */
@Data
public class CategoryCreateDTO {

    @NotNull(message = "所属学科不能为空")
    private Long subjectId;

    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private Integer sort;
}
