package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysDepartmentMapper;
import com.center.medical.datamove.common.bean.model.SysDepartment;
import com.center.medical.datamove.common.service.SysDepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分中心部门表（对应原系统中的qx_cen_dept）(SysDepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@Service("sysDepartmentService")
@RequiredArgsConstructor
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    private final SysDepartmentMapper sysDepartmentMapper;

    /**
     * 分页查询[分中心部门表（对应原系统中的qx_cen_dept）]列表
     *
     * @param page  分页参数
     * @param param SysDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysDepartment> getPage(PageParam<SysDepartment> page, SysDepartment param) {
        return sysDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysDepartment getInfoById(String id) {
        return sysDepartmentMapper.getInfoById(id);
    }

}


