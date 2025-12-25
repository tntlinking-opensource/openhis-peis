package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Basexamltemtype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC检查项目类型表(Basexamltemtype)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:46
 */
public interface BasexamltemtypeService extends IService<Basexamltemtype> {

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Basexamltemtype> getPage(PageParam<Basexamltemtype> page, Basexamltemtype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Basexamltemtype getInfoById(String id);

}

