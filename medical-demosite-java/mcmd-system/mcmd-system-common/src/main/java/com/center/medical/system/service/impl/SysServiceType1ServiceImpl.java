package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceType1;
import com.center.medical.system.dao.SysServiceType1Mapper;
import com.center.medical.system.service.SysServiceType1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统服务(记录系统服务种类)(SysServiceType1)服务实现类
 *
 * @author makejava
 * @since 2024-01-23 11:02:56
 */
@Slf4j
@Service("sysServiceType1Service")
@RequiredArgsConstructor
public class SysServiceType1ServiceImpl extends ServiceImpl<SysServiceType1Mapper, SysServiceType1> implements SysServiceType1Service {

    private final SysServiceType1Mapper sysServiceType1Mapper;

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysServiceType1> getPage(PageParam<SysServiceType1> page, SysServiceType1 param) {
        return sysServiceType1Mapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    @Override
    public SysServiceType1 getInfoById(Integer id) {
        return sysServiceType1Mapper.getInfoById(id);
    }

}

