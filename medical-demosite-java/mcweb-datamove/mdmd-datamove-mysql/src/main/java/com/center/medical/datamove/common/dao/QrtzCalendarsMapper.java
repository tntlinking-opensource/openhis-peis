package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.QrtzCalendars;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 日历信息表(QrtzCalendars)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
public interface QrtzCalendarsMapper extends BaseMapper<QrtzCalendars> {

    /**
     * 分页查询[日历信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzCalendars查询参数
     * @return 分页数据
     */
    IPage<QrtzCalendars> getPage(PageParam<QrtzCalendars> page, @Param("param") QrtzCalendars param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzCalendars getInfoById(@Param("id") String id);

}
