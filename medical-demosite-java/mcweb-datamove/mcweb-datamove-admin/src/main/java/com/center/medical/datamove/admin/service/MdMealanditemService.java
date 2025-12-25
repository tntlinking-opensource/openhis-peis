package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdMealanditem;

import java.util.List;

/**
 * 普通套餐与收费项目关联表(MdMealanditem)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:26:23
 */
public interface MdMealanditemService extends IService<MdMealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdMealanditem> getPage(PageParam<MdMealanditem> page, MdMealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMealanditem getInfoById(String id);

    /**
     * 批量添加或修改
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdMealanditem> mapAsList);
}

