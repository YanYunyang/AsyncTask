package com.yyy.async;

import com.yyy.async.impl.Debugger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TaskWrapper
 *
 * @author Yan Yunyang
 * @date 2023/2/28 18:22
 */
public class TaskWrapper<T> {
    private final Task<T> task;

    private final WorkResult<T> workResult = WorkResult.EmptyResult();

    /**
     * 计数器，记录前置强依赖路径到达本任务的数量，当计数器值为任务组中定义前置强依赖路径时本任务执行（代表所有前置依赖任务均已完成）
     */
    private final AtomicInteger preMustExecutionPathArrivedCount = new AtomicInteger(0);

    /**
     * 保证本任务被执行一次且仅一次
     * volatile仅能解决线程间可见性问题，无法解决原子性问题
     * 0-init 1-finish -2-error 3-working
     */
    private final AtomicInteger state = new AtomicInteger(0);

    public static final int INIT = 0;
    public static final int FINISH = 1;
    public static final int ERROR = 2;
    public static final int WORKING = 3;

    public TaskWrapper(Task<T> task) {
        this.task = task;
    }

    public void work(TaskGroupContext context, ExecutorService executorService, long remainMills, Map<String,TaskWrapper<?>> allTaskWrappers, ExecutionPath executionPath){
        long now = System.currentTimeMillis();
        //是否强依赖执行路径触发本任务
        if (executionPath.getMust()){
            preMustExecutionPathArrivedCount.incrementAndGet();
        }
        //前置的强依赖执行路径是否全部到达
        if (!dependPathAllArrived()){
            Debugger.debug("current task[" + executionPath.getTarget().getTaskId() + "], status[" + getStateName() +"], executionPath[" + executionPath + "]:UNREADY");
            return;
        }
        //如果超时且当前任务未执行，快速失败后，调用后续任务。保证每个任务，每条路径都经过一次
        if (remainMills < 0 && fastFail(INIT,null)){
            workNext(context, executorService, remainMills, allTaskWrappers);
            return;
        }
        //当前任务执行中或执行完成，退出
        if (!compareAndSetState(INIT, WORKING)){
            Debugger.debug("current task[" + executionPath.getTarget().getTaskId() + "], status[" + getStateName() +"], executionPath[" + executionPath + "]:SKIP");
            return;
        }
        Debugger.debug("current task[" + executionPath.getTarget().getTaskId() + "], status[" + getStateName() +"], executionPath[" + executionPath + "]:WORKING");
        fire(context);

        long costMills = System.currentTimeMillis() - now;
        workNext(context, executorService, remainMills-costMills, allTaskWrappers);
    }

    private void workNext(TaskGroupContext context, ExecutorService executorService, long remainMills, Map<String, TaskWrapper<?>> allTaskWrappers) {
        List<ExecutionPath> afterExecutionPaths = task.getAfterPath();

        //END任务，无后续任务，直接退出
        int size = afterExecutionPaths.size();
        if (size == 0){
            return;
        }

        //线程复用
        if (size == 1){
            ExecutionPath executionPath= afterExecutionPaths.get(0);
            TaskWrapper<?> nextTaskWrapper = allTaskWrappers.get(executionPath.getTarget().getTaskId());
            nextTaskWrapper.work(context,executorService,remainMills,allTaskWrappers,executionPath);
            return;
        }

        CompletableFuture<?>[] futures = new CompletableFuture[size];
        for (int i = 0; i<size; i++){
            ExecutionPath executionPath = afterExecutionPaths.get(i);
            TaskWrapper<?> nextTaskWrapper = allTaskWrappers.get(executionPath.getTarget().getTaskId());
            futures[i] = CompletableFuture.runAsync(()->nextTaskWrapper.work(context,executorService,remainMills,allTaskWrappers,executionPath),executorService);
        }
        try {
            CompletableFuture.allOf(futures).get(remainMills, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire(TaskGroupContext context) {
        task.getExecutionUnit().begin();
        try{
            T result = task.work(context);
            //如果正常结束，写结果。如果状态写失败，说明触发了异常，快速失败。
            if (compareAndSetState(WORKING,FINISH)){
                workResult.setResultState(ResultState.SUCCESS);
                workResult.setResult(result);
                task.getExecutionUnit().result(workResult);
            }
        }catch(Exception e){
            fastFail(WORKING,e);
        }
    }

    private boolean compareAndSetState(int expect, int update) {
        return this.state.compareAndSet(expect,update);
    }

    private boolean fastFail(int expect, Exception exception) {
        //试图将任务从expect状态修改为ERROR
        if (!compareAndSetState(expect,ERROR)){
            return false;
        }

        //尚未处理结果
        if (workResult.getResultState() == ResultState.EMPTY){
            if (exception == null){
                timeoutResult();
            }else{
                exceptionResult(exception);
            }
        }
        Debugger.debug("current task[" + task.getTaskId() + "], status[" + getStateName() +"], resultState[" + workResult.getResultState() + "]:FASTFAIL");
        task.getExecutionUnit().result(workResult);
        return true;
    }

    private void exceptionResult(Exception exception) {
        workResult.setResultState(ResultState.EXCEPTION);
        workResult.setResult(task.getExecutionUnit().defaultValue());
        workResult.setException(exception);
    }

    private void timeoutResult() {
        workResult.setResultState(ResultState.TIMEOUT);
        workResult.setResult(task.getExecutionUnit().defaultValue());
    }

    private String getStateName() {
        int status = state.get();
        switch (status){
            case INIT:
                return "INIT";
            case WORKING:
                return "WORKING";
            case FINISH:
                return "FINISH";
            case ERROR:
                return "ERROR";
            default:
                return "UNKNOWN STATUS";
        }
    }

    public WorkResult<T> getWorkResult() {
        return workResult;
    }

    public Task<T> getTask() {
        return task;
    }

    private boolean dependPathAllArrived() {
        return task.getPreMustExecutionPathCount() == preMustExecutionPathArrivedCount.get();
    }

    public void stopWork() {
        if (getState() == INIT || getState() == WORKING){
            fastFail(getState(),null);
        }
    }

    public int getState(){
        return state.get();
    }
}
