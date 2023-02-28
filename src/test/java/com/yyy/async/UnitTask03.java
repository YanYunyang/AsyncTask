package com.yyy.async;

/**
 * UnitTask03
 *
 * @author Yan Yunyang
 * @date 2023/2/28 17:49
 */
public class UnitTask03 implements ExecutionUnit<String>{
    @Override
    public String action(TaskGroupContext context) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("UnitTask03 running...");
        return null;
    }
}
