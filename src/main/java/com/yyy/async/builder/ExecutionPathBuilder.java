package com.yyy.async.builder;

import com.yyy.async.ExecutionUnit;

/**
 * ExecutionPathBuilder
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:22
 */
public interface ExecutionPathBuilder {

    /**
     * 构造执行路径的source任务，并返回子构造器
     * @param source 源任务执行单元
     * @return From
     */
    From from(ExecutionUnit<?> source);
}
