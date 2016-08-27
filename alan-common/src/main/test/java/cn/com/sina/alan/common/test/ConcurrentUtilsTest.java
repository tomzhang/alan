package cn.com.sina.alan.common.test;

import cn.com.sina.alan.common.utils.concurrent.ConcurrentUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 8/27/16.
 */
public class ConcurrentUtilsTest {
    @Test
    public void testExeSameTask() throws Exception {
        long start = System.currentTimeMillis();
        List<Boolean> result = ConcurrentUtils.concurrentExecuteSame(
                () -> delay(1),
                () -> delay(100)
        );

        System.out.println(result);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        result = ConcurrentUtils.concurrentExecuteSame(
                () -> delay(1),
                () -> delay(1)
        );
        System.out.println(result);
        System.out.println(System.currentTimeMillis() - start);
    }

    private boolean delay(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
