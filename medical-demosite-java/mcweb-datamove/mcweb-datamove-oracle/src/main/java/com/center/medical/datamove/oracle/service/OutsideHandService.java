package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.OutsideHand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS外送手动结果表(OutsideHand)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:33
 */
public interface OutsideHandService extends IService<OutsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OutsideHand> getPage(PageParam<OutsideHand> page, OutsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideHand getInfoById(String id);

}

