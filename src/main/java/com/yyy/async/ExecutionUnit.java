package com.yyy.async;

/**
 * ExecutionUnit
 *
 * <p>最小执行单元，具体业务执行逻辑，回调异常处理等需实现本接口</p>
 * <p>warning:任务id默认为class name，如同一个执行单元实现需要编</p>
 * <p>排多次，需要重写getId()方法，保证执行单元实例在任务组内唯一</p>
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:15
 */
public interface ExecutionUnit<T> {

    /**
     * 业务逻辑执行
     * @param context 上下文
     * @return 执行结果
     */
    T action(TaskGroupContext context);

    /**
     * 任务出现异常、超时时返回的默认值
     * @return null
     */
    default T defaultValue() {
        return null;
    }

    /**
     * 任务开始执行时的回调接口
     */
    default void begin(){

    }

    /**
     * 任务执行结束时的回调接口
     * @param workResult 任务执行结果
     */
    default void result(WorkResult<T> workResult){

    }

    /**
     * 执行单元的唯一id，一个执行单元不同的实例需要在同一个任务组中，需要重写本方法
     * @return 任务id
     */
    default String getId(){
        return this.getClass().getName();
    }
}
