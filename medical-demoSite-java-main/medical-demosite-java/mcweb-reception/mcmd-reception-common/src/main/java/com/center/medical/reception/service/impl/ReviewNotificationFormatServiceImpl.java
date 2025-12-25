package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.ReviewNotificationFormat;
import com.center.medical.reception.dao.ReviewNotificationFormatMapper;
import com.center.medical.reception.service.ReviewNotificationFormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC复查通知格式(ReviewNotificationFormat)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:27
 */
@Slf4j
@Service("reviewNotificationFormatService")
@RequiredArgsConstructor
public class ReviewNotificationFormatServiceImpl extends ServiceImpl<ReviewNotificationFormatMapper, ReviewNotificationFormat> implements ReviewNotificationFormatService {

    private final ReviewNotificationFormatMapper reviewNotificationFormatMapper;

    /**
     * 分页查询[JC复查通知格式]列表
     *
     * @param page  分页参数
     * @param param ReviewNotificationFormat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReviewNotificationFormat> getPage(PageParam<ReviewNotificationFormat> page, ReviewNotificationFormat param) {
        return reviewNotificationFormatMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReviewNotificationFormat getInfoById(String id) {
        return reviewNotificationFormatMapper.getInfoById(id);
    }

}

