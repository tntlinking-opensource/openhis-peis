package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.UserLevelBind;
import org.apache.ibatis.annotations.Param;

/**
 * 会员当前的等级(UserLevelBind)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:37
 */
public interface UserLevelBindMapper extends BaseMapper<UserLevelBind> {

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param UserLevelBind查询参数
     * @return 分页数据
     */
    IPage<UserLevelBind> getPage(PageParam<UserLevelBind> page, @Param("param") UserLevelBind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    UserLevelBind getInfoById(@Param("id") Long id);

}
