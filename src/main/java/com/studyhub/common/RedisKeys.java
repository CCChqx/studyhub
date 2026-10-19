package com.studyhub.common;

/**
 * Redis Key 统一收口。禁止在业务代码里硬编码 key 字符串。
 * 命名格式：studyhub:模块:实体:操作:标识
 */
public final class RedisKeys {

    private static final String PREFIX = "studyhub:";

    private RedisKeys() {
    }

    // ---------- 认证 ----------
    public static String refreshToken(Long userId) {
        return PREFIX + "auth:refresh:" + userId;
    }

    // ---------- 知识点缓存（示例范式，按业务落地时扩展） ----------
    public static String kpDetail(Long id) {
        return PREFIX + "kp:detail:" + id;
    }

    public static String kpDetailLock(Long id) {
        return PREFIX + "kp:detail:lock:" + id;
    }

    public static String kpPage(String queryHash) {
        return PREFIX + "kp:page:" + queryHash;
    }

    // ---------- 幂等 ----------
    public static String idempotent(String value, String signature, int argsHash) {
        return PREFIX + "idem:" + value + ":" + signature + ":" + argsHash;
    }
}
