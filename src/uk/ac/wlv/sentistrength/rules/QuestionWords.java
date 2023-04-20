// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   QuestionWords.java

package uk.ac.wlv.sentistrength.rules;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;

import java.io.*;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions

/**
 * 问题词列表(QuestionWords)
 * e.g what,how
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class QuestionWords {

    /**
     * 问题词列表
     */
    private String sgQuestionWord[];
    /**
     * 问题词计数器
     */
    private int igQuestionWordCount;
    /**
     * 问题词列表上限
     */
    private int igQuestionWordMax;

    /**
     * 初始化问题词列表计数器，置为0
     */
    public QuestionWords() {
        igQuestionWordCount = 0;
        igQuestionWordMax = 0;
    }

    /**
     * 初始化问题词列表
     *
     * @param sFilename 存放问题词的文件名
     * @param options   分类时的参数设置
     * @return 是否正常初始化
     */
    public boolean initialise(String sFilename, ClassificationOptions options) {
        if (igQuestionWordMax > 0)
            return true;
        File f = new File(sFilename);
        if (!f.exists()) {
            System.out.println((new StringBuilder("Could not find the question word file: ")).append(sFilename).toString());
            return false;
        }
        igQuestionWordMax = FileOps.i_CountLinesInTextFile(sFilename) + 2;
        sgQuestionWord = new String[igQuestionWordMax];
        igQuestionWordCount = 0;
        try {
            BufferedReader rReader;
            if (options.bgForceUTF8)
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sFilename), "UTF8"));
            else
                rReader = new BufferedReader(new FileReader(sFilename));
            String sLine;
            while ((sLine = rReader.readLine()) != null)
                if (sLine != "") {
                    igQuestionWordCount++;
                    sgQuestionWord[igQuestionWordCount] = sLine;
                }
            rReader.close();
            Sort.quickSortStrings(sgQuestionWord, 1, igQuestionWordCount);
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find the question word file: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println((new StringBuilder("Found question word file but could not read from it: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询某个问题词是否在列表中
     *
     * @param sWord 问题词
     * @return 是否在列表中
     */
    public boolean questionWord(String sWord) {
        return Sort.i_FindStringPositionInSortedArray(sWord, sgQuestionWord, 1, igQuestionWordCount) >= 0;
    }
}
