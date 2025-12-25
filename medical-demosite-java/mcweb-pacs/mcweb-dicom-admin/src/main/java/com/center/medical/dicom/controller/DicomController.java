package com.center.medical.dicom.controller;

import cn.hutool.core.io.FileUtil;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.DicomInfoVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.uuid.UUID;
import com.center.medical.dicom.service.DicomService;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.bean.model.DeployVersion;
import com.center.medical.system.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xhp
 * @since 2023-04-11 13:54
 */
@RestController
@RequestMapping("/open/api/dicom")
@Api(tags = "dicom")
@RequiredArgsConstructor
@Validated
@Slf4j
public class DicomController extends BaseController {
    private final AttachmentService attachmentService;
    private final DicomService dicomService;
    private final SystemConfig systemConfig;

    @PostMapping("/test")
    @ApiOperation(value = "测试dicom", notes = "测试dicom")
    public R<DeployVersion> test() throws IOException {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/test";
        Attachment attachment = new Attachment();
        attachment.setBw("enName");
        attachment.setInReport(0);
        attachment.setPatientcode("patientcode");
        attachment.setShortCode(911111111);
        attachment.setFeeItemId("feeitemId");
        attachment.setPacsFeeItemId("feeitemId");
        attachment.setDepId("deptNo");
        attachment.setBranchId("branch.getBranchId()");
        attachment.setType(1);
        attachment.setStatus(0);
        attachment.setCreatedate(new Date());
        attachment.setFileType("PACS");
        attachment.setMemo("dicom接收");
        log.info("文件路径：{}", System.getProperty("user.dir") + "/images/1704417564035_1.2.840.113704.7.1.1.9836.1704417208.1.jpg");

        File file = new File(System.getProperty("user.dir") + "/images/1704417564035_1.2.840.113704.7.1.1.9836.1704417208.1.jpg");
        log.info("是否存在文件：{}、{}", file.isFile(), file.exists());
        //上传dcm文件
        String extName = FileUtil.extName(file.getName());
        attachmentService.uploadFile(file, attachment, baseDir, extName, "patientcode/" + file.getName(), false, true);

        return R.ok();
    }


    @PostMapping("/uploadDicom")
    @ApiOperation(value = "上传DICOM", notes = "上传DICOM（多个）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "上传DICOM文件列表", required = true),
    })
    public R<List<String>> uploadDicom(MultipartFile[] files, String feeitemId) {
        log.info("收费项目ID：{}", feeitemId);
        if (files == null || files.length == 0) {
            return R.fail("文件不能为空");
        }
        //临时保存到本地根目录
        String baseDir = System.getProperty("user.dir");
        String folderPath = baseDir + File.separator + "dicomUpload" + File.separator + UUID.randomUUID();
        File folder = new File(folderPath);
        folder.mkdirs();
        List<String> dicomList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            //如果文件名包含中文则将中文转化成拼音
            String dcmName = StringUtils.delSpecialChar(StringUtils.convertToPinyin(fileName));
            //dcm文件临时保存位置
            File dcmFile = new File(folderPath, dcmName);
            try {
                file.transferTo(dcmFile);
                Attachment attachment = dicomService.saveDicomPath(dcmFile, feeitemId);
                if (Objects.nonNull(attachment)) {
                    dicomList.add(attachment.getFilePath());
                }
            } catch (IOException e) {
                log.error("上传失败：{}、{}", fileName, e);
            }
        }
        return R.ok(dicomList);
    }





    @PostMapping("/classification")
    @ApiOperation(value = "分类dicom文件", notes = "分类dicom文件")
    public R<Boolean> classification(String folderPath) throws IOException {
        Boolean b = dicomService.classification(folderPath);
        return R.ok(b);
    }





    @PostMapping("/readInfo")
    @ApiOperation(value = "读取dicom文件的信息", notes = "读取dicom文件的信息")
    public R<DicomInfoVo> readInfo(String folderPath) throws IOException {
        DicomInfoVo vo = dicomService.readInfo(folderPath);
        return R.ok(vo);
    }



}
