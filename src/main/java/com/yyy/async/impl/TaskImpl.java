package com.yyy.async.impl;

import com.yyy.async.*;

import java.util.List;

/**
 * TaskImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:40
 */
public class TaskImpl<T> implements Task<T> {
    private final ExecutionUnit<T> executionUnit;

    public TaskImpl(ExecutionUnit<T> executionUnit) {
        this.executionUnit = executionUnit;
    }

    @Override
    public T work(TaskGroupContext context) {
        return null;
    }

    @Override
    public String getTaskId() {
        return executionUnit.getId();
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitOnEntry(this) + visitor.visitOnExit(this);
    }

    @Override
    public void addPrePath(ExecutionPath executionPath) {

    }

    @Override
    public List<ExecutionPath> getPrePath() {
        return null;
    }

    @Override
    public void addAfterPath(ExecutionPath executionPath) {

    }

    @Override
    public List<ExecutionPath> getAfterPath() {
        return null;
    }

    @Override
    public int getPreMustExecutionPathCount() {
        return 0;
    }

    @Override
    public ExecutionUnit<?> getExecutionUnit() {
        return null;
    }

    @Override
    public boolean isStartOrEnd() {
        return ((executionUnit instanceof Start)||(executionUnit instanceof End));
    }
}
