package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysRoleMenuMapper;
import com.center.medical.datamove.common.bean.model.SysRoleMenu;
import com.center.medical.datamove.common.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色和菜单关联表(SysRoleMenu)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
@Slf4j
@Service("sysRoleMenuService")
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 分页查询[角色和菜单关联表]列表
     *
     * @param page  分页参数
     * @param param SysRoleMenu查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysRoleMenu> getPage(PageParam<SysRoleMenu> page, SysRoleMenu param) {
        return sysRoleMenuMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public SysRoleMenu getInfoById(Long id) {
        return sysRoleMenuMapper.getInfoById(id);
    }

}


