package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Tjreg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS一般检查(Tjreg)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:32
 */
public interface TjregMapper extends BaseMapper<Tjreg> {

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param Tjreg查询参数
     * @return 分页数据
     */
    IPage<Tjreg> getPage(PageParam<Tjreg> page, @Param("param") Tjreg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Tjreg getInfoById(@Param("id") String id);

}
