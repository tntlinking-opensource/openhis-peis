package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:45
 */
public interface SectionResultTwoMapper extends BaseMapper<SectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, @Param("param") SectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultTwo getInfoById(@Param("id") String id);

}
