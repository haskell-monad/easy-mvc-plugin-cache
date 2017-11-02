package easy.framework.plugin.cache.annotation;

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
public @interface CacheEvict {
    /**
     * 待清除的key
     * @return
     */
	String key() default "";

    /**
     * 什么时候执行清除
     * @return
     */
	InvocationType type() default InvocationType.BEFORE;


	enum InvocationType {
        /**
         * 目标方法执行之前
         */
		BEFORE,
        /**
         * 目标方法执行之后
         */
        AFTER
	}
}
