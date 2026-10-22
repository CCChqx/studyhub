package com.studyhub.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新增题目入参。type：1 单选 / 2 多选 / 3 简答。
 */
@Data
public class QuestionCreateDTO {

    @NotNull(message = "所属学科不能为空")
    private Long subjectId;

    @NotNull(message = "所属分类不能为空")
    private Long categoryId;

    /** 可为空：题目可以只挂分类，不挂知识点 */
    private Long kpId;

    @NotNull(message = "题目类型不能为空")
    @Min(value = 1, message = "题目类型只能是 1/2/3")
    @Max(value = 3, message = "题目类型只能是 1/2/3")
    private Integer type;

    @NotBlank(message = "题干不能为空")
    private String stem;

    /** 选择题必填，JSON 数组字符串，如 ["A.xx","B.xx"]；简答题可为空 */
    private String options;

    @NotBlank(message = "答案不能为空")
    private String answer;

    private String analysis;
}
