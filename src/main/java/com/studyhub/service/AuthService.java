package com.studyhub.service;

import com.studyhub.pojo.dto.LoginDTO;
import com.studyhub.pojo.dto.RegisterDTO;
import com.studyhub.pojo.vo.LoginVO;
import com.studyhub.pojo.vo.UserVO;

public interface AuthService {

    UserVO register(RegisterDTO dto);

    LoginVO login(LoginDTO dto);
}
