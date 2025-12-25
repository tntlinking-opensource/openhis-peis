package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface WzTjdwxxMxMapper extends BaseMapper<WzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxxMx查询参数
     * @return 分页数据
     */
    IPage<WzTjdwxxMx> getList(PageParam<WzTjdwxxMx> page, @Param("param") WzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzTjdwxxMx getInfoById(@Param("id") String id);

}
