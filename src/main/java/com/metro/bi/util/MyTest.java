package com.metro.bi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyTest {

    private static final Logger log = LoggerFactory.getLogger(MyTest.class);

    public void taskSchedule(List<Task> tasks){
        ExecutorService executor = Executors.newCachedThreadPool();
        List<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>();
        for(int i=0;i<tasks.size();i++){
            futureTasks.add(new FutureTask(tasks.get(i)));
            executor.submit(futureTasks.get(i));
        }
        try {
            log.info("task运行结果"+futureTasks.get(1).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        log.info("所有任务执行完毕");
    }

    public static void main(String[] args) {

    }

    class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for(int i=0;i<100;i++)
                sum += i;
            return sum;
        }
    }


}
