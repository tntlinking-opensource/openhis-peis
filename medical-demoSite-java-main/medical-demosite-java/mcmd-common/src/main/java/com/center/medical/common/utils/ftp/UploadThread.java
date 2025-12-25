package com.center.medical.common.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: 路飞
 * @date: 2023-06-26 14:11
 * @description: FTP上传线程工具
 */
public class UploadThread extends Thread {
    private FTPClient ftpClient;
    private File file;
    private String remotePath;

    public UploadThread(FTPClient ftpClient, File file, String remotePath) {
        this.ftpClient = ftpClient;
        this.file = file;
        this.remotePath = remotePath;
    }

    @Override
    public void run() {
        try {
            // 切换到指定的工作目录
            ftpClient.changeWorkingDirectory(remotePath);
            // 上传文件
            ftpClient.storeFile(file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
