package com.center.medical.pacs.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.vo.DicomInfoVo;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.uuid.UUID;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.service.KsIpService;
import com.center.medical.dicom.service.DicomService;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.*;
import com.center.medical.pacs.service.PacsAbteilungService;
import com.center.medical.pacs.service.PacsCsharpService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * pacs科室接口
 * 彩超、放射科室接口
 *
 * @author xhp
 * @since 2023-03-15 9:27
 */
@Slf4j
@RestController
@RequestMapping("/pacs/sysytem/pacsAbteilungs")
@Api(tags = "pacs科室")
@RequiredArgsConstructor
@Validated
public class PacsAbteilungController extends BaseController {
    private final PacsAbteilungService pacsAbteilungService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final DicomReceiver dicomReceiver;
    private final KsIpService ksIpService;
    private final ISysConfigService iSysConfigService;
    private final DicomService dicomService;
    private final PacsCsharpService pacsCsharpService;
    @Autowired
    private LoadProperties loadProperties;


    @GetMapping("/abteilung/list")
    @ApiOperation(value = "获取科室列表", notes = "获取科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = false),
            @ApiImplicitParam(name = "autoFill", value = "是否补全体检号，true是 false否", required = false)
    })
    public R getAbteilunList(String patientcode, String autoFill) {
        //是否补全体检号
        if ("true".equals(autoFill)) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        String userNo = SecurityUtils.getUserNo();
        List<PacsAbteilungAbteilunListVo> list = pacsAbteilungService.getAbteilunList(patientcode, userNo);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("patientCode", patientcode);
        return R.ok(map);
    }

    @GetMapping("/patient/list")
    @ApiOperation(value = "体检者列表分页查询", notes = "体检者列表分页查询")
    public R<IPage<PacsAbteilungPatientListVo>> getPatientList(PageParamSimple pageParamSimple, PatientListParam patientListParam) {
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        if (ObjectUtil.isNotEmpty(patientListParam.getAutoFill()) && "true".equals(patientListParam.getAutoFill())) {
            patientListParam.setPatientcode(ToolUtil.patientCode(patientListParam.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        }
        return R.ok(pacsAbteilungService.getPatientList(page, patientListParam));
    }

    @GetMapping("/item/list")
    @ApiOperation(value = "体检者收费项目列表查询", notes = "体检者收费项目列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", required = true)
    })
    public R<List<PacsAbteilungItemListVo>> getItemList(@RequestParam String patientcode, @RequestParam String deptNo) {
        return R.ok(pacsAbteilungService.getItemList(patientcode, deptNo));
    }

    @GetMapping("/sign/list")
    @ApiOperation(value = "体征词列表查询", notes = "体征词列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true),
            @ApiImplicitParam(name = "itemId", value = "pacs基础收费项目id", required = true)
    })
    public R<List<PacsAbteilungSignListVo>> getSignList(@RequestParam String patientcode, @RequestParam String itemId) {
        return R.ok(pacsAbteilungService.getSignList(patientcode, itemId));
    }

    @GetMapping("/item/search")
    @ApiOperation(value = "选择收费项目，展示小结描述等信息", notes = "选择收费项目，展示小结描述等信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feeitemId", value = "体检者收费项目id", required = true),
    })
    public R<PacsAbteilungItemSearchVo> search(@RequestParam String feeitemId) {
        return R.ok(pacsAbteilungService.search(feeitemId));
    }

    @GetMapping("/history/list")
    @ApiOperation(value = "历史列表分页查询", notes = "历史列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true),
            @ApiImplicitParam(name = "deptNo", value = "部门编号", required = true)
    })
    public R<IPage<PacsAbteilungHistoryListVo>> getHistoryList(PageParamSimple pageParamSimple, @RequestParam String patientcode, @RequestParam String deptNo, @RequestParam(required = false) String describe) {
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(pacsAbteilungService.getHistoryList(page, patientcode, deptNo, describe));
    }

    @RepeatSubmit(interval = 3000, message = "三秒内只能审核一次！")
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "审核、保存", notes = "审核、保存")
    public R saveOrUpdate(@Validated @RequestBody PacsAbteilungSaveParam pacsAbteilungSaveParam) {
        pacsAbteilungService.saveOrUpdate(pacsAbteilungSaveParam);
        if(pacsAbteilungSaveParam.getType() == 2 && StringUtils.equals(loadProperties.name, "bazhou")){
            //发送中间库保存数据
            Peispatientfeeitem peispatientfeeitem = peispatientfeeitemService.getInfoById(pacsAbteilungSaveParam.getFeeitemId());
            pacsCsharpService.sendSaveResult(peispatientfeeitem.getIdPatient(),pacsAbteilungSaveParam.getDeptNo());
        }
        return R.ok();
    }

    @PutMapping("/reverse")
    @ApiOperation(value = "反审核", notes = "反审核")
    public R reverse(@Validated @RequestBody PacsAbteilungReverseParam pacsAbteilungReverseParam) {
        pacsAbteilungService.reverse(pacsAbteilungReverseParam);
        return R.ok();
    }

    @GetMapping("/check")
    @ApiOperation(value = "查询体检者状态", notes = "查询体检者状态，如：体检者已总检不能修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true),
    })
    public R check(@RequestParam String patientcode) {
        pacsAbteilungService.check(patientcode);
        return R.ok();
    }


    @GetMapping("/doctor/list")
    @ApiOperation(value = "检查人、审核人列表", notes = "检查人、审核人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptNo", value = "部门编号", required = true),
            @ApiImplicitParam(name = "inputCode", value = "拼音码", required = true)
    })
    public R<List<AllUserDataVo>> getDoctorList(@RequestParam String deptNo, @RequestParam String inputCode) {
        return R.ok(pacsAbteilungService.getDoctorList(deptNo, inputCode));
    }


    @GetMapping("/img/list")
    @ApiOperation(value = "图片列表", notes = "放射科室图片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feeitemId", value = "体检者收费项目id", required = true),
    })
    public R<List<PacsAbteilungImgVo>> getImgList(@RequestParam String feeitemId) {
        return R.ok(pacsAbteilungService.getImgList(feeitemId));
    }


    @GetMapping("/dcmimg/info")
    @ApiOperation(value = "获取Dicom图片信息", notes = "获取Dicom图片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dcmsrc", value = "Dicom图片路径", required = true),
    })
    public R<DicomInfoVo> dcmimgInfo(@RequestParam String dcmsrc) {
        Domain domain = iSysConfigService.getDomain();
        DicomInfoVo dicomInfoVo = new DicomInfoVo();
        String dicomUrl = dcmsrc;
        if (!StringUtils.startsWith(dcmsrc, "http") || !StringUtils.startsWith(dcmsrc, "ftp")) {
            if (StringUtils.startsWith(dcmsrc, "/")) {
                dicomUrl = domain.getLcDomain() + dcmsrc;
            } else {
                dicomUrl = domain.getLcDomain() + "/" + dcmsrc;
            }
        }
        try {
            URL url = new URL(dicomUrl);
            InputStream inputStream = url.openStream();

            DicomInputStream dicomInputStream = new DicomInputStream(inputStream);
            Attributes attrs = dicomInputStream.readDataset(-1, Tag.PixelData);
            String studyDate = DateUtil.format(DateUtil.parse(attrs.getString(Tag.ContentDate), "yyyyMMdd"), "yyyy-MM-dd");
            dicomInfoVo.setContentDate(studyDate);
            String studyTime = DateUtils.formatSeriesTime(attrs.getString(Tag.StudyTime));
            dicomInfoVo.setContentTime(studyTime);
//            dicomInfoVo.setModality(attrs.getString(Tag.Modality));
//            dicomInfoVo.setManufacturer(attrs.getString(Tag.Manufacturer));
            dicomInfoVo.setInstitutionName(attrs.getString(Tag.InstitutionName));
//            dicomInfoVo.setSeriesDesc(attrs.getString(Tag.SeriesDescription));
//            dicomInfoVo.setInstitutionalDeptName(attrs.getString(Tag.InstitutionalDepartmentName));
            dicomInfoVo.setPatientName(attrs.getString(Tag.StudyDate));
            dicomInfoVo.setPatientcode(attrs.getString(Tag.PatientID));
            dicomInfoVo.setPatientSex(attrs.getString(Tag.PatientSex));
            dicomInfoVo.setBodyPart(attrs.getString(Tag.BodyPartExamined));
            dicomInfoVo.setExposureTime(attrs.getString(Tag.ExposureTime) + "ms");
//            dicomInfoVo.setExposure(attrs.getString(Tag.Exposure));
//            dicomInfoVo.setExposureIndex(attrs.getString(Tag.ExposureIndex));
            dicomInfoVo.setWindowCenter(attrs.getString(Tag.WindowCenter));
            dicomInfoVo.setWindowWidth(attrs.getString(Tag.WindowWidth));
            dicomInfoVo.setMag(attrs.getString(Tag.MagneticFieldStrength));
            dicomInfoVo.setKv(attrs.getString(Tag.KVP));
            dicomInfoVo.setPatienOrientation(attrs.getString(Tag.PatientOrientation));
            dicomInfoVo.setViewPosition(attrs.getString(Tag.ViewPosition));
            dicomInfoVo.setMa(attrs.getString(Tag.XRayTubeCurrent));
            dicomInfoVo.setMa(attrs.getString(Tag.XRayTubeCurrent));
            dicomInfoVo.setAge(attrs.getString(Tag.PatientAge));


            log.info("dicomInfoVo:{}", dicomInfoVo);
            dicomInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok(dicomInfoVo);
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.formatSeriesTime("122259"));
    }

    @GetMapping("/patient/total")
    @ApiOperation(value = "获取体检者总计", notes = "获取体检者总计")
    public R<PacsAbteilungPatientTotalVo> getPatientTotal(PatientListParam patientListParam) {
        return R.ok(pacsAbteilungService.getPatientTotal(patientListParam));
    }

    @DeleteMapping("/attachment/delete/{ids}")
    @ApiOperation(value = "删除图片", notes = "删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "图片id", required = true),
    })
    public R delete(@PathVariable List<String> ids) {
        pacsAbteilungService.deleteAttachments(ids);
        return R.ok();
    }

    @DeleteMapping("/attachment/deleteByFeeitemId/{id}")
    @ApiOperation(value = "清除图片", notes = "删除收费项目下所有图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "体检者收费项目id", required = true),
    })
    public R deleteByFeeitemId(@PathVariable String id) {
        pacsAbteilungService.deleteByFeeitemId(id);
        return R.ok();
    }

    @PostMapping("/uploadDicom")
    @ApiOperation(value = "上传DICOM", notes = "上传DICOM（多个）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "上传DICOM文件列表", required = true),
            @ApiImplicitParam(name = "feeitemId", value = "体检者的收费项目ID")
    })
    public R<MultUploadResultVo> uploadDicom(List<MultipartFile> files, String feeitemId) {
        log.info("收费项目ID：{}", feeitemId);
        if (files == null || files.size() == 0) {
            return R.fail("文件不能为空");
        }
        //临时保存到本地根目录
        String baseDir = System.getProperty("user.dir");
        String folderPath = baseDir + File.separator + "dicomUpload" + File.separator + UUID.randomUUID();
        File folder = new File(folderPath);
        folder.mkdirs();
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        MultUploadResultVo multUploadResultVo = new MultUploadResultVo();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            //如果文件名包含中文则将中文转化成拼音
            String dcmName = com.center.medical.common.utils.StringUtils.delSpecialChar(com.center.medical.common.utils.StringUtils.convertToPinyin(fileName));
            //dcm文件临时保存位置
            File dcmFile = new File(folderPath, dcmName);
            try {
                file.transferTo(dcmFile);
                Attachment attachment = dicomService.saveDicomPath(dcmFile, feeitemId);
                if (Objects.nonNull(attachment)) {
                    successList.add(attachment.getFilePath());
                }
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");
            } catch (IOException e) {
                log.error("上传失败：{}、{}", fileName, e);
                failList.add(fileName);
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败," + e.getMessage() + "</br>");
            }
        }
        multUploadResultVo.setSuccessList(successList);
        multUploadResultVo.setFailList(failList);
        multUploadResultVo.setResultMsg(resultMsg.toString());
        return R.ok(dicomReceiver.upload(files, feeitemId));
    }

    @PostMapping("/uploadScreenshot")
    @ApiOperation(value = "彩超截图上传", notes = "彩超截图上传")
    public R<PacsAbteilungSaveScreenshotVo> uploadScreenshot(@RequestBody PacsAbteilungSaveScreenshotParam pacsAbteilungSaveScreenshotParam) {
        return R.ok(pacsAbteilungService.uploadScreenshot(pacsAbteilungSaveScreenshotParam));
    }


    /**
     * 根据科室配置ID彩超抓图
     *
     * @param feeitemId
     * @param ksIpId
     * @return
     */
    @GetMapping("/readImages/{feeitemId}/{ksIpId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据科室配置ID彩超抓图", notes = "根据科室配置ID彩超抓图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feeitemId", value = "体检者收费项目id"),
            @ApiImplicitParam(name = "ksIpId", value = "科室配置信息ID")
    })
    @Log(title = "根据科室配置ID抓图", businessType = BusinessType.OTHER)
    public R readImages(@PathVariable String feeitemId, @PathVariable String ksIpId) {
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemService.getInfoById(feeitemId);
        if (Objects.isNull(peispatientfeeitem)) {
            throw new GlobalException("体检者收费项目不存在");
        }
        log.info("开始执行抓图功能1：{}", ksIpId);
        KsIp ks = ksIpService.getInfoById(ksIpId);
        String patientCode = peispatientfeeitem.getIdPatient();
        log.info("开始执行抓图功能2：{},{}", feeitemId, Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH_2 + feeitemId + "/" + patientCode + "/" + ks.getId());
        if (Objects.nonNull(ks)) {
            return R.ok(HttpUtil.get(Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH_2 + feeitemId + "/" + patientCode + "/" + ks.getId()));
        }
        return R.fail("根据科室配置ID抓图失败，该科室配置信息不存在或者已被关闭！");
    }




    /**
     * 图片设置进报告
     *
     * @param param
     * @return
     */
    @PutMapping("/setInReport")
    @ApiOperation(value = "图片设置进报告", notes = "图片设置进报告")
    public R<Boolean> setInReport(@Validated @RequestBody SetInReportParam param) {
        return R.ok(pacsAbteilungService.setInReport(param));
    }
}
