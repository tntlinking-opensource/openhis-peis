package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.UserHarmClass;
import com.center.medical.system.dao.UserHarmClassMapper;
import com.center.medical.system.service.UserHarmClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 总检医生-危害因素分类关联表 (UserHarmClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:44
 */
@Slf4j
@Service("userHarmClassService")
@RequiredArgsConstructor
public class UserHarmClassServiceImpl extends ServiceImpl<UserHarmClassMapper, UserHarmClass> implements UserHarmClassService {

    private final UserHarmClassMapper userHarmClassMapper;

    /**
     * 分页查询[总检医生-危害因素分类关联表 ]列表
     *
     * @param page  分页参数
     * @param param UserHarmClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserHarmClass> getList(PageParam<UserHarmClass> page, UserHarmClass param) {
        return userHarmClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public UserHarmClass getInfoById(String id) {
        return userHarmClassMapper.getInfoById(id);
    }

}

