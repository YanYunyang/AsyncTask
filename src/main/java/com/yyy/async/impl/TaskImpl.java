package com.yyy.async.impl;

import com.yyy.async.*;
import com.yyy.async.exception.AsyncTaskException;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:40
 */
public class TaskImpl<T> implements Task<T> {
    private final List<ExecutionPath> preExecutionPath = new ArrayList<>();
    private final List<ExecutionPath> afterExecutionPath = new ArrayList<>();
    private final List<ExecutionPath> preMustExecutionPath = new ArrayList<>();

    private final ExecutionUnit<T> executionUnit;


    public TaskImpl(ExecutionUnit<T> executionUnit) {
        if (executionUnit == null){
            throw new AsyncTaskException("param [executionUnit] should not be null!");
        }
        this.executionUnit = executionUnit;
    }

    @Override
    public T work(TaskGroupContext context) {
        return executionUnit.action(context);
    }

    @Override
    public String getTaskId() {
        return executionUnit.getId();
    }

    @Override
    public void addPrePath(ExecutionPath executionPath) {
        this.preExecutionPath.add(executionPath);
        if (executionPath.getMust()){
            preMustExecutionPath.add(executionPath);
        }
    }

    @Override
    public List<ExecutionPath> getPrePath() {
        return preExecutionPath;
    }

    @Override
    public void addAfterPath(ExecutionPath executionPath) {
        this.afterExecutionPath.add(executionPath);
    }

    @Override
    public List<ExecutionPath> getAfterPath() {
        return afterExecutionPath;
    }

    @Override
    public int getPreMustExecutionPathCount() {
        return preMustExecutionPath.size();
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitOnEntry(this) + visitor.visitOnExit(this);
    }

    @Override
    public ExecutionUnit<T> getExecutionUnit() {
        return executionUnit;
    }

    @Override
    public boolean isStartOrEnd() {
        return ((executionUnit instanceof Start)||(executionUnit instanceof End));
    }
}
