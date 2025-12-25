package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardType;

/**
 * 卡类型(CardType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface CardTypeService extends IService<CardType> {

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param CardType查询参数
     * @return 分页数据
     */
    IPage<CardType> getList(PageParam<CardType> page, CardType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardType getInfoById(String id);

}

