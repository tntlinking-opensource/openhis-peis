package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrComboandfzx;

import java.util.List;

/**
 * 最小套餐与分中心关联表(Comboandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:07:21
 */
public interface OrComboandfzxService extends IService<OrComboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrComboandfzx> getPage(PageParam<OrComboandfzx> page, OrComboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrComboandfzx getInfoById(String id);

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<OrComboandfzx> getByTcid(String tcid);

    /**
     * 通过套餐id和分中心id查询
     * @param tcid
     * @param fzxId
     * @return
     */
    OrComboandfzx getByTcidAndFzx(String tcid, String fzxId);
}

