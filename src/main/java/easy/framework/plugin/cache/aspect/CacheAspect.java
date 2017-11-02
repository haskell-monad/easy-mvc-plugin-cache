package easy.framework.plugin.cache.aspect;

import easy.framework.aop.model.ProxyChain;
import easy.framework.core.ClassHelper;
import easy.framework.plugin.AbstractPluginProxy;
import easy.framework.plugin.cache.Cache;
import easy.framework.plugin.cache.CacheHelper;
import easy.framework.plugin.cache.CacheManage;
import easy.framework.plugin.cache.annotation.CacheConfig;
import easy.framework.plugin.cache.annotation.CacheEvict;
import easy.framework.plugin.cache.annotation.Cacheable;
import easy.framework.plugin.cache.annotation.Caching;
import easy.framework.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class CacheAspect extends AbstractPluginProxy{

    private final Lock lock = new ReentrantLock();

    private final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Override
    public List<Class<?>> getTargetClassList() {
        List<Class<?>> targetClassList = new ArrayList<>();
        Set<Class<?>> cacheConfigList = ClassHelper.findClassByAnnotation(CacheConfig.class);
        targetClassList.addAll(cacheConfigList);
        return targetClassList;
    }

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        lock.lock();
        try {
            Object result;
            Class<?> targetClass = proxyChain.getTargetClass();
            Method targetMethod = proxyChain.getMethod();
            Object[] params = proxyChain.getArgs();
            String cacheName = targetClass.getAnnotation(CacheConfig.class).cacheName();
            CacheManage cacheManage = CacheHelper.getCacheManage(targetClass);
            Cache cache = cacheManage.getCache(cacheName);
            List<String> afterEvictKey = new ArrayList<>();
            if(this.existCacheAnnotation(targetMethod)){
                if(targetMethod.isAnnotationPresent(CacheEvict.class)){
                    CacheEvict cacheEvict = targetMethod.getAnnotation(CacheEvict.class);
                    String cacheKey = this.getCacheKey(cacheEvict.key(),targetMethod.getName(),params);
                    CacheEvict.InvocationType type = cacheEvict.type();
                    if(CacheEvict.InvocationType.AFTER.equals(type)){
                        afterEvictKey.add(cacheKey);
                    }else{
                        cache.remove(cacheKey);
                    }
                }
                if(targetMethod.isAnnotationPresent(Caching.class)){
                    Caching caching = targetMethod.getAnnotation(Caching.class);
                    CacheEvict[] evict = caching.evict();
                    if(evict != null && evict.length > 0){
                        for (CacheEvict cacheEvict : evict) {
                            String cacheKey = this.getCacheKey(cacheEvict.key(),targetMethod.getName(),params);
                            if(CacheEvict.InvocationType.AFTER.equals(cacheEvict.type())){
                                afterEvictKey.add(cacheKey);
                            }else{
                                cache.remove(cacheKey);
                            }
                        }
                    }
                }
                if(targetMethod.isAnnotationPresent(Cacheable.class)){
                    Cacheable cacheable = targetMethod.getAnnotation(Cacheable.class);
                    String cacheKey = this.getCacheKey(cacheable.key(),targetMethod.getName(),params);
                    long expiry = cacheable.expiry();
                    if(cache.get(cacheKey) != null){
                        result = cache.get(cacheKey);
                        logger.debug("[easy-mvc-plugin-cache]缓存数据: {}", JsonUtils.toJson(result));
                    }else{
                        result = proxyChain.doChain();
                        if(result != null){
                            cache.put(cacheKey,result,expiry);
                        }
                    }
                }else{
                    result = proxyChain.doChain();
                }
                if(afterEvictKey.size() > 0){
                    cache.remove(afterEvictKey.toArray(new String[afterEvictKey.size()]));
                }
            }else{
                result = proxyChain.doChain();
            }
            return result;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取缓存key: key-params 或者 methodName-params
     * @param key  用户自定义key
     * @param methodName 方法名
     * @param params 方法参数列表
     * @return
     */
    private String getCacheKey(String key,String methodName,Object[] params){
        String cacheKey = StringUtils.isBlank(key) ? methodName : key;
        if(params != null && params.length > 0){
            cacheKey = cacheKey + "-" +Arrays.toString(params);
        }
        return cacheKey;
    }

    private boolean existCacheAnnotation(Method targetMethod){
        if(targetMethod.isAnnotationPresent(CacheEvict.class)){
            return true;
        }else if(targetMethod.isAnnotationPresent(Caching.class)){
            return true;
        }else if(targetMethod.isAnnotationPresent(Cacheable.class)){
            return true;
        }else{
            return false;
        }
    }
}
