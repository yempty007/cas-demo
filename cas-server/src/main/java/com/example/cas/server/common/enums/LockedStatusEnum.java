package com.example.cas.server.common.enums;

/**
 * @Description 锁定状态枚举
 * @Author yempty
 * @Date 2020/8/31 15:43
 */
public enum  LockedStatusEnum {
    /**
     * 未锁
     */
    UN_LOCKED(0),
    /**
     * 锁定
     */
    LOCKED(1);

    private int value;

    LockedStatusEnum(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
