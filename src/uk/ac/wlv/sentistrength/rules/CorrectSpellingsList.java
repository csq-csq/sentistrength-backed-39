// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   CorrectSpellingsList.java

package uk.ac.wlv.sentistrength.rules;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;

import java.io.*;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions

/**
 * 正规拼写词汇列表
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class CorrectSpellingsList {

    /**
     * 正拼词列表
     */
    private String[] sgCorrectWord;
    /**
     * 正拼词计数器
     */
    private int igCorrectWordCount;
    /**
     * 正拼词上限
     */
    private int igCorrectWordMax;

    /**
     * 初始化词汇表的计数器，置为0
     */
    public CorrectSpellingsList() {
        igCorrectWordCount = 0;
        igCorrectWordMax = 0;
    }

    /**
     * 初始化词汇表
     *
     * @param sFilename 字典
     * @param options   分类时的参数设置
     * @return 是否正常初始化
     */
    public boolean initialise(String sFilename, ClassificationOptions options) {
        if (igCorrectWordMax > 0) {
            return true;
        }
        if (!options.bgCorrectSpellingsUsingDictionary) {
            return true;
        }
        igCorrectWordMax = FileOps.i_CountLinesInTextFile(sFilename) + 2;
        sgCorrectWord = new String[igCorrectWordMax];
        igCorrectWordCount = 0;
        File f = new File(sFilename);
        if (!f.exists()) {
            System.out.println((new StringBuilder("Could not find the spellings file: ")).append(sFilename).toString());
            return false;
        }
        try {
            BufferedReader rReader;
            if (options.bgForceUTF8) {
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sFilename), "UTF8"));
            } else {
                rReader = new BufferedReader(new FileReader(sFilename));
            }
            String sLine;
            while ((sLine = rReader.readLine()) != null) {
                if (sLine.equals("")) {
                    igCorrectWordCount++;
                    sgCorrectWord[igCorrectWordCount] = sLine;
                }
            }
            rReader.close();
            Sort.quickSortStrings(sgCorrectWord, 1, igCorrectWordCount);
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find the spellings file: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println((new StringBuilder("Found spellings file but could not read from it: ")).append(sFilename).toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询某个单词是否在词汇表中
     *
     * @param sWord 要查询的单词
     * @return 是否在表中
     */
    public boolean correctSpelling(String sWord) {
        return Sort.i_FindStringPositionInSortedArray(sWord, sgCorrectWord, 1, igCorrectWordCount) >= 0;
    }
}
