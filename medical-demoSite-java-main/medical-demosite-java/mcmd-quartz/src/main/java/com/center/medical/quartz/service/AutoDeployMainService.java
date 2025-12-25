package com.center.medical.quartz.service;

import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.system.bean.model.DeployVersionBranch;

import java.util.List;

/**
 * 自动更新线程（主服务）
 * @author xhp
 * @since 2023-12-04 14:28
 */
public interface AutoDeployMainService {

    /**
     * 执行数据库更新
     */
    void updateDb();

    /**
     * 查询需要更新sql语句的记录
     * @return
     */
    List<DeployVersionBranch> selectSqlUpdateList();

    /**
     * 执行数据库更新语句
     * @param updateSql
     * @throws Exception
     */
    void runSqlScript(String updateSql) throws Exception;

    /**
     * 获取最新版本信息
     * @return
     */
    DeployVersionDto getVersion();

    /**
     * 检查配置文件是否正确配置
     * @return
     */
    boolean checkConfig();

    /**
     * 保存更新结果
     * @param deployVersionDto
     * @param isSuccess
     * @param message
     */
    void saveRecord(DeployVersionDto deployVersionDto,int isSuccess,String message);
}
