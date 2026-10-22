package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.studyhub.common.ErrorCode;
import com.studyhub.converter.UserConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.UserMapper;
import com.studyhub.pojo.dto.LoginDTO;
import com.studyhub.pojo.dto.RegisterDTO;
import com.studyhub.pojo.entity.User;
import com.studyhub.pojo.vo.LoginVO;
import com.studyhub.pojo.vo.UserVO;
import com.studyhub.service.AuthService;
import com.studyhub.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Override
    public UserVO register(RegisterDTO dto) {

        String username = dto.getUsername().trim().toLowerCase(Locale.ROOT);

        User existing = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (existing != null) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }
        // 注册
        User user = new User();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getNickname());
        user.setPasswordHash(hashPassword);
        user.setStatus(1);

        // 防止并发情况下的重复
        try{
            userMapper.insert(user);
        }catch(DuplicateKeyException e){
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }

        return UserConverter.toVO(user);
    }

    @Override
    public LoginVO login(LoginDTO dto) {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, dto.getUsername()));

        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        boolean ok = passwordEncoder.matches(dto.getPassword(), user.getPasswordHash());
        if (!ok){
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        if (!Integer.valueOf(1).equals(user.getStatus())){
            throw new BusinessException(ErrorCode.USER_DISABLED);
        }
        userMapper.update(null,new LambdaUpdateWrapper<User>()
                .eq(User::getId,user.getId())
                .set(User::getLastLoginTime,LocalDateTime.now()));

        String accessToken = jwtUtil.generateToken(user.getId(), user.getUsername(), "access");

        return LoginVO.builder()
                .accessToken(accessToken)
                .expiresIn(accessExpiration/1000)
                .tokenType("Bearer")
                .user(UserConverter.toVO(user))
                .build();
    }
}
