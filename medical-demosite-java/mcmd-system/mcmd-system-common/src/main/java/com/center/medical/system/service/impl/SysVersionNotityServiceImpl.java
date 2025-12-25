package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionNotity;
import com.center.medical.system.dao.SysVersionNotityMapper;
import com.center.medical.system.service.SysVersionNotityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 版本控制-新版本通知记录(SysVersionNotity)服务实现类
 *
 * @author makejava
 * @since 2024-04-26 10:52:08
 */
@Slf4j
@Service("sysVersionNotityService")
@RequiredArgsConstructor
public class SysVersionNotityServiceImpl extends ServiceImpl<SysVersionNotityMapper, SysVersionNotity> implements SysVersionNotityService {

    private final SysVersionNotityMapper sysVersionNotityMapper;

    /**
     * 分页查询[版本控制-新版本通知记录]列表
     *
     * @param page  分页参数
     * @param param SysVersionNotity查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysVersionNotity> getPage(PageParam<SysVersionNotity> page, SysVersionNotity param) {
        return sysVersionNotityMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysVersionNotity getInfoById(Integer id) {
        return sysVersionNotityMapper.getInfoById(id);
    }

}

