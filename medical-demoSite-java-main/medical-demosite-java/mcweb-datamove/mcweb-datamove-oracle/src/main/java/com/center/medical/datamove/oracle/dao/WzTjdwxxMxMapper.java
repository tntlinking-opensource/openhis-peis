package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
public interface WzTjdwxxMxMapper extends BaseMapper<WzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxxMx查询参数
     * @return 分页数据
     */
    IPage<WzTjdwxxMx> getPage(PageParam<WzTjdwxxMx> page, @Param("param") WzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzTjdwxxMx getInfoById(@Param("id") String id);

}
