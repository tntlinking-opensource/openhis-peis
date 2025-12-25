package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.LimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * CW卡额度操作表(LimitOperation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface LimitOperationService extends IService<LimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param LimitOperation查询参数
     * @return 分页数据
     */
    IPage<LimitOperation> getList(PageParam<LimitOperation> page, LimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    LimitOperation getInfoById(String id);

}

