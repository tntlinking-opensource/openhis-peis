package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.DayTargetParam;
import com.center.medical.sellcrm.bean.vo.DayTargetVo;

import java.util.List;

/**
 * XS销售目标(CrmSelltarget)服务接口
 *
 * @author ay
 * @since 2024-01-22 11:13:07
 */
public interface DayTargetService extends IService<Selltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DayTargetVo> getPage(PageParam<DayTargetVo> page, DayTargetParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Selltarget getInfoById(String id);

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    Double getSummaryData(DayTargetParam param);

    /**
     * 导出销售日目标
     * @param param
     * @return
     */
    List<DayTargetVo> exportData(DayTargetParam param);
}

