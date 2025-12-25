package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AdvanceVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF预检跟踪回访记录表(AdvanceVisit)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:20
 */
public interface AdvanceVisitService extends IService<AdvanceVisit> {

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AdvanceVisit> getPage(PageParam<AdvanceVisit> page, AdvanceVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AdvanceVisit getInfoById(String id);

}

