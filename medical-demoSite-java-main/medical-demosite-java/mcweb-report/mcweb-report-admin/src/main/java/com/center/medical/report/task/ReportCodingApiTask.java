package com.center.medical.report.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.report.bean.param.IReportParam;
import com.center.medical.report.service.IPersonalReportService;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 通过api获取赋码  重新生成报告
 */
@Component("reportCodingApiTask")
public class ReportCodingApiTask {

    private static final Logger log = LoggerFactory.getLogger(ReportCodingApiTask.class);
    @Resource
    private IPersonalReportService iPersonalReportService;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private AttachmentService attachmentService;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private ISysBranchService iSysBranchService;

    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 报告赋码并重新生成报告
     *
     */
    public void start(){
        log.info("api报告赋码并重新生成报告开始！");
        String url = iSysConfigService.getDomain().getProfessionDomain() + "/Qjk/reportCoding";
        String string = HttpUtils.sendGet(url, null);
        log.info("api报告赋码请求结果:{}",string);

        List<Attachment> list = attachmentService.list(new LambdaQueryWrapper<Attachment>().eq(Attachment::getFileType, "赋码未生成报告"));
        log.info("api报告赋码查询到:{}",list.size());
        List<String> patientCodes = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)){
            for (Attachment attachment : list) {
                String patientcode = attachment.getPatientcode();
                patientCodes.add(patientcode);
                attachment.setFileType("赋码");
            }
        }
        attachmentService.updateBatchById(list);

        //生成报告
        if (CollectionUtil.isNotEmpty(patientCodes)){
            log.info("api报告赋码开始生成报告");
            for (String patientCode : patientCodes) {
                IReportParam param = new IReportParam();
                param.setDh(1);
                param.setIsJy("0");
                param.setShowAllImage("0");
                param.setUsername("赋码定时任务");
                param.setPatientcode(Arrays.asList(patientCode));
                param.setSkipVerification(Boolean.TRUE);
                try {
                    iPersonalReportService.create(param);
                    log.info("赋码报告生成成功，体检号：" + patientCode );
                }catch (Exception e){
                    log.info("赋码报告生成失败，体检号：" + patientCode );
                    log.error(e.getMessage());
                }
            }
        }

        log.info("报告赋码并重新生成报告结束！");
    }

    /**
     * 上传附件
     * @param files
     */
    private void uploadAttFile(MultipartFile files,String patientCode) {
        //删除之前的附件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>().eq(Attachment::getPatientcode,patientCode)
                .eq(Attachment::getFileType,"赋码"));
        //插入新附件
        SysBranch defaultBranch = iSysBranchService.getDefaultBranch();
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        Attachment attachment = new Attachment();
        String originalFilename = files.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        attachment.setFileType("赋码");
        attachment.setType(AttachmentType.IMAGE.value());
        attachment.setBranchId(defaultBranch.getBranchId());
        attachment.setCreatedate(new Date());
        attachment.setStatus(0);
        attachment.setPatientcode(patientCode);
        attachmentService.save(attachment);

        try {
            attachmentService.uploadFile(files, attachment, baseDir, extName, null, true);
        } catch (IOException e) {
            log.info("第三方报告上传保存失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());
    }


}
