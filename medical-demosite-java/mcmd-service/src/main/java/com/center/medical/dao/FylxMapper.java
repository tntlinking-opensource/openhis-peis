package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Fylx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC费用类型(Fylx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
public interface FylxMapper extends BaseMapper<Fylx> {

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param Fylx查询参数
     * @return 分页数据
     */
    IPage<Fylx> getList(PageParam<Fylx> page, @Param("param") Fylx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Fylx getInfoById(@Param("id") String id);

}
