package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NotifierMapper;
import com.center.medical.datamove.oracle.bean.model.Notifier;
import com.center.medical.datamove.oracle.service.NotifierService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BG报告领取通知(Notifier)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:12
 */
@Slf4j
@Service("notifierService")
@RequiredArgsConstructor
public class NotifierServiceImpl extends ServiceImpl<NotifierMapper, Notifier> implements NotifierService {

    private final NotifierMapper notifierMapper;

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param Notifier查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Notifier> getPage(PageParam<Notifier> page, Notifier param) {
        return notifierMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Notifier getInfoById(String id) {
        return notifierMapper.getInfoById(id);
    }

}


