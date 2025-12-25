package com.center.medical.report.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.MultipartFileUtil;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 报告赋码并重新生成报告
 */
@Component("reportCodingTask")
public class ReportCodingTask {

    private static final Logger log = LoggerFactory.getLogger(ReportCodingTask.class);
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

    /**
     * 报告赋码并重新生成报告
     *
     */
    public void start(){
        log.info("报告赋码并重新生成报告开始！");
        String path = sysConfigService.getSysConfigObject(Constants.REPORT_CODING_CONFIG, String.class);
        if (StringUtils.isEmpty(path)){
            log.info("请先配置报告赋码地址!");
        }
        log.info("报告赋码地址是:{}",path);
        File root_file = new File(path);// 本地文件夹
        if (!root_file.exists() || !root_file.isDirectory()) {
            log.info("报告赋码地址不存在!");
        }
        List<File> files = new ArrayList<>();
        List<String> patientCodes = new ArrayList<>();
        //读取目录下的图片文件
        for (File file : root_file.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".bmp")) {
                String patientCode = file.getName().substring(0, file.getName().lastIndexOf('.'));
                log.info("体检号上传赋码:{}",patientCode);
                //上传到对应的附件
                uploadAttFile(file,patientCode);
                files.add(file);
                patientCodes.add(patientCode);
            }
        }
        if(CollectionUtil.isEmpty(files)){
            log.info("目录下没有bmp文件!赋码定时任务结束");
        }else {
            //把原路径的文件放到备份路径上
            String time_str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String backup_path = path + "/" + "backup" +"/"+ time_str;// 备份路径
            File backup_direct = new File(backup_path);
            if (!backup_direct.exists()) {// 创建路径
                backup_direct.mkdirs();
            }
            log.info("报告赋码backup_path是:{}",backup_path);
            Path targetDir = Paths.get(backup_path);
            for (File file : files) {
                Path sourcePath = file.toPath();
                Path targetPath = targetDir.resolve(file.getName());
                try {
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("文件移动成功: {}",file.getName());
                } catch (IOException e) {
                    log.error("文件移动失败:{}",file.getName());
                    log.error("文件移动失败错误信息:{}",e.getMessage());
                }
            }



            for (String patientCode : patientCodes) {
                //生成报告
                IReportParam param = new IReportParam();
                param.setDh(1);
                param.setIsJy("0");
                param.setShowAllImage("0");
                param.setUsername("赋码定时任务");
                param.setPatientcode(Arrays.asList(patientCode));
                try {
                    iPersonalReportService.create(param);
                    log.info("赋码报告生成成功，体检号：" + patientCode );
                }catch (Exception e){
                    log.info("赋码报告生成失败，体检号：" + patientCode );
                    log.error(e.getMessage());
                }
            }
            log.info("报告赋码并重新生成报告结束！");
        }



    }

    /**
     * 上传附件
     * @param files
     */
    private void uploadAttFile(File files,String patientCode) {
        //删除之前的附件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>().eq(Attachment::getPatientcode,patientCode)
                .in(Attachment::getFileType,"赋码","赋码未生成报告"));
        //插入新附件
        SysBranch defaultBranch = iSysBranchService.getDefaultBranch();
        MultipartFile file = MultipartFileUtil.fileConvertMultipartFile(files);
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        Attachment attachment = new Attachment();
        String originalFilename = file.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        attachment.setFileType("赋码");
        attachment.setType(AttachmentType.IMAGE.value());
        attachment.setBranchId(defaultBranch.getBranchId());
        attachment.setCreatedate(new Date());
        attachment.setStatus(0);
        attachment.setPatientcode(patientCode);
        attachmentService.save(attachment);

        try {
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
        } catch (IOException e) {
            log.info("第三方报告上传保存失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());
    }


}
