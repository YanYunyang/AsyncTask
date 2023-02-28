package com.yyy.async;

/**
 * UnitTask01
 *
 * @author Yan Yunyang
 * @date 2023/2/28 17:46
 */
public class UnitTask01 implements ExecutionUnit<String>{
    @Override
    public String action(TaskGroupContext context) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("UnitTask01 running...");
        return "hello,world! from UnitTask01.";
    }
}
