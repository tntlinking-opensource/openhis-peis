package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysUserBranchMapper;
import com.center.medical.datamove.common.bean.model.SysUserBranch;
import com.center.medical.datamove.common.service.SysUserBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统用户关联的分中心(SysUserBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
@Slf4j
@Service("sysUserBranchService")
@RequiredArgsConstructor
public class SysUserBranchServiceImpl extends ServiceImpl<SysUserBranchMapper, SysUserBranch> implements SysUserBranchService {

    private final SysUserBranchMapper sysUserBranchMapper;

    /**
     * 分页查询[系统用户关联的分中心]列表
     *
     * @param page  分页参数
     * @param param SysUserBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUserBranch> getPage(PageParam<SysUserBranch> page, SysUserBranch param) {
        return sysUserBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysUserBranch getInfoById(String id) {
        return sysUserBranchMapper.getInfoById(id);
    }

}


