package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新学科入参。比 Create 多一个必传的 id。
 */
@Data
public class SubjectUpdateDTO {

    @NotNull(message = "学科 ID 不能为空")
    private Long id;

    @NotBlank(message = "学科名称不能为空")
    private String name;

    @NotBlank(message = "学科标识不能为空")
    private String code;

    private String description;
}
