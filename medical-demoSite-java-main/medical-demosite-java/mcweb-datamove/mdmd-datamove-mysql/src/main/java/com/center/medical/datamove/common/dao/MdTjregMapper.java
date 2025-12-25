package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjreg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS一般检查(MdTjreg)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:08
 */
public interface MdTjregMapper extends BaseMapper<MdTjreg> {

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param MdTjreg查询参数
     * @return 分页数据
     */
    IPage<MdTjreg> getPage(PageParam<MdTjreg> page, @Param("param") MdTjreg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjreg getInfoById(@Param("id") String id);

}
