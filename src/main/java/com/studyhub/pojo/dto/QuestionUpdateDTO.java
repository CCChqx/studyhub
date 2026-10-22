package com.studyhub.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 修改题目入参。
 */
@Data
public class QuestionUpdateDTO {

    @NotNull(message = "题目 ID 不能为空")
    private Long id;

    @NotNull(message = "所属学科不能为空")
    private Long subjectId;

    @NotNull(message = "所属分类不能为空")
    private Long categoryId;

    private Long kpId;

    @NotNull(message = "题目类型不能为空")
    @Min(value = 1, message = "题目类型只能是 1/2/3")
    @Max(value = 3, message = "题目类型只能是 1/2/3")
    private Integer type;

    @NotBlank(message = "题干不能为空")
    private String stem;

    private String options;

    @NotBlank(message = "答案不能为空")
    private String answer;

    private String analysis;

    /** 可选：1 启用 / 0 停用；为空则保持原值 */
    @Min(value = 0, message = "状态只能是 0/1")
    @Max(value = 1, message = "状态只能是 0/1")
    private Integer status;
}
