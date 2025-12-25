package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjdwBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * XS体检单位：部门信息(MdTjdwBranch)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:06
 */
public interface MdTjdwBranchMapper extends BaseMapper<MdTjdwBranch> {

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param MdTjdwBranch查询参数
     * @return 分页数据
     */
    IPage<MdTjdwBranch> getPage(PageParam<MdTjdwBranch> page, @Param("param") MdTjdwBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjdwBranch getInfoById(@Param("id") String id);

}
