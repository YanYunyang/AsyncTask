package com.yyy.async.impl;

import com.yyy.async.*;
import com.yyy.async.exception.AsyncTaskException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * TaskGroupImpl
 *
 * @author Yan Yunyang
 * @date 2023/2/28 15:36
 */
public class TaskGroupImpl implements TaskGroup {
    /**
     * taskMap是taskGroup核心，存储任务组中的所有任务。
     */
    private final Map<String, Task<?>> taskMap;
    private boolean ready = false;

    private String taskGroupId;

    public TaskGroupImpl(Map<String, Task<?>> taskMap) {
        this.taskMap = taskMap;
    }

    @Override
    public boolean work(TaskGroupContext context, ExecutorService executorService, long timeout) throws ExecutionException, InterruptedException {
        if (context == null){
            throw  new AsyncTaskException("param [context] should not be null");
        }
        isReady();

        //封装任务组中所有任务
        Map<String, TaskWrapper<?>> allTaskWrappers = wrapAllTask(taskMap);
        putWorkResult(context,allTaskWrappers);

        TaskWrapper<?> startTaskWrapper = allTaskWrappers.get(Start.class.getName());
        Task<?> startTask = startTaskWrapper.getTask();
        //起始任务，调试开启控制台打印启动信息
        startTask.work(context);

        CompletableFuture<?>[] futures = new CompletableFuture[startTask.getAfterPath().size()];
        for (int i = 0; i < startTask.getAfterPath().size(); i++){
            ExecutionPath executionPath = startTask.getAfterPath().get(i);
            TaskWrapper<?> nextTaskWrapper = allTaskWrappers.get(executionPath.getTarget().getTaskId());
            futures[i] = CompletableFuture.runAsync(()->nextTaskWrapper.work(context,executorService,timeout,allTaskWrappers,executionPath),executorService);
        }
        try {
            CompletableFuture.allOf(futures).get(timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (TimeoutException e) {
            //停止所有任务执行
            for (TaskWrapper<?> taskWrapper : allTaskWrappers.values()){
                taskWrapper.stopWork();
            }
            return false;
        }
    }

    private void putWorkResult(TaskGroupContext context, Map<String, TaskWrapper<?>> allTaskWrappers) {
        for (TaskWrapper<?> taskWrapper : allTaskWrappers.values()){
            context.put(taskWrapper.getTask().getTaskId(),taskWrapper.getWorkResult());
        }
    }

    private Map<String, TaskWrapper<?>> wrapAllTask(Map<String, Task<?>> taskMap) {
        Map<String, TaskWrapper<?>> allTaskWrappers = new HashMap<>();
        for (Task<?> task : taskMap.values()){
            allTaskWrappers.put(task.getTaskId(),new TaskWrapper<>(task));
        }
        return allTaskWrappers;
    }

    @Override
    public String showTaskGroup() {
        isReady();
        SysOutVisitor sysOutVisitor = new SysOutVisitor();
        return accept(sysOutVisitor);
    }

    @Override
    public String getTaskGroupId() {
        return this.taskGroupId;
    }

    public void setTaskGroupId(String taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    private void isReady() {
        if (!ready){
            throw new AsyncTaskException(" task group is not built yet, can not work!");
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public String accept(Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.visitOnEntry(this)).append(LF);

        Task<?> start = taskMap.get(Start.class.getName());
        sb.append(start.accept(visitor));
        for (Task<?> task : taskMap.values()){
            if (task.isStartOrEnd()){
                continue;
            }
            sb.append(task.accept(visitor));
        }
        Task<?> end = taskMap.get(End.class.getName());
        sb.append(end.accept(visitor));
        sb.append(visitor.visitOnExit(this));
        return sb.toString();
    }
}
