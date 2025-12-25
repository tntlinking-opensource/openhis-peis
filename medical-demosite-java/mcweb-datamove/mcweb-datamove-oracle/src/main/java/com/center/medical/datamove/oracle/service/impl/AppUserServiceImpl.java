package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppUserMapper;
import com.center.medical.datamove.oracle.bean.model.AppUser;
import com.center.medical.datamove.oracle.service.AppUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppUser)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:04
 */
@Slf4j
@Service("appUserService")
@RequiredArgsConstructor
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    private final AppUserMapper appUserMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppUser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUser> getPage(PageParam<AppUser> page, AppUser param) {
        return appUserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppUser getInfoById(String id) {
        return appUserMapper.getInfoById(id);
    }

}


