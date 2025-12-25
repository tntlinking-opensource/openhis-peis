package com.center.medical.system.config;

import cn.hutool.core.io.FileUtil;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

/**
 * @author: 路飞
 * @date: 2023/7/4 15:57
 * @description: SQL脚本执行工具
 */
@Slf4j
@Component
public class SqlScriptExecutor {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void executeRemoteSqlScript(String remoteScriptUrl) {
        if (StringUtils.isBlank(remoteScriptUrl)) {
            log.error("------------SQL脚本文件地址为空");
            throw new ServiceException("SQL脚本文件地址不能为空！");
        }

        log.info("------------开始下载数据库更新文件：{}", remoteScriptUrl);
        String[] sqlList;
        try {
            String sqlScript = readSql(remoteScriptUrl, 0);
            sqlList = parseSqlScript(sqlScript);
        } catch (IOException e) {
            log.error("------------读取SQL文件失败！");
            throw new ServiceException("读取SQL文件失败!");
        }
//            log.info("------------开始执行数据库更新操作sqlList:{}", JSONUtil.toJsonStr(sqlList));
        for (String sql : sqlList) {
            if (StringUtils.isNotBlank(sql)) {
                log.info("------------开始执行数据库更新操作sql:{}", sql);
                try {
                    // 执行 SQL 脚本
                    jdbcTemplate.execute(sql);
                } catch (Exception e) {
                    //SQL执行失败
                    log.error("------------SQL执行失败：{}", e);
                    throw new ServiceException("SQL执行失败：" + e.getMessage());
                }
            }
        }
        log.info("------------数据库更新操作完成");

    }

    /**
     * 将读取sql脚本文件中的内容
     *
     * @param urlString sql文件地址
     * @param flag      文件类型： 0.远程文件 1.本地文件
     * @return
     * @throws IOException
     */
    private static String readSql(String urlString, Integer flag) throws IOException {
        InputStream inputStream = null;
        if (flag == 0) {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            inputStream = connection.getInputStream();
        } else {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            File file = new File(urlString);
            inputStream = FileUtil.getInputStream(file);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            return content.toString();
        } catch (Exception e) {
            log.error("------------读取sql文件失败：{}", e);
            throw new ServiceException("读取sql文件失败!");
        }
    }

    /**
     * 将字符串解析成单条SQL语句
     *
     * @param sqlScript
     * @return
     */
    private static String[] parseSqlScript(String sqlScript) {
        // 使用正则表达式匹配分号，但要注意分号可能在字符串中，因此需要一些复杂的逻辑
        Pattern pattern = Pattern.compile("(?s);(?=(?:[^']*'[^']*')*[^']*$)");
        String[] statements = pattern.split(sqlScript);

        // 将匹配到的语句分割成数组
        return statements;
    }

}
