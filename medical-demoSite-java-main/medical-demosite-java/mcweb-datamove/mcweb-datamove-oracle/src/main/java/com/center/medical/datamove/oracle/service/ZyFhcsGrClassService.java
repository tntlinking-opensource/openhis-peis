package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ZyFhcsGrClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC个人防护用品种类(ZyFhcsGrClass)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:57
 */
public interface ZyFhcsGrClassService extends IService<ZyFhcsGrClass> {

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGrClass> getPage(PageParam<ZyFhcsGrClass> page, ZyFhcsGrClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhcsGrClass getInfoById(String id);

}

