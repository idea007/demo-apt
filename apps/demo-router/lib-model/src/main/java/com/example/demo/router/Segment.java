package com.example.demo.router;

/**
 * @ClassName Segment
 * @Des 路径片段，类似于 URI 的 segment
 * @Author lipengfei
 * @Date 2023/8/7 19:17
 */
public class Segment {
    public final String original;
    public final String key;
    public final String value;
    /**
     * 描述参数类型：java 基本类型，不可能为空
     */
    public final String type;
    public final boolean isParameter;

    public Segment(String original, String key, String value, String type, boolean isParameter) {
        this.original = original;
        this.key = key;
        this.value = value;
        this.type = type;
        this.isParameter = isParameter;
    }
}
