package com.yyy.async.builder;

import com.yyy.async.ExecutionUnit;
import com.yyy.async.Task;
import com.yyy.async.TaskGroup;
import com.yyy.async.TaskGroupFactory;
import com.yyy.async.exception.AsyncTaskException;
import com.yyy.async.impl.End;
import com.yyy.async.impl.Start;
import com.yyy.async.impl.TaskGroupImpl;
import com.yyy.async.impl.TaskImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TaskGroupBuilderImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:34
 */
public class TaskGroupBuilderImpl implements TaskGroupBuilder{
    private final Map<String, Task<?>> taskMap = new ConcurrentHashMap<>();
    private final TaskGroupImpl taskGroup = new TaskGroupImpl(taskMap);
    private final Start START = new Start();
    private final End END = new End();

    public TaskGroupBuilderImpl() {
        START.setTaskGroup(taskGroup);
        String startTaskId = START.getId();
        taskMap.putIfAbsent(startTaskId,new TaskImpl<>(START));

        END.setTaskGroup(taskGroup);
        String endTaskId = END.getId();
        taskMap.putIfAbsent(endTaskId,new TaskImpl<>(END));
    }

    @Override
    public ExecutionPathBuilder executionPath() {
        return new ExecutionPathBuilderImpl(taskMap);
    }

    @Override
    public void single(ExecutionUnit<?> executionUnit) {
        taskMap.putIfAbsent(executionUnit.getId(),new TaskImpl<>(executionUnit));
    }

    @Override
    public TaskGroup build(String taskGroupId) {
        if (taskMap.values().size() == 0){
            throw new AsyncTaskException("the task group with id[" + taskGroupId + "] is not defined, please define it first!");
        }
        addExecutionPathFromSTART();
        addExecutionPathToEND();
        taskGroup.setTaskGroupId(taskGroupId);
        taskGroup.setReady(true);
        TaskGroupFactory.register(taskGroup);
        return taskGroup;
    }

    private void addExecutionPathToEND() {
        for (Task<?> task : taskMap.values()){
            if (task.isStartOrEnd()){
                continue;
            }
            if (task.getAfterPath().size() == 0){
                this.executionPath().from(task.getExecutionUnit()).to(END).must(true);
            }
        }
    }

    private void addExecutionPathFromSTART() {
        for (Task<?> task : taskMap.values()){
            if (task.isStartOrEnd()){
                continue;
            }
            if (task.getPrePath().size() == 0){
                this.executionPath().from(START).to(task.getExecutionUnit()).must(true);
            }
        }
    }
}
