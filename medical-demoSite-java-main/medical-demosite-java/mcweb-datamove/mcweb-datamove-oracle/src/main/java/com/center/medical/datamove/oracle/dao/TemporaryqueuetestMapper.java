package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Temporaryqueuetest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Temporaryqueuetest)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:20
 */
public interface TemporaryqueuetestMapper extends BaseMapper<Temporaryqueuetest> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuetest查询参数
     * @return 分页数据
     */
    IPage<Temporaryqueuetest> getPage(PageParam<Temporaryqueuetest> page, @Param("param") Temporaryqueuetest param);


}
