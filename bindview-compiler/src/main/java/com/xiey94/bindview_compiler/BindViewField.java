package com.xiey94.bindview_compiler;

import com.xiey94.bindview_annotation.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_compiler.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class BindViewField {
    private VariableElement mFieldElement;

    private int mResId;

    public BindViewField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only field can be annotated with @%s", BindView.class.getSimpleName()));
        }
        mFieldElement = (VariableElement) element;
        //获取注解和值
        BindView bindView = mFieldElement.getAnnotation(BindView.class);
        mResId = bindView.value();
        if (mResId < 0) {
            throw new IllegalArgumentException(String.format("values() in %s for field % is nor vald", BindView.class.getSimpleName(), mFieldElement.getSimpleName()));
        }
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public int getResId() {
        return mResId;
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }

}
