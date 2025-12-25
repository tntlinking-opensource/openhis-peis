package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.UserSaleerMapper;
import com.center.medical.datamove.oracle.bean.model.UserSaleer;
import com.center.medical.datamove.oracle.service.UserSaleerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (UserSaleer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:45
 */
@Slf4j
@Service("userSaleerService")
@RequiredArgsConstructor
public class UserSaleerServiceImpl extends ServiceImpl<UserSaleerMapper, UserSaleer> implements UserSaleerService {

    private final UserSaleerMapper userSaleerMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param UserSaleer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UserSaleer> getPage(PageParam<UserSaleer> page, UserSaleer param) {
        return userSaleerMapper.getPage(page, param);
    }


}


