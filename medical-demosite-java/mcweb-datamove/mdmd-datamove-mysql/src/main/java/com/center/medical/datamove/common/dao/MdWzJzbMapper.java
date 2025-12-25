package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzJzb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——家族病(MdWzJzb)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
public interface MdWzJzbMapper extends BaseMapper<MdWzJzb> {

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param MdWzJzb查询参数
     * @return 分页数据
     */
    IPage<MdWzJzb> getPage(PageParam<MdWzJzb> page, @Param("param") MdWzJzb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzJzb getInfoById(@Param("id") String id);

}
