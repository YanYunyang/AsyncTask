package com.yyy.async.builder;

import com.yyy.async.ExecutionUnit;
import com.yyy.async.TaskGroup;

/**
 * TaskGroupBuilderImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:34
 */
public class TaskGroupBuilderImpl implements TaskGroupBuilder{


    @Override
    public ExecutionPathBuilder executionPath() {
        return null;
    }

    @Override
    public void single(ExecutionUnit<?> executionUnit) {

    }

    @Override
    public TaskGroup build(String taskGroupId) {
        return null;
    }
}
