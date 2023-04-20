package uk.ac.wlv.sentistrength.strategy.readText.impl;

import uk.ac.wlv.sentistrength.strategy.readText.ReadTextStrategy;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.corpus.Paragraph;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

public class PortReadTextStrategy implements ReadTextStrategy {

    public void readText(Corpus c, int iListenPort) {
        ServerSocket serverSocket = null;
        String decodedText = "";
        boolean var6 = false;

        try {
            serverSocket = new ServerSocket(iListenPort);
        } catch (IOException var23) {
            System.out.println("Could not listen on port " + iListenPort + " because\n" + var23.getMessage());
            return;
        }

        System.out.println("Listening on port: " + iListenPort + " IP: " + serverSocket.getInetAddress());

        while (true) {
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
            } catch (IOException var20) {
                System.out.println("Accept failed at port: " + iListenPort);
                return;
            }

            PrintWriter out;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException var19) {
                System.out.println("IOException clientSocket.getOutputStream " + var19.getMessage());
                var19.printStackTrace();
                return;
            }

            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException var18) {
                System.out.println("IOException InputStreamReader " + var18.getMessage());
                var18.printStackTrace();
                return;
            }

            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.indexOf("GET /") == 0) {
                        int lastSpacePos = inputLine.lastIndexOf(" ");
                        if (lastSpacePos < 5) {
                            lastSpacePos = inputLine.length();
                        }

                        decodedText = URLDecoder.decode(inputLine.substring(5, lastSpacePos), "UTF-8");
                        System.out.println("Analysis of text: " + decodedText);
                        break;
                    }

                    if (inputLine.equals("MikeSpecialMessageToEnd.")) {
                        break;
                    }
                }
            } catch (IOException var24) {
                System.out.println("IOException " + var24.getMessage());
                var24.printStackTrace();
                decodedText = "";
            } catch (Exception var25) {
                System.out.println("Non-IOException " + var25.getMessage());
                decodedText = "";
            }

            int iPos = 1;
            int iNeg = 1;
            int iTrinary = 0;
            int iScale = 0;
            Paragraph paragraph = new Paragraph();
            paragraph.setParagraph(decodedText, c.resources, c.options);
            iNeg = paragraph.getParagraphNegativeSentiment();
            iPos = paragraph.getParagraphPositiveSentiment();
            iTrinary = paragraph.getParagraphTrinarySentiment();
            iScale = paragraph.getParagraphScaleSentiment();
            String sRationale = "";
            if (c.options.bgEchoText) {
                sRationale = " " + decodedText;
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

            if (c.options.bgForceUTF8) {
                try {
                    out.print(new String(sOutput.getBytes("UTF-8"), "UTF-8"));
                } catch (UnsupportedEncodingException var22) {
                    out.print("UTF-8 Not found on your system!");
                    var22.printStackTrace();
                }
            } else {
                out.print(sOutput);
            }

            try {
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException var21) {
                System.out.println("IOException closing streams or sockets" + var21.getMessage());
                var21.printStackTrace();
            }
        }
    }

}
