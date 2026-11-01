package com.studyhub.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewSubmitDTO {

    @NotNull(message = "知识点不能为空")
    private Long knowledgePointId;

    @NotNull(message = "自测评分不能为空")
    @Min(value = 0, message = "自测分数不能低于0")
    @Max(value = 5, message = "自测分数最高不能超过5")
    private Integer quality;

    @NotBlank(message = "requestId 不能为空")
    @Size(max = 64, message = "requestId 最长64位")
    private String requestId;
}
