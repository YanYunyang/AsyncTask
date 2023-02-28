package com.yyy.async.impl;

import com.yyy.async.Task;
import com.yyy.async.TaskGroup;
import com.yyy.async.TaskGroupContext;
import com.yyy.async.Visitor;
import com.yyy.async.exception.AsyncTaskException;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

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
        return false;
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
