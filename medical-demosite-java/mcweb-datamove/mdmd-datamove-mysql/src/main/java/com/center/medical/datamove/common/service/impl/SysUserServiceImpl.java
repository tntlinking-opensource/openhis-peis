package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysUserMapper;
import com.center.medical.datamove.common.bean.model.SysUser;
import com.center.medical.datamove.common.service.SysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息表(SysUser)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
@Slf4j
@Service("sysUserService")
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[用户信息表]列表
     *
     * @param page  分页参数
     * @param param SysUser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUser> getPage(PageParam<SysUser> page, SysUser param) {
        return sysUserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public SysUser getInfoById(Long id) {
        return sysUserMapper.getInfoById(id);
    }

}


