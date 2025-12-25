package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(MdFxSummary)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdFxSummaryService extends IService<MdFxSummary> {

    /**
     * 分页查询[本次职业健康检查危害因素人员检查情况汇总一览表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxSummary> getPage(PageParam<MdFxSummary> page, MdFxSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxSummary getInfoById(String id);

}

