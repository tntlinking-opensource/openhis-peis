package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSectionResultMain;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果主表(MdSectionResultMain)数据库访问层
 *
 * @author ay
 * @since 2023-11-10 14:27:19
 */
public interface MdSectionResultMainMapper extends BaseMapper<MdSectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionResultMain查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultMain> getPage(PageParam<MdSectionResultMain> page, @Param("param") MdSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultMain getInfoById(@Param("id") String id);

}
