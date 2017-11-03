# easy-mvc-plugin-cache
缓存插件

### easy-mvc.properties文件中增加如下配置：
```java

#缓存管理器配置项(可选,默认local,其他待实现): redis/guava/local(concurrentHashMap)
easy.plugin.cache.manager=local

```
### pom.xml引入插件
``` java
  <dependency>
    <groupId>org.lmy</groupId>
    <artifactId>easy-mvc-plugin-cache</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
```

### 使用示例

@CacheConfig、@Cacheable、@CacheEvict、@Caching

```java
/**
* @CacheConfig 标识启用缓存配置,cacheName标识缓存名称
*/
@CacheConfig(cacheName = "USER_DATA")
public class UserServiceImpl implements UserService{  
  
  /**
  * 缓存数据(userId = 100)
  * key标识缓存前缀,不填写的话,默认取方法名称,如：userInfo
  * expiry标识缓存过期时间(毫秒),-1标识永不过期
  * 最终的缓存cacheKey = key + "-" + 所有方法参数列表
  * 如: userInfo-100 或者 user:info-100 将会被缓存
  */
  @Cacheable
  @Cacheable(key = "user:info",expiry = ExpiryTime.ONE_HOUR)
  public void userInfo(String userId){
      ...
  }
    
 /**
  * 清除单个缓存(userId = 100)
  * key标识缓存前缀,不填写的话,默认取方法名称,如：evictSingleCache
  * type为清除缓存时机,可选值为InvocationType.BEFORE(方法执行前,默认值)或者InvocationType.AFTER(方法执行后)
  * 最终的缓存cacheKey = key + "-" + 所有方法参数列表
  * 如: evictSingleCache-100 或者 user:info-100 将会被清除掉
  */ 
  @CacheEvict(key = "user:info",type = CacheEvict.InvocationType.BEFORE)
  public void evictSingleCache(String userId){
      ...
  }
  
  /**
  * 清除多个缓存(同evictSingleCache)
  * key标识缓存前缀,不填写的话,默认取方法名称,如：evictMultiCache(此时每个待清除的缓存应该自定义key值)
  * 如:user:info-100 和 shoppingCart:info-100 将会被清除掉
  */
  @Caching(
        evict = {
           @CacheEvict(key = "user:info",type = CacheEvict.InvocationType.BEFORE),
           @CacheEvict(key = "shoppingCart:info",type = CacheEvict.InvocationType.AFTER)
        }
  )
  public void evictMultiCache(String userId){
       ...
  }
    
}

/**
 * 单位(毫秒)
 */
public class ExpiryTime {
    /**
     * 永不过期
     */
    public static final long NEVER = -1;
    /**
     * 一分钟
     */
    public static final long ONE_MINUTE = 60 * 1000;
    /**
     * 一小时
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    /**
     * 一天
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;
}

```
