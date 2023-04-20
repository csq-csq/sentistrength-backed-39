package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl;

import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.OptimiseDictionaryWeight;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.utilities.Sort;

public class OptimiseDictionaryWeightingsForCorpusScaleStrategy implements OptimiseDictionaryWeight {
    public void optimiseDictionaryWeightingsForCorpus(Corpus c, int iMinImprovement, boolean bUseTotalDifference) {
        boolean bFullListChanges = true;
        int iLastScaleNumberCorrect = c.getClassificationScaleNumberCorrect();
        int iNewScaleNumberCorrect = 0;
        int iTotalSentimentWords = c.resources.sentimentWords.getSentimentWordCount();
        int iWordRand[] = new int[iTotalSentimentWords + 1];
        while (bFullListChanges) {
            Sort.makeRandomOrderList(iWordRand);
            bFullListChanges = false;
            for (int i = 1; i <= iTotalSentimentWords; i++) {
                int iOldTermSentimentStrength = c.resources.sentimentWords.getSentiment(iWordRand[i]);
                boolean bCurrentIDChange = false;
                int iAddOneImprovement = 0;
                int iSubtractOneImprovement = 0;
                if (iOldTermSentimentStrength < 4) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldTermSentimentStrength + 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    iNewScaleNumberCorrect = c.getClassificationScaleNumberCorrect();
                    iAddOneImprovement = iNewScaleNumberCorrect - iLastScaleNumberCorrect;
                    if (iAddOneImprovement >= iMinImprovement) {
                        bCurrentIDChange = true;
                        iLastScaleNumberCorrect += iAddOneImprovement;
                    }
                }
                if (iOldTermSentimentStrength > -4 && !bCurrentIDChange) {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldTermSentimentStrength - 1);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                    iNewScaleNumberCorrect = c.getClassificationScaleNumberCorrect();
                    iSubtractOneImprovement = iNewScaleNumberCorrect - iLastScaleNumberCorrect;
                    if (iSubtractOneImprovement >= iMinImprovement) {
                        bCurrentIDChange = true;
                        iLastScaleNumberCorrect += iSubtractOneImprovement;
                    }
                }
                if (bCurrentIDChange) {
                    bFullListChanges = true;
                } else {
                    c.resources.sentimentWords.setSentiment(iWordRand[i], iOldTermSentimentStrength);
                    c.reClassifyClassifiedCorpusForSentimentChange(iWordRand[i], 1);
                }
            }

        }
    }
}
