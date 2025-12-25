package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.CompareReport;
import com.center.medical.bean.model.Nation;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.data.service.ShortmessageService;
import com.center.medical.report.bean.param.ContrastReportParam;
import com.center.medical.report.bean.param.RecordManageParam;
import com.center.medical.report.bean.param.UploadWordParam;
import com.center.medical.report.bean.vo.RecordManageVo;
import com.center.medical.report.service.RecordManageService;
import com.center.medical.service.CompareReportService;
import com.center.medical.service.NationService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.SmsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 对比报告表控制层
 *
 * @author ay
 * @since 2022-12-28 11:11:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "对比报告")
@RequestMapping("/total/RecordManage")
public class RecordManageController extends BaseController {
    /**
     * 服务对象
     */
    private final RecordManageService recordManageService;
    private final MapperFacade mapperFacade;
    private final SmsRecordService smsRecordService;
    private final ShortmessageService shortmessageService;
    private final CompareReportService compareReportService;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final PeispatientService peispatientService;
    private final PatienttypeService patienttypeService;
    private final NationService nationService;


    /**
     * 【对比报告】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【对比报告】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/total/RecordManage/page", "10.总检-报告系统->总检管理-对比报告->分页查询", null),
                new InterfaceVo("对比报告预览", "GET", "/total/RecordManage/previewContrastReport", "10.总检-报告系统->总检管理-对比报告->对比报告预览", null),
                new InterfaceVo("预览", "GET", "/total/RecordManage/sample", "10.总检-报告系统->总检管理-对比报告->预览", null),
                new InterfaceVo("获取某个档案下的所有体检者信息", "GET", "/total/RecordManage/getPeispatient", "10.总检-报告系统->总检管理-对比报告->获取某个档案下的所有体检者信息", null),
                new InterfaceVo("获取对比报告列表信息", "GET", "/total/RecordManage/getCompareReport", "10.总检-报告系统->总检管理-对比报告->获取对比报告列表信息", null),
                new InterfaceVo("下载报告", "GET", "/total/RecordManage/downloadWord", "10.总检-报告系统->总检管理-对比报告->下载报告", null),
                new InterfaceVo("获取会员类型", "GET", "/total/RecordManage/getPatientTypeData", "10.总检-报告系统->总检管理-对比报告->获取会员类型", null),
                new InterfaceVo("获取民族数据", "GET", "/total/RecordManage/getNationData", "10.总检-报告系统->总检管理-对比报告->获取民族数据", null)
        );
        return R.ok(new FunctionVo("10.总检-报告系统", "对比报告", interfaceVos, "10.总检/报告系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检者档案表")
    public R<IPage<RecordManageVo>> getPage(PageParamSimple pageParamSimple, RecordManageParam param) {
        PageParam<RecordManageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.recordManageService.getPage(page, param));
    }


    /**
     * 对比报告预览
     *
     * @param param
     * @return
     */
    @GetMapping("/previewContrastReport")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "对比报告预览", notes = "对比报告预览")
    public R previewContrastReport(ContrastReportParam param) throws IOException {
        Boolean b = true;
        //返回报告数据
        if (StringUtils.isEmpty(param.getPatientcodeFirst())){
            //两个体检号
            b = recordManageService.previewContrastReport(
                param.getPatientcode(),
                param.getPatientcodeBefore(),
                param.getPatientarchiveno());
            return R.ok(b);
        }else {
            //三个体检号
            b = recordManageService.createThree(
                    param.getPatientcode(),
                    param.getPatientcodeBefore(),
                    param.getPatientcodeFirst(),
                    param.getPatientarchiveno());
            return R.ok(b);
        }
    }


    /**
     * 预览
     *
     * @param id
     * @return
     */
    @GetMapping("/sample")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "预览", notes = "预览")
    @ApiImplicitParam(name = "id", value = "id")
    public R sample(String id) {
        Map<String, String> result = new HashMap<String, String>();
        CompareReport compare = compareReportService.getInfoById(id);
        if (ObjectUtils.isEmpty(compare)) {
            throw new ServiceException("报告不存在");
        }
        String configId = compare.getConfigId();
        //访问路径
        String ppath = configId == null ? FileTypePath.FICTITIOUS_PATH
                : baseAttachmentConfigService.getInfoById(configId).getVisitPath();
        //对比报告存放位置（PDF）
        String report = compare.getPathPdf();

        result.put("ppath", ppath);
        result.put("report", report);
        return R.ok(result);
    }


    /**
     * 获取某个档案下的所有体检者信息
     *
     * @param patientarchiveno
     * @return
     */
    @GetMapping("/getPeispatient")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取某个档案下的所有体检者信息", notes = "获取某个档案下的所有体检者信息")
    @ApiImplicitParam(name = "patientarchiveno", value = "档案号( patientarchiveno )")
    public R getPeispatient(String patientarchiveno) {
        List<Peispatient> list = peispatientService.list(new QueryWrapper<Peispatient>()
                .eq("patientarchiveno", patientarchiveno.trim()));
        return R.ok(list);
    }


    /**
     * 获取对比报告列表信息
     *
     * @param patientarchiveno
     * @return
     */
    @GetMapping("/getCompareReport")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取对比报告列表信息", notes = "获取对比报告列表信息")
    @ApiImplicitParam(name = "patientarchiveno", value = "档案号")
    public R getCompareReport(String patientarchiveno) {
        List<HashMap> list = recordManageService.getCompareReport(patientarchiveno.trim());
        return R.ok(list);
    }


//    /**
//     * 下载报告
//     *
//     * @param ids
//     * @return
//     */
//    @GetMapping("/downloadWord")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "下载报告", notes = "下载报告")
//    @ApiImplicitParam(name = "ids", value = "ids")
//    public R downloadWord(String ids) {
//        if (StringUtils.isNotEmpty(ids)) {
//            CompareReport compare = compareReportService.getOne(new QueryWrapper<CompareReport>().eq("id", ids));
//            if (ObjectUtils.isNotEmpty(compare)) {
//                //对比报告存放位置（WORD）为空
//                if (ObjectUtils.isEmpty(compare.getPathWord())) {
//                    throw new ServiceException("报告未生成！");
//                }
//                String configId = compare.getConfigId();
//                //下载地址
//                String realPath = configId == null ? FileTypePath.REAL_PATH
//                        : baseAttachmentConfigService.getInfoById(configId).getRealPath();
//                // TODO: 2022/12/28 下载报告未完成
////				String result = DownUtil.downLoadFile(getResponse(), getRequest(),realPath+"/"+compare.getPathWrod() );
//                return R.ok();
//            } else {
//                throw new ServiceException("报告信息不存在！");
//            }
//        } else {
//            throw new ServiceException("请选择记录！");
//        }
//    }


    /**
     * 获取会员类型
     *
     * @return
     */
    @GetMapping("/getPatientTypeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取会员类型", notes = "获取体检者会员类型")
    public R getPatientTypeData() {
        List<Patienttype> pattList = patienttypeService.list();
        return R.ok(pattList);
    }


    /**
     * 获取民族数据
     *
     * @return
     */
    @GetMapping("/getNationData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取民族数据", notes = "获取民族数据")
    public R getNationData() {
        List<Nation> nationList = nationService.list();
        return R.ok(nationList);
    }




    /**
     * 导出单条
     *
     * @param response
     * @param param
     */
    @Log(title = "对比报告", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出对比报告")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RecordManageParam param) {
        List<RecordManageVo> list = recordManageService.getExportData(param);
        ExcelUtil<RecordManageVo> util = new ExcelUtil<RecordManageVo>(RecordManageVo.class);
        util.exportExcel(response, list, "对比报告");
    }








    /**
     * 上传word
     */
    @PostMapping("/uploadWord")
    @ApiOperation(value = "上传word", notes = "上传word")
    public R uploadWord(UploadWordParam param) throws Exception {
        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("docx") ) {
            throw new GlobalException("请选择正确的文件类型，必须是以docx结尾！");
        }
        if (StringUtils.isBlank(param.getId())) {
            throw new GlobalException("id不能为空！");
        }
        Boolean b = recordManageService.uploadWord(param);
        return R.ok(b);
    }
}

