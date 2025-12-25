package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.LimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * CW卡额度操作表(LimitOperation)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:38
 */
public interface LimitOperationService extends IService<LimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<LimitOperation> getPage(PageParam<LimitOperation> page, LimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LimitOperation getInfoById(String id);

}

