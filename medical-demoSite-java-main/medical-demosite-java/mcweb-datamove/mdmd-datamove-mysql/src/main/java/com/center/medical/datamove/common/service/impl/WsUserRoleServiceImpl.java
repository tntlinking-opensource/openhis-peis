package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsUserRoleMapper;
import com.center.medical.datamove.common.bean.model.WsUserRole;
import com.center.medical.datamove.common.service.WsUserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站用户权限(WsUserRole)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:44
 */
@Slf4j
@Service("wsUserRoleService")
@RequiredArgsConstructor
public class WsUserRoleServiceImpl extends ServiceImpl<WsUserRoleMapper, WsUserRole> implements WsUserRoleService {

    private final WsUserRoleMapper wsUserRoleMapper;

    /**
     * 分页查询[网站用户权限]列表
     *
     * @param page  分页参数
     * @param param WsUserRole查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsUserRole> getPage(PageParam<WsUserRole> page, WsUserRole param) {
        return wsUserRoleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public WsUserRole getInfoById(String id) {
        return wsUserRoleMapper.getInfoById(id);
    }

}


