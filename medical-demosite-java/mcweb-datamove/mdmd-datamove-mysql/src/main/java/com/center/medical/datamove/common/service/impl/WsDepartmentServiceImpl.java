package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsDepartmentMapper;
import com.center.medical.datamove.common.bean.model.WsDepartment;
import com.center.medical.datamove.common.service.WsDepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站部门(WsDepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Slf4j
@Service("wsDepartmentService")
@RequiredArgsConstructor
public class WsDepartmentServiceImpl extends ServiceImpl<WsDepartmentMapper, WsDepartment> implements WsDepartmentService {

    private final WsDepartmentMapper wsDepartmentMapper;

    /**
     * 分页查询[网站部门]列表
     *
     * @param page  分页参数
     * @param param WsDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsDepartment> getPage(PageParam<WsDepartment> page, WsDepartment param) {
        return wsDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsDepartment getInfoById(String id) {
        return wsDepartmentMapper.getInfoById(id);
    }

}


