package com.center.medical.report.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.dao.FxDetectionzyMapper;
import com.center.medical.dao.FxHarmMapper;
import com.center.medical.dao.FxPersonnelviewMapper;
import com.center.medical.dao.FxReviewInfoMapper;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.StencilMaintain;
import com.center.medical.data.dao.BasconclusionMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.StencilMaintainMapper;
import com.center.medical.data.service.StencilMaintainService;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.model.SamplePerson;
import com.center.medical.report.bean.param.CReprotNewDParam;
import com.center.medical.report.bean.param.UploadWordParam;
import com.center.medical.report.bean.utils.DeleteFileUtil;
import com.center.medical.report.bean.utils.MargeDoc;
import com.center.medical.report.bean.utils.WordTool;
import com.center.medical.report.bean.vo.CReprotNewDVo;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.dao.BallCheckReportMapper;
import com.center.medical.report.dao.GroupReportMapper;
import com.center.medical.report.dao.SamplePersonMapper;
import com.center.medical.report.service.BallCheckReportService;
import com.center.medical.report.service.GroupReportService;
import com.center.medical.report.service.ReportConfigService;
import com.center.medical.report.utils.WordConvertPDF;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.dao.CreateorderMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.service.*;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
@Slf4j
@Service("groupReportService")
@RequiredArgsConstructor
public class GroupReportServiceImpl extends ServiceImpl<GroupReportMapper, Report> implements GroupReportService {

    private final GroupReportMapper groupReportMapper;
    private final StencilMaintainMapper stencilMaintainMapper;
    private final BallCheckReportMapper ballCheckReportMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final CreateorderMapper createorderMapper;
    private final FxCompletionService fxCompletionService;
    private final FxItemscheckService fxItemscheckService;
    private final FxDetectionService fxDetectionService;
    private final BasconclusionMapper basconclusionMapper;
    private final SamplePersonMapper samplePersonMapper;
    private final HarmMapper harmMapper;
    private final FxReviewInfoMapper fxReviewInfoMapper;
    private final FxDetectionzyMapper fxDetectionzyMapper;
    private final FxHarmMapper fxHarmMapper;
    private final FxPersonnelviewMapper fxPersonnelviewMapper;
    private final BallCheckReportService ballCheckReportService;
    private final ReportContentService reportContentService;
    private final PeispatientService peispatientService;
    private final SysBranchMapper sysBranchMapper;
    private final ISysConfigService sysConfigService;
    private final ReportConfigService reportConfigService;
    private final StencilMaintainService stencilMaintainService;
    private final ISysConfigService iSysConfigService;
    private WordTool worTool;// word工具类
    private XWPFDocument pfddoc;// word模板
    private final SystemConfig systemConfig;
    private final AttachmentService attachmentService;
    @Autowired
    private LoadProperties loadProperties;

    /**
     * 综合分析生成报告
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NEVER, noRollbackFor = RuntimeException.class)
    public Boolean createReprotNewD(CReprotNewDParam param) {
        //修改检报告主表状态
        BallCheckReport ball = ballCheckReportMapper.getInfoById(param.getAnalyzeId());
        if (ball.getStatus() == 7){
            throw new ServiceException("当前报告主检已审,不能上传修改！");
        }
        CReprotNewDVo vo = new CReprotNewDVo();
        //取样本分析的第一个分中心id
        String fzxid = StringUtils.isNotEmpty(ball.getFzxid()) ? ball.getFzxid().split(",")[0]:sysBranchMapper.getDefaultBranch().getBranchId();
        String content = reportConfigService.getBranchConfig(fzxid);
        if (StringUtils.isEmpty(content)){
            throw new ServiceException("请先维护报告配置！");
        }
        SysBranch sysBranch = sysBranchMapper.getByBranchId(fzxid);
        ReportConfigVo reportConfigVo = JSONObject.parseObject(content, ReportConfigVo.class);
        param.setReportConfigVo(reportConfigVo);
        param.setSysBranch(sysBranch);

        //获取报告模板
        StencilMaintain stencilMaintain = stencilMaintainService.getOne(new QueryWrapper<StencilMaintain>()
                .eq("model_type", 1)
                .eq("is_default", 0)
                .eq("suitable_type", Integer.parseInt(param.getDh()))
                .eq("is_delete", 1));
        if (ObjectUtils.isEmpty(stencilMaintain) || StringUtils.isEmpty(stencilMaintain.getModelUrls())) {
            throw new ServiceException("请先维护团检报告模板！");
        }
        String[] modelUrls = stencilMaintain.getModelUrls().split(";");
        param.setModelUrls(modelUrls);
        //生成报告
        if ("0".equals(param.getDh())) {
            //健康团检报告
            vo = createReprotNew(param);
        }else {
            //职业团检报告
            vo = createReprotDisease(param);
        }
//        //保存json数据
//        String jsonString = JSON.toJSONString(vo);
//        reportContentService.createReportContent(jsonString, 4, null, param.getDh(), param.getAnalyzeId(), null, null, null);



        if (ball != null) {
            //上传报告
            if (!StringUtils.isEmpty(vo.getWordUrl())) {
                String url = uploadReport(vo.getWordUrl(),ball,sysBranch);
                ball.setWordUrl(url);
            }
            if (!StringUtils.isEmpty(vo.getPdfUrl())) {
                String url = uploadReport(vo.getPdfUrl(),ball,sysBranch);
                ball.setPdfUrl(url);
            }
            ball.setGenerateName(param.getGenerateName());
            ball.setProgress(100.0);
            ball.setGenerateDate(new Date());
            ball.setStatus(5);
            ball.setScbs(0);
            ball.setCreateStatus(2);
            ballCheckReportService.updateById(ball);
            System.out.println("团检报告生成成功：" + param.getOrderId());
        }
        return Boolean.TRUE;
    }




    /**
     * 上传报告
     * @param wordUrl
     * @return
     */
    private String uploadReport(String wordUrl,BallCheckReport ball,SysBranch sysBranch) {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        File file = new File(wordUrl);
        String originalFilename = file.getName();
        String extName = FileUtil.extName(originalFilename);

        //删除旧文件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getFileType, "团检报告")
                .eq(Attachment::getType,AttachmentType.FILE.value())
                .eq(Attachment::getPatientcode,ball.getId())
                .eq(Attachment::getMemo,extName)
        );


        //添加新文件
        Attachment attachment = new Attachment();
        attachment.setFileType("团检报告");
        attachment.setType(AttachmentType.FILE.value());
        attachment.setBranchId(sysBranch.getBranchId());
        attachment.setCreatedate(new Date());
        attachment.setStatus(0);
        attachment.setPatientcode(ball.getId());
        attachment.setMemo(extName);
        attachmentService.save(attachment);
        try {
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true,true);
        } catch (IOException e) {
            throw new ServiceException("第三方报告上传保存失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());

        return attachment.getFilePath();
    }

    /**
     * 职业团检报告
     * @param param
     * @return
     */
    private CReprotNewDVo createReprotDisease(CReprotNewDParam param) {

        CReprotNewDVo vo = new CReprotNewDVo();

        String[] modelUrls = param.getModelUrls();
        String orderId = param.getOrderId();
        String analyzeId = param.getAnalyzeId();
        ReportConfigVo reportConfigVo = param.getReportConfigVo();
        SysBranch sysBranch = param.getSysBranch();

        String template_0 = null;
        String template_1 = null;
        String template_2 = null;
        String list_1 = null;
        String list_2 = null;
        String list_3 = null;
        String list_4 = null;
        String list_5 = null;
        String list_6 = null;
        String list_7 = null;
        String list_empty = null;
        String endpage = null;

        BallCheckReport ball = ballCheckReportMapper.getInfoById(param.getAnalyzeId());
        String path = System.getProperty("user.dir") + "/temp/" ;


        for (int i = 0; i < modelUrls.length; i++) {
            String modelUrl = modelUrls[i];// 模板路径
            String str = null;
            if (modelUrl.indexOf("list_") != -1) {
                str = modelUrl.substring(modelUrl.indexOf("list_"),
                        modelUrl.lastIndexOf("."));
            } else if (modelUrl.indexOf("template") != -1) {
                str = modelUrl.substring(modelUrl.indexOf("template"),
                        modelUrl.lastIndexOf("."));
            } else if (modelUrl.indexOf("endpage") != -1) {
                str = modelUrl.substring(modelUrl.indexOf("endpage"),
                        modelUrl.lastIndexOf("."));
            }
            if (!StringUtils.isEmpty(str)) {
                if (str.equals("template_0")) {
                    template_0 = path + modelUrl;
                    continue;
                } else if (str.equals("template_1")) {
                    template_1 = path + modelUrl;
                    continue;
                } else if (str.equals("template_2")) {
                    template_2 = path + modelUrl;
                    continue;
                } else if (str.equals("list_1")) {
                    list_1 = path + modelUrl;
                    continue;
                } else if (str.equals("list_2")) {
                    list_2 = path + modelUrl;
                    continue;
                } else if (str.equals("list_3")) {
                    list_3 = path + modelUrl;
                    continue;
                } else if (str.equals("list_4")) {
                    list_4 = path + modelUrl;
                    continue;
                } else if (str.equals("list_5")) {
                    list_5 = path + modelUrl;
                    continue;
                } else if (str.equals("list_6")) {
                    list_6 = path + modelUrl;
                    continue;
                } else if (str.equals("list_7")) {
                    list_7 = path + modelUrl;
                    continue;
                } else if (str.equals("endpage")) {
                    endpage = path + modelUrl;
                    continue;
                } else if (str.equals("list_empty")) {
                    list_empty = path + modelUrl;
                    continue;
                }
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<String> expUrls = new ArrayList<String>();
        String dstr = dateFormat.format(new Date());
        String url = path + "file/wordexp/group_medical/" + dstr + "/"
                + orderId + "/";
        String saveUrl = path + "file/wordexp/group_medical/" + dstr + "/" + orderId
                + "/";

        File dir = new File(url);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] taleList = { list_1, list_2, list_3, list_4, list_5, list_6 };
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 精确到毫秒


        if (!StringUtils.isEmpty(template_0)) {// 导出template_0 模板内容
            String expName = format.format(new Date()) + "_template_0.docx";
            String expurl = url + expName;
            List<String[]> booleanAndTable = null;
            booleanAndTable = template_0(template_0, analyzeId, orderId,
                    expurl, expName,reportConfigVo,sysBranch,vo);
            // 导出tempalte模板内容
            boolean isTrue = false;
            if (booleanAndTable != null && booleanAndTable.size() == 2) {
                String[] booleanStr = booleanAndTable.get(0);
                // taleList = booleanAndTable.get(1);
                if (booleanStr != null && booleanStr.length == 1) {
                    if (!StringUtils.isEmpty(booleanStr[0])) {
                        if ("true".equals(booleanStr[0])) {
                            isTrue = true;
                        }
                    }
                }
            }
            if (isTrue) {
                expUrls.add(expurl);
            }
        }


        if (!StringUtils.isEmpty(template_1)) {// 导出template_1 模板内容
            String expName = format.format(new Date()) + "_template_1.docx";
            String expurl = url + expName;
            boolean isTrue = template_1(template_1, analyzeId, expurl, expName,reportConfigVo,vo);// 导出tempalte模板内容
            if (isTrue) {
                expUrls.add(expurl);
            }
        }


        if (!StringUtils.isEmpty(template_2)) {// 导出template_2 模板内容
            String expName = format.format(new Date()) + ".docx";
            String expurl = url + expName;
            boolean isTrue = template_2(template_2, analyzeId, orderId, expurl,
                    expName,reportConfigVo,vo);// 导出tempalte模板内容
            if (isTrue) {
                expUrls.add(expurl);
            }
        }


        String list7expUrl = null;// list模板：复查情况表导出后路径
        try {
            if (!StringUtils.isEmpty(taleList[0])) {
                // list1
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList(taleList[0], analyzeId, "1", expurl,
                        expName, list_1, list_empty,vo);// 疑似职业病人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
            }


            if (!StringUtils.isEmpty(taleList[1])) {
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList(taleList[1], analyzeId, "2", expurl,
                        expName, list_2, list_empty,vo);// 职业禁忌证人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
            }
            // list2
            if (!StringUtils.isEmpty(taleList[2])) {
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList(taleList[2], analyzeId, "3", expurl,
                        expName, list_3, list_empty,vo);// 职业病危害因素相关指标需复查人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
                /**
                 * 去掉附件6后面的“复查情况一览表” String name_7 = format.format(new Date()) +
                 * ".docx"; String url_7 = url + name_7; // list7expUrl =
                 * expList7(analyzeId,list_7,path); list7expUrl =
                 * expList7(analyzeId,list_7,url_7);
                 */
            }



            // list3
            if (!StringUtils.isEmpty(taleList[3])) {
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList(taleList[3], analyzeId, "4", expurl,
                        expName, list_4, list_empty,vo);// 其他疾病异常结果人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
            }


            // list4
            if (!StringUtils.isEmpty(taleList[4])) {
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList(taleList[4], analyzeId, "5", expurl,
                        expName, list_5, list_empty,vo);// 本次职业健康检查未见异常人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
            }



            // list5
            if (!StringUtils.isEmpty(taleList[5])) {
                String expName = format.format(new Date()) + ".docx";
                String expurl = url + expName;
                boolean isTure = expList_6(analyzeId, taleList[5], expurl,
                        expName, list_6, list_empty,vo);// 本次职业健康检查漏检人员及漏检项目人员一览表
                if (isTure) {
                    expUrls.add(expurl);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(list7expUrl)) {
            expUrls.add(list7expUrl);
        }

        // 尾页
        String expName = format.format(new Date()) + ".docx";
        String expurl = url + expName;
        boolean isTure = expEndpage(analyzeId, expurl, expName, endpage,reportConfigVo,vo);
        if (isTure) {
            expUrls.add(expurl);
        }

        expName = format.format(new Date());
        String wordUrl = null;
        String pdfUrl = null;
        String saveWordUrl = null;
        String savePdfUrl = null;
        if (expUrls != null && expUrls.size() > 0) {
            // wordUrl = url + expName+".docx";
            wordUrl = url + "disease_group_" + orderId + "_" + expName
                    + ".docx";
            pdfUrl = url + "disease_group_" + orderId + "_" + expName + ".pdf";

            MargeDoc wordUtil = new MargeDoc();
            wordUtil.mergeDocx(expUrls, wordUrl);
            FileOutputStream f=null;
            try {
                XWPFDocument doc=WordTool
                        .getInstance(
                                wordUrl,
                                null).getDoc();
                String zjz=ToolUtil.getPropert("doc_config.properties", "diseaseGroupHead");
                if(ball!=null&&StringUtils.isNotEmpty(zjz)){
                    simpleNumberFooter(doc,
                            ball.getOrgName()+"职业健康检查总结报告     " + reportConfigVo.getInspect()
                                    +"第" + orderId +"号"
                    );
                }

                f=new FileOutputStream(wordUrl);
                doc.write(f);
            } catch (Exception e1) {
                e1.printStackTrace();
            }finally{
                if(f!=null){
                    try {
                        f.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            // wordUtil.mergeDocx(expUrls, wordUrl);
            // JoinWords.uniteDoc(expUrls, wordUrl);
            WordConvertPDF conver = new WordConvertPDF();
            conver.wordToPDF(wordUrl, pdfUrl);
//            for (String e : expUrls) {
//                if (!endpage.equals(e)) {
//                    DeleteFileUtil.deleteFile(e);
//                }
//            }
            vo.setPdfUrl(pdfUrl);
            vo.setWordUrl(wordUrl);
        }


        return vo;
    }

    /**
     * 生成健康团检报告
     * @param param
     * @return
     */
    private CReprotNewDVo createReprotNew(CReprotNewDParam param) {
        CReprotNewDVo vo = new CReprotNewDVo();
        ReportConfigVo reportConfigVo = param.getReportConfigVo();
        SysBranch sysBranch = param.getSysBranch();
        BallCheckReport check = ballCheckReportMapper.getInfoById(param.getAnalyzeId());
        //地址
        String path = System.getProperty("user.dir") + "/temp/" ;
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
        param.setPrefix(prefix);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderCode = param.getOrderId();

        String file = path + "file/wordexp/group_medical/temporary/" + orderCode + "/";
        File dir = new File(file);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] expWords = new String[9];
        String[] modelUrls = param.getModelUrls();

        String model3_1 = null;
        for (int i = 0; i < modelUrls.length; i++) {
            String modelUrl = modelUrls[i];// 模板路径
            String str = modelUrl.substring(modelUrl.indexOf("_") - 1,
                    modelUrl.lastIndexOf("."));
            if (str.equals("3_1")) {
                model3_1 = modelUrl;
                break;
            }
        }

        for (int i = 0; i < modelUrls.length; i++) {
            String modelUrl = modelUrls[i];// 模板路径
            String str = modelUrl.substring(modelUrl.indexOf("_") - 1,
                    modelUrl.lastIndexOf("."));
            modelUrl = path + modelUrl;
            boolean isTrue = false;
            String expUrl = file + str + ".docx";
            String expName = str + ".docx";
            try {
                worTool = WordTool.getInstance(modelUrl, null);
                pfddoc = worTool.getDoc();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (str.equals("1_1")) {
                System.out.println("开始生成1_1模板");
                Map<String, Object> params = replaceData1_1(param);
                params.put("ADDRESS",reportConfigVo.getAddress());
                params.put("TELEPHONE",reportConfigVo.getPhone());
                params.put("CENTERORGNAME",sysBranch.getCenterorgname());
                vo.setData1_1(params);
                isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[0] = expUrl;
                    System.out.println("1_1模板生成成功");
                }else {
                    System.out.println("1_1模板生成失败");
                }
            } else if (str.equals("1_2")) {
                Map<String, Object> export = replaceData1_2(param);
                vo.setData1_2(export);
                for (Map.Entry<String, Object> entry : export.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();

                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTablesTwo(pfddoc, list, key
                                    .toString().trim(), expUrl);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                isTrue = worTool.exportTable(export, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[1] = expUrl;
                }
            } else if (str.equals("1_3")) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("ITEMS_M_W_T", check.getItemTotal());
                vo.setData1_3(params);
                isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[2] = expUrl;
                }
            } else if (str.equals("2_1")) {
                worTool = null;
                pfddoc = null;
                try {
                    worTool = WordTool.getInstance(modelUrl, null);
                    pfddoc = worTool.getDoc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, Object> params = replaceData2_1(param.getAnalyzeId());
                vo.setData2_1(params);
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();
                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTablesTwo(pfddoc, list, key
                                    .toString().trim(), expUrl);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[3] = expUrl;
                }
            } else if (str.equals("2_2")) {
                Map<String, Object> params = replaceData2_2(param.getAnalyzeId());
                params.put("ABNORMAL_M", StringUtils.isNotEmpty(check.getExceptionM())? prefix + check.getExceptionM() : null);
                params.put("ABNORMAL_W", StringUtils.isNotEmpty(check.getExceptionW())? prefix + check.getExceptionW() : null);
                params.put("ABNORMAL_T", StringUtils.isNotEmpty(check.getExceptionT())? prefix + check.getExceptionT() : null);
                vo.setData2_2(params);
                isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[4] = expUrl;
                }
            } else if (str.equals("3_0")) {
                isTrue = exp3_0(str + ".docx", modelUrl, param.getAnalyzeId(),
                        path + model3_1, expUrl, path, file , vo);
                if (isTrue) {
                    expWords[5] = expUrl;
                }
            } else if (str.equals("4_1")) {
                isTrue = exp4_1(str + ".docx", expUrl, worTool, pfddoc);
                if (isTrue) {
                    expWords[6] = expUrl;
                }
            } else if (str.equals("4_2")) {
                isTrue = exp4_2(expUrl, param.getAnalyzeId(), file, worTool,
                        pfddoc, modelUrl, vo);
                if (isTrue) {
                    expWords[7] = expUrl;
                }
            } else if (str.equals("5_0")) {
                Map<String, Object> params = new HashMap<>();
                params.put("ADDRESS",reportConfigVo.getAddress());
                params.put("TELEPHONE",reportConfigVo.getPhone());
                params.put("CENTERORGNAME",sysBranch.getCenterorgname());
                isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                        worTool, null, null, 1);
                if (isTrue) {
                    expWords[8] = expUrl;
                }
            }
        }

        List<String> expwords = new ArrayList<String>();
        if (ObjectUtils.isNotEmpty(expWords)){
            for (int i = 0; i < expWords.length; i++) {
                String str = expWords[i];
                if (StringUtils.isNotEmpty(str)) {
                    System.out.println("添加连接地址" + str);
                    expwords.add(str);
                }
            }
        }

        String date = formatter.format(new Date());
        String wordName = "health_group_" + param.getOrderId() + "_" + date
                + ".docx";
        String pdfName = "health_group_" + param.getOrderId() + "_" + date
                + ".pdf";

        String wordUrl = file + wordName;// 团检报告word最终输出的位置
        String pdfUrl = file + pdfName;// 团检报告pdf最终输出的位置

        System.out.println("wordUrl:" + wordUrl);
        System.out.println("pdfUrl:" + pdfUrl);
        System.out.println("-----开始合并word-----");
        MargeDoc wordUtil = new MargeDoc();
        wordUtil.mergeDocx(expwords, wordUrl);
        System.out.println("-----合并word成功-----");
        System.out.println("-----开始合并pdf-----");
        WordConvertPDF conver = new WordConvertPDF();
        conver.wordToPDF(wordUrl, pdfUrl);
        System.out.println("-----合并pdf成功-----");
        //删除临时文件
//        for (String e : expwords) {
//            DeleteFileUtil.deleteFile(e);
//        }

        vo.setWordUrl(wordUrl);
        vo.setPdfUrl(pdfUrl);

        return vo;
    }


    /**
     * 删除文件夹下的所有文件及当前文件夹
     * @param directory
     */
    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // 递归删除子文件夹
                    } else {
                        file.delete(); // 删除文件
                    }
                }
            }
            // 在删除所有文件和子文件夹后，删除当前文件夹
            directory.delete();
        }
    }



    /**
     * 导出4_2模板中的内容
     *
     * @Title: exp4_2
     * @param
     * @param modelUrl
     * @param expUrl
     * @param analyzeId
     * @return boolean
     * @author YINZL
     * @since 2017年2月13日 V 1.0
     */
    private boolean exp4_2(String expUrl, String analyzeId, String path,
                           WordTool worTool, XWPFDocument pfddoc, String modelUrl , CReprotNewDVo vo) {

        List<Map> mapList = new ArrayList<>();
        List<String> patientcode = groupReportMapper.getAllPatientcode(analyzeId);
        List<String> expUrls = new ArrayList<String>();
        if (patientcode != null && patientcode.size() > 0) {
            System.out.println("=====size:"+patientcode.size());
            for (int j = 0; j < patientcode.size(); j++) {
                //综合分析
                List<FxDetection> fxDetection = fxDetectionService.list(new QueryWrapper<FxDetection>()
                        .eq("sample_id", analyzeId)
                        .eq("patientcode", patientcode.get(j).trim()));
                Map<String, Object> params = new HashMap<String, Object>();

                if (fxDetection != null && fxDetection.size() > 0) {
                    FxDetection fx = fxDetection.get(0);
                    params.put("tjh",
                            fx.getPatientcode() != null ? fx.getPatientcode()
                                    : "");
                    params.put("xm",
                            fx.getPatientname() != null ? fx.getPatientname()
                                    : "");
                    params.put("xb",
                            fx.getSex() != null ? fx.getSex() == 0 ? "男" : "女"
                                    : "");
                    params.put("nl", fx.getAge() != null ? fx.getAge()
                            .toString() : "");
                    params.put("bm",
                            fx.getOrgDepart() != null ? fx.getOrgDepart() : "");
                    String str = "";
                    for (int i = 0; i < fxDetection.size(); i++) {
                        String con = fxDetection.get(i).getConclusion();
                        if (!org.apache.commons.lang3.StringUtils.isEmpty(con)) {
                            str = str + (i + 1) + "." + con + "\n";
                        }
                    }
                    params.put("tjjl", !StringUtils.isEmpty(str) ? str : "未见异常");
                }

                if (params != null) {
                    String expurl = path + "/" + j + ".docx";
                    try {
                        worTool = WordTool.getInstance(modelUrl, null);
                        pfddoc = worTool.getDoc();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean isTrue = worTool.exportTable(params, j + ".docx",
                            pfddoc, expurl, worTool, null, null, 0);

                    if (isTrue) {
                        expUrls.add(expurl);
                    }
                }
                mapList.add(params);
            }
        }

        vo.setData4_2(mapList);
        if (expUrls != null && expUrls.size() > 0) {
            System.out.println("merge=========");
            MargeDoc wordUtil = new MargeDoc();
            wordUtil.mergeDocx(expUrls, expUrl);
            System.out.println("merge end=========");
            // JoinWords.uniteDoc(expUrls, expUrl);
            for (String e : expUrls) {
                DeleteFileUtil.deleteFile(e);
            }
            return true;
        } else {
            return false;
        }

    }



    /**
     * 导出4_1模板中的内容
     *
     * @Title: exp4_1
     * @param
     * @param
     * @param expUrl
     * @return boolean
     * @author YINZL
     * @since 2017年2月13日 V 1.0
     */
    private boolean exp4_1(String expName, String expUrl, WordTool worTool,
                           XWPFDocument pfddoc) {

        boolean isTrue = worTool.exportTable(null, expName, pfddoc, expUrl,
                worTool, null, null, 1);
        return isTrue;
    }


    /**
     * 导出3_0模板中的内容
     * @param expName
     * @param modelUrl
     * @param analyzeId
     * @param model3_1
     * @param expUrl
     * @param path
     * @param file
     * @return
     */
    private boolean exp3_0(String expName, String modelUrl, String analyzeId,
                           String model3_1, String expUrl, String path, String file,CReprotNewDVo vo) {
        List<FxDetection> fxDetection = fxDetectionService.findFxDetection(analyzeId);
        List<String> depNames = new ArrayList<String>();
        if (fxDetection != null && fxDetection.size() > 0) {
            for (FxDetection f : fxDetection) {
                String depName = f.getDepName();
                if (!depNames.contains(depName)) {
                    depNames.add(depName);
                }
            }
        } else {
            return false;
        }
        List<String> savePaths = new ArrayList<String>();

        if (depNames != null && depNames.size() > 0) {
            for (int i = 0; i < depNames.size(); i++) {

                List<FxDetection> fx = new ArrayList<FxDetection>();
                for (FxDetection f : fxDetection) {
                    if ((f.getDepName() == null && depNames.get(i) == null)
                            || (f.getDepName() != null && f.getDepName()
                            .equals(depNames.get(i)))) {
                        fx.add(f);
                    }
                }
                List<String> basIds = new ArrayList<String>();

                if (fx != null && fx.size() > 0) {
                    for (FxDetection f : fx) {
                        String basId = f.getBasconclusionId();
                        if (!org.apache.commons.lang3.StringUtils.isEmpty(basId)) {
                            if (!basIds.contains(basId)) {
                                basIds.add(basId);
                            }
                        }
                    }
                }

                String path3_1 = file + "exp3_1_" + i + ".docx";
                String path3_0 = file + "exp3_0_" + i + ".docx";
                String exp = file + i + ".docx";
                String path3_0_p = file + "exp3_0_" + i + "_";

                boolean isTrue = exp3_1(model3_1, depNames.get(i), path3_1);
                List<String> url = new ArrayList<String>();
                if (isTrue) {
                    url.add(path3_1);
                }
                isTrue = exp_3_0(modelUrl, path3_0, depNames.get(i), basIds,
                        path3_0_p, analyzeId , vo);
                if (isTrue) {
                    url.add(path3_0);
                }
                if (url != null && url.size() > 0) {
                    if (url.size() == 2) {
                        MargeDoc wordUtil = new MargeDoc();
                        wordUtil.mergeDocx(url, exp);
                        // JoinWords.uniteDoc(url, exp);
                        savePaths.add(exp);
                    }
                    for (String e : url) {
                        DeleteFileUtil.deleteFile(e);
                    }

                }
            }
            if (savePaths != null && savePaths.size() > 0) {
                MargeDoc wordUtil = new MargeDoc();
                wordUtil.mergeDocxTj(savePaths, expUrl, 1);
                // JoinWords.uniteDoc(savePaths, expUrl);
                for (String e : savePaths) {
                    DeleteFileUtil.deleteFile(e);
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private boolean exp3_1(String model3_1, String d, String expUrl) {
        try {
            worTool = WordTool.getInstance(model3_1, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isTrue = false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("DEPNAME", d == null ? "<无分类>" : d);
        isTrue = worTool.exportTable(params, "3_1.docx", pfddoc, expUrl,
                worTool, null, null, 1);
        return isTrue;
    }



    /**
     * 获取检字
     * @param ball
     * @return
     */
    private String getJz(BallCheckReport ball) {
        //分中心缩写+年份+订单号：如JD检字（2023）第011006号
        SysBranch defaultBranch = sysBranchMapper.getDefaultBranch();
        String pyjm = defaultBranch.getPyjm();
        //当前年份
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return pyjm +"检字(" + year + ")第" + ball.getOrderId() + "号";

    }


    /**
     * 结束页数据
     *
     * @param
     * @return
     */
    private Boolean expEndpage(String analyzeId, String expurl, String expName,
                                           String modelUrl,ReportConfigVo reportConfigVo,CReprotNewDVo vo) {
        try {
            worTool = WordTool.getInstance(modelUrl, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        String harmIds = null;
        String harmClas = findHazardsBySampleId(analyzeId);// 主要职业病危害因素
        if (!StringUtils.isEmpty(harmClas)) {
            String[] strs = harmClas.split("!");
            if (strs != null && strs.length == 2) {
                harmIds = !StringUtils.isEmpty(strs[1]) ? strs[1] : null;
            }
        }
        String danagerIds = "";
        String bodyHarm = "";
        if (harmIds != null) {
            String[] strs = harmIds.split(",");
            if (strs != null && strs.length > 0) {
                for (int s = 0; s < strs.length; s++) {
                    String[] str = strs[s].split(":");
                    if (str != null && str.length == 2) {
                        if (s == 0) {
                            danagerIds = str[1];
                        } else {
                            danagerIds = danagerIds + "," + str[1];
                        }
                    }
                }
            }
            //查询所有危害因素对身体危害
            bodyHarm = findBodyHarm(danagerIds);
        }
        params.put("FOR_PERSON_INFLUENCE", bodyHarm);

        params.put("JZ", reportConfigVo.getInspect());
        params.put("PHONE", reportConfigVo.getPhone());
        params.put("FAX", reportConfigVo.getFax());
        params.put("ADDRESS", reportConfigVo.getAddress());


        //附件7：职业健康检查机构备案回执
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
        params.put("PIC7", StringUtils.isNotEmpty(reportConfigVo.getPic()) ? prefix + reportConfigVo.getPic() : null);



        //附件9：沃德体检主要技术人员资质一览表数据
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (CollectionUtils.isNotEmpty(reportConfigVo.getPersonAptitude())){
            // 对查询出来之后的数组集合对应相应的坐标进行赋值
            List<PersonAptitudeDto> harmAndIllness = reportConfigVo.getPersonAptitude();
            for (int h = 0; h < harmAndIllness.size(); h++) {
                PersonAptitudeDto str = harmAndIllness.get(h);
                HashMap<String, String> result = new HashMap<String, String>();
                if (h == 0) {
                    result.put("name", str.getName());
                    result.put("sex", str.getSex());
                    result.put("post", str.getPost());
                    result.put("ks", str.getKs());
                    result.put("major", str.getMajor());
                    result.put("workYears", str.getWorkYears());
                    result.put("number", str.getNumber());
                } else {
                    result.put("name" + h, str.getName());
                    result.put("sex" + h, str.getSex());
                    result.put("post" + h, str.getPost());
                    result.put("ks" + h, str.getKs());
                    result.put("major" + h, str.getMajor());
                    result.put("workYears" + h, str.getWorkYears());
                    result.put("number" + h, str.getNumber());
                }
                mapList.add(result);
            }
            // 对赋值之后的坐标表格进行增行操作
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }
            if (mapList != null && mapList.size() > 1) {
                params.put("", mapList);
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();
                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTables2(pfddoc, list, key
                                    .toString().trim(), expurl, 1);// 导出位置

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        vo.setEndpage(params);

        boolean isTrue = false;
        isTrue = worTool.exportTable(params, expName, pfddoc, expurl, worTool,
                null, null, 0);
        return isTrue;

    }


    /**
     * 查询所有危害因素对身体危害
     *
     * @param danagerIds
     * @return
     */
    private String findBodyHarm(String danagerIds) {
        List<BodyHarmDto> list = groupReportMapper.findBodyHarm(danagerIds.split(","));
        String returnStr = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                BodyHarmDto obj = list.get(i);
                String name = obj.getHarmName() != null ? String.valueOf(obj.getHarmName()) : "";
                String details = obj.getAffect() != null ? String.valueOf(obj.getAffect()) : "";
                if (i == 0) {
                    returnStr = "" + name + "：" + details;
                } else {
                    returnStr = returnStr + "\n" + "    " + name + "："
                            + details;
                }
            }
        }
        return returnStr;

    }


    /**
     * list6模板导出
     *
     * @param
     * @return
     */
    private Boolean expList_6(String analyzeId, String tableNO, String expUrl,
                                          String expName, String modelUrl, String emptyUrl,CReprotNewDVo vo) {
        boolean showIdcardno = true;
        List<FxCompletion> fxCompletions = fxCompletionService.list(new QueryWrapper<FxCompletion>()
                .eq("sample_id", analyzeId).isNotNull("unchecked_items"));
        try {
            worTool = WordTool.getInstance(modelUrl, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (fxCompletions != null && fxCompletions.size() > 0) {
            for (int i = 0; i < fxCompletions.size(); i++) {
                FxCompletion view = fxCompletions.get(i);
                HashMap<String, String> result = new HashMap<String, String>();

                if (i == 0) {
                    result.put("num", (i + 1) + "");
                    result.put(
                            "patientcode",showIdcardno?(view.getIdcardno()==null?"":view.getIdcardno()):(
                                    view.getPatientcode() != null ? view
                                            .getPatientcode() : ""));
                    result.put(
                            "name",
                            view.getPatientname() != null ? view
                                    .getPatientname() : "");
                    result.put("sex",
                            "0".equals(view.getSex())?"男":"1".equals(view.getSex())?"女":""
                    );
                    result.put("age", view.getAge() != null ? view.getAge()
                            .toString() : "");
                    result.put("craft",
                            view.getTrades() != null ? view.getTrades() : "");
                    result.put("years", ToolUtil.getYearMonth(view.getJhgl()));
                    // result.put("years",view.getJhgl()!=null?view.getJhgl().toString():"");
                    result.put("reason",
                            view.getJhys() != null ? view.getJhys() : "");
                    result.put(
                            "missobject",
                            view.getUncheckedItems() != null ? view
                                    .getUncheckedItems() : "");
                } else {
                    result.put("num" + i, (i + 1) + "");
                    result.put(
                            "patientcode" + i,
                            showIdcardno?(view.getIdcardno()==null?"":view.getIdcardno()):(
                                    view.getPatientcode() != null ? view
                                            .getPatientcode() : "")
                    );
                    result.put(
                            "name" + i,
                            view.getPatientname() != null ? view
                                    .getPatientname() : "");
                    result.put("sex" + i,
                            "0".equals(view.getSex())?"男":"1".equals(view.getSex())?"女":""
                    );
                    result.put("age" + i, view.getAge() != null ? view.getAge()
                            .intValue() + "" : "");
                    result.put("craft" + i,
                            view.getTrades() != null ? view.getTrades() : "");
                    result.put("years" + i,
                            ToolUtil.getYearMonth(view.getJhgl()));
                    // result.put("years"+i,view.getJhgl()!=null?view.getJhgl().toString():"");
                    result.put("reason" + i,
                            view.getJhys() != null ? view.getJhys() : "");
                    result.put(
                            "missobject" + i,
                            view.getUncheckedItems() != null ? view
                                    .getUncheckedItems() : "");
                }
                mapList.add(result);
            }
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (mapList != null
            // && mapList.size() > 1
        ) {
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }
            if (mapList.size() > 1) {
                params.put("", mapList);
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();
                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTables2(pfddoc, list, key
                                    .toString().trim(), expUrl, 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (mapList.size() == 0) {
                try {
                    pfddoc = worTool.updateTables2(pfddoc,
                            new ArrayList<HashMap<String, Object>>(), ""
                                    .toString().trim(), expUrl, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        params.put("listnum", tableNO);
        vo.setList_6(params);
        boolean isTrue = false;
        // if (params != null && params.size() > 0) {
        if (fxCompletions != null && fxCompletions.size() > 0) {
            isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                    worTool, null, null, 0);
        } else {
            isTrue = expEmpty(expUrl, 6, expName, emptyUrl);
        }
        return isTrue;
    }

    /**
     * 附表1~5的数据
     *
     * @param
     * @param summarySerialNo 结论词序列号
     * @return
     */
    private Boolean expList(String tableNo, String analyzeId,
                                        String summarySerialNo, String expUrl, String expName,
                                        String modelUrl, String emptyUrl,CReprotNewDVo vo) {

        boolean showIdcardno = true;
        if (StringUtils.equals(loadProperties.name, "changsha")){
            showIdcardno = false;
        }
        //综合分析-人员一览表
        List<FxPersonnelview> fxPersonnelview = fxPersonnelviewMapper.selectList(new QueryWrapper<FxPersonnelview>()
                .eq("sample_id", analyzeId).eq("summary_serialno", summarySerialNo));
        try {
            worTool = WordTool.getInstance(modelUrl, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<HashMap> mapList = new ArrayList<HashMap>();
        if (fxPersonnelview != null && fxPersonnelview.size() > 0) {
            for (int i = 0; i < fxPersonnelview.size(); i++) {
                FxPersonnelview view = fxPersonnelview.get(i);
                HashMap<String, String> result = new HashMap<String, String>();

                if (i == 0) {
                    result.put("num", (i + 1) + "");
                    result.put(
                            "patientcode",showIdcardno?(view.getIdcardno()==null?"":view.getIdcardno()):(
                                    view.getPatientcode() != null ? view
                                            .getPatientcode() : ""));
                    result.put(
                            "name",
                            view.getPatientname() != null ? view
                                    .getPatientname() : "");
                    result.put("sex", view.getSex() != null ? view.getSex()
                            .equals("0") ? "男" : "女" : "");
                    result.put("age", view.getAge() != null ? String.valueOf(view.getAge()) : "");
                    result.put("craft",
                            view.getTrades() != null ? view.getTrades() : "");
                    // result.put("years", view.getJhgl() != null ?
                    // view.getJhgl()
                    // .toString() : "");
                    result.put("years", ToolUtil.getYearMonth(view.getJhgl()));
                    result.put("opinion", "可以继续从事原工作，加强防护");//平度要的,先写死
                    result.put("reason",
                            view.getJhys() != null ? view.getJhys() : "");
                    //去除里面的换行符
                    result.put("result",
                            view.getPositives() != null ? view.getPositives().replace("\n", "")
                                    : "");
                    result.put(
                            "verdict",
                            view.getOccupationSummary() != null ? view
                                    .getOccupationSummary() : "");
                    if (!"5".equals(summarySerialNo)) {
                        result.put(
                                "idea",
                                view.getSummaryText() != null ? view
                                        .getSummaryText() : "");
                    }
                } else {
                    result.put("num" + i, (i + 1) + "");
                    result.put(
                            "patientcode" + i,
                            showIdcardno?(view.getIdcardno()==null?"":view.getIdcardno()):(
                                    view.getPatientcode() != null ? view
                                            .getPatientcode() : "")
                    );
                    result.put(
                            "name" + i,
                            view.getPatientname() != null ? view
                                    .getPatientname() : "");
                    result.put("sex" + i, view.getSex() != null ? view.getSex()
                            .equals("0") ? "男" : "女" : "");
                    result.put("age" + i, view.getAge() != null ? String.valueOf(view.getAge()): "");
                    result.put("craft" + i,
                            view.getTrades() != null ? view.getTrades() : "");
                    result.put("years" + i,
                            ToolUtil.getYearMonth(view.getJhgl()));
                    result.put("opinion" + i, "可以继续从事原工作，加强防护");//平度要的,先写死
                    // result.put("years" + i, view.getJhgl() != null ? view
                    // .getJhgl().toString() : "");
                    result.put("reason" + i,
                            view.getJhys() != null ? view.getJhys() : "");
                    result.put("result" + i,
                            view.getPositives() != null ? view.getPositives().replace("\n", "")
                                    : "");
                    result.put(
                            "verdict" + i,
                            view.getOccupationSummary() != null ? view
                                    .getOccupationSummary() : "");
                    if (!"5".equals(summarySerialNo)) {
                        result.put(
                                "idea" + i,
                                view.getSummaryText() != null ? view
                                        .getSummaryText() : "");
                    }
                }
                mapList.add(result);
            }
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (mapList != null && mapList.size() > 0) {
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }

            if (mapList.size() > 1) {
                params.put("", mapList);
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();
                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTables2(pfddoc, list, key
                                    .toString().trim(), expUrl, 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            /**
             * 没数据时显示空表 try { pfddoc = worTool.updateTables2(pfddoc, new
             * ArrayList<HashMap<String,Object>>(), "" , expUrl,1);
             *
             * } catch (Exception e) { e.printStackTrace(); }
             */
        }
        params.put("fxPersonnelview",fxPersonnelview);
        params.put("listnum", tableNo);
        params.put("count", fxPersonnelview.size());
        if ("3".equals(summarySerialNo)) {
            String announcements = findAnnouncements(analyzeId,
                    summarySerialNo);
            params.put("reviewattention",
                    !StringUtils.isEmpty(announcements) ? announcements : "");
        }
        //保存返回数据
        switch (summarySerialNo) {
            case "1":
                vo.setList_1(params);
                break;
            case "2":
                vo.setList_2(params);
                break;
            case "3":
                vo.setList_3(params);
                break;
            case "4":
                vo.setList_4(params);
                break;
            case "5":
                vo.setList_5(params);
                break;
        }

        boolean isTrue = false;
        // if (params != null && params.size() > 0) {
        if (fxPersonnelview != null && fxPersonnelview.size() > 0) {
            isTrue = worTool.exportTable(params, expName, pfddoc, expUrl,
                    worTool, null, null, 0);
        } else {
            isTrue = expEmpty(expUrl, Integer.parseInt(summarySerialNo),
                    expName, emptyUrl);
        }
        return isTrue;
    }


    /**
     * 附件如无内容，则显示如下形式（不带表格及表注） 附件1：疑似职业病人员一览表：无
     *
     * @Title: expEmpty
     * @return boolean
     * @author xuhp
     * @since 2018年1月9日 V 1.0
     */
    private boolean expEmpty(String expUrl, int listNO, String expName,
                             String modelUrl) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(modelUrl)) {
            return false;
        }
        try {
            worTool = WordTool.getInstance(modelUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        String msg = null;
        switch (listNO) {
            case 1:
                msg = "1：疑似职业病人员一览表";
                break;
            case 2:
                msg = "2：职业禁忌证人员一览表";
                break;
            case 3:
                msg = "3：职业相关性疾病需复查人员一览表";
                break;
            case 4:
                msg = "4：其他疾病异常结果人员一览表";
                break;
            case 5:
                msg = "5：本次职业健康检查未见异常人员一览表";
                break;
            case 6:
                msg = "6：本次职业健康检查漏检人员及漏检项目人员一览表";
                break;
            default:
                msg = "";
        }
        params.put("msg", msg);
        pfddoc = worTool.getDoc();
        return worTool.exportTable(params, expName, pfddoc, expUrl, worTool,
                null, null, 0);
    }


    /**
     * 查询复查注意事项
     *
     * @param analyzeId
     * @param summarySerialNo
     * @return
     */
    private String findAnnouncements(String analyzeId, String summarySerialNo) {
        List<String> list = groupReportMapper.findAnnouncements(analyzeId, summarySerialNo);
        String returnStr = "";
        if (list != null && list.size() > 0) {
            int j = 1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    String l = list.get(i).toString();
                    if (j == 1) {
                        returnStr = j + "." + l;
                    } else {
                        returnStr = returnStr + "\n" + j + "." + l;
                    }
                    j++;
                }
            }
        }
        return returnStr;
    }

    /**
     * 导出tempalte_2模板内容及该模板中所涉及的附表内容
     *
     * @param
     * @return
     */
    private Boolean template_2(String template_2, String analyzeId,
                               String orderId, String expurl, String expName,ReportConfigVo reportConfigVo,CReprotNewDVo vo) {
        try {
            worTool = WordTool.getInstance(template_2, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<HashMap> mapList = countAllCollectTable(analyzeId);
        Map<String, Object> params = new HashMap<String, Object>();

        if (CollectionUtils.isNotEmpty(mapList)) {
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }
            params.put("", mapList);
        }

        if (mapList != null && mapList.size() > 1) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                Object key = entry.getKey();
                // 处理多行数据
                if (value instanceof List) {
                    List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                    try {
                        pfddoc = worTool.updateTables2(pfddoc, list, key
                                .toString().trim(), expurl, 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //团检报告主表
        BallCheckReport ball = ballCheckReportMapper.getInfoById(analyzeId);
        String thisCheckStatus = "";
        if (ball != null) {
            thisCheckStatus = ball.getBcbgfx();
        }
        params.put("THIS_CHECK_STATUS", !StringUtils.isEmpty(thisCheckStatus) ? thisCheckStatus : "");
        //根据样本ID获取所有危害因素
        List<String> harmIds = findHarmIdsBySampleId(analyzeId);
        String hagardDescribe = "";
        int num = 1;
//		if (harmIds != null && harmIds.size() > 0) {
        for (int j = 0; j < harmIds.size(); j++) {
            List<FxDetectionzy> fxDetectionzys = fxDetectionzyMapper.selectList(new QueryWrapper<FxDetectionzy>()
                    .eq("sample_id", analyzeId).eq("occupation_diagnosis", String.valueOf(harmIds.get(j))));
            String harmName = null;
            String harmNum = null;
            String nameNumStr = null;
            if (fxDetectionzys != null && fxDetectionzys.size() > 0) {
                for (int i = 0; i < fxDetectionzys.size(); i++) {
                    harmName = fxDetectionzys.get(i).getHarmName() != null ? fxDetectionzys
                            .get(i).getHarmName() : "";
                    harmNum = fxDetectionzys.get(i).getHarmNum() != null ? fxDetectionzys
                            .get(i).getHarmNum().toString()
                            : "";
                    String diseaseName = fxDetectionzys.get(i).getDisease() != null ? fxDetectionzys
                            .get(i).getDisease() : "";
                    String diseaseNum = fxDetectionzys.get(i).getCheckNum() != null ? fxDetectionzys
                            .get(i).getCheckNum().toString()
                            : "";
                    String offer = fxDetectionzys.get(i)
                            .getOccupationDiseast() != null ? fxDetectionzys
                            .get(i).getOccupationDiseast().toString() : "";
                    if (i == 0) {
                        nameNumStr = diseaseName + diseaseNum + "人，建议"
                                + offer + ";";
                    } else {
                        nameNumStr = nameNumStr + diseaseName + diseaseNum
                                + "人，建议" + offer + ";";
                    }
                }
                if (j == 0) {
                    hagardDescribe = "    " + num + hagardDescribe + "." + "接触危害因素"
                            + harmName + harmNum + "人," + nameNumStr + "\n";
                    num++;
                } else {
                    hagardDescribe = hagardDescribe + "    " + num + "."
                            + "接触危害因素"// 四个空格 手动首行缩进
                            + harmName + harmNum + "人," + nameNumStr + "\n";
                    num++;
                }
            }

        }
        List<FxHarm> fhs = fxHarmMapper.selectList(new QueryWrapper<FxHarm>().eq("sample_id", analyzeId));
        for (FxHarm fh : fhs) {
            if (!harmIds.contains(fh.getHarmId())) {
                if (StringUtils.isEmpty(hagardDescribe)) {
                    hagardDescribe = "    " + num + "." + "接触危害因素"
                            + fh.getHarmName() + fh.getPersonNum() + "人。\n";
                    num++;
                } else {
                    hagardDescribe = hagardDescribe + "    " + num + "."
                            + "接触危害因素"// 四个空格 手动首行缩进
                            + fh.getHarmName() + fh.getPersonNum() + "人。\n";
                    num++;
                }
            }
        }
        if (!StringUtils.isEmpty(hagardDescribe)) {
            params.put("HAGARD_DESCRIBE", hagardDescribe);
        } else {
            params.put("HAGARD_DESCRIBE", "暂无分析内容");
        }

        params.put("JZ",reportConfigVo.getInspect());

        vo.setTemplate_2(params);
        boolean isTrue = false;
        if (params != null && params.size() > 0) {
            isTrue = worTool.exportTable(params, expName, pfddoc, expurl,
                    worTool, null, null, 0);
        }
        return isTrue;
    }

    /**
     * 根据样本ID获取所有危害因素
     *
     * @param analyzeId
     * @return
     */
    private List<String> findHarmIdsBySampleId(String analyzeId) {
        List<String> list = groupReportMapper.findHarmIdsBySampleId(analyzeId);
        List<String> harmIds = new ArrayList<String>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String obj = list.get(i).toString();
                if (!StringUtils.isEmpty(obj)) {
                    harmIds.add(obj);
                }
            }
        }
        return harmIds;
    }

    /**
     * 本次职业健康检查危害因素人员检查情况汇总一览表
     *
     * @param analyzeId
     * @return
     */
    private List<HashMap> countAllCollectTable(String analyzeId) {
        List<AllCollectTableDto> list = groupReportMapper.countAllCollectTable(analyzeId);
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                AllCollectTableDto obj = list.get(i);
                HashMap<String, String> result = new HashMap<String, String>();
                String status = obj.getRegimentationNote() != null ? obj.getRegimentationNote().toString() : "";

                if (!StringUtils.isEmpty(status)) {
                    if ("0".equals(status)) {
                        status = "上岗前";
                    } else if ("1".equals(status)) {
                        status = "在岗期间";
                    } else if ("2".equals(status)) {
                        status = "离岗时";
                    } else if ("3".equals(status)) {
                        status = "离岗后";
                    } else if ("4".equals(status)) {
                        status = "应急";
                    }
                } else {
                    status = "未知状态";
                }
                if (i == 0) {
                    result.put("OBJ_STATUS", status);
                    result.put("HARM_NAME", obj.getHarmName() != null ? obj.getHarmName().toString()
                            : "0");
                    result.put("ORG_NAME", obj.getOrgDepart() != null ? obj.getOrgDepart().toString()
                            : "");
                    result.put("INDUSTRIAL_DISEASE",
                            obj.getYszyb() != null ? obj.getYszyb().toString() : "0");
                    result.put("OCCUPATIONAL_CONTRAINDICATION",
                            obj.getZyjjz() != null ? obj.getZyjjz().toString() : "0");
                    result.put("REVIEW", obj.getFc() != null ? obj.getFc().toString()
                            : "0");
                    result.put("OTHER_DISEASE",
                            obj.getQtjb() != null ? obj.getQtjb().toString() : "0");
                    result.put("NO_ABNORMALITY_SEEN",
                            obj.getWjyc() != null ? obj.getWjyc().toString() : "0");
                }else {
                    result.put("OBJ_STATUS" + i , status);
                    result.put("HARM_NAME" + i , obj.getHarmName() != null ? obj.getHarmName().toString()
                            : "0");
                    result.put("ORG_NAME" + i , obj.getOrgDepart() != null ? obj.getOrgDepart().toString()
                            : "");
                    result.put("INDUSTRIAL_DISEASE" + i ,
                            obj.getYszyb() != null ? obj.getYszyb().toString() : "0");
                    result.put("OCCUPATIONAL_CONTRAINDICATION" + i ,
                            obj.getZyjjz() != null ? obj.getZyjjz().toString() : "0");
                    result.put("REVIEW" + i , obj.getFc() != null ? obj.getFc().toString()
                            : "0");
                    result.put("OTHER_DISEASE" + i ,
                            obj.getQtjb() != null ? obj.getQtjb().toString() : "0");
                    result.put("NO_ABNORMALITY_SEEN" + i ,
                            obj.getWjyc() != null ? obj.getWjyc().toString() : "0");
                }


                mapList.add(result);
            }
        }
        return mapList;


    }


    /**
     * 导出tempalte_1模板内容及该模板中所涉及的附表内容
     *
     * @return boolean
     * @Title: template_1
     * @author YINZL
     * @since 2017年2月27日 V 1.0
     */
    private Boolean template_1(String template_1, String analyzeId,
                               String expurl, String expName,ReportConfigVo reportConfigVo,CReprotNewDVo vo) {
        try {
            worTool = WordTool.getInstance(template_1, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<HashMap> mapList = findAllPeopleNum(analyzeId);
        Map<String, Object> params = new HashMap<String, Object>();
        if (mapList != null) {
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }
        }
        params.put("", mapList);

        if (mapList != null && mapList.size() > 1) {

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                Object key = entry.getKey();
                // 处理多行数据
                if (value instanceof List) {
                    List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                    try {
                        pfddoc = worTool.updateTables2(pfddoc, list, key
                                .toString().trim(), expurl, 1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        params.put("JZ",reportConfigVo.getInspect());
        vo.setTemplate_1(params);
        boolean isTrue = false;
        if (params != null && params.size() > 0) {
            isTrue = worTool.exportTable(params, expName, pfddoc, expurl,
                    worTool, null, null, 1);
        }
        return isTrue;
    }

    /**
     * 本次职业健康检查危害因素人员分布情况一览表统计
     *
     * @param analyzeId
     * @return
     */
    private List<HashMap> findAllPeopleNum(String analyzeId) {
        List<AllPeopleNumDto> list = groupReportMapper.findAllPeopleNum(analyzeId);
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                AllPeopleNumDto obj = list.get(i);
                HashMap<String, String> result = new HashMap<String, String>();
                if (i == 0) {
                    result.put("DANGER_NAME",
                            obj.getHarmName() != null ? obj.getHarmName().toString() : "");
                    result.put("SHOULD_BE_CHECKED",
                            obj.getPeopleNum() != null ? obj.getPeopleNum().toString() : "");
                    result.put("REALITY_CHECK",
                            obj.getInspectNum() != null ? obj.getInspectNum().toString() : "");
                    result.put("NOT_CHECK",
                            obj.getUnexploredNum() != null ? obj.getUnexploredNum().toString() : "");
                    result.put("MAN_NUM",
                            obj.getManNum() != null ? obj.getManNum().toString() : "");
                    result.put("WOMEN_NUM",
                            obj.getWomenNum() != null ? obj.getWomenNum().toString() : "");
                } else {
                    result.put("DANGER_NAME" + i,
                            obj.getHarmName() != null ? obj.getHarmName().toString() : "");
                    result.put("SHOULD_BE_CHECKED" + i,
                            obj.getPeopleNum() != null ? obj.getPeopleNum().toString() : "");
                    result.put("REALITY_CHECK" + i,
                            obj.getInspectNum() != null ? obj.getInspectNum().toString() : "");
                    result.put("NOT_CHECK" + i,
                            obj.getUnexploredNum() != null ? obj.getUnexploredNum().toString() : "");
                    result.put("MAN_NUM" + i,
                            obj.getManNum() != null ? obj.getManNum().toString() : "");
                    result.put("WOMEN_NUM" + i,
                            obj.getWomenNum() != null ? obj.getWomenNum().toString() : "");
                }
                mapList.add(result);
            }
        }
        return mapList;
    }


    /**
     * 获取1_1模板中的坐标及所对应的数据
     *
     * @return Map<String, Object>
     * @Title: replaceData1_1
     * @author YINZL
     * @since 2017年1月24日 V 1.0
     */
    private Map<String, Object> replaceData1_1(CReprotNewDParam param) {
        String prefix = param.getPrefix();
        String analyzeId = param.getAnalyzeId();
        BallCheckReport ball = ballCheckReportMapper.getInfoById(param.getAnalyzeId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ORDER_ID", ball.getOrderId());
        Date beginTime = ball.getBeginTime();
        Date endTime = ball.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("RANGE_DATE",
                (beginTime == null ? "" : sdf.format(beginTime)) + "至"
                        + (endTime == null ? "" : sdf.format(endTime)));
        String groupId = ball.getGroupId();
        if (!StringUtils.isEmpty(groupId) && !"null".equals(groupId)) {
            //我的客户表
            Sellcustomer customer = sellcustomerMapper.getInfoById(groupId);
            if (customer != null) {
                params.put("TEAM_NAME",
                        customer.getKhdwmc() != null ? customer.getKhdwmc()
                                : "");
                params.put("TEAM_ADDRESS",
                        customer.getKhdwzcdz() != null ? customer.getKhdwzcdz()
                                : "");
            } else {
                params.put("TEAM_NAME", "");
                params.put("TEAM_ADDRESS", "");
            }
        } else {
            params.put("TEAM_NAME", "");
            params.put("TEAM_ADDRESS", "");
        }
        String orderId = ball.getOrderId();
        if (!StringUtils.isEmpty(orderId) && !"null".equals(orderId)) {
            //订单表
            Createorder order = createorderMapper.selectOne(new QueryWrapper<Createorder>().eq("ddh", orderId));
            if (order != null) {
                Date date = order.getCjddrq();
                String dateStr = null;
                if (date != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    dateStr = formatter.format(date); //
                }
                if (dateStr != null) {
                    params.put("RECORD_DATE", dateStr);
                } else {
                    params.put("RECORD_DATE", "");
                }
            } else {
                params.put("RECORD_DATE", "");
            }
        } else {
            params.put("RECORD_DATE", "");
        }

        if (!StringUtils.isEmpty(analyzeId) && !"null".equals(analyzeId)) {

            List<FxCompletion> fxCompletion = fxCompletionService.findFxCompletion(analyzeId);

            if (fxCompletion != null && fxCompletion.size() > 0) {
                Integer size = fxCompletion.size();
                if (size == 1) {
                    String begindate = null;
                    String enddate = null;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date begin = fxCompletion.get(0).getDateregister();
                    if (begin != null) {
                        begindate = dateFormat.format(begin);
                    }
                    Date end = fxCompletion.get(0).getDateregister();
                    if (end != null) {
                        enddate = dateFormat.format(end);
                    }
                    if (begindate != null) {
                        params.put("REGISTER_BEGIN", begindate);
                    } else {
                        params.put("REGISTER_BEGIN", "");
                    }
                    if (enddate != null) {
                        params.put("REGISTER_END", enddate);
                    } else {
                        params.put("REGISTER_END", "");
                    }
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String begindate = null;
                    String enddate = null;
                    Date begin = fxCompletion.get(0).getDateregister();
                    if (begin != null) {
                        begindate = formatter.format(begin);
                    }
                    Date end = fxCompletion.get(size - 1).getDateregister();
                    if (end != null) {
                        enddate = formatter.format(end);
                    }
                    if (begindate != null) {
                        params.put("REGISTER_BEGIN", begindate);
                    } else {
                        params.put("REGISTER_BEGIN", "");
                    }
                    if (enddate != null) {
                        params.put("REGISTER_END", enddate);
                    } else {
                        params.put("REGISTER_END", "");
                    }
                }
                Integer less18M = 0;// 小于等于18男
                Integer less29M = 0;// 小于等于29 大于18男
                Integer less39M = 0;// 小于等于39 大于29男
                Integer less49M = 0;// 小于等于49 大于39男
                Integer less59M = 0;// 小于等于59 大于49男
                Integer less69M = 0;// 小于等于69 大于59男
                Integer more70M = 0;// 大于等于70 男
                Integer less18W = 0;// 小于等于18女
                Integer less29W = 0;// 小于等于29 大于18女
                Integer less39W = 0;// 小于等于39 大于29女
                Integer less49W = 0;// 小于等于49 大于39女
                Integer less59W = 0;// 小于等于59 大于49女
                Integer less69W = 0;// 小于等于69 大于59女
                Integer more70W = 0;// 大于等于70 女
                Integer planMan = 0;// 计划参检男性人数
                Integer planWomen = 0;// 计划参检女性人数
                Integer planTotal = fxCompletion.size();// 计划参检总人数
                Integer man = 0;// 参检男性人数
                Integer women = 0;// 参检女性人数

                for (FxCompletion f : fxCompletion) {
                    String sex = f.getSex();
                    if (!StringUtils.isEmpty(sex)) {
                        Integer isbegin = f.getFExamstarted();
                        if (sex.equals("0")) {
                            planMan++;
                            if (isbegin != null && isbegin == 1) {
                                man++;
                            }
                        } else if (sex.equals("1")) {
                            planWomen++;
                            if (isbegin != null && isbegin == 1) {
                                women++;
                            }
                        }
                    }

                    Integer age = f.getAge();
                    if (age != null && !StringUtils.isEmpty(sex)
                            && !"null".equals(sex)) {
                        if (age <= 18) {
                            if (sex.equals("0")) {// 男
                                less18M++;
                            } else if (sex.equals("1")) {// 女
                                less18W++;
                            }

                        } else if (age <= 29 && age > 18) {
                            if (sex.equals("0")) {// 男
                                less29M++;
                            } else if (sex.equals("1")) {// 女
                                less29W++;
                            }

                        } else if (age <= 39 && age > 29) {
                            if (sex.equals("0")) {// 男
                                less39M++;
                            } else if (sex.equals("1")) {// 女
                                less39W++;
                            }

                        } else if (age <= 49 && age > 39) {
                            if (sex.equals("0")) {// 男
                                less49M++;
                            } else if (sex.equals("1")) {// 女
                                less49W++;
                            }

                        } else if (age <= 59 && age > 49) {
                            if (sex.equals("0")) {// 男
                                less59M++;
                            } else if (sex.equals("1")) {// 女
                                less59W++;
                            }

                        } else if (age <= 69 && age > 59) {
                            if (sex.equals("0")) {// 男
                                less69M++;
                            } else if (sex.equals("1")) {// 女
                                less69W++;
                            }

                        } else if (age >= 70) {
                            if (sex.equals("0")) {// 男
                                more70M++;
                            } else if (sex.equals("1")) {// 女
                                more70W++;
                            }

                        }
                    }
                }
                Integer total = man + women;// 参检总人数
                params.put("PLAN_M", planMan.toString());// 计划参检男
                params.put("PLAN_W", planWomen.toString());// 计划参见女
                params.put("PLAN_T", planTotal.toString());// 计划参检总数
                params.put("PLAN_M_M", returnPercent(planMan, planTotal));// 男性构成百分比
                params.put("PLAN_M_W", returnPercent(planWomen, planTotal));// 女性构成百分比
                params.put("INSPECT_M", man.toString());// 参检男性人数
                params.put("INSPECT_W", women.toString());// 参检女性人数
                params.put("INSPECT_T", total.toString());// 参检总人数
                params.put("INSPECT_M_M", returnPercent(man, total));// 参检男性人数占总参检人数百分比
                params.put("INSPECT_M_W", returnPercent(women, total));// 参检女性人数占总参检人数百分比
                params.put("INSPECT_T_M", returnPercent(man, planMan));// 参检男性占计划男性百分比
                params.put("INSPECT_T_W", returnPercent(women, planWomen));// 参检女性占计划女性百分比
                params.put("INSPECT_T_A", returnPercent(total, planTotal));// 参检总人数占计划总人数的百分比
                params.put("NO_M", String.valueOf((planMan - man)));// 未检男性人数
                params.put("NO_W", String.valueOf((planWomen - women)));// 未检女性人数
                params.put("NO_T", String.valueOf((planTotal - total)));// 未检总人数
                params.put("NO_M_M",
                        returnPercent(planMan - man, planTotal - total));// 未检男性占总未检人数百分比
                params.put("NO_M_W",
                        returnPercent(planWomen - women, planTotal - total));// 未检女性占总未检人数百分比
                params.put("NO_T_M", returnPercent(planMan - man, planMan));// 未检男性占计划男性百分比
                params.put("NO_T_W",
                        returnPercent(planWomen - women, planWomen));// 未检女性占计划女性百分比
                params.put("NO_T_A",
                        returnPercent(planTotal - total, planTotal));// 未检总人数占计划总人数百分比

                params.put("LITTLE18_M", String.valueOf(less18M));
                params.put("LITTLE30_M", String.valueOf(less29M));
                params.put("LITTLE40_M", String.valueOf(less39M));
                params.put("LITTLE50_M", String.valueOf(less49M));
                params.put("LITTLE60_M", String.valueOf(less59M));
                params.put("LITTLE70_M", String.valueOf(less69M));
                params.put("MORE69_M", String.valueOf(more70M));
                Integer sumM = less18M + less29M + less39M + less49M + less59M
                        + less69M + more70M;// 所有男性人数
                params.put("M_T", String.valueOf(sumM));

                params.put("LITTLE18_W", String.valueOf(less18W));
                params.put("LITTLE30_W", String.valueOf(less29W));
                params.put("LITTLE40_W", String.valueOf(less39W));
                params.put("LITTLE50_W", String.valueOf(less49W));
                params.put("LITTLE60_W", String.valueOf(less59W));
                params.put("LITTLE70_W", String.valueOf(less69W));
                params.put("MORE69_W", String.valueOf(more70W));
                Integer sumW = less18W + less29W + less39W + less49W + less59W
                        + less69W + more70W;// 所有女性人数
                params.put("W_T", String.valueOf(sumW));

                params.put("LITTLE18_T", String.valueOf(less18W + less18M));
                params.put("LITTLE30_T", String.valueOf(less29W + less29M));
                params.put("LITTLE40_T", String.valueOf(less39W + less39M));
                params.put("LITTLE50_T", String.valueOf(less49W + less49M));
                params.put("LITTLE60_T", String.valueOf(less59W + less59M));
                params.put("LITTLE70_T", String.valueOf(less69W + less69M));
                params.put("MORE69_T", String.valueOf(more70W + more70M));
                params.put("T_T", String.valueOf(sumW + sumM));

            } else {
                params.put("REGISTER_BEGIN", "");
                params.put("REGISTER_END", "");

                params.put("LITTLE18_M", "0");
                params.put("LITTLE30_M", "0");
                params.put("LITTLE40_M", "0");
                params.put("LITTLE50_M", "0");
                params.put("LITTLE60_M", "0");
                params.put("LITTLE70_M", "0");
                params.put("MORE69_M", "0");
                params.put("M_T", "0");
                params.put("LITTLE18_W", "0");
                params.put("LITTLE30_W", "0");
                params.put("LITTLE40_W", "0");
                params.put("LITTLE50_W", "0");
                params.put("LITTLE60_W", "0");
                params.put("LITTLE70_W", "0");
                params.put("MORE69_W", "0");
                params.put("W_T", "0");
                params.put("LITTLE18_T", "0");
                params.put("LITTLE30_T", "0");
                params.put("LITTLE40_T", "0");
                params.put("LITTLE50_T", "0");
                params.put("LITTLE60_T", "0");
                params.put("LITTLE70_T", "0");
                params.put("MORE69_T", "0");
                params.put("T_T", "0");
            }
            params.put("M_NUM", StringUtils.isNotEmpty(ball.getPicInspectM())? prefix + ball.getPicInspectM() : null);
            params.put("W_NUM", StringUtils.isNotEmpty(ball.getPicInspectW())? prefix + ball.getPicInspectW() : null);
            params.put("T_NUM", StringUtils.isNotEmpty(ball.getPicInspectT())? prefix + ball.getPicInspectT() : null);
            params.put("M_W_T", StringUtils.isNotEmpty(ball.getPicAgeColumnar())? prefix + ball.getPicAgeColumnar() : null);
            params.put("M_T_P", StringUtils.isNotEmpty(ball.getPicAgeM())? prefix + ball.getPicAgeM() : null);
            params.put("W_T_P", StringUtils.isNotEmpty(ball.getPicAgeW())? prefix + ball.getPicAgeW() : null);
            params.put("T_T_P", StringUtils.isNotEmpty(ball.getPicAgePie())? prefix + ball.getPicAgePie() : null);

        }

        //放入图表数据
        //年龄分布
//        List<HashMap> nlfb = ballCheckReportService.ageanalyze_zy(analyzeId);
//        params.put("nlfb", nlfb);
        return params;
    }


    /**
     * 根据两个数返回百分比的字符串
     *
     * @param a
     * @param b
     * @return String
     * @Title: returnPercent
     * @author YINZL
     * @since 2017年2月7日 V 1.0
     */
    public String returnPercent(Integer a, Integer b) {
        if (b != 0) {
            float size = (float) a / b * 100;
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
            String filesize = df.format(size);// 返回的是String类型的
            return filesize + "%";
        } else {
            return "0.00%";
        }
    }


    /**
     * 获取1_2模板中所有的数据及坐标
     *
     * @return Map<String, Object>
     * @Title: replaceData1_2
     * @author YINZL
     * @since 2017年2月4日 V 1.0
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> replaceData1_2(CReprotNewDParam param) {
        String analyzeId = param.getAnalyzeId();
        Map<String, Object> params = new HashMap<String, Object>();

        List<HashMap> mapList = new ArrayList<HashMap>();
        //综合分析-项目參检（健康）
        List<FxItemscheck> items = fxItemscheckService.getListBySampleId(analyzeId);
        if (items != null && items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> result = new HashMap<String, String>();
                String num = "";
                if (i != 0) {
                    num = num + i;
                }

                result.put("CJQK.DEP_NAME"+ num, !StringUtils.isEmpty(items
                        .get(i).getDepName()) ? items.get(i).getDepName() : "");
                result.put("CJQK.ITEM_NAME"+ num, !StringUtils.isEmpty(items
                        .get(i).getItemName()) ? items.get(i).getItemName()
                        : "");
                result.put("CJQK.O_M_N"+ num,
                        items.get(i).getCheckMale() != null ? items.get(i)
                                .getCheckMale().toString() : "");
                result.put("CJQK.O_W_N"+ num,
                        items.get(i).getCheckFemale() != null ? items.get(i)
                                .getCheckFemale().toString() : "");
                result.put("CJQK.O_T_N"+ num,
                        items.get(i).getCheckTotal() != null ? items.get(i)
                                .getCheckTotal().toString() : "");
                result.put("CJQK.T_M_N"+ num,
                        items.get(i).getAllMale() != null ? items.get(i)
                                .getAllMale().toString() : "");
                result.put("CJQK.T_W_N"+ num,
                        items.get(i).getAllFemale() != null ? items.get(i)
                                .getAllFemale().toString() : "");
                result.put("CJQK.T_T_N"+ num,
                        items.get(i).getAllTotal() != null ? items.get(i)
                                .getAllTotal().toString() : "");

                result.put(
                        "CJQK.O_M_B"+ num,
                        items.get(i).getPerMale() != null ? String
                                .valueOf((MathUtil.multiply(items.get(i)
                                        .getPerMale(), 100.0)))
                                + "%" : "");
                result.put(
                        "CJQK.O_W_B"+ num,
                        items.get(i).getPerFemale() != null ? String
                                .valueOf((MathUtil.multiply(items.get(i)
                                        .getPerFemale(), 100.0)))
                                + "%" : "");
                result.put(
                        "CJQK.O_A_B"+ num,
                        items.get(i).getPerTotal() != null ? String
                                .valueOf((MathUtil.multiply(items.get(i)
                                        .getPerTotal(), 100.0)))
                                + "%" : "");
                result.put("CJQK.NUM"+ num,
                        items.get(i).getRowno() != null ? items.get(i)
                                .getRowno().toString() : "");

                mapList.add(result);
            }
        }
        params.put("CJQK", mapList);
        for (int i = 0; i < mapList.size(); i++) {// 将查询到的模板中所对应的数据存放到prams中
            HashMap hm = mapList.get(i);
            Iterator it = hm.keySet().iterator();
            while (it.hasNext()) {
                String k = it.next().toString();
                Object v = hm.get(k);
                params.put(k, v);
            }
        }
        return params;

    }


    /**
     * 获取2_1模板中所有的数据及坐标
     *
     * @return Map<String, Object>
     * @Title: replaceData2_1
     * @author YINZL
     * @since 2017年2月4日 V 1.0
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> replaceData2_1(String analyzeId) {
        // List<FxDetection> fxDetection = reportAuditDao
        // .findFxDetection(analyzeId);
        List<FxDetection> fxDetection = groupReportMapper.findFxDetection2(analyzeId);
        Map<String, Object> params = new HashMap<String, Object>();
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (fxDetection != null && fxDetection.size() > 0) {
            for (int i = 0; i < fxDetection.size(); i++) {
                HashMap<String, String> result = new HashMap<String, String>();
                FxDetection fx = fxDetection.get(i);


                if (i == 0) {
                    result.put("TYPE", fx.getDepName() != null ? fx.getDepName().toString() : "");
                    result.put("CONCLUSION", fx.getConclusion() != null ? fx.getConclusion().toString()
                            : "");
                    result.put("DETECTION_M", fx.getDetectionMale() != null ? fx.getDetectionMale().toString()
                            : "");
                    result.put("DETECTION_W", fx.getDetectionFemale() != null ? fx.getDetectionFemale().toString()
                            : "");
                    result.put("DETECTION_T", fx.getDetectionTotal() != null ? fx.getDetectionTotal().toString()
                            : "");
                } else {
                    result.put("TYPE" + i, fx.getDepName() != null ? fx.getDepName().toString()
                            : "");
                    result.put("CONCLUSION" + i,
                            fx.getConclusion() != null ? fx.getConclusion().toString() : "");
                    result.put("DETECTION_M" + i,
                            fx.getDetectionMale() != null ? fx.getDetectionMale().toString() : "");
                    result.put("DETECTION_W" + i,
                            fx.getDetectionFemale() != null ? fx.getDetectionFemale().toString() : "");
                    result.put("DETECTION_T" + i,
                            fx.getDetectionTotal() != null ? fx.getDetectionTotal().toString() : "");

                }
                mapList.add(result);
            }
        }
        for (int ii = 0; ii < mapList.size(); ii++) {// 将查询到的模板中所对应的数据存放到prams中
            HashMap hm = mapList.get(ii);
            Iterator it = hm.keySet().iterator();
            while (it.hasNext()) {
                String k = it.next().toString();
                Object v = hm.get(k);
                params.put(k, v);
            }
        }
        params.put("", mapList);

        return params;
    }


    /**
     * 获取2_2模板中所有的数据及坐标
     *
     * @return Map<String, Object>
     * @Title: replaceData2_2
     * @author YINZL
     * @since 2017年2月4日 V 1.0
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> replaceData2_2(String analyzeId) {
//        BallCheckReport check = ballCheckReportMapper.getInfoById(analyzeId);
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("ABNORMAL_M", check.getExceptionM());
//        params.put("ABNORMAL_W", check.getExceptionW());
//        params.put("ABNORMAL_T", check.getExceptionT());
        HashMap map = ballCheckReportService.loadcheckanalyze(analyzeId, null);
        return map;
    }


    private boolean exp_3_0(String modelUrl, String path3_0, String d,
                            List<String> basIds, String path, String sampleId,CReprotNewDVo vo) {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        List<String> expUrls = new ArrayList<String>();
        if (basIds != null && basIds.size() > 0) {
            for (int i = 0; i < basIds.size(); i++) {
                try {
                    worTool = WordTool.getInstance(modelUrl, null);
                    pfddoc = worTool.getDoc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String expurl = path + i + ".docx";
                String basId = basIds.get(i);
                String basconclusion = null;
                List<FxDetection> fx = fxDetectionService.list(new QueryWrapper<FxDetection>()
                        .eq("basconclusion_id", basId)
                        .eq("dep_name", d)
                        .eq("sample_id", sampleId));
                if (fx != null && fx.size() > 0) {
                    basconclusion = fx.get(0).getConclusion();
                }

                Map<String, Object> params = new HashMap<String, Object>();
                if (!StringUtils.isEmpty(basconclusion)) {
                    params.put("BASCONCLUSION", basconclusion);
                } else {
                    params.put("BASCONCLUSION", "");
                }
                if (!StringUtils.isEmpty(basId)) {
                    Basconclusion bas = basconclusionMapper.getInfoById(basId);
                    if(bas==null) {
                        System.out.println("缺少结论词id:"+basId);
                        throw new RuntimeException("缺少结论词id:"+basId);
                    }
                    String suggestion = bas.getSuggestion();
                    if (!StringUtils.isEmpty(suggestion)) {
                        params.put("HEALTH_OFFER", suggestion);
                    } else {
                        params.put("HEALTH_OFFER", "");
                    }
                } else {
                    params.put("HEALTH_OFFER", "");
                }
                if (fx != null && fx.size() > 0) {
                    List<HashMap> mapList = new ArrayList<HashMap>();
                    for (int ii = 0; ii < fx.size(); ii++) {
                        FxDetection detection = fx.get(ii);
                        HashMap<String, String> result = new HashMap<String, String>();
                        String sex = detection.getSex() != null ? detection
                                .getSex() == 0 ? "男" : "女" : " ";
                        if (ii == 0) {
                            result.put(
                                    "PATIENTCODE",
                                    detection.getPatientcode() != null ? detection
                                            .getPatientcode() : "");
                            result.put(
                                    "NAME",
                                    detection.getPatientname() != null ? detection
                                            .getPatientname() : "");
                            result.put("SIX", sex);
                            result.put("AGE",
                                    detection.getAge() != null ? detection
                                            .getAge().toString() : "");
                            result.put(
                                    "DEP",
                                    detection.getOrgDepart() != null ? detection
                                            .getOrgDepart() : "");
                        } else {
                            result.put(
                                    "PATIENTCODE" + ii,
                                    detection.getPatientcode() != null ? detection
                                            .getPatientcode() : "");
                            result.put(
                                    "NAME" + ii,
                                    detection.getPatientname() != null ? detection
                                            .getPatientname() : "");
                            result.put("SIX" + ii, sex);
                            result.put("AGE" + ii,
                                    detection.getAge() != null ? detection
                                            .getAge().toString() : "");
                            result.put(
                                    "DEP" + ii,
                                    detection.getOrgDepart() != null ? detection
                                            .getOrgDepart() : "");
                        }
                        mapList.add(result);

                    }
                    for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                        HashMap hm = mapList.get(j);
                        Iterator it = hm.keySet().iterator();
                        while (it.hasNext()) {
                            String k = it.next().toString();
                            Object v = hm.get(k);
                            params.put(k, v);
                        }
                    }
                    if (mapList != null && mapList.size() > 1) {
                        params.put("", mapList);
                        for (Map.Entry<String, Object> entry : params.entrySet()) {
                            Object value = entry.getValue();
                            Object key = entry.getKey();
                            // 处理多行数据
                            if (value instanceof List) {
                                List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                                try {
                                    pfddoc = worTool.updateTables2(pfddoc,
                                            list, key.toString().trim(),
                                            expurl, 1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } else {
                    params.put("PATIENTCODE", "");
                    params.put("NAME", "");
                    params.put("SIX", "");
                    params.put("AGE", "");
                    params.put("DEP", "");
                }
                resultMap.add(params);
                boolean isTrue = worTool.exportTable(params, "3_1.docx",
                        pfddoc, expurl, worTool, null, null, 1);
                if (isTrue) {
                    expUrls.add(expurl);
                }
            }
        } else {
            try {
                worTool = WordTool.getInstance(modelUrl, null);
                pfddoc = worTool.getDoc();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String expurl = path + "wjyc.docx";
            List<FxDetection> fx = fxDetectionService.list(new QueryWrapper<FxDetection>()
                    .isNull("dep_name")
                    .eq("sample_id", sampleId));

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("BASCONCLUSION", "本次体检项目未见异常");
            params.put("HEALTH_OFFER", "");
            if (fx != null && fx.size() > 0) {
                List<HashMap> mapList = new ArrayList<HashMap>();
                for (int ii = 0; ii < fx.size(); ii++) {
                    FxDetection detection = fx.get(ii);
                    HashMap<String, String> result = new HashMap<String, String>();
                    String sex = detection.getSex() != null ? detection
                            .getSex() == 0 ? "男" : "女" : " ";
                    if (ii == 0) {
                        result.put(
                                "PATIENTCODE",
                                detection.getPatientcode() != null ? detection
                                        .getPatientcode() : "");
                        result.put(
                                "NAME",
                                detection.getPatientname() != null ? detection
                                        .getPatientname() : "");
                        result.put("SIX", sex);
                        result.put("AGE",
                                detection.getAge() != null ? detection.getAge()
                                        .toString() : "");
                        result.put(
                                "DEP",
                                detection.getOrgDepart() != null ? detection
                                        .getOrgDepart() : "");
                    } else {
                        result.put(
                                "PATIENTCODE" + ii,
                                detection.getPatientcode() != null ? detection
                                        .getPatientcode() : "");
                        result.put(
                                "NAME" + ii,
                                detection.getPatientname() != null ? detection
                                        .getPatientname() : "");
                        result.put("SIX" + ii, sex);
                        result.put("AGE" + ii,
                                detection.getAge() != null ? detection.getAge()
                                        .toString() : "");
                        result.put(
                                "DEP" + ii,
                                detection.getOrgDepart() != null ? detection
                                        .getOrgDepart() : "");
                    }
                    mapList.add(result);

                }
                for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                    HashMap hm = mapList.get(j);
                    Iterator it = hm.keySet().iterator();
                    while (it.hasNext()) {
                        String k = it.next().toString();
                        Object v = hm.get(k);
                        params.put(k, v);
                    }
                }
                if (mapList != null && mapList.size() > 1) {
                    params.put("", mapList);
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        Object value = entry.getValue();
                        Object key = entry.getKey();
                        // 处理多行数据
                        if (value instanceof List) {
                            List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                            try {
                                pfddoc = worTool.updateTables2(pfddoc, list,
                                        key.toString().trim(), expurl, 1);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            } else {
                params.put("PATIENTCODE", "");
                params.put("NAME", "");
                params.put("SIX", "");
                params.put("AGE", "");
                params.put("DEP", "");
            }
            resultMap.add(params);
            boolean isTrue = worTool.exportTable(params, "3_1.docx", pfddoc,
                    expurl, worTool, null, null, 1);
            if (isTrue) {
                expUrls.add(expurl);
            }
        }
        //封装返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("deptName",d);
        result.put("data",resultMap);
        if (ObjectUtils.isNotEmpty(vo.getData3_0())){
            vo.getData3_0().add(result);
        }else {
            ArrayList<Map<String, Object>> maps = new ArrayList<>();
            maps.add(result);
            vo.setData3_0(maps);
        }

        if (expUrls != null && expUrls.size() > 0) {
            MargeDoc wordUtil = new MargeDoc();
            wordUtil.mergeDocx(expUrls, path3_0);
            // JoinWords.uniteDoc(expUrls, path3_0);
            for (String e : expUrls) {
                DeleteFileUtil.deleteFile(e);
            }
            return true;
        } else {
            return false;
        }
    }








    /**
     * 获取4_2模板中所有的数据及坐标
     *
     * @return Map<String, Object>
     * @Title: replaceData4_2
     * @author YINZL
     * @since 2017年2月4日 V 1.0
     */
    @SuppressWarnings("rawtypes")
    private List<Map> replaceData4_2(CReprotNewDParam param) {
        String analyzeId = param.getAnalyzeId();
        List<Map> mapList = new ArrayList<>();
        //根据样本ID查询出所有的人员体检号
        List<String> patientcode = groupReportMapper.getAllPatientcode(param.getAnalyzeId());
        if (patientcode != null && patientcode.size() > 0) {
            for (int j = 0; j < patientcode.size(); j++) {
                Map<String, Object> params = new HashMap<String, Object>();
                //综合分析
                List<FxDetection> fxDetection = fxDetectionService.list(new QueryWrapper<FxDetection>()
                        .eq("sample_id", analyzeId)
                        .eq("patientcode", patientcode.get(j).trim()));


                if (fxDetection != null && fxDetection.size() > 0) {
                    FxDetection fx = fxDetection.get(0);
                    params.put("tjh",
                            fx.getPatientcode() != null ? fx.getPatientcode()
                                    : "");
                    params.put("xm",
                            fx.getPatientname() != null ? fx.getPatientname()
                                    : "");
                    params.put("xb",
                            fx.getSex() != null ? fx.getSex() == 0 ? "男" : "女"
                                    : "");
                    params.put("nl", fx.getAge() != null ? fx.getAge()
                            .toString() : "");
                    params.put("bm",
                            fx.getOrgDepart() != null ? fx.getOrgDepart() : "");
                    String str = "";
                    for (int i = 0; i < fxDetection.size(); i++) {
                        String con = fxDetection.get(i).getConclusion();
                        if (!StringUtils.isEmpty(con)) {
                            str = str + (i + 1) + "." + con + "\n";
                        }
                    }
                    params.put("tjjl", !StringUtils.isEmpty(str) ? str : "未见异常");
                    mapList.add(params);
                }
            }
        }
        return mapList;
    }


    /**
     * 导出tempalte_0模板内容及该模板中所涉及的附表内容
     *
     * @return List<String>
     * @Title: expTemplate
     * @author YINZL
     * @since 2017年2月20日 V 1.0
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<String[]> template_0(String template, String analyzeId,
                                           String orderId, String expurl, String expName,ReportConfigVo reportConfigVo,SysBranch sysBranch,CReprotNewDVo vo) {
        Map<String, Object> params = new HashMap<String, Object>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            worTool = WordTool.getInstance(template, null);
            pfddoc = worTool.getDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("ORDER_NO", orderId);
        params.put("ZK_INSPECTION", ReportConstants.zk_inspection);
        params.put("SEND_TIME", dateFormat.format(new Date()));
        //团检报告主表
        BallCheckReport ball = ballCheckReportMapper.getInfoById(analyzeId);

        //获取检字
        params.put("JZ", reportConfigVo.getInspect());
        params.put("CENTERORGNAME", reportConfigVo.getProducer());
        params.put("ADDRESS", reportConfigVo.getAddress());
        params.put("PHONE", reportConfigVo.getPhone());
        params.put("EMAIL", reportConfigVo.getEmail());
        params.put("POSTALCODE", reportConfigVo.getPostalCode());
        params.put("FAX", reportConfigVo.getFax());
        params.put("CERTIFICATENO", reportConfigVo.getCertificateNo());



        String sglx = null;
        if (ball != null) {
            sglx = ball.getSglx();
        }
        String[] sglxs = !StringUtils.isEmpty(sglx) ? sglx.split(",") : null;
        if (ball != null) {
            params.put("DEP_NAME",
                    ball.getOrgName() != null ? ball.getOrgName() : "");

            if (StringUtils.isNotEmpty(sglx)) {

                if (sglxs != null && sglxs.length > 0) {
                    String type = "上岗前 □";
                    String type1 = "在岗期间 □";
                    String type2 = " 离岗时 □";
                    String type3 = "离岗后 □";
                    String type4 = "应急 □";
                    String job = "";
                    String job1 = "";
                    String job2 = "";
                    String job3 = "";
                    String job4 = "";

                    for (int i = 0; i < sglxs.length; i++) {
                        String lx = sglxs[i];
                        if (!StringUtils.isEmpty(lx)) {
                            if ("0".equals(lx)) {
                                type = "上岗前 √";
                                job = "上岗前";
                            } else if ("1".equals(lx)) {
                                type1 = "在岗期间 √";
                                job1 = "在岗期间";
                            } else if ("2".equals(lx)) {
                                type2 = "离岗时 √";
                                job2 = "离岗时";
                            } else if ("3".equals(lx)) {
                                type3 = "离岗后 √";
                                job3 = "离岗后";
                            } else if ("4".equals(lx)) {
                                type4 = "应急 √";
                                job4 = "应急";
                            }
                        }
                    }
                    /************************* 将类型连成字符串 开始 ***************/
                    String jobs = "";
                    if (!StringUtils.isEmpty(job)) {
                        if (!StringUtils.isEmpty(jobs)) {
                            jobs = jobs + "、" + job;
                        } else {
                            jobs = job;
                        }
                    }

                    if (!StringUtils.isEmpty(job1)) {
                        if (!StringUtils.isEmpty(jobs)) {
                            jobs = jobs + "、" + job1;
                        } else {
                            jobs = job1;
                        }
                    }
                    if (!StringUtils.isEmpty(job2)) {
                        if (!StringUtils.isEmpty(jobs)) {
                            jobs = jobs + "、" + job2;
                        } else {
                            jobs = job2;
                        }
                    }
                    if (!StringUtils.isEmpty(job3)) {
                        if (!StringUtils.isEmpty(jobs)) {
                            jobs = jobs + "、" + job3;
                        } else {
                            jobs = job3;
                        }
                    }
                    if (!StringUtils.isEmpty(job4)) {
                        if (!StringUtils.isEmpty(jobs)) {
                            jobs = jobs + "、" + job4;
                        } else {
                            jobs = job4;
                        }
                    }

                    /************************* 将类型连成字符串 结束 ***************/
                    //潍坊去除离岗和应急
                    if (StringUtils.equals(loadProperties.name, "weifang")){
                        params.put("CHECK_TYPE", type + " " + type1 + " " + type2);
                    }else {
                        params.put("CHECK_TYPE", type + " " + type1 + " " + type2
                                + " " + type3 + " " + type4);
                    }
                    params.put("ON_JOB_STATUS", jobs);
                } else {
                    params.put("CHECK_TYPE", "");
                    params.put("ON_JOB_STATUS", "");
                }

            } else {
                params.put("CHECK_TYPE", "");
                params.put("ON_JOB_STATUS", "");
            }
        } else {
            params.put("DEP_NAME", "");
            params.put("CHECK_TYPE", "");
            params.put("ON_JOB_STATUS", "");
        }

        //团检报告人员样本表
        List<SamplePerson> samp = samplePersonMapper.selectList(new QueryWrapper<SamplePerson>().eq("group_id", analyzeId));
        if (samp != null) {
            params.put("SHOULD_NUM", String.valueOf(samp.size()));
        } else {
            params.put("SHOULD_NUM", "");
        }
        List<FxCompletion> fxCompletion = fxCompletionService.list(new QueryWrapper<FxCompletion>()
                .eq("sample_id", analyzeId).eq("registered", 1));
        if (fxCompletion != null) {
            Date start = null;
            Date end = null;
            for (FxCompletion fc : fxCompletion) {
                if (fc.getRegistered() != null
                        && fc.getRegistered().intValue() == 1
                        && fc.getDateregister() != null) {
                    if (start == null || fc.getDateregister().before(start)) {
                        start = fc.getDateregister();
                    }
                }
                if (fc.getZytjzt() != null && fc.getZytjzt().intValue() > 0
                        && fc.getDateregisternotime() != null) {
                    if (end == null || fc.getDateregisternotime().after(end)) {
                        end = fc.getDateregisternotime();
                    }
                }
            }
            // 实际的登记和总检时间
            SimpleDateFormat inspectFormat = new SimpleDateFormat("yyyy.MM.dd");
            params.put("INSPECT_TIME",
                    (start == null ? "" : inspectFormat.format(start)) + " — "
                            + (end == null ? "" : inspectFormat.format(end)));
            //检查人数需要去掉复查和补检的
            long count = fxCompletionService.count(new QueryWrapper<FxCompletion>()
                    .eq("sample_id", analyzeId)
                    .eq("registered", 1)
                    .eq("f_registered", 1));

            params.put("ACTUAL_NUM", count == 0? String.valueOf(fxCompletion.size()) : String.valueOf(count));
        } else {
            params.put("ACTUAL_NUM", "");
        }
        String harmIds = null;
        //获取样本分析中某个类型的危害因素都有哪些危害因素
        String harmClas = findHazardsBySampleId(analyzeId);// 主要职业病危害因素
        if (!StringUtils.isEmpty(harmClas)) {
            String[] strs = harmClas.split("!");
            if (strs != null && strs.length == 2) {
                harmIds = !StringUtils.isEmpty(strs[1]) ? strs[1] : null;
//				String harmClass = !StringUtils.isEmpty(strs[0]) ? strs[0] : "";
//				params.put("HARM_CLASS", harmClass);
            } else {
//				params.put("HARM_CLASS", "");
            }
        } else {
//			params.put("HARM_CLASS", "");
        }
        String[] hs = getFourHarmClass(analyzeId);
        params.put("HARM_CLASS", hs[0]);
        params.put("HAGARD_NAMES", hs[1]);
        String harmItems = "";
        List<HashMap> mapList = new ArrayList<HashMap>();
        List<String[]> harmAndIllness = new ArrayList<String[]>();// 表格的三个字段所组成的数组集合???
        if (sglxs != null && sglxs.length > 0) {
            for (int i = 0; i < sglxs.length; i++) {
                String typeName = null;
                String type = sglxs[i];
                if (type.equals("0")) {
                    typeName = "(上岗前)";
                } else if (type.equals("1")) {
                    typeName = "(在岗期间)";
                } else if (type.equals("2")) {
                    typeName = "(离岗时)";
                } else if (type.equals("3")) {
                    typeName = "(离岗后)";
                } else if (type.equals("4")) {
                    typeName = "(应急)";
                }
                // 删去危害因素后的“（在岗期间）”
                typeName = "";
                // 检查项目（按照职业病危害因素分别列出，体现不同因素的项目不同）
                String harmDepartment = findHarmDepartment(harmIds, type, typeName);
                harmItems = harmDepartment;
                harmAndIllness = findHarmAndIllness(harmAndIllness, harmIds, type, typeName);

            }
            // 对查询出来之后的数组集合对应相应的坐标进行赋值
            if (harmAndIllness != null && harmAndIllness.size() > 0) {
                for (int h = 0; h < harmAndIllness.size(); h++) {
                    String[] str = harmAndIllness.get(h);
                    HashMap<String, String> result = new HashMap<String, String>();
                    if (str != null && str.length == 3) {
                        if (h == 0) {
                            result.put("HAGARD", str[0]);
                            result.put("INDUSTRIAL_DISEASE", str[1]);
                            result.put("PROFESSIONAL_CONTRAINDICATED", str[2]);

                        } else {
                            result.put("HAGARD" + h, str[0]);
                            result.put("INDUSTRIAL_DISEASE" + h, str[1]);
                            result.put("PROFESSIONAL_CONTRAINDICATED" + h,
                                    str[2]);
                        }
                        mapList.add(result);
                    }
                }
            }
            // 对赋值之后的坐标表格进行增行操作
            for (int j = 0; j < mapList.size(); j++) {// 将查询到的模板中所对应的数据存放到prams中
                HashMap hm = mapList.get(j);
                Iterator it = hm.keySet().iterator();
                while (it.hasNext()) {
                    String k = it.next().toString();
                    Object v = hm.get(k);
                    params.put(k, v);
                }
            }
            if (mapList != null && mapList.size() > 1) {
                params.put("", mapList);
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    Object key = entry.getKey();
                    // 处理多行数据
                    if (value instanceof List) {
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) value;
                        try {
                            pfddoc = worTool.updateTables2(pfddoc, list, key
                                    .toString().trim(), expurl, 1);// 导出位置

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        params.put("HARM_DEPARTMENT", harmItems);



        String danagerIds = "";
        if (harmIds != null) {
            String[] strs = harmIds.split(",");
            if (strs != null && strs.length > 0) {
                for (int s = 0; s < strs.length; s++) {
                    String[] str = strs[s].split(":");
                    if (str != null && str.length == 2) {
                        if (s == 0) {
                            danagerIds = str[1];
                        } else {
                            danagerIds = danagerIds + "," + str[1];
                        }
                    }
                }
            }

        }

        String diagnosis = findDiagnosis(danagerIds, sglx);
        params.put("DIAGNOSIS_FROM", diagnosis);

        CustomerMessageDto customerMessage = findCustomerMessage(analyzeId);
        if (customerMessage != null) {
            params.put("STAFF_NUM", customerMessage.getSjcyrs());
            params.put("PRODUCT_NAME", customerMessage.getZycp());
            params.put("TECHNOLOGICAL_PROCESS", customerMessage.getGylc());
            params.put("MATERIAL_NAME", customerMessage.getZyyfl());
            if (StringUtils.isNotEmpty(customerMessage.getZybwhys())) {
                List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", customerMessage.getZybwhys().split(",")));
                StringBuilder harmNames = new StringBuilder();
                for (int i = 0, s = harms.size(); i < s; i++) {
                    if (i != s - 1) {
                        harmNames.append(harms.get(i).getHarmName() + ",");
                    } else {
                        harmNames.append(harms.get(i).getHarmName());
                    }
                }
//				params.put("HAGARD_NAMES", harmNames.toString());
            }
        } else {
            params.put("STAFF_NUM", "");
            params.put("PRODUCT_NAME", "");
            params.put("TECHNOLOGICAL_PROCESS", "");
            params.put("MATERIAL_NAME", "");
//			params.put("HAGARD_NAMES", "");
        }
        //团检报告人员样本表
        List<SamplePerson> samplePerson = samplePersonMapper.selectList(new QueryWrapper<SamplePerson>().eq("ball_check_id", analyzeId));
        if (samplePerson != null && samplePerson.size() > 0) {
            params.put("SHOULD_NUM", samplePerson.size());
        } else {
            params.put("SHOULD_NUM", "");
        }
        // String diseaseNameCount =
        // reportAuditDao.countAllDisease(ball.getGroupId().trim());//模板中LAST_CASE的数据
        params.put("LAST_CASE", ball.getBgfx() != null ? ball.getBgfx() : "");// 上次报告分析数据
        // String bodyHarm = reportAuditDao.findBodyHarm(danagerIds);
        // params.put("FOR_PERSON_INFLUENCE", bodyHarm);
        String[] tableList = new String[6];
        // 都有哪些附表数组中的0：疑似职业病表；1：职业禁忌证表；2：复查人数表；
        // 3：其他疾病或异常表；4：未见异常表；5：漏检人员及项目表

        List<String[]> count = null;
        try {
            //查询所有检查出结果的数量
            count = countAllSum(analyzeId, sglx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (count != null && count.size() == 3) {
            String[] allCount = count.get(0);
            String[] parr = count.get(2);
            tableList = count.get(1);
            if (allCount != null && allCount.length == 6) {
                params.put("SUSPECTED_OCCUPATIONAL_DISEASE", allCount[0]);
                params.put("PROFESSIONAL_CONTRAINDICATED_T", allCount[1]);
                params.put("REVIEW_OF_THE_SITUATION", allCount[2]);
                params.put("OTHER_EXCEPTIONS", allCount[3]);
                params.put("NO_ABNORMALITY_SEEN", allCount[4]);
                params.put("LEAK_DETECTION", allCount[5]);
                params.put("P1", parr[0]);
                params.put("P2", parr[1]);
                params.put("P3", parr[2]);
                params.put("P4", parr[3]);
                params.put("P5", parr[4]);
                params.put("P6", parr[5]);
                if (!"0".equals(parr[2])) {
                    StringBuilder REVIEW_OF_THE_SITUATION = new StringBuilder(
                            "本次职业健康检查发现");
                    List<FxReviewInfo> ris = fxReviewInfoMapper.selectList(new QueryWrapper<FxReviewInfo>()
                            .orderByAsc("harm_name").eq("sample_id", analyzeId));
                    String fh = null;
                    for (FxReviewInfo ri : ris) {
                        String harmName = ri.getHarmName();
                        if (!harmName.equals(fh)) {
                            REVIEW_OF_THE_SITUATION.append(harmName + "作业");
                            fh = harmName;
                        }
                        REVIEW_OF_THE_SITUATION.append(ri.getDiagnosis()
                                + ri.getNum() + "人，建议" + ri.getSummaryText()
                                + ",");
                    }
                    params.put(
                            "REVIEW_OF_THE_SITUATION",
                            REVIEW_OF_THE_SITUATION.substring(0,
                                    REVIEW_OF_THE_SITUATION.length() - 1)
                                    + "（名单详见附表3及个体报告书）\n");
                }
            } else {
                // params.put("SUSPECTED_OCCUPATIONAL_DISEASE", "未发现疑似职业病人");
                // params.put("PROFESSIONAL_CONTRAINDICATED", "未发现职业禁忌证人");
                // params.put("REVIEW_OF_THE_SITUATION",
                // "未发现职业健康检查职业病危害效应相关指标异常需要复查人员");
                // params.put("OTHER_EXCEPTIONS", "未发现其他异常结果人员");
                // params.put("NO_ABNORMALITY_SEEN", "未发现本次职业健康检查未见异常");
                params.put("SUSPECTED_OCCUPATIONAL_DISEASE", "");
                params.put("PROFESSIONAL_CONTRAINDICATED", "");
                params.put("REVIEW_OF_THE_SITUATION", "");
                params.put("OTHER_EXCEPTIONS", "");
                params.put("NO_ABNORMALITY_SEEN", "");
                params.put("LEAK_DETECTION", "");
                params.put("P1", "0");
                params.put("P2", "0");
                params.put("P3", "0");
                params.put("P4", "0");
                params.put("P5", "0");
                params.put("P6", "0");
            }

        } else {
            params.put("SUSPECTED_OCCUPATIONAL_DISEASE", "");
            params.put("PROFESSIONAL_CONTRAINDICATED", "");
            params.put("REVIEW_OF_THE_SITUATION", "");
            params.put("OTHER_EXCEPTIONS", "");
            params.put("NO_ABNORMALITY_SEEN", "");
            params.put("LEAK_DETECTION", "");
            params.put("P1", "0");
            params.put("P2", "0");
            params.put("P3", "0");
            params.put("P4", "0");
            params.put("P5", "0");
            params.put("P6", "0");
            // params.put("SUSPECTED_OCCUPATIONAL_DISEASE", "未发现疑似职业病人");
            // params.put("PROFESSIONAL_CONTRAINDICATED", "未发现职业禁忌证人");
            // params.put("REVIEW_OF_THE_SITUATION",
            // "未发现职业健康检查职业病危害效应相关指标异常需要复查人员");
            // params.put("OTHER_EXCEPTIONS", "未发现其他异常结果人员");
            // params.put("NO_ABNORMALITY_SEEN", "未发现本次职业健康检查未见异常");
        }

        boolean isTrue = worTool.exportTable(params, expName, pfddoc, expurl,
                worTool, null, null, 0);
        String[] istrue = new String[1];
        if (isTrue) {
            istrue[0] = "true";
        } else {
            istrue[0] = "false";
        }
        List<String[]> returnList = new ArrayList<String[]>();
        returnList.add(istrue);
        returnList.add(tableList);
        vo.setTemplate_0(params);
        return returnList;
    }

    /**
     * 查询所有检查出结果的数量
     *
     * @param analyzeId
     * @param sglx
     * @return
     */
    private List<String[]> countAllSum(String analyzeId, String sglx) {
        String[] strs = new String[6];
        String[] tableNo = new String[6];
        String[] peopleNo = new String[6];
        // 1 可疑职业病
        // 2 职业禁忌证
        // 3 复查
        // 4 其他疾病或异常
        // 5 目前未见异常
        List<SerialnoCountDto> list = groupReportMapper.serialnoCount(analyzeId);
        int num = 1;
        if (list != null && list.size() > 0) {
            SerialnoCountDto obj = list.get(0);
            peopleNo[0] = obj.getCount1();
            peopleNo[1] = obj.getCount2();
            peopleNo[2] = obj.getCount3();
            peopleNo[3] = obj.getCount4();
            peopleNo[4] = obj.getCount5();
            if (obj.getCount1() != null && !String.valueOf(obj.getCount1()).equals("0")) {
                tableNo[0] = String.valueOf(num);
                num++;
                //查询结论1
                List<FindSerialno1Dto> l = groupReportMapper.findSerialno1(analyzeId);

                StringBuilder builder = new StringBuilder("发现");
                String hid = "";
                for (int i = 0; i < l.size(); i++) {
                    FindSerialno1Dto os = l.get(i);
                    String harmId = os.getId().toString();
                    if (!hid.equals(harmId)) {
                        hid = harmId;
                        builder.append(os.getHarmName().toString());
                    }
                    builder.append(os.getOccupationDiseast().toString());
                    builder.append(os.getCount().toString());
                    builder.append("人,");
                }
                builder.append("建议到有资质的职业病诊断机构明确诊断。" + "（名单详见附表" + 1
                        + "及个体报告书）\n");
                strs[0] = builder.toString();
            } else {
                strs[0] = "未发现疑似职业病" + "（名单详见附表" + 1 + "及个体报告书）\n";
            }

            // strs[1]=
            // obj[1]!=null&&!String.valueOf(obj[1]).equals("0")?"\n发现"+String.valueOf(obj[1])+"人：建议调离或者暂时脱离原工作岗位。"+"（名单详见附表"+2+"及个体报告书）\n":
            // ("\n未发现职业禁忌证病人"+"（名单详见附表"+2+"及个体报告书）\n");
            // strs[1]=
            // obj[1]!=null&&!String.valueOf(obj[1]).equals("0")?"发现"+String.valueOf(obj[1])+"人：建议调离或者暂时脱离原工作岗位。"+"（名单详见附表"+num+"及个体报告书）":"未发现职业禁忌证病人";
            if (obj.getCount2() != null && !String.valueOf(obj.getCount2()).equals("0")) {
                tableNo[1] = String.valueOf(num);
                num++;
                //查询结论2
                List<FindSerialno1Dto> l = groupReportMapper.findSerialno2(analyzeId);
                StringBuilder builder = new StringBuilder("发现");
                String hid = "";
                for (int i = 0; i < l.size(); i++) {
                    FindSerialno1Dto os = l.get(i);
                    String harmId = os.getId().toString();
                    if (!hid.equals(harmId)) {
                        hid = harmId;
                        builder.append(os.getHarmName().toString());
                    }
                    builder.append(os.getDiagnosis().toString());
                    builder.append(os.getCount().toString());
                    builder.append("人,");
                }
                builder.append("建议调离或者暂时脱离原工作岗位。" + "（名单详见附表" + 2 + "及个体报告书）\n");
                strs[1] = builder.toString();
            } else {
                strs[1] = "未发现职业禁忌证病人" + "（名单详见附表" + 2 + "及个体报告书）\n";
            }

            // strs[2]=
            // obj[2]!=null&&!String.valueOf(obj[2]).equals("0")?"\n本次职业健康检查发现需要复查人数"+String.valueOf(obj[2])+"（名单详见附表"+3+"及个体报告书）\n":"\n";
            // strs[2]=
            // obj[2]!=null&&!String.valueOf(obj[2]).equals("0")?"本次职业健康检查发现需要复查人数"+String.valueOf(obj[2])+"（名单详见附表"+num+"及个体报告书）":"未发现职业健康检查职业病危害效应相关指标异常需要复查人员";
            if (obj.getCount3() != null && !String.valueOf(obj.getCount3()).equals("0")) {
                tableNo[2] = String.valueOf(num);
                num++;
                //查询结论3
                List<FindSerialno1Dto> l = groupReportMapper.findSerialno3(analyzeId);
                StringBuilder builder = new StringBuilder("本次职业健康检查发现");
                String hid = "";
                for (int i = 0; i < l.size(); i++) {
                    FindSerialno1Dto os = l.get(i);
                    String harmId = os.getId().toString();
                    if (!hid.equals(harmId)) {
                        hid = harmId;
                        builder.append(os.getHarmName().toString());
                    }
                    builder.append(os.getDiagnosis().toString());
                    builder.append(os.getCount().toString());
                    builder.append("人,建议");
                    builder.append(os.getSummaryText() == null ? "" : os.getSummaryText().toString());
                }
                builder.append("（名单详见附表3及个体报告书）\n");
                strs[2] = builder.toString();
            } else {
                strs[2] = "（名单详见附表3及个体报告书）\n";
            }

            // strs[3]=
            // obj[3]!=null&&!String.valueOf(obj[3]).equals("0")?"不属于本次职业健康检查目标疾病"+String.valueOf(obj[3])+"人，建议到相关专科医院诊治。"+"（名单详见附表"+num+"及个体报告书）":"未发现其他异常结果人员";
            strs[3] = obj.getCount4() != null && !String.valueOf(obj.getCount4()).equals("0") ? "不属于本次职业健康检查目标疾病"
                    + String.valueOf(obj.getCount4())
                    + "人，建议到相关专科医院诊治。"
                    + "（名单详见附表"
                    + 4
                    + "及个体报告书）\n"
                    : ("" + "（名单详见附表" + 4 + "及个体报告书）\n");
            if (obj.getCount4() != null && !String.valueOf(obj.getCount4()).equals("0")) {
                tableNo[3] = String.valueOf(num);
                num++;

            }

            // strs[4]=
            // obj[4]!=null&&!String.valueOf(obj[4]).equals("0")?"本次职业健康检查未见异常"+String.valueOf(obj[4])+"人。"+"（名单详见附表"+num+"及个体报告书）":"未发现本次职业健康检查未见异常";
            strs[4] = obj.getCount5() != null && !String.valueOf(obj.getCount5()).equals("0") ? "本次职业健康检查未见异常"
                    + String.valueOf(obj.getCount5())
                    + "人。"
                    + "（名单详见附表"
                    + 5
                    + "及个体报告书）\n"
                    : ("" + "（名单详见附表" + 5 + "及个体报告书）\n");
            if (obj.getCount5() != null && !String.valueOf(obj.getCount5()).equals("0")) {
                tableNo[4] = String.valueOf(num);
                num++;

            }

        }
        List<Integer> lists = groupReportMapper.getCountByAnalyzeId(analyzeId);

        if (lists != null && lists.size() == 1) {
            String obj = lists.get(0).toString();
            peopleNo[5] = obj;
            // if (!StringUtils.isEmpty(obj)) {
            if (!"0".equals(obj)) {
                strs[5] = "本次职业健康检查漏检人员及漏检项目共" + obj + "人。（名单详见附表" + 6
                        + "及个体报告书）";
                tableNo[5] = String.valueOf(num);
            } else {
                // strs[5]="本次体检未发现漏检人员";
                strs[5] = "（名单详见附表" + 6 + "及个体报告书）";
            }
        } else {
            peopleNo[0] = "0";
            // strs[5]="未发现漏检项目";
            strs[5] = "（名单详见附表" + 6 + "及个体报告书）";
        }
        List<String[]> returnStr = new ArrayList<String[]>();
        returnStr.add(strs);
        returnStr.add(tableNo);
        returnStr.add(peopleNo);
        return returnStr;

    }

    /**
     * 查询样本客户的一些信息
     *
     * @param analyzeId
     * @return
     */
    private CustomerMessageDto findCustomerMessage(String analyzeId) {
        List<CustomerMessageDto> list = groupReportMapper.findCustomerMessage(analyzeId);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 根据危害因素的ID组成的字符串查询所有的诊断标准
     *
     * @param danagerIds
     * @param sglxs
     * @return
     */
    private String findDiagnosis(String danagerIds, String sglxs) {
        if (StringUtils.isNotEmpty(danagerIds) && StringUtils.isNotEmpty(sglxs)) {
            String[] danagerId = danagerIds.split(",");
            String[] sglx = sglxs.split(",");
            List<String> list = groupReportMapper.findDiagnosis(danagerId, sglx);
            HashSet<String> set = new HashSet<String>();
            for (int i = 0; i < list.size(); i++) {
                set.addAll(Arrays.asList(list.get(i).toString().split("；")));// 用;拆分后去重复
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> ite = set.iterator();
            while (ite.hasNext()) {
                String s = ite.next();
                if (ite.hasNext()) {
                    sb.append(s + ";\n");
                } else {
                    sb.append(s + ";");
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 查询危害因素和疾病
     *
     * @param returnList
     * @param harmIds
     * @param type
     * @param typeName
     * @return
     */
    public List<String[]> findHarmAndIllness(List<String[]> returnList, String harmIds, String type, String typeName) {

        String[] harmStr = !StringUtils.isEmpty(harmIds) ? harmIds.split(",")
                : null;
        if (harmStr != null && harmStr.length > 0) {
            for (int i = 0; i < harmStr.length; i++) {
                String[] idName = harmStr[i].split(":");
                if (idName != null && idName.length == 2) {
                    List<HarmAndIllnessDto> list = groupReportMapper.findHarmAndIllness(idName[1], type);
                    if (list != null && list.size() > 0) {
                        for (int j = 0; j < list.size(); j++) {
                            HarmAndIllnessDto str = list.get(j);
                            String[] strs = new String[3];
                            strs[0] = idName[0] + typeName;// 危害因素(上岗状态)
                            strs[1] = str.getIndustrialDisease() != null ? String.valueOf(str.getIndustrialDisease())
                                    : "";// 目标疾病(职业病)
                            strs[2] = str.getContraindication() != null ? String.valueOf(str.getContraindication())
                                    : "";// 目标疾病(禁忌症)
                            returnList.add(strs);
                        }
                    }
                }
            }
        }

        return returnList;

    }

    /**
     * 检查项目（按照职业病危害因素分别列出，体现不同因素的项目不同）
     *
     * @param harmIds
     * @param type
     * @param typeName
     * @return
     */
    private String findHarmDepartment(String harmIds, String type, String typeName) {
        String[] harmStr = !StringUtils.isEmpty(harmIds) ? harmIds.split(",")
                : null;
        String harmDepartment = "";
        if (harmStr != null && harmStr.length > 0) {
            for (int i = 0; i < harmStr.length; i++) {
                String[] idName = harmStr[i].split(":");
                String name = "";
                if (idName != null && idName.length == 2) {
                    name = idName[0] + typeName + ":";
                    List<String> list = groupReportMapper.findHarmDepartment(idName[1], type);
                    String items = "";
                    for (int j = 0; j < list.size(); j++) {
                        String str = String.valueOf(list.get(j));
                        if (j == 0) {
                            items = str;
                        } else {
                            items = items + "、" + str;
                        }
                    }
                    name = name + items + "；";
                }
                harmDepartment = harmDepartment + name + "\n";
            }

        }
        return harmDepartment;


    }


    /**
     * 四、主要职业病危害因素 按种类列举
     * 2021.11.22
     */
    private String[] getFourHarmClass(String analyzeId) {
        List<FourHarmClassDto> l = groupReportMapper.getFourHarmClass(analyzeId);
        StringBuilder sb = new StringBuilder();
        List<String> harmNames = new ArrayList<String>();
        for (FourHarmClassDto os : l) {
            sb.append(os.getHarmClass() + "：" + os.getHarmName() + "；\n");
            harmNames.add(os.getHarmName().toString());
        }
        return new String[]{
                sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : ""
                , StringUtils.join(harmNames, "、")
        };

    }


    /**
     * 获取样本分析中主要职业病危害因素
     *
     * @param analyzeId
     * @return List<HashMap>
     * @Title: findHazardsBySampleId
     * @author YINZL
     * @since 2017年2月22日 V 1.0
     */
    private String findHazardsBySampleId(String analyzeId) {
        List<Harm> list = groupReportMapper.findHarm(analyzeId);
        String harmClas = "";
        String harmIds = "";
        if (list.size() > 0) {
            Set<String> harmNames = new HashSet<String>();
            Set<String> harmStrs = new HashSet<String>();
            for (int i = 0; i < list.size(); i++) {
                Harm os = list.get(i);
                harmNames.add(os.getHarmName().toString());
                harmStrs.add(os.getHarmName() + ":" + os.getId());
            }
            //2020.8.13去掉危害因素分类
            harmClas = StringUtils.join(harmNames, "、") + "；";
            harmIds = StringUtils.join(harmStrs, ",");
        } else {
            //兼容老数据 2020-7-28
            List<FxSummary> lists = groupReportMapper.findHarmSql(analyzeId);
            if (lists != null) {
                String className = null;
                String harmName = null;

                int num = 1;
                HashMap<String, String> result = new HashMap<String, String>();
                for (int i = 0; i < lists.size(); i++) {
                    FxSummary str = lists.get(i);
                    if (str != null) {
                        String harm = String.valueOf(str.getHarmName() != null ? str.getHarmName() : "");
                        String clas = String.valueOf(str.getClassName() != null ? str.getClassName() : "");

                        if (i == 0) {
                            className = clas;
                            harmIds = harm + ":"
                                    + String.valueOf(str.getClassName() != null ? str.getClassName() : "");
                        } else {
                            harmIds = harmIds + "," + harm + ":"
                                    + String.valueOf(str.getClassName() != null ? str.getClassName() : "");
                        }

                        if (className.equals(clas)) {
                            className = clas;
                            if (i == 0) {
                                harmName = harm;
                            } else {
                                harmName = harmName + "、" + harm;
                            }
                            if (i + 1 == list.size()) {
                                result.put("HAGARD_TYPE" + num, className);
                                result.put("HAGARD_NAME" + num, harmName);
                                num++;
                                if (harmClas != null && "".equals(harmClas)) {
                                    harmClas = className + "：" + harmName + "；";
                                } else {
                                    harmClas = harmClas + "\n" + className + "："
                                            + harmName + "；";
                                }
                            }
                        } else {
                            if (harmClas != null && "".equals(harmClas)) {
                                harmClas = className + "：" + harmName + "；";
                            } else {
                                harmClas = harmClas + "\n" + className + "："
                                        + harmName + "；";
                            }

                            className = clas;
                            harmName = harm;
                            if (i + 1 == list.size()) {
                                harmClas = harmClas + "\n" + className + "："
                                        + harmName + "；";
                            }
                        }
                    }
                }
            }
        }
        return harmClas + "!" + harmIds;
    }




    public void simpleNumberFooter(XWPFDocument document,String text) throws Exception {
        /**这段代码和下面的代码各能生成一行页眉，最终在word中产生两条页眉，故注释 掉
         //第一页分节时//可能没header  或者段落  ，修改模板  ，增加header再删除文本
         XWPFParagraph  firstpara=document.getHeaderList().get(1).getParagraphs().get(0);
         XWPFRun firstrun=firstpara.createRun();
         firstrun.setText(text);
         firstrun.setFontSize(9);
         firstpara.setBorderBottom(Borders.THICK);
         firstpara.setAlignment(ParagraphAlignment.CENTER);
         firstpara.setVerticalAlignment(TextAlignment.CENTER);
         CTRPr firstrpr = firstrun.getCTR().isSetRPr() ? firstrun.getCTR().getRPr() : firstrun.getCTR()
         .addNewRPr();
         CTFonts firstfonts = firstrpr.isSetRFonts() ? firstrpr.getRFonts() : firstrpr
         .addNewRFonts();
         firstfonts.setAscii("宋体");
         firstfonts.setEastAsia("宋体");
         firstfonts.setHAnsi("宋体");
         */



        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph codePara = new XWPFParagraph(ctp, document);
        XWPFRun r1 = codePara.createRun();
        r1.setText(text);
        r1.setFontSize(9);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR()
                .addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr
                .addNewRFonts();
        fonts.setAscii("宋体");
        fonts.setEastAsia("宋体");
        fonts.setHAnsi("宋体");

        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        codePara.setBorderBottom(Borders.THICK);
        XWPFParagraph[] newparagraphs = new XWPFParagraph[1];
        newparagraphs[0] = codePara;
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(
                document, sectPr);
        headerFooterPolicy.createHeader(STHdrFtr.DEFAULT, newparagraphs);
    }


    /**
     * 上传word
     * @param param
     * @return
     */
    @Override
    public Boolean uploadWord(UploadWordParam param) throws IOException {
        BallCheckReport ball = ballCheckReportMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(ball)){
            throw new ServiceException("该团检报告不存在或已被删除！");
        }
        if (ball.getStatus() == 7){
            throw new ServiceException("当前报告主检已审,不能上传修改！");
        }

        MultipartFile file = param.getFile();
        SysBranch sysBranch = sysBranchMapper.getDefaultBranch();
        //获取pdf
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String path = System.getProperty("user.dir") + "/temp/file/wordexp/group_medical/temporary/" + date;
        WordConvertPDF wcp = new WordConvertPDF();
        String wordUrl = saveFileToDirectory(file, path);
        String pdfUrl = path + "/" + param.getId()+".pdf";
        wcp.wordToPDF(wordUrl,pdfUrl);

        //上传
        String wordUploadUrl = uploadReport(wordUrl,ball,sysBranch);
        ball.setWordUrl(wordUploadUrl);
        String pdfUploadUrl = uploadReport(pdfUrl,ball,sysBranch);
        ball.setPdfUrl(pdfUploadUrl);
        ballCheckReportMapper.updateById(ball);
        return Boolean.TRUE;
    }


    /**
     * MultipartFile 文件保存到本地
     * @param file
     * @param uploadDirectory
     * @throws IOException
     */
    public String saveFileToDirectory(MultipartFile file, String uploadDirectory) throws IOException {
        // 确保目标目录存在，如果不存在则创建
        File directory = new File(uploadDirectory);
        if (!directory.exists()){
            directory.mkdirs();
        }
        // 构建目标文件路径
        String path = uploadDirectory + File.separator + file.getOriginalFilename();
        File targetFile = new File(path);
        // 保存文件到目标路径
        FileCopyUtils.copy(file.getBytes(), targetFile);
        return path;
    }




}

