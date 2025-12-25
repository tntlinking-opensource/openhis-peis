package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardPayway;

import java.util.List;

/**
 * 卡办理收款方式表(CardPayway)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface CardPaywayService extends IService<CardPayway> {

    /**
     * 分页查询[卡办理收款方式表]列表
     *
     * @param page  分页参数
     * @param param CardPayway查询参数
     * @return 分页数据
     */
    IPage<CardPayway> getList(PageParam<CardPayway> page, CardPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardPayway getInfoById(String id);

    /**
     * 根据卡办理id获取付款方式和收费员
     * @param processId
     * @return
     */
    List<CardPayway> selectFeechargerList(String processId);
}

