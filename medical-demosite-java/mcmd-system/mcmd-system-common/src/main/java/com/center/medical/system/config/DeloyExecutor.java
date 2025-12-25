package com.center.medical.system.config;

import com.center.medical.system.bean.dto.DeloyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: 路飞
 * @date: 2023/7/4 15:57
 * @description: 系统版本更新部署执行器
 */
@Slf4j
@Component
public class DeloyExecutor {
    @Autowired
    private SqlScriptExecutor sqlScriptExecutor;

    /**
     * 执行
     *
     * @param param
     */
    public void run(DeloyDto param) {
//        //1.执行数据库更新操作
//        for (String sql : param.getSqlFiles()) {
//            sqlScriptExecutor.executeRemoteSqlScript(sql);
//        }
//
//        //2.执行程序更新操作
////        executeScriptAndShutdown("D:\\local-git-repository\\customer\\zhongkang\\medical\\medical-java\\doc\\local.bat");
//        executeScriptAndShutdown(param);
        log.info("DeloyExecutor.run执行完成：{}", param);
    }

    @Async
    public void executeScriptAndShutdown(DeloyDto param) {
        log.info("开始执行部署脚本：{}", param);
        try {
            // 执行 running.bat 脚本
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", param.getScriptPath(), param.getFileName(), param.getYmlActive(), param.getServiceFile(), param.getCommand());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            // 等待进程执行结束
            int exitCode = process.waitFor();

            // 输出进程执行结果
            System.out.println("Process exited with code: " + exitCode);
            // 不等待脚本执行完成
        } catch (IOException | InterruptedException e) {
            log.error("脚本执行失败：{}", e);
            e.printStackTrace();
        }
        log.info("部署脚本执行完毕...");
    }

    public static void main(String[] args) {
        // 指定脚本路径
        String scriptPath = "D:\\local-git-repository\\customer\\zhongkang\\medical\\medical-java\\doc\\local.bat";

        try {
            // 构建 ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", scriptPath);
            // 重定向错误流到输出流
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 等待进程执行结束
            int exitCode = process.waitFor();

            // 输出进程执行结果
            System.out.println("Process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
