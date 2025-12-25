package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.VCheckLisMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (VCheckLisMain)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:48
 */
public interface VCheckLisMainMapper extends BaseMapper<VCheckLisMain> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckLisMain查询参数
     * @return 分页数据
     */
    IPage<VCheckLisMain> getPage(PageParam<VCheckLisMain> page, @Param("param") VCheckLisMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    VCheckLisMain getInfoById(@Param("id") Object id);

}
