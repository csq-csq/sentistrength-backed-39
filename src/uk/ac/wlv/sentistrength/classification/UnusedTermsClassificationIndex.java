// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   UnusedTermsClassificationIndex.java

package uk.ac.wlv.sentistrength.classification;

import uk.ac.wlv.utilities.Trie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 未使用的术语分类索引
 * <p>这个类应该是用来设置不同的输出类型，以及提高准确性的</p>
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class UnusedTermsClassificationIndex {

    /**
     * 句表
     */
    private String[] sgTermList;
    /**
     * 句表计数器
     */
    private int igTermListCount;
    /**
     * 句表上限
     */
    private int igTermListMax;
    /**
     * 左指针列表
     */
    private int[] igTermListLessPtr;
    /**
     * 右指针列表
     */
    private int[] igTermListMorePtr;
    /**
     * 频率值表
     */
    private int[] igTermListFreq;
    /**
     * 临时频率值表，用在还没完全将某个内容录入表中时
     */
    private int[] igTermListFreqTemp;
    /**
     * 正值的正确值与估计值的差异
     */
    private int[] igTermListPosClassDiff;
    /**
     * 临时存放新增内容的id
     */
    private int[] iTermsAddedIDTemp;
    /**
     * 负值的正确值与估计值的差异
     */
    private int[] igTermListNegClassDiff;
    /**
     * 规模值的正确值与估计值的差异
     */
    private int[] igTermListScaleClassDiff;
    /**
     * 二元值的正确值与估计值的差异
     */
    private int[] igTermListBinaryClassDiff;
    /**
     * 三元值的正确值与估计值的差异
     */
    private int[] igTermListTrinaryClassDiff;
    /**
     * 新增内容的计数器
     */
    private int iTermsAddedIDTempCount;
    /**
     * 正确的正值表
     */
    private int[][] igTermListPosCorrectClass;
    /**
     * 正确的负值表
     */
    private int[][] igTermListNegCorrectClass;
    /**
     * 正确的规模值表
     */
    private int[][] igTermListScaleCorrectClass;
    /**
     * 正确的二元值表
     */
    private int[][] igTermListBinaryCorrectClass;
    /**
     * 正确的三元值表
     */
    private int[][] igTermListTrinaryCorrectClass;

    /**
     * 构造器，把列表计数器置为0，上限置为50000
     */
    public UnusedTermsClassificationIndex() {
        sgTermList = null;
        igTermListCount = 0;
        igTermListMax = 50000;
    }

    /**
     * 没什么内容的空主方法
     *
     * @param args1 Java命令行参数
     */
//    public static void main(String args1[]) {
//    }

    /**
     * 添加新术语至索引
     *
     * @param sTerm 输入术语
     */
    public void addTermToNewTermIndex(String sTerm) {
        if (sgTermList == null)
            initialise(true, true, true, true);
        if (sTerm.equals("")) {
            return;
        }
        boolean bDontAddMoreElements = false;
        if (igTermListCount == igTermListMax)
            bDontAddMoreElements = true;
        int iTermID = Trie.i_GetTriePositionForString(sTerm, sgTermList, igTermListLessPtr, igTermListMorePtr, 1, igTermListCount, bDontAddMoreElements);
        if (iTermID > 0) {
            iTermsAddedIDTemp[++iTermsAddedIDTempCount] = iTermID;
            igTermListFreqTemp[iTermID]++;
            if (iTermID > igTermListCount)
                igTermListCount = iTermID;
        }
    }

    /**
     * 添加正负值情绪强度的索引
     *
     * @param iCorrectPosClass 正确的正值
     * @param iEstPosClass     估计的正值
     * @param iCorrectNegClass 正确的负值
     * @param iEstNegClass     估计的负值
     */
    public void addNewIndexToMainIndexWithPosNegValues(int iCorrectPosClass, int iEstPosClass, int iCorrectNegClass, int iEstNegClass) {
        if (iCorrectNegClass > 0 && iCorrectPosClass > 0) {
            for (int iTerm = 1; iTerm <= iTermsAddedIDTempCount; iTerm++) {
                int iTermID = iTermsAddedIDTemp[iTerm];
                if (igTermListFreqTemp[iTermID] != 0)
                    try {
                        igTermListNegCorrectClass[iTermID][iCorrectNegClass - 1]++;
                        igTermListPosCorrectClass[iTermID][iCorrectPosClass - 1]++;
                        igTermListPosClassDiff[iTermID] += iCorrectPosClass - iEstPosClass;
                        igTermListNegClassDiff[iTermID] += iCorrectNegClass + iEstNegClass;
                        igTermListFreq[iTermID]++;
                        iTermsAddedIDTemp[iTerm] = 0;
                    } catch (Exception e) {
                        System.out.println((new StringBuilder("[UnusedTermsClassificationIndex] Error trying to add Pos + Neg to index. ")).append(e.getMessage()).toString());
                    }
            }

        }
        iTermsAddedIDTempCount = 0;
    }

    /**
     * 添加规模值情绪强度的索引
     *
     * @param iCorrectScaleClass 正确的规模值
     * @param iEstScaleClass     估计的规模值
     */
    public void addNewIndexToMainIndexWithScaleValues(int iCorrectScaleClass, int iEstScaleClass) {
        for (int iTerm = 1; iTerm <= iTermsAddedIDTempCount; iTerm++) {
            int iTermID = iTermsAddedIDTemp[iTerm];
            if (igTermListFreqTemp[iTermID] != 0)
                try {
                    igTermListScaleCorrectClass[iTermID][iCorrectScaleClass + 4]++;
                    igTermListScaleClassDiff[iTermID] += iCorrectScaleClass - iEstScaleClass;
                    igTermListFreq[iTermID]++;
                    iTermsAddedIDTemp[iTerm] = 0;
                } catch (Exception e) {
                    System.out.println((new StringBuilder("Error trying to add scale values to index. ")).append(e.getMessage()).toString());
                }
        }

        iTermsAddedIDTempCount = 0;
    }

    /**
     * 添加三元分类情绪强度的索引
     *
     * @param iCorrectTrinaryClass 正确的三元值
     * @param iEstTrinaryClass     估计的三元值
     */
    public void addNewIndexToMainIndexWithTrinaryValues(int iCorrectTrinaryClass, int iEstTrinaryClass) {
        for (int iTerm = 1; iTerm <= iTermsAddedIDTempCount; iTerm++) {
            int iTermID = iTermsAddedIDTemp[iTerm];
            if (igTermListFreqTemp[iTermID] != 0)
                try {
                    igTermListTrinaryCorrectClass[iTermID][iCorrectTrinaryClass + 1]++;
                    igTermListTrinaryClassDiff[iTermID] += iCorrectTrinaryClass - iEstTrinaryClass;
                    igTermListFreq[iTermID]++;
                    iTermsAddedIDTemp[iTerm] = 0;
                } catch (Exception e) {
                    System.out.println((new StringBuilder("Error trying to add trinary values to index. ")).append(e.getMessage()).toString());
                }
        }

        iTermsAddedIDTempCount = 0;
    }

    /**
     * 添加二元分类情绪强度的索引
     *
     * @param iCorrectBinaryClass 正确的二元值
     * @param iEstBinaryClass     估计的二元值
     */
    public void addNewIndexToMainIndexWithBinaryValues(int iCorrectBinaryClass, int iEstBinaryClass) {
        for (int iTerm = 1; iTerm <= iTermsAddedIDTempCount; iTerm++) {
            int iTermID = iTermsAddedIDTemp[iTerm];
            if (igTermListFreqTemp[iTermID] != 0)
                try {
                    igTermListBinaryClassDiff[iTermID] += iCorrectBinaryClass - iEstBinaryClass;
                    if (iCorrectBinaryClass == -1)
                        iCorrectBinaryClass = 0;
                    igTermListBinaryCorrectClass[iTermID][iCorrectBinaryClass]++;
                    igTermListFreq[iTermID]++;
                    iTermsAddedIDTemp[iTerm] = 0;
                } catch (Exception e) {
                    System.out.println((new StringBuilder("Error trying to add scale values to index. ")).append(e.getMessage()).toString());
                }
        }

        iTermsAddedIDTempCount = 0;
    }

    /**
     * 初始化
     *
     * @param bInitialiseScale   是否初始化规模值（刻度尺）
     * @param bInitialisePosNeg  是否初始化正负值
     * @param bInitialiseBinary  是否初始化二元分类
     * @param bInitialiseTrinary 是否初始化三元分类
     */
    public void initialise(boolean bInitialiseScale, boolean bInitialisePosNeg, boolean bInitialiseBinary, boolean bInitialiseTrinary) {
        igTermListCount = 0;
        igTermListMax = 50000;
        iTermsAddedIDTempCount = 0;
        sgTermList = new String[igTermListMax];
        igTermListLessPtr = new int[igTermListMax + 1];
        igTermListMorePtr = new int[igTermListMax + 1];
        igTermListFreq = new int[igTermListMax + 1];
        igTermListFreqTemp = new int[igTermListMax + 1];
        iTermsAddedIDTemp = new int[igTermListMax + 1];
        if (bInitialisePosNeg) {
            igTermListNegCorrectClass = new int[igTermListMax + 1][5];
            igTermListPosCorrectClass = new int[igTermListMax + 1][5];
            igTermListNegClassDiff = new int[igTermListMax + 1];
            igTermListPosClassDiff = new int[igTermListMax + 1];
        }
        if (bInitialiseScale) {
            igTermListScaleCorrectClass = new int[igTermListMax + 1][9];
            igTermListScaleClassDiff = new int[igTermListMax + 1];
        }
        if (bInitialiseBinary) {
            igTermListBinaryCorrectClass = new int[igTermListMax + 1][2];
            igTermListBinaryClassDiff = new int[igTermListMax + 1];
        }
        if (bInitialiseTrinary) {
            igTermListTrinaryCorrectClass = new int[igTermListMax + 1][3];
            igTermListTrinaryClassDiff = new int[igTermListMax + 1];
        }
    }

    /**
     * 用情绪强度（-5+5正负值）打印索引
     *
     * @param sOutputFile 输出文件
     * @param iMinFreq    最小频率
     */
    public void printIndexWithPosNegValues(String sOutputFile, int iMinFreq) {
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutputFile));
            wWriter.write((new StringBuilder("Term\tTermFreq >= ")).append(iMinFreq).append("\t").append("PosClassDiff (correct-estimate)\t").append("NegClassDiff\t").append("PosClassAvDiff\t").append("NegClassAvDiff\t").toString());
            for (int i = 1; i <= 5; i++)
                wWriter.write((new StringBuilder("CorrectClass")).append(i).append("pos\t").toString());

            for (int i = 1; i <= 5; i++)
                wWriter.write((new StringBuilder("CorrectClass")).append(i).append("neg\t").toString());

            wWriter.write("\n");
            if (igTermListCount > 0) {
                for (int iTerm = 1; iTerm <= igTermListCount; iTerm++)
                    if (igTermListFreq[iTerm] >= iMinFreq) {
                        wWriter.write((new StringBuilder(String.valueOf(sgTermList[iTerm]))).append("\t").append(igTermListFreq[iTerm]).append("\t").append(igTermListPosClassDiff[iTerm]).append("\t").append(igTermListNegClassDiff[iTerm]).append("\t").append((float) igTermListPosClassDiff[iTerm] / (float) igTermListFreq[iTerm]).append("\t").append((float) igTermListNegClassDiff[iTerm] / (float) igTermListFreq[iTerm]).append("\t").toString());
                        for (int i = 0; i < 5; i++)
                            wWriter.write((new StringBuilder(String.valueOf(igTermListPosCorrectClass[iTerm][i]))).append("\t").toString());

                        for (int i = 0; i < 5; i++)
                            wWriter.write((new StringBuilder(String.valueOf(igTermListNegCorrectClass[iTerm][i]))).append("\t").toString());

                        wWriter.write("\n");
                    }

            } else {
                wWriter.write("No terms found in corpus!\n");
            }
            wWriter.close();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error printing index to ")).append(sOutputFile).toString());
            e.printStackTrace();
        }
    }

    /**
     * 用规模值（单一的正负比例）打印索引
     *
     * @param sOutputFile 输出文件
     * @param iMinFreq    最小频率
     */
    public void printIndexWithScaleValues(String sOutputFile, int iMinFreq) {
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutputFile));
            wWriter.write("Term\tTermFreq\tScaleClassDiff (correct-estimate)\tScaleClassAvDiff\t");
            for (int i = -4; i <= 4; i++)
                wWriter.write((new StringBuilder("CorrectClass")).append(i).append("\t").toString());

            wWriter.write("\n");
            for (int iTerm = 1; iTerm <= igTermListCount; iTerm++)
                if (igTermListFreq[iTerm] > iMinFreq) {
                    wWriter.write((new StringBuilder(String.valueOf(sgTermList[iTerm]))).append("\t").append(igTermListFreq[iTerm]).append("\t").append(igTermListScaleClassDiff[iTerm]).append("\t").append((float) igTermListScaleClassDiff[iTerm] / (float) igTermListFreq[iTerm]).append("\t").toString());
                    for (int i = 0; i < 9; i++)
                        wWriter.write((new StringBuilder(String.valueOf(igTermListScaleCorrectClass[iTerm][i]))).append("\t").toString());

                    wWriter.write("\n");
                }

            wWriter.close();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error printing Scale index to ")).append(sOutputFile).toString());
            e.printStackTrace();
        }
    }

    /**
     * 用三元分类打印索引
     *
     * @param sOutputFile 输出文件
     * @param iMinFreq    最小频率
     */
    public void printIndexWithTrinaryValues(String sOutputFile, int iMinFreq) {
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutputFile));
            wWriter.write("Term\tTermFreq\tTrinaryClassDiff (correct-estimate)\tTrinaryClassAvDiff\t");
            for (int i = -1; i <= 1; i++)
                wWriter.write((new StringBuilder("CorrectClass")).append(i).append("\t").toString());

            wWriter.write("\n");
            for (int iTerm = 1; iTerm <= igTermListCount; iTerm++)
                if (igTermListFreq[iTerm] > iMinFreq) {
                    wWriter.write((new StringBuilder(String.valueOf(sgTermList[iTerm]))).append("\t").append(igTermListFreq[iTerm]).append("\t").append(igTermListTrinaryClassDiff[iTerm]).append("\t").append((float) igTermListTrinaryClassDiff[iTerm] / (float) igTermListFreq[iTerm]).append("\t").toString());
                    for (int i = 0; i < 3; i++)
                        wWriter.write((new StringBuilder(String.valueOf(igTermListTrinaryCorrectClass[iTerm][i]))).append("\t").toString());

                    wWriter.write("\n");
                }

            wWriter.close();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error printing Trinary index to ")).append(sOutputFile).toString());
            e.printStackTrace();
        }
    }

    /**
     * 用二元分类打印索引
     *
     * @param sOutputFile 输出文件
     * @param iMinFreq    最小频率
     */
    public void printIndexWithBinaryValues(String sOutputFile, int iMinFreq) {
        try {
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutputFile));
            wWriter.write("Term\tTermFreq\tBinaryClassDiff (correct-estimate)\tBinaryClassAvDiff\t");
            wWriter.write("CorrectClass-1\tCorrectClass1\t");
            wWriter.write("\n");
            for (int iTerm = 1; iTerm <= igTermListCount; iTerm++)
                if (igTermListFreq[iTerm] > iMinFreq) {
                    wWriter.write((new StringBuilder(String.valueOf(sgTermList[iTerm]))).append("\t").append(igTermListFreq[iTerm]).append("\t").append(igTermListBinaryClassDiff[iTerm]).append("\t").append((float) igTermListBinaryClassDiff[iTerm] / (float) igTermListFreq[iTerm]).append("\t").toString());
                    for (int i = 0; i < 2; i++)
                        wWriter.write((new StringBuilder(String.valueOf(igTermListBinaryCorrectClass[iTerm][i]))).append("\t").toString());

                    wWriter.write("\n");
                }

            wWriter.close();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error printing Binary index to ")).append(sOutputFile).toString());
            e.printStackTrace();
        }
    }
}
