package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysDeptMapper;
import com.center.medical.datamove.common.bean.model.SysDept;
import com.center.medical.datamove.common.service.SysDeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(SysDept)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@Service("sysDeptService")
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）]列表
     *
     * @param page  分页参数
     * @param param SysDept查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysDept> getPage(PageParam<SysDept> page, SysDept param) {
        return sysDeptMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键deptId
     * @return 详情信息
     */
    @Override
    public SysDept getInfoById(Long id) {
        return sysDeptMapper.getInfoById(id);
    }

}


