package com.center.medical.center.qingdao.profession.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.center.qingdao.profession.entity.dto.Attachment;
import com.center.medical.center.qingdao.profession.entity.dto.PageParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * JC附件(Attachment)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
public interface AttachmentService extends IService<Attachment> {



    /**
     * 上传文件
     *
     * @param file       文件
     * @param attachment 文件信息
     * @param baseDir    文件储存的基本路径
     * @param extName    文件类型
     * @param saveFlag   是否需要保存至Attachment中
     * @return 图片路径
     * @throws IOException
     */
    Attachment uploadFile(MultipartFile file, Attachment attachment, String baseDir, String extName, String fileName, Boolean saveFlag) throws IOException;




}

