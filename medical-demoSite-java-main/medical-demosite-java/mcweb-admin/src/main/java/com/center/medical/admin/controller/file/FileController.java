package com.center.medical.admin.controller.file;

import cn.hutool.core.io.FileUtil;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author 路飞
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
@Api(tags = "文件操作(上传下载)")
public class FileController {

    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;

    /**
     * 获取资源地址
     */
    @GetMapping("/getDomain")
    @ApiOperation(value = "获取资源地址", notes = "获取资源地址")
    public R<Domain> getDomain() {
        Domain domain = systemConfig.getDomain();
        domain.setIsOnline(ZhongkangConfig.isOnline() ? 1 : 0);
        return R.ok(domain);
    }

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    @ApiOperation(value = "通用下载请求", notes = "通用下载请求")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ZhongkangConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ApiOperation(value = "通用上传请求（单个）", notes = "通用上传请求（单个）")
    @ApiImplicitParam(name = "file", value = "文件资源")
    public R<String> uploadFile(MultipartFile file) {
        log.info("通用上传请求");
        String extName = FileUtil.extName(file.getOriginalFilename());
        String fileName = FileUtil.mainName(file.getOriginalFilename());
        try {
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.CPP.value());
            Attachment attachment = new Attachment();
            attachment.setFileType(extName);
            //TODO wait等待完善文件类型判断
            attachment.setType(0);
            attachment.setBranchId(SecurityUtils.getCId());
            attachment.setCreatedate(new Date());
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
            log.info("上传结果：{}、{}", attachment.getId(), attachment);
            return R.ok(attachment.getFilePath());
        } catch (Exception e) {
            log.error("上传失败：{}、{}", fileName, e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ApiOperation(value = "通用上传请求（多个）", notes = "通用上传请求（多个）")
    public R uploadFiles(List<MultipartFile> files) {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.CPP.value());
        List<String> urlList = new ArrayList<>();
        List<String> successList = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = FileUtil.mainName(file.getOriginalFilename());
            try {
                // 上传文件路径
                Attachment attachment = new Attachment();
                String extName = FileUtil.extName(file.getOriginalFilename());
                attachment.setFileType(extName);
                //TODO wait等待完善文件类型判断
                attachment.setType(0);
                attachment.setBranchId(SecurityUtils.getCId());
                attachment.setCreatedate(new Date());
                attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
                log.info("上传结果：{}、{}", attachment.getId(), attachment);
                urlList.add(attachment.getFilePath());
                successList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");

            } catch (Exception e) {
                failList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败！</br>");
                log.error("通用上传请求（多个）, 文件<font color='red'>" + fileName + "</font>上传失败，{}", e);
            }
        }
        MultUploadResultVo result = new MultUploadResultVo();
        result.setSuccessList(successList);
        result.setFailList(failList);
        result.setUrlList(urlList);
        result.setResultMsg(resultMsg.toString());
        return R.ok(result);
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    @ApiOperation(value = "本地资源通用下载", notes = "本地资源通用下载")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = ZhongkangConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    public static void main(String[] args) {
        String server = "XXX.XXX.XXX.XXX0"; // FTP服务器地址
        int port = 21; // FTP服务器端口号
        String user = "admin"; // FTP登录用户名
        String password = "abc.123"; // FTP登录密码

        String localFilePath = "D:/data/txt/090000886908_1.txt"; // 本地图片文件路径
        String remoteDirectory = "/newimage/images/common"; // 远程FTP目录

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(localFilePath);
            FileInputStream inputStream = new FileInputStream(localFile);

            String remoteFilename = localFile.getName();
            System.out.println("文件名称：" + remoteFilename);
            String remoteFilePath = remoteDirectory + "/" + remoteFilename;
            System.out.println("文件：" + remoteFilePath);

            //判断目录是否存在，不存在则
            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
                if (ftpClient.makeDirectory(remoteDirectory)) {
                    System.out.println("远程目录创建成功！");
                } else {
                    System.out.println("远程目录创建失败！");
                    return;
                }
            }

            boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            if (uploaded) {
                System.out.println("图片上传成功！");
            } else {
                System.out.println("图片上传失败！");
            }

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
