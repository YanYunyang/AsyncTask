package com.yyy.async.builder;

import com.yyy.async.ExecutionUnit;
import com.yyy.async.TaskGroup;

/**
 * TaskGroupBuilder
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:22
 */
public interface TaskGroupBuilder {

    /**
     * 执行路径builder
     * @return builder
     */
    ExecutionPathBuilder executionPath();

    /**
     * 无执行路径，孤立任务
     */
    void single(ExecutionUnit<?> executionUnit);

    /**
     * 构造编排后的任务组
     * @param taskGroupId 任务组id
     * @return 任务组
     */
    TaskGroup build(String taskGroupId);
}
