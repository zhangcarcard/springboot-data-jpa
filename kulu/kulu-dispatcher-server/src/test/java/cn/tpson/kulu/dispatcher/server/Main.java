package cn.tpson.kulu.dispatcher.server;

import cn.tpson.kulu.common.util.MessageDigestUtils;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhangka in 2018/08/08
 */
public class Main {
    public static void main(String[] args) {
        /*System.out.println(Integer.MAX_VALUE);
        for (int i = 0; i < 10; ++i) {
            System.out.println(Objects.hashCode(MessageDigestUtils.MD5(String.valueOf(ThreadLocalRandom.current().nextInt()))));
        }*/
/*
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(MessageDigestUtils.MD5("a").hashCode());
        System.out.println(MessageDigestUtils.MD5("aa").hashCode());
        System.out.println(MessageDigestUtils.MD5("aaa").hashCode());
        System.out.println("aaa".hashCode());
        System.out.println(31 * (31 * 97 + 97) + 97);
        System.out.println(Objects.hashCode("aaa"));
        System.out.println(Math.pow(2, 31));
        System.out.println(Math.sin(Math.PI / 6));*/
        System.out.println(MessageDigestUtils.MD5("123"));
    }
}
