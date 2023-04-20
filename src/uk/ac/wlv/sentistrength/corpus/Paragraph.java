package uk.ac.wlv.sentistrength.corpus;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.sentistrength.classification.ClassificationResources;
import uk.ac.wlv.sentistrength.classification.TextParsingOptions;
import uk.ac.wlv.sentistrength.classification.UnusedTermsClassificationIndex;
import uk.ac.wlv.utilities.Sort;
import uk.ac.wlv.utilities.StringIndex;

import java.util.Random;

/**
 * 段落类
 *
 * @author 陆恺
 * @version 1.0 2023-3-5
 */
public class Paragraph {
    /**
     * 句式集
     */
    private Sentence[] sentence;
    /**
     * 句式数
     */
    private int igSentenceCount = 0;
    /**
     * 情绪符集
     */
    private int[] igSentimentIDList;
    /**
     * 情绪符集数
     */
    private int igSentimentIDListCount = 0;
    /**
     * 是否有情绪符集
     */
    private boolean bSentimentIDListMade = false;
    /**
     * 积极情绪符
     */
    private int igPositiveSentiment = 0;
    /**
     * 负面情绪符
     */
    private int igNegativeSentiment = 0;
    /**
     * 三元情绪符
     */
    private int igTrinarySentiment = 0;
    /**
     * 情绪符级别
     */
    private int igScaleSentiment = 0;
    /**
     * 资源
     */
    private ClassificationResources resources;
    /**
     * 选项
     */
    private ClassificationOptions options;
    /**
     * 随机器
     */
    private Random generator = new Random();
    /**
     * 分类结果
     */
    private String sgClassificationRationale = "";

    /**
     * 将段落加到积极消极信息索引中
     *
     * @param unusedTermsClassificationIndex 未使用术语分类索引
     * @param iCorrectPosClass               正确积极类
     * @param iEstPosClass                   实际积极类
     * @param iCorrectNegClass               正确消极类
     * @param iEstNegClass                   实际消极类
     */
    public void addParagraphToIndexWithPosNegValues(UnusedTermsClassificationIndex unusedTermsClassificationIndex, int iCorrectPosClass, int iEstPosClass, int iCorrectNegClass, int iEstNegClass) {
        for (int i = 1; i <= this.igSentenceCount; ++i) {
            this.sentence[i].addSentenceToIndex(unusedTermsClassificationIndex);
        }

        unusedTermsClassificationIndex.addNewIndexToMainIndexWithPosNegValues(iCorrectPosClass, iEstPosClass, iCorrectNegClass, iEstNegClass);
    }

    /**
     * 将段落加到级别索引中.
     *
     * @param unusedTermsClassificationIndex 未使用术语分类索引
     * @param iCorrectScaleClass             正确级别
     * @param iEstScaleClass                 实际级别
     */
    public void addParagraphToIndexWithScaleValues(UnusedTermsClassificationIndex unusedTermsClassificationIndex, int iCorrectScaleClass, int iEstScaleClass) {
        for (int i = 1; i <= this.igSentenceCount; ++i) {
            this.sentence[i].addSentenceToIndex(unusedTermsClassificationIndex);
        }

        unusedTermsClassificationIndex.addNewIndexToMainIndexWithScaleValues(iCorrectScaleClass, iEstScaleClass);
    }

    /**
     * 将段落加到二元值索引中.
     *
     * @param unusedTermsClassificationIndex 未使用术语分类索引
     * @param iCorrectBinaryClass            正确二元值
     * @param iEstBinaryClass                实际二元值
     */
    public void addParagraphToIndexWithBinaryValues(UnusedTermsClassificationIndex unusedTermsClassificationIndex, int iCorrectBinaryClass, int iEstBinaryClass) {
        for (int i = 1; i <= this.igSentenceCount; ++i) {
            this.sentence[i].addSentenceToIndex(unusedTermsClassificationIndex);
        }

        unusedTermsClassificationIndex.addNewIndexToMainIndexWithBinaryValues(iCorrectBinaryClass, iEstBinaryClass);
    }

    /**
     * 获取检查后的索引数
     *
     * @param stringIndex        语段索引
     * @param textParsingOptions 文段分隔符选择
     * @param bRecordCount       记录数
     * @param bArffIndex         arff文件索引
     * @return 检查后的索引数
     */
    public int addToStringIndex(StringIndex stringIndex, TextParsingOptions textParsingOptions, boolean bRecordCount, boolean bArffIndex) {
        int iTermsChecked = 0;

        for (int i = 1; i <= this.igSentenceCount; ++i) {
            iTermsChecked += this.sentence[i].addToStringIndex(stringIndex, textParsingOptions, bRecordCount, bArffIndex);
        }

        return iTermsChecked;
    }

    /**
     * 将段落加到三元组索引中
     *
     * @param unusedTermsClassificationIndex 未使用术语分类索引
     * @param iCorrectTrinaryClass           正确三元组
     * @param iEstTrinaryClass               实际三元组
     */
    public void addParagraphToIndexWithTrinaryValues(UnusedTermsClassificationIndex unusedTermsClassificationIndex, int iCorrectTrinaryClass, int iEstTrinaryClass) {
        for (int i = 1; i <= this.igSentenceCount; ++i) {
            this.sentence[i].addSentenceToIndex(unusedTermsClassificationIndex);
        }

        unusedTermsClassificationIndex.addNewIndexToMainIndexWithTrinaryValues(iCorrectTrinaryClass, iEstTrinaryClass);
    }

    /**
     * 初始化段落
     *
     * @param sParagraph               段落文本
     * @param classResources           资源
     * @param newClassificationOptions 分类选项
     */
    public void setParagraph(String sParagraph, ClassificationResources classResources, ClassificationOptions newClassificationOptions) {
        this.resources = classResources;
        this.options = newClassificationOptions;
        if (sParagraph.indexOf("\"") >= 0) {
            sParagraph = sParagraph.replace("\"", "'");
        }

        int iSentenceEnds = 2;
        int iPos = 0;

        while (iPos >= 0 && iPos < sParagraph.length()) {
            iPos = sParagraph.indexOf("<br>", iPos);
            if (iPos >= 0) {
                iPos += 3;
                ++iSentenceEnds;
            }
        }

        iPos = 0;

        while (iPos >= 0 && iPos < sParagraph.length()) {
            iPos = sParagraph.indexOf(".", iPos);
            if (iPos >= 0) {
                ++iPos;
                ++iSentenceEnds;
            }
        }

        iPos = 0;

        while (iPos >= 0 && iPos < sParagraph.length()) {
            iPos = sParagraph.indexOf("!", iPos);
            if (iPos >= 0) {
                ++iPos;
                ++iSentenceEnds;
            }
        }

        iPos = 0;

        while (iPos >= 0 && iPos < sParagraph.length()) {
            iPos = sParagraph.indexOf("?", iPos);
            if (iPos >= 0) {
                ++iPos;
                ++iSentenceEnds;
            }
        }

        this.sentence = new Sentence[iSentenceEnds];
        this.igSentenceCount = 0;
        int iLastSentenceEnd = -1;
        boolean bPunctuationIndicatesSentenceEnd = false;
        int iNextBr = sParagraph.indexOf("<br>");
        String sNextSentence = "";

        for (iPos = 0; iPos < sParagraph.length(); ++iPos) {
            String sNextChar = sParagraph.substring(iPos, iPos + 1);
            if (iPos == sParagraph.length() - 1) {
                sNextSentence = sParagraph.substring(iLastSentenceEnd + 1);
            } else if (iPos == iNextBr) {
                sNextSentence = sParagraph.substring(iLastSentenceEnd + 1, iPos);
                iLastSentenceEnd = iPos + 3;
                iNextBr = sParagraph.indexOf("<br>", iNextBr + 2);
            } else if (this.b_IsSentenceEndPunctuation(sNextChar)) {
                bPunctuationIndicatesSentenceEnd = true;
            } else if (sNextChar.compareTo(" ") == 0) {
                if (bPunctuationIndicatesSentenceEnd) {
                    sNextSentence = sParagraph.substring(iLastSentenceEnd + 1, iPos);
                    iLastSentenceEnd = iPos;
                }
            } else if (this.b_IsAlphanumeric(sNextChar) && bPunctuationIndicatesSentenceEnd) {
                sNextSentence = sParagraph.substring(iLastSentenceEnd + 1, iPos);
                iLastSentenceEnd = iPos - 1;
            }

            if (!sNextSentence.equals("")) {
                ++this.igSentenceCount;
                this.sentence[this.igSentenceCount] = new Sentence();
                this.sentence[this.igSentenceCount].setSentence(sNextSentence, this.resources, this.options);
                sNextSentence = "";
                bPunctuationIndicatesSentenceEnd = false;
            }
        }

    }

    /**
     * 获取情绪词表
     *
     * @return 情绪词表
     */
    public int[] getSentimentIDList() {
        if (!this.bSentimentIDListMade) {
            this.makeSentimentIDList();
        }

        return this.igSentimentIDList;
    }

    /**
     * 获取分类结果
     *
     * @return 分类结果
     */
    public String getClassificationRationale() {
        return this.sgClassificationRationale;
    }

    /**
     * 制作情绪词表
     */
    public void makeSentimentIDList() {
        boolean bIsDuplicate = false;
        this.igSentimentIDListCount = 0;

        int i;
        for (i = 1; i <= this.igSentenceCount; ++i) {
            if (this.sentence[i].getSentimentIDList() != null) {
                this.igSentimentIDListCount += this.sentence[i].getSentimentIDList().length;
            }
        }

        if (this.igSentimentIDListCount > 0) {
            this.igSentimentIDList = new int[this.igSentimentIDListCount + 1];
            this.igSentimentIDListCount = 0;

            for (i = 1; i <= this.igSentenceCount; ++i) {
                int[] sentenceIDList = this.sentence[i].getSentimentIDList();
                if (sentenceIDList != null) {
                    for (int j = 1; j < sentenceIDList.length; ++j) {
                        if (sentenceIDList[j] != 0) {
                            bIsDuplicate = false;

                            for (int k = 1; k <= this.igSentimentIDListCount; ++k) {
                                if (sentenceIDList[j] == this.igSentimentIDList[k]) {
                                    bIsDuplicate = true;
                                    break;
                                }
                            }

                            if (!bIsDuplicate) {
                                this.igSentimentIDList[++this.igSentimentIDListCount] = sentenceIDList[j];
                            }
                        }
                    }
                }
            }

            Sort.quickSortInt(this.igSentimentIDList, 1, this.igSentimentIDListCount);
        }

        this.bSentimentIDListMade = true;
    }

    /**
     * 获得标记段落
     *
     * @return 标记段落
     */
    public String getTaggedParagraph() {
        String sTagged = "";

        for (int i = 1; i <= this.igSentenceCount; ++i) {
            sTagged = sTagged + this.sentence[i].getTaggedSentence();
        }

        return sTagged;
    }

    /**
     * 获取翻译完成的段落
     *
     * @return 翻译完成的段落
     */
    public String getTranslatedParagraph() {
        String sTranslated = "";

        for (int i = 1; i <= this.igSentenceCount; ++i) {
            sTranslated = sTranslated + this.sentence[i].getTranslatedSentence();
        }

        return sTranslated;
    }

    /**
     * 重新计算段落情绪词得分
     */
    public void recalculateParagraphSentimentScores() {
        for (int iSentence = 1; iSentence <= this.igSentenceCount; ++iSentence) {
            this.sentence[iSentence].recalculateSentenceSentimentScore();
        }

        this.calculateParagraphSentimentScores();
    }

    /**
     * 根据情绪词变化重定义段落分类
     *
     * @param iSentimentWordID 情绪词类型
     */
    public void reClassifyClassifiedParagraphForSentimentChange(int iSentimentWordID) {
        if (this.igNegativeSentiment == 0) {
            this.calculateParagraphSentimentScores();
        } else {
            if (!this.bSentimentIDListMade) {
                this.makeSentimentIDList();
            }

            if (this.igSentimentIDListCount != 0) {
                if (Sort.i_FindIntPositionInSortedArray(iSentimentWordID, this.igSentimentIDList, 1, this.igSentimentIDListCount) >= 0) {
                    for (int iSentence = 1; iSentence <= this.igSentenceCount; ++iSentence) {
                        this.sentence[iSentence].reClassifyClassifiedSentenceForSentimentChange(iSentimentWordID);
                    }

                    this.calculateParagraphSentimentScores();
                }

            }
        }
    }

    /**
     * 获取段落中积极情绪词
     *
     * @return 段落中积极情绪词
     */
    public int getParagraphPositiveSentiment() {
        if (this.igPositiveSentiment == 0) {
            this.calculateParagraphSentimentScores();
        }

        return this.igPositiveSentiment;
    }

    /**
     * 获取段落中消极情绪词
     *
     * @return 消极情绪词
     */
    public int getParagraphNegativeSentiment() {
        if (this.igNegativeSentiment == 0) {
            this.calculateParagraphSentimentScores();
        }

        return this.igNegativeSentiment;
    }

    /**
     * 获取段落中三元情绪词
     *
     * @return 三元情绪词
     */
    public int getParagraphTrinarySentiment() {
        if (this.igNegativeSentiment == 0) {
            this.calculateParagraphSentimentScores();
        }

        return this.igTrinarySentiment;
    }

    /**
     * 获取段落中情绪词级别
     *
     * @return 情绪词级别
     */
    public int getParagraphScaleSentiment() {
        if (this.igNegativeSentiment == 0) {
            this.calculateParagraphSentimentScores();
        }

        return this.igScaleSentiment;
    }

    /**
     * @param sChar 文本
     * @return 判断文本是否以标点符号结束
     */
    private boolean b_IsSentenceEndPunctuation(String sChar) {
        return sChar.compareTo(".") == 0 || sChar.compareTo("!") == 0 || sChar.compareTo("?") == 0;
    }

    /**
     * @param sChar 文本
     * @return 判断文本是否全字母
     */
    private boolean b_IsAlphanumeric(String sChar) {
        return sChar.compareToIgnoreCase("a") >= 0 && sChar.compareToIgnoreCase("z") <= 0 || sChar.compareTo("0") >= 0 && sChar.compareTo("9") <= 0 || sChar.compareTo("$") == 0 || sChar.compareTo("£") == 0 || sChar.compareTo("'") == 0;
    }

    /**
     * 计算段落情绪词得分
     */
    private void calculateParagraphSentimentScores() {
        this.igPositiveSentiment = 1;
        this.igNegativeSentiment = -1;
        this.igTrinarySentiment = 0;
        if (this.options.bgExplainClassification && this.sgClassificationRationale.length() > 0) {
            this.sgClassificationRationale = "";
        }

        int iPosTotal = 0;
        int iPosMax = 0;
        int iNegTotal = 0;
        int iNegMax = 0;
        int iPosTemp = 0;
        int iNegTemp = 0;
        int iSentencesUsed = 0;
        int wordNum = 0;
        int sentiNum = 0;
        if (this.igSentenceCount != 0) {
            int iNegTot;
            for (iNegTot = 1; iNegTot <= this.igSentenceCount; ++iNegTot) {
                iNegTemp = this.sentence[iNegTot].getSentenceNegativeSentiment();
                iPosTemp = this.sentence[iNegTot].getSentencePositiveSentiment();
                wordNum += this.sentence[iNegTot].getIgTermCount();
                sentiNum += this.sentence[iNegTot].getIgSentiCount();
                if (iNegTemp != 0 || iPosTemp != 0) {
                    iNegTotal += iNegTemp;
                    ++iSentencesUsed;
                    if (iNegMax > iNegTemp) {
                        iNegMax = iNegTemp;
                    }

                    iPosTotal += iPosTemp;
                    if (iPosMax < iPosTemp) {
                        iPosMax = iPosTemp;
                    }
                }

                if (this.options.bgExplainClassification) {
                    this.sgClassificationRationale = this.sgClassificationRationale + this.sentence[iNegTot].getClassificationRationale() + " ";
                }
            }

            int var10000;
            if (iNegTotal == 0) {
                var10000 = this.options.igEmotionParagraphCombineMethod;
                this.options.getClass();
                if (var10000 != 2) {
                    this.igPositiveSentiment = 0;
                    this.igNegativeSentiment = 0;
                    this.igTrinarySentiment = this.binarySelectionTieBreaker();
                }
            }

            var10000 = this.options.igEmotionParagraphCombineMethod;
            this.options.getClass();
            if (var10000 == 1) {
                this.igPositiveSentiment = (int) ((double) ((float) iPosTotal / (float) iSentencesUsed) + 0.5D);
                this.igNegativeSentiment = (int) ((double) ((float) iNegTotal / (float) iSentencesUsed) - 0.5D);
                if (this.options.bgExplainClassification) {
                    this.sgClassificationRationale = this.sgClassificationRationale + "[result = average (" + iPosTotal + " and " + iNegTotal + ") of " + iSentencesUsed + " sentences]";
                }
            } else {
                var10000 = this.options.igEmotionParagraphCombineMethod;
                this.options.getClass();
                if (var10000 == 2) {
                    this.igPositiveSentiment = iPosTotal;
                    this.igNegativeSentiment = iNegTotal;
                    if (this.options.bgExplainClassification) {
                        this.sgClassificationRationale = this.sgClassificationRationale + "[result: total positive; total negative]";
                    }
                } else {
                    this.igPositiveSentiment = iPosMax;
                    this.igNegativeSentiment = iNegMax;
                    if (this.options.bgExplainClassification) {
                        this.sgClassificationRationale = this.sgClassificationRationale + "[result: max + and - of any sentence]";
                    }
                }
            }

            var10000 = this.options.igEmotionParagraphCombineMethod;
            this.options.getClass();
            if (var10000 != 2) {
                if (this.igPositiveSentiment == 0) {
                    this.igPositiveSentiment = 1;
                }

                if (this.igNegativeSentiment == 0) {
                    this.igNegativeSentiment = -1;
                }
            }

            if (this.options.bgScaleMode) {
                this.igScaleSentiment = this.igPositiveSentiment + this.igNegativeSentiment;
                if (this.options.bgExplainClassification) {
                    this.sgClassificationRationale = this.sgClassificationRationale + "[scale result = sum of pos and neg scores]";
                }

            } else {
                var10000 = this.options.igEmotionParagraphCombineMethod;
                this.options.getClass();
                if (var10000 == 2) {
                    if (this.igPositiveSentiment == 0 && this.igNegativeSentiment == 0) {
                        if (this.options.bgBinaryVersionOfTrinaryMode) {
                            this.igTrinarySentiment = this.options.igDefaultBinaryClassification;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[binary result set to default value]";
                            }
                        } else {
                            this.igTrinarySentiment = 0;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[trinary result 0 as pos=1, neg=-1]";
                            }
                        }
                    } else {
                        if ((float) this.igPositiveSentiment > this.options.fgNegativeSentimentMultiplier * (float) (-this.igNegativeSentiment)) {
                            this.igTrinarySentiment = 1;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[overall result 1 as pos > -neg * " + this.options.fgNegativeSentimentMultiplier + "]";
                            }


                        }

                        if ((float) this.igPositiveSentiment < this.options.fgNegativeSentimentMultiplier * (float) (-this.igNegativeSentiment)) {
                            this.igTrinarySentiment = -1;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[overall result -1 as pos < -neg * " + this.options.fgNegativeSentimentMultiplier + "]";
                            }


                        }

                        if (this.options.bgBinaryVersionOfTrinaryMode) {
                            this.igTrinarySentiment = this.options.igDefaultBinaryClassification;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[binary result = default value as pos = -neg * " + this.options.fgNegativeSentimentMultiplier + "]";
                            }
                        } else {
                            this.igTrinarySentiment = 0;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[trinary result = 0 as pos = -neg * " + this.options.fgNegativeSentimentMultiplier + "]";
                            }
                        }
                    }
                } else {
                    if (this.igPositiveSentiment == 1 && this.igNegativeSentiment == -1) {
                        if (this.options.bgBinaryVersionOfTrinaryMode) {
                            this.igTrinarySentiment = this.binarySelectionTieBreaker();
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[binary result = default value as pos=1 neg=-1]";
                            }
                        } else {
                            this.igTrinarySentiment = 0;
                            if (this.options.bgExplainClassification) {
                                this.sgClassificationRationale = this.sgClassificationRationale + "[trinary result = 0 as pos=1 neg=-1]";
                            }
                        }


                    }

                    if (this.igPositiveSentiment > -this.igNegativeSentiment) {
                        this.igTrinarySentiment = 1;
                        if (this.options.bgExplainClassification) {
                            this.sgClassificationRationale = this.sgClassificationRationale + "[overall result = 1 as pos>-neg]";
                        }


                    }

                    if (this.igPositiveSentiment < -this.igNegativeSentiment) {
                        this.igTrinarySentiment = -1;
                        if (this.options.bgExplainClassification) {
                            this.sgClassificationRationale = this.sgClassificationRationale + "[overall result = -1 as pos<-neg]";
                        }


                    }

                    iNegTot = 0;
                    int iPosTot = 0;

                    for (int iSentence = 1; iSentence <= this.igSentenceCount; ++iSentence) {
                        iNegTot += this.sentence[iSentence].getSentenceNegativeSentiment();
                        iPosTot = this.sentence[iSentence].getSentencePositiveSentiment();
                    }

                    if (this.options.bgBinaryVersionOfTrinaryMode && iPosTot == -iNegTot) {
                        this.igTrinarySentiment = this.binarySelectionTieBreaker();
                        if (this.options.bgExplainClassification) {
                            this.sgClassificationRationale = this.sgClassificationRationale + "[binary result = default as posSentenceTotal>-negSentenceTotal]";
                        }
                    } else {
                        if (this.options.bgExplainClassification) {
                            this.sgClassificationRationale = this.sgClassificationRationale + "[overall result = largest of posSentenceTotal, negSentenceTotal]";
                        }

                        if (iPosTot > -iNegTot) {
                            this.igTrinarySentiment = 1;
                        } else {
                            this.igTrinarySentiment = -1;
                        }
                    }
                }

            }
        }
    }

    /**
     * 二元选择
     *
     * @return 选择项
     */
    private int binarySelectionTieBreaker() {
        if (this.options.igDefaultBinaryClassification != 1 && this.options.igDefaultBinaryClassification != -1) {
            return this.generator.nextDouble() > 0.5D ? 1 : -1;
        } else {
            return this.options.igDefaultBinaryClassification != 1 && this.options.igDefaultBinaryClassification != -1 ? this.options.igDefaultBinaryClassification : this.options.igDefaultBinaryClassification;
        }
    }
}
