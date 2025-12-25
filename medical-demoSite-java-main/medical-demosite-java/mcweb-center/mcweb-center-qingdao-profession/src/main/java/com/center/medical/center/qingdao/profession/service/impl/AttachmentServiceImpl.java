package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.center.qingdao.profession.constant.Constants;
import com.center.medical.center.qingdao.profession.entity.dto.Attachment;
import com.center.medical.center.qingdao.profession.mapper.AttachmentMapper;
import com.center.medical.center.qingdao.profession.service.AttachmentService;
import com.center.medical.center.qingdao.profession.utils.FtpUtil;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * JC附件(Attachment)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
@Slf4j
@Service("attachmentService")
@RequiredArgsConstructor
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {
    private static final Integer THREAD_POOL_SIZE = 10;





    /**
     * 上传文件
     *
     * @param file       文件
     * @param attachment 文件信息
     * @param baseDir    文件储存的基本路径
     * @param extName    文件类型，后缀名
     * @param extName    文件名
     * @param saveFlag   是否需要保存至Attachment中
     * @return 图片路径
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment uploadFile(MultipartFile file, Attachment attachment, String baseDir, String extName, String fileName, Boolean saveFlag) throws IOException {
//        log.info("上传文件:{}、{}、{}、{}、{}", attachment, baseDir, fileName, extName, ZhongkangConfig.isOnline());
        String filePath = randomFilePath(extName, fileName);
        // 数据存入数据库
        attachment.setFilePath(Constants.RESOURCE_PREFIX + "/" + baseDir + "/" + filePath);
        attachment.setFileSize(file.getBytes().length);
        // 上传
        try {
            //线下系统将文件储存至指定的硬盘中位置中
            FtpUtil.upload(StrUtil.subBefore(attachment.getFilePath(), "/", true), StrUtil.subAfter(attachment.getFilePath(), "/", true), file.getInputStream(), false);
        } catch (IOException e) {
            log.error("上传文件失败！{}", e);
            throw new ServiceException("上传文件失败！");
        }
        if (saveFlag) {
            this.saveOrUpdate(attachment);
        }
        return attachment;
    }


    private String randomFilePath(String fileType, String filename) {
        fileType = StringUtils.isNotBlank(fileType) ? fileType : "jpg";
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/" + ((StringUtils.isBlank(filename) ? (IdUtil.simpleUUID() + "." + fileType) : filename));
    }






}

