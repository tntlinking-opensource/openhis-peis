package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideChargeItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送项目表(OutsideChargeItem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:29
 */
public interface OutsideChargeItemMapper extends BaseMapper<OutsideChargeItem> {

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param OutsideChargeItem查询参数
     * @return 分页数据
     */
    IPage<OutsideChargeItem> getPage(PageParam<OutsideChargeItem> page, @Param("param") OutsideChargeItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideChargeItem getInfoById(@Param("id") String id);

}
