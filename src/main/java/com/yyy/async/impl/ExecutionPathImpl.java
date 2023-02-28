package com.yyy.async.impl;

import com.yyy.async.ExecutionPath;
import com.yyy.async.Task;

/**
 * ExecutionPathImpl
 *
 * <p>执行路径实现类，编排能力核心，连接任务</p>
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:44
 */
public class ExecutionPathImpl implements ExecutionPath {
    private boolean must = false;
    private Task<?> source;
    private Task<?> target;

    @Override
    public void setMust(boolean must) {
        this.must = must;
    }

    @Override
    public boolean getMust() {
        return must;
    }

    @Override
    public void setSource(Task<?> task) {
        this.source = task;
    }

    @Override
    public Task<?> getSource() {
        return source;
    }

    @Override
    public void setTarget(Task<?> task) {
        this.target = task;
    }

    @Override
    public Task<?> getTarget() {
        return target;
    }

    @Override
    public String toString() {
        String must;
        if (this.must){
            must = "[must]";
        }else{
            must = "------";
        }
        return source.getTaskId() + "---" + must + "--->" + target.getTaskId();
    }
}
