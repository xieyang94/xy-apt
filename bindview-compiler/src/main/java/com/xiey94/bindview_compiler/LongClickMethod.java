package com.xiey94.bindview_compiler;

import com.xiey94.bindview_annotation.LongClick;
import com.xiey94.bindview_annotation.OnClick;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_compiler.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class LongClickMethod {
    private int[] ids;
    private Name methodName;

    public LongClickMethod(Element element){
        if (element.getKind()!= ElementKind.METHOD){
            throw new IllegalArgumentException(String.format("Must be annotated with @%s "+ LongClick.class.getSimpleName()));
        }

        ExecutableElement methodElement= (ExecutableElement) element;
        methodName=methodElement.getSimpleName();
        ids=methodElement.getAnnotation(LongClick.class).value();
        if (ids==null){
            throw new IllegalArgumentException(String.format("Must be valid ids with @%s "+LongClick.class.getSimpleName()));
        }else {
            for (int id : ids) {
                if (id<0){
                    throw new IllegalArgumentException(String.format("Must be valid ids with @% "+LongClick.class.getSimpleName()));
                }
            }
        }

        List<? extends VariableElement> parameters = methodElement.getParameters();
        if (parameters.size() != 1) {
            throw new IllegalArgumentException(String.format("The method annotated with @%s must have one paraments", OnClick.class.getSimpleName()));
        }
    }

    public int[] getIds() {
        return ids;
    }

    public Name getMethodName() {
        return methodName;
    }
}
