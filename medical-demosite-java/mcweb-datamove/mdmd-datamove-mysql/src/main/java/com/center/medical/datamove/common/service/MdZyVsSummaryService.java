package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZyVsSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病处理意见(MdZyVsSummary)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyVsSummaryService extends IService<MdZyVsSummary> {

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZyVsSummary> getPage(PageParam<MdZyVsSummary> page, MdZyVsSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyVsSummary getInfoById(String id);

}

