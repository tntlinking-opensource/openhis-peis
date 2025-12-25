package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserDep;
import com.center.medical.system.dao.SysUserDepMapper;
import com.center.medical.system.service.SysUserDepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统用户关联的科室(SysUserDep)表服务实现类
 *
 * @author ay
 * @since 2023-04-06 13:33:20
 */
@Slf4j
@Service("sysUserDepService")
@RequiredArgsConstructor
public class SysUserDepServiceImpl extends ServiceImpl<SysUserDepMapper, SysUserDep> implements SysUserDepService {

    private final SysUserDepMapper sysUserDepMapper;

    /**
     * 分页查询[系统用户关联的科室]列表
     *
     * @param page  分页参数
     * @param param SysUserDep查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUserDep> getList(PageParam<SysUserDep> page, SysUserDep param) {
        return sysUserDepMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysUserDep getInfoById(String id) {
        return sysUserDepMapper.getInfoById(id);
    }

}

