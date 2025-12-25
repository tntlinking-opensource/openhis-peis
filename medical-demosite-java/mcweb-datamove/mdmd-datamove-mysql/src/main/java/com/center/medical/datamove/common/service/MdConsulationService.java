package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdConsulation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室/总检咨询(MdConsulation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdConsulationService extends IService<MdConsulation> {

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdConsulation> getPage(PageParam<MdConsulation> page, MdConsulation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdConsulation getInfoById(String id);

}

