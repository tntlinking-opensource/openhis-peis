package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BasexamltemSign;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目体证词关联表(BasexamltemSign)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:44
 */
public interface BasexamltemSignMapper extends BaseMapper<BasexamltemSign> {

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param BasexamltemSign查询参数
     * @return 分页数据
     */
    IPage<BasexamltemSign> getPage(PageParam<BasexamltemSign> page, @Param("param") BasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BasexamltemSign getInfoById(@Param("id") String id);

}
