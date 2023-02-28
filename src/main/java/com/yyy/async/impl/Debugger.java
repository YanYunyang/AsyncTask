package com.yyy.async.impl;

/**
 * Debugger
 *
 * @author Yan Yunyang
 * @date 2023/2/28 15:23
 */
public class Debugger {
    private static boolean isDebugOn = false;

    public static void debug(String message){
        if (isDebugOn){
            System.out.println(message);
        }
    }

    public static void enableDebug(){
        isDebugOn = true;
    }
}
