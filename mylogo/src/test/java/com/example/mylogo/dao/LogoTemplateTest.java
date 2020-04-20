package com.example.mylogo.dao;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LogoTemplateTest {

    @Test
    void createLogo() {
        // 先启动 logo Builder
        HashMap<String, Object> m = new HashMap<>();
        LogoTemplate tmp = new LogoTemplate();
        // 先注释掉 redis相关代码
        tmp.createLogo(m);

    }
}