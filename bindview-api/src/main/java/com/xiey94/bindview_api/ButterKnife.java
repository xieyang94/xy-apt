package com.xiey94.bindview_api;

import android.app.Activity;
import android.view.View;

import com.xiey94.bindview_api.finder.ActivityFinder;
import com.xiey94.bindview_api.finder.Finder;
import com.xiey94.bindview_api.finder.ViewFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_api.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class ButterKnife {
    private ButterKnife() {
        throw new AssertionError("No .instances");
    }

    private static final ActivityFinder ACTIVITY_FINDER = new ActivityFinder();

    private static final ViewFinder VIEW_FINDER = new ViewFinder();

    private static Map<String, Injector> FINDER_MAP = new HashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity, ACTIVITY_FINDER);
    }

    public static void bind(View view) {
        bind(view, view);
    }

    public static void bind(Object host, View view) {
        bind(host, view, VIEW_FINDER);
    }

    /**
     * 获取目标类
     */
    public static void bind(Object host, Object source, Finder finder) {
        String className = host.getClass().getName();
        try {
            Injector injector = FINDER_MAP.get(className);
            if (injector == null) {
                Class<?> finderClass = Class.forName(className + "$$Injector");
                injector = (Injector) finderClass.newInstance();
                FINDER_MAP.put(className, injector);
            }
            injector.inject(host, source, finder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
