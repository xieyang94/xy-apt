package com.xiey94.bindview_compiler;

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
public class OnClickMethod {
    private Name mMethodName;

    private int[] ids;

    public OnClickMethod(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(String.format("Only method can be annotated witg @%s" + OnClick.class.getSimpleName()));
        }
        ExecutableElement methodElement = (ExecutableElement) element;
        mMethodName = methodElement.getSimpleName();
        ids = methodElement.getAnnotation(OnClick.class).value();
        if (ids == null) {
            throw new IllegalArgumentException(String.format("Must be valid ids for @%s" + OnClick.class.getSimpleName()));
        } else {
            for (int id : ids) {
                if (id < 0) {
                    throw new IllegalArgumentException(String.format("Must be valid ids for @%s" + OnClick.class.getSimpleName()));
                }
            }
        }

        List<? extends VariableElement> parameters = methodElement.getParameters();
        if (parameters.size() != 1) {
            throw new IllegalArgumentException(String.format("The method annotated with @%s must have one paraments", OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return mMethodName;
    }

    public int[] getIds() {
        return ids;
    }
}
