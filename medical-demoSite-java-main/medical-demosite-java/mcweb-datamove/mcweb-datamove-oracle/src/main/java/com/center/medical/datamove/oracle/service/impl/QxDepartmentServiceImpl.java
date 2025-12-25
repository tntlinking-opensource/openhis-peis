package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxDepartmentMapper;
import com.center.medical.datamove.oracle.bean.model.QxDepartment;
import com.center.medical.datamove.oracle.service.QxDepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxDepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:45
 */
@Slf4j
@Service("qxDepartmentService")
@RequiredArgsConstructor
public class QxDepartmentServiceImpl extends ServiceImpl<QxDepartmentMapper, QxDepartment> implements QxDepartmentService {

    private final QxDepartmentMapper qxDepartmentMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxDepartment> getPage(PageParam<QxDepartment> page, QxDepartment param) {
        return qxDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxDepartment getInfoById(String id) {
        return qxDepartmentMapper.getInfoById(id);
    }

}


