package com.xiey94.bindview_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xieyang on 2018/5/7.
 * Email: xiey94@qq.com
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ContentView {
    int value();
}
