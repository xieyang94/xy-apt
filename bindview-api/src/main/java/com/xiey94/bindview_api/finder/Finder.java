package com.xiey94.bindview_api.finder;

import android.content.Context;
import android.view.View;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_api.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public interface Finder {
    Context getContext(Object source);

    View findView(Object source, int id);
}
