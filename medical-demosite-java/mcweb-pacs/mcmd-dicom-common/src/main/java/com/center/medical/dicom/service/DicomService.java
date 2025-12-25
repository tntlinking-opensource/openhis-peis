package com.center.medical.dicom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.DicomInfoVo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * dicom
 *
 * @author xhp
 * @since 2023-04-13 9:00
 */
public interface DicomService extends IService<Attachment> {
    /**
     * 接收dicom后保存
     *
     * @param dcmFile
     * @param feeitemId 体检号：体检号为空则从dicom图片中去，体检号不为空则使用该体检号
     * @return
     * @throws IOException
     */
    Attachment saveDicomPath(File dcmFile, String feeitemId) throws IOException;

    //会回滚
    void uploadDicomFileTask() throws ExecutionException, InterruptedException;

    /**
     * 测试将数据库操作单独放在private函数中是否会回滚
     */
    void testPrivateRollback();

    /**
     * 分类dicom文件
     * @param folderPath
     * @return
     */
    Boolean classification(String folderPath) throws IOException;

    /**
     * 读取dicom文件的信息
     * @param folderPath
     * @return
     */
    DicomInfoVo readInfo(String folderPath) throws IOException;
}
