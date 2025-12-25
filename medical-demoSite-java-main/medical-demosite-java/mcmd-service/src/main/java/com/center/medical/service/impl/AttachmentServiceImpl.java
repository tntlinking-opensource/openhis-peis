package com.center.medical.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.SpecialSymbolsDataDto;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.ThirdPartyImagesVo;
import com.center.medical.common.config.AliOss;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.HuaWeiOss;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.DicomConstants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.common.utils.ftp.FtpUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AttachmentMapper;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import com.google.common.io.Files;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.regex.Pattern;
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

    private final AttachmentMapper attachmentMapper;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final ISysConfigService iSysConfigService;


    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Attachment> getList(PageParam<Attachment> page, Attachment param) {
        return attachmentMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Attachment getInfoById(String id) {
        return attachmentMapper.getInfoById(id);
    }

    /**
     * 保存图片
     *
     * @param attachment
     */
    @Override
    public void savePicture(Attachment attachment) {
        if (StringUtils.isNotBlank(attachment.getDepId())) {
            String ksId = attachment.getDepId();
            if ("163".equals(ksId)) {
                attachment.setFileType("ELE");
            } else {
                SysDept dept = iSysDeptService.getByDeptNo(ksId);
                List<String> pacs = Arrays.asList("us", "dr", "cr", "ct", "mr", "dx");
                if (pacs.contains(dept.getJklx())) {
                    attachment.setFileType("PACS");
                }
            }
        }
        attachmentMapper.insert(attachment);
    }

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
        if ("PACS".equals(attachment.getFileType())) {
            try {
                //判断是否线上
                if (ZhongkangConfig.isOnline()) {
                    upload(file.getBytes(), StrUtil.subAfter(attachment.getFilePath(), "/", false), extName);
                } else {
                    //线下系统将文件储存至指定的硬盘中位置中
                    FtpUtil.upload(StrUtil.subBefore(attachment.getFilePath(), "/", true), StrUtil.subAfter(attachment.getFilePath(), "/", true), file.getInputStream(), false);
                }
            } catch (IOException e) {
                log.error("上传文件失败！{}", e);
                // 文件上传失败，保存到本地文件夹中
                try {
                    file.transferTo(new File(DicomConstants.FAILED_FILE_PATH + "/" + file.getName()));
                } catch (IOException ex) {
                    log.error("上传失败文件保存失败！{}", ex);
                }
            }
        } else {
            try {
                //判断是否线上
                if (ZhongkangConfig.isOnline()) {
                    upload(file.getBytes(), StrUtil.subAfter(attachment.getFilePath(), "/", false), extName);
                } else {
                    //线下系统将文件储存至指定的硬盘中位置中
                    FtpUtil.upload(StrUtil.subBefore(attachment.getFilePath(), "/", true), StrUtil.subAfter(attachment.getFilePath(), "/", true), file.getInputStream(), false);
                }
            } catch (IOException e) {
                log.error("上传文件失败！{}", e);
                throw new ServiceException("上传文件失败！");
            }
        }
        if (saveFlag) {
            this.saveOrUpdate(attachment);
        }
        return attachment;
    }


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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment uploadFile(File file, Attachment attachment, String baseDir, String extName, String fileName, Boolean saveFlag, Boolean isDelete) throws IOException {
        if (!file.isFile()) {
            return attachment;
        }
        // log.info("上传文件:{}、{}、{}、{}、{}", attachment, baseDir, fileName, extName, ZhongkangConfig.isOnline());
        String filePath = randomFilePath(extName, fileName);
        // 数据存入数据库
        attachment.setFilePath(Constants.RESOURCE_PREFIX + "/" + baseDir + "/" + filePath);
        attachment.setFileSize(Math.toIntExact(file.length()));
        // 上传
        if ("PACS".equals(attachment.getFileType())) {
//            executorService.submit(() -> {
//                Date startTime = new Date();
//                BufferedInputStream inputStream = FileUtil.getInputStream(file);
//                try {
//                    //判断是否线上
//                    if (ZhongkangConfig.isOnline()) {
//                        byte[] byteArray = Files.toByteArray(file);
//                        upload(byteArray, StrUtil.subAfter(attachment.getFilePath(), "/", false), extName);
//                    } else {
//                        //线下系统将文件储存至指定的硬盘中位置中
//                        FtpUtil.upload(StrUtil.subBefore(attachment.getFilePath(), "/", true), StrUtil.subAfter(attachment.getFilePath(), "/", true), inputStream);
//                    }
//                } catch (IOException e) {
//                    log.error("上传文件失败！{}", e);
//                    // 文件上传失败，保存到本地文件夹中
//                    try {
//                        Files.copy(file, new File(DicomConstants.FAILED_FILE_PATH + "/" + file.getName()));
//                    } catch (IOException ex) {
//                        log.error("上传失败文件保存失败！{}", ex);
//                    }
//                } finally {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        log.error("inputStream关闭失败！{}", e);
//                    }
//                }
//                Date endTime = new Date();
//                log.info("上传文件用时：{}-{}={}", DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.between(startTime, endTime, DateUnit.SECOND));
//                if (isDelete) {
//                    file.delete();
//                }
//            });
        } else {
            BufferedInputStream inputStream = FileUtil.getInputStream(file);
            try {
                //判断是否线上
                if (ZhongkangConfig.isOnline()) {
                    byte[] byteArray = Files.toByteArray(file);
                    upload(byteArray, StrUtil.subAfter(attachment.getFilePath(), "/", false), extName);
                } else {
                    //线下系统将文件储存至指定的硬盘中位置中
                    FtpUtil.upload(StrUtil.subBefore(attachment.getFilePath(), "/", true), StrUtil.subAfter(attachment.getFilePath(), "/", true), inputStream, false);
                }
            } catch (IOException e) {
                log.error("上传文件失败！{}", e);
                throw new ServiceException("上传文件失败！");
            } finally {
                inputStream.close();
                if (isDelete) {
                    file.delete();
                }
            }
        }
        if (saveFlag) {
            this.saveOrUpdate(attachment);
        }
        return attachment;
    }

    /**
     * 上传同步文件
     *
     * @param host     源文件host地址
     * @param filePath 图片地址
     * @param isOnline 图片存储地方：1线上， 2线下内网
     * @return 图片路径
     * @throws IOException
     */
    @Override
    public Boolean uploadSyncFile(String host, String filePath, Integer isOnline) throws IOException {
        //同步至线上
        host = host.endsWith("/") ? host : host + "/";
        filePath = filePath.startsWith("/") ? StrUtil.subAfter(filePath, "/", false) : filePath;
        String localFilePath = host + filePath;
        String extName = FileUtil.extName(localFilePath);

        //判断是否已经同步过相同的文件
        Domain domain = iSysConfigService.getDomain();
        String toHost = "";
        if (isOnline == 1) {
            toHost = domain.getRsPfDomain();
        } else {
            toHost = domain.getRsLcDomain();
        }
        toHost = toHost.endsWith("/") ? toHost : toHost + "/";
        log.info("同步文件：{}、{}", localFilePath, toHost + filePath);
        if (FileUtils.areFilesSameSize(localFilePath, toHost + filePath)) {
            log.info("文件相同无需同步");
            return Boolean.TRUE;
        } else {
            log.info("文件不相同需要同步");
        }
        // 获取远程图片的输入流
        URL url = new URL(localFilePath);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        if (isOnline == 1) {
            //同步至线上
            byte[] bytes = FileUtils.convertInputStreamToByteArray(inputStream);
            upload(bytes, filePath, extName);
        } else {
            //线下系统将文件储存至指定的硬盘中位置中
            FtpUtil.upload(StrUtil.subBefore(filePath, "/", true), StrUtil.subAfter(filePath, "/", true), inputStream, false);
        }
//        FtpUtil.upload(StrUtil.subBefore(filePath, "/", true), StrUtil.subAfter(filePath, "/", true), inputStream);
        inputStream.close();
//            log.info("上传成功!");
//        } catch (FileNotFoundException e) {
//            log.error("上传失败,文件不存在：", e);
//            throw new FileNotFoundException(e.getMessage());
//        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) throws IOException {
        // 获取远程图片的输入流
        URL url = new URL("https://newtj.obs.cn-north-4.myhuaweicloud.com/newimage/files/material/01/20250815/45df184b115d459792be293135cb7305.xlsx");
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
    }

    /**
     * 根据文件路径删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(String filePath) {
        attachmentMapper.delete(new LambdaQueryWrapper<Attachment>().eq(Attachment::getFilePath, filePath));

        if (!ZhongkangConfig.isOnline()) {
            //线下系统删除文件
            FileUtils.deleteFile(filePath);
            return;
        }

//        try {
        //七牛云
//            Qiniu qiniu = shopConfig.getQiniu();
//            if (Objects.nonNull(qiniu) && BooleanUtil.isTrue(qiniu.getIsOpen())) {
//                bucketManager(qiniu).delete(qiniu.getBucket(), filePath);
//                return;
//            }

        AliOss aliOss = systemConfig.getAliOss();
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

//            QCloud qCloud = shopConfig.getQCloud();
//            if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(qCloud.getIsOpen())) {
//                COSClient cosClient = cosClient(qCloud);
//                try {
//                    cosClient.deleteObject(qCloud.getBucketName(), filePath);
//                } catch (Exception e) {
//                    log.error("腾讯云Oss删除文件错误：", e);
//                } finally {
//                    cosClient.shutdown();
//                }
//                return;
//            }
//
//            HuaWeiOss huaWeiOss = shopConfig.getHuaWeiObs();
//            if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(huaWeiOss.getIsOpen())) {
//                ObsClient obsClient = new ObsClient(huaWeiOss.getAccessKeyId(), huaWeiOss.getSecretAccessKey(), huaWeiOss.getEndpoint());
//                try {
//                    obsClient.deleteObject(huaWeiOss.getBucketName(), filePath);
//                } catch (Exception e) {
//                    log.error("华为Oss删除文件错误：", e);
//                } finally {
//                    obsClient.close();
//                }
//            }
//
//            Minio minio = shopConfig.getMinio();
//            if (Objects.nonNull(minio) && BooleanUtil.isTrue(minio.getIsOpen())) {
//                MinioClient minioClient = MinioClient.builder()
//                        .endpoint(minio.getEndpoint())
//                        .credentials(minio.getAccessKey(), minio.getSecretKey())
//                        .build();
//                try {
//                    minioClient.removeObject(
//                            RemoveObjectArgs.builder()
//                                    .bucket(minio.getBucketName())
//                                    .object(filePath).build()
//                    );
//                } catch (Exception e) {
//                    log.error("minio删除文件错误：", e);
//                }
//            }
//        } catch (QiniuException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            log.error("IOException：", e);
//        }
    }

    /**
     * 根据文件Id列表与店铺id批量删除文件记录
     *
     * @param ids
     * @param branchId
     */
    @Override
    public void deleteByIdsAndBranchId(List<String> ids, String branchId) {
        List<Attachment> attachmentList = attachmentMapper.getByIds(ids);
        // 获取文件的实际路径--数据库中保存的文件路径为： / + 实际的文件路径
        List<String> filePaths = attachmentList.stream().map(item -> {
            if (!branchId.equals(item.getBranchId())) {
                throw new ServiceException("存在非本中心下的文件，删除失败");
            }
            return item.getFilePath();
        }).collect(Collectors.toList());
        this.batchDeleteFiles(filePaths);
        attachmentMapper.batchDeleteByIds(ids);
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


    private String randomFilePath(String fileType, String filename) {
        fileType = StringUtils.isNotBlank(fileType) ? fileType : "jpg";
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/" + ((StringUtils.isBlank(filename) ? (IdUtil.simpleUUID() + "." + fileType) : filename));
    }

    @Override
    public void downloadZipFromOSS(String zipName, List<String> paths) {
        boolean online = ZhongkangConfig.isOnline();
        HuaWeiOss huaWeiOss = systemConfig.getHuaWeiObs();
//        log.info("阿里云OSS配置信息：{}", aliOss);
        FileOutputStream fos = null;
        // 压缩文件输出流
        ZipOutputStream zos = null;
        if (Objects.nonNull(huaWeiOss) && BooleanUtil.isTrue(huaWeiOss.getIsOpen())) {
            ObsClient ossClient = new ObsClient(huaWeiOss.getAccessKeyId(), huaWeiOss.getSecretAccessKey(), huaWeiOss.getEndpoint());
            try {
                // 创建zip文件
                fos = new FileOutputStream(zipName);
                // 作用是为任何OutputStream产生校验和
                // 第一个参数是制定产生校验和的输出流，第二个参数是指定Checksum的类型 （Adler32（较快）和CRC32两种）
                CheckedOutputStream cos = new CheckedOutputStream(fos, new Adler32());
                // 用于将数据压缩成zip文件格式
                zos = new ZipOutputStream(cos);

                ObsObject ossObject = null;
                InputStream is = null;
                // 循环根据路径从OSS获得对象，存入临时文件zip中
                for (String path : paths) {
                    try {
                        log.info("下载文件路径 {}", path);
                        if (online) {
                            // 根据path 和 bucket 从OSS获取对象
                            path = path.startsWith("/") ? path.substring(1) : path;
                            ossObject = ossClient.getObject(huaWeiOss.getBucketName(), path);
                            // 获取输出流
                            is = ossObject.getObjectContent();
                        } else {
                            String fileUrl = systemConfig.getDownloadFileUrl(path);
                            // 构造URL
                            URL url = new URL(fileUrl);
                            URLConnection con = url.openConnection();
                            is = con.getInputStream();
                        }

                        // 将文件放入zip中，并命名不能重复
                        zos.putNextEntry(new ZipEntry(path));
                        // 向压缩文件中写数据
                        int len = 0;
                        while ((len = is.read()) != -1) {
                            zos.write(len);
                        }
                    } catch (Exception e) {
                        log.error("未找到文件 {}", path);
                        continue;
                    } finally {
                        // 关闭流
                        is.close();
                        zos.closeEntry();
                    }
                }
            } catch (FileNotFoundException e) {
                log.error("读取文件失败：{}、 {}", paths, e);
            } catch (UnsupportedEncodingException e) {
                log.error("关闭流is.close()失败：{}、 {}", paths, e);
            } catch (IOException e) {
                log.error("关闭流zos.closeEntry()失败：{}、 {}", paths, e);
            } finally {
                log.error("6读取文件失败：{}、 {}", paths);
                try {
                    // 关闭流
                    zos.close();
                } catch (Exception e) {
                    log.error("7读取文件失败：{}、 {}", paths, e);
                }
            }
        }
        log.error("8读取文件失败：{}、 {}", paths);
    }

    private void upload(byte[] bytes, String filePath, String extName) throws IOException {
//        Qiniu qiniu = shopConfig.getQiniu();
//        if (Objects.nonNull(qiniu) && BooleanUtil.isTrue(qiniu.getIsOpen())) {
//            String upToken = auth(qiniu).uploadToken(qiniu.getBucket(), filePath);
//            uploadManager(qiniu).put(bytes, filePath, upToken);
//            return;
//        }

        AliOss aliOss = systemConfig.getAliOss();
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

//        QCloud qCloud = shopConfig.getQCloud();
//        if (Objects.nonNull(qCloud) && BooleanUtil.isTrue(qCloud.getIsOpen())) {
//            //初始化cosClient
//            COSClient cosClient = cosClient(qCloud);
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(bytes.length);
//            objectMetadata.setContentType(extName);
//            ByteArrayInputStream input = null;
//            try {
//                input = new ByteArrayInputStream(bytes);
//                com.qcloud.cos.model.PutObjectRequest putObjectRequest =
//                        new com.qcloud.cos.model.PutObjectRequest(qCloud.getBucketName(), filePath, input, objectMetadata);
//                cosClient.putObject(putObjectRequest);
//            } catch (Exception e) {
//                log.error("腾讯云Oss上传文件错误：", e);
//            } finally {
//                cosClient.shutdown();
//                if (Objects.nonNull(input)) {
//                    input.close();
//                }
//            }
//            return;
//        }
//
        HuaWeiOss huaWeiOss = systemConfig.getHuaWeiObs();
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
//
//        Minio minio = shopConfig.getMinio();
//        if (Objects.nonNull(minio) && BooleanUtil.isTrue(minio.getIsOpen())) {
//            MinioClient minioClient = MinioClient.builder()
//                    .endpoint(minio.getEndpoint())
//                    .credentials(minio.getAccessKey(), minio.getSecretKey())
//                    .build();
//            InputStream input = null;
//            try {
//                input = new ByteArrayInputStream(bytes);
//                minioClient.putObject(
//                        PutObjectArgs.builder()
//                                .bucket(minio.getBucketName())
//                                .stream(input, input.available(), -1)
//                                .object(filePath)
//                                .build()
//                );
//            } catch (Exception e) {
//                log.error("minio上传文件错误：", e);
//            } finally {
//                if (Objects.nonNull(input)) {
//                    input.close();
//                }
//            }
//        }
    }


    // ================== 七牛云配置 ======================

    /**
     * 构建一个七牛上传工具实例
     */
//    private UploadManager uploadManager(Qiniu qiniu) {
//        return new UploadManager(qiniuConfig(qiniu));
//    }

    /**
     * 认证信息实例
     *
     * @return
     */
//    private Auth auth(Qiniu qiniu) {
//        return Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
//    }

    /**
     * 构建七牛空间管理实例
     */
//    private BucketManager bucketManager(Qiniu qiniu) {
//        return new BucketManager(auth(qiniu), qiniuConfig(qiniu));
//    }

    /**
     * 根据配置文件选择机房
     */
//    private com.qiniu.storage.Configuration qiniuConfig(Qiniu qiniu) {
//
//        Region region = null;
//        if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_BEI)) {
//            region = Region.huabei();
//        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_DONG)) {
//            region = Region.huadong();
//        } else if (Objects.equals(qiniu.getZone(), QiniuZone.HUA_NAN)) {
//            region = Region.huanan();
//        } else if (Objects.equals(qiniu.getZone(), QiniuZone.BEI_MEI)) {
//            region = Region.beimei();
//        } else if (Objects.equals(qiniu.getZone(), QiniuZone.XIN_JIA_PO)) {
//            region = Region.xinjiapo();
//        }
//        return new com.qiniu.storage.Configuration(region);
//    }


    /**
     * 创建腾讯云cos client
     *
     * @return
     */
//    private COSClient cosClient(QCloud qCloud) {
//        BasicCOSCredentials cred = new BasicCOSCredentials(qCloud.getSecretId(), qCloud.getSecretKey());
//        com.qcloud.cos.region.Region region = new com.qcloud.cos.region.Region(qCloud.getRegion());
//        ClientConfig clientConfig = new ClientConfig(region);
//        //https协议
//        clientConfig.setHttpProtocol(HttpProtocol.https);
//        return new COSClient(cred, clientConfig);
//    }

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

    /**
     * 查询第三方系统报告
     *
     * @param patientcode
     * @param feeItemId
     * @return
     */
    @Override
    public List<String> queryThirdReports(String patientcode, String feeItemId) {
        return attachmentMapper.queryThirdReports(patientcode, feeItemId);
    }

    /**
     * 清除附件表中的特殊符号
     * @param symbol
     * @return
     */
    @Override
    public Boolean clearSpecialSymbols(String symbol) {
        String fileRoot = ZhongkangConfig.getProfile();
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("linux")) {
            // Linux系统
            fileRoot = ZhongkangConfig.getProfileLinux();
        }
        //不知道为什么东区的路径多一层/newimage
        if (fileRoot.equals("G:/newimages/newimage")){
            fileRoot = "G:/newimages";
        }
        log.info("打印一下fileRoot" + fileRoot);
        // 正则表达式匹配特殊字符
        String regex = "[^a-zA-Z0-9_.-]";
        Pattern pattern = Pattern.compile(regex);
        //查询包含特殊符号的数据
        List<SpecialSymbolsDataDto> list = attachmentMapper.getSpecialSymbolsData(symbol);
        for (SpecialSymbolsDataDto dto : list) {
            String filePath = fileRoot + dto.getFilePath();
            File originalFile = new File(filePath);
            String originalFileName = originalFile.getName();
            // 使用正则表达式去掉文件名中的特殊字符
            String newFileName = pattern.matcher(originalFileName).replaceAll("");
            // 获取文件的新路径
            String newFilePath = originalFile.getParent() + File.separator + newFileName;
            log.info("newFilePath是{}",newFilePath);
            // 重命名文件
            if (originalFile.renameTo(new File(newFilePath))) {
                log.info("体检号:{}文件重命名成功,新文件地址是{}",dto.getPatientcode(),newFilePath);
                Attachment attachment = new Attachment();
                attachment.setId(dto.getId());
                attachment.setFilePath(newFilePath.replace(fileRoot,""));
                attachmentMapper.updateById(attachment);
            } else {
                log.info("体检号:{}文件重命名失败",dto.getPatientcode());
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 获取第三方系统报告
     * @param patientcode
     * @return
     */
    @Override
    public List<ThirdPartyImagesVo> getThirdPartyReport(String patientcode) {
        return attachmentMapper.getThirdPartyReport(patientcode);
    }


    /**
     * 上传华为云
     * @param host     源文件host地址
     * @param filePath 图片地址
     * @return
     * @throws IOException
     */
    @Override
    public void uploadHuaWeiReport(String host, String filePath) throws IOException {

        host = host.endsWith("/") ? host : host + "/";
        filePath = filePath.startsWith("/") ? StrUtil.subAfter(filePath, "/", false) : filePath;
        String fullPath = host + filePath;
        // 获取远程图片的输入流
        URL url = new URL(fullPath);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = FileUtils.convertInputStreamToByteArray(inputStream);
        HuaWeiOss huaWeiOss = systemConfig.getHuaWeiObs();

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
}

