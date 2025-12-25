package com.center.medical.report.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.bean.model.ReportShareMain;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.bean.param.AddReportShareParam;
import com.center.medical.bean.param.ReportSharePageParam;
import com.center.medical.bean.param.UpReportShareParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ReportShareMainVo;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.dao.MobileReportMapper;
import com.center.medical.report.dao.ReportMapper;
import com.center.medical.report.dao.ReportShareMainMapper;
import com.center.medical.report.dao.ReportShareTwoMapper;
import com.center.medical.report.service.ReportShareMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 报告分享主表(ReportShareMain)服务实现类
 *
 * @author ay
 * @since 2023-09-19 16:19:55
 */
@Slf4j
@Service("reportShareMainService")
@RequiredArgsConstructor
public class ReportShareMainServiceImpl extends ServiceImpl<ReportShareMainMapper, ReportShareMain> implements ReportShareMainService {

    private final ReportShareMainMapper reportShareMainMapper;
    private final PeispatientMapper peispatientMapper;
    private final ReportShareTwoMapper reportShareTwoMapper;
    private final Snowflake snowflake;
    private final MapperFacade mapperFacade;
    private final MobileReportMapper mobileReportMapper;
    private final ReportMapper reportMapper;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * 分页查询[报告分享主表]列表
     *
     * @param page  分页参数
     * @param param ReportShareMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportShareMainVo> getPage(PageParam<ReportShareMainVo> page, ReportSharePageParam param) {
        return reportShareMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportShareMain getInfoById(String id) {
        return reportShareMainMapper.getInfoById(id);
    }


    /**
     * 分享报告
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReportShareMainVo reportShare(AddReportShareParam param) {
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getPatientcodes())){
            throw new ServiceException("请至少选择一个体检者");
        }
        //生成主表信息
        ReportShareMain reportShareMain = new ReportShareMain();
        // 获取当前日期

        reportShareMain.setId(String.valueOf(snowflake.nextId()));
        reportShareMain.setAutofill(1);//默认自动填充
        String extractedCode = generateRandomCode();
        reportShareMain.setExtractedCode(extractedCode);//提取码
        reportShareMain.setPath(ReportConstants.REPORTSHAREPATH + "id=" + reportShareMain.getId() + "&extractedCode=" + extractedCode);//分享链接
        reportShareMain.setPathName(SecurityUtils.getUsername()+"分享的健康体检报告");
        reportShareMain.setNum(param.getPatientcodes().size());
        reportShareMain.setExpirationDate(30);//默认30天
        //设置过期时间
        DateTime expirationTime = DateUtil.offsetDay(new Date(), 30);
        reportShareMain.setExpirationTime(expirationTime);
        reportShareMain.setStatus(0);//生效
        reportShareMain.setCreatedate(new Date());
        reportShareMain.setCreateName(SecurityUtils.getUsername());
        reportShareMain.setCreateId(SecurityUtils.getUserNo());
        reportShareMain.setVisits(0);
        reportShareMainMapper.insert(reportShareMain);
        String mainId = reportShareMain.getId();

        //生成报告关联表
        for (String patientcode : param.getPatientcodes()) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
            if (ObjectUtils.isEmpty(peispatient)){
                throw new ServiceException("体检号:"+ patientcode + "不存在!");
            }
            Report report = reportMapper.getInfoByPatientcode(patientcode,0);
            if (ObjectUtils.isEmpty(report)){
                throw new ServiceException("体检号:"+ patientcode + "请先生成报告!");
            }
            //7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取
            List<Integer> statusList = Arrays.asList(7,9,10,11);
            if (!statusList.contains(report.getStatus())){
                throw new ServiceException("体检号:"+ patientcode + "未完成终审!");
            }
            ReportShareTwo two = new ReportShareTwo();
            two.setMainId(mainId);
            two.setPatientcode(patientcode);
            two.setIdOrg(peispatient.getIdOrg());
            two.setOrgName(peispatient.getOrgName());
            two.setPatientname(peispatient.getPatientname());
            two.setIdSex(peispatient.getIdSex());
            two.setAge(peispatient.getAge());
            two.setDateregister(peispatient.getDateregister());
            two.setCreatedate(new Date());
            two.setCreateName(SecurityUtils.getUsername());
            two.setCreateId(SecurityUtils.getUserNo());
            reportShareTwoMapper.insert(two);
        }

        //返回数据
        ReportShareMainVo reportShareMainVo = mapperFacade.map(reportShareMain, ReportShareMainVo.class);
        return reportShareMainVo;
    }

    /**
     * 生成随机四位的体检卡
     * @return
     */
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }


    /**
     * 更新分享报告
     * @param param
     * @return
     */
    @Override
    public String updateReportShare(UpReportShareParam param) {
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getId())){
            throw new ServiceException("请输入id!");
        }
        ReportShareMain reportShareMain = mapperFacade.map(param, ReportShareMain.class);
        ReportShareMain main = reportShareMainMapper.getInfoById(param.getId());
        reportShareMain.setPath(ReportConstants.REPORTSHAREPATH + "id=" + reportShareMain.getId()
                + (reportShareMain.getAutofill() == 1 ? "&extractedCode=" + main.getExtractedCode() : ""));//分享链接
        //设置过期时间
        DateTime expirationTime = DateUtil.offsetDay(reportShareMain.getCreatedate(), param.getExpirationDate());
        reportShareMain.setExpirationTime(expirationTime);
        //判断是否过期
        Date date = new Date();
        boolean isExpired = date.before(reportShareMain.getExpirationTime());
        //更新状态
        if (!isExpired){
            //过期
            reportShareMain.setStatus(1);
        }else {
            //没过期
            reportShareMain.setStatus(0);
        }
        reportShareMain.setModifydate(new Date());
        reportShareMain.setModifyId(SecurityUtils.getUserNo());
        reportShareMain.setModifyName(SecurityUtils.getUsername());
        //更新
        reportShareMainMapper.updateById(reportShareMain);
        return reportShareMain.getPath();
    }


    /**
     * 分享报告统计-详情
     * @param id
     * @return
     */
    @Override
    public IPage<ReportShareTwo> details(PageParam<ReportShareTwo> page , String id) {
        if (ObjectUtils.isEmpty(id)){
            throw new ServiceException("请输入id!");
        }
        return reportShareTwoMapper.details(page,id);
    }


    /**
     * 校验验证码并返回列表数据
     * @param param
     * @return
     */
    @Override
    public List<ReportListDto> validationCode(ValidationCodeParam param) {
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
        List<ReportShareTwo> list = reportShareTwoMapper.selectList(new LambdaQueryWrapper<ReportShareTwo>()
                .eq(ReportShareTwo::getMainId, id));
        if (ObjectUtils.isEmpty(list)){
            throw new ServiceException("该连接下没有数据!");
        }
        List<String> patientCodes = list.stream().map(ReportShareTwo::getPatientcode).collect(Collectors.toList());
        List<ReportListDto> reportListDtos = mobileReportMapper.getReportList(patientCodes);
        return reportListDtos;
    }


    /**
     * 分享报告过期
     * @return
     */
    @Override
    public Boolean reportShareMainExpire() {
        //生效中的，并且不是长期有效的
        List<ReportShareMain> reportShareMains = reportShareMainMapper.selectList(new LambdaQueryWrapper<ReportShareMain>()
                .eq(ReportShareMain::getStatus, 0)
                .ne(ReportShareMain::getExpirationDate,999)
        );
        if (ObjectUtils.isNotEmpty(reportShareMains)){
            for (ReportShareMain reportShareMain : reportShareMains) {
                //判断过没过期
                Date date = new Date();
                boolean isExpired = date.before(reportShareMain.getExpirationTime());
                //更新状态
                if (!isExpired){
                    //过期
                    reportShareMain.setStatus(1);
                    reportShareMainMapper.updateById(reportShareMain);
                }
            }
        }

        return Boolean.TRUE;
    }
}

