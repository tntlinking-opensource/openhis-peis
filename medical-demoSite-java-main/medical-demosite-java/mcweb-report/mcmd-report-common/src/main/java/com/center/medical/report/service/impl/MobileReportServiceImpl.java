package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.dto.ShareReportDto;
import com.center.medical.bean.model.NewMobileReportParam;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.model.ReportShareMain;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.bean.param.LastAccessParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ValidationCodeVo;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.report.dao.MobileReportMapper;
import com.center.medical.report.dao.ReportShareMainMapper;
import com.center.medical.report.dao.ReportShareTwoMapper;
import com.center.medical.report.service.MobileReportService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成报告内容(ReportContent)服务实现类
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@Service("mobileReportService")
@RequiredArgsConstructor
public class MobileReportServiceImpl extends ServiceImpl<MobileReportMapper, ReportContent> implements MobileReportService {

    private final MobileReportMapper mobileReportMapper;
    private final ReportShareMainMapper reportShareMainMapper;
    private final ReportShareTwoMapper reportShareTwoMapper;
    private final ISysConfigService iSysConfigService;



    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportContent getInfoById(String id) {
        return mobileReportMapper.getInfoById(id);
    }


    /**
     * 获取报告列表
     *
     * @param phone
     * @return
     */
    @Override
    public List<ReportListDto> getReportList(String phone,String year) {
        List<ReportListDto> reportListDtos = new ArrayList<>();
        //根据手机号查询体检者档案,获取体检号
        List<String> patientCodes = mobileReportMapper.getPatientCodes(phone,year);
        if (ObjectUtils.isNotEmpty(patientCodes)) {
            //查询报告列表
            reportListDtos = mobileReportMapper.getReportList(patientCodes);
        }

        return reportListDtos;
    }



    /**
     * 校验验证码并返回列表数据
     * @param param
     * @return
     */
    @Override
    public ValidationCodeVo validationCode(ValidationCodeParam param) {
        String extractedCode = param.getExtractedCode();
        String id = param.getId();
        if (ObjectUtils.isEmpty(id)){
            throw new ServiceException("请输入id!");
        }
        ReportShareMain reportShareMain = reportShareMainMapper.getInfoById(id);
        if (ObjectUtils.isEmpty(reportShareMain)){
            throw new ServiceException("请确认id是否正确!");
        }
        if (!extractedCode.equals(reportShareMain.getExtractedCode())){
            throw new ServiceException("提取码不正确!");
        }
        if (reportShareMain.getStatus() != 0){
            throw new ServiceException("该记录状态已过期!");
        }
        // 判断是否过期
        Date date = new Date();
        boolean isExpired = date.before(reportShareMain.getExpirationTime());
        if (!isExpired){
            throw new ServiceException("该链接已过期!");
        }
        List<ReportShareTwo> list = reportShareTwoMapper.selectList(new LambdaQueryWrapper<ReportShareTwo>()
                .eq(ReportShareTwo::getMainId, id));
        if (ObjectUtils.isEmpty(list)){
            throw new ServiceException("该连接下没有数据!");
        }
        List<String> patientCodes = list.stream().map(ReportShareTwo::getPatientcode).collect(Collectors.toList());
        param.setPatientCodes(patientCodes);
        List<ShareReportDto> reportListDtos = mobileReportMapper.getShareReportList(param);
        //拼接url前缀
        Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
        for (ShareReportDto dto : reportListDtos) {
            if (StringUtils.isNotEmpty(dto.getUrlPdf())){
                dto.setUrlPdf(prefix + dto.getUrlPdf());
            }
        }
        //返回数据
        ValidationCodeVo vo = new ValidationCodeVo();
        vo.setList(reportListDtos);
        vo.setPathName(reportShareMain.getPathName());
        vo.setNum(reportShareMain.getNum());
        vo.setExpirationDate(reportShareMain.getExpirationDate());
        return vo;
    }


    /**
     * 分享报告-访问次数和ip
     * @param param
     * @return
     */
    @Override
    public Boolean lastAccess(LastAccessParam param) {
        ReportShareMain reportShareMain = reportShareMainMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(reportShareMain)){
            throw new ServiceException("请确认id是否正确!");
        }
        reportShareMain.setVisits(reportShareMain.getVisits()+1);
        reportShareMain.setLastIp(IpUtils.getHostIp());
        reportShareMain.setLastTime(new Date());
        reportShareMainMapper.updateById(reportShareMain);
        return Boolean.TRUE;
    }

    /**
     * 新版小程序查询手机报告列表
     * @param param
     * @return
     */
    @Override
    public List<ReportListDto> getNewReportList(NewMobileReportParam param) {
        return mobileReportMapper.getNewReportList(param);
    }


    /**
     * 查询报告和手机号是否对应
     * @param patientcode
     * @param phone
     * @return
     */
    @Override
    public Boolean checkDetails(String patientcode, String phone) {
        String reportPhone = mobileReportMapper.getCheckDetailsPhone(patientcode);
        return StringUtils.isNotEmpty(reportPhone) && reportPhone.equals(phone);
    }

    /**
     * 根据订单号、手机号查询报告列表
     * @param phone
     * @param orderNum
     * @return
     */
    @Override
    public List<ReportListDto> getReportListByOrderId(String phone, String orderNum) {
        List<ReportListDto> reportList = mobileReportMapper.getReportListByOrderId(phone, orderNum);
        Domain domain = iSysConfigService.getDomain();
        List<ReportListDto> resultList = reportList.stream().map(item -> {
            if (StringUtils.isNotEmpty(item.getUrlPdf())) {
                item.setUrlPdf(domain.getRsPfDomain() + item.getUrlPdf());
            }
            return item;
        }).collect(Collectors.toList());
        return resultList;
    }
}


