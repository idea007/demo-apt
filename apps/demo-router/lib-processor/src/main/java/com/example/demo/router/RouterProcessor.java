package com.example.demo.router;


import com.example.demo.router.annotations.Router;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedOptions({"defaultRouterScheme", "moduleName"})
public class RouterProcessor extends AbstractProcessor {

    private final String CLASS_PACKAGE = "com.example.demo.router";
    private final String MAPPER_COLLECTOR = "MapperCollector";
    private final String ROUTER_COLLECTOR = "RouterCollector";

    private final ClassName RouterManager_Class = ClassName.get(CLASS_PACKAGE, "RouterManager");
    private final ClassName IPagedMapperCollector_Class = ClassName.get(CLASS_PACKAGE, "IPagedMapperCollector");


    private Messager messager;
    private Filer filer;
    private String defaultRouterScheme;
    private String moduleName;
    private String mapperCollectorName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        defaultRouterScheme = processingEnv.getOptions().get("defaultRouterScheme");
        moduleName = processingEnv.getOptions().get("moduleName");

        if (moduleName == null || moduleName.trim().length() <= 0) {
            mapperCollectorName = MAPPER_COLLECTOR;
        } else {
            mapperCollectorName = MAPPER_COLLECTOR + "_" + moduleName;
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "------ init " + " defaultRouterScheme=" + defaultRouterScheme + " mapperCollectorName=" + mapperCollectorName);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Router.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        generateRouterCollector();
        generateRouterItemGather(mapperCollectorName, roundEnv);
        return true;
    }

    /**
     * 生成 router 收集器
     * 壳工程才需要生成，别的业务模块必须设置 moduleName，壳工程 moduleName 必须是 null
     */
    private void generateRouterCollector() {
        if (!(moduleName == null || moduleName.trim().length() <= 0)) {
            return;
        }
        // 方法
        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.STATIC, Modifier.FINAL);
        initMethod.addStatement(mapperCollectorName + ".init()");

        // 属性
        FieldSpec.Builder fieldBuild = FieldSpec.builder(String.class, "DEFAULT_SCHEME")
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                .initializer("$S", defaultRouterScheme);

        // 类定义
        TypeSpec routerGatherClass = TypeSpec.classBuilder(ROUTER_COLLECTOR)
                .addModifiers(Modifier.FINAL)
                .addField(fieldBuild.build())
                .addMethod(initMethod.build())
                .build();

        try {
            JavaFile.builder(CLASS_PACKAGE, routerGatherClass)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    /**
     * 每个模块生成 router 收集器
     */
    private void generateRouterItemGather(String className, RoundEnvironment roundEnv) {
        //表示所有的打了 @Router 标的类
        Set<? extends Element> routerElements = roundEnv.getElementsAnnotatedWith(Router.class);


        // 开始生成 MapperCollector 的 init 方法
        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init").addModifiers(Modifier.STATIC, Modifier.FINAL);

        initMethod.addCode("\n");
        initMethod.addStatement("$T<$T> segments", List.class, Segment.class);
        initMethod.addCode("\n");

        // 用于记录所有的要生成的 Mapper，一条路由可能对应多个 Mapper
        ArrayList<Mapper> mappers = new ArrayList<>();

        for (Element element : routerElements) {
            generateMapper(element.getAnnotation(Router.class), element, initMethod, mappers, moduleName);
        }

        Collections.sort(mappers, new Comparator<Mapper>() {
            @Override
            public int compare(Mapper m1, Mapper m2) {
                return m2.priority - m1.priority;
            }
        });
        for (Mapper mapper : mappers) {
            writeMapperToCode(mapper, initMethod);
        }

        // 类定义
        TypeSpec routerGatherClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.FINAL)
                .addSuperinterface(IPagedMapperCollector_Class)
                .addMethod(initMethod.build())
                .build();

        try {
            JavaFile.builder(CLASS_PACKAGE, routerGatherClass)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    private void writeMapperToCode(Mapper mapper, MethodSpec.Builder method) {

        method.addComment(String.format("=============== mapping  %s ===============", mapper.target.reflectionName()));
        method.addComment(String.format("url is %s", mapper.originalUri));
        method.addCode("\n");

        List<Segment> path = mapper.segments;
        if (path.size() == 0) {
            // path = Collections.emptyList(); 或者 rPath = Collections.emptyList();
            method.addStatement("segments" + " = $T.emptyList()", ClassName.get(Collections.class));
        } else {
            // 生成这样的代码：path = Arrays.asList(new Segment(raw, key, value, type", isParam, reg),...);
            // 或者 rPath
            method.addCode("segments" + " = $T.asList(", ClassName.get(Arrays.class));
            for (int i = 0; i < path.size(); i++) {
                Segment segment = path.get(i);
                method.addCode(
                        String.format("new $T($S, $S, $S, $S, %s)%s", String.valueOf(segment.isParameter), i == path.size() - 1 ? "" : ","),
                        Segment.class, segment.original, segment.key, segment.value, segment.type);
            }
            method.addCode(");");
        }
        method.addCode("\n");

        // addMapper
        method.addStatement("$T.instance.addMapper(new $T($S, $S,$S,segments,$T.class, " + mapper.priority + ", $S))",
                RouterManager_Class, Mapper.class, mapper.originalUri, mapper.scheme, mapper.host, mapper.target, mapper.module);

        method.addCode("\n\n");
    }


    private void generateMapper(Router router, Element element, MethodSpec.Builder initMethod, ArrayList<Mapper> mappers, String moduleName) {
        String[] routerValues = router.value();

        //首先获取到声明的所有 paramReg 正则
        HashMap<String, String> regs = new HashMap<>(4, 1);

        for (String routerValue : routerValues) {
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }
            ClassName className = ClassName.get((TypeElement) element);
            String tempModuleName;
            if (!isEmpty(router.module().trim())) {
                tempModuleName = router.module().trim();
            } else {
                tempModuleName = moduleName;
            }
            // 解析 routerValue
            String scheme = null;
            String host;
            String pathString;
            String queryString = null;

            // scheme
            int i = routerValue.indexOf("://");
            if (i > 0) {
                scheme = routerValue.substring(0, routerValue.indexOf("://"));
            }

            // host + path 的开头 index
            int schemeSpecificPartStartIndex = getSchemeSpecificPartStartIndex(routerValue, scheme);
            // host + path 的结尾 index（不包含）
            int schemeSpecificPartEndIndex = routerValue.indexOf("?");
            boolean hasQuery = schemeSpecificPartEndIndex > 0;
            if (!hasQuery) {
                schemeSpecificPartEndIndex = routerValue.length();
            }

            pathString = routerValue.substring(schemeSpecificPartStartIndex, schemeSpecificPartEndIndex).replaceAll("/+", "/");

            host = pathString.split("/")[0];

            if (host == null || host.length() == 0) {
                continue;
            }

            // 将头尾的 / 删除
            pathString = pathString.substring(host.length()).replaceAll("(^/+)|(/+$)", "");

            if (hasQuery) {
                queryString = routerValue.substring(schemeSpecificPartEndIndex + 1);
            }

            // 根据 pathString 生成一串 Segment
            List<Segment> segmentArrayList = new ArrayList<>();
            if (!isEmpty(pathString)) {
                String[] segments = pathString.split("/");
                for (String s : segments) {
                    // 创建 segment 对象
                    Segment segment = createSegment(s, regs);
                    if (segment == null) {
                        continue;
                    }
                    segmentArrayList.add(segment);
                }
            }


            // 生成一个 mapper ,添加到 mappers 中
            Mapper mapper = new Mapper(routerValue, scheme, host, segmentArrayList, className, router.priority(), tempModuleName);
            mappers.add(mapper);
        }

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Segment 的正则匹配表达式，匹配的是{key?:type=value}
     */
    private static final String SEGMENT_REG =
            "\\{" +
                    "(?<key>[^=:{}]+)" +
                    "(:(?<type>float|int|long|string|boolean|short|double|byte|char))?" +
                    "(=(?<value>[^=:{}]+))?" +
                    "\\}";
    private static final Pattern SEGMENT_PATTERN = Pattern.compile(SEGMENT_REG);


    /**
     * 生成一个 Segment 对象
     *
     * @param raw       Segment 在模板里面的原始声明，比如 scheme://answer/{host:int} 第一个 Segment 的 raw 是 {host:int}
     * @param paramRegs 要匹配的正则们
     */
    static Segment createSegment(String raw, HashMap<String, String> paramRegs) {
        // 如果是符合正则，那么就认为是一个参数化的 Segment，否则就一普通的 Segment
        if (raw.matches(SEGMENT_REG)) {
            Matcher matcher = SEGMENT_PATTERN.matcher(raw);
            if (matcher.find()) {
                @SuppressWarnings("NewApi") String key = matcher.group("key");
                @SuppressWarnings("NewApi") String type = matcher.group("type");
                @SuppressWarnings("NewApi") String value = matcher.group("value");

                String regexp = paramRegs.get(key);

                if (regexp != null && regexp.length() > 0 && value != null && value.length() > 0) {
                    if (!value.matches(regexp)) {
                        return null;
                    }
                }
                type = convertType(type);
                if (checkType(value, type)) {
                    return new Segment(raw, key, value, type, true);
                } else {
                    return null;
                }
            }
        } else {
            return new Segment(raw, raw, raw, "string", false);
        }
        return null;
    }


    /**
     * host + path 的开头 index，比如 http://scheme.com/answer 返回的就是 indexOf("scheme.com/answer")
     */
    private static int getSchemeSpecificPartStartIndex(String uri, String scheme) {
        int index;
        if (isEmpty(scheme)) {

            //允许 ://host 或者 /host 或者 host开头
            if (uri.startsWith("://")) {
                index = 3;
            } else if (uri.startsWith("//")) {
                index = 2;
            } else if (uri.startsWith("/")) {
                index = 1;
            } else {
                index = 0;
            }
        } else {
            index = scheme.length() + 3;
        }
        return index;
    }

    /**
     * 不正确的类型返回 string
     */
    private static String convertType(String type) {
        if (type == null || type.trim().length() == 0) {
            type = "string";
        }
        type = type.trim().toLowerCase();
        if (!isValidType(type)) {
            type = "string";
        }
        return type;
    }

    private static String[] ALL_TYPES = new String[]{"int", "long", "float", "short", "double", "byte", "boolean", "char", "string"};

    private static boolean isValidType(String type) {
        for (String s : ALL_TYPES) {
            if (s.equals(type)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static boolean checkType(String value, String type) {
        if (value == null) {
            return true;
        }
        try {
            switch (type) {
                case "int":
                    Integer.parseInt(value);
                    break;
                case "long":
                    Long.parseLong(value);
                    break;
                case "float":
                    Float.parseFloat(value);
                    break;
                case "short":
                    Short.parseShort(value);
                    break;
                case "double":
                    Double.parseDouble(value);
                    break;
                case "byte":
                    Byte.parseByte(value);
                    break;
                case "boolean":
                    assert "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
                    break;
                case "char":
                    assert value.length() == 1;
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException | AssertionError e) {
            return false;
        }
        return true;
    }


}
