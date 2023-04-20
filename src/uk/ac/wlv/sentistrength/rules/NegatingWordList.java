// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   NegatingWordList.java

package uk.ac.wlv.sentistrength.rules;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;

import java.io.*;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions

/**
 * 一个否定词列表（NegatingWordList），包含了反转后续情感词的词（包括任何前面的助推词）
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class NegatingWordList {

    /**
     * 否定词列表
     */
    private String[] sgNegatingWord;
    /**
     * 否定词计数器
     */
    private int igNegatingWordCount;
    /**
     * 否定词列表上限
     */
    private int igNegatingWordMax;

    /**
     * 初始化否定词列表的计数器，置为0
     */
    public NegatingWordList() {
        igNegatingWordCount = 0;
        igNegatingWordMax = 0;
    }

    /**
     * 初始化否定词列表
     *
     * @param sFilename 存放否定词的文件名
     * @param options   分类时的参数设置
     * @return 是否正常初始化
     */
    public boolean initialise(String sFilename, ClassificationOptions options) {
        if (igNegatingWordMax > 0)
            return true;
        File f = new File(sFilename);
        if (!f.exists()) {
            System.out.println((new StringBuilder("Could not find the negating words file: ")).append(sFilename).toString());
            return false;
        }
        igNegatingWordMax = FileOps.i_CountLinesInTextFile(sFilename) + 2;
        sgNegatingWord = new String[igNegatingWordMax];
        igNegatingWordCount = 0;
        try {
            BufferedReader rReader;
            if (options.bgForceUTF8)
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sFilename), "UTF8"));
            else
                rReader = new BufferedReader(new FileReader(sFilename));
            String sLine;
            while ((sLine = rReader.readLine()) != null)
                if (sLine != "") {
                    igNegatingWordCount++;
                    sgNegatingWord[igNegatingWordCount] = sLine;
                }
            rReader.close();
            Sort.quickSortStrings(sgNegatingWord, 1, igNegatingWordCount);
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find negating words file: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println((new StringBuilder("Found negating words file but could not read from it: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询某个词是否在否定词列表中
     *
     * @param sWord 查询的词
     * @return 是/否
     */
    public boolean negatingWord(String sWord) {
        return Sort.i_FindStringPositionInSortedArray(sWord, sgNegatingWord, 1, igNegatingWordCount) >= 0;
    }
}
