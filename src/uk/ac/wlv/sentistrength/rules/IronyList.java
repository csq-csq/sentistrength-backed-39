// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name:   IronyList.java

package uk.ac.wlv.sentistrength.rules;

import uk.ac.wlv.sentistrength.classification.ClassificationOptions;
import uk.ac.wlv.utilities.FileOps;
import uk.ac.wlv.utilities.Sort;

import java.io.*;

// Referenced classes of package uk.ac.wlv.sentistrength:
//            ClassificationOptions

/**
 * 反语列表
 *
 * @author 陈思齐
 * @version 1.1 2023-3-7
 */
public class IronyList {

    /**
     * 反语表
     */
    private String sgIronyTerm[];
    /**
     * 反语表的计数器
     */
    private int igIronyTermCount;
    /**
     * 反语表的上限
     */
    private int igIronyTermMax;

    /**
     * 初始化反语列表的计数器，置为0
     */
    public IronyList() {
        igIronyTermCount = 0;
        igIronyTermMax = 0;
    }

    /**
     * 判断某个输入是否为反语
     *
     * @param term 输入语句
     * @return 是/否
     */
    public boolean termIsIronic(String term) {
        int iIronyTermCount = Sort.i_FindStringPositionInSortedArray(term, sgIronyTerm, 1, igIronyTermCount);
        return iIronyTermCount >= 0;
    }

    /**
     * 初始化反语列表
     *
     * @param sSourceFile 存放反语的文件名
     * @param options     分类时的参数设置
     * @return 是否正常初始化
     */
    public boolean initialise(String sSourceFile, ClassificationOptions options) {
        if (igIronyTermCount > 0)
            return true;
        File f = new File(sSourceFile);
        if (!f.exists())
            return true;
        try {
            igIronyTermMax = FileOps.i_CountLinesInTextFile(sSourceFile) + 2;
            igIronyTermCount = 0;
            String sIronyTermTemp[] = new String[igIronyTermMax];
            sgIronyTerm = sIronyTermTemp;
            BufferedReader rReader;
            if (options.bgForceUTF8)
                rReader = new BufferedReader(new InputStreamReader(new FileInputStream(sSourceFile), "UTF8"));
            else
                rReader = new BufferedReader(new FileReader(sSourceFile));
            String sLine;
            while ((sLine = rReader.readLine()) != null)
                if (!sLine.equals("")) {
                    String sData[] = sLine.split("\t");
                    if (sData.length > 0)
                        sgIronyTerm[++igIronyTermCount] = sData[0];
                }
            rReader.close();
        } catch (FileNotFoundException e) {
            System.out.println((new StringBuilder("Could not find IronyTerm file: ")).append(sSourceFile).toString());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println((new StringBuilder("Found IronyTerm file but could not read from it: ")).append(sSourceFile).toString());
            e.printStackTrace();
            return false;
        }
        Sort.quickSortStrings(sgIronyTerm, 1, igIronyTermCount);
        return true;
    }
}
