package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病体检机构(ZyOccupationOrg)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:59
 */
public interface ZyOccupationOrgMapper extends BaseMapper<ZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param ZyOccupationOrg查询参数
     * @return 分页数据
     */
    IPage<ZyOccupationOrg> getPage(PageParam<ZyOccupationOrg> page, @Param("param") ZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    ZyOccupationOrg getInfoById(@Param("id") Object id);

}
