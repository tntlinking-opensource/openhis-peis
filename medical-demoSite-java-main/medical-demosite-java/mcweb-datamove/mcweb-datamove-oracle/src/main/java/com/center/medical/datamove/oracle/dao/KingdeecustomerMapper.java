package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeecustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Kingdeecustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:24
 */
public interface KingdeecustomerMapper extends BaseMapper<Kingdeecustomer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeecustomer查询参数
     * @return 分页数据
     */
    IPage<Kingdeecustomer> getPage(PageParam<Kingdeecustomer> page, @Param("param") Kingdeecustomer param);


}
