package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Tjreg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS一般检查(Tjreg)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
public interface TjregMapper extends BaseMapper<Tjreg> {

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param Tjreg查询参数
     * @return 分页数据
     */
    IPage<Tjreg> getList(PageParam<Tjreg> page, @Param("param") Tjreg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Tjreg getInfoById(@Param("id") String id);

}
