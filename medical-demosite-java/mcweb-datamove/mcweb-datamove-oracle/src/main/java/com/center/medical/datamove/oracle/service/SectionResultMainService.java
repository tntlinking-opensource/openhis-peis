package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SectionResultMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS科室检查结果主表(SectionResultMain)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:42
 */
public interface SectionResultMainService extends IService<SectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionResultMain> getPage(PageParam<SectionResultMain> page, SectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultMain getInfoById(String id);

}

