package com.yyy.async.builder;

/**
 * To
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:23
 */
public interface To {
    /**
     * 标识执行路径（source--[boolean]-->target）是否为强制依赖
     * @param isMust must
     */
    void must(boolean isMust);
}
