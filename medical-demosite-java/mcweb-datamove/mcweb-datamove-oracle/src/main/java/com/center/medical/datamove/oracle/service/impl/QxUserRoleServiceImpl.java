package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxUserRoleMapper;
import com.center.medical.datamove.oracle.bean.model.QxUserRole;
import com.center.medical.datamove.oracle.service.QxUserRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxUserRole)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:55
 */
@Slf4j
@Service("qxUserRoleService")
@RequiredArgsConstructor
public class QxUserRoleServiceImpl extends ServiceImpl<QxUserRoleMapper, QxUserRole> implements QxUserRoleService {

    private final QxUserRoleMapper qxUserRoleMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUserRole查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxUserRole> getPage(PageParam<QxUserRole> page, QxUserRole param) {
        return qxUserRoleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public QxUserRole getInfoById(String id) {
        return qxUserRoleMapper.getInfoById(id);
    }

}


