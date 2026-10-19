package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新知识点入参。比 Create 多一个必传的 id，同样不含 status。
 */
@Data
public class KnowledgePointUpdateDTO {

    @NotNull(message = "知识点 ID 不能为空")
    private Long id;

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
