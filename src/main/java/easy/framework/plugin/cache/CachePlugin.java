package easy.framework.plugin.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import easy.framework.plugin.Plugin;
import easy.framework.plugin.cache.common.ConfigConstant;
import easy.framework.plugin.cache.model.Duration;

/**
 * @author limengyu
 * @create 2017/11/2
 */
public class CachePlugin implements Plugin {

    private static final Logger logger = LoggerFactory.getLogger(CachePlugin.class);

    @Override
    public void init() {
        new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory
                        .Builder()
                        .namingPattern("CachePlugin-Thread-%d")
                        .daemon(true)
                        .build()
        ).scheduleWithFixedDelay(() -> {
            try {
                Collection<CacheManage> allCacheManage = CacheHelper.getAllCacheManage();
                if(allCacheManage != null && allCacheManage.size() > 0){
                    Iterator<CacheManage> manageList = allCacheManage.iterator();
                    while (manageList.hasNext()){
                        CacheManage cacheManage = manageList.next();
                        Collection<String> cacheNames = cacheManage.getCacheNames();
                        Iterator<String> cacheNameList = cacheNames.iterator();
                        while(cacheNameList.hasNext()){
                            String cacheName = cacheNameList.next();
                            Cache cache = cacheManage.getCache(cacheName);
                            if(cache == null){
                                continue;
                            }
                            Map<String,Duration> durationMap = cache.getDurationMap();
                            if(durationMap == null || durationMap.size() == 0){
                                continue;
                            }
                            durationMap.forEach((cacheKey,duration) -> {
                                if(duration.isExpire()){
                                    cache.remove(cacheKey);
                                    logger.debug("[CachePlugin]>>>缓存[{}]已过期",cacheKey);
                                }
                            });
                        }
                    }
                }
            }catch (Exception e){
                logger.error("CachePlugin-Thread运行异常",e);
            }
        },300,60,TimeUnit.SECONDS);
    }

    @Override
    public void destroy() {

    }

    @Override
    public String getName() {
        return ConfigConstant.PLUGIN_NAME;
    }

    public static void main(String[] args) throws InterruptedException {
        new CachePlugin().init();
        Thread.sleep(100000);
    }
}
