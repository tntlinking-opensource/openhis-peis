package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZyFhcsGrClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC个人防护用品种类(MdZyFhcsGrClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
public interface MdZyFhcsGrClassService extends IService<MdZyFhcsGrClass> {

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZyFhcsGrClass> getPage(PageParam<MdZyFhcsGrClass> page, MdZyFhcsGrClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyFhcsGrClass getInfoById(String id);

}

