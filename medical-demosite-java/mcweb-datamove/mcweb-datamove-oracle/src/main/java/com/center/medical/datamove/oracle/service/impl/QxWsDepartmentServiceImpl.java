package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsDepartmentMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsDepartment;
import com.center.medical.datamove.oracle.service.QxWsDepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsDepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:02
 */
@Slf4j
@Service("qxWsDepartmentService")
@RequiredArgsConstructor
public class QxWsDepartmentServiceImpl extends ServiceImpl<QxWsDepartmentMapper, QxWsDepartment> implements QxWsDepartmentService {

    private final QxWsDepartmentMapper qxWsDepartmentMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsDepartment> getPage(PageParam<QxWsDepartment> page, QxWsDepartment param) {
        return qxWsDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsDepartment getInfoById(String id) {
        return qxWsDepartmentMapper.getInfoById(id);
    }

}


