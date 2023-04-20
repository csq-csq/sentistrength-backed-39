package uk.ac.wlv.sentistrength.strategy.readText.impl;

import uk.ac.wlv.sentistrength.strategy.readText.ReadTextStrategy;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.corpus.Paragraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StdReadTextStrategy implements ReadTextStrategy {

    public void readText(Corpus c, int iTextCol) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String sTextToParse;
        try {
            while ((sTextToParse = stdin.readLine()) != null) {
                boolean bSuccess;
                if (sTextToParse.indexOf("#Change_TermWeight") >= 0) {
                    String[] sData = sTextToParse.split("\t");
                    bSuccess = c.resources.sentimentWords.setSentiment(sData[1], Integer.parseInt(sData[2]));
                    if (bSuccess) {
                        System.out.println("1");
                    } else {
                        System.out.println("0");
                    }
                } else {
                    int iPos = 1;
                    bSuccess = false;
                    int iTrinary = 0;
                    int iScale = 0;
                    Paragraph paragraph = new Paragraph();
                    if (iTextCol > -1) {
                        String[] sData = sTextToParse.split("\t");
                        if (sData.length >= iTextCol) {
                            paragraph.setParagraph(sData[iTextCol], c.resources, c.options);
                        }
                    } else {
                        paragraph.setParagraph(sTextToParse, c.resources, c.options);
                    }

                    int iNeg = paragraph.getParagraphNegativeSentiment();
                    iPos = paragraph.getParagraphPositiveSentiment();
                    iTrinary = paragraph.getParagraphTrinarySentiment();
                    iScale = paragraph.getParagraphScaleSentiment();
                    String sRationale = "";
                    String sOutput;
                    if (c.options.bgEchoText) {
                        sOutput = sTextToParse + "\t";
                    } else {
                        sOutput = "";
                    }

                    if (c.options.bgExplainClassification) {
                        sRationale = paragraph.getClassificationRationale();
                    }

                    if (c.options.bgTrinaryMode) {
                        sOutput = sOutput + iPos + "\t" + iNeg + "\t" + iTrinary + "\t" + sRationale;
                    } else if (c.options.bgScaleMode) {
                        sOutput = sOutput + iPos + "\t" + iNeg + "\t" + iScale + "\t" + sRationale;
                    } else {
                        sOutput = sOutput + iPos + "\t" + iNeg + "\t" + sRationale;
                    }

                    if (c.options.bgForceUTF8) {
                        try {
                            System.out.println(new String(sOutput.getBytes("UTF-8"), "UTF-8"));
                        } catch (UnsupportedEncodingException var13) {
                            System.out.println("UTF-8Not found on your system!");
                            var13.printStackTrace();
                        }
                    } else {
                        System.out.println(sOutput);
                    }
                }
            }
        } catch (IOException var14) {
            System.out.println("Error reading input");
            var14.printStackTrace();
        }

    }

}
