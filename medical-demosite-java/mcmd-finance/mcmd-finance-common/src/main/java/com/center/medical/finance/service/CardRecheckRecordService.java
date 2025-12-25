package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardRecheckRecord;

/**
 * 十周年卡复查金额记录表(CardRecheckRecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:36
 */
public interface CardRecheckRecordService extends IService<CardRecheckRecord> {

    /**
     * 分页查询[十周年卡复查金额记录表]列表
     *
     * @param page  分页参数
     * @param param CardRecheckRecord查询参数
     * @return 分页数据
     */
    IPage<CardRecheckRecord> getList(PageParam<CardRecheckRecord> page, CardRecheckRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardRecheckRecord getInfoById(String id);

}

