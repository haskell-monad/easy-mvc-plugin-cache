package easy.framework.plugin.cache.impl;

import easy.framework.plugin.cache.Cache;
import easy.framework.plugin.cache.model.Duration;

import java.util.Map;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class RedisCache implements Cache{


    @Override
    public void put(Object key, Object value) {

    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public void remove(Object ...key) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public void put(Object key, Object value, long delay) {

    }

    @Override
    public boolean existKey(Object key) {
        return false;
    }

    @Override
    public Map<String, Duration> getDurationMap() {
        return null;
    }
}
