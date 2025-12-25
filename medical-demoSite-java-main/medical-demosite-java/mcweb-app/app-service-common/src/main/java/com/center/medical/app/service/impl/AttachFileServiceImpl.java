package com.center.medical.app.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.AttachFile;
import com.center.medical.app.common.bean.*;
import com.center.medical.app.common.enums.QiniuZone;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.dao.AttachFileMapper;
import com.center.medical.app.service.AttachFileGroupService;
import com.center.medical.app.service.AttachFileService;
import com.obs.services.ObsClient;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 上传文件记录表(AttachFile)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:49
 */
@Slf4j
@Service("attachFileService")
@RequiredArgsConstructor
public class AttachFileServiceImpl extends ServiceImpl<AttachFileMapper, AttachFile> implements AttachFileService {

    private final AttachFileMapper attachFileMapper;
    private final AttachFileGroupService attachFileGroupService;
    private final ShopConfig shopConfig;
    public final static String NORM_MONTH_PATTERN = "yyyy/MM/";

    /**
     * 上传文件
     *
     * @param bytes        文件byte数组
     * @param originalName 文件原名
     * @return 文件路径
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(byte[] bytes, String originalName) throws IOException {
        String extName = FileUtil.extName(originalName);
        String imgName = FileUtil.mainName(originalName);
        String filePath = randomFilePath(extName);
        AttachFile attachFile = new AttachFile();
        attachFile.setFilePath(filePath);
        attachFile.setFileSize(bytes.length);
        attachFile.setFileType(extName);
        attachFile.setFileName(imgName);
        attachFile.setUploadTime(new Date());
        attachFileMapper.insert(attachFile);
        upload(bytes, filePath, extName);
        return filePath;
    }

    /**
     * 上传图片
     *
     * @param bytes      图片byte数组
     * @param attachFile 图片信息
     * @param extName    文件类型
     * @return 图片路径
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadImg(byte[] bytes, AttachFile attachFile, String extName) throws IOException {
        String filePath = randomFilePath(attachFile.getFileType());
        // 数据存入数据库
        attachFile.setFilePath(filePath);
        attachFile.setFileSize(bytes.length);
        attachFileMapper.save(attachFile);
        // 上传
        upload(bytes, filePath, extName);
        return attachFile.getFileId();
    }

    /**
     * 根据文件路径删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    public void deleteFile(String filePath) {
        attachFileMapper.delete(new LambdaQueryWrapper<AttachFile>().eq(AttachFile::getFilePath, filePath));
        try {
            Qiniu qiniu = shopConfig.getQiniu();
            if (Objects.nonNull(qiniu) && BooleanUtil.isTrue(qiniu.getIsOpen())) {
                bucketManager(qiniu).delete(qiniu.getBucket(), filePath);
                return;
            }

            AliOss aliOss = shopConfig.getAliOss();
            if (Objects.nonNull(aliOss) && BooleanUtil.isTrue(aliOss.getIsOpen())) {
                OSS ossClient = new OSSClientBuilder().build(aliOss.getEndpoint(), aliOss.getAccessKeyId(), aliOss.getAccessKeySecret());
                try {
                    ossClient.deleteObject(aliOss.getBucketName(), filePath);
                } catch (Exception e) {
                    log.error("阿里云Oss删除文件错误：", e);
                } finally {
                    ossClient.shutdown();
                }
                return;
            }

            QCloud qCloud = shopConfig.getQCloud();
            if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(qCloud.getIsOpen())) {
                COSClient cosClient = cosClient(qCloud);
                try {
                    cosClient.deleteObject(qCloud.getBucketName(), filePath);
                } catch (Exception e) {
                    log.error("腾讯云Oss删除文件错误：", e);
                } finally {
                    cosClient.shutdown();
                }
                return;
            }

            HuaWeiOss huaWeiOss = shopConfig.getHuaWeiObs();
            if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(huaWeiOss.getIsOpen())) {
                ObsClient obsClient = new ObsClient(huaWeiOss.getAccessKeyId(), huaWeiOss.getSecretAccessKey(), huaWeiOss.getEndpoint());
                try {
                    obsClient.deleteObject(huaWeiOss.getBucketName(), filePath);
                } catch (Exception e) {
                    log.error("华为Oss删除文件错误：", e);
                } finally {
                    obsClient.close();
                }
            }

            Minio minio = shopConfig.getMinio();
            if (Objects.nonNull(minio) && BooleanUtil.isTrue(minio.getIsOpen())) {
                MinioClient minioClient = MinioClient.builder()
                        .endpoint(minio.getEndpoint())
                        .credentials(minio.getAccessKey(), minio.getSecretKey())
                        .build();
                try {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(minio.getBucketName())
                                    .object(filePath).build()
                    );
                } catch (Exception e) {
                    log.error("minio删除文件错误：", e);
                }
            }
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("IOException：", e);
        }
    }

    /**
     * 更新文件信息
     *
     * @param attachFile 文件信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFile(AttachFile attachFile) {
        return attachFileMapper.updateFile(attachFile);
    }

    /**
     * 根据文件Id列表与店铺id批量删除文件记录
     *
     * @param ids
     * @param branchId
     */
    @Override
    public void deleteByIdsAndBranchId(List<String> ids, String branchId) {
        List<AttachFile> attachFileList = attachFileMapper.getByIds(ids);
        // 获取文件的实际路径--数据库中保存的文件路径为： / + 实际的文件路径
        List<String> filePaths = attachFileList.stream().map(item -> {
            if (!branchId.equals(item.getBranchId())) {
                throw new AppBindException("存在非本中心下的文件，删除失败");
            }
            return item.getFilePath();
        }).collect(Collectors.toList());
        this.batchDeleteFiles(filePaths);
        attachFileMapper.batchDeleteByIds(ids);
    }

    /**
     * 根据文件路径批量删除文件
     *
     * @param filePaths 文件路径列表
     */
    private void batchDeleteFiles(List<String> filePaths) {
        //图片添加也是循环添加，批量删除目前也是循环单个删除，后续或许有更好的办法
        for (String filePath : filePaths) {
            deleteFile(filePath);
        }
    }


    private String randomFilePath(String fileType) {
        return DateUtil.format(new Date(), NORM_MONTH_PATTERN) + IdUtil.simpleUUID() + "." + fileType;
    }

    private void upload(byte[] bytes, String filePath, String extName) throws IOException {
        Qiniu qiniu = shopConfig.getQiniu();
        if (Objects.nonNull(qiniu) && BooleanUtil.isTrue(qiniu.getIsOpen())) {
            String upToken = auth(qiniu).uploadToken(qiniu.getBucket(), filePath);
            uploadManager(qiniu).put(bytes, filePath, upToken);
            return;
        }

        AliOss aliOss = shopConfig.getAliOss();
        if (Objects.nonNull(aliOss.getIsOpen()) && BooleanUtil.isTrue(aliOss.getIsOpen())) {
            OSS ossClient = new OSSClientBuilder().build(aliOss.getEndpoint(), aliOss.getAccessKeyId(), aliOss.getAccessKeySecret());
            InputStream input = null;
            try {
                input = new ByteArrayInputStream(bytes);
                ossClient.putObject(new PutObjectRequest(aliOss.getBucketName(), filePath, input));
            } catch (Exception e) {
                log.error("阿里云Oss上传文件错误：", e);
            } finally {
                ossClient.shutdown();
                if (Objects.nonNull(input)) {
                    input.close();
                }
            }
            return;
        }

        QCloud qCloud = shopConfig.getQCloud();
        if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(qCloud.getIsOpen())) {
            //初始化cosClient
            COSClient cosClient = cosClient(qCloud);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);
            objectMetadata.setContentType(extName);
            ByteArrayInputStream input = null;
            try {
                input = new ByteArrayInputStream(bytes);
                com.qcloud.cos.model.PutObjectRequest putObjectRequest =
                        new com.qcloud.cos.model.PutObjectRequest(qCloud.getBucketName(), filePath, input, objectMetadata);
                cosClient.putObject(putObjectRequest);
            } catch (Exception e) {
                log.error("腾讯云Oss上传文件错误：", e);
            } finally {
                cosClient.shutdown();
                if (Objects.nonNull(input)) {
                    input.close();
                }
            }
            return;
        }

        HuaWeiOss huaWeiOss = shopConfig.getHuaWeiObs();
        if (Objects.nonNull(huaWeiOss) && BooleanUtil.isTrue(huaWeiOss.getIsOpen())) {
            // 创建ObsClient实例
            ObsClient obsClient = new ObsClient(huaWeiOss.getAccessKeyId(), huaWeiOss.getSecretAccessKey(), huaWeiOss.getEndpoint());
            InputStream input = null;
            try {
                input = new ByteArrayInputStream(bytes);
                obsClient.putObject(huaWeiOss.getBucketName(), filePath, input);
            } catch (Exception e) {
                log.error("华为Oss上传文件错误：", e);
            } finally {
                obsClient.close();
                if (Objects.nonNull(input)) {
                    input.close();
                }
            }
        }

        Minio minio = shopConfig.getMinio();
        if (Objects.nonNull(minio) && BooleanUtil.isTrue(minio.getIsOpen())) {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minio.getEndpoint())
                    .credentials(minio.getAccessKey(), minio.getSecretKey())
                    .build();
            InputStream input = null;
            try {
                input = new ByteArrayInputStream(bytes);
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minio.getBucketName())
                                .stream(input, input.available(), -1)
                                .object(filePath)
                                .build()
                );
            } catch (Exception e) {
                log.error("minio上传文件错误：", e);
            } finally {
                if (Objects.nonNull(input)) {
                    input.close();
                }
            }
        }
    }


    // ================== 七牛云配置 ======================

    /**
     * 构建一个七牛上传工具实例
     */
    private UploadManager uploadManager(Qiniu qiniu) {
        return new UploadManager(qiniuConfig(qiniu));
    }

    /**
     * 认证信息实例
     *
     * @return
     */
    private Auth auth(Qiniu qiniu) {
        return Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    private BucketManager bucketManager(Qiniu qiniu) {
        return new BucketManager(auth(qiniu), qiniuConfig(qiniu));
    }

    /**
     * 根据配置文件选择机房
     */
    private com.qiniu.storage.Configuration qiniuConfig(Qiniu qiniu) {

        Region region = null;
        if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_BEI)) {
            region = Region.huabei();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_DONG)) {
            region = Region.huadong();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_NAN)) {
            region = Region.huanan();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.BEI_MEI)) {
            region = Region.beimei();
        } else if (Objects.equals(qiniu.getZone(), QiniuZone.XIN_JIA_PO)) {
            region = Region.xinjiapo();
        }
        return new com.qiniu.storage.Configuration(region);
    }


    /**
     * 创建腾讯云cos client
     *
     * @return
     */
    private COSClient cosClient(QCloud qCloud) {
        BasicCOSCredentials cred = new BasicCOSCredentials(qCloud.getSecretId(), qCloud.getSecretKey());
        com.qcloud.cos.region.Region region = new com.qcloud.cos.region.Region(qCloud.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        //https协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

    /**
     * 根据配置文件选择机房
     */
//	private com.qiniu.storage.Configuration qiniuConfig(Qiniu qiniu) {
//
//		Zone zone = null;
//		if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_BEI)) {
//			zone = Zone.huabei();
//		} else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_DONG)) {
//			zone = Zone.huadong();
//		} else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_NAN)) {
//			zone = Zone.huanan();
//		} else if (Objects.equals(qiniu.getZone(), QiniuZone.BEI_MEI)) {
//			zone = Zone.beimei();
//		} else if (Objects.equals(qiniu.getZone(), QiniuZone.XIN_JIA_PO)) {
//			zone = Zone.xinjiapo();
//		}
//		return new com.qiniu.storage.Configuration(zone);
//	}

}

