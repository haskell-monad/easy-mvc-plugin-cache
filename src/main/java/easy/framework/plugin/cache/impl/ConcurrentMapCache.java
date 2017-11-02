package easy.framework.plugin.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import easy.framework.plugin.cache.Cache;
import easy.framework.plugin.cache.common.ExpiryTime;
import easy.framework.plugin.cache.model.Duration;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class ConcurrentMapCache implements Cache<String,Object>{

    private final Map<String,Object> localCache;
    private final Map<String,Duration> localDuration;

    public ConcurrentMapCache() {
        this.localCache = new ConcurrentHashMap<>();
        this.localDuration = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String key, Object value) {
        put(key,value,ExpiryTime.NEVER);
    }

    @Override
    public Object get(String key) {
        return localCache.get(key);
    }

    @Override
    public void remove(String ...key) {
        if(key != null && key.length > 0){
            for (int i = 0; i < key.length;i++){
                if(localCache.containsKey(key[i])){
                    localCache.remove(key[i]);
                }
                if(localDuration.containsKey(key[i])){
                    localDuration.remove(key[i]);
                }
            }
        }
    }

    @Override
    public void removeAll() {
        localCache.clear();
        localDuration.clear();
    }

    @Override
    public void put(String key, Object value,long delay) {
        localCache.put(key, value);
        if(ExpiryTime.NEVER != delay){
            localDuration.put(key, new Duration(delay));
        }
    }

    @Override
    public boolean existKey(String key) {
        return localCache.containsKey(key) ? true : false;
    }

    @Override
    public Map<String,Duration> getDurationMap(){
        return localDuration;
    }
}
