package cn.com.sina.alan.common.utils.concurrent;

import cn.com.sina.alan.common.exception.AlanConcurrentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并发执行任务工具类
 * Created by whf on 8/27/16.
 */
public class ConcurrentUtils {
    /**
     * 默认超时值
     */
    public static final int TIMEOUT = 2000;
    /**
     * 默认超时值时间单位
     */
    public static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    private ConcurrentUtils() {}

    /**
     * 并发执行两个返回结果相同的任务
     * @param task1
     * @param task2
     * @param <T>
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T> List<T> concurrentExecuteSame(AlanTask<T> task1, AlanTask<T> task2) throws AlanConcurrentException {
        return concurrentExecuteSame(task1, task2, TIMEOUT, UNIT);
    }

    /**
     * 并发执行两个返回结果相同的任务
     * @param task1
     * @param task2
     * @param timeout 超时时间值
     * @param unit 超时时间值的单位
     * @param <T>
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T> List<T> concurrentExecuteSame(AlanTask<T> task1, AlanTask<T> task2, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<List<T>> f = CompletableFuture.supplyAsync(
                () -> task1.execute()
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute()
        ), (r1, r2) -> Arrays.asList(r1, r2));

        return processFuture(f, timeout, unit);
    }

    /**
     * 并发执行两个返回类型不同的任务
     * @param task1
     * @param task2
     * @param <T1>
     * @param <T2>
     * @return
     * @throws AlanConcurrentException
     */
    public static <T1, T2> List<Object> concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2) throws AlanConcurrentException {
        return concurrentExecuteDiff(task1, task2, TIMEOUT, UNIT);
    }

    /**
     * 并发执行两个返回类型不同的任务
     * @param task1
     * @param task2
     * @param timeout
     * @param unit
     * @param <T1>
     * @param <T2>
     * @throws AlanConcurrentException 并发调用发生错误
     * @return
     */
    public static <T1, T2> List<Object> concurrentExecuteDiff(AlanTask<T1> task1, AlanTask<T2> task2, int timeout, TimeUnit unit) throws AlanConcurrentException {
        Future<List<Object>> f = CompletableFuture.supplyAsync(
                () -> task1.execute()
        ).thenCombine( CompletableFuture.supplyAsync(
                () -> task2.execute()
        ), (r1, r2) -> {
            List<Object> result = new ArrayList<>(2);
            result.add(r1);
            result.add(r2);
            return result;
        } );

        return processFuture(f, timeout, unit);
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
            throw new AlanConcurrentException(e);

        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new AlanConcurrentException(e);
        }

    }

}
