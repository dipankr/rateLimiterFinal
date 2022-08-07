package com.ratelimiter.util.ratelimitex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class RateLimitExecutorTest  implements RateLimitListener {
    // Number of iterations to be executed (simulate load generation)
    private static long iterations = 1000;
    // Rate Limit to be sent in Transactions per Second.
    // Inspect the TPS value in the prints and play around with this value. Increase this value to avoid throttling
    private static long threshold_to_be_enforced = 10;
    // Sleep time between pumping traffic in milliseconds. Set to zero for uncontrolled traffic ingestion.
    // Give a sleep time of 100 ms for example to pump traffic every 100 milliseconds and control the TPS
    private static long sleepTime = 0;

   /* public static void main(String[] args) throws InterruptedException {
        new RateLimitExecutorTest().test();
    }
*/
    @Test
    public void test() throws InterruptedException {
        // Setup and provision the rate limit
        // User Defined Rate Limit Instance Id. Eg: HTTP Interface
        String instance_id = "HTTP Interface";
        // Create an instance of Rate Limit Executor
        RateLimitExecutor rateLimiter = new RateLimitExecutor();
        // Set the thresholds / second.
        rateLimiter.build(TimeUnit.SECONDS, threshold_to_be_enforced);
        // Associate instance ID
        rateLimiter.setInstance_id(instance_id);
        // Provision Rate Limit
        RateLimitManager._instance.provisionRateLimit(rateLimiter, instance_id, new RateLimitThrottleListener());
        // Start the test
        for (int i = 0; i < iterations; i++) {
            RateLimitManager._instance.pegTraffic(instance_id);
            Thread.sleep(sleepTime);
        }
        try {
            // Wait for graceful termination of worker thread from the pool
            RateLimitManager._instance.getThreadPool().awaitTermination(2, TimeUnit.SECONDS);
            RateLimitManager._instance.deProvisionRateLimit("HTTP Interface");
        } catch (InterruptedException e) {
            Assertions.assertTrue(false);
        }
        System.out.println("\n Test Passed 🎉 \n");
        Assertions.assertTrue(true);
    }

    public void rateLimitThresholdBreached() {
        System.out.println("Rate Limit has been breached for: ");
    }

    public void rateLimitThresholdNormal() {
        System.out.println("Rate Limit is under control for: ");
    }
}