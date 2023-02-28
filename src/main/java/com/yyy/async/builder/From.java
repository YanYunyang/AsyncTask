package com.yyy.async.builder;

import com.yyy.async.ExecutionUnit;

/**
 * From
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:22
 */
public interface From {

    /**
     * 构造执行路径的target任务，并返回子构造器
     * @param target 目标任务执行单元
     * @return To
     */
    To to(ExecutionUnit<?> target);
}
