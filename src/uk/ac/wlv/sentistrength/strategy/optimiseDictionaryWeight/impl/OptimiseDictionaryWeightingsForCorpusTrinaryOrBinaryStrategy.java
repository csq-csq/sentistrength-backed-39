package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl;

import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.OptimiseDictionaryWeight;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.utilities.Sort;

public class OptimiseDictionaryWeightingsForCorpusTrinaryOrBinaryStrategy implements OptimiseDictionaryWeight {
    public void optimiseDictionaryWeightingsForCorpus(Corpus c, int iMinImprovement, boolean bUseTotalDifference) {
        boolean bFullListChanges = true;
        int iLastTrinaryCorrect = c.getClassificationTrinaryNumberCorrect();
        int iNewTrinary = 0;
        int iTotalSentimentWords = c.resources.sentimentWords.getSentimentWordCount();
        int iWordRand[] = new int[iTotalSentimentWords + 1];
        while (bFullListChanges) {
            Sort.makeRandomOrderList(iWordRand);
            bFullListChanges = false;
            for (int i = 1; i <= iTotalSentimentWords; i++) {
                int iOldSentimentStrength = c.resources.sentimentWords.getSentiment(iWordRand[i]);
                boolean bCurrentIDChange = false;
                int iAddOneImprovement = 0;
                int iSubtractOneImprovement = 0;
                if (iOldSentimentStrength < 4) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength + 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    iNewTrinary = c.getClassificationTrinaryNumberCorrect();
                    iAddOneImprovement = iNewTrinary - iLastTrinaryCorrect;
                    if (iAddOneImprovement >= iMinImprovement) {
                        bCurrentIDChange = true;
                        iLastTrinaryCorrect += iAddOneImprovement;
                    }
                }
                if (iOldSentimentStrength > -4 && !bCurrentIDChange) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength - 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    iNewTrinary = c.getClassificationTrinaryNumberCorrect();
                    iSubtractOneImprovement = iNewTrinary - iLastTrinaryCorrect;
                    if (iSubtractOneImprovement >= iMinImprovement) {
                        bCurrentIDChange = true;
                        iLastTrinaryCorrect += iSubtractOneImprovement;
                    }
                }
                if (bCurrentIDChange) {
                    bFullListChanges = true;
                } else {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldSentimentStrength);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                }
            }

        }
    }
}
