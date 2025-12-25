package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunction;
import com.center.medical.system.dao.SysFunctionMapper;
import com.center.medical.system.service.SysFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统业务功能(SysFunction)服务实现类
 *
 * @author makejava
 * @since 2024-03-19 11:12:09
 */
@Slf4j
@Service("sysFunctionService")
@RequiredArgsConstructor
public class SysFunctionServiceImpl extends ServiceImpl<SysFunctionMapper, SysFunction> implements SysFunctionService {

    private final SysFunctionMapper sysFunctionMapper;

    /**
     * 分页查询[系统业务功能]列表
     *
     * @param page  分页参数
     * @param param SysFunction查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysFunction> getPage(PageParam<SysFunction> page, SysFunction param) {
        return sysFunctionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键functionId
     * @return 详情信息
     */
    @Override
    public SysFunction getInfoById(String id) {
        return sysFunctionMapper.getInfoById(id);
    }

}

