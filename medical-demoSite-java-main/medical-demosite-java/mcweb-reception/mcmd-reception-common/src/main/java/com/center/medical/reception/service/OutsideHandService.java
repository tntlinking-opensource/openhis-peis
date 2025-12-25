package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideHand;

/**
 * KS外送手动结果表(OutsideHand)表服务接口
 *
 * @author ay
 * @since 2022-12-07 15:11:14
 */
public interface OutsideHandService extends IService<OutsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OutsideHand> getList(PageParam<OutsideHand> page, OutsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideHand getInfoById(String id);

}

