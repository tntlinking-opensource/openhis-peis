package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Expresscompany;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 快递公司表(Expresscompany)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:39
 */
public interface ExpresscompanyMapper extends BaseMapper<Expresscompany> {

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param Expresscompany查询参数
     * @return 分页数据
     */
    IPage<Expresscompany> getPage(PageParam<Expresscompany> page, @Param("param") Expresscompany param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Expresscompany getInfoById(@Param("id") String id);

}
