package com.yyy.async.impl;

import com.yyy.async.ExecutionPath;
import com.yyy.async.Task;
import com.yyy.async.TaskGroup;
import com.yyy.async.Visitor;

import java.util.List;

/**
 * SysOutVisitor
 *
 * @author Yan Yunyang
 * @date 2023/2/28 15:30
 */
public class SysOutVisitor implements Visitor {
    @Override
    public String visitOnEntry(TaskGroup taskGroup) {
        return "-----------TaskGroup definition:-----------";
    }

    @Override
    public String visitOnExit(TaskGroup taskGroup) {
        return "-------------------------------------------";
    }

    @Override
    public String visitOnEntry(Task<?> task) {
        StringBuilder sb = new StringBuilder();
        sb.append("task:").append(task.getTaskId()).append(LF);

        List<ExecutionPath> afterExecutionPath = task.getAfterPath();
        for (ExecutionPath path: afterExecutionPath){
            sb.append("\texecutionPath:").append(path).append(LF);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(Task<?> task) {
        return "";
    }
}
