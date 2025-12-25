package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.WsUsers;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站用户(WsUsers)数据库访问层
 *
 * @author ay
 * @since 2024-05-29 16:31:10
 */
public interface WsUsersMapper extends BaseMapper<WsUsers> {

    /**
     * 分页查询[网站用户]列表
     *
     * @param page  分页参数
     * @param param WsUsers查询参数
     * @return 分页数据
     */
    IPage<WsUsers> getPage(PageParam<WsUsers> page, @Param("param") WsUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsUsers getInfoById(@Param("id") String id);

}
