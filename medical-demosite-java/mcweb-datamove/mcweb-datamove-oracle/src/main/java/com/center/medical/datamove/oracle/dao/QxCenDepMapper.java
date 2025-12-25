package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxCenDep;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxCenDep)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:43
 */
public interface QxCenDepMapper extends BaseMapper<QxCenDep> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxCenDep查询参数
     * @return 分页数据
     */
    IPage<QxCenDep> getPage(PageParam<QxCenDep> page, @Param("param") QxCenDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxCenDep getInfoById(@Param("id") String id);

}
