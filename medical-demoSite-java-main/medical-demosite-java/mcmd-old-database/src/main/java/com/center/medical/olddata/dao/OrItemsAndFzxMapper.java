package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrItemsAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-07-13 14:27:28
 */
public interface OrItemsAndFzxMapper extends BaseMapper<OrItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    IPage<OrItemsAndFzx> getPage(PageParam<OrItemsAndFzx> page, @Param("param") OrItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrItemsAndFzx getInfoById(@Param("id") String id);

}
