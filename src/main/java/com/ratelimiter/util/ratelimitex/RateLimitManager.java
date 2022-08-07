package com.ratelimiter.util.ratelimitex;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class RateLimitManager {
    // Static singleton class which is thread safe from multiple instantiation race conditions in traditional singletons
    public static final RateLimitManager _instance = new RateLimitManager();
    // Container for keeping track of all provisioned rate limits.
    private ConcurrentHashMap<String, RateLimitExecutor> rateLimitMap = new ConcurrentHashMap<>();
    // Thread pool initialization
    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // To provision a new Rate Limit Policy and executor. Callback interface to receive notifications
    public void provisionRateLimit(RateLimitExecutor builder, String instance_id, RateLimitListener listener) {
        builder.setListener(listener);
        rateLimitMap.put(instance_id, builder);
    }

    // To remove a rate limit policy
    public void deProvisionRateLimit(String instance_id) {
        try {
            rateLimitMap.remove(instance_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // API Call to peg traffic and evaluate the rate limit provisioned
    public void pegTraffic(String instance_id) {
        rateLimitMap.get(instance_id).evalute();
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
