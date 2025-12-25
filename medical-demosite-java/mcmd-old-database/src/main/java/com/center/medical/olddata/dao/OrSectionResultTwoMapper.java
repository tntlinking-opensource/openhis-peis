package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultTwo;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 14:39:40
 */
public interface OrSectionResultTwoMapper extends BaseMapper<OrSectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<OrSectionResultTwo> getPage(PageParam<OrSectionResultTwo> page, @Param("param") OrSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionResultTwo getInfoById(@Param("id") String id);

}
