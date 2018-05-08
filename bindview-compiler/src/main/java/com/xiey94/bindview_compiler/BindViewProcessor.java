package com.xiey94.bindview_compiler;

import com.google.auto.service.AutoService;
import com.xiey94.bindview_annotation.BindView;
import com.xiey94.bindview_annotation.ContentView;
import com.xiey94.bindview_annotation.LongClick;
import com.xiey94.bindview_annotation.OnClick;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @author : xiey
 * @project name : apt.
 * @package name  : com.xiey94.bindview_compiler.
 * @date : 2018/5/6.
 * @signature : do my best.
 * @explain :
 */
@AutoService(javax.annotation.processing.Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;
    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        types.add(LongClick.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();
        processorContentView(roundEnv);
        processBindView(roundEnv);
        processOnClick(roundEnv);
        processLongClick(roundEnv);

        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                annotatedClass.generateFinder().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void processorContentView(RoundEnvironment roundEnv){
        for (Element element : roundEnv.getElementsAnnotatedWith(ContentView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            ContentViewClass method = new ContentViewClass(element);
            annotatedClass.addMethod(method);
        }
    }

    /**
     * 遍历目标RoundEnvironment
     */
    private void processBindView(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
        }
    }

    /**
     * 如果在map中存在就直接用，不存在就new出来放在map里
     */
    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement encloseElement;
        if (element instanceof TypeElement) {
            encloseElement = (TypeElement) element;
        } else {
            encloseElement = (TypeElement) element.getEnclosingElement();
        }
        String fullClassName = encloseElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(encloseElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;


    }

    public void processOnClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addClickMethod(method);
        }
    }

    public void processLongClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(LongClick.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            LongClickMethod method = new LongClickMethod(element);
            annotatedClass.addLongClickMethod(method);
        }
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }


}
