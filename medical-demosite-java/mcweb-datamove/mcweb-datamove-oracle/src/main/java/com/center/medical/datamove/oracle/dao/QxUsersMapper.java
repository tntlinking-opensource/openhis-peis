package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxUsers)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:56
 */
public interface QxUsersMapper extends BaseMapper<QxUsers> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUsers查询参数
     * @return 分页数据
     */
    IPage<QxUsers> getPage(PageParam<QxUsers> page, @Param("param") QxUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxUsers getInfoById(@Param("id") String id);

}
