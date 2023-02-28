package com.yyy.async;

/**
 * UnitTask02
 *
 * @author Yan Yunyang
 * @date 2023/2/28 17:48
 */
public class UnitTask02 implements ExecutionUnit<String>{
    @Override
    public String action(TaskGroupContext context) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("UnitTask02 running...");
        return null;
    }
}
