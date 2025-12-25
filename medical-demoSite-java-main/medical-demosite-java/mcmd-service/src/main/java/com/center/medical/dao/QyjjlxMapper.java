package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Qyjjlx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 企业经济类型(Qyjjlx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface QyjjlxMapper extends BaseMapper<Qyjjlx> {

    /**
     * 分页查询[企业经济类型]列表
     *
     * @param page  分页参数
     * @param param Qyjjlx查询参数
     * @return 分页数据
     */
    IPage<Qyjjlx> getList(PageParam<Qyjjlx> page, @Param("param") Qyjjlx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Qyjjlx getInfoById(@Param("id") String id);

}
