package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxRolesMapper;
import com.center.medical.datamove.oracle.bean.model.QxRoles;
import com.center.medical.datamove.oracle.service.QxRolesService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxRoles)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:51
 */
@Slf4j
@Service("qxRolesService")
@RequiredArgsConstructor
public class QxRolesServiceImpl extends ServiceImpl<QxRolesMapper, QxRoles> implements QxRolesService {

    private final QxRolesMapper qxRolesMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxRoles查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxRoles> getPage(PageParam<QxRoles> page, QxRoles param) {
        return qxRolesMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxRoles getInfoById(String id) {
        return qxRolesMapper.getInfoById(id);
    }

    ;

}


