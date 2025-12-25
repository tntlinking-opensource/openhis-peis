package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxPersonnelview;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 综合分析-人员一览表(FxPersonnelview)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
public interface FxPersonnelviewService extends IService<FxPersonnelview> {

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxPersonnelview查询参数
     * @return 分页数据
     */
    IPage<FxPersonnelview> getList(PageParam<FxPersonnelview> page, FxPersonnelview param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxPersonnelview getInfoById(String id);

}

