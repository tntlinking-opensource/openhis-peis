package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjdwxxMx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——单位明细信息(MdWzTjdwxxMx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
public interface MdWzTjdwxxMxMapper extends BaseMapper<MdWzTjdwxxMx> {

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param MdWzTjdwxxMx查询参数
     * @return 分页数据
     */
    IPage<MdWzTjdwxxMx> getPage(PageParam<MdWzTjdwxxMx> page, @Param("param") MdWzTjdwxxMx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzTjdwxxMx getInfoById(@Param("id") String id);

}
