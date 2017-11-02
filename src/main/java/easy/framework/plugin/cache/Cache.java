package easy.framework.plugin.cache;

import easy.framework.plugin.cache.model.Duration;

import java.util.Map;

/**
 * @author limengyu
 */
public interface Cache<K, V> {

    /**
     * 添加缓存
     * @param key
     * @param value
     */
	void put(K key, V value);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 移除缓存
     * @param key
     */
    void remove(K ...key);

    /**
     * 移除所有缓存
     */
    void removeAll();

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param delay
     */
    void put(K key, V value, long delay);

    /**
     * 缓存中是否存在key
     * @param key
     * @return
     */
    boolean existKey(K key);

    /**
     * 获取所有有过期的缓存
     * @return
     */
    Map<String,Duration> getDurationMap();
}
