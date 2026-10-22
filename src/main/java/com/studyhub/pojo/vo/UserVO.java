package com.studyhub.pojo.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserVO {

    private final Long id;

    private final String username;

    private final String nickname;

    private final Integer status;
}
