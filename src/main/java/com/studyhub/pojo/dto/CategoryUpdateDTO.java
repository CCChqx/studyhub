package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新分类入参。比 Create 多一个必传的 id。
 */
@Data
public class CategoryUpdateDTO {

    @NotNull(message = "分类 ID 不能为空")
    private Long id;

    @NotNull(message = "所属学科不能为空")
    private Long subjectId;

    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private Integer sort;
}
