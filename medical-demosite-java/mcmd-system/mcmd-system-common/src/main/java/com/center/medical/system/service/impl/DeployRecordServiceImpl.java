package com.center.medical.system.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.system.bean.model.DeployVersion;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.center.medical.system.bean.model.MdKsIp;
import com.center.medical.system.bean.param.DeployRecordManualParam;
import com.center.medical.system.bean.vo.DeployRecordListVo;
import com.center.medical.system.dao.DeployMdKsIpMapper;
import com.center.medical.system.dao.DeployRecordMapper;
import com.center.medical.system.bean.model.DeployRecord;
import com.center.medical.system.dao.DeployVersionBranchMapper;
import com.center.medical.system.dao.DeployVersionMapper;
import com.center.medical.system.service.DeployRecordService;
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
 * 自动部署-更新记录(DeployRecord)服务实现类
 *
 * @author makejava
 * @since 2023-11-15 08:28:43
 */
@Slf4j
@Service("deployRecordService")
@RequiredArgsConstructor
public class DeployRecordServiceImpl extends ServiceImpl<DeployRecordMapper, DeployRecord> implements DeployRecordService {

    private final DeployRecordMapper deployRecordMapper;
    private final DeployVersionBranchMapper deployVersionBranchMapper;
    private final DeployVersionMapper deployVersionMapper;
    private final DeployMdKsIpMapper deployMdKsIpMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[自动部署-更新记录]列表
     *
     * @param page  分页参数
     * @param param DeployRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DeployRecord> getPage(PageParam<DeployRecord> page, DeployRecord param) {
        return deployRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DeployRecord getInfoById(String id) {
        return deployRecordMapper.getInfoById(id);
    }

    /**
     * 根据版本信息分中心关联表id查询此中心各服务更新状态
     * @param id
     * @return
     */
    @Override
    public List<DeployRecordListVo> selectRecordListByBranchId(int id){
        DeployVersionBranch deployVersionBranch=deployVersionBranchMapper.selectById(id);
        DeployVersion deployVersion=deployVersionMapper.selectById(deployVersionBranch.getVersionId());
        return baseMapper.selectRecordListByBranchId(deployVersionBranch.getBranchId(),deployVersionBranch.getVersionId(),deployVersion.getUpdateType());
    }

     /**
     * 人工处理
     * @param param
     */
    @Override
    @Transactional
    public void manual(DeployRecordManualParam param){
        List<String> ksIpIds =param.getKsIpIds();
        Integer versionId=param.getVersionId();
        List<DeployRecord> updateList=new ArrayList<>();
        List<DeployRecord> insertList=new ArrayList<>();
        Date currentDate=new Date();
        String username= SecurityUtils.getUsername();
        for(String ksIpId:ksIpIds){
            MdKsIp mdKsIp=deployMdKsIpMapper.getInfoById(ksIpId);
            String branchId=mdKsIp.getBranchId();
            DeployRecord deployRecord=baseMapper.selectOne(
                    new QueryWrapper<DeployRecord>()
                        .eq("version_id",versionId)
                        .eq("ks_ip_id",ksIpId)
            );
            if(deployRecord==null){
                deployRecord=new DeployRecord();
                deployRecord.setVersionId(versionId);
                deployRecord.setBranchId(branchId);
                deployRecord.setKsIpId(ksIpId);
                deployRecord.setId(String.valueOf(snowflake.nextId()));
                deployRecord.setIsManual(true);
                deployRecord.setManualUpdateTime(currentDate);
                deployRecord.setIsManualSuccess(true);
                deployRecord.setCreator(username);
                deployRecord.setModifier(username);
                deployRecord.setCreatedate(currentDate);
                insertList.add(deployRecord);
            }else{
                deployRecord.setIsManual(true);
                deployRecord.setManualUpdateTime(currentDate);
                deployRecord.setIsManualSuccess(true);
                deployRecord.setModifier(username);
                deployRecord.setModifydate(currentDate);
                updateList.add(deployRecord);
            }

        }
        if(insertList.size()>0){
            saveBatch(insertList);
        }
        if(updateList.size()>0){
            updateBatchById(updateList);
        }
    }
}
