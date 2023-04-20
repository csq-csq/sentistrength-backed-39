package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl;

import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.Context;
import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.OptimiseDictionaryWeight;
import uk.ac.wlv.sentistrength.corpus.Corpus;

public class OptimiseDictionaryWeightingsForCorpusStrategy implements OptimiseDictionaryWeight {
    Context context;
    public void optimiseDictionaryWeightingsForCorpus(Corpus c, int iMinImprovement, boolean bUseTotalDifference) {
        if (c.options.bgTrinaryMode){
            context = new Context(new OptimiseDictionaryWeightingsForCorpusTrinaryOrBinaryStrategy());
            context.optimise(c, iMinImprovement, bUseTotalDifference);
        }
        else if (c.options.bgScaleMode){
            context = new Context(new OptimiseDictionaryWeightingsForCorpusScaleStrategy());
            context.optimise(c, iMinImprovement, bUseTotalDifference);
        }
        else{
            context = new Context(new OptimiseDictionaryWeightingsForCorpusPosNegStrategy());
            context.optimise(c, iMinImprovement, bUseTotalDifference);
        }
    }

    public void optimiseDictionaryWeightingsForCorpusMultipleTimes(Corpus c, int iMinImprovement, boolean bUseTotalDifference, int iOptimisationTotal) {
        if (iOptimisationTotal < 1)
            return;
        if (iOptimisationTotal == 1) {
            optimiseDictionaryWeightingsForCorpus(c, iMinImprovement, bUseTotalDifference);
            return;
        }
        int iTotalSentimentWords = c.resources.sentimentWords.getSentimentWordCount();
        int[] iOriginalSentimentStrengths = new int[iTotalSentimentWords + 1];
        for (int j = 1; j <= iTotalSentimentWords; j++)
            iOriginalSentimentStrengths[j] = c.resources.sentimentWords.getSentiment(j);

        int[] iTotalWeight = new int[iTotalSentimentWords + 1];
        for (int j = 1; j <= iTotalSentimentWords; j++)
            iTotalWeight[j] = 0;

        for (int i = 0; i < iOptimisationTotal; i++) {
            optimiseDictionaryWeightingsForCorpus(c, iMinImprovement, bUseTotalDifference);
            for (int j = 1; j <= iTotalSentimentWords; j++)
                iTotalWeight[j] += c.resources.sentimentWords.getSentiment(j);

            for (int j = 1; j <= iTotalSentimentWords; j++)
                c.resources.sentimentWords.setSentiment(j, iOriginalSentimentStrengths[j]);

        }

        for (int j = 1; j <= iTotalSentimentWords; j++)
            c.resources.sentimentWords.setSentiment(j, (int) ((double) ((float) iTotalWeight[j] / (float) iOptimisationTotal) + 0.5D));

        optimiseDictionaryWeightingsForCorpus(c, iMinImprovement, bUseTotalDifference);
    }
}
