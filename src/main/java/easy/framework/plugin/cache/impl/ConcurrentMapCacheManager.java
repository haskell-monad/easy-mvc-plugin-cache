package easy.framework.plugin.cache.impl;

import easy.framework.plugin.cache.Cache;
import easy.framework.plugin.cache.CacheManage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class ConcurrentMapCacheManager implements CacheManage{

    private final Map<String,Cache> cacheManager;

    public ConcurrentMapCacheManager(){
        this.cacheManager = new ConcurrentHashMap<>();
    }

    @Override
    public Cache getCache(String cacheName) {
        if(!cacheManager.containsKey(cacheName)){
            Cache cache = new ConcurrentMapCache();
            cacheManager.put(cacheName,cache);
        }
        return cacheManager.get(cacheName);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManager.keySet();
    }
}
