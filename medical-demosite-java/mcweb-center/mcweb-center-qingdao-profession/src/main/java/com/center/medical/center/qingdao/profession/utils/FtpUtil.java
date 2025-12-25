package com.center.medical.center.qingdao.profession.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpConfig;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.json.JSONUtil;
import com.center.medical.center.qingdao.profession.constant.Constants;
import com.center.medical.center.qingdao.profession.entity.dto.ZhongkangConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 路飞
 * @date: 2023-06-26 14:22
 * @description: FTP工具类
 */
@Slf4j
public class FtpUtil {
    //ftp路径分隔符
    public final static String SEPERATOR = "/";


    /**
     * ftp上传文件
     *
     * @param destPath 远程FTP目录
     * @param fileName 文件名
     * @param file     文件
     * @param isFtp    是否通过ftp上传
     * @return
     */
    public static Boolean upload(String destPath, String fileName, InputStream file, Boolean isFtp) {
//        log.info("上传文件:{}、{}、{}、{}", destPath, fileName, file, isFtp);

        Boolean result;
        try {
            // 调用方法将InputStream写入文件
            String fileRoot = ZhongkangConfig.getProfile();
            String osName = System.getProperty("os.name").toLowerCase();
//                System.out.println("当前系统：" + osName);
            if (osName.contains("linux")) {
                // Linux系统
                fileRoot = ZhongkangConfig.getProfileLinux();
            }
            String filePath = fileRoot + (destPath.startsWith(Constants.RESOURCE_PREFIX) ? StrUtil.removePrefix(destPath, Constants.RESOURCE_PREFIX):(destPath.startsWith(Constants.RESOURCE_PREFIX1)?StrUtil.removePrefix(destPath, Constants.RESOURCE_PREFIX1):destPath));
            log.info("写入文件的路径filePath：{}、fileName：{}",filePath,fileName);
            writeToFile(file, filePath,fileName);
            result = Boolean.TRUE;
        } catch (IOException e) {
            log.error("文件写入失败", e);
            //TODO wait 上传失败后做记录，等待重新上传
            result = Boolean.FALSE;
        } finally {
            // 确保InputStream被关闭
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;


    }

    /**
     * 将InputStream写入文件
     *
     * @param inputStream 输入流
     * @param filePath    目标文件路径
     * @throws IOException 如果写入文件失败
     */
    public static void writeToFile(InputStream inputStream, String filePath, String fileName) throws IOException {
//        log.info("写入文件filePath：{}", filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream不能为空");
        }
//        log.info("写入文件filePath：{},{}", filePath, FileUtil.exist(filePath));
        if (!FileUtil.exist(filePath)){
//            log.info("创建目录：{}", filePath);
            FileUtil.mkdir(filePath);
        }

        // 创建目标文件
        File targetFile = new File((filePath.endsWith("/")?filePath:filePath+"/")+fileName);

        // 创建文件输出流
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 从InputStream读取数据并写入文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

//            log.info("文件已成功写入: {}" , filePath);
        } catch (IOException e) {
            throw new IOException("无法将InputStream写入文件", e);
        }
    }


//    /**
//     * 上传
//     *
//     * @param localFilePath   源文件地址
//     * @param remoteDirectory 远程FTP目录
//     * @param fileName        文件名称
//     * @return
//     */
//    public static Boolean upload(String localFilePath, String remoteDirectory, String fileName) {
//
//        String server = "XXX.XXX.XXX.XXX0"; // FTP服务器地址
//        int port = 21; // FTP服务器端口号
//        String user = "admin"; // FTP登录用户名
//        String password = "abc.123"; // FTP登录密码
//
//        Boolean upload = Boolean.FALSE; // 上传结果
//        FTPClient ftpClient = new FTPClient();
//        try {
//            ftpClient.connect(server, port);
//            ftpClient.login(user, password);
//            ftpClient.enterLocalPassiveMode();
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//
//            File localFile = new File(localFilePath);
//            FileInputStream inputStream = new FileInputStream(localFile);
//
//            String remoteFilename = localFile.getName();
//            System.out.println("文件名称：" + remoteFilename);
//            String remoteFilePath = remoteDirectory + "/" + remoteFilename;
//            System.out.println("文件：" + remoteFilePath);
//
//            //判断目录是否存在，不存在则
//            if (!ftpClient.changeWorkingDirectory(remoteDirectory)) {
//                if (ftpClient.makeDirectory(remoteDirectory)) {
//                    System.out.println("远程目录创建成功！");
//                    upload = Boolean.TRUE;
//                } else {
//                    System.out.println("远程目录创建失败！");
//                    upload = Boolean.FALSE;
//                    //TODO wait 上传失败后做记录
//                }
//            }
//
//            boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
//            inputStream.close();
//
//            if (uploaded) {
//                upload = Boolean.TRUE;
//                System.out.println("图片上传成功！");
//                //TODO wait 上传成功后做记录
//            } else {
//                upload = Boolean.FALSE;
//                System.out.println("图片上传失败！");
//                //TODO wait 上传失败后做记录
//            }
//
//            ftpClient.logout();
//            ftpClient.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //TODO wait 上传失败后做记录
//            return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//    }
}
