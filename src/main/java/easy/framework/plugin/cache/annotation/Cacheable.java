package easy.framework.plugin.cache.annotation;

import easy.framework.plugin.cache.common.ExpiryTime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存key：${key}-参数列表(参数列表是后台自动添加上)
 * @author limengyu
 * @create 2017/11/01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
	String key() default "";
	long expiry() default ExpiryTime.NEVER;
}
