package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsSectionResultMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-检查结果主表(PacsSectionResultMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:43
 */
public interface PacsSectionResultMainService extends IService<PacsSectionResultMain> {

    /**
     * 分页查询[PACS-检查结果主表]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultMain查询参数
     * @return 分页数据
     */
    IPage<PacsSectionResultMain> getList(PageParam<PacsSectionResultMain> page, PacsSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsSectionResultMain getInfoById(String id);

}

