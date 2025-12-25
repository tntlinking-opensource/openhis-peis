package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZyFhcsGcClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC工程防护种类(MdZyFhcsGcClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
public interface MdZyFhcsGcClassService extends IService<MdZyFhcsGcClass> {

    /**
     * 分页查询[JC工程防护种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZyFhcsGcClass> getPage(PageParam<MdZyFhcsGcClass> page, MdZyFhcsGcClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyFhcsGcClass getInfoById(String id);

}

