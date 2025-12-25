package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysUserPostMapper;
import com.center.medical.datamove.common.bean.model.SysUserPost;
import com.center.medical.datamove.common.service.SysUserPostService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户与岗位关联表(SysUserPost)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Slf4j
@Service("sysUserPostService")
@RequiredArgsConstructor
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    private final SysUserPostMapper sysUserPostMapper;

    /**
     * 分页查询[用户与岗位关联表]列表
     *
     * @param page  分页参数
     * @param param SysUserPost查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUserPost> getPage(PageParam<SysUserPost> page, SysUserPost param) {
        return sysUserPostMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public SysUserPost getInfoById(Long id) {
        return sysUserPostMapper.getInfoById(id);
    }

}


