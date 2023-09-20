/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g7asmt1.server;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author turzo
 */


public class CacheServer {
    
      final int MAX_SIZE = 150;
    private List<CacheItemServer> cache;
    boolean enabled = true;

    public CacheServer() {
        this.cache = new ArrayList<CacheItemServer>();
    }

    synchronized public void add(String method, String args, Result result) {
        CacheItemServer item = new CacheItemServer();
        item.key = method + String.join("$", args);
        item.value = result;

        if (this.cache.size() == MAX_SIZE) {
            this.cache.remove(0);
        }

        this.cache.add(item);
    }

    synchronized public Result get(String method, String args) {
        if (!this.enabled) {
            return null;
        }

        String key = method + String.join("$", args);

        for (CacheItemServer item : this.cache) {
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
