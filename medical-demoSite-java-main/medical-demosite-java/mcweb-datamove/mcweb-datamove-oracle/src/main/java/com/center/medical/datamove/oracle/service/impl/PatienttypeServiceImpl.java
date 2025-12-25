package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PatienttypeMapper;
import com.center.medical.datamove.oracle.bean.model.Patienttype;
import com.center.medical.datamove.oracle.service.PatienttypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者类型(Patienttype)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:56
 */
@Slf4j
@Service("patienttypeService")
@RequiredArgsConstructor
public class PatienttypeServiceImpl extends ServiceImpl<PatienttypeMapper, Patienttype> implements PatienttypeService {

    private final PatienttypeMapper patienttypeMapper;

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param Patienttype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Patienttype> getPage(PageParam<Patienttype> page, Patienttype param) {
        return patienttypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Patienttype getInfoById(String id) {
        return patienttypeMapper.getInfoById(id);
    }

}


