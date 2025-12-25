package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdDepartmentMapper;
import com.center.medical.datamove.common.bean.model.KdDepartment;
import com.center.medical.datamove.common.service.KdDepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 金蝶中的部门信息（kingdeedepartment）(KdDepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@Service("kdDepartmentService")
@RequiredArgsConstructor
public class KdDepartmentServiceImpl extends ServiceImpl<KdDepartmentMapper, KdDepartment> implements KdDepartmentService {

    private final KdDepartmentMapper kdDepartmentMapper;

    /**
     * 分页查询[金蝶中的部门信息（kingdeedepartment）]列表
     *
     * @param page  分页参数
     * @param param KdDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdDepartment> getPage(PageParam<KdDepartment> page, KdDepartment param) {
        return kdDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    @Override
    public KdDepartment getInfoById(String id) {
        return kdDepartmentMapper.getInfoById(id);
    }

}


