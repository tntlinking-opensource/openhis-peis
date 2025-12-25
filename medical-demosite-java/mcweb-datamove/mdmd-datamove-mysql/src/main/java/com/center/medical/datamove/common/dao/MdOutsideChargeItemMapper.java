package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideChargeItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送项目表(MdOutsideChargeItem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
public interface MdOutsideChargeItemMapper extends BaseMapper<MdOutsideChargeItem> {

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideChargeItem查询参数
     * @return 分页数据
     */
    IPage<MdOutsideChargeItem> getPage(PageParam<MdOutsideChargeItem> page, @Param("param") MdOutsideChargeItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOutsideChargeItem getInfoById(@Param("id") String id);

}
