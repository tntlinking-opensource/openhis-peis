package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsUserRoleMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsUserRole;
import com.center.medical.datamove.oracle.service.QxWsUserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsUserRole)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:09
 */
@Slf4j
@Service("qxWsUserRoleService")
@RequiredArgsConstructor
public class QxWsUserRoleServiceImpl extends ServiceImpl<QxWsUserRoleMapper, QxWsUserRole> implements QxWsUserRoleService {

    private final QxWsUserRoleMapper qxWsUserRoleMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsUserRole查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsUserRole> getPage(PageParam<QxWsUserRole> page, QxWsUserRole param) {
        return qxWsUserRoleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public QxWsUserRole getInfoById(String id) {
        return qxWsUserRoleMapper.getInfoById(id);
    }

    ;

}


