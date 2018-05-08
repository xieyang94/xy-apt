package com.xiey94.bindview_api.finder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_api.finder.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class ActivityFinder implements Finder {
    @Override
    public Context getContext(Object source) {
        return (Activity) source;
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity) source).findViewById(id);
    }
}
