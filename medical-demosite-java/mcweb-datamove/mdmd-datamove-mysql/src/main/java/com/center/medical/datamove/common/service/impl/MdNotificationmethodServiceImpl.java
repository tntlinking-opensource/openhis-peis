package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdNotificationmethodMapper;
import com.center.medical.datamove.common.bean.model.MdNotificationmethod;
import com.center.medical.datamove.common.service.MdNotificationmethodService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知方式（领取方式）表(MdNotificationmethod)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Slf4j
@Service("mdNotificationmethodService")
@RequiredArgsConstructor
public class MdNotificationmethodServiceImpl extends ServiceImpl<MdNotificationmethodMapper, MdNotificationmethod> implements MdNotificationmethodService {

    private final MdNotificationmethodMapper mdNotificationmethodMapper;

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param MdNotificationmethod查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdNotificationmethod> getPage(PageParam<MdNotificationmethod> page, MdNotificationmethod param) {
        return mdNotificationmethodMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdNotificationmethod getInfoById(String id) {
        return mdNotificationmethodMapper.getInfoById(id);
    }

}


