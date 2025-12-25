package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Basexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目表(Basexamltem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:42
 */
public interface BasexamltemMapper extends BaseMapper<Basexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    IPage<Basexamltem> getPage(PageParam<Basexamltem> page, @Param("param") Basexamltem param);


}
