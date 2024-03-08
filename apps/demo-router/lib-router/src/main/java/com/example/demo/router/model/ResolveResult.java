package com.example.demo.router.model;

import android.os.Bundle;

/**
 * @ClassName ResolveResult
 * @Des
 * @Author lipengfei
 * @Date 2023/8/8 15:27
 */
public class ResolveResult {

    public final String originalUrl;
    public final Bundle bundle;
    public final Class target;
    public final String module;

    public ResolveResult(String originalUrl, Bundle bundle, Class target, String module) {
        this.originalUrl = originalUrl;
        this.bundle = bundle;
        this.target = target;
        this.module = module;
    }
}
