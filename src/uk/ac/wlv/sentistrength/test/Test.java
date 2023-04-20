// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   Test.java

package uk.ac.wlv.sentistrength.test;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Description:&nbsp; 测试类
 *
 * @author 201850003
 * date: 2023/03/06
 */
public class Test {

    /**
     * Description:<br>&nbsp; 默认构造函数
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public Test() {
    }

    /**
     * Description:<br>&nbsp;测试输入的字符串是否存在非 ASCII 编码字符并打印结果。
     *
     * @param args main方法参数数组
     * @author 201850003
     * date: 2023/03/06
     */
    public static void main(String[] args) {
        Test test = new Test();
        test.runTest(args);
    }

    /**
     * @param args 参数数组
     * @author 201850003
     * date: 2023/04/20
     */
    public void runTest(String[] args){
        CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
        String test = "R\351al";
        System.out.println((new StringBuilder(String.valueOf(test))).append(" isPureAscii() : ").append(asciiEncoder.canEncode(test)).toString());
        for (int i = 0; i < test.length(); i++)
            if (!asciiEncoder.canEncode(test.charAt(i)))
                System.out.println((new StringBuilder(String.valueOf(test.charAt(i)))).append(" isn't Ascii() : ").toString());

        test = "Real";
        System.out.println((new StringBuilder(String.valueOf(test))).append(" isPureAscii() : ").append(asciiEncoder.canEncode(test)).toString());
        test = "a\u2665c";
        System.out.println((new StringBuilder(String.valueOf(test))).append(" isPureAscii() : ").append(asciiEncoder.canEncode(test)).toString());
        for (int i = 0; i < test.length(); i++)
            if (!asciiEncoder.canEncode(test.charAt(i)))
                System.out.println((new StringBuilder(String.valueOf(test.charAt(i)))).append(" isn't Ascii() : ").toString());

        System.out.println((new StringBuilder("Encoded Word = ")).append(URLEncoder.encode(test)).toString());
    }
}
