package com.yyy.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * TaskGroup
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:33
 */
public interface TaskGroup {

    /**
     * 按照任务编排执行自定义任务
     * @param context 任务组运行时上下文
     * @param executorService 运行线程池
     * @param timeout 任务组超时时间
     * @return 执行结果
     */
    boolean work(TaskGroupContext context, ExecutorService executorService, long timeout) throws ExecutionException,InterruptedException;

    /**
     * 展示任务组编排结构
     * @return 任务组结构
     */
    String showTaskGroup();

    /**
     * taskGroupId是任务组唯一标识
     * @return 任务组id
     */
    String getTasGroupkId();
}
