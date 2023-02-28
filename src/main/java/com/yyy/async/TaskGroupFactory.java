package com.yyy.async;

import com.yyy.async.exception.AsyncTaskException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TaskGroupFactory
 *
 * @author Yan Yunyang
 * @date 2023/2/28 13:45
 */
public class TaskGroupFactory {
    static Map<String /* taskGroupId */,TaskGroup> taskGroupMap = new ConcurrentHashMap<>();

    public static void register(TaskGroup taskGroup){
        String taskGroupId = taskGroup.getTaskGroupId();
        if (taskGroupMap.get(taskGroupId) != null){
            throw new AsyncTaskException("the task group with id[" + taskGroupId + "] is already built, no need to build again");
        }
        taskGroupMap.put(taskGroupId,taskGroup);
    }

    public static TaskGroup get(String taskGroupId){
        TaskGroup taskGroup = taskGroupMap.get(taskGroupId);
        if (taskGroup == null){
            throw new AsyncTaskException("there is no task group instance for " + taskGroupId + ",please build it first");
        }
        return taskGroup;
    }
}
