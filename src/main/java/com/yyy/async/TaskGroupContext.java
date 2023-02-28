package com.yyy.async;

import com.yyy.async.exception.AsyncTaskException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TaskGroupContext
 *
 * 任务组上下文，负责启动参数初始化，任务执行结果存储，全链路共享
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:25
 */
public class TaskGroupContext {
    private final Map<String,Object> context = new ConcurrentHashMap<>();

    /**
     * 任务组共享上下文，查询key对应value
     * @param key 关键字
     * @return value
     */
    public Object get(String key){
        return context.get(key);
    }

    /**
     * 任务组共享上下文，存储
     * @param key 关键字
     * @param value 值
     */
    public void put(String key, Object value){
        if (context.containsKey(key)){
            throw new AsyncTaskException("context don't allow duplicate key[" + key + "]");
        }
        context.put(key,value);
    }

    /**
     * 查询任务组中任务执行单元的结果
     * @param executionUnit 执行单元
     * @return 执行结果
     */
    public WorkResult<?> getWorkResult(ExecutionUnit<?> executionUnit){
        return (WorkResult<?>) context.get(executionUnit.getId());
    }
}
