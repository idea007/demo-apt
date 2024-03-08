package com.example.demo.router;

import org.junit.Test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        String str1="content://com.example.hello.HelloFileProvider/my_musics/0x0000600001/extend.json";
        String str2="scheme://test_clazz/{id}/accountid/xxxx";
        URI uri = URI.create(str1);
        System.out.println("hhh");

        try {
            URL url= new URL(str1);
            System.out.println("hhh");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}