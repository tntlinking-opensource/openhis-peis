package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.SpecimenType;
import com.center.medical.data.bean.param.SpecimenTypeParam;

/**
 * 标本种类(SpecimenType)服务接口
 *
 * @author ay
 * @since 2023-11-07 15:49:17
 */
public interface SpecimenTypeService extends IService<SpecimenType> {

    /**
     * 分页查询[标本种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SpecimenType> getPage(PageParam<SpecimenType> page, SpecimenTypeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SpecimenType getInfoById(String id);

}

