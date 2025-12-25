package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeeorganization;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (Kingdeeorganization)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:26
 */
public interface KingdeeorganizationMapper extends BaseMapper<Kingdeeorganization> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeeorganization查询参数
     * @return 分页数据
     */
    IPage<Kingdeeorganization> getPage(PageParam<Kingdeeorganization> page, @Param("param") Kingdeeorganization param);


}
