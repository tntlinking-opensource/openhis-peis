package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SectionTotal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * ZJ总检主表(SectionTotal)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:47
 */
public interface SectionTotalService extends IService<SectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionTotal> getPage(PageParam<SectionTotal> page, SectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionTotal getInfoById(String id);

}

