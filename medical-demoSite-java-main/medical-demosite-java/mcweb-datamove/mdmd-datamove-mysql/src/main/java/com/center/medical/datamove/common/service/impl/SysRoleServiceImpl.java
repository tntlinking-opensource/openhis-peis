package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysRoleMapper;
import com.center.medical.datamove.common.bean.model.SysRole;
import com.center.medical.datamove.common.service.SysRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色信息表(SysRole)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
@Slf4j
@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    /**
     * 分页查询[角色信息表]列表
     *
     * @param page  分页参数
     * @param param SysRole查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysRole> getPage(PageParam<SysRole> page, SysRole param) {
        return sysRoleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public SysRole getInfoById(Long id) {
        return sysRoleMapper.getInfoById(id);
    }

}


