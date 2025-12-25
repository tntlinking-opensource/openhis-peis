package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOrderandcombo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单与套餐关联表(MdOrderandcombo)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
public interface MdOrderandcomboService extends IService<MdOrderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOrderandcombo> getPage(PageParam<MdOrderandcombo> page, MdOrderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrderandcombo getInfoById(String id);

}

