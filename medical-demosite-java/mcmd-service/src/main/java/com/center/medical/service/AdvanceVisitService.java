package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.AdvanceVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF预检跟踪回访记录表(AdvanceVisit)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
public interface AdvanceVisitService extends IService<AdvanceVisit> {

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisit查询参数
     * @return 分页数据
     */
    IPage<AdvanceVisit> getList(PageParam<AdvanceVisit> page, AdvanceVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    AdvanceVisit getInfoById(String id);

}

