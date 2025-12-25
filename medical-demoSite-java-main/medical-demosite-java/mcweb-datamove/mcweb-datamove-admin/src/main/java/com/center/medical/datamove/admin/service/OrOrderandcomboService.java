package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Orderandcombo;

import java.util.List;

/**
 * 订单与套餐关联表(Orderandcombo)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:38:47
 */
public interface OrOrderandcomboService extends IService<Orderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, Orderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandcombo getInfoById(String id);

    /**
     * 通过订单id查询
     *
     * @param id
     * @return
     */
    List<Orderandcombo> getByDdId(String id);
}

