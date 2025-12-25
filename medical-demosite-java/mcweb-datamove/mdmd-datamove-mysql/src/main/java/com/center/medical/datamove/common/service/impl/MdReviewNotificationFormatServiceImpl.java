package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReviewNotificationFormatMapper;
import com.center.medical.datamove.common.bean.model.MdReviewNotificationFormat;
import com.center.medical.datamove.common.service.MdReviewNotificationFormatService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC复查通知格式(MdReviewNotificationFormat)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Slf4j
@Service("mdReviewNotificationFormatService")
@RequiredArgsConstructor
public class MdReviewNotificationFormatServiceImpl extends ServiceImpl<MdReviewNotificationFormatMapper, MdReviewNotificationFormat> implements MdReviewNotificationFormatService {

    private final MdReviewNotificationFormatMapper mdReviewNotificationFormatMapper;

    /**
     * 分页查询[JC复查通知格式]列表
     *
     * @param page  分页参数
     * @param param MdReviewNotificationFormat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReviewNotificationFormat> getPage(PageParam<MdReviewNotificationFormat> page, MdReviewNotificationFormat param) {
        return mdReviewNotificationFormatMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReviewNotificationFormat getInfoById(String id) {
        return mdReviewNotificationFormatMapper.getInfoById(id);
    }

}


