package easy.framework.plugin.cache.common;

/**
 * @author limengyu
 * @create 2017/10/23
 */
public class ConfigConstant {

    public static final String PLUGIN_NAME = "cache-manage";

    /**
     * 缓存配置项
     */
    public static final String CACHE_KEY = "easy.plugin.cache";

    /**
     * 缓存管理器配置项
     */
    public static final String CACHE_MANAGER_KEY = "easy.plugin.cache.manager";

    enum CacheType{
        LOCAL("local"),
        REDIS("redis"),
        GUAVA("guava");
        CacheType(String name){}
    }
}
