package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginVO {

    private final String accessToken;

    private final String tokenType;

    private final Long expiresIn;

    private final UserVO user;
}
