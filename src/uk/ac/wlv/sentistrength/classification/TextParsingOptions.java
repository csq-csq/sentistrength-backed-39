// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   TextParsingOptions.java

package uk.ac.wlv.sentistrength.classification;


/**
 * Description:&nbsp; 文本分析选项类
 *
 * @author 201850003
 * date: 2023/03/06
 */
public class TextParsingOptions {

    /**
     * Description: <br>&nbsp; 是否包括标点符号
     */
    public boolean bgIncludePunctuation;
    /**
     * Description: <br>&nbsp; N-Gram算法滑动窗口大小
     */
    public int igNgramSize;
    /**
     * Description: <br>&nbsp; 是否使用翻译
     */
    public boolean bgUseTranslations;
    /**
     * Description: <br>&nbsp; 是否增添强调代码
     */
    public boolean bgAddEmphasisCode;

    public TextParsingOptions() {
        bgIncludePunctuation = true;
        igNgramSize = 1;
        bgUseTranslations = true;
        bgAddEmphasisCode = false;
    }
}
