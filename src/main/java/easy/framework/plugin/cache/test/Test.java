package easy.framework.plugin.cache.test;

import easy.framework.plugin.cache.annotation.CacheConfig;
import easy.framework.plugin.cache.annotation.CacheEvict;
import easy.framework.plugin.cache.annotation.Cacheable;
import easy.framework.plugin.cache.annotation.Caching;

/**
 * @author limengyu
 * @create 2017/11/1
 */
@CacheConfig(cacheName = "test")
public class Test {

    @Cacheable(key = "USER#userId#age")
    public void cache(String userId,String age){

    }

    @Caching(
        evict = {
           @CacheEvict(key = "USER#userId",type = CacheEvict.InvocationType.AFTER)
        }
    )
    public void evict(String userId){

    }

    public static void main(String[] args) {

    }
}
