package com.example.demo.router;

import com.squareup.javapoet.ClassName;

import java.util.List;

/**
 * @ClassName RouteItem
 * @Des 映射信息
 * @Author lipengfei
 * @Date 2023/8/7 19:24
 */
public class Mapper {
    public final String originalUri;
    public final String scheme;
    public final String host;
    public final List<Segment> segments;
    public final ClassName target;
    public Class targetClass;
    public final int priority;
    public final String module;

    public Mapper(String originalUri, String scheme, String host, List<Segment> segments, ClassName target, Class targetClass, int priority, String module) {
        this.originalUri = originalUri;
        this.scheme = scheme;
        this.host = host;
        this.segments = segments;
        this.target = target;
        this.priority = priority;
        this.module = module;
        this.targetClass = targetClass;
    }

    public Mapper(String originalUri, String scheme, String host, List<Segment> segments, ClassName target, int priority, String module) {
        this(originalUri, scheme, host, segments, target, null, priority, module);
    }

    public Mapper(String originalUri, String scheme, String host, List<Segment> segments, Class targetClass, int priority, String module) {
        this(originalUri, scheme, host, segments, null, targetClass, priority, module);
    }
}
