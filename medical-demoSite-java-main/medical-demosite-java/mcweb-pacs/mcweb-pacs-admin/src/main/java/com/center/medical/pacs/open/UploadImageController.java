package com.center.medical.pacs.open;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.vo.DicomInfoVo;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.service.KsIpService;
import com.center.medical.pacs.bean.param.PacsAbteilungSaveScreenshotParam;
import com.center.medical.pacs.bean.vo.PacsAbteilungSaveScreenshotVo;
import com.center.medical.pacs.controller.DicomReceiver;
import com.center.medical.pacs.service.PacsAbteilungService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 影像科室传图业务
 */
@Slf4j
@RestController
@RequestMapping("/lan/images")
@Api(tags = "接收影像科室传图")
@RequiredArgsConstructor
@Validated
public class UploadImageController {
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final KsIpService ksIpService;
    private final PacsAbteilungService pacsAbteilungService;
    private final DicomReceiver dicomReceiver;

    /**
     * 执行传图
     *
     * @param ksIPId 科室配置信息ID
     * @return
     */
    @GetMapping("/doupload/{ksIPId}")
    @ApiOperation(value = "执行传图", notes = "执行传图", position = 1)
    @ApiImplicitParam(name = "ksIPId", value = "科室配置信息ID")
    public R<String> doupload(@PathVariable String ksIPId) {
        log.info("开始执行传图功能：{}", ksIPId);
        KsIp ks = ksIpService.getInfoById(ksIPId);
        if (Objects.nonNull(ks)) {
            return R.ok(HttpUtil.get(Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH + ks.getId()));
        }
        return R.fail();
    }

    /**
     * 执行读图
     *
     * @param patientCode 体检号
     * @param ksIpId      科室配置信息ID
     * @return
     */
    @GetMapping("/doread/{patientCode}/{ksIpId}")
    @ApiOperation(value = "执行读图", notes = "执行读图", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksIpId", value = "科室配置信息ID")
    })
    public R<String> doread(@PathVariable String patientCode, @PathVariable String ksIpId) {
        log.info("开始执行读图功能1：{}", ksIpId);
        KsIp ks = ksIpService.getInfoById(ksIpId);
        log.info("开始执行读图功能2：{},{}", patientCode, Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH_2 + patientCode + "/" + ks.getId());
        if (Objects.nonNull(ks)) {
            return R.ok(HttpUtil.get(Constants.HTTP + ks.getIp() + Constants.KS_UPLAOD_IMAGE_PATH_2 + patientCode + "/" + ks.getId()));
        }
        return R.fail();
    }

    /**
     * 接收图片
     *
     * @param file 图片文件
     * @param ksID 科室ID
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "接收图片", notes = "上传图片", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件"),
            @ApiImplicitParam(name = "ksID", value = "科室ID")
    })
//    @Log(title = "接收影像科室传图-上传图片", businessType = BusinessType.OTHER)
    public Boolean uploads(MultipartFile file, String ksID) {
        log.info("接收影像科室传图-上传图片:{}", ksID);
        if (Objects.isNull(file)) {
            return Boolean.FALSE; //R.fail(Boolean.FALSE, "请选择要上传的图片！");
        }
        String configId = null;
        // 上传文件路径
        SysDept dept = iSysDeptService.getByDeptNo(ksID);
        if (Objects.isNull(dept)) {
            log.error("接收图片失败，科室ID为【{}】的科室不存在！", ksID);
            return Boolean.FALSE;
        }
        String baseDir1 = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/" + iSysDeptService.getByDeptNo(ksID).getInputCode();
        // 完善储存路径规则
        String fileName = file.getOriginalFilename();
        String pcode = null;// 体检号
        String sort = null;// 序号
        try {
            if (fileName.indexOf("_") != -1 && fileName.indexOf(".") != -1) {
                pcode = fileName.substring(0, fileName.indexOf("_"));
                sort = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
            } else {// 只有一张时没有下划线
                pcode = fileName.substring(0, fileName.indexOf("."));
                sort = "1";
            }
            pcode = ToolUtil.patientCode(pcode, iSysBranchService.getBranchFlag(null)).toUpperCase();
            long count = peispatientService.count(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, pcode));
            if (count == 0) {
                String result = "文件<font color='red'>" + fileName + "</font>上传失败,不存在的体检号【" + pcode + "】</br>";
                log.error("接收影像科室传图-上传图片，上传失败：{}", result);
                return Boolean.FALSE; //R.fail(Boolean.FALSE, result);
            }
//            String baseDir = baseDir1 + "/" + pcode;
            String baseDir = baseDir1;
            Attachment attachment = new Attachment();
            String extName = FileUtil.extName(fileName);
            attachment.setFileType("科室图像");
            attachment.setType(AttachmentType.IMAGE.value());
            SysBranch branch = iSysBranchService.getDefaultBranch();
            String branchId = branch.getBranchId();
            attachment.setBranchId(branchId);
            attachment.setCreatedate(new Date());
            attachmentService.uploadFile(file, attachment, baseDir, extName, fileName, false);
            log.info("上传结果：{}", attachment);

            // 保存
            attachment.setPatientcode(pcode);
            attachment.setShortCode(CodeUtil.getShortCodeByLong(pcode));
            attachment.setDepId(ksID);
            attachment.setFileSort(sort);
            attachment.setConfigId(configId);
            attachment.setFileType("科室图像");
            attachment.setMemo("科室自动抓取上传");
            attachmentService.savePicture(attachment);
            return Boolean.TRUE; //R.ok(Boolean.TRUE, "文件<font color='green'>" + fileName + "</font>上传成功！</br>");

        } catch (Exception e) {
            log.error("接收影像科室传图-上传图片，文件<font color='red'>" + fileName + "</font>上传失败：{}", e);
            return Boolean.FALSE; //R.fail(Boolean.FALSE, "文件<font color='red'>" + fileName + "</font>上传失败," + e.getMessage() + "</br>");
        }
    }

    @PostMapping("/uploadScreenshot")
    @ApiOperation(value = "彩超截图上传", notes = "彩超截图上传")
    public Boolean uploadScreenshot(MultipartFile file, String feeitemId) {
        PacsAbteilungSaveScreenshotParam param = new PacsAbteilungSaveScreenshotParam();
        param.setFeeitemId(feeitemId);
        param.setFile(file);
        log.info("彩超截图上传:{}", param);
        PacsAbteilungSaveScreenshotVo result = pacsAbteilungService.uploadScreenshot(param);
        if (Objects.nonNull(result.getSrc())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @PostMapping("/uploadDicom")
    @ApiOperation(value = "上传DICOM", notes = "上传DICOM（多个）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "上传DICOM文件列表", required = true),
            @ApiImplicitParam(name = "feeitemId", value = "体检者收费项目ID")
    })
    public R<MultUploadResultVo> uploadDicom(MultipartFile[] files, String feeitemId) {
        if (files == null || files.length == 0) {
            return R.fail("文件不能为空");
        }
        return R.ok(dicomReceiver.upload(Arrays.asList(files), feeitemId));
    }


    @PostMapping("/resetDicom")
    @ApiOperation(value = "设置dicom信息", notes = "设置dicom信息")
    public R<List<Attachment>> setDicom() {
        List<Attachment> list = attachmentService.list(new LambdaQueryWrapper<Attachment>().isNotNull(Attachment::getDcmPath));

        for (Attachment item : list) {
            if (StringUtils.isNotBlank(item.getDcmPath())) {
                String dicomPath = StringUtils.substringBefore(item.getDcmPath(), ",");
                if (StringUtils.isNotBlank(dicomPath)) {
                    String dicomUrl = "http://XXX.XXX.XXX.XXX:8080" + dicomPath;
                    try {
                        URL url = new URL(dicomUrl);
                        InputStream inputStream = url.openStream();

                        DicomInputStream dicomInputStream = new DicomInputStream(inputStream);
                        Attributes attrs = dicomInputStream.readDataset(-1, Tag.PixelData);

                        //dicom图片信息
                        DicomInfoVo dicomInfoVo = new DicomInfoVo();
                        //窗位
                        double windowCenter = attrs.getDouble(Tag.WindowCenter, 0.0);
                        dicomInfoVo.setWindowCenter(windowCenter + "");
                        //窗位
                        double windowWidth = attrs.getDouble(Tag.WindowWidth, 0.0);
                        dicomInfoVo.setWindowWidth(windowWidth + "");
                        log.info("窗位:{}、窗位：{}", windowCenter, windowWidth);

                        String studyDate = DateUtil.format(DateUtil.parse(attrs.getString(Tag.ContentDate), "yyyyMMdd"), "yyyy-MM-dd");
                        dicomInfoVo.setContentDate(studyDate);
                        String studyTime = DateUtils.formatSeriesTime(attrs.getString(Tag.StudyTime));
                        dicomInfoVo.setContentTime(studyTime);
                        // dicomInfoVo.setModality(attrs.getString(Tag.Modality));
                        // dicomInfoVo.setManufacturer(attrs.getString(Tag.Manufacturer));
                        dicomInfoVo.setInstitutionName(attrs.getString(Tag.InstitutionName));
                        // dicomInfoVo.setSeriesDesc(attrs.getString(Tag.SeriesDescription));
                        // dicomInfoVo.setInstitutionalDeptName(attrs.getString(Tag.InstitutionalDepartmentName));
                        dicomInfoVo.setPatientName(attrs.getString(Tag.StudyDate));
                        dicomInfoVo.setPatientcode(attrs.getString(Tag.PatientID));
                        dicomInfoVo.setPatientSex(attrs.getString(Tag.PatientSex));
                        dicomInfoVo.setBodyPart(attrs.getString(Tag.BodyPartExamined));
                        dicomInfoVo.setExposureTime(attrs.getString(Tag.ExposureTime) + "ms");
                        // dicomInfoVo.setExposure(attrs.getString(Tag.Exposure));
                        // dicomInfoVo.setExposureIndex(attrs.getString(Tag.ExposureIndex));
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
                        String dicomInfo = JSONUtil.toJsonStr(dicomInfoVo);
                        item.setDcmPath(dicomPath + "," + dicomInfo);
                        dicomInputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        attachmentService.updateBatchById(list);
        return R.ok(list);
    }
}
