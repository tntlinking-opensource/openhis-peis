package com.center.medical.enterprise.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.enterprise.bean.dto.KsResultsDto;
import com.center.medical.enterprise.bean.dto.ProfessionResultDto;
import com.center.medical.enterprise.bean.model.Peispatient;
import com.center.medical.enterprise.bean.model.Report;
import com.center.medical.enterprise.bean.param.OrderListDataParam;
import com.center.medical.enterprise.bean.param.ReportListDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.core.domain.entity.SysUser;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.common.util.Render;
import com.center.medical.enterprise.common.utils.SecurityUtils;
import com.center.medical.enterprise.mapper.ReportMapper;
import com.center.medical.enterprise.service.PeispatientService;
import com.center.medical.enterprise.service.ReportService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * BG报告主表(MdReport)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Slf4j
@Service("mdReportService")
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    private final ReportMapper reportMapper;
    private final PeispatientService peispatientService;

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param MdReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportListDataVo> getPage(PageParam<ReportListDataVo> page, ReportListDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        IPage<ReportListDataVo> iPage = reportMapper.getPage(page, param);
        List<ReportListDataVo> list = iPage.getRecords();
        for (ReportListDataVo vo : list) {
            int fRegistered = vo.getFRegistered();
            int fReadytofinal = vo.getFReadytofinal();
            if(fRegistered==0){
                vo.setTjzt("未检");
            }else if(fReadytofinal==0){
                vo.setTjzt("在检");
            }else{
                vo.setTjzt("分检完成");
            }
            vo.setSex(Render.getSex(vo.getIdSex()));
            vo.setJktjzt(Render.getTjzt(vo.getJktjztNum()));
            vo.setZytjzt(Render.getTjzt(vo.getZytjztNum()));
            Integer zytjzt = vo.getZytjztNum();
            if(zytjzt!=null && zytjzt.intValue()>0) {
                ProfessionResultDto oos = reportMapper.getProfessionResultById(vo.getPatientcode());
                vo.setOccupationSummary(oos.getOccupationSummary());
                String summaryText = reportMapper.getSummaryText(vo.getPatientcode(),oos.getSerialNo());
                vo.setSummaryText(summaryText);
            }
        }
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Report getInfoById(String id) {
        return reportMapper.getInfoById(id);
    }

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    @Override
    public List<ReportExportVo> getExportData(ReportListDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        return reportMapper.getExportData(param);
    }

    /**
     * 职业结论导出
     * @param param
     * @return
     */
    @Override
    public List<ReportExportZyVo> getExportZyData(ReportListDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        List<ReportExportZyVo> list = reportMapper.getExportZyData(param);
        for (ReportExportZyVo vo : list) {
            vo.setTjzt(Render.getTjzt(vo.getZytjzt()));
            Integer zytjzt = vo.getZytjzt();
            if(zytjzt!=null && zytjzt.intValue()>0) {
                ProfessionResultDto oos = reportMapper.getProfessionResultById(vo.getPatientcode());
                vo.setOccupationSummary(oos.getOccupationSummary());
                String summaryText = reportMapper.getSummaryText(vo.getPatientcode(),oos.getSerialNo());
                vo.setSummaryText(summaryText);
            }
        }
        return list;
    }

    /**
     * 对比报告数据
     * @param id
     * @return
     */
    @Override
    public List<CompareDataVo> getCompareData(String id) {
        List<CompareDataVo> compareDataVoList = new ArrayList<>();
        Peispatient patient = peispatientService.getInfoById(id);
        String patientcode = patient.getPatientcode();
        List<String> columns = new ArrayList<String>();//所有体检号
        columns.add(patientcode);

        //本次结果
        List<KsResultsDto> ksResultsDtoList = reportMapper.getksResults(patientcode);
        Set<String> deptIds = new HashSet<String>();
        for (KsResultsDto dto : ksResultsDtoList) {
            deptIds.add(dto.getDeptNo());
        }
        CompareDataVo vo = new CompareDataVo(patientcode,ksResultsDtoList);
        compareDataVoList.add(vo);


        //历次结果,只显示本次体检有的科室
        List<String> patientcodes = peispatientService.getOtherCode(patient.getPatientarchiveno(),patientcode);
        if (CollectionUtil.isNotEmpty(patientcodes)){
            List<String> deptIdlist = new ArrayList<>(deptIds);
            for (String code : patientcodes) {
                List<KsResultsDto> list = reportMapper.getPreviousResults(code,deptIdlist);
                CompareDataVo otherVo = new CompareDataVo(code,list);
                compareDataVoList.add(otherVo);
            }

        }
        return compareDataVoList;
    }

    /**
     * 获取订单信息
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<OrderListDataVo> getOrderListData(PageParam<OrderListDataVo> page, OrderListDataParam param) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String customerId = user.getCustomerId();
        if (!"1".equals(customerId)){
            param.setCustomerId(customerId);
        }
        //去空格
        if (ObjectUtils.isNotEmpty(param.getKey())) {
            param.setKey(param.getKey().trim());
        }
        return reportMapper.getOrderListData(page,param);
    }
}


