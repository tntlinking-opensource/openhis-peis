package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Basexamltemtype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目类型表(Basexamltemtype)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:46
 */
public interface BasexamltemtypeMapper extends BaseMapper<Basexamltemtype> {

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param Basexamltemtype查询参数
     * @return 分页数据
     */
    IPage<Basexamltemtype> getPage(PageParam<Basexamltemtype> page, @Param("param") Basexamltemtype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Basexamltemtype getInfoById(@Param("id") String id);

}
