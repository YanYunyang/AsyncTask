package com.yyy.async;

/**
 * UnitTask04
 *
 * @author Yan Yunyang
 * @date 2023/2/28 17:50
 */
public class UnitTask04 implements ExecutionUnit<String>{
    @Override
    public String action(TaskGroupContext context) {
        System.out.println("UnitTask04 running");
        System.out.println("UnitTask04 get Unit01Task01 Work Result[" + context.get(UnitTask01.class.getName()) + "]");
        return "well done";
    }
}
