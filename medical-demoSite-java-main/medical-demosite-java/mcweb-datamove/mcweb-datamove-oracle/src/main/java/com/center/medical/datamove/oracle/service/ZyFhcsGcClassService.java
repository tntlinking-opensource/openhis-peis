package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ZyFhcsGcClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC工程防护种类(ZyFhcsGcClass)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:56
 */
public interface ZyFhcsGcClassService extends IService<ZyFhcsGcClass> {

    /**
     * 分页查询[JC工程防护种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGcClass> getPage(PageParam<ZyFhcsGcClass> page, ZyFhcsGcClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhcsGcClass getInfoById(String id);

}

