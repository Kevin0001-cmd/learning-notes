package com.kuang.util;

import org.junit.Test;

import java.util.UUID;

@SuppressWarnings("all")
public class IDutils {

    public static String getId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    @Test
    public void test(){
        System.out.println(getId());
        System.out.println(getId());
        System.out.println(getId());
    }
}
