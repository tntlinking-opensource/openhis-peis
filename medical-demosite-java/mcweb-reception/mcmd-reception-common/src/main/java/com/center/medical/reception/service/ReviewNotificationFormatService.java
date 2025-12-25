package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.ReviewNotificationFormat;

/**
 * JC复查通知格式(ReviewNotificationFormat)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:27
 */
public interface ReviewNotificationFormatService extends IService<ReviewNotificationFormat> {

    /**
     * 分页查询[JC复查通知格式]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReviewNotificationFormat> getPage(PageParam<ReviewNotificationFormat> page, ReviewNotificationFormat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReviewNotificationFormat getInfoById(String id);

}

