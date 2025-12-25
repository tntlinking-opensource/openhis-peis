package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdOrganization;
import org.apache.ibatis.annotations.Param;

/**
 * kingdeeorganization(KdOrganization)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdOrganizationMapper extends BaseMapper<KdOrganization> {

    /**
     * 分页查询[kingdeeorganization]列表
     *
     * @param page  分页参数
     * @param param KdOrganization查询参数
     * @return 分页数据
     */
    IPage<KdOrganization> getPage(PageParam<KdOrganization> page, @Param("param") KdOrganization param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgId
     * @return 详情信息
     */
    KdOrganization getInfoById(@Param("id") String id);

}
