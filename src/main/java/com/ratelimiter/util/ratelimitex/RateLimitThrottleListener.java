package com.ratelimiter.util.ratelimitex;

public class RateLimitThrottleListener implements RateLimitListener {
    public RateLimitThrottleListener() {
    }

    public void rateLimitThresholdBreached() {
        System.out.println("\033[1;33m" + "Received threshold breach callback notification" + "\033[0m");
    }
}
