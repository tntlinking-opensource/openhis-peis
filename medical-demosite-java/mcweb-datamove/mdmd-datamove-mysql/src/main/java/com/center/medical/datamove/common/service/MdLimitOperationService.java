package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdLimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * CW卡额度操作表(MdLimitOperation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
public interface MdLimitOperationService extends IService<MdLimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdLimitOperation> getPage(PageParam<MdLimitOperation> page, MdLimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLimitOperation getInfoById(String id);

}

