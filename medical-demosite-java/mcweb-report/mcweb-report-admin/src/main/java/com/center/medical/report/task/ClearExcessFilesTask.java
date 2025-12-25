package com.center.medical.report.task;

import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.system.service.ISysConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 清除报告临时文件的定时任务
 */
@Component("ClearExcessFilesTask")
public class ClearExcessFilesTask {

    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 清除报告多余文件
     */
    public void start() {
        System.out.println("清除报告临时文件任务执行中--------------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(22L, ips)) {
            String tempFolderPath = System.getProperty("user.dir") + "/temp";
            File tempFolder = new File(tempFolderPath);
            File[] files = tempFolder.listFiles();

            //删除temp文件夹下除了file的文件
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && !file.getName().equals("file")) {
                        // 删除文件夹及其内容
                        deleteFolder(file);
                    }
                }
            }

            //删除file文件夹下除了wordmodel的文件
            String tempFolderPath2 = System.getProperty("user.dir") + "/temp/file";
            File tempFolder2 = new File(tempFolderPath2);
            File[] files2 = tempFolder2.listFiles();
            if (files2 != null) {
                for (File file : files2) {
                    if (file.isDirectory() && !file.getName().equals("wordmodel")) {
                        // 删除文件夹及其内容
                        deleteFolder(file);
                    }
                }
            }

            System.out.println("清除报告临时文件任务成功！！！！");
        }

    }


    /**
     * 递归删除文件夹及文件
     *
     * @param folder
     */
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归删除子文件夹
                    deleteFolder(file);
                } else {
                    // 删除文件
                    file.delete();
                }
            }
        }
        // 删除空文件夹
        folder.delete();
    }


}
