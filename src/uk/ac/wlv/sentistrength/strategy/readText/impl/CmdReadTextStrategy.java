package uk.ac.wlv.sentistrength.strategy.readText.impl;

import uk.ac.wlv.sentistrength.strategy.readText.ReadTextStrategy;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.corpus.Paragraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CmdReadTextStrategy implements ReadTextStrategy {

    public void readText(Corpus c) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            while (true) {
                try {
                    while (true) {
                        String sTextToParse = stdin.readLine();
                        if (sTextToParse.toLowerCase().equals("@end")) {
                            return;
                        }

                        int iPos = 1;
                        int iNeg = 1;
                        int iTrinary = 0;
                        int iScale = 0;
                        Paragraph paragraph = new Paragraph();
                        paragraph.setParagraph(sTextToParse, c.resources, c.options);
                        iNeg = paragraph.getParagraphNegativeSentiment();
                        iPos = paragraph.getParagraphPositiveSentiment();
                        iTrinary = paragraph.getParagraphTrinarySentiment();
                        iScale = paragraph.getParagraphScaleSentiment();
                        String sRationale = "";
                        if (c.options.bgEchoText) {
                            sRationale = " " + sTextToParse;
                        }

                        if (c.options.bgExplainClassification) {
                            sRationale = " " + paragraph.getClassificationRationale();
                        }

                        String sOutput;
                        if (c.options.bgTrinaryMode) {
                            sOutput = iPos + " " + iNeg + " " + iTrinary + sRationale;
                        } else if (c.options.bgScaleMode) {
                            sOutput = iPos + " " + iNeg + " " + iScale + sRationale;
                        } else {
                            sOutput = iPos + " " + iNeg + sRationale;
                        }

                        if (!c.options.bgForceUTF8) {
                            System.out.println(sOutput);
                        } else {
                            try {
                                System.out.println(new String(sOutput.getBytes("UTF-8"), "UTF-8"));
                            } catch (UnsupportedEncodingException var12) {
                                System.out.println("UTF-8Not found on your system!");
                                var12.printStackTrace();
                            }
                        }
                    }
                } catch (IOException var13) {
                    System.out.println(var13);
                }
            }
        }
    }

}
