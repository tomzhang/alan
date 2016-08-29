package cn.com.sina.alan.common.utils.concurrent;

import cn.com.sina.alan.common.exception.AlanConcurrentException;
import cn.com.sina.alan.common.exception.AlanException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 并发执行任务工具类
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 8/27/16.
 */
public class ConcurrentUtils {
    /**
     * 默认超时值
     */
    public static final int TIMEOUT = 3000;
    /**
     * 默认超时值时间单位
     */
    public static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /**
     * 线程池默认大小
     */
    public static int THREAD_POOL_SIZE = 10;

    /**
     * 线程池. 固定大小
     */
    public static Executor pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private ConcurrentUtils() {}

    /**
     * 并发执行两个返回结果相同的任务
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param <T> 并发调用返回结果的类型
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T> List<T> concurrentExecuteSame(AlanTask<T> task1, AlanTask<T> task2) throws AlanConcurrentException {
        return concurrentExecuteSame(task1, task2, TIMEOUT, UNIT);
    }

    /**
     * 并发执行两个任务,并调用用户自定义逻辑聚合结果
     *
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param func 聚合逻辑
     * @param <T1> 并发任务返回结果对象的类型
     * @param <T2> 聚合后的返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2> T2 concurrentExecuteSame(AlanTask<T1> task1, AlanTask<T1> task2, BiFunction<T1, T1, T2> func) throws AlanConcurrentException {
        return concurrentExecuteSame(task1, task2, func, TIMEOUT, UNIT);
    }


    /**
     * 并发执行两个返回结果相同的任务
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param <T>
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T> List<T> concurrentExecuteSame(AlanTask<T> task1, AlanTask<T> task2, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<List<T>> f = CompletableFuture.supplyAsync(
                () -> task1.execute(),
                pool
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute(),
                pool
        ), (r1, r2) -> Arrays.asList(r1, r2));

        return processFuture(f, timeout, unit);
    }

    /**
     * 并发执行两个任务,并调用用户自定义逻辑聚合结果
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param func 聚合逻辑
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param <T1> task1的返回类型
     * @param <T2> task2的返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2> T2 concurrentExecuteSame(AlanTask<T1> task1, AlanTask<T1> task2, BiFunction<T1, T1, T2> func, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<T2> f = CompletableFuture.supplyAsync(
                () -> task1.execute(),
                pool
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute(),
                pool
        ), func);

        return processFuture(f, timeout, unit);
    }

    /**
     * 并发执行多个任务,取最快返回的结果
     *
     * @param tasks 并发任务
     * @param <T> 返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T> T concurrentExecuteRace(AlanTask<T>... tasks) throws AlanConcurrentException {
        return concurrentExecuteRace(TIMEOUT, UNIT, tasks);
    }

    /**
     * 并发执行多个任务,取最快返回的结果
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param tasks 并发任务
     * @param <T> 返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T> T concurrentExecuteRace(int timeout, TimeUnit unit, AlanTask<T>... tasks) throws AlanConcurrentException {
        checkTasks(tasks);

        CompletableFuture<T>[] futures = Stream.of(tasks)
                .map( task -> CompletableFuture.supplyAsync( () -> task.execute(), pool ) )
                .toArray( CompletableFuture[]::new );

        CompletableFuture f = CompletableFuture.anyOf(futures);
        return (T) processFuture(f, timeout, unit);
    }

    /**
     * 并发执行两个返回类型不同的任务
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param <T1> task1的返回类型
     * @param <T2> task2的返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2> List<Object> concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2) throws AlanConcurrentException {
        return concurrentExecuteDiff(task1, task2, TIMEOUT, UNIT);
    }

    /**
     * 并发执行两个返回类型不同的任务, 并调用用户逻辑聚合结果
     *
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param func 聚合逻辑
     * @param <T1> task1的返回类型
     * @param <T2> task2的返回类型
     * @param <T3> 聚合结果后的返回类型
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2, T3> T3 concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2, BiFunction<T1, T2, T3> func) throws AlanConcurrentException {
        return concurrentExecuteDiff(task1, task2, func, TIMEOUT, UNIT);
    }

    /**
     * 并发执行两个返回类型不同的任务
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param <T1> task1的返回类型
     * @param <T2> task2的返回类型
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T1, T2> List<Object> concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<List<Object>> f = CompletableFuture.supplyAsync(
                () -> task1.execute(),
                pool
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute(),
                pool
        ), (r1, r2) -> {
            List<Object> result = new ArrayList<>(2);
            result.add(r1);
            result.add(r2);
            return result;
        } );

        return processFuture(f, timeout, unit);
    }

    /**
     * 并发执行两个返回类型不同的任务, 并调用用户逻辑聚合结果
     *
     * @param task1 并发任务1
     * @param task2 并发任务2
     * @param func 聚合逻辑
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param <T1> 并发任务1的返回结果
     * @param <T2> 并发任务2的返回结果
     * @param <T3> 聚合结果
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2, T3> T3 concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2, BiFunction<T1, T2, T3> func, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<T3> f = CompletableFuture.supplyAsync(
                () -> task1.execute(),
                pool
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute(),
                pool
        ), func);

        return processFuture(f, timeout, unit);
    }

    /**
     * 支持无限多个任务并发执行
     * @param tasks 至少传2个任务
     * @return
     * @throws AlanConcurrentException
     */
    public static List<Object> concurrentExecuteDiffs(AlanTask<Object>... tasks) throws AlanConcurrentException {
        return concurrentExecuteDiffs(TIMEOUT, UNIT, tasks);
    }

    /**
     * 支持无限多个任务并发执行
     *
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param tasks 至少传2个任务
     * @return
     * @throws AlanConcurrentException
     */
    public static List<Object> concurrentExecuteDiffs(int timeout, TimeUnit unit, AlanTask<Object>... tasks) throws AlanConcurrentException {
        checkTasks(tasks);

        final int LEN = tasks.length;
        // 投递任务
        List<CompletableFuture<Object>> futures = Arrays.asList(tasks).stream()
                .map( task -> CompletableFuture.supplyAsync( () -> task.execute(), pool ) )
                .collect(Collectors.toList());

        // 获取结果
        List<Object> result = new ArrayList<>(LEN);
        for (CompletableFuture<Object> f : futures) {
            result.add(processFuture(f, timeout, unit));
        }

        return result;
    }

    /**
     * 从Future中取出结果并处理异常
     * @param f
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     * @throws AlanConcurrentException
     */
    private static  <T> T processFuture(Future<T> f, int timeout, TimeUnit unit) throws AlanConcurrentException {
        try {
            return f.get(timeout, unit);

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new AlanConcurrentException(e);

        } catch (ExecutionException e) {
            e.printStackTrace();

            // 如果是业务异常, 则重新抛出
            if (e.getCause() instanceof AlanException) {
                throw (AlanException)e.getCause();
            } else {
                throw new AlanConcurrentException(e);
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new AlanConcurrentException(e);
        }

    }

    private static void checkTasks(AlanTask... tasks) {
        if (null == tasks) {
            throw new IllegalArgumentException("tasks cannot be null");
        }

        final int LEN = tasks.length;
        if (LEN < 2) {
            throw new IllegalArgumentException("at least 2 tasks");
        }

    }

}
