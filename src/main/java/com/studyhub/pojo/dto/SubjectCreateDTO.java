package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新建学科入参。只收前端该传的字段，不含 id / 时间等后端生成字段。
 */
@Data
public class SubjectCreateDTO {

    @NotBlank(message = "学科名称不能为空")
    private String name;

    @NotBlank(message = "学科标识不能为空")
    private String code;

    private String description;
}
