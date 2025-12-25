package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdExpresscompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 快递公司表(MdExpresscompany)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdExpresscompanyService extends IService<MdExpresscompany> {

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdExpresscompany> getPage(PageParam<MdExpresscompany> page, MdExpresscompany param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdExpresscompany getInfoById(String id);

}

