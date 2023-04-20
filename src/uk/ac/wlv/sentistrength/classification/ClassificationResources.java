// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   ClassificationResources.java

package uk.ac.wlv.sentistrength.classification;

import uk.ac.wlv.sentistrength.rules.*;
import uk.ac.wlv.utilities.FileOps;

import java.io.File;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            EmoticonsList, CorrectSpellingsList, SentimentWords, NegatingWordList,
//            QuestionWords, BoosterWordsList, IdiomList, EvaluativeTerms,
//            IronyList, Lemmatiser, ClassificationOptions

/**
 * Description:&nbsp; 资源汇总类
 *
 * @author 201850003
 * date: 2023/03/06
 */
public class ClassificationResources {

    /**
     * Description: <br>&nbsp; 表情符号列表
     *
     * @see EmoticonsList
     */
    public EmoticonsList emoticons;
    /**
     * Description: <br>&nbsp; 纠正拼写列表
     *
     * @see CorrectSpellingsList
     */
    public CorrectSpellingsList correctSpellings;
    /**
     * Description: <br>&nbsp; 情绪词列表
     *
     * @see SentimentWords
     */
    public SentimentWords sentimentWords;
    /**
     * Description: <br>&nbsp; 否定词列表
     *
     * @see NegatingWordList
     */
    public NegatingWordList negatingWords;
    /**
     * Description: <br>&nbsp; 疑问词列表
     *
     * @see QuestionWords
     */
    public QuestionWords questionWords;
    /**
     * Description: <br>&nbsp; 助推词列表
     *
     * @see BoosterWordsList
     */
    public BoosterWordsList boosterWords;
    /**
     * Description: <br>&nbsp; 习语列表
     *
     * @see IdiomList
     */
    public IdiomList idiomList;
    /**
     * Description: <br>&nbsp; 评估术语列表
     *
     * @see EvaluativeTerms
     */
    public EvaluativeTerms evaluativeTerms;
    /**
     * Description: <br>&nbsp; 反讽词列表
     *
     * @see IronyList
     */
    public IronyList ironyList;
    /**
     * Description: <br>&nbsp; 词性还原器
     *
     * @see Lemmatiser
     */
    public Lemmatiser lemmatiser;
    /**
     * Description: <br>&nbsp; SentiStrength 文件夹
     *
     * @see String
     */
    public String sgSentiStrengthFolder;
    /**
     * Description: <br>&nbsp; 情绪单词文件
     *
     * @see String
     */
    public String sgSentimentWordsFile;
    /**
     * Description: <br>&nbsp; 情绪单词文件2
     *
     * @see String
     */
    public String sgSentimentWordsFile2;
    /**
     * Description: <br>&nbsp; 表情符号查找表
     *
     * @see String
     */
    public String sgEmoticonLookupTable;
    /**
     * Description: <br>&nbsp; 正确拼写的文件名
     *
     * @see String
     */
    public String sgCorrectSpellingFileName;
    /**
     * Description: <br>&nbsp; 正确拼写的文件名2
     *
     * @see String
     */
    public String sgCorrectSpellingFileName2;
    /**
     * Description: <br>&nbsp; 习语查找表
     *
     * @see String
     */
    public String sgSlangLookupTable;
    /**
     * Description: <br>&nbsp; 否定词列表文件
     *
     * @see String
     */
    public String sgNegatingWordListFile;
    /**
     * Description: <br>&nbsp; 助推词列表文件
     *
     * @see String
     */
    public String sgBoosterListFile;
    /**
     * Description: <br>&nbsp; 习语查找表文件
     *
     * @see String
     */
    public String sgIdiomLookupTableFile;
    /**
     * Description: <br>&nbsp; 疑问词列表文件
     *
     * @see String
     */
    public String sgQuestionWordListFile;
    /**
     * Description: <br>&nbsp; 反讽词列表文件
     *
     * @see String
     */
    public String sgIronyWordListFile;
    /**
     * Description: <br>&nbsp; 附属文件
     *
     * @see String
     */
    public String sgAdditionalFile;
    /**
     * Description: <br>&nbsp; 词法还原文件
     *
     * @see String
     */
    public String sgLemmaFile;

    /**
     * Description:<br>&nbsp; 构造函数，初始化全局变量
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public ClassificationResources() {
        emoticons = new EmoticonsList();
        correctSpellings = new CorrectSpellingsList();
        sentimentWords = new SentimentWords();
        negatingWords = new NegatingWordList();
        questionWords = new QuestionWords();
        boosterWords = new BoosterWordsList();
        idiomList = new IdiomList();
        evaluativeTerms = new EvaluativeTerms();
        ironyList = new IronyList();
        lemmatiser = new Lemmatiser();
        sgSentiStrengthFolder = System.getProperty("user.dir") + "/src/SentStrength_Data/";
        sgSentimentWordsFile = "EmotionLookupTable.txt";
        sgSentimentWordsFile2 = "SentimentLookupTable.txt";
        sgEmoticonLookupTable = "EmoticonLookupTable.txt";
        sgCorrectSpellingFileName = "Dictionary.txt";
        sgCorrectSpellingFileName2 = "EnglishWordList.txt";
        sgSlangLookupTable = "SlangLookupTable_NOT_USED.txt";
        sgNegatingWordListFile = "NegatingWordList.txt";
        sgBoosterListFile = "BoosterWordList.txt";
        sgIdiomLookupTableFile = "IdiomLookupTable.txt";
        sgQuestionWordListFile = "QuestionWords.txt";
        sgIronyWordListFile = "IronyTerms.txt";
        sgAdditionalFile = "";
        sgLemmaFile = "";
    }

    /**
     * Description:<br>&nbsp; 初始化各项分类选项参数。返回值用布尔值，表示初始化是否成功完成。
     *
     * @param options 分类选项对象
     * @return boolean
     * @author 201850003
     * date: 2023/03/06
     */
    public boolean initialise(ClassificationOptions options) {
        int iExtraLinesToReserve = 0;
        if (sgAdditionalFile.compareTo("") != 0) {
            iExtraLinesToReserve = FileOps.i_CountLinesInTextFile((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgAdditionalFile).toString());
            if (iExtraLinesToReserve < 0) {
  // System.out.println((new StringBuilder("No lines found in additional file! Ignoring ")).append(sgAdditionalFile).toString());
                return false;
            }
        }
        if (initLemmatiserOrNot(options)) {
   //         System.out.println((new StringBuilder("Can't load lemma file! ")).append(sgLemmaFile).toString());
            return false;
        }
        File f = new File((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgSentimentWordsFile).toString());
        if (!f.exists() || f.isDirectory()) {
            sgSentimentWordsFile = sgSentimentWordsFile2;
        }
        File f2 = new File((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgCorrectSpellingFileName).toString());

        if (!f2.exists() || f2.isDirectory()) {
            sgCorrectSpellingFileName = sgCorrectSpellingFileName2;
        }
        if (initPartRules(options, iExtraLinesToReserve)) {
            if (iExtraLinesToReserve > 0) {
                return evaluativeTerms.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgAdditionalFile).toString(), options, idiomList, sentimentWords);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 根据选项决定对 lemmatiser 进行初始化，并以布尔值返回任务执行结果
     * @param options 选项
     * @return boolean
     * @author 201850003
     * date: 2023/04/19
     */
    public boolean initLemmatiserOrNot(ClassificationOptions options) {
        return options.bgUseLemmatisation
                && !lemmatiser.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgLemmaFile).toString(), false);
    }


    /**
     * 对8种单词打分规则进行初始化，并判断是否成功初始化
     * @param options              选项
     * @param iExtraLinesToReserve 需要 reverse 的额外行数
     * @return boolean
     * @author 201850003
     * date: 2023/04/19
     */
    public boolean initPartRules(ClassificationOptions options, int iExtraLinesToReserve) {
        return emoticons.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgEmoticonLookupTable).toString(), options)
                && correctSpellings.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgCorrectSpellingFileName).toString(), options)
                && sentimentWords.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgSentimentWordsFile).toString(), options, iExtraLinesToReserve)
                && negatingWords.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgNegatingWordListFile).toString(), options)
                && questionWords.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgQuestionWordListFile).toString(), options)
                && ironyList.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgIronyWordListFile).toString(), options)
                && boosterWords.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgBoosterListFile).toString(), options, iExtraLinesToReserve)
                && idiomList.initialise((new StringBuilder(String.valueOf(sgSentiStrengthFolder))).append(sgIdiomLookupTableFile).toString(), options, iExtraLinesToReserve);
    }


}
