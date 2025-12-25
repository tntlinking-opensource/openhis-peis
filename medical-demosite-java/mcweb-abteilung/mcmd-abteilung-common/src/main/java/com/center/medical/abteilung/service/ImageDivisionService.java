package com.center.medical.abteilung.service;

/**
 * @author xhp
 * @since 2023-06-29 9:25
 */
public interface ImageDivisionService {

    /**
     * 传图
     * @param id
     * @return
     */
    String uploadImage(String id);

    /**
     * 上传数据
     * @param id
     * @return
     */
    String uploadData(String id);
}
