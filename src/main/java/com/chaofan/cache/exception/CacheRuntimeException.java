package com.chaofan.cache.exception;

import java.io.Serial;

/**
 * 缓存运行时异常
 *
 * @author 李超凡
 * @since 2022/5/28 21:18
 */
public class CacheRuntimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6119882127259529825L;

    public CacheRuntimeException(String message) {
        super(message);
    }

    public CacheRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
