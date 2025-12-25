package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCreateorder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表(MdCreateorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
public interface MdCreateorderMapper extends BaseMapper<MdCreateorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    IPage<MdCreateorder> getPage(PageParam<MdCreateorder> page, @Param("param") MdCreateorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreateorder getInfoById(@Param("id") String id);

}
