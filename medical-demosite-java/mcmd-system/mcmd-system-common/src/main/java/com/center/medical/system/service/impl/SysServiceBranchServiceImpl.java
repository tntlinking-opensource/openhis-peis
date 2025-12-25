package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceBranch;
import com.center.medical.system.dao.SysServiceBranchMapper;
import com.center.medical.system.service.SysServiceBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统服务-分中心关联记录(SysServiceBranch)服务实现类
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
@Slf4j
@Service("sysServiceBranchService")
@RequiredArgsConstructor
public class SysServiceBranchServiceImpl extends ServiceImpl<SysServiceBranchMapper, SysServiceBranch> implements SysServiceBranchService {

    private final SysServiceBranchMapper sysServiceBranchMapper;

    /**
     * 分页查询[系统服务-分中心关联记录]列表
     *
     * @param page  分页参数
     * @param param SysServiceBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysServiceBranch> getPage(PageParam<SysServiceBranch> page, SysServiceBranch param) {
        return sysServiceBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysServiceBranch getInfoById(Integer id) {
        return sysServiceBranchMapper.getInfoById(id);
    }

}

