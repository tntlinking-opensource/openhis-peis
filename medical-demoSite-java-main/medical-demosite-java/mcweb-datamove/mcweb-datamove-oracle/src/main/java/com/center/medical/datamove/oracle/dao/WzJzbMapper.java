package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzJzb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——家族病(WzJzb)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
public interface WzJzbMapper extends BaseMapper<WzJzb> {

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param WzJzb查询参数
     * @return 分页数据
     */
    IPage<WzJzb> getPage(PageParam<WzJzb> page, @Param("param") WzJzb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzJzb getInfoById(@Param("id") String id);

}
