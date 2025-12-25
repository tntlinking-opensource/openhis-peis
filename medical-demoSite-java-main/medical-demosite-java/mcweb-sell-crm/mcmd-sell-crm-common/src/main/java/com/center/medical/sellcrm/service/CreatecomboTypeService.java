package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CreatecomboType;
import com.center.medical.sellcrm.bean.vo.TypeListVo;

/**
 * 最小套餐分类(CreatecomboType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
 */
public interface CreatecomboTypeService extends IService<CreatecomboType> {

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CreatecomboType> getList(PageParam<CreatecomboType> page, CreatecomboType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatecomboType getInfoById(String id);

    /**
     * 获取最小套餐分类
     *
     * @param page
     * @return
     */
    IPage<TypeListVo> getTypeList(PageParam<TypeListVo> page);
}

