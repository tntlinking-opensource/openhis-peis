package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.UserLevelBind;
import com.center.medical.member.dao.UserLevelBindMapper;
import com.center.medical.member.service.UserLevelBindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 会员当前的等级(UserLevelBind)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:37
 */
@Slf4j
@Service("userLevelBindService")
@RequiredArgsConstructor
public class UserLevelBindServiceImpl extends ServiceImpl<UserLevelBindMapper, UserLevelBind> implements UserLevelBindService {

    private final UserLevelBindMapper userLevelBindMapper;

    /**
     * 分页查询[会员当前的等级]列表
     *
     * @param page  分页参数
     * @param param UserLevelBind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserLevelBind> getPage(PageParam<UserLevelBind> page, UserLevelBind param) {
        return userLevelBindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userLevelId
     * @return 详情信息
     */
    @Override
    public UserLevelBind getInfoById(Long id) {
        return userLevelBindMapper.getInfoById(id);
    }

}

