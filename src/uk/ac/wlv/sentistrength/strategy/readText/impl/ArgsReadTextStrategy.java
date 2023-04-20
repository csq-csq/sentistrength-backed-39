package uk.ac.wlv.sentistrength.strategy.readText.impl;

import uk.ac.wlv.sentistrength.strategy.readText.ReadTextStrategy;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.corpus.Paragraph;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ArgsReadTextStrategy implements ReadTextStrategy {

    public void readText(Corpus c, String sTextToParse, boolean bURLEncodedOutput) {
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

        String sOutput = "";
        if (c.options.bgTrinaryMode) {
            sOutput = iPos + " " + iNeg + " " + iTrinary + sRationale;
        } else if (c.options.bgScaleMode) {
            sOutput = iPos + " " + iNeg + " " + iScale + sRationale;
        } else {
            sOutput = iPos + " " + iNeg + sRationale;
        }

        if (bURLEncodedOutput) {
            try {
                System.out.println(URLEncoder.encode(sOutput, "UTF-8"));
            } catch (UnsupportedEncodingException var13) {
                var13.printStackTrace();
            }
        } else if (c.options.bgForceUTF8) {
            try {
                System.out.println(new String(sOutput.getBytes("UTF-8"), "UTF-8"));
            } catch (UnsupportedEncodingException var12) {
                System.out.println("UTF-8 Not found on your system!");
                var12.printStackTrace();
            }
        } else {
            System.out.println(sOutput);
        }

    }

}
