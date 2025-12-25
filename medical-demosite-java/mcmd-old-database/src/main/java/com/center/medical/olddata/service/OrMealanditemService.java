package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrMealanditem;

import java.util.List;

/**
 * 普通套餐与收费项目关联表(Mealanditem)服务接口
 *
 * @author ay
 * @since 2023-07-25 22:25:29
 */
public interface OrMealanditemService extends IService<OrMealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrMealanditem> getPage(PageParam<OrMealanditem> page, OrMealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrMealanditem getInfoById(String id);

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    List<OrMealanditem> getByTcid(String tcid);
}

