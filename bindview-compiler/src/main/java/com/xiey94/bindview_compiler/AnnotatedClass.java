package com.xiey94.bindview_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_compiler.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
public class AnnotatedClass {
    /**
     * 类名
     */
    public TypeElement mClassElement;
    /**
     * 成员变量集合
     */
    public List<BindViewField> mField;
    /**
     * 方法
     */
    public List<OnClickMethod> clickMethod;
    public List<LongClickMethod> longClickMethod;
    private List<ContentViewClass> contentViewClasses;
    /**
     * 元素辅助类
     */
    public Elements mElementUtils;

    public AnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
        this.mField = new ArrayList<>();
        this.clickMethod = new ArrayList<>();
        this.longClickMethod = new ArrayList<>();
        this.contentViewClasses = new ArrayList<>();
    }

    /**
     * 获取当前这个类的全名
     */
    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    /**
     * 添加一个注解成员
     */
    public void addField(BindViewField field) {
        mField.add(field);
    }

    public void addClickMethod(OnClickMethod method) {
        clickMethod.add(method);
    }

    public void addLongClickMethod(LongClickMethod method) {
        longClickMethod.add(method);
    }

    public void addMethod(ContentViewClass method) {
        this.contentViewClasses.add(method);
    }

    /**
     * 输出java文件
     */
    public JavaFile generateFinder() {
        //构建方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtil.FINDER, "finder");

        for (ContentViewClass viewClass : contentViewClasses) {
            methodBuilder.addStatement("host.setContentView($L)", viewClass.getId());
        }

        for (BindViewField field : mField) {
            methodBuilder.addStatement("host.$N=($T)finder.findView(source,$L)", field.getFieldName(), ClassName.get(field.getFieldType()), field.getResId());
        }

        /**
         * 声明Listener
         */
        if (clickMethod.size() > 0) {
            methodBuilder.addStatement("$T listener", TypeUtil.ONCLICK_LISTENER);
        }

        for (OnClickMethod method : clickMethod) {
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ONCLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N(view)", method.getMethodName())
                            .build())
                    .build();
            methodBuilder.addStatement("listener=$L ", listener);
            for (int id : method.getIds()) {
                methodBuilder.addStatement("finder.findView(source,$L).setOnClickListener(listener)", id);
            }
        }

        if (longClickMethod.size() > 0) {
            methodBuilder.addStatement("$T longClickListener", TypeUtil.LONGCLICK_LISTENER);
        }

        for (LongClickMethod method : longClickMethod) {
            TypeSpec longClickListener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.LONGCLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onLongClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.BOOLEAN)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N(view)", method.getMethodName())
                            .addStatement("return false")
                            .build())
                    .build();
            methodBuilder.addStatement("longClickListener=$L ", longClickListener);
            for (int id : method.getIds()) {
                methodBuilder.addStatement("finder.findView(source,$L).setOnLongClickListener(longClickListener)", id);
            }
        }


        String packageName = getPackageName(mClassElement);
        String className = getClassName(mClassElement, packageName);
        ClassName bindClassName = ClassName.get(packageName, className);

        //构建类
        TypeSpec finderClass = TypeSpec.classBuilder(bindClassName.simpleName() + "$$Injector")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJECTOR, TypeName.get(mClassElement.asType())))
                .addMethod(methodBuilder.build())
                .build();
        return JavaFile.builder(packageName, finderClass).build();
    }

    /**
     * 包名
     */
    public String getPackageName(TypeElement type) {
        return mElementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    /**
     * 类名
     */
    public static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}
