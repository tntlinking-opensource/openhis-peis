package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.dao.SysDepartmentMapper;
import com.center.medical.system.service.SysDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 沃德医疗部门总集(所有中心部门的总集)(SysDepartment)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-18 19:26:13
 */
@Slf4j
@Service("sysDepartmentService")
@RequiredArgsConstructor
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    private final SysDepartmentMapper sysDepartmentMapper;

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)]列表
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

