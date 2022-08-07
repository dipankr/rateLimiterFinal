package com.ratelimiter.util.ratelimitex;

public class WorkerThread implements Runnable {
    private RateLimitListener listener;

    public WorkerThread(RateLimitListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        this.listener.rateLimitThresholdBreached();
    }
}
