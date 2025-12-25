package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ItemsAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:21
 */
public interface ItemsAndFzxMapper extends BaseMapper<ItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    IPage<ItemsAndFzx> getPage(PageParam<ItemsAndFzx> page, @Param("param") ItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemsAndFzx getInfoById(@Param("id") String id);

}
