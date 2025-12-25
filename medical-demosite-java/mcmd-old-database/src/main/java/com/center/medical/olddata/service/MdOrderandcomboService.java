package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdOrderandcombo;

import java.util.List;

/**
 * 订单与套餐关联表(MdOrderandcombo)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:38:00
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

    /**
     * 批量添加或修改
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdOrderandcombo> mapAsList);

    /**
     * 通过订单id查询
     * @param id
     * @return
     */
    List<MdOrderandcombo> getByDdId(String id);
}

