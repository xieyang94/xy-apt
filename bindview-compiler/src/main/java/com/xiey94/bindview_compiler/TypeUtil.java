package com.xiey94.bindview_compiler;

import com.squareup.javapoet.ClassName;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_compiler.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class TypeUtil {
    public static final ClassName FINDER = ClassName.get("com.xiey94.bindview_api.finder", "Finder");
    public static final ClassName INJECTOR = ClassName.get("com.xiey94.bindview_api", "Injector");
    public static final ClassName ONCLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName LONGCLICK_LISTENER = ClassName.get("android.view", "View", "OnLongClickListener");
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
    public static final ClassName CONTENT_VIEW=ClassName.get("android.support.v7.app","AppCompatActivity","setContentView");
}
