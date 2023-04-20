package com.example.sentistrength.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictArgs {

    private String predict;         // predict , no
    private String pattern;         // classified, unclassified, classifier, zeros
    private String patternParam;    // SourceArff, UnclassifiedArff, Classifier, ClassFor0
    private String classified;      // classified , no
    private String sourceArff;      // 源文件路径
    private String unclassified;    // unclassified , no
    private String unclassifiedArff;// 未分类文件的路径
    private String classifier;      // classifier , no
    private String sClassifier;     //
    private String zeros;           // zeros,no
    private String classFor0;       //

}
