package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight;

import uk.ac.wlv.sentistrength.corpus.Corpus;

public interface OptimiseDictionaryWeight {
    public void optimiseDictionaryWeightingsForCorpus(Corpus c, int iMinImprovement, boolean bUseTotalDifference);
}
