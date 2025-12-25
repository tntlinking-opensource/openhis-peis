package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZySummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病检查结论(MdZySummary)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZySummaryService extends IService<MdZySummary> {

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZySummary> getPage(PageParam<MdZySummary> page, MdZySummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZySummary getInfoById(String id);

}

