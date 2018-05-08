package com.xiey94.bindview_api;

import android.support.annotation.UiThread;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_api.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {

        }
    };
}
