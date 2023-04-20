package uk.ac.wlv.sentistrength.strategy;

import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.ShowHelp;

public class MachineLearningStrategy {

    /**
     * Description:<br>&nbsp;传入机器学习的各项参数，计算并输出训练前的准确率后进行训练
     *
     * @param c                   语料库
     * @param sInputFile          输入文件
     * @param bDoAll              判断是否全部执行的布尔变量
     * @param iMinImprovement     最小改进次数
     * @param bUseTotalDifference 判断使用整体是否不同的布尔变量
     * @param iIterations         迭代次数
     * @param iMultiOptimisations 优化次数
     * @param sOutputFile         输出文件
     * @author 201850003
     * date: 2023/03/06
     */
    public void train(Corpus c, String sInputFile, boolean bDoAll, int iMinImprovement, boolean bUseTotalDifference, int iIterations, int iMultiOptimisations, String sOutputFile) {
        if (iMinImprovement < 1) {
            System.out.println("No action taken because min improvement < 1");
            ShowHelp.showBriefHelp(c);
        } else {
            c.setCorpus(sInputFile);
            c.calculateCorpusSentimentScores();
            int corpusSize = c.getCorpusSize();
            if (c.options.bgTrinaryMode) {
                if (c.options.bgBinaryVersionOfTrinaryMode) {
                    System.out.print("Before training, binary accuracy: " + c.getClassificationTrinaryNumberCorrect() + " " + (float) c.getClassificationTrinaryNumberCorrect() / (float) corpusSize * 100.0F + "%");
                } else {
                    System.out.print("Before training, trinary accuracy: " + c.getClassificationTrinaryNumberCorrect() + " " + (float) c.getClassificationTrinaryNumberCorrect() / (float) corpusSize * 100.0F + "%");
                }
            } else if (c.options.bgScaleMode) {
                System.out.print("Before training, scale accuracy: " + c.getClassificationScaleNumberCorrect() + " " + (float) c.getClassificationScaleNumberCorrect() * 100.0F / (float) corpusSize + "% corr " + c.getClassificationScaleCorrelationWholeCorpus());
            } else {
                System.out.print("Before training, positive: " + c.getClassificationPositiveNumberCorrect() + " " + c.getClassificationPositiveAccuracyProportion() * 100.0F + "% negative " + c.getClassificationNegativeNumberCorrect() + " " + c.getClassificationNegativeAccuracyProportion() * 100.0F + "% ");
                System.out.print("   Positive corr: " + c.getClassificationPosCorrelationWholeCorpus() + " negative " + c.getClassificationNegCorrelationWholeCorpus());
            }

            System.out.println(" out of " + c.getCorpusSize());
            if (bDoAll) {
                System.out.println("Running " + iIterations + " iteration(s) of all options on file " + sInputFile + "; results in " + sOutputFile);
                c.run10FoldCrossValidationForAllOptionVariations(iMinImprovement, bUseTotalDifference, iIterations, iMultiOptimisations, sOutputFile);
            } else {
                System.out.println("Running " + iIterations + " iteration(s) for standard or selected options on file " + sInputFile + "; results in " + sOutputFile);
                c.run10FoldCrossValidationMultipleTimes(iMinImprovement, bUseTotalDifference, iIterations, iMultiOptimisations, sOutputFile);
            }

        }
    }

}
