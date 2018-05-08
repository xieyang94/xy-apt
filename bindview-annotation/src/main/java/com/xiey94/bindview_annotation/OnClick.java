package com.xiey94.bindview_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_annotation.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[] value();
}
