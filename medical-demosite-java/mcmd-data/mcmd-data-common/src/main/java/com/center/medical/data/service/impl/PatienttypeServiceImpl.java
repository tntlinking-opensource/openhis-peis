package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.dao.PatienttypeMapper;
import com.center.medical.data.service.PatienttypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 体检者类型(Patienttype)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-07 19:27:27
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


    /**
     * 获取体检者等级名称
     * @param patient
     * @return
     */
    @Override
    public String getIdPatientClass(Peispatient patient) {
        String result = "";
        if (ObjectUtils.isNotEmpty(patient)) {
            Patienttype pt = patienttypeMapper.selectOne(new QueryWrapper<Patienttype>()
                    .eq("id", patient.getIdPatientclass()));
            if (ObjectUtils.isNotEmpty(pt)) {
                result=pt.getPatientName()==null?"":pt.getPatientName();
            }
        }
        return result;
    }
}

