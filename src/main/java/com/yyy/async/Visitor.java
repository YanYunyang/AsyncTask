package com.yyy.async;

/**
 * Visitor
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:38
 */
public interface Visitor {
    String LF = "\n";

    /**
     * @param taskGroup 被访问元素：任务组
     */
    String visitOnEntry(TaskGroup taskGroup);

    /**
     * @param taskGroup 被访问元素：任务组
     */
    String visitOnExit(TaskGroup taskGroup);

    /**
     * @param task 被访问元素：任务
     */
    String visitOnEntry(Task<?> task);

    /**
     * @param task 被访问元素：任务
     */
    String visitOnExit(Task<?> task);
}
