package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-科室结果表(MdPacsSectionResultTwo)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
public interface MdPacsSectionResultTwoMapper extends BaseMapper<MdPacsSectionResultTwo> {

    /**
     * 分页查询[PACS-科室结果表]列表
     *
     * @param page  分页参数
     * @param param MdPacsSectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<MdPacsSectionResultTwo> getPage(PageParam<MdPacsSectionResultTwo> page, @Param("param") MdPacsSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsSectionResultTwo getInfoById(@Param("id") String id);

}
