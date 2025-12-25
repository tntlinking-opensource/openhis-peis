package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Saleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Saleer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:29
 */
public interface SaleerMapper extends BaseMapper<Saleer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Saleer查询参数
     * @return 分页数据
     */
    IPage<Saleer> getPage(PageParam<Saleer> page, @Param("param") Saleer param);


}
