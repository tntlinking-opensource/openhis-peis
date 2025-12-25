package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideCheckin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送项目图片关联表(MdOutsideCheckin)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
public interface MdOutsideCheckinMapper extends BaseMapper<MdOutsideCheckin> {

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideCheckin查询参数
     * @return 分页数据
     */
    IPage<MdOutsideCheckin> getPage(PageParam<MdOutsideCheckin> page, @Param("param") MdOutsideCheckin param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOutsideCheckin getInfoById(@Param("id") String id);

}
