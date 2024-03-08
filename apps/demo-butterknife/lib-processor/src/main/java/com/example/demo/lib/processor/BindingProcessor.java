package com.example.demo.lib.processor;

import com.example.demo.lib.annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class BindingProcessor extends AbstractProcessor {

    // 返回用于创建新源、类或辅助文件的文件管理器。
    Filer filer;
    // 返回用于报告错误、警告和其他通知的消息发送器。
    Messager messager;

    /**
     * ProcessingEnvironment :注释处理工具框架将为注释处理器提供实现该接口的对象，以便处理器可以使用框架提供的设施来编写新文件、报告错误消息以及查找其他实用程序。
     *
     * @param processingEnv 访问设施的环境工具框架,提供给处理器
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    /**
     * 一个抽象注释处理器，旨在方便大多数具体注释处理器的超类
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    /**
     * 注解处理器实际处理方法，一般要求子类实现该抽象方法，你可以在在这里写你的扫描与处理注解的代码，以及生成Java文件。其中参数RoundEnvironment ，
     * 可以让你查询出包含特定注解的被注解元素，后面我们会看到详细的内容。
     *
     * @param set              the annotation types requested to be processed
     * @param roundEnvironment environment for information about the current and prior round
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        double preTime = System.nanoTime();
        for (Element element : roundEnvironment.getRootElements()) {
            String packageStr = element.getEnclosingElement().toString();
            String classStr = element.getSimpleName().toString();
            ClassName className = ClassName.get(packageStr, classStr + "Binding");
            messager.printMessage(Diagnostic.Kind.NOTE, "------ packageStr=" + packageStr + " classStr=" + classStr + " className=" + className.canonicalName());
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageStr, classStr), "activity");
            boolean hasBinding = false;
            for (Element enclosedElement : element.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    BindView bindView = enclosedElement.getAnnotation(BindView.class);
                    if (bindView != null) {
                        hasBinding = true;
                        constructorBuilder.addStatement("activity.$N = activity.findViewById($L)",
                                enclosedElement.getSimpleName(), bindView.value());
                    }
                }
            }
            TypeSpec builtClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructorBuilder.build())
                    .build();

            if (hasBinding) {
                try {
                    JavaFile.builder(packageStr, builtClass)
                            .build().writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "------ 耗时：" + (System.nanoTime() - preTime));
        return false;
    }

}
