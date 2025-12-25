package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReviewNotificationFormatMapper;
import com.center.medical.datamove.oracle.bean.model.ReviewNotificationFormat;
import com.center.medical.datamove.oracle.service.ReviewNotificationFormatService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC复查通知格式(ReviewNotificationFormat)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:24
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


