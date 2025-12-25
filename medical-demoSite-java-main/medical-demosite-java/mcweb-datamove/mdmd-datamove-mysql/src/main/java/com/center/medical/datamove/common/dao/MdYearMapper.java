package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdYear;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 年份表(MdYear)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface MdYearMapper extends BaseMapper<MdYear> {

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param MdYear查询参数
     * @return 分页数据
     */
    IPage<MdYear> getPage(PageParam<MdYear> page, @Param("param") MdYear param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdYear getInfoById(@Param("id") String id);

}
