package uk.ac.wlv.sentistrength.args;

public class SentiArgs {

    private String sInputFile = "";    // 输入文件路径
    private String sInputFolder = "";  // 输入文件夹路径
    private String sTextToParse = "";  // 要分析的文本
    private String sOptimalTermStrengths = "";  // 最佳术语优化结果
    private String sFileSubString = "\t";    // 文件的子字符串
    private String sResultsFolder = "";      // 结果文件夹路径
    private String sResultsFileExtension = "_out.txt";   // 结果文件拓展名
    private boolean[] bArgumentRecognised;   // 布尔变量数组，用于判断输入的参数数组中每一项对应内容是否为空或有效
    private int iIterations = 1;    // 默认迭代次数
    private int iMinImprovement = 2;   // 默认最小改进次数
    private int iMultiOptimisations = 1;  // 默认多次优化次数
    private int iListenPort = 0;    // 默认监听端口
    private int iTextColForAnnotation = -1; // 用于批注的文本列号
    private int iIdCol = -1;     // id 列号
    private int iTextCol = -1;   // 文本列号
    private boolean bDoAll = false;    //
    private boolean bOkToOverwrite = false;
    private boolean bTrain = false;    // 是否用于训练
    private boolean bReportNewTermWeightsForBadClassifications = false;
    private boolean bStdIn = false;
    private boolean bCmd = false;
    private boolean bWait = false;
    private boolean bUseTotalDifference = true;
    private boolean bURLEncoded = false;
    private String sLanguage = "";

    public void initBArgumentRecognised(int len){
        bArgumentRecognised = new boolean[len];
        for (int i = 0; i < len; ++i) {
            bArgumentRecognised[i] = false;
        }
    }

    public boolean getBArgumentRecognisedValue(int index){
        return bArgumentRecognised[index];
    }
    public void setBArgumentRecognisedArrValue(int[] indexes, boolean value){
        for(int index:indexes){
            setBArgumentRecognisedValue(index,value);
        }
    }

    public void setBArgumentRecognisedValue(int index, boolean value){
        bArgumentRecognised[index] = value;
    }

    public String getsInputFile() {
        return sInputFile;
    }

    public void setsInputFile(String sInputFile) {
        this.sInputFile = sInputFile;
    }

    public String getsInputFolder() {
        return sInputFolder;
    }

    public void setsInputFolder(String sInputFolder) {
        this.sInputFolder = sInputFolder;
    }

    public String getsTextToParse() {
        return sTextToParse;
    }

    public void setsTextToParse(String sTextToParse) {
        this.sTextToParse = sTextToParse;
    }

    public String getsOptimalTermStrengths() {
        return sOptimalTermStrengths;
    }

    public void setsOptimalTermStrengths(String sOptimalTermStrengths) {
        this.sOptimalTermStrengths = sOptimalTermStrengths;
    }

    public String getsFileSubString() {
        return sFileSubString;
    }

    public void setsFileSubString(String sFileSubString) {
        this.sFileSubString = sFileSubString;
    }

    public String getsResultsFolder() {
        return sResultsFolder;
    }

    public void setsResultsFolder(String sResultsFolder) {
        this.sResultsFolder = sResultsFolder;
    }

    public String getsResultsFileExtension() {
        return sResultsFileExtension;
    }

    public void setsResultsFileExtension(String sResultsFileExtension) {
        this.sResultsFileExtension = sResultsFileExtension;
    }

    public boolean[] getbArgumentRecognised() {
        return bArgumentRecognised;
    }

    public void setbArgumentRecognised(boolean[] bArgumentRecognised) {
        this.bArgumentRecognised = bArgumentRecognised;
    }

    public int getiIterations() {
        return iIterations;
    }

    public void setiIterations(int iIterations) {
        this.iIterations = iIterations;
    }

    public int getiMinImprovement() {
        return iMinImprovement;
    }

    public void setiMinImprovement(int iMinImprovement) {
        this.iMinImprovement = iMinImprovement;
    }

    public int getiMultiOptimisations() {
        return iMultiOptimisations;
    }

    public void setiMultiOptimisations(int iMultiOptimisations) {
        this.iMultiOptimisations = iMultiOptimisations;
    }

    public int getiListenPort() {
        return iListenPort;
    }

    public void setiListenPort(int iListenPort) {
        this.iListenPort = iListenPort;
    }

    public int getiTextColForAnnotation() {
        return iTextColForAnnotation;
    }

    public void setiTextColForAnnotation(int iTextColForAnnotation) {
        this.iTextColForAnnotation = iTextColForAnnotation;
    }

    public int getiIdCol() {
        return iIdCol;
    }

    public void setiIdCol(int iIdCol) {
        this.iIdCol = iIdCol;
    }

    public int getiTextCol() {
        return iTextCol;
    }

    public void setiTextCol(int iTextCol) {
        this.iTextCol = iTextCol;
    }

    public boolean isbDoAll() {
        return bDoAll;
    }

    public void setbDoAll(boolean bDoAll) {
        this.bDoAll = bDoAll;
    }

    public boolean isbOkToOverwrite() {
        return bOkToOverwrite;
    }

    public void setbOkToOverwrite(boolean bOkToOverwrite) {
        this.bOkToOverwrite = bOkToOverwrite;
    }

    public boolean isbTrain() {
        return bTrain;
    }

    public void setbTrain(boolean bTrain) {
        this.bTrain = bTrain;
    }

    public boolean isbReportNewTermWeightsForBadClassifications() {
        return bReportNewTermWeightsForBadClassifications;
    }

    public void setbReportNewTermWeightsForBadClassifications(boolean bReportNewTermWeightsForBadClassifications) {
        this.bReportNewTermWeightsForBadClassifications = bReportNewTermWeightsForBadClassifications;
    }

    public boolean isbStdIn() {
        return bStdIn;
    }

    public void setbStdIn(boolean bStdIn) {
        this.bStdIn = bStdIn;
    }

    public boolean isbCmd() {
        return bCmd;
    }

    public void setbCmd(boolean bCmd) {
        this.bCmd = bCmd;
    }

    public boolean isbWait() {
        return bWait;
    }

    public void setbWait(boolean bWait) {
        this.bWait = bWait;
    }

    public boolean isbUseTotalDifference() {
        return bUseTotalDifference;
    }

    public void setbUseTotalDifference(boolean bUseTotalDifference) {
        this.bUseTotalDifference = bUseTotalDifference;
    }

    public boolean isbURLEncoded() {
        return bURLEncoded;
    }

    public void setbURLEncoded(boolean bURLEncoded) {
        this.bURLEncoded = bURLEncoded;
    }

    public String getsLanguage() {
        return sLanguage;
    }

    public void setsLanguage(String sLanguage) {
        this.sLanguage = sLanguage;
    }
}
