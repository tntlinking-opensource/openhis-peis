package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion1;
import com.center.medical.system.dao.SysVersion1Mapper;
import com.center.medical.system.service.SysVersion1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自动部署-更新版本信息(SysVersion1)服务实现类
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
@Slf4j
@Service("sysVersion1Service")
@RequiredArgsConstructor
public class SysVersion1ServiceImpl extends ServiceImpl<SysVersion1Mapper, SysVersion1> implements SysVersion1Service {

    private final SysVersion1Mapper sysVersion1Mapper;

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersion1> getPage(PageParam<SysVersion1> page, SysVersion1 param) {
        return sysVersion1Mapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersion1 getInfoById(Integer id) {
        return sysVersion1Mapper.getInfoById(id);
    }

}

