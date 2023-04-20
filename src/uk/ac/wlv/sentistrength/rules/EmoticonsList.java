// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   EmoticonsList.java

package uk.ac.wlv.sentistrength.rules;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;

import java.io.*;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions

/**
 * 带有相关强度（正或负2）的表情符号列表（EmoticonLookupTable）
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class EmoticonsList {

    /**
     * 表情符号表
     */
    private String[] sgEmoticon;
    /**
     * 表情符号强度
     */
    private int[] igEmoticonStrength;
    /**
     * 表情符号计数
     */
    private int igEmoticonCount;
    /**
     * 表情符号数上限
     */
    private int igEmoticonMax;

    /**
     * 初始化表情列表的计数器，置为0
     */
    public EmoticonsList() {
        igEmoticonCount = 0;
        igEmoticonMax = 0;
    }

    /**
     * 返回某个表情符号的情绪强度
     *
     * @param emoticon 表情符号
     * @return 情绪强度
     */
    public int getEmoticon(String emoticon) {
        int iEmoticon = Sort.i_FindStringPositionInSortedArray(emoticon, sgEmoticon, 1, igEmoticonCount);
        if (iEmoticon >= 0)
            return igEmoticonStrength[iEmoticon];
        else
            return 999;
    }

    /**
     * 初始化表情符号列表
     *
     * @param sSourceFile 存放表情符号的文件
     * @param options     分类时的参数设置
     * @return 是否正常初始化
     */
    public boolean initialise(String sSourceFile, ClassificationOptions options) {
        if (igEmoticonCount > 0)
            return true;
        File f = new File(sSourceFile);
        if (!f.exists()) {
            System.out.println((new StringBuilder("Could not find file: ")).append(sSourceFile).toString());
            return false;
        }
        try {
            igEmoticonMax = FileOps.i_CountLinesInTextFile(sSourceFile) + 2;
            igEmoticonCount = 0;
            String[] sEmoticonTemp = new String[igEmoticonMax];
            sgEmoticon = sEmoticonTemp;
            int[] iEmoticonStrengthTemp = new int[igEmoticonMax];
            igEmoticonStrength = iEmoticonStrengthTemp;
            BufferedReader rReader;
            if (options.bgForceUTF8)
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sSourceFile), "UTF8"));
            else
                rReader = new BufferedReader(new FileReader(sSourceFile));
            String sLine;
            while ((sLine = rReader.readLine()) != null)
                if (sLine != "") {
                    String sData[] = sLine.split("\t");
                    if (sData.length > 1) {
                        igEmoticonCount++;
                        sgEmoticon[igEmoticonCount] = sData[0];
                        try {
                            igEmoticonStrength[igEmoticonCount] = Integer.parseInt(sData[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Failed to identify integer weight for emoticon! Ignoring emoticon");
                            System.out.println((new StringBuilder("Line: ")).append(sLine).toString());
                            igEmoticonCount--;
                        }
                    }
                }
            rReader.close();
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find emoticon file: ")).append(sSourceFile).toString());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println((new StringBuilder("Found emoticon file but could not read from it: ")).append(sSourceFile).toString());
            e.printStackTrace();
            return false;
        }
        if (igEmoticonCount > 1)
            Sort.quickSortStringsWithInt(sgEmoticon, igEmoticonStrength, 1, igEmoticonCount);
        return true;
    }
}
