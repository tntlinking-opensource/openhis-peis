package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsBarcodeClass;
import org.apache.ibatis.annotations.Param;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-18 10:27:20
 */
public interface ItemsBarcodeClassMapper extends BaseMapper<ItemsBarcodeClass> {

    /**
     * 分页查询[收费项目条码打印分类]列表
     *
     * @param page  分页参数
     * @param param ItemsBarcodeClass查询参数
     * @return 分页数据
     */
    IPage<ItemsBarcodeClass> getPage(PageParam<ItemsBarcodeClass> page, @Param("param") ItemsBarcodeClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemsBarcodeClass getInfoById(@Param("id") String id);

}
