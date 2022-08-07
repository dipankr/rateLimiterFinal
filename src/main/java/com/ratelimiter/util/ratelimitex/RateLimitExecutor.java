package com.ratelimiter.util.ratelimitex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static java.lang.System.out;

public class RateLimitExecutor {
    // Defaults to seconds
    private TimeUnit timeUnit;
    // Current transactions counted
    private long transactions = 0L;
    // Transactions per second allowed
    private long threshold;
    // Calculated Transactions per second
    private long tps;
    // Throttle key
    private String instance_id;
    // Timestamp for evaluation
    private double timeStamp;
    // Thread Safety aids
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();
    // Callback handle
    private RateLimitListener listener;

    public RateLimitExecutor() {
        this.timeStamp = System.currentTimeMillis();
    }

    public void setInstanceID(String instance_id) {
        this.instance_id = instance_id;
    }

    public void evalute() {
        out.println("Starting Rate Limit evaluation\n" + "Threshold set is: " + threshold);
        ++transactions;
        wLock.lock();
        // Take the current timestamp
        long currentTime = System.currentTimeMillis();
        // Get the delta time elapsed
        double deltaTime = (currentTime - timeStamp);
        out.println("Delta time elapsed: " + deltaTime);
        // Don’t print TPS on the very first hit as its misleading
        if (transactions != 1) {
            // Calculate transactions per second
            tps = (long)(transactions / deltaTime * 1000L);
            out.println("TPS is - " + tps);
            // What is higher, TPS threshold or transactions per second? Exclude the very first transaction to avoid false positives
            if (tps >= threshold) {
                out.println("Rate limit has been breached, Transaction Number: " + transactions + " in delta time (milliseconds): " + deltaTime + " Threshold: " + threshold);
                out.println();
                RateLimitManager._instance.getThreadPool().execute(new WorkerThread(listener));
            }
        }
        // Leave write lock
        wLock.unlock();
    }

    public void build(TimeUnit time, long threshold) {
        this.timeUnit = time;
        this.threshold = threshold;
    }

    public void setListener(RateLimitListener listener) {
        this.listener = listener;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public Long getTransactions() {
        return transactions;
    }

    public long getThreshold() {
        return threshold;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }
}