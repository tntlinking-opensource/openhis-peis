package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.UserLevel;
import org.apache.ibatis.annotations.Param;

/**
 * 会员等级表(UserLevel)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:36
 */
public interface UserLevelMapper extends BaseMapper<UserLevel> {

    /**
     * 分页查询[会员等级表]列表
     *
     * @param page  分页参数
     * @param param UserLevel查询参数
     * @return 分页数据
     */
    IPage<UserLevel> getPage(PageParam<UserLevel> page, @Param("param") UserLevel param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键levelId
     * @return 详情信息
     */
    UserLevel getInfoById(@Param("id") Integer id);

}
