package com.center.medical.report.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.dao.PatienttypeMapper;
import com.center.medical.data.dao.StencilMaintainMapper;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.param.RecordManageParam;
import com.center.medical.report.bean.param.UploadWordParam;
import com.center.medical.report.bean.utils.CompareReportConclusionsTablePolicy;
import com.center.medical.report.bean.utils.CompareReportInspectionTablePolicy;
import com.center.medical.report.bean.vo.RecordManageVo;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.dao.RecordManageMapper;
import com.center.medical.report.dao.SectionTotalMapper;
import com.center.medical.report.service.RecordManageService;
import com.center.medical.report.service.ReportConfigService;
import com.center.medical.report.utils.CompareReportThreeConclusionsTablePolicy;
import com.center.medical.report.utils.CompareReportThreeInspectionTablePolicy;
import com.center.medical.report.utils.WordConvertPDF;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.CompareReportService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.RowRenderData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 体检者档案表(Peispatientarchive)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:16
 */
@Slf4j
@Service("recordManageService")
@RequiredArgsConstructor
public class RecordManageServiceImpl extends ServiceImpl<RecordManageMapper, Peispatientarchive> implements RecordManageService {

    private final RecordManageMapper recordManageMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final StencilMaintainMapper stencilMaintainMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final CompareReportMapper compareReportMapper;
    private final FileChangeRecordMapper fileChangeRecordMapper;
    private final PatienttypeMapper patienttypeMapper;
    private final NationMapper nationMapper;
    private final SectionTotalMapper sectionTotalMapper;
    private final DictmarriageMapper dictmarriageMapper;
    private final SysBranchMapper sysBranchMapper;
    private final CompareReportService compareReportService;
    private final ReportContentService reportContentService;
    private final ReportConfigService reportConfigService;

    private final SystemConfig systemConfig;
    private final AttachmentService attachmentService;
    private final Snowflake snowflake;

    private static final String TEMPLATE=File.separator+"file"
            +File.separator+"wordmodel"+File.separator+"compare"+File.separator+"thread"
            +File.separator+"compare_tl_double.docx";

    private static final String TEMPLATE_INSPECTION=File.separator+"file"
            +File.separator+"wordmodel"+File.separator+"compare"+File.separator+"thread"
            +File.separator+
            "compare_tl_inspection_double.docx";
    private static final String TEMPLATE_THREE=File.separator+"file"
            +File.separator+"wordmodel"+File.separator+"compare"+File.separator+"thread"
            +File.separator+"compare_tl_three.docx";
    private static final String TEMPLATE_INSPECTION_THREE=File.separator+"file"
            +File.separator+"wordmodel"+File.separator+"compare"+File.separator+"thread"
            +File.separator+
            "compare_tl_inspection_three.docx";



    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<RecordManageVo> getPage(PageParam<RecordManageVo> page, RecordManageParam param) {
        Long offset = (page.getCurrent() - 1) * page.getSize();
        Long limit = page.getSize();
        param.setOffset(offset);
        param.setLimit(limit);
        List<RecordManageVo> list = recordManageMapper.getPage(param);
        IPage<RecordManageVo> iPage = new PageParam<>();
        //放回
        iPage.setRecords(list);
        //查询总数
        Long total = recordManageMapper.getMemberTotal(param);
        iPage.setTotal(total);
        iPage.setCurrent(page.getCurrent());
        iPage.setSize(page.getSize());
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return recordManageMapper.getInfoById(id);
    }


    /**
     * 预览对比报告
     *
     * @param patientcode
     * @param patientcodeBefore
     * @param idPatientarchive
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean previewContrastReport(String patientcode, String patientcodeBefore, String idPatientarchive) throws IOException {
        String templateRealPath = System.getProperty("user.dir") + "/temp/";
        String dir = File.separator+"file"+File.separator+"wordexp"
                +File.separator+"compare_report"+File.separator
                +new SimpleDateFormat("yyyyMMdd").format(new Date())
                +File.separator + idPatientarchive;
        String abPdfPath = dir
                +File.separator+ new SimpleDateFormat("HH_mm_ss_SSS").format(new Date());
        File folder = new File(templateRealPath + dir);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        //渲染数据
        //首页
        Map<String, Object> datas = new HashMap<String, Object>();
        Peispatientarchive archive = peispatientarchiveMapper.getInfoByNo(idPatientarchive);
        String archiveno = archive.getPatientarchiveno() == null ? "" : archive.getPatientarchiveno();
        Peispatient pei = peispatientMapper.getByPatientCode(patientcode);
        //2022.4.6 新需求 不要这次体检的【总检提示】 	//对比报告都是健康报告
        Peispatient peiMax = peispatientMapper.getByPatientCode(patientcodeBefore);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy");
        datas.put("workunit", pei.getOrgName());// 体检单位
        datas.put("department", pei.getOrgDepart());// 部门
        datas.put("patientcode", patientcode);// 体检号
        datas.put("patientname", pei.getPatientname());// 姓名
        datas.put("sex", Render.getSex(pei.getIdSex()));// 性别
        datas.put("record", archiveno);// 档案号
        if (pei.getIdMarriage() != null) {
            //婚姻表
            datas.put("marry", MarriageType.getName(pei.getIdMarriage()));
        }
        String registeryear = pei.getDateregister() != null ? dateFormatter1.format(pei
                .getDateregister()) : "";// 登记年
        String registeryear1 = peiMax.getDateregister() != null ? dateFormatter1.format(peiMax.getDateregister()) : "";// 上一届登记年
        datas.put("register", pei.getDateregister() != null ? dateFormatter.format(pei.getDateregister()) : "");// 登记日期本届
        datas.put("registeryear", registeryear);// 登记年
        datas.put("combo", pei.getExamsuiteName());// 本届套餐
        datas.put("age", pei.getAge() == null ? "" : pei.getAge().intValue());
        //上届
        datas.put("age1", peiMax.getAge() != null ? peiMax.getAge().intValue() : "");// 上一届年龄
        datas.put("patientcode1", peiMax.getPatientcode());// 上一届体检号
        datas.put(
                "register1",
                peiMax.getDateregister() != null ? dateFormatter.format(peiMax
                        .getDateregister()) : "");// 上一届登记日期
        datas.put(
                "registeryear1", registeryear1);// 上一届登记年
        datas.put("combo1", peiMax.getExamsuiteName());// 上一届套餐
        // 2022.4.6 不要本次体检建议
        //各科室小结
        datas.put("conclusion_grid", getDepResult1(patientcode, patientcodeBefore));
        //检验科结果
        List<Map<String, Object>> inspectDatas = getInspectionData(patientcode, patientcodeBefore, registeryear, registeryear1);
        datas.put("inspection_doc", inspectDatas);

        GetDoctorDto doctor = getDoctor(patientcode);

        if (ObjectUtils.isNotEmpty(doctor) && StringUtils.isNotEmpty(doctor.getUsername())) {
            datas.put("totaldoctor", doctor.getUsername());// 本次总检医生
        } else {
            datas.put("totaldoctor", "未知");// 本次总检医生
        }
        //电话
        String content = reportConfigService.getBranchConfig(sysBranchMapper.getDefaultCid());
        if (StringUtils.isNotBlank(content)){
            ReportConfigVo configVo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
            datas.put("hotline", configVo.getPhone());// 本次总检医生电话
        }

        if (ObjectUtils.isNotEmpty(doctor) && ObjectUtils.isNotEmpty(doctor.getTotalTime())) {
            datas.put("reportdate", doctor.getTotalTime());// 总检报告审核日期
        } else {
            datas.put("reportdate", "未知");// 总检报告审核日期
        }

        datas.put("inspection_doc"
                , new DocxRenderData(new File(templateRealPath + TEMPLATE_INSPECTION)
                        , inspectDatas));
        //导出word
        Configure config = Configure.newBuilder()
                .customPolicy("conclusion_grid"
                        , new CompareReportConclusionsTablePolicy())
                .customPolicy("inspect_grid"
                        , new CompareReportInspectionTablePolicy())
                .build();
        XWPFTemplate template = XWPFTemplate.compile(templateRealPath+TEMPLATE
                , config).render(datas);
        template.writeToFile(templateRealPath + abPdfPath + ".docx");
        //转PDF
        WordConvertPDF conver = new WordConvertPDF();
        String docxPath = templateRealPath + abPdfPath + ".docx";
        String pdfPath = templateRealPath + abPdfPath + ".pdf";
        conver.wordToPDF(docxPath, pdfPath);

        //保存 对比报告id
        saveOrUpdateCom(idPatientarchive, patientcode, patientcodeBefore,docxPath,pdfPath);

        //生成的报告,插入到报告内容表中
//        String jsonString = JSON.toJSONString(datas);
//        reportContentService.createReportContent(jsonString, 6, null, null, null, compareReportId, null, null);

        //保存
        return Boolean.TRUE;
    }

    /**
     * 两个体检号获取检验科结果
     *
     * @param patientcode_p
     * @param patientcode_m
     * @param registeryear
     * @param registeryear1
     * @return
     */
    private List<Map<String, Object>> getInspectionData(String patientcode_p, String patientcode_m, String registeryear, String registeryear1) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        //获取检查项目
        List<IDItemIdsDto> itemIds = recordManageMapper.getInspectionData(patientcode_p);
        for (int i = 0; i < itemIds.size(); i++) {
            Map<String, Object> m = new HashMap<String, Object>();
            IDItemIdsDto os = itemIds.get(i);
            String itemId = os.getIdExamfeeitem();
            String itemNamePrn = os.getExamfeeitemNameprn();
            m.put("charges", itemNamePrn);
            data.add(m);
            List<InspectionData2ByThreeDto> l = recordManageMapper.getTwoCodeInspect(patientcode_m, patientcode_p, itemId);
            m.put("registeryear", registeryear);
            m.put("registeryear1", registeryear1);
            m.put("inspect_grid", l);
            List<RowRenderData> rows=new ArrayList<RowRenderData>();
            m.put("inspect_grid", rows);
            for(InspectionData2ByThreeDto arr:l) {
                rows.add(
                        RowRenderData.build(
                                Render.getString(arr.getExamitemNameR()==null?"无":arr.getExamitemNameR()),
                                Render.getString(arr.getExamitemvaluesreportP()==null?"无":arr.getExamitemvaluesreportP()),
                                Render.getString(arr.getStatusP()==null?"无":arr.getStatusP()),
                                Render.getString(arr.getExamitemvaluesreportS()==null?"无":arr.getExamitemvaluesreportS()),
                                Render.getString(arr.getStatusS()==null?"无":arr.getStatusS())
                        )
                );
            }
        }
        return data;
    }

    /**
     * 保存对比报告
     *
     * @param archiveId
     * @param peiCode
     * @param peiMaxCode
     */

    private String saveOrUpdateCom(String archiveId, String peiCode, String peiMaxCode,String docxPath,String pdfPath) {
        //获取默认分中心的报告配置
        SysBranch sysBranch = sysBranchMapper.getDefaultBranch();
        CompareReport compare = new CompareReport();
        //保存
        compare.setPatientarchiveno(archiveId);
        Peispatient pei = peispatientMapper.getByPatientCode(peiCode);
        compare.setPatientcodeThis(pei.getPatientcode());
        compare.setRegisterThis(pei.getDateregister());
        Peispatient peiMax = peispatientMapper.getByPatientCode(peiMaxCode);
        compare.setPatientcodeBefore(peiMax.getPatientcode());
        compare.setRegisterBefore(peiMax.getDateregister());
        compare.setIsDelete(0);
        compare.setId(String.valueOf(snowflake.nextId()));

        //保存word和pdf
        if (StringUtils.isNotEmpty(docxPath)) {
            String url = uploadReport(docxPath,compare,sysBranch);
            compare.setPathWord(url);
        }
        if (StringUtils.isNotEmpty(pdfPath)) {
            String url = uploadReport(pdfPath,compare,sysBranch);
            compare.setPathPdf(url);
        }
        compareReportService.save(compare);
        return compare.getId();
    }



    /**
     * 上传报告
     * @param wordUrl
     * @return
     */
    private String uploadReport(String wordUrl, CompareReport ball, SysBranch sysBranch) {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        File file = new File(wordUrl);
        String originalFilename = file.getName();
        String extName = FileUtil.extName(originalFilename);

        //删除旧文件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getFileType, "对比报告")
                .eq(Attachment::getType, AttachmentType.FILE.value())
                .eq(Attachment::getPatientcode,ball.getId())
                .eq(Attachment::getMemo,extName)
        );


        //添加新文件
        Attachment attachment = new Attachment();
        attachment.setFileType("对比报告");
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
            throw new ServiceException("对比报告上传保存失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());

        return attachment.getFilePath();
    }



    /**
     * 本次没有的项目，上次做了也不显示
     */
    @SuppressWarnings("rawtypes")
    public List<RowRenderData> getDepResult1(String patientcode_p, String patientcode_m) {
        List<DepResult1Dto> list = recordManageMapper.getDepResult1(patientcode_p, patientcode_m);
        List<RowRenderData> data=new ArrayList<RowRenderData>();
        for (int i = 0; i < list.size(); i++) {
            DepResult1Dto obj =  list.get(i);
            data.add(RowRenderData.build(
                    Render.getString(obj.getDeptName()),
                    Render.getString(obj.getPeiC()),
                    Render.getString(obj.getPei1C())
            ));
        }
        return data;
    }

    /**
     * 0号模板根据路径导出到指定位置导出
     *
     * @param pei
     * @param peiMax
     * @param patientcode
     * @param patientarchiveno
     * @return
     */
    private Map<String, Object> exp0(Peispatient pei, Peispatient peiMax, String patientcode, String patientarchiveno) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("workunit",
                !StringUtils.isEmpty(pei.getOrgName()) ? pei.getOrgName() : "");// 体检单位
        params.put("department",
                !StringUtils.isEmpty(pei.getOrgDepart()) ? pei.getOrgDepart()
                        : "");// 部门
        params.put("patientcode", patientcode);// 体检号
        params.put(
                "patientname",
                !StringUtils.isEmpty(pei.getPatientname()) ? pei
                        .getPatientname() : "");// 姓名
        params.put("idSex", Render.getSex(pei.getIdSex()));// 性别
        params.put("record", patientarchiveno);// 档案号
        params.put("marry",
                !StringUtils.isEmpty(pei.getMarriage()) ? pei.getMarriage()
                        : "");// 婚姻情况
        params.put(
                "register",
                pei.getDateregister() != null ? formatter.format(pei
                        .getDateregister()) : "");// 登记日期本届
        params.put(
                "combo",
                !StringUtils.isEmpty(pei.getExamsuiteName()) ? pei
                        .getExamsuiteName() : "");// 本届套餐
        params.put("age", pei.getAge() == null ? "" : pei.getAge().intValue());
        //上届
        params.put("age1", peiMax.getAge() != null ? peiMax.getAge().intValue() : "");// 上一届年龄
        params.put("patientcode1", !StringUtils.isEmpty(peiMax
                .getPatientcode()) ? peiMax.getPatientcode() : "");// 上一届体检号
        params.put(
                "register1",
                peiMax.getDateregister() != null ? formatter.format(peiMax
                        .getDateregister()) : "");// 上一届登记日期
        params.put(
                "combo1",
                !StringUtils.isEmpty(peiMax.getExamsuiteName()) ? peiMax
                        .getExamsuiteName() : "");// 上一届套餐
//        params.put("totaloffer", pei.getAdvice());// 本次体检建议
        GetDoctorDto doctor = getDoctor(patientcode);

        if (ObjectUtils.isNotEmpty(doctor) && StringUtils.isNotEmpty(doctor.getUsername())) {
            params.put("totaldoctor", doctor.getUsername());// 本次总检医生
        } else {
            params.put("totaldoctor", "未知");// 本次总检医生
        }
        if (ObjectUtils.isNotEmpty(doctor) && StringUtils.isNotEmpty(doctor.getPhonenumber())) {
            params.put("hotline", doctor.getPhonenumber());// 本次总检医生电话
        } else {
            params.put("hotline", "未知");// 本次总检医生电话
        }
        if (ObjectUtils.isNotEmpty(doctor) && ObjectUtils.isNotEmpty(doctor.getTotalTime())) {
            params.put("reportdate", doctor.getTotalTime());// 总检报告审核日期
        } else {
            params.put("reportdate", "未知");// 总检报告审核日期
        }

        return params;
    }


    /**
     * 获取医生
     *
     * @param patientcode
     * @return
     */
    public GetDoctorDto getDoctor(String patientcode) {
        //获取医生数据
        GetDoctorDto dto = recordManageMapper.getDoctor(patientcode);
        return dto;
    }


    /**
     * 获取对比报告列表信息
     *
     * @param patientarchiveno
     * @return
     */
    @Override
    public List<HashMap> getCompareReport(String patientarchiveno) {
        //对比报告
        List<CompareReport> compare = compareReportMapper.selectList(new QueryWrapper<CompareReport>()
                .orderByDesc("createDate").eq("patientarchiveno", patientarchiveno));

        List<HashMap> list = new ArrayList<HashMap>();
        if (ObjectUtils.isNotEmpty(compare)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (CompareReport c : compare) {
                HashMap map = new HashMap();
                map.put("id", c.getId());
                if (ObjectUtils.isNotEmpty(c.getPatientarchiveno())) {
                    //体检者档案
                    Peispatientarchive record = peispatientarchiveMapper.getInfoByNo(c.getPatientarchiveno());
                    // 档案号
                    map.put("patientarchiveno", record.getPatientarchiveno());
                }
                if (ObjectUtils.isNotEmpty(c.getPatientcodeThis())) {
                    // 本届体检号
                    map.put("patientcodeThis", c.getPatientcodeThis());
                }
                if (ObjectUtils.isNotEmpty(c.getRegisterThis())) {
                    // 本届登记时间
                    map.put("registerThis", formatter.format(c.getRegisterThis()));
                }
                if (ObjectUtils.isNotEmpty(c.getPatientcodeBefore())) {
                    // 上届体检号
                    map.put("patientcodeBefore", c.getPatientcodeBefore());
                }
                if (ObjectUtils.isNotEmpty(c.getRegisterBefore())) {
                    // 上届登记时间
                    map.put("registerBefore", formatter.format(c.getRegisterBefore()));
                }
                if (ObjectUtils.isNotEmpty(c.getPatientcodeSecond())) {
                    // 上二届体检号
                    map.put("patientcodeSecond", c.getPatientcodeSecond());
                }
                if (ObjectUtils.isNotEmpty(c.getRegisterSecond())) {
                    // 上二届登记时间
                    map.put("registerSecond", formatter.format(c.getRegisterSecond()));
                }
                if (ObjectUtils.isNotEmpty(c.getCreatedate())) {
                    map.put("createdate", timeformatter.format(c.getCreatedate()));
                }
                if (ObjectUtils.isNotEmpty(c.getPathWord())) {
                    map.put("pathWord", c.getPathWord());
                }
                if (ObjectUtils.isNotEmpty(c.getPathPdf())) {
                    map.put("pathPdf", c.getPathPdf());
                }
                list.add(map);
            }
            return list;
        }
        throw new ServiceException("error@无对比报告信息");
    }


    /**
     * 导出对比报告
     *
     * @param param
     * @return
     */
    @Override
    public List<RecordManageVo> getExportData(RecordManageParam param) {
        List<RecordManageVo> list = recordManageMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            RecordManageVo vo = list.get(i);
            //序号
            vo.setRownum(i + 1);
            //会员类型
            Patienttype patienType = patienttypeMapper.getInfoById(vo.getMemberlevel());
            vo.setMemberName(ObjectUtils.isEmpty(patienType) ? null : patienType.getPatientName());
            //民族
            Nation nation = nationMapper.getInfoById(vo.getIdNation());
            vo.setNation(ObjectUtils.isEmpty(nation) ? null : nation.getName());
            //文化程度
            String[] str = {"小学", "初中", "技校", "职高", "高中", "中专", "大专", "大学", "研究生以上"};
            vo.setCulturalName(ObjectUtils.isEmpty(vo.getCultural()) ? "" : str[Integer.valueOf(vo.getCultural().toString())]);
        }
        return list;
    }


    /**
     * 对比报告预览三个体检号
     *
     * @param patientcode
     * @param secondPatientcode
     * @param firstPatientcode
     * @param patientarchiveno
     * @return
     */
    @Override
    public Boolean createThree(String patientcode, String secondPatientcode, String firstPatientcode, String patientarchiveno) throws IOException {

        String templateRealPath = System.getProperty("user.dir") + "/temp/";
        String dir = File.separator+"file"+File.separator+"wordexp"
                +File.separator+"compare_report"+File.separator
                +new SimpleDateFormat("yyyyMMdd").format(new Date())
                +File.separator + patientarchiveno;
        String abPdfPath = dir
                +File.separator+ new SimpleDateFormat("HH_mm_ss_SSS").format(new Date());
        File folder = new File(templateRealPath + dir);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        //渲染数据
        //首页
        Map<String, Object> datas = new HashMap<String, Object>();
        Peispatientarchive archive = peispatientarchiveMapper.getInfoByNo(patientarchiveno);
        String archiveno = archive.getPatientarchiveno() == null ? "" : archive.getPatientarchiveno();
        Peispatient pei = peispatientMapper.getByPatientCode(patientcode);
        //2022.4.6 新需求 不要这次体检的【总检提示】 	//对比报告都是健康报告
        Peispatient secondPei = peispatientMapper.getByPatientCode(secondPatientcode);
        Peispatient firstPei = peispatientMapper.getByPatientCode(firstPatientcode);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy");
        datas.put("workunit", pei.getOrgName());// 体检单位
        datas.put("department", pei.getOrgDepart());// 部门
        datas.put("patientname", pei.getPatientname());// 姓名
        datas.put("sex", Render.getSex(pei.getIdSex()));// 性别
        datas.put("record", archiveno);// 档案号
        datas.put("marry", MarriageType.getName(pei.getIdMarriage()));
        datas.put("patientcode", patientcode);// 体检号
        String registeryear = pei.getDateregister() != null ? dateFormatter1.format(pei
                .getDateregister()) : "";// 登记年
        datas.put(
                "register",
                pei.getDateregister() != null ? dateFormatter.format(pei
                        .getDateregister()) : "");// 登记日期本届
        datas.put(
                "registeryear", registeryear);// 登记年
        datas.put("combo", pei.getExamsuiteName());// 本届套餐
        datas.put("age", pei.getAge() == null ? "" : pei.getAge().intValue());
        //上届
        datas.put("patientcode1", secondPei.getPatientcode());// 上一届体检号
        datas.put(
                "register1",
                secondPei.getDateregister() != null ? dateFormatter.format(secondPei
                        .getDateregister()) : "");// 上一届登记日期
        String registeryear1 = secondPei.getDateregister() != null ? dateFormatter1.format(secondPei
                .getDateregister()) : "";// 上一届登记年
        datas.put(
                "registeryear1", registeryear1);// 上一届登记年
        datas.put("combo1", secondPei.getExamsuiteName());// 上一届套餐
        datas.put("age1", secondPei.getAge() != null ? secondPei.getAge().intValue() : "");// 上一届年龄
        //上上届 firstPei
        datas.put("abc", firstPei.getPatientcode());// 上上届体检号
        datas.put(
                "register2",
                firstPei.getDateregister() != null ? dateFormatter.format(firstPei
                        .getDateregister()) : "");// 上上届登记日期
        String registeryear2 = firstPei.getDateregister() != null ? dateFormatter1.format(firstPei
                .getDateregister()) : "";// 上上届登记年
        datas.put(
                "registeryear2", registeryear2);// 上上届登记年
        datas.put("combo2", firstPei.getExamsuiteName());// 上上届套餐
        datas.put("age2", firstPei.getAge() != null ? firstPei.getAge().intValue() : "");// 上上届年龄

        SectionTotal st = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>().eq("patientcode", patientcode).eq("disease_health", 0));//对比报告都是健康报告
        datas.put("totaloffer", st == null ? "" : st.getOffer());// 本次体检建议
        GetDoctorDto doctor = getDoctor(patientcode);
        if (ObjectUtils.isNotEmpty(doctor)) {
            if (StringUtils.isNotEmpty(doctor.getUsername())) {
                datas.put("totaldoctor", doctor.getUsername());// 本次总检医生
            }
            if (!ObjectUtils.isEmpty(doctor.getTotalTime())) {
                datas.put("reportdate", doctor.getTotalTime());// 总检报告审核日期
            }
        }
        //默认分中心
        SysBranch b = sysBranchMapper.getDefaultBranch();
        if (b != null) {
            datas.put("hotline", b.getTel());//咨询电话
        }
        //各科室小结
        datas.put("conclusion_grid", getDepResult2(patientcode, secondPatientcode, firstPatientcode));

        //检验科结果
        List<Map<String, Object>> inspectDatas = getInspectionData2(patientcode, secondPatientcode, firstPatientcode, registeryear, registeryear1, registeryear2);
        datas.put("inspection_doc"
                , new DocxRenderData(new File(templateRealPath+TEMPLATE_INSPECTION_THREE)
                        , inspectDatas));
        //导出word
        Configure config = Configure.newBuilder()
                .customPolicy("conclusion_grid"
                        , new CompareReportThreeConclusionsTablePolicy())
                .customPolicy("inspect_grid"
                        , new CompareReportThreeInspectionTablePolicy())
                .build();
        XWPFTemplate template = XWPFTemplate.compile(templateRealPath+TEMPLATE_THREE
                , config).render(datas);
        template.writeToFile(templateRealPath+abPdfPath + ".docx");
        //转PDF
        WordConvertPDF conver = new WordConvertPDF();
        conver.wordToPDF(templateRealPath + abPdfPath + ".docx", templateRealPath+abPdfPath + ".pdf");

        String docxPath = templateRealPath + abPdfPath + ".docx";
        String pdfPath = templateRealPath + abPdfPath + ".pdf";
        //保存
        saveOrUpdateCom(patientarchiveno, patientcode, secondPatientcode,docxPath,pdfPath);

        //生成的报告,插入到报告内容表中
//        String jsonString = JSON.toJSONString(datas);
//        reportContentService.createReportContent(jsonString, 6, null, null, null, compareReportId, null, null);
        return Boolean.TRUE;
    }

    /**
     * 三次对比报告的，上次做了也不显示
     * @param patientcode
     * @param secondPatientcode
     * @param firstPatientcode
     * @return
     */
    private List<RowRenderData> getDepResult2(String patientcode, String secondPatientcode, String firstPatientcode) {
        List<DepResult2Dto> list = recordManageMapper.getDepResult2(patientcode, secondPatientcode, firstPatientcode);
        List<RowRenderData> data=new ArrayList<RowRenderData>();
        for (int i = 0; i < list.size(); i++) {
            DepResult2Dto obj = list.get(i);
            data.add(RowRenderData.build(
                    Render.getString(obj.getDeptName()),
                    Render.getString(obj.getPeiC()),
                    Render.getString(obj.getPei1C()),
                    Render.getString(obj.getPei2C())
            ));
        }
        return data;
    }


    /**
     * 检验科结果
     *
     * @param patientcode_p
     * @param patientcode_s
     * @param patientcode_f
     * @param registeryear
     * @param registeryear1
     * @param registeryear2
     * @return
     */
    private List<Map<String, Object>> getInspectionData2(String patientcode_p, String patientcode_s, String patientcode_f, String registeryear, String registeryear1, String registeryear2) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        List<InspectionData2ByCodeDto> itemIds = recordManageMapper.getInspectionData2ByCode(patientcode_p);
        //通过体检号获取检查项目
        for (InspectionData2ByCodeDto os : itemIds) {
            Map<String, Object> m = new HashMap<String, Object>();
            String itemId = os.getIdExamfeeitem();
            String itemNamePrn = os.getExamfeeitemNameprn();
            m.put("charges", itemNamePrn);
            List<InspectionData2ByThreeDto> l = recordManageMapper.getInspectionData2ByThreeCode(patientcode_s, patientcode_f, patientcode_p, itemId);
            List<RowRenderData> rows=new ArrayList<RowRenderData>();
            m.put("registeryear",registeryear);
            m.put("registeryear1",registeryear1);
            m.put("registeryear2",registeryear2);
            m.put("inspect_grid", rows);
            for(InspectionData2ByThreeDto arr:l) {
                rows.add(
                        RowRenderData.build(
                                Render.getString(arr.getExamitemNameR()==null?"无":arr.getExamitemNameR()),
                                Render.getString(arr.getExamitemvaluesreportP()==null?"无":arr.getExamitemvaluesreportP()),
                                Render.getString(arr.getStatusP()==null?"无":arr.getStatusP()),
                                Render.getString(arr.getExamitemvaluesreportS()==null?"无":arr.getExamitemvaluesreportS()),
                                Render.getString(arr.getStatusS()==null?"无":arr.getStatusS()),
                                Render.getString(arr.getExamitemvaluesreportF()==null?"无":arr.getExamitemvaluesreportF()),
                                Render.getString(arr.getStatusF()==null?"无":arr.getStatusF())
                        )
                );
            }
            data.add(m);
        }
        return data;
    }

    /**
     * 上传word
     * @param param
     * @return
     */
    @Override
    public Boolean uploadWord(UploadWordParam param) throws IOException {
        CompareReport compareReport = compareReportService.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(compareReport)){
            throw new ServiceException("该对比报告不存在或已被删除！");
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
        String wordUploadUrl = uploadReport(wordUrl,compareReport,sysBranch);
        compareReport.setPathWord(wordUploadUrl);
        String pdfUploadUrl = uploadReport(pdfUrl,compareReport,sysBranch);
        compareReport.setPathPdf(pdfUploadUrl);
        compareReportService.updateById(compareReport);
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

