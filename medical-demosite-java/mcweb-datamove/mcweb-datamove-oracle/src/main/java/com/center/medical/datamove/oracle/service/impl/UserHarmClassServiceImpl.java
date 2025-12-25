package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.UserHarmClassMapper;
import com.center.medical.datamove.oracle.bean.model.UserHarmClass;
import com.center.medical.datamove.oracle.service.UserHarmClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (UserHarmClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:43
 */
@Slf4j
@Service("userHarmClassService")
@RequiredArgsConstructor
public class UserHarmClassServiceImpl extends ServiceImpl<UserHarmClassMapper, UserHarmClass> implements UserHarmClassService {

    private final UserHarmClassMapper userHarmClassMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param UserHarmClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserHarmClass> getPage(PageParam<UserHarmClass> page, UserHarmClass param) {
        return userHarmClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public UserHarmClass getInfoById(String id) {
        return userHarmClassMapper.getInfoById(id);
    }

    ;

}


