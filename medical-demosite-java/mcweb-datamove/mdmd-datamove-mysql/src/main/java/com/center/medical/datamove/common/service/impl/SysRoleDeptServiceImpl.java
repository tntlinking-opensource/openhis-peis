package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysRoleDeptMapper;
import com.center.medical.datamove.common.bean.model.SysRoleDept;
import com.center.medical.datamove.common.service.SysRoleDeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色和部门关联表(SysRoleDept)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
@Slf4j
@Service("sysRoleDeptService")
@RequiredArgsConstructor
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptService {

    private final SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 分页查询[角色和部门关联表]列表
     *
     * @param page  分页参数
     * @param param SysRoleDept查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysRoleDept> getPage(PageParam<SysRoleDept> page, SysRoleDept param) {
        return sysRoleDeptMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public SysRoleDept getInfoById(Long id) {
        return sysRoleDeptMapper.getInfoById(id);
    }

}


