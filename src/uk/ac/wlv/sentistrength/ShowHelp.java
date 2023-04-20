package uk.ac.wlv.sentistrength;

import uk.ac.wlv.sentistrength.corpus.Corpus;

public class ShowHelp {

    /**
     * Description:<br>&nbsp;打印帮助文档。
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public static void showBriefHelp(Corpus c) {
        System.out.println();
        System.out.println("====" + c.options.sgProgramName + "Brief Help====");
        System.out.println("For most operations, a minimum of two parameters must be set");
        System.out.println("1) folder location for the linguistic files");
        System.out.println("   e.g., on Windows: C:/mike/Lexical_Data/");
        System.out.println("   e.g., on Mac/Linux/Unix: /usr/Lexical_Data/");
        if (c.options.bgTensiStrength) {
            System.out.println("TensiiStrength_Data can be downloaded from...[not completed yet]");
        } else {
            System.out.println("SentiStrength_Data can be downloaded with the Windows version of SentiStrength from sentistrength.wlv.ac.uk");
        }

        System.out.println();
        System.out.println("2) text to be classified or file name of texts to be classified");
        System.out.println("   e.g., To classify one text: text love+u");
        System.out.println("   e.g., To classify a file of texts: input /bob/data.txt");
        System.out.println();
        System.out.println("Here is an example complete command:");
        if (c.options.bgTensiStrength) {
            System.out.println("java -jar TensiStrength.jar sentidata C:/a/Stress_Data/ text am+stressed");
        } else {
            System.out.println("java -jar SentiStrength.jar sentidata C:/a/SentStrength_Data/ text love+u");
        }

        System.out.println();
        if (!c.options.bgTensiStrength) {
            System.out.println("To list all commands: java -jar SentiStrength.jar help");
        }

    }

    /**
     * Description:<br>&nbsp;打印命令行中输入的命令参数选项说明。
     *
     * @author 201850003
     * date: 2023/03/06
     */
    public static void printCommandLineOptions(Corpus c) {
        System.out.println("====" + c.options.sgProgramName + " Command Line Options====");
        System.out.println("=Source of data to be classified=");
        System.out.println(" text [text to process] OR");
        System.out.println(" input [filename] (each line of the file is classified SEPARATELY");
        System.out.println("        May have +ve 1st col., -ve 2nd col. in evaluation mode) OR");
        System.out.println(" annotateCol [col # 1..] (classify text in col, result at line end) OR");
        System.out.println(" textCol, idCol [col # 1..] (classify text in col, result & ID in new file) OR");
        System.out.println(" inputFolder  [foldername] (all files in folder will be *annotated*)");
        System.out.println(" outputFolder [foldername where to put the output (default: folder of input)]");
        System.out.println(" resultsExtension [file-extension for output (default _out.txt)]");
        System.out.println("  fileSubstring [text] (string must be present in files to annotate)");
        System.out.println("  Ok to overwrite files [overwrite]");
        System.out.println(" listen [port number to listen at - call http://127.0.0.1:81/text]");
        System.out.println(" cmd (wait for stdin input, write to stdout, terminate on input: @end");
        System.out.println(" stdin (read from stdin input, write to stdout, terminate when stdin finished)");
        System.out.println(" wait (just initialise; allow calls to public String computeSentimentScores)");
        System.out.println("=Linguistic data source=");
        System.out.println(" sentidata [folder for " + c.options.sgProgramName + " data (end in slash, no spaces)]");
        System.out.println("=Options=");
        System.out.println(" keywords [comma-separated list - " + c.options.sgProgramMeasuring + " only classified close to these]");
        System.out.println("   wordsBeforeKeywords [words to classify before keyword (default 4)]");
        System.out.println("   wordsAfterKeywords [words to classify after keyword (default 4)]");
        System.out.println(" trinary (report positive-negative-neutral classifcation instead)");
        System.out.println(" binary (report positive-negative classifcation instead)");
        System.out.println(" scale (report single -4 to +4 classifcation instead)");
        System.out.println(" emotionLookupTable [filename (default: EmotionLookupTable.txt)]");
        System.out.println(" additionalFile [filename] (domain-specific terms and evaluations)");
        System.out.println(" lemmaFile [filename] (word tab lemma list for lemmatisation)");
        System.out.println("=Classification algorithm parameters=");
        System.out.println(" noBoosters (ignore sentiment booster words (e.g., very))");
        System.out.println(" noNegators (don't use negating words (e.g., not) to flip sentiment) -OR-");
        System.out.println(" noNegatingPositiveFlipsEmotion (don't use negating words to flip +ve words)");
        System.out.println(" bgNegatingNegativeNeutralisesEmotion (negating words don't neuter -ve words)");
        System.out.println(" negatedWordStrengthMultiplier (strength multiplier when negated (default=0.5))");
        System.out.println(" negatingWordsOccurAfterSentiment (negate " + c.options.sgProgramMeasuring + " occurring before negatives)");
        System.out.println("  maxWordsAfterSentimentToNegate (max words " + c.options.sgProgramMeasuring + " to negator (default 0))");
        System.out.println(" negatingWordsDontOccurBeforeSentiment (don't negate " + c.options.sgProgramMeasuring + " after negatives)");
        System.out.println("   maxWordsBeforeSentimentToNegate (max from negator to " + c.options.sgProgramMeasuring + " (default 0))");
        System.out.println(" noIdioms (ignore idiom list)");
        System.out.println(" questionsReduceNeg (-ve sentiment reduced in questions)");
        System.out.println(" noEmoticons (ignore emoticon list)");
        System.out.println(" exclamations2 (sentence with ! counts as +2 if otherwise neutral)");
        System.out.println(" minPunctuationWithExclamation (min punctuation with ! to boost term " + c.options.sgProgramMeasuring + ")");
        System.out.println(" mood [-1,0,1] (default 1: -1 assume neutral emphasis is neg, 1, assume is pos");
        System.out.println(" noMultiplePosWords (multiple +ve words don't increase " + c.options.sgProgramPos + ")");
        System.out.println(" noMultipleNegWords (multiple -ve words don't increase " + c.options.sgProgramNeg + ")");
        System.out.println(" noIgnoreBoosterWordsAfterNegatives (don't ignore boosters after negating words)");
        System.out.println(" noDictionary (don't try to correct spellings using the dictionary)");
        System.out.println(" noMultipleLetters (don't use additional letters in a word to boost " + c.options.sgProgramMeasuring + ")");
        System.out.println(" noDeleteExtraDuplicateLetters (don't delete extra duplicate letters in words)");
        System.out.println(" illegalDoubleLettersInWordMiddle [letters never duplicate in word middles]");
        System.out.println("    default for English: ahijkquvxyz (specify list without spaces)");
        System.out.println(" illegalDoubleLettersAtWordEnd [letters never duplicate at word ends]");
        System.out.println("    default for English: achijkmnpqruvwxyz (specify list without spaces)");
        System.out.println(" sentenceCombineAv (average " + c.options.sgProgramMeasuring + " strength of terms in each sentence) OR");
        System.out.println(" sentenceCombineTot (total the " + c.options.sgProgramMeasuring + " strength of terms in each sentence)");
        System.out.println(" paragraphCombineAv (average " + c.options.sgProgramMeasuring + " strength of sentences in each text) OR");
        System.out.println(" paragraphCombineTot (total the " + c.options.sgProgramMeasuring + " strength of sentences in each text)");
        System.out.println("  *the default for the above 4 options is the maximum, not the total or average");
        System.out.println(" negativeMultiplier [negative total strength polarity multiplier, default 1.5]");
        System.out.println(" capitalsBoostTermSentiment (" + c.options.sgProgramMeasuring + " words in CAPITALS are stronger)");
        System.out.println(" alwaysSplitWordsAtApostrophes (e.g., t'aime -> t ' aime)");
        System.out.println(" MinSentencePosForQuotesIrony [integer] quotes in +ve sentences indicate irony");
        System.out.println(" MinSentencePosForPunctuationIrony [integer] +ve ending in !!+ indicates irony");
        System.out.println(" MinSentencePosForTermsIrony [integer] irony terms in +ve sent. indicate irony");
        System.out.println(" MinSentencePosForAllIrony [integer] all of the above irony terms");
        System.out.println(" lang [ISO-639 lower-case two-letter langauge code] set processing language");
        System.out.println("=Input and Output=");
        System.out.println(" explain (explain classification after results)");
        System.out.println(" echo (echo original text after results [for pipeline processes])");
        System.out.println(" UTF8 (force all processing to be in UTF-8 format)");
        System.out.println(" urlencoded (input and output text is URL encoded)");
        System.out.println("=Advanced - machine learning [1st input line ignored]=");
        System.out.println(" termWeights (list terms in badly classified texts; must specify inputFile)");
        System.out.println(" optimise [Filename for optimal term strengths (eg. EmotionLookupTable2.txt)]");
        System.out.println(" train (evaluate " + c.options.sgProgramName + " by training term strengths on results in file)");
        System.out.println("   all (test all option variations rather than use default)");
        System.out.println("   numCorrect (optimise by # correct - not total classification difference)");
        System.out.println("   iterations [number of 10-fold iterations] (default 1)");
        System.out.println("   minImprovement [min. accuracy improvement to change " + c.options.sgProgramMeasuring + " weights (default 1)]");
        System.out.println("   multi [# duplicate term strength optimisations to change " + c.options.sgProgramMeasuring + " weights (default 1)]");
    }

}
