package com.yyy.async.builder;

/**
 * TaskGroupBuilderFactory
 *
 * @author Yan Yunyang
 * @date 2023/2/28 14:33
 */
public class TaskGroupBuilderFactory {
    public static TaskGroupBuilder create(){
        return new TaskGroupBuilderImpl();
    }
}
