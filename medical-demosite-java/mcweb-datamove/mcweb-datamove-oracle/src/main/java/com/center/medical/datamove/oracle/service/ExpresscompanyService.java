package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Expresscompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 快递公司表(Expresscompany)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:40
 */
public interface ExpresscompanyService extends IService<Expresscompany> {

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Expresscompany> getPage(PageParam<Expresscompany> page, Expresscompany param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Expresscompany getInfoById(String id);

}

