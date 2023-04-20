// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   ClassificationStatistics.java

package uk.ac.wlv.sentistrength.classification;

/**
 * Description:&nbsp; 分类统计数据类
 *
 * @author 201850003
 * date: 2023/03/06
 */
public class ClassificationStatistics {

    /**
     * Description:<br>&nbsp; 默认构造函数
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public ClassificationStatistics() {
    }

    /**
     * Description:<br>&nbsp; 计算相关性绝对值
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param iCount     分析对象总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double correlationAbs(int[] iCorrect, int[] iPredicted, int iCount) {
        double fMeanC = 0.0D;
        double fMeanP = 0.0D;
        double fProdCP = 0.0D;
        double fSumCSq = 0.0D;
        double fSumPSq = 0.0D;
        for (int iRow = 1; iRow <= iCount; iRow++) {
            fMeanC += Math.abs(iCorrect[iRow]);
            fMeanP += Math.abs(iPredicted[iRow]);
        }

        fMeanC /= iCount;
        fMeanP /= iCount;
        for (int iRow = 1; iRow <= iCount; iRow++) {
            fProdCP += ((double) Math.abs(iCorrect[iRow]) - fMeanC) * ((double) Math.abs(iPredicted[iRow]) - fMeanP);
            fSumPSq += Math.pow((double) Math.abs(iPredicted[iRow]) - fMeanP, 2D);
            fSumCSq += Math.pow((double) Math.abs(iCorrect[iRow]) - fMeanC, 2D);
        }

        return fProdCP / (Math.sqrt(fSumPSq) * Math.sqrt(fSumCSq));
    }

    /**
     * Description:<br>&nbsp; 计算相关性
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param iCount     分析对象总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double correlation(int[] iCorrect, int[] iPredicted, int iCount) {
        double fMeanC = 0.0D;
        double fMeanP = 0.0D;
        double fProdCP = 0.0D;
        double fSumCSq = 0.0D;
        double fSumPSq = 0.0D;
        for (int iRow = 1; iRow <= iCount; iRow++) {
            fMeanC += iCorrect[iRow];
            fMeanP += iPredicted[iRow];
        }

        fMeanC /= iCount;
        fMeanP /= iCount;
        for (int iRow = 1; iRow <= iCount; iRow++) {
            fProdCP += ((double) iCorrect[iRow] - fMeanC) * ((double) iPredicted[iRow] - fMeanP);
            fSumPSq += Math.pow((double) iPredicted[iRow] - fMeanP, 2D);
            fSumCSq += Math.pow((double) iCorrect[iRow] - fMeanC, 2D);
        }

        return fProdCP / (Math.sqrt(fSumPSq) * Math.sqrt(fSumCSq));
    }

    /**
     * Description:<br>&nbsp; 计算三元分类或三元、二元混合预测结果对比正确答案得到的矩阵表。
     *
     * @param iTrinaryEstimate 三元分类预测结果数组
     * @param iTrinaryCorrect  三元分类正确答案数组
     * @param iDataCount       数据总数
     * @param estCorr          预测矩阵
     * @author 201850003
     * date: 2023/03/06
     */
    public static void TrinaryOrBinaryConfusionTable(int[] iTrinaryEstimate, int[] iTrinaryCorrect, int iDataCount, int[][] estCorr) {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++)
                estCorr[i][j] = 0;

        }

        for (int i = 1; i <= iDataCount; i++)
            if (iTrinaryEstimate[i] > -2 && iTrinaryEstimate[i] < 2 && iTrinaryCorrect[i] > -2 && iTrinaryCorrect[i] < 2)
                estCorr[iTrinaryEstimate[i] + 1][iTrinaryCorrect[i] + 1]++;
            else
                System.out.println((new StringBuilder("Estimate or correct value ")).append(i).append(" out of range -1 to +1 (data count may be wrong): ").append(iTrinaryEstimate[i]).append(" ").append(iTrinaryCorrect[i]).toString());

    }

    /**
     * Description:<br>&nbsp; 计算选定的数行文本的相关性的绝对值。
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param bSelected  记录是否选中目标行的布尔数组
     * @param bInvert    是否转换
     * @param iCount     总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double correlationAbs(int[] iCorrect, int[] iPredicted, boolean[] bSelected, boolean bInvert, int iCount) {
        double fMeanC = 0.0D;
        double fMeanP = 0.0D;
        double fProdCP = 0.0D;
        double fSumCSq = 0.0D;
        double fSumPSq = 0.0D;
        int iDataCount = 0;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if (bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) {
                fMeanC += Math.abs(iCorrect[iRow]);
                fMeanP += Math.abs(iPredicted[iRow]);
                iDataCount++;
            }

        fMeanC /= iDataCount;
        fMeanP /= iDataCount;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if (bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) {
                fProdCP += ((double) Math.abs(iCorrect[iRow]) - fMeanC) * ((double) Math.abs(iPredicted[iRow]) - fMeanP);
                fSumPSq += Math.pow((double) Math.abs(iPredicted[iRow]) - fMeanP, 2D);
                fSumCSq += Math.pow((double) Math.abs(iCorrect[iRow]) - fMeanC, 2D);
            }

        return fProdCP / (Math.sqrt(fSumPSq) * Math.sqrt(fSumCSq));
    }

    /**
     * Description:<br>&nbsp; 计算预测结果的准确率
     *
     * @param iCorrect              正确答案数组
     * @param iPredicted            预测结果数组
     * @param iCount                总数
     * @param bChangeSignOfOneArray 是否更改一个数组的符号
     * @return int
     * @author 201850003
     * date: 2023/03/06
     */
    public static int accuracy(int iCorrect[], int iPredicted[], int iCount, boolean bChangeSignOfOneArray) {
        int iCorrectCount = 0;
        if (bChangeSignOfOneArray) {
            for (int iRow = 1; iRow <= iCount; iRow++)
                if (iCorrect[iRow] == -iPredicted[iRow])
                    iCorrectCount++;

        } else {
            for (int iRow = 1; iRow <= iCount; iRow++)
                if (iCorrect[iRow] == iPredicted[iRow])
                    iCorrectCount++;

        }
        return iCorrectCount;
    }

    /**
     * Description:<br>&nbsp; 计算选中的数行文本预测结果的准确率
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param bSelected  记录是否选中目标行的布尔数组
     * @param bInvert    是否转换
     * @param iCount     总数
     * @return int
     * @author 201850003
     * date: 2023/03/06
     */
    public static int accuracy(int iCorrect[], int iPredicted[], boolean bSelected[], boolean bInvert, int iCount) {
        int iCorrectCount = 0;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if ((bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) && iCorrect[iRow] == iPredicted[iRow])
                iCorrectCount++;

        return iCorrectCount;
    }

    /**
     * Description:<br>&nbsp; 计算选中的数行文本预测结果误差在 1 以内的准确率
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param bSelected  记录是否选中目标行的布尔数组
     * @param bInvert    是否转换
     * @param iCount     总数
     * @return int
     * @author 201850003
     * date: 2023/03/06
     */
    public static int accuracyWithin1(int iCorrect[], int iPredicted[], boolean bSelected[], boolean bInvert, int iCount) {
        int iCorrectCount = 0;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if ((bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) && Math.abs(iCorrect[iRow] - iPredicted[iRow]) <= 1)
                iCorrectCount++;

        return iCorrectCount;
    }

    /**
     * Description:<br>&nbsp; 计算预测结果误差在 1 以内的的准确率
     *
     * @param iCorrect              正确答案数组
     * @param iPredicted            预测结果数组
     * @param iCount                总数
     * @param bChangeSignOfOneArray 是否更改一个数组的符号
     * @return int
     * @author 201850003
     * date: 2023/03/06
     */
    public static int accuracyWithin1(int iCorrect[], int iPredicted[], int iCount, boolean bChangeSignOfOneArray) {
        int iCorrectCount = 0;
        if (bChangeSignOfOneArray) {
            for (int iRow = 1; iRow <= iCount; iRow++)
                if (Math.abs(iCorrect[iRow] + iPredicted[iRow]) <= 1)
                    iCorrectCount++;

        } else {
            for (int iRow = 1; iRow <= iCount; iRow++)
                if (Math.abs(iCorrect[iRow] - iPredicted[iRow]) <= 1)
                    iCorrectCount++;

        }
        return iCorrectCount;
    }

    /**
     * Description:<br>&nbsp; 计算选定的数行文本的无除法平均绝对误差
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param bSelected  记录是否选中目标行的布尔数组
     * @param bInvert    是否转换
     * @param iCount     总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double absoluteMeanPercentageErrorNoDivision(int iCorrect[], int iPredicted[], boolean bSelected[], boolean bInvert, int iCount) {
        int iDataCount = 0;
        double fAMeanPE = 0.0D;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if (bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) {
                fAMeanPE += Math.abs(iPredicted[iRow] - iCorrect[iRow]);
                iDataCount++;
            }

        return fAMeanPE / (double) iDataCount;
    }

    /**
     * Description:<br>&nbsp; 计算选定的数行文本的有除法平均绝对误差
     *
     * @param iCorrect   正确答案数组
     * @param iPredicted 预测结果数组
     * @param bSelected  记录是否选中目标行的布尔数组
     * @param bInvert    是否转换
     * @param iCount     总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double absoluteMeanPercentageError(int iCorrect[], int iPredicted[], boolean bSelected[], boolean bInvert, int iCount) {
        int iDataCount = 0;
        double fAMeanPE = 0.0D;
        for (int iRow = 1; iRow <= iCount; iRow++)
            if (bSelected[iRow] && !bInvert || !bSelected[iRow] && bInvert) {
                fAMeanPE += Math.abs((double) (iPredicted[iRow] - iCorrect[iRow]) / (double) iCorrect[iRow]);
                iDataCount++;
            }

        return fAMeanPE / (double) iDataCount;
    }

    /**
     * Description:<br>&nbsp; 计算选定的数行文本的无除法平均绝对误差
     *
     * @param iCorrect              正确答案数组
     * @param iPredicted            预测结果数组
     * @param iCount                总数
     * @param bChangeSignOfOneArray 是否更改一个数组的符号
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double absoluteMeanPercentageErrorNoDivision(int iCorrect[], int iPredicted[], int iCount, boolean bChangeSignOfOneArray) {
        double fAMeanPE = 0.0D;
        if (bChangeSignOfOneArray) {
            for (int iRow = 1; iRow <= iCount; iRow++)
                fAMeanPE += Math.abs(iPredicted[iRow] + iCorrect[iRow]);

        } else {
            for (int iRow = 1; iRow <= iCount; iRow++)
                fAMeanPE += Math.abs(iPredicted[iRow] - iCorrect[iRow]);

        }
        return fAMeanPE / (double) iCount;
    }

    /**
     * Description:<br>&nbsp; 计算基线多数类分类准确率
     *
     * @param iCorrect 正确答案数组
     * @param iCount   总数
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double baselineAccuracyMajorityClassProportion(int iCorrect[], int iCount) {
        if (iCount == 0)
            return 0.0D;
        int iClassCount[] = new int[100];
        int iMinClass = iCorrect[1];
        int iMaxClass = iCorrect[1];
        for (int i = 2; i <= iCount; i++) {
            if (iCorrect[i] < iMinClass)
                iMinClass = iCorrect[i];
            if (iCorrect[i] > iMaxClass)
                iMaxClass = iCorrect[i];
        }

        if (iMaxClass - iMinClass >= 100)
            return 0.0D;
        for (int i = 0; i <= iMaxClass - iMinClass; i++)
            iClassCount[i] = 0;

        for (int i = 1; i <= iCount; i++)
            iClassCount[iCorrect[i] - iMinClass]++;

        int iMaxClassCount = 0;
        for (int i = 0; i <= iMaxClass - iMinClass; i++)
            if (iClassCount[i] > iMaxClassCount)
                iMaxClassCount = iClassCount[i];

        return (double) iMaxClassCount / (double) iCount;
    }

    /**
     * Description:<br>&nbsp; 通过基线准确率预测最大的类
     *
     * @param iCorrect    正确答案数组
     * @param iPredict    预测结果数组
     * @param iCount      总数
     * @param bChangeSign 是否改变符号
     * @author 201850003
     * date: 2023/03/06
     */
    public static void baselineAccuracyMakeLargestClassPrediction(int iCorrect[], int iPredict[], int iCount, boolean bChangeSign) {
        if (iCount == 0)
            return;
        int iClassCount[] = new int[100];
        int iMinClass = iCorrect[1];
        int iMaxClass = iCorrect[1];
        for (int i = 2; i <= iCount; i++) {
            if (iCorrect[i] < iMinClass)
                iMinClass = iCorrect[i];
            if (iCorrect[i] > iMaxClass)
                iMaxClass = iCorrect[i];
        }

        if (iMaxClass - iMinClass >= 100)
            return;
        for (int i = 0; i <= iMaxClass - iMinClass; i++)
            iClassCount[i] = 0;

        for (int i = 1; i <= iCount; i++)
            iClassCount[iCorrect[i] - iMinClass]++;

        int iMaxClassCount = 0;
        int iLargestClass = 0;
        for (int i = 0; i <= iMaxClass - iMinClass; i++)
            if (iClassCount[i] > iMaxClassCount) {
                iMaxClassCount = iClassCount[i];
                iLargestClass = i + iMinClass;
            }

        if (bChangeSign) {
            for (int i = 1; i <= iCount; i++)
                iPredict[i] = -iLargestClass;

        } else {
            for (int i = 1; i <= iCount; i++)
                iPredict[i] = iLargestClass;

        }
    }

    /**
     * Description:<br>&nbsp; 计算选定的数行文本的有除法平均绝对误差
     *
     * @param iCorrect              正确答案数组
     * @param iPredicted            预测结果数组
     * @param iCount                总数
     * @param bChangeSignOfOneArray 是否更改一个数组的符号
     * @return double
     * @author 201850003
     * date: 2023/03/06
     */
    public static double absoluteMeanPercentageError(int iCorrect[], int iPredicted[], int iCount, boolean bChangeSignOfOneArray) {
        double fAMeanPE = 0.0D;
        if (bChangeSignOfOneArray) {
            for (int iRow = 1; iRow <= iCount; iRow++)
                fAMeanPE += Math.abs((double) (iPredicted[iRow] + iCorrect[iRow]) / (double) iCorrect[iRow]);

        } else {
            for (int iRow = 1; iRow <= iCount; iRow++)
                fAMeanPE += Math.abs((double) (iPredicted[iRow] - iCorrect[iRow]) / (double) iCorrect[iRow]);

        }
        return fAMeanPE / (double) iCount;
    }
}
