package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.UserLevel;
import com.center.medical.member.dao.UserLevelMapper;
import com.center.medical.member.service.UserLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 会员等级表(UserLevel)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-14 11:19:36
 */
@Slf4j
@Service("userLevelService")
@RequiredArgsConstructor
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {

    private final UserLevelMapper userLevelMapper;

    /**
     * 分页查询[会员等级表]列表
     *
     * @param page  分页参数
     * @param param UserLevel查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserLevel> getPage(PageParam<UserLevel> page, UserLevel param) {
        return userLevelMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键levelId
     * @return 详情信息
     */
    @Override
    public UserLevel getInfoById(Integer id) {
        return userLevelMapper.getInfoById(id);
    }

}

