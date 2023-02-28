package com.yyy.async;

/**
 * ExecutionPath
 *
 * <p>task:source--[must]-->task:target</p>
 * <p>任务间执行路径，通过本类来编排任务组</p>
 * <p>must标识该执行性路径是否为target任务的强依赖</p>
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:17
 */
public interface ExecutionPath {

    /**
     * 设置依赖路径是否为强制依赖
     */
    void setMust(boolean must);

    /**
     * 查询路径must属性
     */
    boolean getMust();

    void setSource(Task<?> task);
    Task<?> getSource();

    void setTarget(Task<?> task);

    Task<?> getTarget();
}
