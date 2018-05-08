package com.xiey94.bindview_compiler;

import com.xiey94.bindview_annotation.ContentView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by xieyang on 2018/5/7.
 * Email: xiey94@qq.com
 */

public class ContentViewClass {
    private int id;

    public ContentViewClass(Element element) {
        if (element.getKind()!= ElementKind.CLASS){
            throw new IllegalArgumentException(String.format("Must be annotated with @%s "+ ContentView.class.getSimpleName()));
        }
        TypeElement methodElement= (TypeElement) element;
        ContentView contentView=methodElement.getAnnotation(ContentView.class);
        id=contentView.value();
        if (id<0){
            throw new IllegalArgumentException(String.format("Must be valid ids with @% "+ContentView.class.getSimpleName()));
        }
    }

    public int getId() {
        return id;
    }
}
