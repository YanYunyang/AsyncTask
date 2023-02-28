package com.yyy.async.impl;

import com.yyy.async.ExecutionUnit;
import com.yyy.async.TaskGroup;
import com.yyy.async.TaskGroupContext;

/**
 * Start
 *
 * <p>特例对象，串联异步任务，作为任务组统一入口</p>
 *
 * @author Yan Yunyang
 * @date 2023/2/28 15:21
 */
public class Start implements ExecutionUnit<Void> {
    private TaskGroup taskGroup;

    @Override
    public Void action(TaskGroupContext context) {
        String message = "task group[" + taskGroup.getTaskGroupId() + "],task[Start]:Working";
        Debugger.debug(message);
        return null;
    }

    public void setTaskGroup(TaskGroup taskGroup){
        this.taskGroup = taskGroup;
    }
}
