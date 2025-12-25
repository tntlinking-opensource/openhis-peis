package com.center.medical.abteilung.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.service.ImageDivisionService;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
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
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 影像科室相关的操作接口
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "影像科室操作")
@RequestMapping("abteilung/image")
public class ImageDivisionController extends BaseController {

    private final AttachmentService attachmentService;
    private final MapperFacade mapperFacade;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final ImageDivisionService imageDivisionService;

    /**
     * 传图上传
     *
     * @return
     */
    @GetMapping("/upLoadFile")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "传图上传", notes = "传图上传", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "选择的ID", required = true),
    })
    @Log(title = "影像科室操作-传图上传", businessType = BusinessType.OTHER)
    public R upLoadFile(@RequestParam("id") String id) {
        return R.ok(imageDivisionService.uploadImage(id));
    }

    /**
     * 获取影像图片
     *
     * @param pageParamSimple 分页参数
     * @param ksID            科室ID
     * @param patientCode     体检号
     * @return 分页数据
     */
    @GetMapping("/getViewImg")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取影像图片", notes = "分页获取影像图片", position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ksID", value = "科室ID"),
            @ApiImplicitParam(name = "patientCode", value = "体检号")
    })
    public R<IPage<Attachment>> getViewImg(PageParamSimple pageParamSimple, String ksID, String patientCode) {
        if (StringUtils.isBlank(patientCode)) {
            throw new GlobalException("体检号不能为空！");
        }
        PageParam<Attachment> page = mapperFacade.map(pageParamSimple, PageParam.class);
        Attachment attachment = new Attachment();
        attachment.setPatientcode(patientCode);
        attachment.setDepId(ksID);
        LambdaQueryWrapper<Attachment> wrapper = new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientCode);
        if (StringUtils.isNotBlank(ksID)) {
            wrapper.eq(Attachment::getDepId, ksID);
        }
        if ("143".equals(ksID)) {
            wrapper.eq(Attachment::getInReport, 1);
        }
        PageParam<Attachment> List = this.attachmentService.page(page, wrapper);
        return R.ok(List);
    }


    /**
     * 上传图片（多个）
     */
    @PostMapping("/uploads")
    @ApiOperation(value = "上传图片", notes = "上传图片（多个）", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "图片文件列表"),
            @ApiImplicitParam(name = "ksID", value = "科室ID")
    })
    @Log(title = "影像科室操作-上传图片", businessType = BusinessType.OTHER)
    public R<MultUploadResultVo> uploads(List<MultipartFile> files, String ksID) {
        if (CollectionUtil.isEmpty(files)) {
            throw new GlobalException("请选择要上传的图片！");
        }
        // TODO:  configId用不到暂时设置为空
//        BaseAttachmentConfig config = baseAttachmentConfigService.getLatestConfig();
        String configId = null;
        // 上传文件路径
        String baseDir1 = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/" + iSysDeptService.getByDeptNo(ksID).getInputCode();
        List<String> urlList = new ArrayList<>();
        List<String> successList = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        StringBuilder resultMsg = new StringBuilder();
        // TODO wait 完善储存路径规则
        for (MultipartFile file : files) {
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
                Peispatient peispatient = peispatientService.getByPatientCode(pcode);
                if (Objects.isNull(peispatient)) {
                    resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败,不存在的体检号【" + pcode + "】</br>");
                    continue;
                }
//                String baseDir = baseDir1 + "/" + pcode;
                String baseDir = baseDir1;
                Attachment attachment = new Attachment();
                String extName = FileUtil.extName(fileName);
                attachment.setFileType("科室图像");
                attachment.setType(AttachmentType.IMAGE.value());
                attachment.setBranchId(SecurityUtils.getCId());
                attachment.setCreatedate(new Date());
                attachmentService.uploadFile(file, attachment, baseDir, extName, fileName, false);
                log.info("上传结果：{}、{}", attachment.getId(), attachment);

                // 保存
                attachment.setPatientcode(pcode);
                attachment.setShortCode(CodeUtil.getShortCodeByLong(pcode));
                attachment.setDepId(ksID);
                attachment.setFileSort(sort);
                attachment.setConfigId(configId);
                attachment.setFileType("科室图像");
                attachment.setMemo("科室手动上传");
                attachment.setInReport("143".equals(ksID) ? 1 : null);
                attachmentService.savePicture(attachment);

                successList.add(fileName);
                urlList.add(attachment.getFilePath());
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");

            } catch (Exception e) {
                failList.add(fileName);
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败," + e.getMessage() + "</br>");
                log.error("影像科室操作-上传图片，文件<font color='red'>" + fileName + "</font>上传失败：{}", e);
            }
        }
        MultUploadResultVo result = new MultUploadResultVo();
        result.setSuccessList(successList);
        result.setFailList(failList);
        result.setUrlList(urlList);
        result.setResultMsg(resultMsg.toString());
        return R.ok(result);
    }

    /**
     * 删除图片
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/imgs/remove")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除图片", notes = "删除图片")
    @ApiImplicitParam(name = "ids", value = "删除的文件记录ID集合")
    @Log(title = "影像科室操作-删除图片", businessType = BusinessType.DELETE)
    public R delete(@RequestParam List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new GlobalException("请选择要删除的图片！");
        }
        return R.toResult(this.attachmentService.removeByIds(ids));
    }



    /**
     * 上传数据
     *
     * @return
     */
    @GetMapping("/uploadData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "上传数据", notes = "上传数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "选择的ID", required = true),
    })
    @Log(title = "科室操作-上传数据", businessType = BusinessType.OTHER)
    public R uploadData(@RequestParam("id") String id) {
        return R.ok(imageDivisionService.uploadData(id));
    }
}

