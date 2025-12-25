package com.center.medical.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.AttachFile;

import java.io.IOException;
import java.util.List;

/**
 * 上传文件记录表(AttachFile)表服务接口
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:49
 */
public interface AttachFileService extends IService<AttachFile> {

    /**
     * 上传文件
     *
     * @param bytes        文件byte数组
     * @param originalName 文件原名
     * @return 文件路径
     * @throws IOException
     */
    String uploadFile(byte[] bytes, String originalName) throws IOException;

    /**
     * 上传图片
     *
     * @param bytes      图片byte数组
     * @param attachFile 图片信息
     * @param extName    文件类型
     * @return 图片路径
     * @throws IOException
     */
    String uploadImg(byte[] bytes, AttachFile attachFile, String extName) throws IOException;

    /**
     * 根据文件路径删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFile(String filePath);

    /**
     * 更新文件信息
     *
     * @param attachFile 文件信息
     * @return 是否成功
     */
    Boolean updateFile(AttachFile attachFile);

    /**
     * 根据文件Id列表与店铺id批量删除文件记录
     *
     * @param ids
     * @param branchId
     */
    void deleteByIdsAndBranchId(List<String> ids, String branchId);

}

