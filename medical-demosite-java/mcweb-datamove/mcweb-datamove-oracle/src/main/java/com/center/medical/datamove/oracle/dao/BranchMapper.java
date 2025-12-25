package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Branch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 分中心维护表(Branch)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:51
 */
public interface BranchMapper extends BaseMapper<Branch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param Branch查询参数
     * @return 分页数据
     */
    IPage<Branch> getPage(PageParam<Branch> page, @Param("param") Branch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Branch getInfoById(@Param("id") String id);

}
