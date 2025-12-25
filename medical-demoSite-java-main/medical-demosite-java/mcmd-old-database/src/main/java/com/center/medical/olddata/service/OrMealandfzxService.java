package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrMealandfzx;

import java.util.List;

/**
 * 普通套餐与分中心关联表(Mealandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:25:31
 */
public interface OrMealandfzxService extends IService<OrMealandfzx> {

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrMealandfzx> getPage(PageParam<OrMealandfzx> page, OrMealandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrMealandfzx getInfoById(String id);

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<OrMealandfzx> getByTcid(String tcid);

    /**
     * 通过套餐id和分中心查询
     * @param tcid
     * @param fzxId
     * @return
     */
    OrMealandfzx getByTcidAndFzx(String tcid, String fzxId);
}

