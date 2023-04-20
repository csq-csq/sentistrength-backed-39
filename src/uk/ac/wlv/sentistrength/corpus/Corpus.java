// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   Corpus.java

package uk.ac.wlv.sentistrength.corpus;
import java.io.*;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.sentistrength.classification.ClassificationResources;
import uk.ac.wlv.sentistrength.classification.ClassificationStatistics;
import uk.ac.wlv.sentistrength.classification.UnusedTermsClassificationIndex;
import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl.OptimiseDictionaryWeightingsForCorpusStrategy;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;


// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions, ClassificationResources, UnusedTermsClassificationIndex, Paragraph,
//            ClassificationStatistics, SentimentWords


/**
 * 语料库类
 *
 * @author 201850081
 * date: 2023/03/07
 */
public class Corpus {
    /**
     * 分类选项类的对象，保存各选项
     */
    public ClassificationOptions options;

    /**
     * 资源汇总类的对象，保存各文件资源
     */
    public ClassificationResources resources;
    /**
     * 段类型的数组
     */
    private Paragraph paragraph[];
    /**
     * 段落数
     */
    private int igParagraphCount;
    /**
     * 肯定词的正确分类数量数组
     */
    private int igPosCorrect[];
    /**
     * 否定词的正确分类数量数组
     */
    private int igNegCorrect[];
    /**
     * 三元分类的正确分类数量数组
     */
    private int igTrinaryCorrect[];
    /**
     * 缩放的正确分类数量数组
     */
    private int igScaleCorrect[];
    /**
     * 肯定词类数量数组
     */
    private int igPosClass[];
    /**
     * 否定词类数量数组
     */
    private int igNegClass[];
    /**
     * 三元分类类数量数组
     */
    private int igTrinaryClass[];
    /**
     * 缩放类数量数组
     */
    private int igScaleClass[];
    /**
     * 语料库分类数量数组
     */
    private boolean bgCorpusClassified;
    /**
     * 情感id列表数量数组
     */
    private int igSentimentIDList[];
    /**
     * 情感id列表计数
     */
    private int igSentimentIDListCount;
    /**
     * 情绪id字段计数
     */
    private int igSentimentIDParagraphCount[];
    /**
     * 情绪id列表是否创建
     */
    private boolean bSentimentIDListMade;
    /**
     * 未使用术语分类索引类
     */
    UnusedTermsClassificationIndex unusedTermsClassificationIndex;
    /**
     * sup语料库成员是否存在
     */
    private boolean bgSupcorpusMember[];
    /**
     * sup语料库成员数
     */
    int igSupcorpusMemberCount;


    /**
     * 语料库构造函数，完成初始化工作
     *
     */
    public Corpus() {
        options = new ClassificationOptions();
        resources = new ClassificationResources();
        igParagraphCount = 0;
        bgCorpusClassified = false;
        igSentimentIDListCount = 0;
        bSentimentIDListMade = false;
        unusedTermsClassificationIndex = null;
    }

    /**
     * 根据选项，选择分类语料库编号
     *
     * @return
     */
    public void indexClassifiedCorpus() {
        unusedTermsClassificationIndex = new UnusedTermsClassificationIndex();
        if (options.bgScaleMode) {
            unusedTermsClassificationIndex.initialise(true, false, false, false);
            for (int i = 1; i <= igParagraphCount; i++)
                paragraph[i].addParagraphToIndexWithScaleValues(unusedTermsClassificationIndex, igScaleCorrect[i], igScaleClass[i]);

        } else if (options.bgTrinaryMode && options.bgBinaryVersionOfTrinaryMode) {
            unusedTermsClassificationIndex.initialise(false, false, true, false);
            for (int i = 1; i <= igParagraphCount; i++)
                paragraph[i].addParagraphToIndexWithBinaryValues(unusedTermsClassificationIndex, igTrinaryCorrect[i], igTrinaryClass[i]);

        } else if (options.bgTrinaryMode && !options.bgBinaryVersionOfTrinaryMode) {
            unusedTermsClassificationIndex.initialise(false, false, false, true);
            for (int i = 1; i <= igParagraphCount; i++)
                paragraph[i].addParagraphToIndexWithTrinaryValues(unusedTermsClassificationIndex, igTrinaryCorrect[i], igTrinaryClass[i]);

        } else {
            unusedTermsClassificationIndex.initialise(false, true, false, false);
            for (int i = 1; i <= igParagraphCount; i++)
                paragraph[i].addParagraphToIndexWithPosNegValues(unusedTermsClassificationIndex, igPosCorrect[i], igPosClass[i], igNegCorrect[i], igNegClass[i]);
        }
    }

    /**
     * 打印语料库中未使用的术语分类索引
     *
     * @param saveFile 保存的文件路径
     * @param iMinFreq 最小频率
     * @return
     */
    public void printCorpusUnusedTermsClassificationIndex(String saveFile, int iMinFreq) {
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        if (unusedTermsClassificationIndex == null)
            indexClassifiedCorpus();
        if (options.bgScaleMode)
            unusedTermsClassificationIndex.printIndexWithScaleValues(saveFile, iMinFreq);
        else if (options.bgTrinaryMode && options.bgBinaryVersionOfTrinaryMode)
            unusedTermsClassificationIndex.printIndexWithBinaryValues(saveFile, iMinFreq);
        else if (options.bgTrinaryMode && !options.bgBinaryVersionOfTrinaryMode)
            unusedTermsClassificationIndex.printIndexWithTrinaryValues(saveFile, iMinFreq);
        else
            unusedTermsClassificationIndex.printIndexWithPosNegValues(saveFile, iMinFreq);
        System.out.println((new StringBuilder("Term weights saved to ")).append(saveFile).toString());
    }

    /**
     * 设置子语料库
     *
     * @param bSubcorpusMember 是否是子语料库成员
     * @return
     */
    public void setSubcorpus(boolean bSubcorpusMember[]) {
        igSupcorpusMemberCount = 0;
        for (int i = 0; i <= igParagraphCount; i++)
            if (bSubcorpusMember[i]) {
                bgSupcorpusMember[i] = true;
                igSupcorpusMemberCount++;
            } else {
                bgSupcorpusMember[i] = false;
            }

    }

    /**
     * 使用整个语料库
     */
    public void useWholeCorpusNotSubcorpus() {
        for (int i = 0; i <= igParagraphCount; i++)
            bgSupcorpusMember[i] = true;

        igSupcorpusMemberCount = igParagraphCount;
    }

    /**
     * 得到语料库大小
     *
     * @return igParagraphCount 段落数
     */
    public int getCorpusSize() {
        return igParagraphCount;
    }

    /**
     * 设置单独的文本语料库
     *
     * @param sText       语料文本
     * @param iPosCorrect 积极的调整指数
     * @param iNegCorrect 消极的调整指数
     * @return boolean 设置是否完成
     */
    public boolean setSingleTextAsCorpus(String sText, int iPosCorrect, int iNegCorrect) {
        if (resources == null && !resources.initialise(options))
            return false;
        igParagraphCount = 2;
        paragraph = new Paragraph[igParagraphCount];
        igPosCorrect = new int[igParagraphCount];
        igNegCorrect = new int[igParagraphCount];
        igTrinaryCorrect = new int[igParagraphCount];
        igScaleCorrect = new int[igParagraphCount];
        bgSupcorpusMember = new boolean[igParagraphCount];
        igParagraphCount = 1;
        paragraph[igParagraphCount] = new Paragraph();
        paragraph[igParagraphCount].setParagraph(sText, resources, options);
        igPosCorrect[igParagraphCount] = iPosCorrect;
        if (iNegCorrect < 0)
            iNegCorrect *= -1;
        igNegCorrect[igParagraphCount] = iNegCorrect;
        useWholeCorpusNotSubcorpus();
        return true;
    }

    /**
     * 根据输入的文本文件更新语料库
     *
     * @param sInFilenameAndPath 输入的目标文件路径和文件名
     * @return boolean 是否更新成功
     */
    public boolean setCorpus(String sInFilenameAndPath) {
        if (resources == null && !resources.initialise(options))
            return false;
        igParagraphCount = FileOps.i_CountLinesInTextFile(sInFilenameAndPath) + 1;
        if (igParagraphCount <= 2) {
            igParagraphCount = 0;
            return false;
        }
        paragraph = new Paragraph[igParagraphCount];
        igPosCorrect = new int[igParagraphCount];
        igNegCorrect = new int[igParagraphCount];
        igTrinaryCorrect = new int[igParagraphCount];
        igScaleCorrect = new int[igParagraphCount];
        bgSupcorpusMember = new boolean[igParagraphCount];
        igParagraphCount = 0;
        try {
            BufferedReader rReader = new BufferedReader(new FileReader(sInFilenameAndPath));
            String sLine;
            if (rReader.ready())
                sLine = rReader.readLine();
            while ((sLine = rReader.readLine()) != null)
                if (sLine != "") {
                    paragraph[++igParagraphCount] = new Paragraph();
                    int iLastTabPos = sLine.lastIndexOf("\t");
                    int iFirstTabPos = sLine.indexOf("\t");
                    if (iFirstTabPos < iLastTabPos || iFirstTabPos > 0 && (options.bgTrinaryMode || options.bgScaleMode)) {
                        paragraph[igParagraphCount].setParagraph(sLine.substring(iLastTabPos + 1), resources, options);
                        if (options.bgTrinaryMode) {
                            try {
                                igTrinaryCorrect[igParagraphCount] = Integer.parseInt(sLine.substring(0, iFirstTabPos).trim());
                            } catch (Exception e) {
                                System.out.println((new StringBuilder("Trinary classification could not be read and will be ignored!: ")).append(sLine).toString());
                                igTrinaryCorrect[igParagraphCount] = 999;
                            }
                            if (igTrinaryCorrect[igParagraphCount] > 1 || igTrinaryCorrect[igParagraphCount] < -1) {
                                System.out.println((new StringBuilder("Trinary classification out of bounds and will be ignored!: ")).append(sLine).toString());
                                igParagraphCount--;
                            } else if (options.bgBinaryVersionOfTrinaryMode && igTrinaryCorrect[igParagraphCount] == 0)
                                System.out.println((new StringBuilder("Warning, unexpected 0 in binary classification!: ")).append(sLine).toString());
                        } else if (options.bgScaleMode) {
                            try {
                                igScaleCorrect[igParagraphCount] = Integer.parseInt(sLine.substring(0, iFirstTabPos).trim());
                            } catch (Exception e) {
                                System.out.println((new StringBuilder("Scale classification could not be read and will be ignored!: ")).append(sLine).toString());
                                igScaleCorrect[igParagraphCount] = 999;
                            }
                            if (igScaleCorrect[igParagraphCount] > 4 || igTrinaryCorrect[igParagraphCount] < -4) {
                                System.out.println((new StringBuilder("Scale classification out of bounds (-4 to +4) and will be ignored!: ")).append(sLine).toString());
                                igParagraphCount--;
                            }
                        } else {
                            try {
                                igPosCorrect[igParagraphCount] = Integer.parseInt(sLine.substring(0, iFirstTabPos).trim());
                                igNegCorrect[igParagraphCount] = Integer.parseInt(sLine.substring(iFirstTabPos + 1, iLastTabPos).trim());
                                if (igNegCorrect[igParagraphCount] < 0)
                                    igNegCorrect[igParagraphCount] = -igNegCorrect[igParagraphCount];
                            } catch (Exception e) {
                                System.out.println((new StringBuilder("Positive or negative classification could not be read and will be ignored!: ")).append(sLine).toString());
                                igPosCorrect[igParagraphCount] = 0;
                            }
                            if (igPosCorrect[igParagraphCount] > 5 || igPosCorrect[igParagraphCount] < 1) {
                                System.out.println((new StringBuilder("Warning, positive classification out of bounds and line will be ignored!: ")).append(sLine).toString());
                                igParagraphCount--;
                            } else if (igNegCorrect[igParagraphCount] > 5 || igNegCorrect[igParagraphCount] < 1) {
                                System.out.println((new StringBuilder("Warning, negative classification out of bounds (must be 1,2,3,4, or 5, with or without -) and line will be ignored!: ")).append(sLine).toString());
                                igParagraphCount--;
                            }
                        }
                    } else {
                        if (iFirstTabPos >= 0) {
                            if (options.bgTrinaryMode)
                                igTrinaryCorrect[igParagraphCount] = Integer.parseInt(sLine.substring(0, iFirstTabPos).trim());
                            sLine = sLine.substring(iFirstTabPos + 1);
                        } else if (options.bgTrinaryMode)
                            igTrinaryCorrect[igParagraphCount] = 0;
                        paragraph[igParagraphCount].setParagraph(sLine, resources, options);
                        igPosCorrect[igParagraphCount] = 0;
                        igNegCorrect[igParagraphCount] = 0;
                    }
                }
            rReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        useWholeCorpusNotSubcorpus();
        System.out.println((new StringBuilder("Number of texts in corpus: ")).append(igParagraphCount).toString());
        return true;
    }

    /**
     * 初始化程序
     *
     * @return boolean 是否初始化成功
     */
    public boolean initialise() {
        return resources.initialise(options);
    }

    /**
     * 重新计算语料库的情绪得分
     *
     * @return
     */
    public void reCalculateCorpusSentimentScores() {
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i])
                paragraph[i].recalculateParagraphSentimentScores();

        calculateCorpusSentimentScores();
    }

    /**
     * 获得语料库成员的积极情绪得分
     *
     * @param i 语料库成员编号
     * @return int 积极情绪得分
     */
    public int getCorpusMemberPositiveSentimentScore(int i) {
        if (i < 1 || i > igParagraphCount)
            return 0;
        else
            return paragraph[i].getParagraphPositiveSentiment();
    }

    /**
     * 获得语料库成员的负面情绪得分
     *
     * @param i 语料库成员编号
     * @return int 负面情绪得分
     */
    public int getCorpusMemberNegativeSentimentScore(int i) {
        if (i < 1 || i > igParagraphCount)
            return 0;
        else
            return paragraph[i].getParagraphNegativeSentiment();
    }

    /**
     * 计算语料库的情绪得分
     *
     * @return
     */
    public void calculateCorpusSentimentScores() {
        if (igParagraphCount == 0)
            return;
        if (igPosClass == null || igPosClass.length < igPosCorrect.length) {
            igPosClass = new int[igParagraphCount + 1];
            igNegClass = new int[igParagraphCount + 1];
            igTrinaryClass = new int[igParagraphCount + 1];
            igScaleClass = new int[igParagraphCount + 1];
        }
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i]) {
                igPosClass[i] = paragraph[i].getParagraphPositiveSentiment();
                igNegClass[i] = paragraph[i].getParagraphNegativeSentiment();
                if (options.bgTrinaryMode)
                    igTrinaryClass[i] = paragraph[i].getParagraphTrinarySentiment();
                if (options.bgScaleMode)
                    igScaleClass[i] = paragraph[i].getParagraphScaleSentiment();
            }

        bgCorpusClassified = true;
    }

    /**
     * 因情绪变化，对分类语料库重新分类
     *
     * @param iSentimentWordID       情绪词的id
     * @param iMinParasToContainWord 构成行最少需要包含的词语数
     * @return
     */
    public void reClassifyClassifiedCorpusForSentimentChange(int iSentimentWordID, int iMinParasToContainWord) {
        if (igParagraphCount == 0)
            return;
        if (!bSentimentIDListMade)
            makeSentimentIDListForCompleteCorpusIgnoringSubcorpus();
        int iSentimentWordIDArrayPos = Sort.i_FindIntPositionInSortedArray(iSentimentWordID, igSentimentIDList, 1, igSentimentIDListCount);
        if (iSentimentWordIDArrayPos == -1 || igSentimentIDParagraphCount[iSentimentWordIDArrayPos] < iMinParasToContainWord)
            return;
        igPosClass = new int[igParagraphCount + 1];
        igNegClass = new int[igParagraphCount + 1];
        if (options.bgTrinaryMode)
            igTrinaryClass = new int[igParagraphCount + 1];
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i]) {
                paragraph[i].reClassifyClassifiedParagraphForSentimentChange(iSentimentWordID);
                igPosClass[i] = paragraph[i].getParagraphPositiveSentiment();
                igNegClass[i] = paragraph[i].getParagraphNegativeSentiment();
                if (options.bgTrinaryMode)
                    igTrinaryClass[i] = paragraph[i].getParagraphTrinarySentiment();
                if (options.bgScaleMode)
                    igScaleClass[i] = paragraph[i].getParagraphScaleSentiment();
            }

        bgCorpusClassified = true;
    }

    /**
     * 打印语料库中的情绪得分
     *
     * @param sOutFilenameAndPath 输出的目标文件名和文件路径
     * @return boolean 打印是否成功
     */
    public boolean printCorpusSentimentScores(String sOutFilenameAndPath) {
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutFilenameAndPath));
            wWriter.write("Correct+\tCorrect-\tPredict+\tPredict-\tText\n");
            for (int i = 1; i <= igParagraphCount; i++)
                if (bgSupcorpusMember[i])
                    wWriter.write((new StringBuilder(String.valueOf(igPosCorrect[i]))).append("\t").append(igNegCorrect[i]).append("\t").append(igPosClass[i]).append("\t").append(igNegClass[i]).append("\t").append(paragraph[i].getTaggedParagraph()).append("\n").toString());

            wWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 得到分类积极情感的准确率
     *
     * @return float 准确率
     */
    public float getClassificationPositiveAccuracyProportion() {
        if (igSupcorpusMemberCount == 0)
            return 0.0F;
        else
            return (float) getClassificationPositiveNumberCorrect() / (float) igSupcorpusMemberCount;
    }

    /**
     * 得到分类负面情感的准确率
     *
     * @return float 准确率
     */
    public float getClassificationNegativeAccuracyProportion() {
        if (igSupcorpusMemberCount == 0)
            return 0.0F;
        else
            return (float) getClassificationNegativeNumberCorrect() / (float) igSupcorpusMemberCount;
    }


    /**
     * 获得负面情感准确率的基线
     *
     * @return double 基线
     */
    public double getBaselineNegativeAccuracyProportion() {
        if (igParagraphCount == 0)
            return 0.0D;
        else
            return ClassificationStatistics.baselineAccuracyMajorityClassProportion(igNegCorrect, igParagraphCount);
    }

    /**
     * 获得积极情感准确率的基线
     *
     * @return double 基线
     * @see double
     */
    public double getBaselinePositiveAccuracyProportion() {
        if (igParagraphCount == 0)
            return 0.0D;
        else
            return ClassificationStatistics.baselineAccuracyMajorityClassProportion(igPosCorrect, igParagraphCount);
    }

    /**
     * 得到被正确分类的负面情感数
     *
     * @return int 负面情感数
     */
    public int getClassificationNegativeNumberCorrect() {
        if (igParagraphCount == 0)
            return 0;
        int iMatches = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i] && igNegCorrect[i] == -igNegClass[i])
                iMatches++;

        return iMatches;
    }

    /**
     * 得到被正确分类的积极情感数
     *
     * @return int 积极情感数
     */
    public int getClassificationPositiveNumberCorrect() {
        if (igParagraphCount == 0)
            return 0;
        int iMatches = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i] && igPosCorrect[i] == igPosClass[i])
                iMatches++;

        return iMatches;
    }

    /**
     * 得到语料库中分类为肯定词与实际的平均差
     *
     * @return double 平均差
     */
    public double getClassificationPositiveMeanDifference() {
        if (igParagraphCount == 0)
            return 0.0D;
        double fTotalDiff = 0.0D;
        int iTotal = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i]) {
                fTotalDiff += Math.abs(igPosCorrect[i] - igPosClass[i]);
                iTotal++;
            }

        if (iTotal > 0)
            return fTotalDiff / (double) iTotal;
        else
            return 0.0D;
    }

    /**
     * 得到语料库中分类为肯定词与实际的总差
     *
     * @return int  总差
     */
    public int getClassificationPositiveTotalDifference() {
        if (igParagraphCount == 0)
            return 0;
        int iTotalDiff = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i])
                iTotalDiff += Math.abs(igPosCorrect[i] - igPosClass[i]);

        return iTotalDiff;
    }

    /**
     * 得到正确分类的三元分类数量
     *
     * @return int 三元分类数量
     */
    public int getClassificationTrinaryNumberCorrect() {
        if (igParagraphCount == 0)
            return 0;
        int iTrinaryCorrect = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i] && igTrinaryCorrect[i] == igTrinaryClass[i])
                iTrinaryCorrect++;

        return iTrinaryCorrect;
    }

    /**
     * 得到整个语料库分类的缩放相关性
     *
     * @return float 语料库规模
     */
    public float getClassificationScaleCorrelationWholeCorpus() {
        if (igParagraphCount == 0)
            return 0.0F;
        else
            return (float) ClassificationStatistics.correlation(igScaleCorrect, igScaleClass, igParagraphCount);
    }

    /**
     * 得到分类缩放的准确率
     *
     * @return float 准确率
     */
    public float getClassificationScaleAccuracyProportion() {
        if (igSupcorpusMemberCount == 0)
            return 0.0F;
        else
            return (float) getClassificationScaleNumberCorrect() / (float) igSupcorpusMemberCount;
    }

    /**
     * 得到语料库中分类为肯定词的相关性
     *
     * @return float 相关性
     */
    public float getClassificationPosCorrelationWholeCorpus() {
        if (igParagraphCount == 0)
            return 0.0F;
        else
            return (float) ClassificationStatistics.correlationAbs(igPosCorrect, igPosClass, igParagraphCount);
    }

    /**
     * 得到语料库中分类为否定词的相关性
     *
     * @return float 相关性
     */
    public float getClassificationNegCorrelationWholeCorpus() {
        if (igParagraphCount == 0)
            return 0.0F;
        else
            return (float) ClassificationStatistics.correlationAbs(igNegCorrect, igNegClass, igParagraphCount);
    }

    /**
     * 得到分类缩放正确的数量
     *
     * @return int 缩放正确的数量
     */
    public int getClassificationScaleNumberCorrect() {
        if (igParagraphCount == 0)
            return 0;
        int iScaleCorrect = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i] && igScaleCorrect[i] == igScaleClass[i])
                iScaleCorrect++;

        return iScaleCorrect;
    }

    /**
     * 得到分类为否定词的数量与实际的总差
     *
     * @return int 总差
     */
    public int getClassificationNegativeTotalDifference() {
        if (igParagraphCount == 0)
            return 0;
        int iTotalDiff = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i])
                iTotalDiff += Math.abs(igNegCorrect[i] + igNegClass[i]);

        return iTotalDiff;
    }

    /**
     * 得到分类为否定词的数量与实际的平均差
     *
     * @return double 平均差
     */
    public double getClassificationNegativeMeanDifference() {
        if (igParagraphCount == 0)
            return 0.0D;
        double fTotalDiff = 0.0D;
        int iTotal = 0;
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        for (int i = 1; i <= igParagraphCount; i++)
            if (bgSupcorpusMember[i]) {
                fTotalDiff += Math.abs(igNegCorrect[i] + igNegClass[i]);
                iTotal++;
            }

        if (iTotal > 0)
            return fTotalDiff / (double) iTotal;
        else
            return 0.0D;
    }

    /**
     * 打印分类结果（未完成归纳）
     *
     * @param sOutFilenameAndPath 输出为目标文件的文件路径名和文件名
     * @return boolean 是否成功输出
     */
    public boolean printClassificationResultsSummary_NOT_DONE(String sOutFilenameAndPath) {
        if (!bgCorpusClassified)
            calculateCorpusSentimentScores();
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutFilenameAndPath));
            for (int i = 1; i <= igParagraphCount; i++) {
                boolean _tmp = bgSupcorpusMember[i];
            }

            wWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 为除了子语料库之外的整个语料库建立情绪词id列表
     *
     * @return
     */
    public void makeSentimentIDListForCompleteCorpusIgnoringSubcorpus() {
        igSentimentIDListCount = 0;
        for (int i = 1; i <= igParagraphCount; i++) {
            paragraph[i].makeSentimentIDList();
            if (paragraph[i].getSentimentIDList() != null)
                igSentimentIDListCount += paragraph[i].getSentimentIDList().length;
        }

        if (igSentimentIDListCount > 0) {
            igSentimentIDList = new int[igSentimentIDListCount + 1];
            igSentimentIDParagraphCount = new int[igSentimentIDListCount + 1];
            igSentimentIDListCount = 0;
            for (int i = 1; i <= igParagraphCount; i++) {
                int sentenceIDList[] = paragraph[i].getSentimentIDList();
                if (sentenceIDList != null) {
                    for (int j = 0; j < sentenceIDList.length; j++)
                        if (sentenceIDList[j] != 0)
                            igSentimentIDList[++igSentimentIDListCount] = sentenceIDList[j];

                }
            }

            Sort.quickSortInt(igSentimentIDList, 1, igSentimentIDListCount);
            for (int i = 1; i <= igParagraphCount; i++) {
                int sentenceIDList[] = paragraph[i].getSentimentIDList();
                if (sentenceIDList != null) {
                    for (int j = 0; j < sentenceIDList.length; j++)
                        if (sentenceIDList[j] != 0)
                            igSentimentIDParagraphCount[Sort.i_FindIntPositionInSortedArray(sentenceIDList[j], igSentimentIDList, 1, igSentimentIDListCount)]++;

                }
            }

        }
        bSentimentIDListMade = true;
    }

    /**
     * 运行多次10交叉验证
     *
     * @param iMinImprovement     最小改进
     * @param bUseTotalDifference 使用总差异
     * @param iReplications       复制
     * @param iMultiOptimisations 多重优化
     * @param sWriter             写入
     * @param wTermStrengthWriter 术语加强写入
     * @return
     */
    private void run10FoldCrossValidationMultipleTimes(int iMinImprovement, boolean bUseTotalDifference, int iReplications, int iMultiOptimisations, BufferedWriter sWriter, BufferedWriter wTermStrengthWriter) {
        for (int i = 1; i <= iReplications; i++)
            run10FoldCrossValidationOnce(iMinImprovement, bUseTotalDifference, iMultiOptimisations, sWriter, wTermStrengthWriter);

        System.out.println((new StringBuilder("Set of ")).append(iReplications).append(" 10-fold cross validations finished").toString());
    }

    /**
     * 运行多次10交叉验证
     *
     * @param iMinImprovement     最小改进
     * @param bUseTotalDifference 使用总差异
     * @param iReplications       复制
     * @param iMultiOptimisations 多重优化
     * @param sOutFileName        输出文件名
     * @return
     * @overriding
     */
    public void run10FoldCrossValidationMultipleTimes(int iMinImprovement, boolean bUseTotalDifference, int iReplications, int iMultiOptimisations, String sOutFileName) {
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutFileName));
            BufferedWriter wTermStrengthWriter = new BufferedWriter(new FileWriter((new StringBuilder(String.valueOf(FileOps.s_ChopFileNameExtension(sOutFileName)))).append("_termStrVars.txt").toString()));
            options.printClassificationOptionsHeadings(wWriter);
            writeClassificationStatsHeadings(wWriter);
            options.printClassificationOptionsHeadings(wTermStrengthWriter);
            resources.sentimentWords.printSentimentTermsInSingleHeaderRow(wTermStrengthWriter);
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wWriter, wTermStrengthWriter);
            wWriter.close();
            wTermStrengthWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 运行10交叉验证选择变化
     *
     * @param iMinImprovement     最小改进
     * @param bUseTotalDifference 是否使用总差
     * @param iReplications       复制
     * @param iMultiOptimisations 多重优化
     * @param sOutFileName        输出文件名
     * @return
     */
    public void run10FoldCrossValidationForAllOptionVariations(int iMinImprovement, boolean bUseTotalDifference, int iReplications, int iMultiOptimisations, String sOutFileName) {
        try {
            BufferedWriter wResultsWriter = new BufferedWriter(new FileWriter(sOutFileName));
            BufferedWriter wTermStrengthWriter = new BufferedWriter(new FileWriter((new StringBuilder(String.valueOf(FileOps.s_ChopFileNameExtension(sOutFileName)))).append("_termStrVars.txt").toString()));
            if (igPosClass == null || igPosClass.length < igPosCorrect.length) {
                igPosClass = new int[igParagraphCount + 1];
                igNegClass = new int[igParagraphCount + 1];
                igTrinaryClass = new int[igParagraphCount + 1];
            }
            options.printClassificationOptionsHeadings(wResultsWriter);
            writeClassificationStatsHeadings(wResultsWriter);
            options.printClassificationOptionsHeadings(wTermStrengthWriter);
            resources.sentimentWords.printSentimentTermsInSingleHeaderRow(wTermStrengthWriter);
            System.out.println("About to start classifications for 20 different option variations");
            if (options.bgTrinaryMode)
                ClassificationStatistics.baselineAccuracyMakeLargestClassPrediction(igTrinaryCorrect, igTrinaryClass, igParagraphCount, false);
            else if (options.bgScaleMode) {
                ClassificationStatistics.baselineAccuracyMakeLargestClassPrediction(igScaleCorrect, igScaleClass, igParagraphCount, false);
            } else {
                ClassificationStatistics.baselineAccuracyMakeLargestClassPrediction(igPosCorrect, igPosClass, igParagraphCount, false);
                ClassificationStatistics.baselineAccuracyMakeLargestClassPrediction(igNegCorrect, igNegClass, igParagraphCount, true);
            }
            options.printBlankClassificationOptions(wResultsWriter);
            if (options.bgTrinaryMode)
                printClassificationResultsRow(igPosClass, igNegClass, igTrinaryClass, wResultsWriter);
            else
                printClassificationResultsRow(igPosClass, igNegClass, igScaleClass, wResultsWriter);
            options.printClassificationOptions(wResultsWriter, igParagraphCount, bUseTotalDifference, iMultiOptimisations);
            calculateCorpusSentimentScores();
            if (options.bgTrinaryMode)
                printClassificationResultsRow(igPosClass, igNegClass, igTrinaryClass, wResultsWriter);
            else
                printClassificationResultsRow(igPosClass, igNegClass, igScaleClass, wResultsWriter);
            options.printBlankClassificationOptions(wTermStrengthWriter);
            resources.sentimentWords.printSentimentValuesInSingleRow(wTermStrengthWriter);
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.igEmotionParagraphCombineMethod = 1 - options.igEmotionParagraphCombineMethod;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.igEmotionParagraphCombineMethod = 1 - options.igEmotionParagraphCombineMethod;
            options.igEmotionSentenceCombineMethod = 1 - options.igEmotionSentenceCombineMethod;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.igEmotionSentenceCombineMethod = 1 - options.igEmotionSentenceCombineMethod;
            options.bgReduceNegativeEmotionInQuestionSentences = !options.bgReduceNegativeEmotionInQuestionSentences;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgReduceNegativeEmotionInQuestionSentences = !options.bgReduceNegativeEmotionInQuestionSentences;
            options.bgMissCountsAsPlus2 = !options.bgMissCountsAsPlus2;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgMissCountsAsPlus2 = !options.bgMissCountsAsPlus2;
            options.bgYouOrYourIsPlus2UnlessSentenceNegative = !options.bgYouOrYourIsPlus2UnlessSentenceNegative;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgYouOrYourIsPlus2UnlessSentenceNegative = !options.bgYouOrYourIsPlus2UnlessSentenceNegative;
            options.bgExclamationInNeutralSentenceCountsAsPlus2 = !options.bgExclamationInNeutralSentenceCountsAsPlus2;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgExclamationInNeutralSentenceCountsAsPlus2 = !options.bgExclamationInNeutralSentenceCountsAsPlus2;
            options.bgUseIdiomLookupTable = !options.bgUseIdiomLookupTable;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgUseIdiomLookupTable = !options.bgUseIdiomLookupTable;
            int iTemp = options.igMoodToInterpretNeutralEmphasis;
            options.igMoodToInterpretNeutralEmphasis = -options.igMoodToInterpretNeutralEmphasis;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.igMoodToInterpretNeutralEmphasis = 0;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.igMoodToInterpretNeutralEmphasis = iTemp;
            System.out.println("About to start 10th option variation classification");
            options.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion = !options.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion = !options.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion;
            options.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion = !options.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion = !options.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion;
            options.bgIgnoreBoosterWordsAfterNegatives = !options.bgIgnoreBoosterWordsAfterNegatives;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgIgnoreBoosterWordsAfterNegatives = !options.bgIgnoreBoosterWordsAfterNegatives;
            options.bgMultipleLettersBoostSentiment = !options.bgMultipleLettersBoostSentiment;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgMultipleLettersBoostSentiment = !options.bgMultipleLettersBoostSentiment;
            options.bgBoosterWordsChangeEmotion = !options.bgBoosterWordsChangeEmotion;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgBoosterWordsChangeEmotion = !options.bgBoosterWordsChangeEmotion;
            if (options.bgNegatingWordsFlipEmotion) {
                options.bgNegatingWordsFlipEmotion = !options.bgNegatingWordsFlipEmotion;
                run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
                options.bgNegatingWordsFlipEmotion = !options.bgNegatingWordsFlipEmotion;
            } else {
                options.bgNegatingPositiveFlipsEmotion = !options.bgNegatingPositiveFlipsEmotion;
                run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
                options.bgNegatingPositiveFlipsEmotion = !options.bgNegatingPositiveFlipsEmotion;
                options.bgNegatingNegativeNeutralisesEmotion = !options.bgNegatingNegativeNeutralisesEmotion;
                run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
                options.bgNegatingNegativeNeutralisesEmotion = !options.bgNegatingNegativeNeutralisesEmotion;
            }
            options.bgCorrectSpellingsWithRepeatedLetter = !options.bgCorrectSpellingsWithRepeatedLetter;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgCorrectSpellingsWithRepeatedLetter = !options.bgCorrectSpellingsWithRepeatedLetter;
            options.bgUseEmoticons = !options.bgUseEmoticons;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgUseEmoticons = !options.bgUseEmoticons;
            options.bgCapitalsBoostTermSentiment = !options.bgCapitalsBoostTermSentiment;
            run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            options.bgCapitalsBoostTermSentiment = !options.bgCapitalsBoostTermSentiment;
            if (iMinImprovement > 1)
                run10FoldCrossValidationMultipleTimes(iMinImprovement - 1, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            run10FoldCrossValidationMultipleTimes(iMinImprovement + 1, bUseTotalDifference, iReplications, iMultiOptimisations, wResultsWriter, wTermStrengthWriter);
            wResultsWriter.close();
            wTermStrengthWriter.close();
            SummariseMultiple10FoldValidations(sOutFileName, (new StringBuilder(String.valueOf(sOutFileName))).append("_sum.txt").toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 运行10交叉验证一次
     *
     * @param iMinImprovement     最小改进
     * @param bUseTotalDifference 是否使用总差
     * @param iMultiOptimisations 多重优化
     * @param wWriter             写入缓冲流
     * @param wTermStrengthWriter 术语加强写入缓冲流
     * @return
     */
    private void run10FoldCrossValidationOnce(int iMinImprovement, boolean bUseTotalDifference, int iMultiOptimisations, BufferedWriter wWriter, BufferedWriter wTermStrengthWriter) {
        int iTotalSentimentWords = resources.sentimentWords.getSentimentWordCount();
        int iParagraphRand[] = new int[igParagraphCount + 1];
        int iPosClassAll[] = new int[igParagraphCount + 1];
        int iNegClassAll[] = new int[igParagraphCount + 1];
        int iTrinaryOrScaleClassAll[] = new int[igParagraphCount + 1];
        int iTotalClassified = 0;
        Sort.makeRandomOrderList(iParagraphRand);
        int iOriginalSentimentStrengths[] = new int[iTotalSentimentWords + 1];
        for (int i = 1; i < iTotalSentimentWords; i++)
            iOriginalSentimentStrengths[i] = resources.sentimentWords.getSentiment(i);

        for (int iFold = 1; iFold <= 10; iFold++) {
            selectDecileAsSubcorpus(iParagraphRand, iFold, true);
            reCalculateCorpusSentimentScores();
            OptimiseDictionaryWeightingsForCorpusStrategy optimiseDictionaryWeightingsForCorpusStrategy = new OptimiseDictionaryWeightingsForCorpusStrategy();
            optimiseDictionaryWeightingsForCorpusStrategy.optimiseDictionaryWeightingsForCorpusMultipleTimes(this, iMinImprovement, bUseTotalDifference, iMultiOptimisations);
            options.printClassificationOptions(wTermStrengthWriter, iMinImprovement, bUseTotalDifference, iMultiOptimisations);
            resources.sentimentWords.printSentimentValuesInSingleRow(wTermStrengthWriter);
            selectDecileAsSubcorpus(iParagraphRand, iFold, false);
            reCalculateCorpusSentimentScores();
            for (int i = 1; i <= igParagraphCount; i++)
                if (bgSupcorpusMember[i]) {
                    iPosClassAll[i] = igPosClass[i];
                    iNegClassAll[i] = igNegClass[i];
                    if (options.bgTrinaryMode)
                        iTrinaryOrScaleClassAll[i] = igTrinaryClass[i];
                    else
                        iTrinaryOrScaleClassAll[i] = igScaleClass[i];
                }

            iTotalClassified += igSupcorpusMemberCount;
            for (int i = 1; i < iTotalSentimentWords; i++)
                resources.sentimentWords.setSentiment(i, iOriginalSentimentStrengths[i]);

        }

        useWholeCorpusNotSubcorpus();
        options.printClassificationOptions(wWriter, iMinImprovement, bUseTotalDifference, iMultiOptimisations);
        printClassificationResultsRow(iPosClassAll, iNegClassAll, iTrinaryOrScaleClassAll, wWriter);
    }

    /**
     * 按行打印分类结果
     *
     * @param iPosClassAll            所有肯定类
     * @param iNegClassAll            所有否定类
     * @param iTrinaryOrScaleClassAll 所有三元或缩放类
     * @param wWriter                 写入缓冲流
     * @return
     */
    private boolean printClassificationResultsRow(int iPosClassAll[], int iNegClassAll[], int iTrinaryOrScaleClassAll[], BufferedWriter wWriter) {
        int iPosCorrect = -1;
        int iNegCorrect = -1;
        int iPosWithin1 = -1;
        int iNegWithin1 = -1;
        int iTrinaryCorrect = -1;
        int iTrinaryCorrectWithin1 = -1;
        double fPosCorrectPoportion = -1D;
        double fNegCorrectPoportion = -1D;
        double fPosWithin1Poportion = -1D;
        double fNegWithin1Poportion = -1D;
        double fTrinaryCorrectPoportion = -1D;
        double fTrinaryCorrectWithin1Poportion = -1D;
        double fPosOrScaleCorr = 9999D;
        double fNegCorr = 9999D;
        double fPosMPE = 9999D;
        double fNegMPE = 9999D;
        double fPosMPEnoDiv = 9999D;
        double fNegMPEnoDiv = 9999D;
        int estCorr[][] = {
                new int[3], new int[3], new int[3]
        };
        try {
            if (options.bgTrinaryMode) {
                iTrinaryCorrect = ClassificationStatistics.accuracy(igTrinaryCorrect, iTrinaryOrScaleClassAll, igParagraphCount, false);
                iTrinaryCorrectWithin1 = ClassificationStatistics.accuracyWithin1(igTrinaryCorrect, iTrinaryOrScaleClassAll, igParagraphCount, false);
                fTrinaryCorrectPoportion = (float) iTrinaryCorrect / (float) igParagraphCount;
                fTrinaryCorrectWithin1Poportion = (float) iTrinaryCorrectWithin1 / (float) igParagraphCount;
                ClassificationStatistics.TrinaryOrBinaryConfusionTable(iTrinaryOrScaleClassAll, igTrinaryCorrect, igParagraphCount, estCorr);
            } else if (options.bgScaleMode) {
                iTrinaryCorrect = ClassificationStatistics.accuracy(igScaleCorrect, iTrinaryOrScaleClassAll, igParagraphCount, false);
                iTrinaryCorrectWithin1 = ClassificationStatistics.accuracyWithin1(igScaleCorrect, iTrinaryOrScaleClassAll, igParagraphCount, false);
                fTrinaryCorrectPoportion = (float) iTrinaryCorrect / (float) igParagraphCount;
                fTrinaryCorrectWithin1Poportion = (float) iTrinaryCorrectWithin1 / (float) igParagraphCount;
                fPosOrScaleCorr = ClassificationStatistics.correlation(igScaleCorrect, iTrinaryOrScaleClassAll, igParagraphCount);
            } else {
                iPosCorrect = ClassificationStatistics.accuracy(igPosCorrect, iPosClassAll, igParagraphCount, false);
                iNegCorrect = ClassificationStatistics.accuracy(igNegCorrect, iNegClassAll, igParagraphCount, true);
                iPosWithin1 = ClassificationStatistics.accuracyWithin1(igPosCorrect, iPosClassAll, igParagraphCount, false);
                iNegWithin1 = ClassificationStatistics.accuracyWithin1(igNegCorrect, iNegClassAll, igParagraphCount, true);
                fPosOrScaleCorr = ClassificationStatistics.correlationAbs(igPosCorrect, iPosClassAll, igParagraphCount);
                fNegCorr = ClassificationStatistics.correlationAbs(igNegCorrect, iNegClassAll, igParagraphCount);
                fPosMPE = ClassificationStatistics.absoluteMeanPercentageError(igPosCorrect, iPosClassAll, igParagraphCount, false);
                fNegMPE = ClassificationStatistics.absoluteMeanPercentageError(igNegCorrect, iNegClassAll, igParagraphCount, true);
                fPosMPEnoDiv = ClassificationStatistics.absoluteMeanPercentageErrorNoDivision(igPosCorrect, iPosClassAll, igParagraphCount, false);
                fNegMPEnoDiv = ClassificationStatistics.absoluteMeanPercentageErrorNoDivision(igNegCorrect, iNegClassAll, igParagraphCount, true);
                fPosCorrectPoportion = (float) iPosCorrect / (float) igParagraphCount;
                fNegCorrectPoportion = (float) iNegCorrect / (float) igParagraphCount;
                fPosWithin1Poportion = (float) iPosWithin1 / (float) igParagraphCount;
                fNegWithin1Poportion = (float) iNegWithin1 / (float) igParagraphCount;
            }
            wWriter.write((new StringBuilder("\t")).append(iPosCorrect).append("\t").append(fPosCorrectPoportion).append("\t").append(iNegCorrect).append("\t").append(fNegCorrectPoportion).append("\t").append(iPosWithin1).append("\t").append(fPosWithin1Poportion).append("\t").append(iNegWithin1).append("\t").append(fNegWithin1Poportion).append("\t").append(fPosOrScaleCorr).append("\t").append(fNegCorr).append("\t").append(fPosMPE).append("\t").append(fNegMPE).append("\t").append(fPosMPEnoDiv).append("\t").append(fNegMPEnoDiv).append("\t").append(iTrinaryCorrect).append("\t").append(fTrinaryCorrectPoportion).append("\t").append(iTrinaryCorrectWithin1).append("\t").append(fTrinaryCorrectWithin1Poportion).append("\t").append(estCorr[0][0]).append("\t").append(estCorr[0][1]).append("\t").append(estCorr[0][2]).append("\t").append(estCorr[1][0]).append("\t").append(estCorr[1][1]).append("\t").append(estCorr[1][2]).append("\t").append(estCorr[2][0]).append("\t").append(estCorr[2][1]).append("\t").append(estCorr[2][2]).append("\t").append(igParagraphCount).append("\n").toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 选择子语料库
     *
     * @param iParagraphRand 随机段落
     * @param iDecile        等分长
     * @param bInvert        是否转换
     * @return
     */
    private void selectDecileAsSubcorpus(int iParagraphRand[], int iDecile, boolean bInvert) {
        if (igParagraphCount == 0)
            return;
        int iMin = (int) (((float) igParagraphCount / 10F) * (float) (iDecile - 1)) + 1;
        int iMax = (int) (((float) igParagraphCount / 10F) * (float) iDecile);
        if (iDecile == 10)
            iMax = igParagraphCount;
        if (iDecile == 0)
            iMin = 0;
        igSupcorpusMemberCount = 0;
        for (int i = 1; i <= igParagraphCount; i++)
            if (i >= iMin && i <= iMax) {
                bgSupcorpusMember[iParagraphRand[i]] = !bInvert;
                if (!bInvert)
                    igSupcorpusMemberCount++;
            } else {
                bgSupcorpusMember[iParagraphRand[i]] = bInvert;
                if (bInvert)
                    igSupcorpusMemberCount++;
            }

    }

    /**
     * 写入分类统计标题
     *
     * @param w 字节缓冲流
     * @return
     */
    private void writeClassificationStatsHeadings(BufferedWriter w)
            throws IOException {
        String sPosOrScale;
        if (options.bgScaleMode)
            sPosOrScale = "ScaleCorrel";
        else
            sPosOrScale = "PosCorrel";
        w.write((new StringBuilder("\tPosCorrect\tiPosCorrect/Total\tNegCorrect\tNegCorrect/Total\tPosWithin1\tPosWithin1/Total\tNegWithin1\tNegWithin1/Total\t")).append(sPosOrScale).append("\tNegCorrel").append("\tPosMPE\tNegMPE\tPosMPEnoDiv\tNegMPEnoDiv").append("\tTrinaryOrScaleCorrect\tTrinaryOrScaleCorrect/TotalClassified").append("\tTrinaryOrScaleCorrectWithin1\tTrinaryOrScaleCorrectWithin1/TotalClassified").append("\test-1corr-1\test-1corr0\test-1corr1").append("\test0corr-1\test0corr0\test0corr1").append("\test1corr-1\test1corr0\test1corr1").append("\tTotalClassified\n").toString());
    }

    /**
     * 归纳多次e10交叉验证
     *
     * @param sInputFile  输入文件地址
     * @param sOutputFile 输出文件地址
     * @return
     */
    @SuppressWarnings("checkstyle:ArrayTypeStyle")
    public void SummariseMultiple10FoldValidations(String sInputFile, String sOutputFile) {
        int iDataRows = 28;
        int iLastOptionCol = 24;
        BufferedReader rResults = null;
        BufferedWriter wSummary = null;
        String sLine = null;
        String sPrevData[] = null;
        String sData[] = null;
        float total[] = new float[iDataRows];
        int iRows = 0;
        int i = 0;
        try {
            rResults = new BufferedReader(new FileReader(sInputFile));
            wSummary = new BufferedWriter(new FileWriter(sOutputFile));
            sLine = rResults.readLine();
            wSummary.write((new StringBuilder(String.valueOf(sLine))).append("\tNumber\n").toString());
            while (rResults.ready()) {
                sLine = rResults.readLine();
                sData = sLine.split("\t");
                boolean bMatching = true;
                if (sPrevData != null)
                    for (i = 0; i < iLastOptionCol; i++)
                        if (!sData[i].equals(sPrevData[i]))
                            bMatching = false;

                if (!bMatching) {
                    for (i = 0; i < iLastOptionCol; i++)
                        wSummary.write((new StringBuilder(String.valueOf(sPrevData[i]))).append("\t").toString());

                    for (i = 0; i < iDataRows; i++)
                        wSummary.write((new StringBuilder(String.valueOf(total[i] / (float) iRows))).append("\t").toString());

                    wSummary.write((new StringBuilder(String.valueOf(iRows))).append("\n").toString());
                    for (i = 0; i < iDataRows; i++)
                        total[i] = 0.0F;

                    iRows = 0;
                }
                for (i = iLastOptionCol; i < iLastOptionCol + iDataRows; i++)
                    try {
                        total[i - iLastOptionCol] += Float.parseFloat(sData[i]);
                    } catch (Exception e) {
                        total[i - iLastOptionCol] += 9999999F;
                    }

                iRows++;
                sPrevData = sLine.split("\t");
            }
            for (i = 0; i < iLastOptionCol; i++)
                wSummary.write((new StringBuilder(String.valueOf(sPrevData[i]))).append("\t").toString());

            for (i = 0; i < iDataRows; i++)
                wSummary.write((new StringBuilder(String.valueOf(total[i] / (float) iRows))).append("\t").toString());

            wSummary.write((new StringBuilder(String.valueOf(iRows))).append("\n").toString());
            wSummary.close();
            rResults.close();
        } catch (IOException e) {
            System.out.println((new StringBuilder("SummariseMultiple10FoldValidations: File I/O error: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println((new StringBuilder("SummariseMultiple10FoldValidations: Error at line: ")).append(sLine).toString());
            System.out.println((new StringBuilder("Value of i: ")).append(i).toString());
            e.printStackTrace();
        }
    }
}
