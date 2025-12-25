package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.UserLevelBind;

/**
 * 会员当前的等级(UserLevelBind)表服务接口
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:37
 */
public interface UserLevelBindService extends IService<UserLevelBind> {

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<UserLevelBind> getPage(PageParam<UserLevelBind> page, UserLevelBind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    UserLevelBind getInfoById(Long id);

}

