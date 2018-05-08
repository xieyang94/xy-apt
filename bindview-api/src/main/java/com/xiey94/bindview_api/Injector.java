package com.xiey94.bindview_api;

import com.xiey94.bindview_api.finder.Finder;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_api.finder.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public interface Injector<T> {
    void inject(T host, Object source, Finder finder);
}
