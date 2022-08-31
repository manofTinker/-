package com.lishuai.highconcurrentsecondkill.aspect;

import java.lang.annotation.*;

/**
 * @author lishuai
 * @date 2022/8/28
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerLock {
    String description() default "";
}
