//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package uk.ac.wlv.sentistrength.classification;

import java.io.*;

/**
 * Description:&nbsp; 分类选项类
 *
 * @author 201850003
 * date: 2023/03/06
 */
public class ClassificationOptions {
    /**
     * Description: <br>&nbsp; 是否使用 TensiStrength
     */
    public boolean bgTensiStrength = false;
    /**
     * Description: <br>&nbsp; 项目名称
     *
     * @see String
     */
    public String sgProgramName = "SentiStrength";
    /**
     * Description: <br>&nbsp; 项目测量目标
     *
     * @see String
     */
    public String sgProgramMeasuring = "sentiment";
    /**
     * Description: <br>&nbsp; 项目正标签
     *
     * @see String
     */
    public String sgProgramPos = "positive sentiment";
    /**
     * Description: <br>&nbsp; 项目负标签
     *
     * @see String
     */
    public String sgProgramNeg = "negative sentiment";
    /**
     * Description: <br>&nbsp;是否使用缩放模式
     */
    public boolean bgScaleMode = false;
    /**
     * Description: <br>&nbsp;是否使用三元分类模式
     */
    public boolean bgTrinaryMode = false;
    /**
     * Description: <br>&nbsp;是否需要获取三元分类模式的二元版本
     */
    public boolean bgBinaryVersionOfTrinaryMode = false;
    /**
     * Description: <br>&nbsp; 默认二元分类参数
     */
    public int igDefaultBinaryClassification = 1;
    /**
     * Description: <br>&nbsp; 情绪段落组合方法
     */
    public int igEmotionParagraphCombineMethod = 0;
    /**
     * Description: <br>&nbsp; 最大组合数量
     */
    final int igCombineMax = 0;
    /**
     * Description: <br>&nbsp; 平均组合数量
     */
    final int igCombineAverage = 1;
    /**
     * Description: <br>&nbsp; 总组合数量
     */
    final int igCombineTotal = 2;
    /**
     * Description: <br>&nbsp; 情绪语句组合方法
     */
    public int igEmotionSentenceCombineMethod = 0;
    /**
     * Description: <br>&nbsp; 消极情绪倍率
     */
    public float fgNegativeSentimentMultiplier = 1.5F;
    /**
     * Description: <br>&nbsp; 是否在问题句子中减少消极情绪
     */
    public boolean bgReduceNegativeEmotionInQuestionSentences = false;
    /**
     * Description: <br>&nbsp; 错误计数是否加2
     */
    public boolean bgMissCountsAsPlus2 = true;
    /**
     * Description: <br>&nbsp; 非消极语句计数是否加2
     */
    public boolean bgYouOrYourIsPlus2UnlessSentenceNegative = false;
    /**
     * Description: <br>&nbsp;中性句子中有感叹号，计数是否加2
     */
    public boolean bgExclamationInNeutralSentenceCountsAsPlus2 = false;
    /**
     * Description: <br>&nbsp;最小标点符号带感叹号是否能更改句子情绪
     */
    public int igMinPunctuationWithExclamationToChangeSentenceSentiment = 0;
    /**
     * Description: <br>&nbsp;是否使用习语查找表
     */
    public boolean bgUseIdiomLookupTable = true;
    /**
     * Description: <br>&nbsp;是否使用使用对象评估表
     */
    public boolean bgUseObjectEvaluationTable = false;
    /**
     * Description: <br>&nbsp; 中性情绪是否视为积极的强调
     */
    public boolean bgCountNeutralEmotionsAsPositiveForEmphasis1 = true;
    /**
     * Description: <br>&nbsp; 情绪对解释中性语句是否强调
     */
    public int igMoodToInterpretNeutralEmphasis = 1;
    /**
     * Description: <br>&nbsp; 是否允许多个积极单词存在时能够增加积极情绪
     */
    public boolean bgAllowMultiplePositiveWordsToIncreasePositiveEmotion = true;
    /**
     * Description: <br>&nbsp; 是否允许多个消极单词存在时能够增加消极情绪
     */
    public boolean bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion = true;
    /**
     * Description: <br>&nbsp; 是否忽略消极单词后的助推词
     */
    public boolean bgIgnoreBoosterWordsAfterNegatives = true;
    /**
     * Description: <br>&nbsp; 是否按字典准确拼写
     */
    public boolean bgCorrectSpellingsUsingDictionary = true;
    /**
     * Description: <br>&nbsp; 是否更正额外的字母拼写错误
     */
    public boolean bgCorrectExtraLetterSpellingErrors = true;
    /**
     * Description: <br>&nbsp; 单词中间的非法双字母
     *
     * @see String
     */
    public String sgIllegalDoubleLettersInWordMiddle = "ahijkquvxyz";
    /**
     * Description: <br>&nbsp; 单词末尾的非法双字母
     *
     * @see String
     */
    public String sgIllegalDoubleLettersAtWordEnd = "achijkmnpqruvwxyz";
    /**
     * Description: <br>&nbsp; 多个字母是否增加情绪
     */
    public boolean bgMultipleLettersBoostSentiment = true;
    /**
     * Description: <br>&nbsp; 助推词是否能改变情绪
     */
    public boolean bgBoosterWordsChangeEmotion = true;
    /**
     * Description: <br>&nbsp; 是否始终在撇号处拆分单词
     */
    public boolean bgAlwaysSplitWordsAtApostrophes = false;
    /**
     * Description: <br>&nbsp; 否定词是否允许出现在情绪词前
     */
    public boolean bgNegatingWordsOccurBeforeSentiment = true;
    /**
     * Description: <br>&nbsp; 情绪词之前到否定词之间的最大单词数
     */
    public int igMaxWordsBeforeSentimentToNegate = 0;
    /**
     * Description: <br>&nbsp; 否定词是否允许出现在情绪词后
     */
    public boolean bgNegatingWordsOccurAfterSentiment = false;
    /**
     * Description: <br>&nbsp; 情绪词之后到否定词之间的最大单词数
     */
    public int igMaxWordsAfterSentimentToNegate = 0;
    /**
     * Description: <br>&nbsp; 否定积极是否允许翻转情绪
     */
    public boolean bgNegatingPositiveFlipsEmotion = true;
    /**
     * Description: <br>&nbsp; 否定消极是否允许中性化情绪
     */
    public boolean bgNegatingNegativeNeutralisesEmotion = true;
    /**
     * Description: <br>&nbsp; 否定单词是否允许翻转情绪
     */
    public boolean bgNegatingWordsFlipEmotion = false;
    /**
     * Description: <br>&nbsp; 否定词权值
     */
    public float fgStrengthMultiplierForNegatedWords = 0.5F;
    /**
     * Description: <br>&nbsp; 是否允许使用重复字母更正拼写
     */
    public boolean bgCorrectSpellingsWithRepeatedLetter = true;
    /**
     * Description: <br>&nbsp; 是否使用表情符号
     */
    public boolean bgUseEmoticons = true;
    /**
     * Description: <br>&nbsp; 大写是否允许增强情绪
     */
    public boolean bgCapitalsBoostTermSentiment = false;
    /**
     * Description: <br>&nbsp; 增强情绪的最小字母重复次数
     */
    public int igMinRepeatedLettersForBoost = 2;
    /**
     * Description: <br>&nbsp; 情绪关键字列表
     *
     * @see String[]
     */
    public String[] sgSentimentKeyWords = null;
    /**
     * Description: <br>&nbsp; 是否忽略没有关键字的句子
     */
    public boolean bgIgnoreSentencesWithoutKeywords = false;
    /**
     * Description: <br>&nbsp; 关键字前包含的单词数
     */
    public int igWordsToIncludeBeforeKeyword = 4;
    /**
     * Description: <br>&nbsp; 关键字后包含的单词数
     */
    public int igWordsToIncludeAfterKeyword = 4;
    /**
     * Description: <br>&nbsp; 是否解释分类
     */
    public boolean bgExplainClassification = false;
    /**
     * Description: <br>&nbsp; 是否回显文本
     */
    public boolean bgEchoText = false;
    /**
     * Description: <br>&nbsp; 是否强制 UTF8 编码
     */
    public boolean bgForceUTF8 = false;
    /**
     * Description: <br>&nbsp; 是否词形还原
     */
    public boolean bgUseLemmatisation = false;
    /**
     * Description: <br>&nbsp; 表示引用反讽的最小积极性句子数量
     */
    public int igMinSentencePosForQuotesIrony = 10;
    /**
     * Description: <br>&nbsp; 表示标点反讽的最小积极性句子数量
     */
    public int igMinSentencePosForPunctuationIrony = 10;
    /**
     * Description: <br>&nbsp; 表示术语反讽的最小积极性句子数量
     */
    public int igMinSentencePosForTermsIrony = 10;

    /**
     * Description:<br>&nbsp; 默认构造函数
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public ClassificationOptions() {
    }

    /**
     * Description:<br>&nbsp; 设置关键字列表，并在后续分析时忽略没有关键字的句子
     *
     * @param sKeywordList 关键字列表
     * @author 201850003
     * date: 2023/03/06
     */
    public void parseKeywordList(String sKeywordList) {
        this.sgSentimentKeyWords = sKeywordList.split(",");
        this.bgIgnoreSentencesWithoutKeywords = true;
    }

    /**
     * Description:<br>&nbsp; 将分类选项参数写入输出流中，返回操作是否成功的结果。
     *
     * @param wWriter             输出流
     * @param iMinImprovement     最小改进次数
     * @param bUseTotalDifference 是否使用总体差异
     * @param iMultiOptimisations 多次优化最小次数
     * @return boolean
     * @author 201850003
     * date: 2023/03/06
     */
    public boolean printClassificationOptions(BufferedWriter wWriter, int iMinImprovement, boolean bUseTotalDifference, int iMultiOptimisations) {
        try {
            if (this.igEmotionParagraphCombineMethod == 0) {
                wWriter.write("Max");
            } else if (this.igEmotionParagraphCombineMethod == 1) {
                wWriter.write("Av");
            } else {
                wWriter.write("Tot");
            }

            if (this.igEmotionSentenceCombineMethod == 0) {
                wWriter.write("\tMax");
            } else if (this.igEmotionSentenceCombineMethod == 1) {
                wWriter.write("\tAv");
            } else {
                wWriter.write("\tTot");
            }

            if (bUseTotalDifference) {
                wWriter.write("\tTotDiff");
            } else {
                wWriter.write("\tExactCount");
            }
            wWriter.write("\t" + iMultiOptimisations + "\t" + this.bgReduceNegativeEmotionInQuestionSentences + "\t" + this.bgMissCountsAsPlus2 + "\t" + this.bgYouOrYourIsPlus2UnlessSentenceNegative + "\t" + this.bgExclamationInNeutralSentenceCountsAsPlus2 + "\t" + this.bgUseIdiomLookupTable + "\t" + this.igMoodToInterpretNeutralEmphasis + "\t" + this.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion + "\t" + this.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion + "\t" + this.bgIgnoreBoosterWordsAfterNegatives + "\t" + this.bgMultipleLettersBoostSentiment + "\t" + this.bgBoosterWordsChangeEmotion + "\t" + this.bgNegatingWordsFlipEmotion + "\t" + this.bgNegatingPositiveFlipsEmotion + "\t" + this.bgNegatingNegativeNeutralisesEmotion + "\t" + this.bgCorrectSpellingsWithRepeatedLetter + "\t" + this.bgUseEmoticons + "\t" + this.bgCapitalsBoostTermSentiment + "\t" + this.igMinRepeatedLettersForBoost + "\t" + this.igMaxWordsBeforeSentimentToNegate + "\t" + iMinImprovement);
            return true;
        } catch (IOException var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * Description:<br>&nbsp; 将分类选项的空白部分的制表符写入输出流，返回操作是否成功的结果。
     *
     * @param wWriter 输出流
     * @return boolean
     * @author 201850003
     * date: 2023/03/06
     */
    public boolean printBlankClassificationOptions(BufferedWriter wWriter) {
        try {
            wWriter.write("~");
            wWriter.write("\t~");
            wWriter.write("\tBaselineMajorityClass");
            wWriter.write("\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~\t~");
            return true;
        } catch (IOException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    /**
     * Description:<br>&nbsp; 将分类选项的头部（各选项名称）及制表符写入输出流，返回操作是否成功的结果。
     *
     * @param wWriter 输出流
     * @return boolean
     * @author 201850003
     * date: 2023/03/06
     */
    public boolean printClassificationOptionsHeadings(BufferedWriter wWriter) {
        try {
            wWriter.write("EmotionParagraphCombineMethod\tEmotionSentenceCombineMethod\tDifferenceCalculationMethodForTermWeightAdjustments\tMultiOptimisations\tReduceNegativeEmotionInQuestionSentences\tMissCountsAsPlus2\tYouOrYourIsPlus2UnlessSentenceNegative\tExclamationCountsAsPlus2\tUseIdiomLookupTable\tMoodToInterpretNeutralEmphasis\tAllowMultiplePositiveWordsToIncreasePositiveEmotion\tAllowMultipleNegativeWordsToIncreaseNegativeEmotion\tIgnoreBoosterWordsAfterNegatives\tMultipleLettersBoostSentiment\tBoosterWordsChangeEmotion\tNegatingWordsFlipEmotion\tNegatingPositiveFlipsEmotion\tNegatingNegativeNeutralisesEmotion\tCorrectSpellingsWithRepeatedLetter\tUseEmoticons\tCapitalsBoostTermSentiment\tMinRepeatedLettersForBoost\tWordsBeforeSentimentToNegate\tMinImprovement");
            return true;
        } catch (IOException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    /**
     * Description:<br>&nbsp; 根据输入的文件名读取文件，并设置分类选项各参数，返回操作是否成功的结果。
     *
     * @param sFilename 文件名
     * @return boolean
     * @author 201850003
     * date: 2023/03/06
     */
    public boolean setClassificationOptions(String sFilename) {
        try {
            BufferedReader rReader = new BufferedReader(new FileReader(sFilename));

            while (rReader.ready()) {
                String sLine = rReader.readLine();
                int iTabPos = sLine.indexOf("\t");
                if (iTabPos > 0) {
                    String[] sData = sLine.split("\t");
                    if (sData[0].equals("EmotionParagraphCombineMethod")) {
                        if (sData[1].indexOf("Max") >= 0) {
                            this.igEmotionParagraphCombineMethod = 0;
                        }

                        if (sData[1].indexOf("Av") >= 0) {
                            this.igEmotionParagraphCombineMethod = 1;
                        }

                        if (sData[1].indexOf("Tot") >= 0) {
                            this.igEmotionParagraphCombineMethod = 2;
                        }
                    } else if (sData[0].equals("EmotionSentenceCombineMethod")) {
                        if (sData[1].indexOf("Max") >= 0) {
                            this.igEmotionSentenceCombineMethod = 0;
                        }

                        if (sData[1].indexOf("Av") >= 0) {
                            this.igEmotionSentenceCombineMethod = 1;
                        }

                        if (sData[1].indexOf("Tot") >= 0) {
                            this.igEmotionSentenceCombineMethod = 2;
                        }
                    } else if (sData[0].equals("IgnoreNegativeEmotionInQuestionSentences")) {
                        this.bgReduceNegativeEmotionInQuestionSentences = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("MissCountsAsPlus2")) {
                        this.bgMissCountsAsPlus2 = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("YouOrYourIsPlus2UnlessSentenceNegative")) {
                        this.bgYouOrYourIsPlus2UnlessSentenceNegative = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("ExclamationCountsAsPlus2")) {
                        this.bgExclamationInNeutralSentenceCountsAsPlus2 = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("UseIdiomLookupTable")) {
                        this.bgUseIdiomLookupTable = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("Mood")) {
                        this.igMoodToInterpretNeutralEmphasis = Integer.parseInt(sData[1]);
                    } else if (sData[0].equals("AllowMultiplePositiveWordsToIncreasePositiveEmotion")) {
                        this.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("AllowMultipleNegativeWordsToIncreaseNegativeEmotion")) {
                        this.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("IgnoreBoosterWordsAfterNegatives")) {
                        this.bgIgnoreBoosterWordsAfterNegatives = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("MultipleLettersBoostSentiment")) {
                        this.bgMultipleLettersBoostSentiment = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("BoosterWordsChangeEmotion")) {
                        this.bgBoosterWordsChangeEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("NegatingWordsFlipEmotion")) {
                        this.bgNegatingWordsFlipEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("NegatingWordsFlipEmotion")) {
                        this.bgNegatingPositiveFlipsEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("NegatingWordsFlipEmotion")) {
                        this.bgNegatingNegativeNeutralisesEmotion = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("CorrectSpellingsWithRepeatedLetter")) {
                        this.bgCorrectSpellingsWithRepeatedLetter = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("UseEmoticons")) {
                        this.bgUseEmoticons = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("CapitalsAreSentimentBoosters")) {
                        this.bgCapitalsBoostTermSentiment = Boolean.parseBoolean(sData[1]);
                    } else if (sData[0].equals("MinRepeatedLettersForBoost")) {
                        this.igMinRepeatedLettersForBoost = Integer.parseInt(sData[1]);
                    } else if (sData[0].equals("WordsBeforeSentimentToNegate")) {
                        this.igMaxWordsBeforeSentimentToNegate = Integer.parseInt(sData[1]);
                    } else if (sData[0].equals("Trinary")) {
                        this.bgTrinaryMode = true;
                    } else if (sData[0].equals("Binary")) {
                        this.bgTrinaryMode = true;
                        this.bgBinaryVersionOfTrinaryMode = true;
                    } else {
                        if (!sData[0].equals("Scale")) {
                            rReader.close();
                            return false;
                        }

                        this.bgScaleMode = true;
                    }
                }
            }

            rReader.close();
            return true;
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
            return false;
        } catch (IOException var8) {
            var8.printStackTrace();
            return false;
        }
    }

    /**
     * Description:<br>&nbsp; 根据是否使用了 TensiStrength 设定项目名称、测量目标、正负标签。
     *
     * @param bTensiStrength 是否使用 TensiStrength
     * @author 201850003
     * date: 2023/03/06
     */
    public void nameProgram(boolean bTensiStrength) {
        this.bgTensiStrength = bTensiStrength;
        if (bTensiStrength) {
            this.sgProgramName = "TensiStrength";
            this.sgProgramMeasuring = "stress and relaxation";
            this.sgProgramPos = "relaxation";
            this.sgProgramNeg = "stress";
        } else {
            this.sgProgramName = "SentiStrength";
            this.sgProgramMeasuring = "sentiment";
            this.sgProgramPos = "positive sentiment";
            this.sgProgramNeg = "negative sentiment";
        }

    }
}
