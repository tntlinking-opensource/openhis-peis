package com.center.medical.pacs.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.pacs.bean.param.PacsGetJlcGridParam;
import com.center.medical.pacs.bean.vo.PacsGetJlcGridVo;
import com.center.medical.pacs.service.PacsCsharpService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * QT体检者表(Peispatient)接口
 *
 * @author ay
 * @since 2023-10-08 09:26:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "彩超程序接口2")
@RequestMapping("/open/api/pacs/pacsCsharpNew")
public class PacsCsharpNewController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsCsharpService pacsCsharpService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;
    private final ISysUserService iSysUserService;
    public static String pacs="PACS";//系统参数type
    public static final String ksID="143";
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final SysBranchMapper sysBranchMapper;

    /**
     * 监察人、审核人下拉数据
     * @return
     */
    @GetMapping("/getConfig")
    @ApiOperation(value = "获取系统配置", notes = "获取系统配置")
    public R getConfig() {
        Map<String,Object> config = new HashMap<String, Object>();
        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        if (StrUtil.isNotEmpty(pacsConfig)) {
            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
            String imgConf = jsonObject.getStr("imageType");
            config.put("imageType", StringUtils.isEmpty(imgConf)?"0":"1");
            config.put("ispacs", pacsCsharpService.getDictionaryStatusByType(pacs)? "1" : "0");
            config.put("users", iSysUserService.getCcUsers(sysBranchMapper.getDefaultCid(),ksID));
        }
        return R.ok(config);
    }

    /**
     * 结论词表格数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getJlcGridData")
    @ApiOperation(value = "结论词表格数据", notes = "结论词表格数据")
    public R<List<PacsGetJlcGridVo>> getJlcGridData(PacsGetJlcGridParam param) {
        List<PacsGetJlcGridVo> jlcGridData = pacsCsharpService.getJlcGridData(param);
        for (PacsGetJlcGridVo vo : jlcGridData) {
            String isSelected = vo.getIsSelected();
            if(StringUtils.isNotEmpty(isSelected) && "1".equals(isSelected)) {
                vo.setIsSelected("true");
            }else {
                vo.setIsSelected("false");
            }
        }
        return R.ok(jlcGridData);
    }



    /**
     * 通过体检号获取图片
     *
     * @param patientcode     查询条件
     * @return 所有数据
     */
    @GetMapping("/getPictureByPatientcode")
    @ApiOperation(value = "通过体检号获取图片", notes = "通过体检号获取图片")
    public R<String> getPictureByPatientcode(String patientcode) throws IOException {
        String picture = peispatientPhotoService.getPictureByCode(patientcode);
        String base64Image = "";
        if (StringUtils.isNotEmpty(picture)){
            Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
            //线上线下地址
            String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
            String imageUrl = prefix + picture;
            // 打开图片连接
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 读取图片数据
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            byte[] data = outputStream.toByteArray();

            // 关闭输入输出流和连接
            inputStream.close();
            outputStream.close();
            connection.disconnect();

            // 使用 Base64 编码
            base64Image = Base64.getEncoder().encodeToString(data);
        }

        return R.ok(base64Image);
    }



    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ApiOperation(value = "通用上传请求（单个）", notes = "通用上传请求（单个）")
    @ApiImplicitParam(name = "file", value = "文件资源")
    public R<String> uploadFile(MultipartFile file,String patientcode) {
        log.info("通用上传请求");
        String extName = FileUtil.extName(file.getOriginalFilename());
        String fileName = FileUtil.mainName(file.getOriginalFilename());
        try {
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value());
            Attachment attachment = new Attachment();
            attachment.setFileType(extName);
            //TODO wait等待完善文件类型判断
            attachment.setType(2);
            attachment.setBranchId(sysBranchMapper.getDefaultCid());
            attachment.setCreatedate(new Date());
            attachment.setPatientcode(patientcode);
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
            log.info("上传结果：{}、{}", attachment.getId(), attachment);
            return R.ok(attachment.getFilePath());
        } catch (Exception e) {
            log.error("上传失败：{}、{}", fileName, e);
            return R.fail(e.getMessage());
        }
    }



}
