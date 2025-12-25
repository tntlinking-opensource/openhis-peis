package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 11:02:13
 */
public interface ItemsAndFzxMapper extends BaseMapper<ItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    IPage<ItemsAndFzx> getList(PageParam<ItemsAndFzx> page, @Param("param") ItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id ItemsAndFzx查询参数
     * @return 分页数据
     */
    ItemsAndFzx getInfoById(@Param("id") String id);

    /**
     * 插入公共收费项目分中心关联记录
     *
     * @param branchId
     */
    void addWithBrandId(@Param("branchId") String branchId);
}
