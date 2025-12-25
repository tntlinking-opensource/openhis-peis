package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportDefaultDoctor;
import com.center.medical.report.bean.param.DefaultDoctorPageParam;
import com.center.medical.report.bean.param.RDDoctorSaOrUpParam;
import com.center.medical.report.bean.vo.ReportDefaultDoctorVo;
import com.center.medical.report.dao.ReportDefaultDoctorMapper;
import com.center.medical.report.service.ReportDefaultDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 报告中科室默认医生(ReportDefaultDoctor)服务实现类
 *
 * @author ay
 * @since 2024-08-21 16:54:18
 */
@Slf4j
@Service("reportDefaultDoctorService")
@RequiredArgsConstructor
public class ReportDefaultDoctorServiceImpl extends ServiceImpl<ReportDefaultDoctorMapper, ReportDefaultDoctor> implements ReportDefaultDoctorService {

    private final ReportDefaultDoctorMapper reportDefaultDoctorMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[报告中科室默认医生]列表
     *
     * @param page  分页参数
     * @param param ReportDefaultDoctor查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportDefaultDoctorVo> getPage(PageParam<ReportDefaultDoctorVo> page, DefaultDoctorPageParam param) {
        return reportDefaultDoctorMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportDefaultDoctor getInfoById(String id) {
        return reportDefaultDoctorMapper.getInfoById(id);
    }

    /**
     * 通过科室id和类型查询
     * @param ksId
     * @param type
     * @return
     */
    @Override
    public String getinfoByKsIdAndType(String ksId, int type, int personnelType) {
        return reportDefaultDoctorMapper.getinfoByKsIdAndType(ksId,type,personnelType);
    }

    /**
     * 添加或更新数据
     * @param param
     * @return
     */
    @Override
    public boolean saOrUp(RDDoctorSaOrUpParam param) {
        //校验一下是否有重复的科室及类型
        //类型，添加全部时，不能有健康和职业。添加健康或职业时，不能有全部
        List<Integer> typeList = new ArrayList<>();
        List<Integer> personnelTypeList = new ArrayList<>();
        if (param.getType() == 2){
            typeList = Arrays.asList(0,1,2);
        }else {
            typeList = Arrays.asList(param.getType(),2);
        }
        if (param.getPersonnelType() == 2){
            personnelTypeList = Arrays.asList(0,1,2);
        }else {
            personnelTypeList = Arrays.asList(param.getType(),2);
        }

        LambdaQueryWrapper<ReportDefaultDoctor> lambdaQueryWrapper = new LambdaQueryWrapper<ReportDefaultDoctor>()
                .eq(ReportDefaultDoctor::getDepId, param.getDepId())
                .in(ReportDefaultDoctor::getType, typeList)
                .in(ReportDefaultDoctor::getPersonnelType, personnelTypeList);
        if (StringUtils.isNotEmpty(param.getId())){
            lambdaQueryWrapper.ne(ReportDefaultDoctor::getId, param.getId());
        }
        Long l = reportDefaultDoctorMapper.selectCount(lambdaQueryWrapper);
        if (l > 0){
            throw new ServiceException("该科室与类型已存在!");
        }
        ReportDefaultDoctor reportDefaultDoctor = mapperFacade.map(param, ReportDefaultDoctor.class);
        saveOrUpdate(reportDefaultDoctor);
        return Boolean.TRUE;
    }
}

