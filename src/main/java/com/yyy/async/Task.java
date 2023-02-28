package com.yyy.async;

import java.util.List;

/**
 * Task
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:18
 */
public interface Task<T> extends Visitable {
    /**
     * 执行当前任务
     * @param context 上下文
     * @return 执行结果
     */
    T work(TaskGroupContext context);

    /**
     * 任务id，等同于封装ExecutionUnit的ID,任务组内唯一
     * @return taskId
     */
    String getTaskId();

    /**
     * 添加任务前置执行路径
     * @param executionPath 执行路径
     */
    void addPrePath(ExecutionPath executionPath);

    /**
     * 查询前置依赖路径
     */
    List<ExecutionPath> getPrePath();

    /**
     * 添加任务后续执行路径
     * @param executionPath 执行路径
     */
    void addAfterPath(ExecutionPath executionPath);

    /**
     * 查询后续执行路径
     */
    List<ExecutionPath> getAfterPath();

    /**
     * 查询任务前置的强依赖执行路径数量
     * @return count
     */
    int getPreMustExecutionPathCount();

    /**
     * 查询task封装的执行单元
     * @return ExecutionUnit
     */
    ExecutionUnit<?> getExecutionUnit();

    /**
     * 判断当前任务是否为内置任务（Start，End）
     * @return boolean
     */
    boolean isStartOrEnd();
}
