package g7asmt1.client;

import g7asmt1.server.Result;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    final int MAX_SIZE = 45;
    private List<CacheItem> cache;
    boolean enabled = true;

    public Cache() {
        this.cache = new ArrayList<CacheItem>();
    }

    synchronized public void add(String method, String[] args, Result result) {
        CacheItem item = new CacheItem();
        item.key = method + String.join("$", args);
        item.value = result;

        if (this.cache.size() == MAX_SIZE) {
            this.cache.remove(0);
        }

        this.cache.add(item);
    }

    synchronized public Result get(String method, String[] args) {
        if (!this.enabled) {
            return null;
        }

        String key = method + String.join("$", args);

        for (CacheItem item : this.cache) {
            if (item.key.equals(key)) {
                return copyResultWithCacheHitFlagSet(item.value);
            }
        }

        return null;
    }

    synchronized public void disable() {
        this.enabled = false;
    }

    synchronized public void enable() {
        this.enabled = true;
    }

    private Result copyResultWithCacheHitFlagSet(Result result) {
        return new Result(result.serviceName, result.result, result.waitingTime, result.executionTime, result.zone, result.serverCacheHit, true);
    }
}
