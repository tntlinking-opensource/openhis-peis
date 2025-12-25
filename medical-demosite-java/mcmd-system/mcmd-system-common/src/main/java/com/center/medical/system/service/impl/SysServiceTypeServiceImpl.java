package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceType;
import com.center.medical.system.dao.SysServiceTypeMapper;
import com.center.medical.system.service.SysServiceTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统服务(记录系统服务种类)(SysServiceType)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
@Slf4j
@Service("sysServiceTypeService")
@RequiredArgsConstructor
public class SysServiceTypeServiceImpl extends ServiceImpl<SysServiceTypeMapper, SysServiceType> implements SysServiceTypeService {

    private final SysServiceTypeMapper sysServiceTypeMapper;

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysServiceType> getPage(PageParam<SysServiceType> page, SysServiceType param) {
        return sysServiceTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    @Override
    public SysServiceType getInfoById(Integer id) {
        return sysServiceTypeMapper.getInfoById(id);
    }

}

