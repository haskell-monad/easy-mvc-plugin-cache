package easy.framework.plugin.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author limengyu
 * @create 2017/11/01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Caching {

    /**
     * 清空多个缓存
     * @return
     */
    CacheEvict[] evict() default {};
}
