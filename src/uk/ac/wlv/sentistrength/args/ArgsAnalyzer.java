package uk.ac.wlv.sentistrength.args;

import uk.ac.wlv.sentistrength.ShowHelp;
import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.sentistrength.corpus.Corpus;

import java.util.Locale;

public class ArgsAnalyzer {

    private Corpus c;
    private SentiArgs sentiArgs;

    public ArgsAnalyzer(Corpus c, SentiArgs sentiArgs){
        this.c = c;
        this.sentiArgs = sentiArgs;
    }

    public boolean analyzeArgs(String[] args){
        sentiArgs.initBArgumentRecognised(args.length);
        //String rooty="/Users/mac/Documents/workspace/SentiStrength/src/input/";
        // 读取传入的参数数组，依次判断参数内容并将结果以布尔值形式记录在 bArgumentRecognised 数组中
        for (int i = 0; i < args.length; ++i) {
            try {
                if (args[i].equalsIgnoreCase("input")) {
                    sentiArgs.setsInputFile(args[i+1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("inputfolder")) {
                    sentiArgs.setsInputFolder(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("outputfolder")) {
                    sentiArgs.setsResultsFolder(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("resultextension")
                        || args[i].equalsIgnoreCase("resultsextension")) {
                    sentiArgs.setsResultsFileExtension(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("filesubstring")) {
                    sentiArgs.setsFileSubString(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("overwrite")) {
                    sentiArgs.setbOkToOverwrite(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("text")) {
                    sentiArgs.setsTextToParse(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("urlencoded")) {
                    sentiArgs.setbURLEncoded(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("listen")) {
                    sentiArgs.setiListenPort(Integer.parseInt(args[i + 1]));
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("stdin")) {
                    sentiArgs.setbStdIn(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("cmd")) {
                    sentiArgs.setbCmd(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("optimise")) {
                    sentiArgs.setsOptimalTermStrengths(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("annotatecol")) {
                    sentiArgs.setiTextColForAnnotation(Integer.parseInt(args[i + 1]));
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("textcol")) {
                    sentiArgs.setiTextCol(Integer.parseInt(args[i + 1]));
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("idcol")) {
                    sentiArgs.setiIdCol(Integer.parseInt(args[i + 1]));
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("lang")) {
                    sentiArgs.setsLanguage(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("train")) {
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("all")) {
                    sentiArgs.setbDoAll(true);
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("numcorrect")) {
                    sentiArgs.setbUseTotalDifference(false);
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("iterations")) {
                    sentiArgs.setiIterations(Integer.parseInt(args[i + 1]));
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("minimprovement")) {
                    sentiArgs.setiMinImprovement(Integer.parseInt(args[i + 1]));
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("multi")) {
                    sentiArgs.setiMultiOptimisations(Integer.parseInt(args[i + 1]));
                    sentiArgs.setbTrain(true);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("termWeights")) {
                    sentiArgs.setbReportNewTermWeightsForBadClassifications(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("wait")) {
                    sentiArgs.setbWait(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("help")) {
                    ShowHelp.printCommandLineOptions(c);
                    return false;
                }
            }
            catch (NumberFormatException var32) {
                System.out.println("Error in argument for " + args[i] + ". Integer expected!");
                return false;
            }
            catch (Exception var33) {
                System.out.println("Error in argument for " + args[i] + ". Argument missing?");
                return false;
            }
        }

        this.parseParamsForCorpusOptions(args);

        if (sentiArgs.getsLanguage().length() > 1) {
            Locale l = new Locale(sentiArgs.getsLanguage());
            Locale.setDefault(l);
        }

        for (int i = 0; i < args.length; ++i) {
            if (!sentiArgs.getBArgumentRecognisedValue(i)) {
                System.out.println("Unrecognised command - wrong spelling or case?: " + args[i]);
                ShowHelp.showBriefHelp(c);
                return false;
            }
        }
        return true;
    }

    /**
     * Description:<br>&nbsp;分析并初始化语料库选项参数，分析并初始化分类选项参数
     *
     * @param args                初始化参数数组
     * @author 201850003
     * date: 2023/03/06
     */
    private void parseParamsForCorpusOptions(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            try {
                if (args[i].equalsIgnoreCase("sentidata")) {
                    this.c.resources.sgSentiStrengthFolder = args[i + 1];
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("emotionlookuptable")) {
                    this.c.resources.sgSentimentWordsFile = args[i + 1];
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("additionalfile")) {
                    this.c.resources.sgAdditionalFile = args[i + 1];
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("keywords")) {
                    this.c.options.parseKeywordList(args[i + 1].toLowerCase());
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("wordsBeforeKeywords")) {
                    this.c.options.igWordsToIncludeBeforeKeyword = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("wordsAfterKeywords")) {
                    this.c.options.igWordsToIncludeAfterKeyword = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("sentiment")) {
                    this.c.options.nameProgram(false);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("stress")) {
                    this.c.options.nameProgram(true);
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("trinary")) {
                    this.c.options.bgTrinaryMode = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("binary")) {
                    this.c.options.bgBinaryVersionOfTrinaryMode = true;
                    this.c.options.bgTrinaryMode = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("scale")) {
                    this.c.options.bgScaleMode = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                    if (this.c.options.bgTrinaryMode) {
                        System.out.println("Must choose binary/trinary OR scale mode");
                        return;
                    }
                }

                ClassificationOptions var10000;
                if (args[i].equalsIgnoreCase("sentenceCombineAv")) {
                    var10000 = this.c.options;
                    this.c.options.getClass();
                    var10000.igEmotionSentenceCombineMethod = 1;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("sentenceCombineTot")) {
                    var10000 = this.c.options;
                    this.c.options.getClass();
                    var10000.igEmotionSentenceCombineMethod = 2;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("paragraphCombineAv")) {
                    var10000 = this.c.options;
                    this.c.options.getClass();
                    var10000.igEmotionParagraphCombineMethod = 1;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("paragraphCombineTot")) {
                    var10000 = this.c.options;
                    this.c.options.getClass();
                    var10000.igEmotionParagraphCombineMethod = 2;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("negativeMultiplier")) {
                    this.c.options.fgNegativeSentimentMultiplier = Float.parseFloat(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("noBoosters")) {
                    this.c.options.bgBoosterWordsChangeEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noNegatingPositiveFlipsEmotion")) {
                    this.c.options.bgNegatingPositiveFlipsEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noNegatingNegativeNeutralisesEmotion")) {
                    this.c.options.bgNegatingNegativeNeutralisesEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noNegators")) {
                    this.c.options.bgNegatingWordsFlipEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noIdioms")) {
                    this.c.options.bgUseIdiomLookupTable = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("questionsReduceNeg")) {
                    this.c.options.bgReduceNegativeEmotionInQuestionSentences = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noEmoticons")) {
                    this.c.options.bgUseEmoticons = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("exclamations2")) {
                    this.c.options.bgExclamationInNeutralSentenceCountsAsPlus2 = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("minPunctuationWithExclamation")) {
                    this.c.options.igMinPunctuationWithExclamationToChangeSentenceSentiment = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("mood")) {
                    this.c.options.igMoodToInterpretNeutralEmphasis = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("noMultiplePosWords")) {
                    this.c.options.bgAllowMultiplePositiveWordsToIncreasePositiveEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noMultipleNegWords")) {
                    this.c.options.bgAllowMultipleNegativeWordsToIncreaseNegativeEmotion = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noIgnoreBoosterWordsAfterNegatives")) {
                    this.c.options.bgIgnoreBoosterWordsAfterNegatives = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noDictionary")) {
                    this.c.options.bgCorrectSpellingsUsingDictionary = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("noDeleteExtraDuplicateLetters")) {
                    this.c.options.bgCorrectExtraLetterSpellingErrors = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("illegalDoubleLettersInWordMiddle")) {
                    this.c.options.sgIllegalDoubleLettersInWordMiddle = args[i + 1].toLowerCase();
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("illegalDoubleLettersAtWordEnd")) {
                    this.c.options.sgIllegalDoubleLettersAtWordEnd = args[i + 1].toLowerCase();
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("noMultipleLetters")) {
                    this.c.options.bgMultipleLettersBoostSentiment = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("negatedWordStrengthMultiplier")) {
                    this.c.options.fgStrengthMultiplierForNegatedWords = Float.parseFloat(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("maxWordsBeforeSentimentToNegate")) {
                    this.c.options.igMaxWordsBeforeSentimentToNegate = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("negatingWordsDontOccurBeforeSentiment")) {
                    this.c.options.bgNegatingWordsOccurBeforeSentiment = false;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("maxWordsAfterSentimentToNegate")) {
                    this.c.options.igMaxWordsAfterSentimentToNegate = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("negatingWordsOccurAfterSentiment")) {
                    this.c.options.bgNegatingWordsOccurAfterSentiment = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("alwaysSplitWordsAtApostrophes")) {
                    this.c.options.bgAlwaysSplitWordsAtApostrophes = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("capitalsBoostTermSentiment")) {
                    this.c.options.bgCapitalsBoostTermSentiment = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("lemmaFile")) {
                    this.c.options.bgUseLemmatisation = true;
                    this.c.resources.sgLemmaFile = args[i + 1];
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("MinSentencePosForQuotesIrony")) {
                    this.c.options.igMinSentencePosForQuotesIrony = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("MinSentencePosForPunctuationIrony")) {
                    this.c.options.igMinSentencePosForPunctuationIrony = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("MinSentencePosForTermsIrony")) {
                    this.c.options.igMinSentencePosForTermsIrony = Integer.parseInt(args[i + 1]);
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("MinSentencePosForAllIrony")) {
                    this.c.options.igMinSentencePosForTermsIrony = Integer.parseInt(args[i + 1]);
                    this.c.options.igMinSentencePosForPunctuationIrony = this.c.options.igMinSentencePosForTermsIrony;
                    this.c.options.igMinSentencePosForQuotesIrony = this.c.options.igMinSentencePosForTermsIrony;
                    sentiArgs.setBArgumentRecognisedArrValue(new int[]{i,i+1},true);
                }

                if (args[i].equalsIgnoreCase("explain")) {
                    this.c.options.bgExplainClassification = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("echo")) {
                    this.c.options.bgEchoText = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

                if (args[i].equalsIgnoreCase("UTF8")) {
                    this.c.options.bgForceUTF8 = true;
                    sentiArgs.setBArgumentRecognisedValue(i,true);
                }

            } catch (NumberFormatException var5) {
                System.out.println("Error in argument for " + args[i] + ". Integer expected!");
                return;
            } catch (Exception var6) {
                System.out.println("Error in argument for " + args[i] + ". Argument missing?");
                return;
            }
        }

    }

    /**
     * Description:<br>&nbsp; 语料库的初始化方法。完成初始化任务，报告无法识别的指令或初始化失败的信息。
     *
     * @param args 初始化参数数组
     * @author 201850003
     * date: 2023/03/05
     */
    public void initialise(String[] args) {
        boolean[] bArgumentRecognised = new boolean[args.length];

        int i;
        for (i = 0; i < args.length; ++i) {
            bArgumentRecognised[i] = false;
        }

        this.parseParamsForCorpusOptions(args);

        for (i = 0; i < args.length; ++i) {
            if (!bArgumentRecognised[i]) {
                System.out.println("Unrecognised command - wrong spelling or case?: " + args[i]);
                ShowHelp.showBriefHelp(c);
                return;
            }
        }

        if (!this.c.initialise()) {
            System.out.println("Failed to initialise!");
        }

    }

}
