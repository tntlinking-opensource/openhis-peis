package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCreatecomboType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 最小套餐分类(MdCreatecomboType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
public interface MdCreatecomboTypeService extends IService<MdCreatecomboType> {

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCreatecomboType> getPage(PageParam<MdCreatecomboType> page, MdCreatecomboType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatecomboType getInfoById(String id);

}

