package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.NotifierMapper;
import com.center.medical.bean.model.Notifier;
import com.center.medical.service.NotifierService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BG报告领取通知(Notifier)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:20
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
    public IPage<Notifier> getList(PageParam<Notifier> page, Notifier param) {
        return notifierMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Notifier getInfoById(String id) {
        return notifierMapper.getInfoById(id);
    }

    ;

}

