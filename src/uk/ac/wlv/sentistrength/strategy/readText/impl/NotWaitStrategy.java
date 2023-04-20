package uk.ac.wlv.sentistrength.strategy.readText.impl;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.sentistrength.classification.ClassificationResources;
import uk.ac.wlv.sentistrength.classification.ClassificationStatistics;
import uk.ac.wlv.sentistrength.corpus.Paragraph;
import uk.ac.wlv.sentistrength.strategy.MachineLearningStrategy;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.args.SentiArgs;
import uk.ac.wlv.sentistrength.ShowHelp;
import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.Context;
import uk.ac.wlv.sentistrength.strategy.optimiseDictionaryWeight.impl.OptimiseDictionaryWeightingsForCorpusStrategy;
import uk.ac.wlv.utilities.FileOps;

import java.io.*;

public class NotWaitStrategy {

    /**
     * @param c         语料库
     * @param sentiArgs 项目参数
     * @author 201850003
     * date: 2023/04/19
     */
    public void read(Corpus c, SentiArgs sentiArgs){
        String sInputFile = sentiArgs.getsInputFile();
        String sInputFolder = sentiArgs.getsInputFolder();
        int iIdCol = sentiArgs.getiIdCol();
        int iTextColForAnnotation = sentiArgs.getiTextColForAnnotation();
        int iMinImprovement = sentiArgs.getiMinImprovement();
        boolean bUseTotalDifference = sentiArgs.isbUseTotalDifference();
        boolean bOkToOverwrite = sentiArgs.isbOkToOverwrite();
        String sOptimalTermStrengths = sentiArgs.getsOptimalTermStrengths();
        String sFileSubString = sentiArgs.getsFileSubString();
        String sResultsFolder = sentiArgs.getsResultsFolder();

        if (sOptimalTermStrengths != "") {
            if (sInputFile == "") {
                System.out.println("Input file must be specified to optimise term weights");
                return;
            }

            if (c.setCorpus(sInputFile)) {
                Context context = new Context(new OptimiseDictionaryWeightingsForCorpusStrategy());
                context.optimise(c, iMinImprovement, bUseTotalDifference);
                c.resources.sentimentWords.saveSentimentList(sOptimalTermStrengths, c);
                System.out.println("Saved optimised term weights to " + sOptimalTermStrengths);
            } else {
                System.out.println("Error: Too few texts in " + sInputFile);
            }
        }
        else if (sentiArgs.isbReportNewTermWeightsForBadClassifications()) {
            if (c.setCorpus(sInputFile)) {
                c.printCorpusUnusedTermsClassificationIndex(FileOps.s_ChopFileNameExtension(sInputFile) + "_unusedTerms.txt", 1);
            } else {
                System.out.println("Error: Too few texts in " + sInputFile);
            }
        }
        else if (sentiArgs.getiTextCol() > 0 && iIdCol > 0) {
            this.classifyAndSaveWithID(c, sInputFile, sInputFolder, sentiArgs.getiTextCol(), iIdCol);
        }
        else if (iTextColForAnnotation > 0) {
            this.annotationTextCol(c, sInputFile, sInputFolder, sFileSubString, iTextColForAnnotation, bOkToOverwrite);
        }
        else {
            if (sInputFolder != "") {
                System.out.println("Input folder specified but textCol and IDcol or annotateCol needed");
            }

            if (sInputFile == "") {
                System.out.println("No action taken because no input file nor text specified");
                ShowHelp.showBriefHelp(c);
                return;
            }

            String sOutputFile = FileOps.getNextAvailableFilename(FileOps.s_ChopFileNameExtension(sInputFile), sentiArgs.getsResultsFileExtension());
            if (sResultsFolder.length() > 0) {
                sOutputFile = sResultsFolder + (new File(sOutputFile)).getName();
            }

            if (sentiArgs.isbTrain()) {
                MachineLearningStrategy machineLearningStrategy = new MachineLearningStrategy();
                machineLearningStrategy.train(c, sInputFile, sentiArgs.isbDoAll(), iMinImprovement, bUseTotalDifference, sentiArgs.getiIterations(), sentiArgs.getiMultiOptimisations(), sOutputFile);
            } else {
                sentiArgs.setiTextCol(sentiArgs.getiTextCol()-1);
                classifyAllLinesInInputFile(c, sInputFile, sentiArgs.getiTextCol(), sOutputFile);
            }

            System.out.println("Finished! Results in: " + sOutputFile);
        }
    }

    /**
     * 文本分类，和id一起保存
     * @param c            语料库
     * @param sInputFile   输入文件
     * @param sInputFolder 输入文件夹
     * @param iTextCol     文本列
     * @param iIdCol       ID列
     * @author 201850003
     * date: 2023/04/19
     */
    private void classifyAndSaveWithID(Corpus c, String sInputFile, String sInputFolder, int iTextCol, int iIdCol) {
        if (!sInputFile.equals("")) {
            classifyAllLinesAndRecordWithID(c, sInputFile, iTextCol - 1, iIdCol - 1, FileOps.s_ChopFileNameExtension(sInputFile) + "_classID.txt");
        } else {
            if (sInputFolder.equals("")) {
                System.out.println("No annotations done because no input file or folder specfied");
                ShowHelp.showBriefHelp(c);
                return;
            }

            File folder = new File(sInputFolder);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles == null) {
                System.out.println("Incorrect or empty input folder specfied");
                ShowHelp.showBriefHelp(c);
                return;
            }
            for (int i = 0; i < listOfFiles.length; ++i) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("Classify + save with ID: " + listOfFiles[i].getName());
                    classifyAllLinesAndRecordWithID(c, sInputFolder + "/" + listOfFiles[i].getName(), iTextCol - 1, iIdCol - 1, sInputFolder + "/" + FileOps.s_ChopFileNameExtension(listOfFiles[i].getName()) + "_classID.txt");
                }
            }
        }

    }

    /**
     * 按对应列号对传入的文件或文件夹进行注释。
     * @param c                     语料库
     * @param sInputFile            输入文件
     * @param sInputFolder          输入文件夹
     * @param sFileSubString        文件子字符串
     * @param iTextColForAnnotation 注释文本列号
     * @param bOkToOverwrite        判断是否允许覆盖
     * @author 201850003
     * date: 2023/04/19
     */
    private void annotationTextCol(Corpus c, String sInputFile, String sInputFolder, String sFileSubString, int iTextColForAnnotation, boolean bOkToOverwrite) {
        if (!bOkToOverwrite) {
            System.out.println("Must include parameter overwrite to annotate");
        } else {
            if (!sInputFile.equals("")) {
                annotateAllLinesInInputFile(c, sInputFile, iTextColForAnnotation - 1);
            } else {
                if (sInputFolder.equals("")) {
                    System.out.println("No annotations done because no input file or folder specfied");
                    ShowHelp.showBriefHelp(c);
                    return;
                }
                File folder = new File(sInputFolder);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; ++i) {
                    if (listOfFiles[i].isFile()) {
                        if (!sFileSubString.equals("") && listOfFiles[i].getName().indexOf(sFileSubString) <= 0) {
                            System.out.println("  Ignoring " + listOfFiles[i].getName());
                        } else {
                            System.out.println("Annotate: " + listOfFiles[i].getName());
                            annotateAllLinesInInputFile(c,sInputFolder + "/" + listOfFiles[i].getName(), iTextColForAnnotation - 1);
                        }
                    }
                }
            }

        }
    }

    /**
     * 对所有行进行分类，并记录其id
     *
     * @param sInputFile  输入文件地址
     * @param iTextCol    文本列
     * @param iIDCol      id列
     * @param sOutputFile 输出文件地址
     * @return
     */
    public void classifyAllLinesAndRecordWithID(Corpus c, String sInputFile, int iTextCol, int iIDCol, String sOutputFile) {
        int iPos = 0;
        int iNeg = 0;
        int iTrinary = -3;
        int iScale = -10;
        int iCount1 = 0;
        String sLine = "";
        ClassificationResources resources = c.resources;
        ClassificationOptions options = c.options;
        try {
            BufferedReader rReader = new BufferedReader(new FileReader(sInputFile));
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sOutputFile));
            while (rReader.ready()) {
                sLine = rReader.readLine();
                iCount1++;
                if (sLine != "") {
                    String sData[] = sLine.split("\t");
                    if (sData.length > iTextCol && sData.length > iIDCol) {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setParagraph(sData[iTextCol], resources, options);
                        if (options.bgTrinaryMode) {
                            iTrinary = paragraph.getParagraphTrinarySentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sData[iIDCol]))).append("\t").append(iTrinary).append("\n").toString());
                        } else if (options.bgScaleMode) {
                            iScale = paragraph.getParagraphScaleSentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sData[iIDCol]))).append("\t").append(iScale).append("\n").toString());
                        } else {
                            iPos = paragraph.getParagraphPositiveSentiment();
                            iNeg = paragraph.getParagraphNegativeSentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sData[iIDCol]))).append("\t").append(iPos).append("\t").append(iNeg).append("\n").toString());
                        }
                    }
                }
            }
            Thread.sleep(10L);
            if (rReader.ready())
                System.out.println("Reader ready again after pause!");
            int character;
            if ((character = rReader.read()) != -1)
                System.out.println((new StringBuilder("Reader returns char after reader.read() false! ")).append(character).toString());
            rReader.close();
            wWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find input file: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error reading or writing from file: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println((new StringBuilder("Error reading from or writing to file: ")).append(sInputFile).toString());
            e.printStackTrace();
        }
        System.out.println((new StringBuilder("Processed ")).append(iCount1).append(" lines from file: ").append(sInputFile).append(". Last line was:\n").append(sLine).toString());
    }

    /**
     * 注释输入文件中的所有行
     *
     * @param sInputFile 输入文件地址
     * @param iTextCol   文本列
     * @return
     */
    public void annotateAllLinesInInputFile(Corpus c, String sInputFile, int iTextCol) {
        int iPos = 0;
        int iNeg = 0;
        int iTrinary = -3;
        int iScale = -10;
        String sTempFile = (new StringBuilder(String.valueOf(sInputFile))).append("_temp").toString();
        ClassificationResources resources = c.resources;
        ClassificationOptions options = c.options;
        try {
            BufferedReader rReader = new BufferedReader(new FileReader(sInputFile));
            BufferedWriter wWriter = new BufferedWriter(new FileWriter(sTempFile));
            while (rReader.ready()) {
                String sLine = rReader.readLine();
                if (sLine != "") {
                    String sData[] = sLine.split("\t");
                    if (sData.length > iTextCol) {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setParagraph(sData[iTextCol], resources, options);
                        if (options.bgTrinaryMode) {
                            iTrinary = paragraph.getParagraphTrinarySentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sLine))).append("\t").append(iTrinary).append("\n").toString());
                        } else if (options.bgScaleMode) {
                            iScale = paragraph.getParagraphScaleSentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sLine))).append("\t").append(iScale).append("\n").toString());
                        } else {
                            iPos = paragraph.getParagraphPositiveSentiment();
                            iNeg = paragraph.getParagraphNegativeSentiment();
                            wWriter.write((new StringBuilder(String.valueOf(sLine))).append("\t").append(iPos).append("\t").append(iNeg).append("\n").toString());
                        }
                    } else {
                        wWriter.write((new StringBuilder(String.valueOf(sLine))).append("\n").toString());
                    }
                }
            }
            rReader.close();
            wWriter.close();
            File original = new File(sInputFile);
            original.delete();
            File newFile = new File(sTempFile);
            newFile.renameTo(original);
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find input file: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error reading or writing from file: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println((new StringBuilder("Error reading from or writing to file: ")).append(sInputFile).toString());
            e.printStackTrace();
        }
    }

    /**
     * 对输入文件中的所有行进行分类
     *
     * @param sInputFile  输入文件地址
     * @param iTextCol    文本列
     * @param sOutputFile 输出文件地址
     * @return
     */
    public void classifyAllLinesInInputFile(Corpus c, String sInputFile, int iTextCol, String sOutputFile) {
        int iPos = 0;
        int iNeg = 0;
        int iTrinary = -3;
        int iScale = -10;
        int iFileTrinary = -2;
        int iFileScale = -9;
        int iClassified = 0;
        int iCorrectPosCount = 0;
        int iCorrectNegCount = 0;
        int iCorrectTrinaryCount = 0;
        int iCorrectScaleCount = 0;
        int iPosAbsDiff = 0;
        int iNegAbsDiff = 0;
        int confusion[][] = {
                new int[3], new int[3], new int[3]
        };
        int maxClassifyForCorrelation = 20000;
        int iPosClassCorr[] = new int[maxClassifyForCorrelation];
        int iNegClassCorr[] = new int[maxClassifyForCorrelation];
        int iPosClassPred[] = new int[maxClassifyForCorrelation];
        int iNegClassPred[] = new int[maxClassifyForCorrelation];
        int iScaleClassCorr[] = new int[maxClassifyForCorrelation];
        int iScaleClassPred[] = new int[maxClassifyForCorrelation];
        String sRationale = "";
        String sOutput = "";
        ClassificationResources resources = c.resources;
        ClassificationOptions options = c.options;
        try {
            BufferedReader rReader;
            BufferedWriter wWriter;
            if (options.bgForceUTF8) {
                wWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sOutputFile), "UTF8"));
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sInputFile), "UTF8"));
            } else {
                wWriter = new BufferedWriter(new FileWriter(sOutputFile));
                rReader = new BufferedReader(new FileReader(sInputFile));
            }
            if (options.bgTrinaryMode || options.bgScaleMode)
                wWriter.write("Overall\tText");
            else if (options.bgTensiStrength)
                wWriter.write("Relax\tStress\tText");
            else
                wWriter.write("Positive\tNegative\tText");
            if (options.bgExplainClassification)
                wWriter.write("\tExplanation\n");
            else
                wWriter.write("\n");
            while (rReader.ready()) {
                String sLine = rReader.readLine();
                if (sLine != "") {
                    int iTabPos = sLine.lastIndexOf("\t");
                    int iFilePos = 0;
                    int iFileNeg = 0;
                    if (iTabPos >= 0) {
                        String sData[] = sLine.split("\t");
                        if (sData.length > 1)
                            if (iTextCol > -1) {
                                wWriter.write((new StringBuilder(String.valueOf(sLine))).append("\t").toString());
                                if (iTextCol < sData.length)
                                    sLine = sData[iTextCol];
                            } else if (options.bgTrinaryMode) {
                                iFileTrinary = -2;
                                try {
                                    iFileTrinary = Integer.parseInt(sData[0].trim());
                                    if (iFileTrinary > 1 || iFileTrinary < -1) {
                                        System.out.println((new StringBuilder("Invalid trinary sentiment ")).append(iFileTrinary).append(" (expected -1,0,1) at line: ").append(sLine).toString());
                                        iFileTrinary = 0;
                                    }
                                } catch (NumberFormatException numberformatexception) {
                                }
                            } else if (options.bgScaleMode) {
                                iFileScale = -9;
                                try {
                                    iFileScale = Integer.parseInt(sData[0].trim());
                                    if (iFileScale > 4 || iFileScale < -4) {
                                        System.out.println((new StringBuilder("Invalid overall sentiment ")).append(iFileScale).append(" (expected -4 to +4) at line: ").append(sLine).toString());
                                        iFileScale = 0;
                                    }
                                } catch (NumberFormatException numberformatexception1) {
                                }
                            } else {
                                try {
                                    iFilePos = Integer.parseInt(sData[0].trim());
                                    iFileNeg = Integer.parseInt(sData[1].trim());
                                    if (iFileNeg < 0)
                                        iFileNeg = -iFileNeg;
                                } catch (NumberFormatException numberformatexception2) {
                                }
                            }
                        sLine = sLine.substring(iTabPos + 1);
                    }
                    Paragraph paragraph = new Paragraph();
                    paragraph.setParagraph(sLine, resources, options);
                    if (options.bgTrinaryMode) {
                        iTrinary = paragraph.getParagraphTrinarySentiment();
                        if (options.bgExplainClassification)
                            sRationale = (new StringBuilder("\t")).append(paragraph.getClassificationRationale()).toString();
                        sOutput = (new StringBuilder(String.valueOf(iTrinary))).append("\t").append(sLine).append(sRationale).append("\n").toString();
                    } else if (options.bgScaleMode) {
                        iScale = paragraph.getParagraphScaleSentiment();
                        if (options.bgExplainClassification)
                            sRationale = (new StringBuilder("\t")).append(paragraph.getClassificationRationale()).toString();
                        sOutput = (new StringBuilder(String.valueOf(iScale))).append("\t").append(sLine).append(sRationale).append("\n").toString();
                    } else {
                        iPos = paragraph.getParagraphPositiveSentiment();
                        iNeg = paragraph.getParagraphNegativeSentiment();
                        if (options.bgExplainClassification)
                            sRationale = (new StringBuilder("\t")).append(paragraph.getClassificationRationale()).toString();
                        sOutput = (new StringBuilder(String.valueOf(iPos))).append("\t").append(iNeg).append("\t").append(sLine).append(sRationale).append("\n").toString();
                    }
                    wWriter.write(sOutput);
                    if (options.bgTrinaryMode) {
                        if (iFileTrinary > -2 && iFileTrinary < 2 && iTrinary > -2 && iTrinary < 2) {
                            iClassified++;
                            if (iFileTrinary == iTrinary)
                                iCorrectTrinaryCount++;
                            confusion[iTrinary + 1][iFileTrinary + 1]++;
                        }
                    } else if (options.bgScaleMode) {
                        if (iFileScale > -9) {
                            iClassified++;
                            if (iFileScale == iScale)
                                iCorrectScaleCount++;
                            if (iClassified < maxClassifyForCorrelation)
                                iScaleClassCorr[iClassified] = iFileScale;
                            iScaleClassPred[iClassified] = iScale;
                        }
                    } else if (iFileNeg != 0) {
                        iClassified++;
                        if (iPos == iFilePos)
                            iCorrectPosCount++;
                        iPosAbsDiff += Math.abs(iPos - iFilePos);
                        if (iClassified < maxClassifyForCorrelation)
                            iPosClassCorr[iClassified] = iFilePos;
                        iPosClassPred[iClassified] = iPos;
                        if (iNeg == -iFileNeg)
                            iCorrectNegCount++;
                        iNegAbsDiff += Math.abs(iNeg + iFileNeg);
                        if (iClassified < maxClassifyForCorrelation)
                            iNegClassCorr[iClassified] = iFileNeg;
                        iNegClassPred[iClassified] = iNeg;
                    }
                }
            }
            rReader.close();
            wWriter.close();
            if (iClassified > 0)
                if (options.bgTrinaryMode) {
                    System.out.println((new StringBuilder("Trinary correct: ")).append(iCorrectTrinaryCount).append(" (").append(((float) iCorrectTrinaryCount / (float) iClassified) * 100F).append("%).").toString());
                    System.out.println("Correct -> -1   0   1");
                    System.out.println((new StringBuilder("Est = -1   ")).append(confusion[0][0]).append(" ").append(confusion[0][1]).append(" ").append(confusion[0][2]).toString());
                    System.out.println((new StringBuilder("Est =  0   ")).append(confusion[1][0]).append(" ").append(confusion[1][1]).append(" ").append(confusion[1][2]).toString());
                    System.out.println((new StringBuilder("Est =  1   ")).append(confusion[2][0]).append(" ").append(confusion[2][1]).append(" ").append(confusion[2][2]).toString());
                } else if (options.bgScaleMode) {
                    System.out.println((new StringBuilder("Scale correct: ")).append(iCorrectScaleCount).append(" (").append(((float) iCorrectScaleCount / (float) iClassified) * 100F).append("%) out of ").append(iClassified).toString());
                    System.out.println((new StringBuilder("  Correlation: ")).append(ClassificationStatistics.correlation(iScaleClassCorr, iScaleClassPred, iClassified)).toString());
                } else {
                    System.out.print((new StringBuilder(String.valueOf(options.sgProgramPos))).append(" correct: ").append(iCorrectPosCount).append(" (").append(((float) iCorrectPosCount / (float) iClassified) * 100F).append("%).").toString());
                    System.out.println((new StringBuilder(" Mean abs diff: ")).append((float) iPosAbsDiff / (float) iClassified).toString());
                    if (iClassified < maxClassifyForCorrelation) {
                        System.out.println((new StringBuilder(" Correlation: ")).append(ClassificationStatistics.correlationAbs(iPosClassCorr, iPosClassPred, iClassified)).toString());
                        int corrWithin1 = ClassificationStatistics.accuracyWithin1(iPosClassCorr, iPosClassPred, iClassified, false);
                        System.out.println((new StringBuilder(" Correct +/- 1: ")).append(corrWithin1).append(" (").append((float) (100 * corrWithin1) / (float) iClassified).append("%)").toString());
                    }
                    System.out.print((new StringBuilder(String.valueOf(options.sgProgramNeg))).append(" correct: ").append(iCorrectNegCount).append(" (").append(((float) iCorrectNegCount / (float) iClassified) * 100F).append("%).").toString());
                    System.out.println((new StringBuilder(" Mean abs diff: ")).append((float) iNegAbsDiff / (float) iClassified).toString());
                    if (iClassified < maxClassifyForCorrelation) {
                        System.out.println((new StringBuilder(" Correlation: ")).append(ClassificationStatistics.correlationAbs(iNegClassCorr, iNegClassPred, iClassified)).toString());
                        int corrWithin1 = ClassificationStatistics.accuracyWithin1(iNegClassCorr, iNegClassPred, iClassified, true);
                        System.out.println((new StringBuilder(" Correct +/- 1: ")).append(corrWithin1).append(" (").append((float) (100 * corrWithin1) / (float) iClassified).append("%)").toString());
                    }
                }
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find input file: ")).append(sInputFile).toString());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println((new StringBuilder("Error reading from input file: ")).append(sInputFile).append(" or writing to output file ").append(sOutputFile).toString());
            e.printStackTrace();
        }
    }

}
