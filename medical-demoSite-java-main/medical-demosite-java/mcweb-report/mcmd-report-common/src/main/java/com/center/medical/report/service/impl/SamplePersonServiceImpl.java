package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.vo.SamplePersonVo;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatient;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.report.bean.model.SamplePerson;
import com.center.medical.report.bean.param.LoadSampleParam;
import com.center.medical.report.dao.SamplePersonMapper;
import com.center.medical.report.service.SamplePersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TJ团检报告人员样本表(SamplePerson)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
@Slf4j
@Service("samplePersonService")
@RequiredArgsConstructor
public class SamplePersonServiceImpl extends ServiceImpl<SamplePersonMapper, SamplePerson> implements SamplePersonService {

    private final SamplePersonMapper samplePersonMapper;
    private final OrPeispatientService orPeispatientService;
    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param SamplePerson查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SamplePerson> getList(PageParam<SamplePerson> page, SamplePerson param) {
        return samplePersonMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SamplePerson getInfoById(String id) {
        return samplePersonMapper.getInfoById(id);
    }

    @Override
    public IPage<SamplePersonVo> loadSamplePersonData(PageParam<SamplePersonVo> page, LoadSampleParam param) {
        //查询新系统数据
        List<String> patientCodes = new ArrayList<>();
        IPage<SamplePersonVo> iPage = samplePersonMapper.loadSamplePersonData(page,param);
        //判断是否包含老系统数据
        for (SamplePersonVo vo : iPage.getRecords()) {
            //如果是老系统数据
            if (StringUtils.isEmpty(vo.getId()) && StringUtils.isNotEmpty(vo.getPatientcode())){
                patientCodes.add(vo.getPatientcode());
            }else {
                if (vo.getFRegistered() == null || vo.getFRegistered() == 0) {
                    vo.setTjzt("未检");
                } else if (vo.getFReadytofinal() == null || vo.getFReadytofinal() == 0) {
                    vo.setTjzt("在检");
                } else {
                    vo.setTjzt("分检完成");
                }
            }
        }
        if (CollectionUtils.isNotEmpty(patientCodes)){
            //查询出老系统数据，并设置到分页数据中
            List<OrPeispatient> orPeispatientList = orPeispatientService.getByPatientCodes(patientCodes);
            if (CollectionUtils.isNotEmpty(orPeispatientList)){
                for (OrPeispatient orPeispatient : orPeispatientList) {
                    for (SamplePersonVo vo : iPage.getRecords()) {
                        if (orPeispatient.getPatientcode().equals(vo.getPatientcode())){
                            vo.setId(orPeispatient.getId());
                            vo.setFExamstarted(orPeispatient.getFExamstarted());
                            vo.setFFinalexamed(orPeispatient.getFFinalexamed());
                            vo.setPatientname(orPeispatient.getPatientname());
                            vo.setIdSex(orPeispatient.getIdSex());
                            vo.setAge(orPeispatient.getAge());
                            vo.setOrgName(orPeispatient.getOrgName());
                            vo.setOrgDepart(orPeispatient.getOrgDepart());
                            vo.setJktjzt(orPeispatient.getJktjzt());
                            vo.setZytjzt(orPeispatient.getZytjzt());
                            vo.setFRegistered(StringUtils.isEmpty(orPeispatient.getFRegistered())?null:Integer.valueOf(orPeispatient.getFRegistered()));
                            vo.setFReadytofinal(StringUtils.isEmpty(orPeispatient.getFReadytofinal())?null:Integer.valueOf(orPeispatient.getFReadytofinal()));
                            if (vo.getFRegistered() == null || vo.getFRegistered() == 0) {
                                vo.setTjzt("未检");
                            } else if (vo.getFReadytofinal() == null || vo.getFReadytofinal() == 0) {
                                vo.setTjzt("在检");
                            } else {
                                vo.setTjzt("分检完成");
                            }
                            break;
                        }
                    }
                }
            }
        }

        //添加属性
        return iPage;
    }

    @Override
    public void deleteByCriteria(String reportId,String groupId) {
        samplePersonMapper.deleteByCriteria(reportId,groupId);
    }

    @Override
    public List<SamplePerson> getListByBallCheckId(String id) {
        return samplePersonMapper.getListByBallCheckId(id);
    }


}

