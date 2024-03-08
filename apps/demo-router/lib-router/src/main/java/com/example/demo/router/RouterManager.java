package com.example.demo.router;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.demo.router.model.ResolveResult;
import com.example.demo.router.utils.UriUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName RouterManager
 * @Des
 * @Author lipengfei
 * @Date 2023/8/7 19:46
 */
public enum RouterManager {
    instance;

    static {
        RouterCollector.init();
    }

    final List<Mapper> mappers = new ArrayList<>();

    public void addMapper(Mapper item) {
        mappers.add(item);
    }

    public ResolveResult resolve(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return resolve(Uri.parse(url));
    }

    private ResolveResult resolve(Uri uri) {
        if (null == uri) {
            return null;
        }
        uri = completionUri(uri);
        Mapper themMapper = null;
        for (int i = 0; i < mappers.size(); i++) {
            themMapper = mappers.get(i);
            if (!isSchemeAndHostMatch(themMapper, uri)) {
                continue;
            }
            if (!isSegmentsMatch(themMapper.segments, uri)) {
                continue;
            }
            return new ResolveResult(uri.toString(), convertBundle(themMapper.segments, uri), themMapper.targetClass, themMapper.module);
        }
        return null;
    }

    /**
     * 补全 uri
     */
    private Uri completionUri(Uri uri) {
        if (TextUtils.isEmpty(uri.getScheme()) && !TextUtils.isEmpty(RouterCollector.DEFAULT_SCHEME)) {
            uri = UriUtils.fixUrlScheme(RouterCollector.DEFAULT_SCHEME, uri);
        }
        return uri;
    }

    private boolean isSchemeAndHostMatch(Mapper mapper, Uri uri) {
        return Objects.equals(mapper.scheme, uri.getScheme()) && Objects.equals(mapper.host, uri.getHost());
    }

    private boolean isSegmentsMatch(@NonNull List<Segment> segments, Uri uri) {
        if (segments.size() != uri.getPathSegments().size()) {
            return false;
        }
        for (int i = 0; i < segments.size(); i++) {
            Segment segment = segments.get(i);
            if (!segment.isParameter) {
                if (!segment.key.equals(uri.getPathSegments().get(i))) {
                    return false;
                }
            } else {
                if (!isSegmentMatch(uri.getPathSegments().get(i), segment)) {
                    return false;
                }
            }
        }
        return true;

    }

    private Bundle convertBundle(List<Segment> segments, Uri uri) {
        Bundle bundle = new Bundle();
        Segment segment;
        for (int i = 0; i < segments.size(); i++) {
            segment = segments.get(i);

            if (segment.isParameter) {
                String value = uri.getPathSegments().get(i);
                putExtra(bundle, segment.key, value, segment.type);
            }
        }
        return bundle;
    }

    private static void putExtra(Bundle bundle, String key, String value, String type) {
        // 编译期已经确保了 type 不为空
        switch (type) {
            case "int":
                bundle.putInt(key, Integer.parseInt(value));
                break;
            case "long":
                bundle.putLong(key, Long.parseLong(value));
                break;
            case "float":
                bundle.putFloat(key, Float.parseFloat(value));
                break;
            case "boolean":
                bundle.putBoolean(key, Boolean.parseBoolean(value));
                break;
            case "short":
                bundle.putShort(key, Short.parseShort(value));
                break;
            case "double":
                bundle.putDouble(key, Double.parseDouble(value));
                break;
            case "byte":
                bundle.putByte(key, Byte.parseByte(value));
                break;
            case "char":
                bundle.putChar(key, value.charAt(0));
                break;
            default:
                bundle.putString(key, value);
                break;
        }
    }


    private boolean isSegmentMatch(String originalStr, Segment segment) {
        if (TextUtils.isEmpty(originalStr)) {
            return false;
        }
        if (TextUtils.isEmpty(segment.type) || "string".equals(segment.type)) {
            // 如果是 string ，直接返回 true
            return true;
        }
        try {
            switch (segment.type) {
                case "int":
                    Integer.parseInt(originalStr);
                    break;
                case "long":
                    Long.parseLong(originalStr);
                    break;
                case "float":
                    Float.parseFloat(originalStr);
                    break;
                case "short":
                    Short.parseShort(originalStr);
                    break;
                case "double":
                    Double.parseDouble(originalStr);
                    break;
                case "byte":
                    Byte.parseByte(originalStr);
                    break;
                case "boolean":
                    return "true".equalsIgnoreCase(originalStr) || "false".equalsIgnoreCase(originalStr);
                case "char":
                    return originalStr.length() == 1;
                default:
                    // 未识别的类型
                    return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
