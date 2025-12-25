package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.CreatecomboType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐分类(CreatecomboType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:56
 */
public interface CreatecomboTypeMapper extends BaseMapper<CreatecomboType> {

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param CreatecomboType查询参数
     * @return 分页数据
     */
    IPage<CreatecomboType> getPage(PageParam<CreatecomboType> page, @Param("param") CreatecomboType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatecomboType getInfoById(@Param("id") String id);

}
