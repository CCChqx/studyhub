package com.studyhub.converter;

import com.studyhub.pojo.entity.User;
import com.studyhub.pojo.vo.UserVO;

public class UserConverter {

    private UserConverter() {}

    public static UserVO toVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .build();
    }
}
