package easy.framework.plugin.cache;

import java.util.Collection;

/**
 * 缓存管理器
 * @author limengyu
 */
public interface CacheManage {

    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @return
     */
    Cache getCache(String cacheName);

    /**
     * 获取所有缓存的名字
     * @return
     */
    Collection<String> getCacheNames();
}
