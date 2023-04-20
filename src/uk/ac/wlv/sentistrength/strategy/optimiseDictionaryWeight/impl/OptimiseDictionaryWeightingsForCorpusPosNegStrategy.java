package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl;

import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.OptimiseDictionaryWeight;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.utilities.Sort;

public class OptimiseDictionaryWeightingsForCorpusPosNegStrategy implements OptimiseDictionaryWeight {
    public void optimiseDictionaryWeightingsForCorpus(Corpus c, int iMinImprovement, boolean bUseTotalDifference) {
        boolean bFullListChanges = true;
        int iLastPos = 0;
        int iLastNeg = 0;
        int iLastPosTotalDiff = 0;
        int iLastNegTotalDiff = 0;
        if (bUseTotalDifference) {
            iLastPosTotalDiff = c.getClassificationPositiveTotalDifference();
            iLastNegTotalDiff = c.getClassificationNegativeTotalDifference();
        } else {
            iLastPos = c.getClassificationPositiveNumberCorrect();
            iLastNeg = c.getClassificationNegativeNumberCorrect();
        }
        int iNewPos = 0;
        int iNewNeg = 0;
        int iNewPosTotalDiff = 0;
        int iNewNegTotalDiff = 0;
        int iTotalSentimentWords = c.resources.sentimentWords.getSentimentWordCount();
        int[] iWordRand = new int[iTotalSentimentWords + 1];
        while (bFullListChanges) {
            Sort.makeRandomOrderList(iWordRand);
            bFullListChanges = false;
            for (int i = 1; i <= iTotalSentimentWords; i++) {
                int iOldSentimentStrength = c.resources.sentimentWords.getSentiment(iWordRand[i]);
                boolean bCurrentIDChange = false;
                if (iOldSentimentStrength < 4) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength + 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    if (bUseTotalDifference) {
                        iNewPosTotalDiff = c.getClassificationPositiveTotalDifference();
                        iNewNegTotalDiff = c.getClassificationNegativeTotalDifference();
                        if (((iNewPosTotalDiff - iLastPosTotalDiff) + iNewNegTotalDiff) - iLastNegTotalDiff <= -iMinImprovement)
                            bCurrentIDChange = true;
                    } else {
                        iNewPos = c.getClassificationPositiveNumberCorrect();
                        iNewNeg = c.getClassificationNegativeNumberCorrect();
                        if (((iNewPos - iLastPos) + iNewNeg) - iLastNeg >= iMinImprovement)
                            bCurrentIDChange = true;
                    }
                }
                if (iOldSentimentStrength > -4 && !bCurrentIDChange) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength - 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    if (bUseTotalDifference) {
                        iNewPosTotalDiff = c.getClassificationPositiveTotalDifference();
                        iNewNegTotalDiff = c.getClassificationNegativeTotalDifference();
                        if (((iNewPosTotalDiff - iLastPosTotalDiff) + iNewNegTotalDiff) - iLastNegTotalDiff <= -iMinImprovement)
                            bCurrentIDChange = true;
                    } else {
                        iNewPos = c.getClassificationPositiveNumberCorrect();
                        iNewNeg = c.getClassificationNegativeNumberCorrect();
                        if (((iNewPos - iLastPos) + iNewNeg) - iLastNeg >= iMinImprovement)
                            bCurrentIDChange = true;
                    }
                }
                if (bCurrentIDChange) {
                    if (bUseTotalDifference) {
                        iLastNegTotalDiff = iNewNegTotalDiff;
                        iLastPosTotalDiff = iNewPosTotalDiff;
                        bFullListChanges = true;
                    } else {
                        iLastNeg = iNewNeg;
                        iLastPos = iNewPos;
                        bFullListChanges = true;
                    }
                } else {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                }
            }

        }
    }
}
