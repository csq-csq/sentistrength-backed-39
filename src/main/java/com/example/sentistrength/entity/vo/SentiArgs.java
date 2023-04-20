package com.example.sentistrength.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
