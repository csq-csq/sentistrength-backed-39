package uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight;

import uk.ac.wlv.sentistrength.corpus.Corpus;

public class Context {
    private OptimiseDictionaryWeight strategy;
    public Context(OptimiseDictionaryWeight strategy){
        this.strategy = strategy;
    }

    public void optimise(Corpus c, int iMinImprovement, boolean bUseTotalDifference){
        strategy.optimiseDictionaryWeightingsForCorpus(c, iMinImprovement, bUseTotalDifference);
    }
}
