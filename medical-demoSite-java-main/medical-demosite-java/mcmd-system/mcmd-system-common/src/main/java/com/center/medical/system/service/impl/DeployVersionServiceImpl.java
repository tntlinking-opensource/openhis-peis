package com.center.medical.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.center.medical.system.bean.param.DeployVersionListParam;
import com.center.medical.system.bean.param.DeployVersionSaveParam;
import com.center.medical.system.bean.param.DeployVersionUpdateParam;
import com.center.medical.system.dao.DeployVersionMapper;
import com.center.medical.system.bean.model.DeployVersion;
import com.center.medical.system.service.DeployVersionBranchService;
import com.center.medical.system.service.DeployVersionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自动部署-更新版本信息(DeployVersion)服务实现类
 *
 * @author makejava
 * @since 2023-11-15 08:42:33
 */
@Slf4j
@Service("deployVersionService")
@RequiredArgsConstructor
public class DeployVersionServiceImpl extends ServiceImpl<DeployVersionMapper, DeployVersion> implements DeployVersionService {

    private final DeployVersionMapper deployVersionMapper;
    private final DeployVersionBranchService deployVersionBranchService;

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param DeployVersion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DeployVersion> getPage(PageParam<DeployVersion> page, DeployVersionListParam param) {
        return deployVersionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DeployVersion getInfoById(Integer id) {
        return deployVersionMapper.getInfoById(id);
    }

    /**
     * 新增
     */
    @Override
    @Transactional
    public void saveVersion(DeployVersionSaveParam param){
        Date currentDate=new Date();
        DeployVersion deployVersion= BeanUtil.toBean(param,DeployVersion.class);
        deployVersion.setCreatedate(currentDate);
        deployVersion.setModifydate(currentDate);
        deployVersion.setIsDelete(false);
        deployVersion.setCreator(SecurityUtils.getUsername());
        baseMapper.insert(deployVersion);
        List<String> branchIds=param.getBranchIds();
        if(branchIds.size()>0){
            List<DeployVersionBranch> deployVersionBranches=new ArrayList<>();
            Integer versionId = deployVersion.getId();
            for(String branchId:branchIds){
                deployVersionBranches.add(createNewDeployVersionBranch(currentDate,branchId,versionId));
            }
            deployVersionBranchService.saveBatch(deployVersionBranches);
        }

    }

    /**
     * 更新
     * @param param
     */
    @Override
    @Transactional
    public  void updateVersion(DeployVersionUpdateParam param){
        Date currentDate=new Date();
        Integer versionId=param.getId();
        DeployVersion deployVersion=baseMapper.selectById(versionId);
        BeanUtil.copyProperties(param,deployVersion,"createdate","modifydate","isDelete","modifier","creator");
        deployVersion.setModifydate(currentDate);
        deployVersion.setModifier(SecurityUtils.getUsername());
        baseMapper.updateById(deployVersion);

        List<DeployVersionBranch> updateDeployVersionBranches=new ArrayList<>();
        List<DeployVersionBranch> insertDeployVersionBranches=new ArrayList<>();
        List<String> branchIds=param.getBranchIds();
        List<DeployVersionBranch> delBranchList=deployVersionBranchService.list(
                new QueryWrapper<DeployVersionBranch>()
                    .eq("version_id",versionId)
                    .eq("is_delete",0)
                    .notIn("branch_id",branchIds)
        );
        for(DeployVersionBranch deployVersionBranch:delBranchList){
            deployVersionBranch.setIsDelete(true);
            deployVersionBranch.setModifydate(currentDate);
            updateDeployVersionBranches.add(deployVersionBranch);
        }
        for(String branchId:branchIds){
            DeployVersionBranch deployVersionBranch=deployVersionBranchService.getOne(
                    new QueryWrapper<DeployVersionBranch>()
                            .eq("version_id",versionId)
                            .eq("branch_id",branchId)
            );
            if(deployVersionBranch==null){
                insertDeployVersionBranches.add(createNewDeployVersionBranch(currentDate,branchId,versionId));
            }else{
                if(deployVersionBranch.getIsDelete()){
                    deployVersionBranch.setIsDelete(false);
                    deployVersionBranch.setModifydate(currentDate);
                    updateDeployVersionBranches.add(deployVersionBranch);
                }
            }
        }
        if(updateDeployVersionBranches.size()>0){
            deployVersionBranchService.updateBatchById(updateDeployVersionBranches);
        }
        if(insertDeployVersionBranches.size()>0){
            deployVersionBranchService.saveBatch(insertDeployVersionBranches);
        }
    }

    DeployVersionBranch createNewDeployVersionBranch(Date currentDate,String branchId,int versionId){
        DeployVersionBranch deployVersionBranch=new DeployVersionBranch();
        deployVersionBranch.setCreatedate(currentDate);
        deployVersionBranch.setModifydate(currentDate);
        deployVersionBranch.setBranchId(branchId);
        deployVersionBranch.setVersionId(versionId);
        deployVersionBranch.setIsDelete(false);
        deployVersionBranch.setIsSqlUpdated(0);
        deployVersionBranch.setIsServiceUpdated(false);
        return deployVersionBranch;
    }

    /**
     * 删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteVersion(List<Integer> ids){
        List<DeployVersion> deployVersions=baseMapper.selectBatchIds(ids);
        for(DeployVersion deployVersion:deployVersions){
            deployVersion.setIsDelete(true);
        }
        this.updateBatchById(deployVersions);
    }
}
