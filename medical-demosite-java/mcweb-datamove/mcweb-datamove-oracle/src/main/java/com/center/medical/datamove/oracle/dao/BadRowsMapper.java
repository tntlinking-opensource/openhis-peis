package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BadRows;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (BadRows)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:16
 */
public interface BadRowsMapper extends BaseMapper<BadRows> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BadRows查询参数
     * @return 分页数据
     */
    IPage<BadRows> getPage(PageParam<BadRows> page, @Param("param") BadRows param);


}
