package com.center.medical.common.utils.poi;


import java.util.List;

public class ExcelExp {

    private String fileName;// sheet的名称
    private String[] handers;// sheet里的标题
    private List dataset;// sheet里的数据集
    private Class clazz;
    public ExcelExp(String fileName, List dataset, Class clazz) {
        this.fileName = fileName;
        this.dataset = dataset;
        this.clazz = clazz;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getHanders() {
        return handers;
    }

    public void setHanders(String[] handers) {
        this.handers = handers;
    }

    public List getDataset() {
        return dataset;
    }

    public void setDataset(List dataset) {
        this.dataset = dataset;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}

