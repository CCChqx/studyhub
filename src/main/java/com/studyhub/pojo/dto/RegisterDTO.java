package com.studyhub.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3,max = 20,message = "用户名长度3-20")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "用户名只能包含字母、数字、下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8,max = 32,message = "密码长度 8-32")
    private String password;

    @Size(max = 50,message = "昵称最长 50")
    private String nickname;
}
