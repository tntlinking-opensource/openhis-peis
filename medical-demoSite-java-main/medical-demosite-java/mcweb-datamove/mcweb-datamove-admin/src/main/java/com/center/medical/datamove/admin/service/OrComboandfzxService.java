package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Comboandfzx;

import java.util.List;

/**
 * 最小套餐与分中心关联表(Comboandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:07:21
 */
public interface OrComboandfzxService extends IService<Comboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Comboandfzx> getPage(PageParam<Comboandfzx> page, Comboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Comboandfzx getInfoById(String id);

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<Comboandfzx> getByTcid(String tcid);
}

