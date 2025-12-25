package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusiontype;
import org.apache.ibatis.annotations.Param;

/**
 * 总检结论词类型(Basconclusiontype)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
public interface BasconclusiontypeMapper extends BaseMapper<Basconclusiontype> {

    /**
     * 分页查询[总检结论词类型]列表
     *
     * @param page  分页参数
     * @param param Basconclusiontype查询参数
     * @return 分页数据
     */
    IPage<Basconclusiontype> getList(PageParam<Basconclusiontype> page, @Param("param") Basconclusiontype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basconclusiontype getInfoById(@Param("id") String id);

}
