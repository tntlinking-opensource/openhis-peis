package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Qyjjlx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 企业经济类型(Qyjjlx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:11
 */
public interface QyjjlxMapper extends BaseMapper<Qyjjlx> {

    /**
     * 分页查询[企业经济类型]列表
     *
     * @param page  分页参数
     * @param param Qyjjlx查询参数
     * @return 分页数据
     */
    IPage<Qyjjlx> getPage(PageParam<Qyjjlx> page, @Param("param") Qyjjlx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Qyjjlx getInfoById(@Param("id") String id);

}
