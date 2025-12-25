package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReviewNotificationFormat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC复查通知格式(MdReviewNotificationFormat)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
public interface MdReviewNotificationFormatMapper extends BaseMapper<MdReviewNotificationFormat> {

    /**
     * 分页查询[JC复查通知格式]列表
     *
     * @param page  分页参数
     * @param param MdReviewNotificationFormat查询参数
     * @return 分页数据
     */
    IPage<MdReviewNotificationFormat> getPage(PageParam<MdReviewNotificationFormat> page, @Param("param") MdReviewNotificationFormat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReviewNotificationFormat getInfoById(@Param("id") String id);

}
