package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TjdwBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * XS体检单位：部门信息(TjdwBranch)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:31
 */
public interface TjdwBranchMapper extends BaseMapper<TjdwBranch> {

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param TjdwBranch查询参数
     * @return 分页数据
     */
    IPage<TjdwBranch> getPage(PageParam<TjdwBranch> page, @Param("param") TjdwBranch param);


}
