package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.ThirdPartyImagesVo;
import com.center.medical.common.utils.page.PageParam;
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
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    IPage<Attachment> getList(PageParam<Attachment> page, Attachment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Attachment getInfoById(String id);

    /**
     * 保存文件
     *
     * @param attachment
     */
    void savePicture(Attachment attachment);

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

    /**
     * 上传文件
     *
     * @param file       文件
     * @param attachment 文件信息
     * @param baseDir    文件储存的基本路径
     * @param extName    文件类型，后缀名
     * @param extName    文件名
     * @param saveFlag   是否需要保存至Attachment中
     * @param isDelete   上传完后是否删除
     * @return 文件路径
     * @throws IOException
     */
    Attachment uploadFile(File file, Attachment attachment, String baseDir, String extName, String fileName, Boolean saveFlag, Boolean isDelete) throws IOException;

    /**
     * 上传同步文件
     *
     * @param host     源文件host地址
     * @param filePath 图片地址
     * @param isOnline 图片存储地方：1线上， 2线下内网
     * @return 图片路径
     * @throws IOException
     */
    Boolean uploadSyncFile(String host, String filePath, Integer isOnline) throws IOException;

    /**
     * 根据文件路径删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFile(String filePath);

    /**
     * 根据文件Id列表与分中心id批量删除文件记录
     *
     * @param ids
     * @param branchId
     */
    void deleteByIdsAndBranchId(List<String> ids, String branchId);

    void downloadZipFromOSS(String zipName, List<String> paths);

    /**
     * 查询第三方系统报告
     *
     * @param patientcode
     * @param feeItemId
     * @return
     */
    List<String> queryThirdReports(String patientcode, String feeItemId);

    /**
     * 清除附件表中的特殊符号
     * @param symbol
     * @return
     */
    Boolean clearSpecialSymbols(String symbol);

    /**
     * 获取第三方系统报告
     * @param patientno
     * @return
     */
    List<ThirdPartyImagesVo> getThirdPartyReport(String patientcode);



    /**
     * 上传同步文件
     *
     * @param host     源文件host地址
     * @param filePath 图片地址
     * @return 图片路径
     * @throws IOException
     */
    void uploadHuaWeiReport(String host, String filePath) throws IOException;
}

