package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionBranch1;
import com.center.medical.system.dao.SysVersionBranch1Mapper;
import com.center.medical.system.service.SysVersionBranch1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自动部署-版本更新分中心关联表(SysVersionBranch1)服务实现类
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
@Slf4j
@Service("sysVersion1BranchService")
@RequiredArgsConstructor
public class SysVersionBranch1ServiceImpl extends ServiceImpl<SysVersionBranch1Mapper, SysVersionBranch1> implements SysVersionBranch1Service {

    private final SysVersionBranch1Mapper sysVersionBranch1Mapper;

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param SysVersionBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionBranch1> getPage(PageParam<SysVersionBranch1> page, SysVersionBranch1 param) {
        return sysVersionBranch1Mapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersionBranch1 getInfoById(Integer id) {
        return sysVersionBranch1Mapper.getInfoById(id);
    }

}

