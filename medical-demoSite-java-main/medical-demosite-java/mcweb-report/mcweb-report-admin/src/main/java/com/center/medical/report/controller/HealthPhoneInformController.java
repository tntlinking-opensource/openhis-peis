package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.bean.param.SmsDataParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.PhoneInformVo;
import com.center.medical.bean.vo.SearchCodeVo;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报告领取通知-健康报告领取通知(Report)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告领取通知-健康报告领取通知")
@RequestMapping("report/healthPhoneInform")
public class HealthPhoneInformController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportService reportService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final NotificationmethodService notificationmethodService;
    private final SmsRecordService smsRecordService;

    /**
     * 【报告领取通知-健康报告领取通知】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【报告领取通知-健康报告领取通知】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询健康报告领取通知", "GET", "/report/healthPhoneInform/page", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->分页查询", null),
                new InterfaceVo("详情", "GET", "/report/healthPhoneInform/{id}", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->详情", null),
                new InterfaceVo("通知", "PUT", "/report/healthPhoneInform/notice", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->通知", null),
                new InterfaceVo("保存短信", "POST", "/report/healthPhoneInform/sendMsg", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->保存短信", null),
                new InterfaceVo("取消发送", "Put", "/report/healthPhoneInform/cancelSMS", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->取消发送", null),
                new InterfaceVo("导出数据", "POST", "/report/healthPhoneInform/export", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->导出数据", null),
                new InterfaceVo("批量发送短信，查询体检号", "GET", "/report/healthPhoneInform/searchCode", "10.总检-报告系统->报告管理-报告领取通知-健康报告领取通知->批量发送短信，查询体检号", null));
        return R.ok(new FunctionVo("10.总检-报告系统", "报告领取通知", interfaceVos, "10.总检/报告系统"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询职业报告领取通知")
    public R<IPage<PhoneInformVo>> getPage(PageParamSimple pageParamSimple, PhoneInformParam param) {
        PageParam<PhoneInformVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PhoneInformVo> iPage = reportService.getReceiveReportData(page, param,0, Constants.HEALTHPHONE);
        return R.ok(iPage);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查BG报告主表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Report> selectOne(@PathVariable String id) {
        return R.ok(this.reportService.getInfoById(id));
    }


    /**
     * 通知
     * @param noticeParam
     * @return
     */
    @PutMapping("/notice")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "通知", notes = "通知")
    public R notice(NoticeParam noticeParam) {
        noticeParam.setDiseaseHealth(Constants.HEALTHPHONE);
        Boolean b = reportService.notice(noticeParam);
        return R.toResult(b);
    }


    /**
     * 保存短信
     * @param formdata
     * @return
     */
    @PostMapping("/sendMsg")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存短信", notes = "保存短信")
    public R sendMsg(@RequestBody SendMsgFormDataParam formdata) {
        String b = reportService.sendMsg(formdata,Constants.HEALTHPHONE);
        return R.ok(b);
    }


    /**
     * 取消发送
     * @param patientcodes
     * @return
     */
    @PutMapping("/cancelSMS")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "取消发送", notes = "取消发送")
    public R cancelSMS(@RequestParam("patientcodes") List<String> patientcodes) {
        Boolean b = reportService.cancelSMS(patientcodes,Constants.HEALTHPHONE);
        return R.toResult(b);
    }


    /**
     * 导出数据
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出数据", notes = "导出Excel数据")
    public void export(HttpServletResponse response, PhoneInformParam param) {
        List<PhoneInformVo> list = reportService.exportNoticeReport(param,0);
        ExcelUtil<PhoneInformVo> util = new ExcelUtil<PhoneInformVo>(PhoneInformVo.class);
        util.exportExcel(response, list, "职业报告领取通知");
    }


    /**
     * 批量发送短信，查询体检号
     * @param patientCode
     */
    @GetMapping("/searchCode")
    @ApiOperation(value = "批量发送短信，查询体检号", notes = "批量发送短信，查询体检号")
    public R<SearchCodeVo> searchCode(String patientCode) {
        Map<String,Object> result = new HashMap<String, Object>();
        //体检号不为空
        if(StringUtils.isEmpty(patientCode)) {
            throw new ServiceException("请输入体检号！");
        }
        //体检号补0
        patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        Peispatient patient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode",patientCode));

        if(ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("体检号"+patientCode+"不存在！");
        }
        if(ObjectUtils.isEmpty(patient.getJktjzt())||patient.getJktjzt()<9){
            throw new ServiceException("体检号为"+patientCode+"的报告未交接，不能通知！");
        }else if(patient.getJktjzt()==11){
            throw new ServiceException("体检号为"+patientCode+"的报告已领取，不能通知！");
        }
        if(StringUtils.isEmpty(patient.getPhone())) {
            throw new ServiceException("体检号为"+patientCode+"的报告没有电话，不能通知！");
        }

        Report r = reportService.getOne(new QueryWrapper<Report>()
                .eq("patientcode",patientCode).eq("disease_health",0));

        if(ObjectUtils.isEmpty(r)) {
            throw new ServiceException("体检号为"+patientCode+"的报告不存在！");
        }

        boolean isSendNotice = false;
        if(ObjectUtils.isNotEmpty(r.getGrantId())) {
            Notificationmethod method = notificationmethodService.getInfoById(r.getGrantId());
            if(ObjectUtils.isNotEmpty(method) && "1".equals(method.getIsSendNotice())) {
                isSendNotice = true;
            }
        }
        if(!isSendNotice) {
            throw new ServiceException("体检号为"+patientCode+"的报告发放方式不需要通知！");
        }

        //设置返回数据
        SearchCodeVo vo = new SearchCodeVo();
        vo.setId(r.getId());
        vo.setPatientcode(patientCode);
        vo.setPatientname(patient.getPatientname());
        vo.setDoctorapply(patient.getDoctorapply());
        vo.setOrgName(patient.getOrgName());
        vo.setNumorgresv(patient.getNumorgresv());

        return R.ok(vo);
    }


    /**
     * 查看短信数据
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getSmsData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "查看短信数据", notes = "查看短信数据")
    public R<IPage<SmsRecord>> getSmsData(PageParamSimple pageParamSimple, SmsDataParam param) {
        PageParam<SmsRecord> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<SmsRecord> iPage = smsRecordService.getSmsData(page,param);
        return R.ok(iPage);
    }



}

