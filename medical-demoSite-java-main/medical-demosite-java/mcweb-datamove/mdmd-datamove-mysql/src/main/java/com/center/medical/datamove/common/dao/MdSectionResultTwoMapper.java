package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果表-结论词、小结(MdSectionResultTwo)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:35
 */
public interface MdSectionResultTwoMapper extends BaseMapper<MdSectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultTwo> getPage(PageParam<MdSectionResultTwo> page, @Param("param") MdSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultTwo getInfoById(@Param("id") String id);

}
