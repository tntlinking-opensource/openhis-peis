package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDrugstoreClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 药品分类(MdDrugstoreClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugstoreClassService extends IService<MdDrugstoreClass> {

    /**
     * 分页查询[药品分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDrugstoreClass> getPage(PageParam<MdDrugstoreClass> page, MdDrugstoreClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrugstoreClass getInfoById(String id);

}

