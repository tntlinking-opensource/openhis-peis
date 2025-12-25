package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysUserRoleMapper;
import com.center.medical.datamove.common.bean.model.SysUserRole;
import com.center.medical.datamove.common.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户和角色关联表(SysUserRole)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Slf4j
@Service("sysUserRoleService")
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 分页查询[用户和角色关联表]列表
     *
     * @param page  分页参数
     * @param param SysUserRole查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUserRole> getPage(PageParam<SysUserRole> page, SysUserRole param) {
        return sysUserRoleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public SysUserRole getInfoById(Long id) {
        return sysUserRoleMapper.getInfoById(id);
    }

}


