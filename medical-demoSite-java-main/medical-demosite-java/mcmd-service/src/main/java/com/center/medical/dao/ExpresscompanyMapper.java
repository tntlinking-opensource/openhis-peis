package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Expresscompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 快递公司表(Expresscompany)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
public interface ExpresscompanyMapper extends BaseMapper<Expresscompany> {

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param Expresscompany查询参数
     * @return 分页数据
     */
    IPage<Expresscompany> getList(PageParam<Expresscompany> page, @Param("param") Expresscompany param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Expresscompany getInfoById(@Param("id") String id);

}
