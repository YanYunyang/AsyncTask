package com.yyy.async.builder;

import com.yyy.async.ExecutionPath;
import com.yyy.async.ExecutionUnit;
import com.yyy.async.Task;
import com.yyy.async.exception.AsyncTaskException;
import com.yyy.async.impl.ExecutionPathImpl;
import com.yyy.async.impl.TaskImpl;

import java.util.Map;

/**
 * ExecutionPathBuilderImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:35
 */
public class ExecutionPathBuilderImpl implements ExecutionPathBuilder,From,To{
    private final Map<String, Task<?>> taskMap;
    private String sourceTaskId;
    private String targetTaskId;

    public ExecutionPathBuilderImpl(Map<String, Task<?>> taskMap) {
        this.taskMap = taskMap;
    }

    @Override
    public From from(ExecutionUnit<?> source) {
        if (source == null){
            throw new AsyncTaskException("null values are not allowed!");
        }
        this.sourceTaskId = source.getId();
        taskMap.putIfAbsent(this.sourceTaskId,new TaskImpl<>(source));
        return this;
    }

    @Override
    public To to(ExecutionUnit<?> target) {
        if (target == null){
            throw new AsyncTaskException("null values are not allowed!");
        }
        this.targetTaskId = target.getId();
        taskMap.putIfAbsent(this.targetTaskId, new TaskImpl<>(target));
        return this;
    }

    @Override
    public void must(boolean isMust) {
        ExecutionPath executionPath = new ExecutionPathImpl();
        executionPath.setMust(isMust);

        Task<?> sourceTask = taskMap.get(sourceTaskId);
        executionPath.setSource(sourceTask);
        sourceTask.addAfterPath(executionPath);

        Task<?> targetTask = taskMap.get(targetTaskId);
        executionPath.setTarget(targetTask);
        targetTask.addPrePath(executionPath);
    }
}
