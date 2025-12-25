package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.bean.model.Attachment;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author: 路飞船长
 * @date: 2025/6/19 10:13
 * @description:
 */
public interface BoyingBusinessLocalService extends IService<SectionResultMain> {

    /**
     * 上传图片文件
     * @param base64
     * @param patientcode
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    Attachment uploadFile(String base64, String patientcode, String fileName) throws IOException;

    /**
     * 添加结果
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    void addResult(BoyingWriteReportParam param);
}
