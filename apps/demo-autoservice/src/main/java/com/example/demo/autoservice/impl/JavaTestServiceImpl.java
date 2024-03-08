package com.example.demo.autoservice.impl;

import com.example.demo.autoservice.inter.ITestService;
import com.google.auto.service.AutoService;

@AutoService(ITestService.class)
public class JavaTestServiceImpl implements ITestService {
    @Override
    public String sayHello() {
        return "say hello from " + this.getClass().getSimpleName();
    }
}
