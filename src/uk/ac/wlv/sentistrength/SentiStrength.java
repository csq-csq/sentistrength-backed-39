package uk.ac.wlv.sentistrength;

import uk.ac.wlv.sentistrength.args.ArgsAnalyzer;
import uk.ac.wlv.sentistrength.args.SentiArgs;
import uk.ac.wlv.sentistrength.corpus.Corpus;
import uk.ac.wlv.sentistrength.corpus.Paragraph;
import uk.ac.wlv.sentistrength.strategy.readText.impl.*;

import java.io.*;
import java.net.URLDecoder;


/**
 * Description:&nbsp; 项目主类
 *
 * @author 201850003
 * date: 2023/03/05
 */
public class SentiStrength {

    /**
     * @see Corpus
     */
    private Corpus c;
    /**
     * @see SentiArgs
     */
    private SentiArgs sentiArgs;
    /**
     * @see ArgsAnalyzer
     */
    private ArgsAnalyzer argsAnalyzer;

    /**
     * Description:<br>&nbsp; 未传入初始化参数时，调用此构造函数，构建一个语料库
     *
     * @author 201850003
     * date: 2023/03/05
     */
    public SentiStrength() {
        this.c = new Corpus();
        this.sentiArgs = new SentiArgs();
        this.argsAnalyzer = new ArgsAnalyzer(c,sentiArgs);
    }


    /**
     * Description:<br>&nbsp; 传入初始化参数时，调用此构造函数，构建并初始化语料库
     *
     * @param args 语料库初始化参数数组
     * @author 201850003
     * date: 2023/03/05
     */
    public SentiStrength(String[] args) {
        this.c = new Corpus();
        this.sentiArgs = new SentiArgs();
        this.argsAnalyzer = new ArgsAnalyzer(c,sentiArgs);
        this.initialiseAndRun(args);
    }


    /**
     * Description:<br>&nbsp; 创建 SentiStrength 类的实例，并初始化其语料库
     *
     * @param args 语料库初始化参数数组
     * @author 201850003
     * date: 2023/03/06
     */
    public static void main(String[] args) {
        SentiStrength classifier = new SentiStrength();
        classifier.initialiseAndRun(args);
    }


    /**
     * Description:<br>&nbsp;读取参数，初始化语料库并运行
     *
     * @param args 语料库初始化参数数组
     * @author = 201850003
     * date: 2023/03/05
     */
    public void initialiseAndRun(String[] args) {
        if(argsAnalyzer.analyzeArgs(args)){
            initCorpusThenReadText();
        }
    }


    /**
     * 初始化语料库，然后读取文本
     * @author 201850003
     * date: 2023/04/19
     */
    private void initCorpusThenReadText(){
        if (c.initialise()) {
            readText();
        } else {
            System.out.println("Failed to initialise!");

            try {
                File f = new File(c.resources.sgSentiStrengthFolder);
                if (!f.exists()) {
                    System.out.println("Folder does not exist! " + c.resources.sgSentiStrengthFolder);
                }
            } catch (Exception var30) {
                System.out.println("Folder doesn't exist! " + c.resources.sgSentiStrengthFolder);
            }

            ShowHelp.showBriefHelp(c);
        }
    }


    /**
     * 读取文本
     * @author 201850003
     * date: 2023/04/19
     */
    private void readText(){
        int iListenPort = sentiArgs.getiListenPort();

        if (sentiArgs.getsTextToParse() != "") {
            boolean urlEncoded=sentiArgs.isbURLEncoded();
            if (urlEncoded) {
                try {
                    sentiArgs.setsTextToParse(URLDecoder.decode(sentiArgs.getsTextToParse(), "UTF-8"));
                } catch (UnsupportedEncodingException var31) {
                    var31.printStackTrace();
                }
            } else {
                sentiArgs.setsTextToParse(sentiArgs.getsTextToParse().replace("+", " "));
            }

            ArgsReadTextStrategy argsReadTextStrategy = new ArgsReadTextStrategy();
            argsReadTextStrategy.readText(c, sentiArgs.getsTextToParse(), urlEncoded);
        }
        else if (iListenPort > 0) {
            PortReadTextStrategy portReadTextStrategy = new PortReadTextStrategy();
            portReadTextStrategy.readText(c, iListenPort);
        }
        else if (sentiArgs.isbCmd()) {
            CmdReadTextStrategy cmdReadTextStrategy = new CmdReadTextStrategy();
            cmdReadTextStrategy.readText(c);
        }
        else if (sentiArgs.isbStdIn()) {
            StdReadTextStrategy stdReadTextStrategy = new StdReadTextStrategy();
            stdReadTextStrategy.readText(c, sentiArgs.getiTextCol());
        }
        else if (!sentiArgs.isbWait()) {
            NotWaitStrategy notWaitStrategy = new NotWaitStrategy();
            notWaitStrategy.read(c, sentiArgs);
        }
    }


    /**
     * Description:&nbsp; 计算并返回传入的语句的各项情绪分数。
     *
     * @param sentence 文本中的句子
     * @return {@link String }
     * @author 201850003
     * date: 2023/03/05
     */
    public String computeSentimentScores(String sentence) {
        int iPos = 1;
        int iNeg = 1;
        int iTrinary = 0;
        int iScale = 0;
        Paragraph paragraph = new Paragraph();
        paragraph.setParagraph(sentence, this.c.resources, this.c.options);
        iNeg = paragraph.getParagraphNegativeSentiment();
        iPos = paragraph.getParagraphPositiveSentiment();
        iTrinary = paragraph.getParagraphTrinarySentiment();
        iScale = paragraph.getParagraphScaleSentiment();
        String sRationale = "";
        if (this.c.options.bgEchoText) {
            sRationale = " " + sentence;
        }

        if (this.c.options.bgExplainClassification) {
            sRationale = " " + paragraph.getClassificationRationale();
        }

        if (this.c.options.bgTrinaryMode) {
            return iPos + " " + iNeg + " " + iTrinary + sRationale;
        } else {
            return this.c.options.bgScaleMode ? iPos + " " + iNeg + " " + iScale + sRationale : iPos + " " + iNeg + sRationale;
        }
    }


    /**
     * Description:<br>&nbsp;提供获取语料库的方法。
     *
     * @return {@link Corpus }
     * @author 201850003
     * date: 2023/03/06
     */
    public Corpus getCorpus() {
        return this.c;
    }
}
