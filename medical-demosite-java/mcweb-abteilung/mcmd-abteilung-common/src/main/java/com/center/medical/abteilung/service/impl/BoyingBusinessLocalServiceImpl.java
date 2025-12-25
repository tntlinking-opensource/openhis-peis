package com.center.medical.abteilung.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.service.BoyingBusinessLocalService;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.PdfUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.Base64ToMultipartFile;
import com.center.medical.common.utils.file.MultipartFileUtil;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import com.center.medical.outside.constant.BoyingConstant;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeisStateService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: 路飞船长
 * @date: 2025/6/19 10:12
 * @description:
 */
@Slf4j
@RequiredArgsConstructor
@Service("boyingBusinessLocalService")
public class BoyingBusinessLocalServiceImpl extends ServiceImpl<SectionResultMainMapper, SectionResultMain> implements BoyingBusinessLocalService {
    private final ISysBranchService iSysBranchService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysDeptService iSysDeptService;
    private final PeispatientMapper peispatientMapper;
    private final ISysUserService iSysUserService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final DescribeMapper describeMapper;

    /**
     * 上传图片文件
     * @return
     */
    @Override
    public Attachment uploadFile(String base64, String patientcode, String fileName) throws IOException {

        Attachment attachment = new Attachment();
        attachment.setPatientcode(patientcode);

        //pdf转jpg.base64
        base64="data:image/jpeg;base64,"+ base64;
        //base64转MultipartFile
        Base64ToMultipartFile file = MultipartFileUtil.base64ConvertMultipartFile(base64);
        String extName = file.getExtension();

        String baseDir1 = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/" + iSysDeptService.getByDeptNo(BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM).getInputCode();
//        String baseDir = baseDir1 + "/" + patientcode;
        attachment.setFileType("科室图像");
        attachment.setType(AttachmentType.IMAGE.value());
        SysBranch branch = iSysBranchService.getDefaultBranch();
        String branchId = branch.getBranchId();
        attachment.setBranchId(branchId);
        attachment.setCreatedate(new Date());
        log.info("博英心电图-开始上传：{}、{}", baseDir1, extName);
        attachmentService.uploadFile(file, attachment, baseDir1, extName, fileName, false);
        log.info("博英心电图-上传结果：{}、{}", attachment.getId(), attachment);

        attachment.setDepId(BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM);
        attachment.setFileSort("1");
        attachment.setFileType("科室图像");
        attachment.setMemo(BoyingConstant.ATTACHMENT_MEMO);
        attachment.setShortCode(CodeUtil.getShortCodeByLong(patientcode));

        return attachment;
    }

    /**
     * 接口由医院HIS或集成平台提供服务，心电信息管理系统发起计费、归档报告请求，HIS需接受请求执行对应的操作。
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addResult(BoyingWriteReportParam param){
        String patientcode = param.getPatientID();
        if(StrUtil.isEmpty(patientcode)){
            throw new ServiceException("OrderID不能为空");
        }
        //判断体检号是否存在
        Peispatient peispatient;
        //判断是否是8位
        if(patientcode.length()!=8){
            peispatient = peispatientMapper.getByPatientCode(patientcode);
        }else {
            peispatient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient> ()
                    .eq(Peispatient::getShortCode,patientcode)
                    .eq(Peispatient::getFPaused, 0)
                    .eq(Peispatient::getFRegistered, 1)
            );
        }
        //已总检不能再覆盖
        if((Objects.nonNull(peispatient.getJktjzt()) && peispatient.getJktjzt()>0) || (Objects.nonNull(peispatient.getZytjzt()) && peispatient.getZytjzt()>0) ){
            throw new ServiceException("博英心电图OrderID【"+param.getOrderId()+"】已总检完成，不能再覆盖");
        }

        if(Objects.isNull(peispatient)){
            throw new ServiceException("博英心电图OrderID【"+param.getOrderId()+"】在体检系统中不存在");
        }

        //查询科室小结
        SectionResultMain sectionResultMain=baseMapper.selectOne(
                new LambdaQueryWrapper<SectionResultMain>()
                        .eq(SectionResultMain::getPatientcode,patientcode)
                        .eq(SectionResultMain::getDepId,BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM)
        );
        boolean isInsert=sectionResultMain==null;
        //如果已审核，不覆盖
//        if(!isInsert && sectionResultMain.getIsAudit()!=null && sectionResultMain.getIsAudit().intValue()==1){
//            throw new ServiceException("OrderID【"+param.getOrderId()+"】已在体检系统中完成审核，不能覆盖。");
//        }
        List<Attachment> attachments=new ArrayList<>();
        log.info("博英心电图-报告pdf地址：{}",param.getReportUrl());
        try {
            //报告pdf地址
            String reportUrlStrs=param.getReportUrl();
            if (StrUtil.isEmpty(reportUrlStrs)){
                log.info("博英心电图-报告pdf地址为空");
            }else {
                String[] reportUrls = reportUrlStrs.split(",");
                for (String reportUrl : reportUrls) {
                    if (StrUtil.isEmpty(reportUrl)) continue;
                    String newReportUrl = reportUrl.replaceAll("\\\\", "/");
                    reportUrl = reportUrl.substring(0, reportUrl.lastIndexOf("/")) + URLEncoder.encode(reportUrl.substring(reportUrl.lastIndexOf("/")), "UTF-8");
                    if(StrUtil.isEmpty(reportUrl)) {
                        throw new ServiceException("报告pdf地址不存在！");
                    }
                    log.info("博英心电图-开始转化：{}", reportUrl);
                    List<String> pdfToBase64Dto = PdfUtil.pdfToBase64(reportUrl);
                    log.info("博英心电图-图片：{}", pdfToBase64Dto.size());
                    // 获取reportUrl不包含后缀名的文件名
                    String enName = newReportUrl.substring(newReportUrl.lastIndexOf("/") + 1, newReportUrl.lastIndexOf("."));
                    // enName只保留数字、字母、-、_这些字符
                    enName = StringUtils.delSpecialChar2(enName);
                    String imgName = patientcode + "_" + StringUtils.convertToPinyin(enName);
                    int i = 1;
                    for (String base64 : pdfToBase64Dto) {
                        log.info("博英心电图-开始上传图片imgName：{}", imgName+ i + ".jpg");
                        Attachment attachment = uploadFile(base64, patientcode, imgName+ i + ".jpg");
                        log.info("博英心电图-上传成功图片：{}", attachment);
                        attachments.add(attachment);
                        i++;
                    }
                }

            }

        } catch (Exception e) {
            throw new ServiceException("博英心电图保存失败！");
        }
        log.info("博英心电图-保存图片1：{}", attachments);
        //保存图片
        if(CollUtil.isNotEmpty(attachments)){
            attachmentService.remove(
                    new LambdaQueryWrapper<Attachment>()
                            .eq(Attachment::getPatientcode,patientcode)
                            .eq(Attachment::getMemo,BoyingConstant.ATTACHMENT_MEMO)
                            .eq(Attachment::getDepId,BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM)
            );
            for (Attachment attachment : attachments) {
                if (StringUtils.isNotBlank(attachment.getFilePath())){
                    log.info("博英心电图-保存图片2：{}", attachment.getFilePath());
                    attachmentService.savePicture(attachment);
                }else {
                    log.info("博英心电图-保存图片3：{}", attachment.getFilePath());
                }
            }
        }
        log.info("博英心电图-保存小结：{}", isInsert);
        //保存小结
        if(isInsert){
            sectionResultMain=new SectionResultMain();
            sectionResultMain.setDepId(BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM);
            sectionResultMain.setPatientcode(patientcode);
            sectionResultMain.setShortCode(peispatient.getShortCode());
        }
        sectionResultMain.setIsFinish(0);
        String conclusion=param.getConclusion();
        //将conclusion中的英文问号替换成中文问号
        conclusion = conclusion.replace("?", "？");

        //查询检查医生
        SysUser sysUser = iSysUserService.selectUserByUserName("博英-"+param.getReportDoc());

        if (Objects.nonNull(sysUser)){
            sectionResultMain.setIsAudit(1);
            sectionResultMain.setAuditId(sysUser.getUserNo());
            sectionResultMain.setAuditTime(new Date());
            sectionResultMain.setAuditName(sysUser.getUserName());
            sectionResultMain.setWriteId(sysUser.getUserNo());
            sectionResultMain.setWriteTime(new Date());
            sectionResultMain.setWriteName(sysUser.getUserName());
            sectionResultMain.setRummagerId(sysUser.getUserNo());
            sectionResultMain.setRummagerTime(new Date());
            sectionResultMain.setRummagerName(sysUser.getUserName());
        } else {
            log.error("博英心电图小结保存失败，系统未匹配到检查医师：" + param.getReportDoc());
            sectionResultMain.setIsAudit(0);
            conclusion = conclusion + "【系统未匹配到检查医师：" + param.getReportDoc() + "，请让运维人员联系博英心电图厂家提供这个医师的签名图片，然后维护这个医师账号，维护完后再重新获取结果】";
        }

        if (peispatient.getIdExamtype().equals("0")){
            sectionResultMain.setConclusions(conclusion);
        } else {
            sectionResultMain.setConclusions(conclusion);
            sectionResultMain.setZyConclusions(conclusion);
        }
        if(isInsert){
            baseMapper.insert(sectionResultMain);
        }else{
            baseMapper.updateById(sectionResultMain);
        }
        // 插入describe表记录
        Describe describe = new Describe();
        describe.setPatientcode(patientcode);
        describe.setItemId("276");
        describe.setItemName("心电图");
        describe.setInspectDescribe(conclusion);
        describe.setSignList(conclusion);
        describe.setCreatedate(new Date());
        describe.setDepId(BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM);
        describe.setTjlx(peispatient.getIdExamtype().equals("0")?0:1);
        describe.setShortCode(peispatient.getShortCode());
        describe.setDepDescription("心电图室");
        Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.selectByBoying(patientcode, BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM);
        describe.setFeeName(peispatientfeeitem.getExamfeeitemName());
        describe.setFeeId(peispatientfeeitem.getIdExamfeeitem());
        describeMapper.insert(describe);


        if (Objects.nonNull(sysUser)){
            log.info("博英心电图-将收费项目设置成已检：{}", sysUser);
            //将收费项目设置成已检
            //体检者收费项目表
            Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectByBoying(peispatient.getPatientcode(),BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM);

            log.info("博英心电图-体检者收费项目表：{}", feeitem);
            if (feeitem != null) {
                if (feeitem.getChangeItem() != null && feeitem.getChangeItem() == 1) {
                    throw new ServiceException("博英心电图结果写入失败，收费项目" + feeitem.getExamfeeitemName() + "已退项。");
                }
                if (feeitem.getSfjj() != null && feeitem.getSfjj() == 1) {
                    throw new ServiceException("博英心电图结果写入失败，收费项目" + feeitem.getExamfeeitemName() + "已拒检。");
                }
                if (feeitem.getFGiveup() != null && feeitem.getFGiveup() == 1) {
                    throw new ServiceException("博英心电图结果写入失败，收费项目" + feeitem.getExamfeeitemName() + "已弃检。");
                }
                feeitem.setFExaminated(1);
                feeitem.setExaminatetime(new Date());
                peispatientfeeitemMapper.updateById(feeitem);
            }

            //修改全检标志、开始检查标志
            if (outsideMainService.getAllSfxmtzjStatus(patientcode)) {
                peispatient.setFReadytofinal(1);//0:已备单 1:分检完成
                peispatient.setReadytofinalDate(new Date());
                peisStateService.setScbs(peispatient.getPatientcode(), 0);
                //根据获inputCode取体检者收费项目表
                List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
                for (Peispatientfeeitem other : other_items) {
                    //设置未关联科室项目为已检
                    other.setFExaminated(1);
                    peispatientfeeitemMapper.updateById(other);
                }
            }
            log.info("博英心电图-体检者收费项目表：{}", peispatient);
            peispatient.setFExamstarted(1);
            peispatientMapper.updateById(peispatient);

        }
        //返回结果
    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        String reportUrl = "http://121.42.142.245:8081/Uploads/UPLOAD/2025\\10\\20251015081710909-胡志刚.pdf";
        log.info("reportUrl：{}", reportUrl);

        String newReportUrl = reportUrl.replaceAll("\\\\", "/");
        log.info("newReportUrl：{}", reportUrl);

        // 获取reportUrl不包含后缀名的文件名
        String imgName = newReportUrl.substring(newReportUrl.lastIndexOf("/") + 1, newReportUrl.lastIndexOf("."));
        log.info("imgName：{}", imgName);
        imgName = "111111" + "_" + StringUtils.convertToPinyin(imgName);
        log.info("imgName：{}", imgName);
        System.out.println(imgName);


        List<String> pdfToBase64Dto = PdfUtil.pdfToBase64("http://121.42.142.245:8081/Uploads/UPLOAD%2F2025%5C11%5C20251108091720922-%E5%AD%9F%E7%A7%80%E6%95%8F.pdf");
        log.info("pdfToBase64Dto：{}", pdfToBase64Dto.size());



    }
}


