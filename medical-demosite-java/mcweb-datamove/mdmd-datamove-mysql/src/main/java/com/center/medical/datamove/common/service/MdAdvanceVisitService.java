package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdAdvanceVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF预检跟踪回访记录表(MdAdvanceVisit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
public interface MdAdvanceVisitService extends IService<MdAdvanceVisit> {

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdAdvanceVisit> getPage(PageParam<MdAdvanceVisit> page, MdAdvanceVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdAdvanceVisit getInfoById(String id);

}

