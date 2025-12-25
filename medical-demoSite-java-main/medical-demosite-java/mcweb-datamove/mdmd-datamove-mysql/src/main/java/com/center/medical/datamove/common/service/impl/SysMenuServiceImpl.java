package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysMenuMapper;
import com.center.medical.datamove.common.bean.model.SysMenu;
import com.center.medical.datamove.common.service.SysMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 菜单权限表(SysMenu)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@Service("sysMenuService")
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 分页查询[菜单权限表]列表
     *
     * @param page  分页参数
     * @param param SysMenu查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysMenu> getPage(PageParam<SysMenu> page, SysMenu param) {
        return sysMenuMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键menuId
     * @return 详情信息
     */
    @Override
    public SysMenu getInfoById(Long id) {
        return sysMenuMapper.getInfoById(id);
    }

}


