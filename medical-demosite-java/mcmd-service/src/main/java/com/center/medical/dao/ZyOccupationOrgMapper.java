package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.ZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病体检机构(ZyOccupationOrg)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:23
 */
public interface ZyOccupationOrgMapper extends BaseMapper<ZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param ZyOccupationOrg查询参数
     * @return 分页数据
     */
    IPage<ZyOccupationOrg> getList(PageParam<ZyOccupationOrg> page, @Param("param") ZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id orgCode主键
     */
    ZyOccupationOrg getInfoById(@Param("id") String id);

}
