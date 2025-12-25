package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ReviewNotificationFormat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC复查通知格式(ReviewNotificationFormat)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:22
 */
public interface ReviewNotificationFormatMapper extends BaseMapper<ReviewNotificationFormat> {

    /**
     * 分页查询[JC复查通知格式]列表
     *
     * @param page  分页参数
     * @param param ReviewNotificationFormat查询参数
     * @return 分页数据
     */
    IPage<ReviewNotificationFormat> getPage(PageParam<ReviewNotificationFormat> page, @Param("param") ReviewNotificationFormat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReviewNotificationFormat getInfoById(@Param("id") String id);

}
