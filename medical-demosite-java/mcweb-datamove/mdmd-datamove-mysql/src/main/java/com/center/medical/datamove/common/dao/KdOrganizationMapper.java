package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdOrganization;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金蝶中的组织信息（kingdeeorganization）(KdOrganization)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdOrganizationMapper extends BaseMapper<KdOrganization> {

    /**
     * 分页查询[金蝶中的组织信息（kingdeeorganization）]列表
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
