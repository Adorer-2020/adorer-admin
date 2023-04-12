package space.adorer.admin.annotation;

import java.lang.annotation.*;

/**
 * 接口限流注解
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 上午11:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds() default 1;

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount() default 5;
}
