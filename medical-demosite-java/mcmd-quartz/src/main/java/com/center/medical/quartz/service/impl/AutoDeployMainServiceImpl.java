package com.center.medical.quartz.service.impl;

import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.ddl.DdlScript;
import com.center.medical.bean.enums.DeployType;
import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.bean.param.AutoDeploySaveRecordParam;
import com.center.medical.common.config.AutoDeployConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.service.AutoDeployService;
import com.center.medical.quartz.service.AutoDeployMainService;
import com.center.medical.system.bean.model.DeployVersion;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.center.medical.system.dao.DeployVersionBranchMapper;
import com.center.medical.system.dao.DeployVersionMapper;
import com.center.medical.system.service.DeployVersionBranchService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

/**
 * 自动更新线程（主服务）关闭并发
 * @author xhp
 * @since 2023-12-04 14:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDeployMainServiceImpl implements AutoDeployMainService {
    private final DataSource dataSource;
    private DdlScript ddlScript;
    private final DeployVersionBranchMapper deployVersionBranchMapper;
    private final ISysBranchService iSysBranchService;
    private final DeployVersionMapper deployVersionMapper;
    private final DeployVersionBranchService deployVersionBranchService;
    private final AutoDeployConfig autoDeployConfig;

    /**
     * 执行数据库更新
     */
    @Override
    public void updateDb(){
        log.info("开始更新数据库");

        //查询所有需要执行sql更新的记录
        List<DeployVersionBranch> deployVersionBranches=selectSqlUpdateList();
        log.info("共{}条sql脚本需要更新",deployVersionBranches.size());

        //执行sql
        Date currentDate=new Date();
        for(DeployVersionBranch deployVersionBranch:deployVersionBranches){
            try{
                DeployVersion deployVersion=deployVersionMapper.selectById(deployVersionBranch.getVersionId());
                log.info(deployVersion.getUpdateSql());

                runSqlScript(deployVersion.getUpdateSql());

                deployVersionBranch.setIsSqlUpdated(1);
                deployVersionBranch.setSqlUpdateTime(currentDate);
                deployVersionBranch.setModifydate(currentDate);
                deployVersionBranchMapper.updateById(deployVersionBranch);
                log.info("sql脚本执行成功");
            }catch(Exception e){
                log.error("sql脚本执行失败",e);
                deployVersionBranch.setIsSqlUpdated(2);
                deployVersionBranch.setSqlUpdateTime(currentDate);
                deployVersionBranch.setModifydate(currentDate);
                deployVersionBranchMapper.updateById(deployVersionBranch);
            }
        }

    }

    /**
     * 查询需要更新sql语句的记录
     * @return
     */
    @Override
    public List<DeployVersionBranch> selectSqlUpdateList(){
        String branchId=iSysBranchService.getDefaultBranch().getBranchId();
        return deployVersionBranchMapper.selectSqlUpdateList(branchId);
    }

    /**
     * 执行数据库更新语句
     * 多条语句，遇到错误后不再继续执行.
     * 执行失败时抛出异常,已执行成功的ddl语句仍然生效
     * @param updateSql sql语句，多条用;\n拼接，\n不可省略
     */
    @Override
    public void runSqlScript(String updateSql) throws Exception {
        if(ddlScript==null)ddlScript=new DdlScript(dataSource);
        ddlScript.run(new StringReader(updateSql));
    }

    /**
     * 检查配置文件是否正确配置
     * @return
     */
    @Override
    public boolean checkConfig(){
        log.info("检查自动部署配置文件是否正确");
        Integer updateType=autoDeployConfig.getUpdateType();
        if(updateType==null){
            log.info("deploy.updateType不能为空");
            return false;
        }
        log.info("自动部署配置文件正确");
        return true;
    }


    /**
     * 获取最新版本信息
     * @return
     */
    @Override
    public DeployVersionDto getVersion(){
        String ksIp=SystemUtil.getHostInfo().getAddress();
        Integer updateType= DeployType.MCWEB_ADMIN.getId();
        DeployVersionDto deployVersionDto = deployVersionBranchService.getVersion(ksIp,updateType);
        deployVersionDto.setDownloadJarPath(ZhongkangConfig.getDownloadPath() + deployVersionDto.getFilePath());
        String jarPath= AutoDeployService.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        deployVersionDto.setJarPath(jarPath.substring(jarPath.indexOf(":")+1,jarPath.indexOf("!")));
        return deployVersionDto;
    }

    /**
     * 保存更新结果
     * @param deployVersionDto
     * @param isSuccess
     * @param message
     */
    @Override
    @Transactional
    public void saveRecord(DeployVersionDto deployVersionDto,int isSuccess,String message){
        log.info("保存更新结果");
        AutoDeploySaveRecordParam param=new AutoDeploySaveRecordParam();
        param.setIsSuccess(isSuccess);
        param.setMessage(message);
        param.setVersionId(deployVersionDto.getId());
        param.setKsIpId(deployVersionDto.getKsIpId());
        param.setMessage(message);
        param.setAddress(SystemUtil.getHostInfo().getAddress());
        param.setHostName(SystemUtil.getHostInfo().getName());
        deployVersionBranchService.saveRecord(param);
    }
}
