package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.UserSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (UserSaleer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:44
 */
public interface UserSaleerMapper extends BaseMapper<UserSaleer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param UserSaleer查询参数
     * @return 分页数据
     */
    IPage<UserSaleer> getPage(PageParam<UserSaleer> page, @Param("param") UserSaleer param);


}
