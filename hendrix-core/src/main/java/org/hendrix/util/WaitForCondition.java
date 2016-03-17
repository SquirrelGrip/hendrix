package org.hendrix.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WaitForCondition {

    public static final int PERIOD = 500;

    public static boolean waitFor(Condition condition, int timeout) {
        return waitFor(condition, timeout, TimeUnit.SECONDS);
    }

    public static boolean waitFor(final Condition condition, int timeout, TimeUnit unit) {
        final CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (condition.getResult()) {
                    latch.countDown();
                }
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        try {
            executorService.scheduleAtFixedRate(runnable, 0, PERIOD, TimeUnit.MILLISECONDS);
            return latch.await(timeout, unit);
        } catch (InterruptedException e) {
            return false;
        } finally {
            executorService.shutdown();
        }
    }

    public static boolean waitFor(final Condition condition) {
        final CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (condition.getResult()) {
                    latch.countDown();
                }
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        try {
            executorService.scheduleAtFixedRate(runnable, 0, PERIOD, TimeUnit.MILLISECONDS);
            latch.await();
        } catch (InterruptedException e) {
            return false;
        } finally {
            executorService.shutdown();
        }
        return true;
    }

    public interface Condition {
        boolean getResult();
    }

}
