package com.yyy.async;

import com.yyy.async.builder.TaskGroupBuilder;
import com.yyy.async.builder.TaskGroupBuilderFactory;
import com.yyy.async.impl.Debugger;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MultiTaskTest
 *
 * @author Yan Yunyang
 * @date 2023/2/28 17:52
 */
public class MultiTaskTest {

    @Test
    public void show_task_group_structure(){
        TaskGroupBuilder taskGroupBuilder = TaskGroupBuilderFactory.create();
        UnitTask01 unitTask01 = new UnitTask01();
        UnitTask02 unitTask02 = new UnitTask02();
        UnitTask03 unitTask03 = new UnitTask03();
        UnitTask04 unitTask04 = new UnitTask04();
        taskGroupBuilder.executionPath().from(unitTask01).to(unitTask04).must(true);
        taskGroupBuilder.executionPath().from(unitTask02).to(unitTask04).must(false);
        taskGroupBuilder.executionPath().from(unitTask03).to(unitTask04).must(false);

        TaskGroup taskGroup = taskGroupBuilder.build("yyy test");
        System.out.println(taskGroup.showTaskGroup());
    }

    @Test
    public void task_group_execute() throws ExecutionException, InterruptedException {
        TaskGroupBuilder taskGroupBuilder = TaskGroupBuilderFactory.create();
        UnitTask01 unitTask01 = new UnitTask01();
        UnitTask02 unitTask02 = new UnitTask02();
        UnitTask03 unitTask03 = new UnitTask03();
        UnitTask04 unitTask04 = new UnitTask04();
        taskGroupBuilder.executionPath().from(unitTask01).to(unitTask04).must(true);
        taskGroupBuilder.executionPath().from(unitTask02).to(unitTask04).must(false);
        taskGroupBuilder.executionPath().from(unitTask03).to(unitTask04).must(false);

        TaskGroup taskGroup = taskGroupBuilder.build("yyy test");
        Debugger.enableDebug();
        TaskGroupContext context = new TaskGroupContext();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        taskGroup.work(context,executorService,2000);
    }
}
