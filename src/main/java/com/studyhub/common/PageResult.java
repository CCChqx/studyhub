package com.studyhub.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 分页结果统一返回体。只暴露 total + list 两个字段，挡掉 MyBatis-Plus Page 的框架细节。
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResult<T> {

    private final long total;
    private final List<T> list;

    public static <T> PageResult<T> of(long total, List<T> list) {
        return new PageResult<>(total, list == null ? List.of() : list);
    }
}
