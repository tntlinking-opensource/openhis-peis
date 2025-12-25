package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDiseast;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业病名称(OccupationDiseast)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:19
 */
public interface OccupationDiseastMapper extends BaseMapper<OccupationDiseast> {

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseast查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseast> getList(PageParam<OccupationDiseast> page, @Param("param") OccupationDiseast param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDiseast getInfoById(@Param("id") String id);

}
