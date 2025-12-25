package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOccupationDrug;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病/禁忌症(MdOccupationDrug)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdOccupationDrugService extends IService<MdOccupationDrug> {

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOccupationDrug> getPage(PageParam<MdOccupationDrug> page, MdOccupationDrug param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationDrug getInfoById(String id);

}

