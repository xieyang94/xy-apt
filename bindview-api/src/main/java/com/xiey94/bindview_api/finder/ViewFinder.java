package com.xiey94.bindview_api.finder;

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
public class ViewFinder implements Finder {
    @Override
    public Context getContext(Object source) {
        return ((View) source).getContext();
    }

    @Override
    public View findView(Object source, int id) {
        return ((View) source).findViewById(id);
    }
}
