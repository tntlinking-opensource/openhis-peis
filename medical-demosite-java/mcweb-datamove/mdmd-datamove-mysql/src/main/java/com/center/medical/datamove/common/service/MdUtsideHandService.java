package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUtsideHand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS外送手动结果表(MdUtsideHand)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:28
 */
public interface MdUtsideHandService extends IService<MdUtsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUtsideHand> getPage(PageParam<MdUtsideHand> page, MdUtsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUtsideHand getInfoById(String id);

}

