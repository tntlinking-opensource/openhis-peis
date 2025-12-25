package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Fylx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC费用类型(Fylx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:02
 */
public interface FylxMapper extends BaseMapper<Fylx> {

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param Fylx查询参数
     * @return 分页数据
     */
    IPage<Fylx> getPage(PageParam<Fylx> page, @Param("param") Fylx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Fylx getInfoById(@Param("id") String id);

}
