package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysMenu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单权限表(SysMenu)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 分页查询[菜单权限表]列表
     *
     * @param page  分页参数
     * @param param SysMenu查询参数
     * @return 分页数据
     */
    IPage<SysMenu> getPage(PageParam<SysMenu> page, @Param("param") SysMenu param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键menuId
     * @return 详情信息
     */
    SysMenu getInfoById(@Param("id") Long id);

}
