package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy1;
import com.center.medical.system.dao.SysVersionDeploy1Mapper;
import com.center.medical.system.service.SysVersionDeploy1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 版本控制-更新记录(SysVersionDeploy1)服务实现类
 *
 * @author makejava
 * @since 2024-01-23 10:36:18
 */
@Slf4j
@Service("sysVersion1DeployService")
@RequiredArgsConstructor
public class SysVersionDeploy1ServiceImpl extends ServiceImpl<SysVersionDeploy1Mapper, SysVersionDeploy1> implements SysVersionDeploy1Service {

    private final SysVersionDeploy1Mapper sysVersionDeploy1Mapper;

    /**
     * 分页查询[版本控制-更新记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionDeploy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionDeploy1> getPage(PageParam<SysVersionDeploy1> page, SysVersionDeploy1 param) {
        return sysVersionDeploy1Mapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersionDeploy1 getInfoById(String id) {
        return sysVersionDeploy1Mapper.getInfoById(id);
    }

}

