package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.abteilung.bean.param.DmbShenHeParam;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.bmdService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.service.AttachmentConfigService;
import com.center.medical.service.AttachmentService;
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
import java.util.HashMap;
import java.util.List;

/**
 * 科室列表-骨密度检测(SectionResultMain)表控制层
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-骨密度检测")
@RequestMapping("abteilung/bmd")
public class BmdController extends BaseController {
    /**
     * 服务对象
     */
    private final bmdService bmdService;
    private final MapperFacade mapperFacade;
    private final AttachmentService attachmentService;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final AttachmentConfigService attachmentConfigService;
    private final ISysDeptService sysDeptService;
    private final DivisionService divisionService;


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/bmd")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "读卡", notes = "读卡，详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "gridSeclect", value = "是否是选择体检者列表（内科）"),
            @ApiImplicitParam(name = "autoFill", value = "是否自动补全"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R bmd(String patientcode, String gridSeclect, String autoFill, String ksId) {
        return R.ok(bmdService.bmd(patientcode, gridSeclect, autoFill, ksId));
    }


    /**
     * 获取骨密度审核数据
     *
     * @param patientcode
     * @param ksId
     * @return
     */
    @GetMapping("/getBmdCheckedData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取骨密度审核数据", notes = "获取骨密度审核数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R getBmdCheckedData(String patientcode, String ksId) {
        return R.ok(bmdService.getBmdCheckedData(patientcode, ksId));
    }


    /**
     * 查看图片
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @GetMapping("/viewImg")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "查看图片", notes = "查看图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R viewImg(String patientcode, String ksID) {
        //地址
        String realPath = FileTypePath.FICTITIOUS_PATH;
        //附件
        List<Attachment> attachments = attachmentService.list(new QueryWrapper<Attachment>().orderByAsc("file_type")
                .eq("patientCode", patientcode).eq("dep_id", ksID)
                .in("file_type", new String[]{"科室图像", "PACS", "PACSN"}));
        List<HashMap<String, Object>> pacImgs = new ArrayList<HashMap<String, Object>>();
        for (Attachment attachment : attachments) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String type = attachment.getFileType();
            map.put("id", attachment.getId());
            map.put("type", type);
            if ("PACS".equals(type)) {
                String path = ToolUtil.getPacsPath(attachment.getFilePath());
                map.put("path", path);
                map.put("name", path.substring(path.lastIndexOf("\\") + 1));
            } else if ("PACSN".equals(type)) {
                String path = bmdService.getPath(attachment);
                map.put("path", path);
                map.put("name", path.substring(path.lastIndexOf("\\") + 1));
                //手动上传或http上传的图片
            } else if (StringUtils.isNotEmpty(attachment.getConfigId())) {
                map.put("type", "PACSN");
                map.put("path", attachmentConfigService.getAttVisitPath(attachment));
                map.put("name", patientcode + "_" + attachment.getFileSort());
            } else {
                map.put("path", attachment.getFilePath());
                map.put("name", patientcode + "_" + attachment.getFileSort());
            }
            pacImgs.add(map);
        }
        return R.ok(pacImgs);
    }


    /**
     * 图像检测科室图片上传
     *
     * @param file
     * @param ksID
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadImg")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "图像检测科室图片上传", notes = "图像检测科室图片上传(未完成)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R uploadImg(@RequestParam("avatarfile") MultipartFile file, String ksID) throws Exception {
        return R.ok();
    }


    /**
     * 骨密度审核
     *
     * @param param
     * @return
     */
    @PostMapping("/dmbshenhe")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "骨密度审核", notes = "骨密度审核")
    public R dmbshenhe(@RequestBody DmbShenHeParam param) {
        Boolean b = bmdService.dmbshenhe(param);
        return R.toResult(b);
    }

    /**
     * 反审核(图像检查)
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @PutMapping("/caseReverse")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "反审核", notes = "反审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R caseReverse(String patientcode, String ksID) {
        Boolean b = divisionService.caseReverse(patientcode, ksID);
        return R.toResult(b);
    }

}

