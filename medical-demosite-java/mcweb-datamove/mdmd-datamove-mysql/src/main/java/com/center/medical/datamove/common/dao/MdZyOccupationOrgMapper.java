package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病体检机构(MdZyOccupationOrg)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyOccupationOrgMapper extends BaseMapper<MdZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param MdZyOccupationOrg查询参数
     * @return 分页数据
     */
    IPage<MdZyOccupationOrg> getPage(PageParam<MdZyOccupationOrg> page, @Param("param") MdZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    MdZyOccupationOrg getInfoById(@Param("id") String id);

}
