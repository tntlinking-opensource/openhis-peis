package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionTotal;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ总检主表(SectionTotal)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
public interface OrSectionTotalMapper extends BaseMapper<OrSectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    IPage<OrSectionTotal> getPage(PageParam<OrSectionTotal> page, @Param("param") OrSectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionTotal getInfoById(@Param("id") String id);

}
