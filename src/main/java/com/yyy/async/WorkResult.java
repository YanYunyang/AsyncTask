package com.yyy.async;

/**
 * WorkResult
 *
 * @author Yan Yunyang
 * @date 2023/2/28 11:48
 */
public class WorkResult<T> {
    /**
     * 任务执行结果
     */
    private T result;

    /**
     * 结果状态
     */
    private ResultState resultState;
    /**
     * 任务执行抛出异常
     */
    private Exception exception;

    public WorkResult(T result, ResultState resultState) {
        this(result,resultState,null);
    }

    public WorkResult(T result, ResultState resultState, Exception exception) {
        this.result = result;
        this.resultState = resultState;
        this.exception = exception;
    }

    public static <V> WorkResult<V> EmptyResult(){
        return new WorkResult<>(null,ResultState.EMPTY);
    }

    @Override
    public String toString() {
        return "WorkResult{" +
                "result=" + result +
                ", resultState=" + resultState +
                ", exception=" + exception +
                '}';
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
