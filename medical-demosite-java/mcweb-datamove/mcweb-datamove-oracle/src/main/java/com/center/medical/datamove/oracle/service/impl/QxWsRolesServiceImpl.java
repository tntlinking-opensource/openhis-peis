package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsRolesMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsRoles;
import com.center.medical.datamove.oracle.service.QxWsRolesService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsRoles)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:07
 */
@Slf4j
@Service("qxWsRolesService")
@RequiredArgsConstructor
public class QxWsRolesServiceImpl extends ServiceImpl<QxWsRolesMapper, QxWsRoles> implements QxWsRolesService {

    private final QxWsRolesMapper qxWsRolesMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsRoles查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsRoles> getPage(PageParam<QxWsRoles> page, QxWsRoles param) {
        return qxWsRolesMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsRoles getInfoById(String id) {
        return qxWsRolesMapper.getInfoById(id);
    }

}


