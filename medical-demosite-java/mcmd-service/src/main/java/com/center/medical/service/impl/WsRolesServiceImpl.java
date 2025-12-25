package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.WsRoles;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.WsRolesMapper;
import com.center.medical.service.WsRolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 网站角色(WsRoles)服务实现类
 *
 * @author ay
 * @since 2024-05-29 16:24:13
 */
@Slf4j
@Service("wsRolesService")
@RequiredArgsConstructor
public class WsRolesServiceImpl extends ServiceImpl<WsRolesMapper, WsRoles> implements WsRolesService {

    private final WsRolesMapper wsRolesMapper;

    /**
     * 分页查询[网站角色]列表
     *
     * @param page  分页参数
     * @param param WsRoles查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsRoles> getPage(PageParam<WsRoles> page, WsRoles param) {
        return wsRolesMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsRoles getInfoById(String id) {
        return wsRolesMapper.getInfoById(id);
    }

}

