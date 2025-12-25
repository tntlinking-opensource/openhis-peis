package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.SpecimenType;
import com.center.medical.data.bean.param.SpecimenTypeParam;
import org.apache.ibatis.annotations.Param;

/**
 * 标本种类(SpecimenType)数据库访问层
 *
 * @author ay
 * @since 2023-11-07 15:49:17
 */
public interface SpecimenTypeMapper extends BaseMapper<SpecimenType> {

    /**
     * 分页查询[标本种类]列表
     *
     * @param page  分页参数
     * @param param SpecimenType查询参数
     * @return 分页数据
     */
    IPage<SpecimenType> getPage(PageParam<SpecimenType> page, @Param("param") SpecimenTypeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SpecimenType getInfoById(@Param("id") String id);

}
