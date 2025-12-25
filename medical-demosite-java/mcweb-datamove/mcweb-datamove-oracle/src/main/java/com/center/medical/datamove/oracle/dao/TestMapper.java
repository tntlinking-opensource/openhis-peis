package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Test;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Test)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:22
 */
public interface TestMapper extends BaseMapper<Test> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Test查询参数
     * @return 分页数据
     */
    IPage<Test> getPage(PageParam<Test> page, @Param("param") Test param);


}
