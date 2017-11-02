package easy.framework.plugin.cache;

import easy.framework.InstanceFactory;
import easy.framework.plugin.cache.common.ConfigConstant;
import easy.framework.plugin.cache.impl.ConcurrentMapCacheManager;
import easy.framework.utils.PropertyUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class CacheHelper {

    /**
     * <目标类,缓存管理器>
     */
    private static final Map<Class<?>,CacheManage> CACHE_MANAGER = new ConcurrentHashMap<>();

    /**
     * 获取所有的缓存管理器
     * @return
     */
    public static final Collection<CacheManage> getAllCacheManage(){
        return CACHE_MANAGER.values();
    }

    /**
     * 获取目标类的缓存
     * @param clazz
     * @return
     */
    public static CacheManage getCacheManage(Class<?> clazz){
        if(!CACHE_MANAGER.containsKey(clazz)){
            CacheManage cacheManage = builderCacheManager();
            CACHE_MANAGER.put(clazz,cacheManage);
        }
        return CACHE_MANAGER.get(clazz);
    }

    /**
     * 创建缓存管理器
     * @return
     */
    private static CacheManage builderCacheManager(){
        CacheManage cacheManage;
        String cacheManagerKey = PropertyUtil.getStringValue(ConfigConstant.CACHE_MANAGER_KEY);
        if(StringUtils.isBlank(cacheManagerKey)){
            cacheManage = new ConcurrentMapCacheManager();
        }else{
            //TODO 这里需要获取自定义的管理器
            cacheManage = null;
        }
        return cacheManage;
    }
}
