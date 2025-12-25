package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDiseast;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病名称(OccupationDiseast)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:16
 */
public interface OccupationDiseastMapper extends BaseMapper<OccupationDiseast> {

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseast查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseast> getPage(PageParam<OccupationDiseast> page, @Param("param") OccupationDiseast param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationDiseast getInfoById(@Param("id") String id);

}
