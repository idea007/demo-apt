package com.example.demo.router.utils;

import android.net.Uri;

/**
 * @ClassName UriUtils
 * @Des TODO
 * @Author lipengfei
 * @Date 2023/8/8 16:36
 */
public class UriUtils {
    private UriUtils() {
    }
    /**
     * 给 url 添加一个 scheme ———— 如果它没有的话
     */
    public static Uri fixUrlScheme(String scheme, Uri uri) {
        // 如果 scheme 是 null，就给它一个默认的 scheme
        return Uri.parse(fixUrlScheme(scheme, uri.toString()));
    }

    /**
     * 给 url 添加一个 scheme ———— 如果它没有的话
     */
    public static String fixUrlScheme(String scheme, String originalUrl) {
        if (originalUrl.indexOf("://") > 0) {
            return originalUrl;
        }
        StringBuilder sb = new StringBuilder();
        if (originalUrl.startsWith("://")) {
            sb.append(scheme).append(originalUrl);
        } else if (originalUrl.startsWith("//")) {
            sb.append(scheme).append(":").append(originalUrl);
        } else if (originalUrl.startsWith("/")) {
            sb.append(scheme).append(":/").append(originalUrl);
        } else {
            sb.append(scheme).append("://").append(originalUrl);
        }
        return sb.toString();
    }

}
