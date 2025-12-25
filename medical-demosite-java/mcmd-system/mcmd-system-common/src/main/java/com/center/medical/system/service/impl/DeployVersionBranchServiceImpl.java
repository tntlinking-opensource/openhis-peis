package com.center.medical.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.system.bean.model.DeployRecord;
import com.center.medical.system.bean.model.DeployVersion;
import com.center.medical.system.bean.model.MdKsIp;
import com.center.medical.common.bean.param.AutoDeploySaveRecordParam;
import com.center.medical.system.dao.DeployMdKsIpMapper;
import com.center.medical.system.dao.DeployRecordMapper;
import com.center.medical.system.dao.DeployVersionBranchMapper;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.center.medical.system.dao.DeployVersionMapper;
import com.center.medical.system.service.DeployVersionBranchService;
import com.center.medical.system.service.ISysBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 自动部署-版本更新分中心关联表(DeployVersionBranch)服务实现类
 *
 * @author makejava
 * @since 2023-11-15 08:58:49
 */
@Slf4j
@Service("deployVersionBranchService")
@RequiredArgsConstructor
public class DeployVersionBranchServiceImpl extends ServiceImpl<DeployVersionBranchMapper, DeployVersionBranch> implements DeployVersionBranchService {

    private final DeployVersionBranchMapper deployVersionBranchMapper;

    private final DeployMdKsIpMapper deployMdKsIpMapper;

    private final DeployVersionMapper deployVersionMapper;

    private final DeployRecordMapper deployRecordMapper;

    private final Snowflake snowflake;

    private final ISysBranchService iSysBranchService;

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param DeployVersionBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DeployVersionBranch> getPage(PageParam<DeployVersionBranch> page, DeployVersionBranch param) {
        return deployVersionBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DeployVersionBranch getInfoById(Integer id) {
        return deployVersionBranchMapper.getInfoById(id);
    }


    /**
     * 自动更新-获取更新版本信息
     * @param ksIp
     * @param updateType
     * @return
     */
    @Override
    public DeployVersionDto getVersion(String ksIp,int updateType){
        String branchId=iSysBranchService.getDefaultBranch().getBranchId();
        //MdKsIp表，一个科室多个服务会有多条，多个科室用同一个服务也会有多条
        MdKsIp mdKsIp=deployMdKsIpMapper.selectOne(
                new QueryWrapper<MdKsIp>()
                    .eq("is_enable_update",1)
                    .eq("update_type",updateType)
                    .eq("status",1)
                    .eq("branch_id",branchId)
                    .likeRight("ip",ksIp+":")
        );
        if(mdKsIp==null){
            throw new ServiceException("md_ks_ip表中,科室ip:"+ksIp+",更新类型"+updateType+",不存在，无法自动更新");
        }
        if(mdKsIp.getIsEnableUpdate()==null||!mdKsIp.getIsEnableUpdate()){
            throw new ServiceException("md_ks_ip表中,科室"+mdKsIp.getName()+",is_enable_update!=1,不开启自动更新");
        }
        String deployStartupCommand=mdKsIp.getDeployStartupCommand();
        if(StrUtil.isBlank(deployStartupCommand)){
            throw new ServiceException("md_ks_ip表中,科室"+mdKsIp.getName()+",deployStartupCommand=null,没有配置启动更新服务命令，无法自动更新");
        }
        String serviceStartupCommand=mdKsIp.getServiceStartupCommand();
        if(StrUtil.isBlank(serviceStartupCommand)){
            throw new ServiceException("md_ks_ip表中,科室"+mdKsIp.getName()+",serviceStartupCommand=null,没有配置服务启动命令，无法自动更新");
        }
        String deployServiceAddr=mdKsIp.getDeployServiceAddr();
        if(StrUtil.isBlank(deployServiceAddr)){
            throw new ServiceException("md_ks_ip表中,科室"+mdKsIp.getName()+",deployServiceAddr=null,没有配置更新服务地址，无法自动更新");
        }
        String serviceAddr=mdKsIp.getServiceAddr();
        if(StrUtil.isBlank(serviceAddr)){
            throw new ServiceException("md_ks_ip表中,科室"+mdKsIp.getName()+",serviceAddr=null,没有配置被更新服务地址，无法自动更新");
        }

        //查询可更新的最新版本
        DeployVersion deployVersion=deployVersionMapper.getVersion(updateType);
        if(deployVersion==null){
            throw new ServiceException("已是最新版本");
        }

        //判断最新版本是否已更新
        String ksIpId=mdKsIp.getId();
        DeployRecord deployRecord=deployRecordMapper.selectOne(
                    new QueryWrapper<DeployRecord>()
                        .eq("ks_ip_id",ksIpId)
                    .eq("version_id",deployVersion.getId())
            );
        if(deployRecord!=null){
            //已自动更新
            if(deployRecord.getIsSuccess()!=null&&deployRecord.getIsSuccess()){
                throw new ServiceException("已是最新版本");
            }else{
                //更新失败已人工处理
                if(deployRecord.getIsManualSuccess()!=null&&deployRecord.getIsManualSuccess()){
                    throw new ServiceException("已是最新版本");
                }else{
                    //更新失败
                    throw new ServiceException("最新版本已更新过，但更新失败,请人工更新");
                }
            }
        }

        DeployVersionDto deployVersionDto=BeanUtil.toBean(deployVersion,DeployVersionDto.class);
        deployVersionDto.setDeployStartupCommand(deployStartupCommand);
        deployVersionDto.setServiceStartupCommand(serviceStartupCommand);
        deployVersionDto.setDeployServiceAddr(deployServiceAddr);
        deployVersionDto.setServiceAddr(serviceAddr);
        deployVersionDto.setKsIpId(ksIpId);
        return deployVersionDto;
    }

    /**
     * 保存更新记录
     * @param autoDeploySaveRecordParam
     */
    @Override
    @Transactional
    public void saveRecord(AutoDeploySaveRecordParam autoDeploySaveRecordParam){
        String ksIpId=autoDeploySaveRecordParam.getKsIpId();
        String branchId=iSysBranchService.getDefaultBranch().getBranchId();
        Date currentDate=new Date();

        //保存更新记录
        DeployRecord deployRecord= BeanUtil.toBean(autoDeploySaveRecordParam,DeployRecord.class);
        Integer versionId=deployRecord.getVersionId();
        DeployRecord deployRecordOld=deployRecordMapper.selectOne(
                new QueryWrapper<DeployRecord>()
                    .eq("version_id",versionId)
                .eq("ks_ip_id",ksIpId)
                .eq("branch_id",branchId)
        );
        if(deployRecordOld!=null){
            deployRecordOld.setModifydate(currentDate);
            deployRecordOld.setUpdateTime(currentDate);
            deployRecordOld.setIsSuccess(deployRecord.getIsSuccess());
            deployRecordOld.setMessage(deployRecord.getMessage());
            deployRecordOld.setAddress(deployRecord.getAddress());
            deployRecordOld.setHostName(deployRecord.getHostName());
            deployRecordMapper.updateById(deployRecordOld);
        }else{
            deployRecord.setId(String.valueOf(snowflake.nextId()));
            deployRecord.setCreatedate(currentDate);
            deployRecord.setModifydate(currentDate);
            deployRecord.setUpdateTime(currentDate);
            deployRecord.setBranchId(branchId);
            deployRecord.setIsManual(false);
            deployRecordMapper.insert(deployRecord);
        }

        //修改总更新状态
        MdKsIp mdKsIp=deployMdKsIpMapper.getInfoById(ksIpId);
        Integer updateType=mdKsIp.getUpdateType();
        int unUpdatedCount=deployVersionBranchMapper.selectUnUpdatedCount(updateType,versionId,branchId);
        DeployVersionBranch deployVersionBranch=deployVersionBranchMapper.selectOne(
                new QueryWrapper<DeployVersionBranch>()
                    .eq("is_delete",0)
                    .eq("version_id",deployRecord.getVersionId())
                    .eq("branch_id",branchId)
        );
        deployVersionBranch.setServiceUpdateTime(currentDate);
        deployVersionBranch.setIsServiceUpdated(unUpdatedCount==0);
        deployVersionBranch.setModifydate(currentDate);
        deployVersionBranchMapper.updateById(deployVersionBranch);

    }

    /**
     * 根据版本信息id查询各分中心更新状态
     * @param id deployVersion.id
     * @return
     */
    @Override
    public List<DeployVersionBranch> selectBranchListByVersionId(int id){
        return baseMapper.selectBranchListByVersionId(id);
    }
}
