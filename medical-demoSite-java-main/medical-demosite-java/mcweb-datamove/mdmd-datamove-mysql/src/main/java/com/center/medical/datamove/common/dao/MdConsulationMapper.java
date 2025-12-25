package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdConsulation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室/总检咨询(MdConsulation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdConsulationMapper extends BaseMapper<MdConsulation> {

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param MdConsulation查询参数
     * @return 分页数据
     */
    IPage<MdConsulation> getPage(PageParam<MdConsulation> page, @Param("param") MdConsulation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdConsulation getInfoById(@Param("id") String id);

}
