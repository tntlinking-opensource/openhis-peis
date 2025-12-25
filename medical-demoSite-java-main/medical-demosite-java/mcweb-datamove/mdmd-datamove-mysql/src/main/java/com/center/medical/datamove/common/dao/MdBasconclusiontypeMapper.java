package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBasconclusiontype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 总检结论词类型(MdBasconclusiontype)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
public interface MdBasconclusiontypeMapper extends BaseMapper<MdBasconclusiontype> {

    /**
     * 分页查询[总检结论词类型]列表
     *
     * @param page  分页参数
     * @param param MdBasconclusiontype查询参数
     * @return 分页数据
     */
    IPage<MdBasconclusiontype> getPage(PageParam<MdBasconclusiontype> page, @Param("param") MdBasconclusiontype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasconclusiontype getInfoById(@Param("id") String id);

}
