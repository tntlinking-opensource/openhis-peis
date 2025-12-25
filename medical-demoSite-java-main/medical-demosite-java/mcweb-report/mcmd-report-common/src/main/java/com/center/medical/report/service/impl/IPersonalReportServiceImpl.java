package com.center.medical.report.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.NursingRegistrationDto;
import com.center.medical.bean.dto.ReportSignatureConfigDto;
import com.center.medical.bean.dto.SummaryAndPicturesDto;
import com.center.medical.bean.dto.SyncFileCheckDto;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.*;
import com.center.medical.common.utils.*;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.ElectroAudiometerMapper;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.core.domain.entity.SysCenDep;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.EmphasisAskSymptom;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.EmphasisAskSymptomMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.dao.StencilMaintainMapper;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.model.TotalDoctor;
import com.center.medical.report.bean.param.IReportParam;
import com.center.medical.report.bean.param.ReviewNoticeListParam;
import com.center.medical.report.bean.param.inspectReportsParam;
import com.center.medical.report.bean.utils.GroupReviewTablePolicy;
import com.center.medical.report.bean.vo.CreateReportVo;
import com.center.medical.report.bean.vo.IReportVo;
import com.center.medical.report.bean.vo.PrivateReportVo;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.dao.*;
import com.center.medical.report.service.*;
import com.center.medical.report.utils.CreateBarcodeDefault;
import com.center.medical.report.utils.ReportUtil;
import com.center.medical.report.utils.WordConvertPDF;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.service.*;
import com.center.medical.statistics.dao.GroupReviewNoticeMapper;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysCenDepMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
@Slf4j
@Service("iPersonalReportService")
@RequiredArgsConstructor
public class IPersonalReportServiceImpl extends ServiceImpl<IPersonalReportMapper, Report> implements IPersonalReportService {

    private final IPersonalReportMapper iPersonalReportMapper;
    private final ReportMapper reportMapper;
    private final OtherReportMapper otherReportMapper;
    private final PeispatientMapper peispatientMapper;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final ItemsMapper itemsMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final StencilMaintainMapper stencilMaintainMapper;

    private final SystemConfig systemConfig;
    private final HarmMapper harmMapper;
    private final PeispatientConsultationMapper peispatientConsultationMapper;
    private final WzJwbMapper wzJwbMapper;
    private final SysBranchMapper sysBranchMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysCenDepMapper sysCenDepMapper;
    private final DivisionService divisionService;
    private final ISysUserService sysUserService;
    private final SectionTotalMapper sectionTotalMapper;
    private final TotalDoctorMapper totalDoctorMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final PacsResultMapper pacsResultMapper;
    private final SysUserMapper sysUserMapper;
    private final PeispatientConsultationPicMapper peispatientConsultationPicMapper;
    private final EmphasisAskSymptomMapper emphasisAskSymptomMapper;
    private final TjregMapper tjregMapper;
    private final DescribeMapper describeMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentConfigService attachmentConfigService;
    private final LungMapper lungMapper;
    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final OutsidePictrueMapper outsidePictrueMapper;
    private final PrivateReportService privateReportService;
    private final ReportContentService reportContentService;
    private final ISysConfigService iSysConfigService;
    private final InspectReportsService inspectReportsService;
    private final ReportPrintingMapper reportPrintingMapper;

    private final ReportConfigService reportConfigService;

    private final AttachmentService attachmentService;
    private final ReportDefaultDoctorService reportDefaultDoctorService;
    private final GroupReviewNoticeMapper groupReviewNoticeMapper;
    private final GroupReviewNoticePatientService groupReviewNoticePatientService;

    private final PacsResultThirdService pacsResultThirdService;

    @Autowired
    private LoadProperties loadProperties;


    public final static String[] YGEIDS = new String[]{"186", "187", "188", "190", "189"};//乙肝五项检查项目id
    public final static String YGID = "669";//乙肝五项收费项目id
    public final static String YGLDBID = "505";//乙肝两对半id(所有体征词都属于乙肝两对半检查项目)
    private final static String GDYP = "402848e3614f8e980161c1817f5407f4";//肝胆胰脾彩超收费项目id
    private final static SimpleDateFormat RUMFORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat auditFormat = new SimpleDateFormat("yyyy-MM-dd");// 科室审核时间
    public static final String CCKS = "143";//彩超科室ID
    private static final String CT = "173";//CT科室 ID
    private static final SimpleDateFormat nameFormat = new SimpleDateFormat(
            "HH_mm_ss_SSS");// 报告文件名

    private ReportUtil ru = new ReportUtil();
    private static final String DISEASE_END_PATH_1 = "file/wordmodel/ks-i/diseaseEnd.jasper";
    private static final String DISEASE_END_PATH_2 = "file/wordmodel/ks-i/diseaseEnd3.jasper";
    private static final String HEALTH_END_PATH = "file/wordmodel/ks-i/healthEnd.jasper";

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS");// 临时文件文件名

    public static final String[] healthModelUrls = new String[]{
            "file/wordmodel/ks-i/healthone.jasper"// 0
            , "file/wordmodel/ks-i/healthtwo.jasper",
            "file/wordmodel/ks-i/healththree.jasper"};
    public static final String[] professionModelUrls = new String[]{
            "file/wordmodel/ks-i/profession-one.jasper"// 0
            , "file/wordmodel/ks-i/prfession-two.jasper",
            "file/wordmodel/ks-i/profession-tree.jasper",
            "file/wordmodel/ks-i/profession-four.jasper",
            "file/wordmodel/ks-i/profession-1.5.jasper"};

    /**
     * 速成或生成检验报告
     *
     * @param param
     * @return
     */
    @Override
    public Boolean create(IReportParam param) {
        //取出属性
        List<String> patientcode = param.getPatientcode();
        //当前登录用户名
        String username = param.getUsername();
        String isJy = param.getIsJy();
        Integer dh = param.getDh();
        String showAllImage = param.getShowAllImage();

        if (CollectionUtils.isNotEmpty(patientcode)) {
            for (int i = 0; i < patientcode.size(); i++) {
                String code = patientcode.get(i);
                Peispatient patient = peispatientMapper.getByPatientCode(code);
                //不是检验报告
                if (!"1".equals(isJy)) {
                    //报告主表
                    Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                            .eq("disease_health", dh).eq("patientcode", code));
                    if (report == null) {
                        throw new ServiceException("无总检报告内容:" + code);
                    } else if (report.getStatus() != null
                            && report.getStatus() == 11
                            && !param.getSkipVerification()
                    ) {
                        throw new ServiceException("报告已领取，请反领取后再生成:" + code);
                    }

                    //删除原始文件地址
                    if (StringUtils.isNotEmpty(report.getUrlPdf())) {
                        attachmentService.deleteFile(report.getUrlPdf());
                    }

                    //报告数据
                    param.setLrctType(0);
                    param.setType(0);
                    param.setCode(code);
                    if ("3".equals(patient.getIdExamtype())) {
                        param.setDh(3);
                    }
                    CreateReportVo vo = null;
                    try {
                        vo = createReport(param);
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }

                    //更新报告内容
                    //生成的报告,插入到报告内容表中
                    String jsonString = JSON.toJSONString(vo.getIReportVo());
                    //把前缀都替换掉
                    jsonString = jsonString.replace(vo.getPrefix(), "");
                    List<String> reportPicList = vo.getReportPicList();
                    reportContentService.createReportContent(jsonString, 5, code, String.valueOf(param.getDh()), null, null, null, reportPicList);


                    //更新报告表
                    Integer a = report.getCreateNum();
                    if (a != null) {
                        report.setCreateNum(report.getCreateNum() + 1);
                    } else {
                        report.setCreateNum(1);
                    }
                    //设置报告地址
                    if (StringUtils.isNotBlank(vo.getReportPath())) {
                        report.setUrlPdf(vo.getReportPath());
                    }
                    report.setGenerateName(username);
                    report.setGenerateDate(new Date());
                    reportMapper.updateById(report);

                    // 插入redis同步报告
                    SyncFileCheckDto dto = new SyncFileCheckDto(report.getId(), report.getPatientcode(), report.getUrlPdf(), new Date(), 1, 0);
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_CHECK, dto, DateUtil.currentSeconds());
                } else {
                    //报告数据
                    param.setLrctType(0);
                    param.setType(0);
                    param.setCode(code);
                    param.setDh("0".equals(patient.getIdExamtype())||"2".equals(patient.getIdExamtype()) ? 0 : 1);
                    CreateReportVo vo = null;
                    try {
                        vo = createReport(param);
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }
                    //其他报告
                    OtherReport ot = otherReportMapper.selectOne(new QueryWrapper<OtherReport>()
                            .eq("patientcode", code).eq("report_type", 0));
                    if (ot == null) {
                        ot = new OtherReport(code
                                , vo.getReportPath()
                                , null
                                , 1
                                , null
                                , new Date()
                                , 0
                                , SecurityUtils.getUserNo());
                        otherReportMapper.insert(ot);
                    } else {
                        //已生成
                        ot.setCreateStatus(1);
                        ot.setCreateTime(new Date());
                        ot.setPdfUrl(vo.getReportPath());
                        ot.setCreater(SecurityUtils.getUserNo());
                        otherReportMapper.updateById(ot);
                    }
                }


            }
        } else {
            throw new ServiceException("请选择体检者:");
        }
        return Boolean.TRUE;
    }

    /**
     * 生成检验报告
     *
     * @param patientcode
     * @param dh
     * @param isJy
     * @param showAllImage
     * @param username
     * @return
     */
    private IReportVo run(String patientcode, Integer dh, String isJy, String showAllImage, String username) {
//        List<String> reportPic = new ArrayList<>();
//        IReportVo vo = new IReportVo();
//        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
//        /** 头模板 */
//        Map<String, Object> partHead = new HashMap<String, Object>();
//        partHead.put("part", -1);
//        partHead.put("title", "1".equals(isJy) ? "检验" : "职业复查");
//        partHead.put("patientcode", patientcode);
//        partHead.put("patientname", patient.getPatientname());
//        partHead.put("patientorg", patient.getOrgName());
//        partHead.put("patientphone", patient.getPhone());
//        partHead.put("patientage", patient.getAge() == null ? "" : patient
//                .getAge().intValue());
//        partHead.put("patientsex", patient.getIdSex() == 0 ? "男"
//                : "女");
//        String medicaltype = patient.getMedicaltype();
//        if ("0".equals(medicaltype)) {
//            partHead.put("patientmedicaltype", "上岗前");
//        } else if ("1".equals(medicaltype)) {
//            partHead.put("patientmedicaltype", "在岗期间");
//        } else if ("2".equals(medicaltype)) {
//            partHead.put("patientmedicaltype", "离岗时");
//        } else if ("3".equals(medicaltype)) {
//            partHead.put("patientmedicaltype", "离岗后");
//        } else if ("4".equals(medicaltype)) {
//            partHead.put("patientmedicaltype", "应急");
//        }
//        String jhyss = patient.getJhys();
//        if (jhyss != null) {
//            List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhyss.split(",")));
//            StringBuilder jhys = new StringBuilder();
//            for (int i = 0; i < harms.size(); i++) {
//                jhys.append(harms.get(i).getHarmName());
//                if (i != harms.size() - 1) {
//                    jhys.append("、");
//                }
//            }
//            partHead.put("patientharm", jhys.toString());
//        }
//        vo.setHead(partHead);
//
//
//        //检验科
//        int isContainPacs = "1".equals(Constants.PUT_PACS_LAST) ? 0 : 2;
//        List<SysDept> deps = dh == 1 ? findAllDepD(patientcode, isContainPacs) : findAllDep(patientcode, isContainPacs);
//        String lrctJyAuditName = Constants.JYSIGN;
//        //没有检验科直接提示
//        if (CollectionUtils.isEmpty(deps)) {
//            throw new ServiceException("该体检者没有检验科检查项目!");
//        }
//        Boolean b = true;
//        for (SysDept dep : deps) {
//            if ("19".equals(dep.getDeptNo())) {
//                b = false;
//            }
//        }
//        if (b) {
//            throw new ServiceException("该体检者没有检验科检查项目!");
//        }
//
//
//        List<IReportDto> iReportDtoList = new ArrayList<>();
//        for (SysDept dept : deps) {
//            List<Map> itemMapList = new ArrayList<>();
//            IReportDto dto = new IReportDto();
//            if ("1".equals(isJy) && !"19".equals(dept.getDeptNo())) {
//                continue;
//            }
//            String depName = dept.getDeptName();
//            boolean isHealth = "1".equals(Constants.PHYSICAL_EXAMINATION_FORM);
//            int sjbggs = dept.getSjbggs();
//            String doctorType = Constants.DOCTOR_TYPE;
//            if (sjbggs == 1) {
//                // 检验科
//                //如果只有隐私项目，就不显示检验科
//                List<AllInspectDto> list = iPersonalReportMapper.findAllInspect(patientcode, dh);
//                if (list.size() == 0) {
//                    continue;
//                }
//                String inspectionFooter = Constants.INSPECTION_FOOTER;
//                if (StringUtils.isEmpty(inspectionFooter)) {
//                    inspectionFooter = "本次检验结果获山东省通用认证(“一单通”)。";
//                } else if ("-1".equals(inspectionFooter)) {
//                    inspectionFooter = null;
//                }
//                /**头部 */
//                Map<String, Object> part0 = new HashMap<String, Object>();
//                part0.put("part", 0);
//                part0.put("yblx", depName);
//                part0.put("sjbggs", sjbggs);
//                part0.put("inspectionFooter", inspectionFooter);
//                dto.setHead(part0);
//
//                /**收费项目*/
//                String special_inspection_exam_items = Constants.SPECIAL_INSPECTION_EXAM_ITEMS;
//                String[] sepcialArr = special_inspection_exam_items == null ? new String[0] : special_inspection_exam_items.trim().split(",");
//                List<String> speciallList = Arrays.asList(sepcialArr);
//                Map<String, Object> itemMap = null;
//                String fItemName = null;
//                Collection<Map<String, String>> examCol = null;
//                String p_name = "";
//                String p_name_lab = "";
//
//                //肝功、肾功、血糖、血脂这四项的标本采集时间应该是一样的，四项的报告时间是一样的(按标本类型分类)
//                Map<String, String> idLabtypeMap = new HashMap<String, String>();
//                for (int i = 0; i < list.size(); i++) {
//                    AllInspectDto os = list.get(i);
//                    String itemName = os.getExamfeeitemNameprn() != null ? String.valueOf(os.getExamfeeitemNameprn()) : "";
//                    String txt = os.getExamitemvaluestext() == null ? "" : os.getExamitemvaluestext().toString();
//                    //item_name包含后面空格，最多34个字符，超过34个字符医生名字为xxx/xxx时最后一个字显示不出来
//                    //打印名称最长为34
//                    //当打印名称小于等于10，留出24个空格，大于10，留出34-length个空格
//                    //如果日期和医生名字超出一般长度，修改模板，删掉几个固定空格，现已预留出三个空格
//                    //实际中文长度比其他字符更长（awt中为13:7），按2:1处理
//                    //在GBK和GB2312编码下一个汉字(包括中文形式下的符号)是2个字节，一个英文(包括英文下的符号)1个字节，一个数字1个字节；
//                    //UTF-8编码下一个汉字(包括中文形式下的符号)一般是3个字节，一个英文(包括英文下的符号)1个字节，一个数字1个字节；
//                    //gb2312下直接用字节数判断长度
//                    int bl = 0;
//                    try {
//                        bl = itemName.getBytes("GB2312").length;
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    int l = bl <= 10 ? 24 : 34 - bl;
//                    for (int ii = 0; ii < l; ii++) {
//                        itemName = itemName + " ";
//                    }
//                    if (!itemName.equals(fItemName)) {
//                        itemMap = new HashMap<String, Object>();
//                        itemMap.put("TXT_VALUES", txt);
//                        itemMap.put("ITEM_NAME", itemName);
//                        itemMap.put("DATE", ObjectUtils.isNotEmpty(os.getAuditDate()) ? os.getAuditDate() : "");
//                        //老年人查体 自动生成签收时间和采集时间，
//                        if (isHealth) {
//                            String idLabtype = os.getIdLabtype() == null ? "null" : os.getIdLabtype().toString();
//                            String generated = idLabtypeMap.get(idLabtype);
//                            if (StringUtils.isEmpty(generated)) {
//                                Date dateregister = patient.getDateregister();
//                                Calendar c = Calendar.getInstance();
//                                c.setTime(dateregister);
//                                //标本采集时间根据登记时间，与登记时间间隔10-20分钟,自动生成
//                                c.add(Calendar.MINUTE, getRandom(10, 10));
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                                itemMap.put("specimenCollectionTime", sdf.format(c.getTime()));
//                                //报告时间与标本采集时间间隔90-110分钟
//                                c.add(Calendar.MINUTE, getRandom(90, 20));
//                                itemMap.put("reportTime", sdf.format(c.getTime()));
//
//                                idLabtypeMap.put(idLabtype, itemMap.get("specimenCollectionTime")
//                                        + "," + itemMap.get("reportTime"));
//                            } else {
//                                String[] generatedArr = generated.split(",");
//                                itemMap.put("specimenCollectionTime", generatedArr[0]);
//                                itemMap.put("reportTime", generatedArr[1]);
//                            }
//                        }
//                        itemMap.put("RECEIVE_DATE", os.getReceiveDate() != null ? os.getReceiveDate() : "");
//                        if ("1".equals(doctorType)) {
//                            if (isHealth && StringUtils.isNotEmpty(lrctJyAuditName)) {
//                                setDoctor(itemMap, lrctJyAuditName, "jy_audit_path", "P_NAME",prefix);
//                            } else if (StringUtils.isEmpty(p_name)) {
//                                setDoctor(itemMap, os.getAuditName() == null ? null : os.getAuditName().toString(), "jy_audit_path", "P_NAME");
//                            } else {
//                                setDoctor(itemMap, p_name, "jy_audit_path", "P_NAME");
//                            }
//
//                            if (StringUtils.isEmpty(p_name_lab)) {
//                                setDoctor(itemMap, os.getInspectName() == null ? null : os.getInspectName().toString(), "jy_rummager_path", "P_NAME_LAB");
//                            } else {
//                                setDoctor(itemMap, p_name_lab, "jy_rummager_path", "P_NAME_LAB");
//                            }
//                        } else {
//                            itemMap.put(
//                                    "P_NAME",
//                                    os.getInspectName() != null ? String
//                                            .valueOf(os.getInspectName()) : "");
//                            itemMap.put(
//                                    "P_NAME_LAB",
//                                    os.getAuditName() != null ? String
//                                            .valueOf(os.getAuditName()) : "");
//                        }
//                        itemMap.put("part", 1);
//                        itemMap.put("sjbggs", sjbggs);
//                        examCol = new Vector<Map<String, String>>();
//                        itemMap.put("EXAMLIST", examCol);
//                        itemMap.put("inspectionFooter", inspectionFooter);
//                        itemMapList.add(itemMap);
//                        fItemName = itemName;
//                    } else {
//                        itemMap.put("TXT_VALUES", itemMap.get("TXT_VALUES") + "\n" + txt);
//                    }
//
//                    Map<String, String> examMap = new HashMap<String, String>();
//                    examMap.put("ITEM",
//                            os.getExamitemNameprn() == null ? "" : os.getExamitemNameprn().toString());
//                    examMap.put("RESULT",
//                            os.getExamitemvaluesreport() == null ? "" : os.getExamitemvaluesreport().toString());
//                    examMap.put("SIGN",
//                            os.getUnits() == null ? "" : os.getUnits().toString());
//                    examMap.put("CONSULT",
//                            os.getStatus() == null ? "" : os.getStatus().toString());
//                    examMap.put("UNIT",
//                            os.getRefrange() == null ? "" : os.getRefrange().toString());
//                    String examId = os.getIdExamitem() == null ? "" : os.getIdExamitem().toString();
//                    examMap.put("red",
//                            (speciallList.contains(examId)
//                                    && os.getExamitemvaluesreport() != null
//                                    && !"阴性".equals(os.getExamitemvaluesreport().toString().trim())
//                                    && !"未见".equals(os.getExamitemvaluesreport().toString().trim())
//                            )
//                                    ? "1" : "0");
//                    examCol.add(examMap);
//                }
//                dto.setItem(itemMapList);
//
//                /** 小结 */
//                Map<String, Object> part2 = new HashMap<String, Object>();
//                part2.put("part", 2);
//                part2.put("sjbggs", sjbggs);
//                part2.put("inspectionFooter", inspectionFooter);
//                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
//                        .eq("patientcode", patientcode)
//                        .eq("dep_id", dept.getDeptNo()));
//                if (section != null) {
//                    if (dh == 1) {
//                        part2.put("P_CONCLUSIONS",
//                                section.getZyConclusions() == null ? ""
//                                        : section.getZyConclusions());
//                    } else {
//                        part2.put("P_CONCLUSIONS",
//                                section.getConclusions() == null ? ""
//                                        : section.getConclusions());
//                    }
//                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
//                }
//                dto.setSummary(part2);
//                iReportDtoList.add(dto);
//            }
//
//        }
//
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        //复查报告尾页
//        if (dh == 3) {
//            parameters = getDiseaseEndData(patientcode);
//            parameters.put("patientcode", patientcode);
//            parameters.put("patientname", patient.getPatientname());
//            String end_title = ReportConstants.END_TITLE;
//            end_title = StringUtils.isNotEmpty(end_title) ? end_title : "青岛沃德国际体检中心职业体检总结报告";
//            parameters.put("end_title", end_title);
//            String end_org = ReportConstants.END_ORG;
//            end_org = StringUtils.isNotEmpty(end_org) ? end_org : "沃德国际体检中心";
//            parameters.put("end_org", end_org);
//            if (!"3".equals(patient.getIdExamtype())) {
//                parameters.put("logo", ReportConstants.diseaseEndLogo);
//                parameters.put("qrcode", ReportConstants.diseaseEndQrcode);
//                if ("1".equals(ReportConstants.is_open)) {
//                    parameters.put("org_address", ReportConstants.org_address);
//                    parameters.put("org_phone", ReportConstants.org_phone);
//                    String zipCode = ReportConstants.org_zipcode;
//                    parameters.put("org_zipcode", zipCode == null ? "" : zipCode);
//                    String zzzs = ReportConstants.org_zzzs;
//                    parameters.put("org_zzzs", zzzs == null ? "" : zzzs);
//                    String orgName = ReportConstants.org_orgName;
//                    parameters.put("org_orgName", orgName == null ? "" : orgName);
//                    String fax = ReportConstants.org_fax;
//                    parameters.put("org_fax", fax == null ? "" : fax);
//                    String email = ReportConstants.org_email;
//                    parameters.put("org_email", email == null ? "" : email);
//                } else {
//                    SysBranch branch = sysBranchMapper.getDefaultBranch();
//                    if (branch != null) {
//                        parameters.put("org_address", branch.getAddress());
//                        parameters.put("org_phone", branch.getTel());
//                    }
//                    String zipCode = Constants.ZIPCODE;
//                    parameters.put("org_zipcode", zipCode);
//                    String zzzs = Constants.ZZZS;
//                    parameters.put("org_zzzs", zzzs);
//                    String orgName = Constants.orgName;
//                    parameters.put("org_orgName", orgName);
//                    String fax = Constants.fax;
//                    parameters.put("org_fax", fax);
//                    String email = Constants.email;
//                    parameters.put("org_email", email);
//                }
//                String end_logo = ReportConstants.end_logo;
//                parameters.put("end_logo", end_logo);
//                String end_qrcode = ReportConstants.end_qrcode;
//                parameters.put("end_qrcode", end_qrcode);
//
//            }
//        }
//        vo.setEnd(parameters);
//
//
//        //检验科数据
//        vo.setKsList(iReportDtoList);
//        //有隐私报告就把隐私报告放进去
//        if (dh == 0 && iPersonalReportMapper.hasPrivateItem(patientcode).size() > 0) {
//            //查询隐私报告数据
//            PrivateReportVo ysbg = privateReportService.create(patientcode);
//            vo.setYsbg(ysbg);
//        }
//        return vo;
        return null;
    }

    /**
     * 创建个检健康或职业报告
     *
     * @param param
     * @return
     */
    public CreateReportVo createReport(IReportParam param) throws JRException {
        String local = System.getProperty("user.dir") + "/temp/";

        //线上线下地址
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        //生成pdf存储数据
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        CreateReportVo createReportVo = new CreateReportVo();
        createReportVo.setPrefix(prefix);
        List<String> reportPicList = new ArrayList<>();
        IReportVo vo = new IReportVo();
        Integer dh = param.getDh();
        String isJy = param.getIsJy();
        String showAllImage = param.getShowAllImage();
        Integer lrctType = param.getLrctType();
        String patientcode = param.getCode();
        Integer type = param.getType();
        int pageNum = 0;

        /** 头模板 */
        boolean isfc = false;// 是否复查
        boolean pacs = false;//是否综合  oldpacs也可以确定是哪个收费项目，与是否开启PACS无关
        String jhysStr = null;
        String medicalType = null;
        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        String patientname = patient.getPatientname();
        HashMap<String, Object> head = new HashMap<String, Object>();
        if (dh == 0 && !"1".equals(isJy) && lrctType != 1) {
            // 生成健康个检头部文件
            head = healthHeadPage(patientcode, type, lrctType);
            pageNum = (Integer) head.get("pageNum");
//        } else if (lrctType == 1) {//老人查体档案不生成第一部分
//            head = healthHeadPage(patientcode, type, lrctType);
        } else if (dh == 1 && !"1".equals(isJy)) {
            // 生成职业个检头部文件
            head = professionHeadPage(patientcode);
            pageNum = (Integer) head.get("pageNum");
            pacs = "2".equals(patient.getIdExamtype());
            jhysStr = patient.getJhys();
            medicalType = patient.getMedicaltype();

        } else if (dh == 3) {
            //用的模板头在ks_main6里面
            dh = 1;
            isfc = true;
            patientname = patient.getPatientname();
        } else if ("1".equals(isJy)) {
            //检验报告生成，个人信息再简化一下，简化成和复查报告单那样的
            isfc = true;
            patientname = patient.getPatientname();
        }
        vo.setHead(head);
        if (!isfc) {
            jasperPrintList.addAll((Collection<? extends JasperPrint>) head
                    .get("jasperPrintList"));
            //添加完需要删掉，不然的话转json会报错
            head.remove("jasperPrintList");
        }


        /** 各科室 */
        String doctorType = Constants.DOCTOR_TYPE;
        int isContainPacs = "1".equals(Constants.PUT_PACS_LAST) ? 0 : 2;
        List<SysDept> deps = dh.intValue() == 1 ? findAllDepD(patientcode, isContainPacs) : findAllDep(patientcode, isContainPacs);
        Collection<Map<String, ?>> ksList = new Vector<Map<String, ?>>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("P_KSLIST", ksList);
        parameters.put("patientcode", patientcode);
        parameters.put("patientname", patientname);
        parameters.put("dh", dh);
        parameters.put("doctorType", doctorType);
        parameters.put("pageNum", pageNum);
        //公卫查体隐藏页眉左侧
        if ("1".equals(isJy)) {
            parameters.put("hideTop", "1");
        }
        if (0 == dh) {
            // TODO: 2024/7/10 锦都去除沃德logo
            if (!StringUtils.equals(loadProperties.name, "jindu")) {
                parameters.put("header", local + Constants.HEADER);
            }
        }

        /*健康体检表*/
        boolean isHealth = "1".equals(Constants.PHYSICAL_EXAMINATION_FORM);
        if (isHealth) {
            Map<String, Object> wz = new HashMap<String, Object>();
            ksList.add(wz);
            wz.put("part", -2);
        }

        boolean use_pic_sign = "1".equals(Constants.USE_PIC_SIGN);


        boolean configAuditor = false;
        SysUser auditor = null;
        SysUser auditor2 = null;
        if (dh.intValue() == 1) {
            String auditorUsername = Constants.AUDITOR;
            String auditor2Username = Constants.AUDITOR2;
            if (StringUtils.isNotEmpty(auditorUsername) && StringUtils.isNotEmpty(auditor2Username)) {
                configAuditor = true;
                auditor = sysUserMapper.selectUserByUserName(auditorUsername);
                if (auditor == null) {
                    throw new ServiceException("账号名auditor不存在：" + auditorUsername);
                }
                auditor2 = sysUserMapper.selectUserByUserName(auditor2Username);
                if (auditor2 == null) {
                    throw new ServiceException("账号名auditor2不存在：" + auditor2Username);
                }
            }
        }


        /*职业性问诊*/
        PeispatientConsultation pc = null;
        boolean hasWz = false;
        Object[] pos = null;
        if (!isHealth) {
            pc = peispatientConsultationMapper.selectOne(new QueryWrapper<PeispatientConsultation>()
                    .eq("patientcode", patient.getPatientcode()));
            hasWz = dh == 1 && !"1".equals(isJy) && pc != null && pc.getIsAudit() != null
                    && pc.getIsAudit() == 1;
        } else {
            SectionResultMain sectionResultMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                    .eq("patientcode", patient.getPatientcode()).eq("dep_id", "124"));
            hasWz = dh == 1 && !"1".equals(isJy) && "1".equals(sectionResultMain.getIsAudit());
        }
        Map<String, Object> four = null;
        if (hasWz) {
            Map<String, Object> wz = new HashMap<String, Object>();
            ksList.add(wz);
            wz.put("sjbggs", 8);
            wz.put("duty", patient.getDateregister() == null ? ""
                    : new SimpleDateFormat("yyyy-MM-dd")
                    .format(patient.getDateregister()));// 检查日期
            SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                    .eq("patientcode", patient.getPatientcode()).eq("dep_id", "124"));
            //检查医生签名
            if (main != null) {
                String rummId = reportDefaultDoctorService.getinfoByKsIdAndType("124",dh,2);
                if (StringUtils.isEmpty(rummId)){
                    rummId = main.getRummagerId();
                }
                if (rummId != null) {
                    SysUser rum = sysUserMapper.getUserByNo(rummId);
                    if (rum != null) {
                        if (ObjectUtils.isNotEmpty(rum.getSignPic())) {
                            wz.put("sign", prefix + rum.getSignPic());
                        } else {
                            wz.put("doctorName", rum.getUserName());
                        }
                    }
                }
            }
            four = new HashMap<>();
            four.put("doctor", wz);

            Collection<Map<String, String>> symboList = new Vector<Map<String, String>>();
            Set<String> symbols = new HashSet<String>();//用于去重
            Map<String, String> symbo = null;
            //科室中录入的
            String illness = isHealth ? (pos[1] == null ? "" : pos[1].toString()) : pc.getSymptom();
            if (!StringUtils.isEmpty(illness) && !"null".equals(illness)) {
                illness = illness.replaceAll(" ", "");
                // 拆分出所有的病和级别
                String[] ills = illness.split(",");
                if (ills != null && ills.length > 0) {
                    for (int i = 0; i < ills.length; i++) {
                        String str = ills[i];
                        if (!StringUtils.isEmpty(str)) {
                            String[] nameAndLevel = str.split(":");// 拆分出病名和级别
                            if (nameAndLevel != null && nameAndLevel.length == 2) {
                                String name = nameAndLevel[0];
                                String value = nameAndLevel[1];
                                if (symbo == null || StringUtils.isNotEmpty(symbo.get("name2"))) {
                                    symbo = new HashMap<String, String>();
                                    symboList.add(symbo);
                                    symbo.put("name", name);
                                    symbo.put("value", value);
                                    symbo.put("name2", "");
                                    symbo.put("value2", "");
                                } else {
                                    symbo.put("name2", name);
                                    symbo.put("value2", value);
                                }
                                symbols.add(name);
                            }
                        }
                    }
                }
            }
            //重点问询症状，没录入的是-号
            List<EmphasisAskSymptom> eas = emphasisAskSymptomMapper.selectList(new QueryWrapper<EmphasisAskSymptom>()
                    .eq("is_delete", 0)
                    .eq("disiase_type", Integer.parseInt(patient.getMedicaltype()))
                    .in("harmname", patient.getJhys().split(",")));
            for (EmphasisAskSymptom ea : eas) {
                String name = ea.getSymptom();
                if (!symbols.contains(name)) {
                    //男性不显示月经异常
                    if (name.equals("月经异常") && patient.getIdSex()==0) {
                        continue;
                    }
                    if (symbo == null || StringUtils.isNotEmpty(symbo.get("name2"))) {
                        symbo = new HashMap<String, String>();
                        symboList.add(symbo);
                        symbo.put("name", name);
                        symbo.put("value", "-");
                        symbo.put("name2", "");
                        symbo.put("value2", "");
                    } else {
                        symbo.put("name2", name);
                        symbo.put("value2", "-");
                    }
                    symbols.add(name);
                }
            }
            wz.put("SYMBOL_LIST", symboList);
            four.put("list", symboList);
        }
        //把职业报告4放进去
        if (ObjectUtils.isNotEmpty(four)) {
            head.put("four", four);
            vo.setHead(head);
        }
        List<NursingRegistrationDto> noInspector = new ArrayList<>();
        List<String> reviewerDept = new ArrayList<>();
        List<String> examinerDept = new ArrayList<>();

        //获取单签名的科室图片
        ReportSignatureConfigDto reportSignatureConfigDto = iSysConfigService.getSysConfigObject(Constants.REPORT_SIGNATURE_CONFIG, ReportSignatureConfigDto.class);
        if (ObjectUtils.isNotEmpty(reportSignatureConfigDto)) {
            if (CollectionUtils.isNotEmpty(reportSignatureConfigDto.getParam())){
                noInspector = reportSignatureConfigDto.getParam();
            }
            if (CollectionUtils.isNotEmpty(reportSignatureConfigDto.getReviewerParam())){
                List<NursingRegistrationDto> nursingRegistrationDtoList = reportSignatureConfigDto.getReviewerParam();
                reviewerDept = nursingRegistrationDtoList.stream()
                        .map(NursingRegistrationDto::getId)
                        .collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(reportSignatureConfigDto.getExaminerParam())){
                List<NursingRegistrationDto> nursingRegistrationDtoList = reportSignatureConfigDto.getExaminerParam();
                examinerDept = nursingRegistrationDtoList.stream()
                        .map(NursingRegistrationDto::getId)
                        .collect(Collectors.toList());
            }
        }

        /* 复查头部 */
        if (isfc) {
            Map<String, Object> partHead = new HashMap<String, Object>();
            partHead.put("part", -1);
            String branchName = "青岛沃德国际体检中心";
            if (StringUtils.equals(loadProperties.name, "jindu")) {
                branchName = "青岛锦都医院";
            } else if (StringUtils.equals(loadProperties.name, "huaou")) {
                branchName = "黄岛沃德中医康复医院";
            } else if (StringUtils.equals(loadProperties.name, "weifang")) {
                branchName = "潍坊沃德东宜体检中心";
            }
            partHead.put("branchName", branchName);
            partHead.put("title", "1".equals(isJy) ? "检验" : "职业复查");
            partHead.put("patientcode", patientcode);
            partHead.put("patientname", patientname);
            partHead.put("patientorg", patient.getOrgName());
            partHead.put("patientphone", patient.getPhone());
            partHead.put("patientage", patient.getAge() == null ? "" : patient
                    .getAge().intValue());
            partHead.put("patientsex", patient.getIdSex() == 0 ? "男"
                    : "女");
            String medicaltype = patient.getMedicaltype();
            if ("0".equals(medicaltype)) {
                partHead.put("patientmedicaltype", "上岗前");
            } else if ("1".equals(medicaltype)) {
                partHead.put("patientmedicaltype", "在岗期间");
            } else if ("2".equals(medicaltype)) {
                partHead.put("patientmedicaltype", "离岗时");
            } else if ("3".equals(medicaltype)) {
                partHead.put("patientmedicaltype", "离岗后");
            } else if ("4".equals(medicaltype)) {
                partHead.put("patientmedicaltype", "应急");
            }
            String jhyss = patient.getJhys();
            if (jhyss != null) {
                List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhyss.split(",")));
                StringBuilder jhys = new StringBuilder();
                for (int i = 0; i < harms.size(); i++) {
                    jhys.append(harms.get(i).getHarmName());
                    if (i != harms.size() - 1) {
                        jhys.append("、");
                    }
                }
                partHead.put("patientharm", jhys.toString());
            }
            //总工龄和接害工龄
            partHead.put("zgl", ToolUtil.getYearMonth(patient.getZgl()));
            partHead.put("jhgl", ToolUtil.getYearMonth(patient.getJhgl()));
            //赋码图片
            Attachment reportCoding = attachmentService.getOne(new LambdaQueryWrapper<Attachment>()
                    .eq(Attachment::getPatientcode, patientcode).eq(Attachment::getFileType, "赋码"));
            if (ObjectUtils.isNotEmpty(reportCoding)){
                parameters.put("reportCoding",prefix + reportCoding.getFilePath());
            }
            ksList.add(partHead);
            vo.setHead(partHead);
        }


        String maxImageConf = Constants.DEPT_MAX_IMAGE;
        Integer maxImageNum = "1".equals(showAllImage) || StringUtils.isEmpty(maxImageConf) ?
                null : Integer.parseInt(maxImageConf);
        //检验部分签名
        String lrctJyAuditName = Constants.JYSIGN;
        String oldpacs = Constants.OLDPACS;
        String oldReplace = null;
        String oldTarget = null;
        if (StringUtils.isNotEmpty(oldpacs) && oldpacs.indexOf("=") != -1) {
            String[] oparr = oldpacs.split("=");
            oldTarget = oparr[0];
            oldReplace = oparr[1];
        }
        boolean isNeedReplace = "1".equals(Constants.ISNEEDREPLACE)
                && StringUtils.isNotEmpty(oldTarget)
                && StringUtils.isNotEmpty(oldReplace);
        Collection<Map<String, Object>> depList = new Vector<Map<String, Object>>();

        //将易影云胶片二维码添加至最后一个有易影结果的放射科室后面
        YiyingConfig yiyingConfig = iSysConfigService.getSysConfigObject(Constants.YIYING_CONFIG, YiyingConfig.class);
        //最后一个有易影结果的放射科室编号
        String lastYiyingDeptNo=null;
        //易影云胶片二维码base64
        List<String> yiyingQrCodeBase64s=null;
        if(yiyingConfig!=null && yiyingConfig.getIsShowQrcode()!=null && yiyingConfig.getIsShowQrcode().booleanValue()){
            lastYiyingDeptNo=pacsResultThirdService.getLastYiyingDeptNo(patientcode,deps);
            if(StrUtil.isNotEmpty(lastYiyingDeptNo))yiyingQrCodeBase64s=pacsResultThirdService.createQrCodeBase64(
                    patientcode
                    , CodeUtil.getShortCodeByLong(patientcode).toString()
                    ,yiyingConfig);
        }

        //科室数据
        List<IReportDto> iReportDtoList = new ArrayList<>();
        for (SysDept dept : deps) {
            List<Map> itemMapList = new ArrayList<>();
            List<Map> picList = new ArrayList<>();//用于存放图片的方便同步
            IReportDto dto = new IReportDto();
            if ("1".equals(isJy) && !"19".equals(dept.getDeptNo())) { //检验报告只看检验科
                continue;
            }
            String depName = dept.getDeptName();
            String depId = dept.getDeptNo();
            int sjbggs = dept.getSjbggs();

            int targetDh = dh;
            boolean noInspectorFound = noInspector.stream()
                    .anyMatch(person -> (ObjectUtils.isNotEmpty(person.getDh()) && (person.getDh() == targetDh || person.getDh() == 2))
                            && person.getId().equals(depId));
            if (sjbggs == 6 || sjbggs == 4 || sjbggs == 11) {// 内科、视力、碳13
                /** 头部 */
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", 0);
                part0.put("sjbggs", sjbggs);
                part0.put("DEP_NAME", depName);
                dto.setHead(part0);
                depList.add(part0);

                /** 收费项目 */
                List<CaseDataDto> list = iPersonalReportMapper.getCaseData(patientcode, depId, dh);
                Map<String, Object> itemMap = null;
                String fItemName = null;
                Collection<Map<String, String>> examCol = null;
                for (int i = 0; i < list.size(); i++) {
                    CaseDataDto os = list.get(i);
                    String itemName = os.getFeeName() != null ? String.valueOf(os.getFeeName()) : "";
                    //如果描述为空就跳过
                    if (StringUtils.isEmpty(os.getDescribe())) {
                        continue;
                    }
                    if (!itemName.equals(fItemName)) {
                        fItemName = itemName;
                        itemMap = new HashMap<String, Object>();
                        itemMap.put("ITEM_NAME", itemName);
                        examCol = new Vector<Map<String, String>>();
                        itemMap.put("EXAMLIST", examCol);
                        itemMap.put("sjbggs", sjbggs);
                        itemMap.put("part", 1);
                        itemMapList.add(itemMap);
                        depList.add(itemMap);
                    }
                    //妇科的其他和初步诊断，如果是无和未见明显异常的话就给个空
                    if ("妇科".equals(itemName) && "其他".equals(os.getItemName()) && "无;".equals(os.getDescribe())) {
                        os.setDescribe("");
                    }
                    if ("妇科".equals(itemName) && "初步诊断".equals(os.getItemName()) && "未见明显异常;".equals(os.getDescribe())) {
                        os.setDescribe("");
                    }
                    Map<String, String> examMap = new HashMap<String, String>();
                    examMap.put("ITEM",
                            os.getItemName() == null ? "" : os.getItemName().toString());
                    examMap.put(
                            "SIGN",
                            os.getDescribe() == null ? "" : os.getDescribe());
                    examCol.add(examMap);
                }
                dto.setItem(itemMapList);

                /** 小结 */
                Map<String, Object> part2 = new HashMap<String, Object>();
                part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                part2.put("part", 2);
                part2.put("sjbggs", sjbggs);
                part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode).eq("dep_id", depId));
                if (section != null) {
                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                section.getZyConclusions() == null ? ""
                                        : section.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                section.getConclusions() == null ? ""
                                        : section.getConclusions());
                    }
                    part2.put("audit_time",
                            auditFormat.format(section.getAuditTime()));
                    //医师签名
                    if (noInspectorFound){
                        //内科，耳科，放射，心电图 不需要检查者
                    }else {
                        setRummager(part2, section.getRummagerName(), prefix,dept.getDeptNo(),dh);
                    }
                    setAudit(part2, section.getAuditName(), prefix,dept.getDeptNo(),dh);
                    //小结
                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
                }
                dto.setSummary(part2);

                depList.add(part2);
            } else if (sjbggs == 1) {
                // 检验科
                //如果只有隐私项目，就不显示检验科
                List<AllInspectDto> list = iPersonalReportMapper.findAllInspect(patientcode, dh);
                if (list.size() == 0) {
                    continue;
                }
                String inspectionFooter = Constants.INSPECTION_FOOTER;
                if (StringUtils.equals(loadProperties.name, "hn") || StringUtils.equals(loadProperties.name, "bazhou")) {
                    inspectionFooter = null;
                } else {
                    inspectionFooter = "本次检验结果获山东省通用认证(“一单通”)。";
                }
                /**头部 */
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", 0);
                part0.put("yblx", depName);
                part0.put("sjbggs", sjbggs);
                part0.put("inspectionFooter", inspectionFooter);
                dto.setHead(part0);
                depList.add(part0);

                /**收费项目*/
                String special_inspection_exam_items = Constants.SPECIAL_INSPECTION_EXAM_ITEMS;
                String[] sepcialArr = special_inspection_exam_items == null ? new String[0] : special_inspection_exam_items.trim().split(",");
                List<String> speciallList = Arrays.asList(sepcialArr);
                Map<String, Object> itemMap = null;
                String fItemName = null;
                Collection<Map<String, String>> examCol = null;
                String p_name = "";
                String p_name_lab = "";
                AdiconSignatureConfig adiconSignatureConfig = iSysConfigService.getSysConfigObject(Constants.ADICON_SIGNATURE_CONFIG, AdiconSignatureConfig.class);
//                if ("1".equals(doctorType)) {
//                    p_name = Constants.LAB_AUDITER;
//                    p_name_lab = Constants.LAB_RUMMAGER;
//                }
                //肝功、肾功、血糖、血脂这四项的标本采集时间应该是一样的，四项的报告时间是一样的(按标本类型分类)
                Map<String, String> idLabtypeMap = new HashMap<String, String>();
                for (int i = 0; i < list.size(); i++) {
                    AllInspectDto os = list.get(i);
                    String itemName = os.getExamfeeitemNameprn() != null ? String.valueOf(os.getExamfeeitemNameprn()) : "";
                    String txt = os.getExamitemvaluestext() == null ? "" : os.getExamitemvaluestext().toString();
                    //item_name包含后面空格，最多34个字符，超过34个字符医生名字为xxx/xxx时最后一个字显示不出来
                    //打印名称最长为34
                    //当打印名称小于等于10，留出24个空格，大于10，留出34-length个空格
                    //如果日期和医生名字超出一般长度，修改模板，删掉几个固定空格，现已预留出三个空格
                    //实际中文长度比其他字符更长（awt中为13:7），按2:1处理
                    //在GBK和GB2312编码下一个汉字(包括中文形式下的符号)是2个字节，一个英文(包括英文下的符号)1个字节，一个数字1个字节；
                    //UTF-8编码下一个汉字(包括中文形式下的符号)一般是3个字节，一个英文(包括英文下的符号)1个字节，一个数字1个字节；
                    //gb2312下直接用字节数判断长度
                    int bl = 0;
                    try {
                        bl = itemName.getBytes("GB2312").length;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    int l = bl <= 10 ? 24 : 34 - bl;
                    for (int ii = 0; ii < l; ii++) {
                        itemName = itemName + " ";
                    }
                    if (!itemName.equals(fItemName)) {
                        itemMap = new HashMap<String, Object>();
                        itemMap.put("TXT_VALUES", txt);
                        itemMap.put("ITEM_NAME", itemName);
                        itemMap.put("DATE", ObjectUtils.isNotEmpty(os.getAuditDate()) ? os.getAuditDate() : "");
                        //老年人查体 自动生成签收时间和采集时间，
                        if (isHealth) {
                            String idLabtype = os.getIdLabtype() == null ? "null" : os.getIdLabtype().toString();
                            String generated = idLabtypeMap.get(idLabtype);
                            if (StringUtils.isEmpty(generated)) {
                                Date dateregister = patient.getDateregister();
                                Calendar c = Calendar.getInstance();
                                c.setTime(dateregister);
                                //标本采集时间根据登记时间，与登记时间间隔10-20分钟,自动生成
                                c.add(Calendar.MINUTE, getRandom(10, 10));
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                itemMap.put("specimenCollectionTime", sdf.format(c.getTime()));
                                //报告时间与标本采集时间间隔90-110分钟
                                c.add(Calendar.MINUTE, getRandom(90, 20));
                                itemMap.put("reportTime", sdf.format(c.getTime()));

                                idLabtypeMap.put(idLabtype, itemMap.get("specimenCollectionTime")
                                        + "," + itemMap.get("reportTime"));
                            } else {
                                String[] generatedArr = generated.split(",");
                                itemMap.put("specimenCollectionTime", generatedArr[0]);
                                itemMap.put("reportTime", generatedArr[1]);
                            }
                        }
                        itemMap.put("RECEIVE_DATE", os.getReceiveDate() != null ? os.getReceiveDate() : "");

                        //如果艾迪康代码和配置的审核人都不为空，就取配置中人，否则谁审核的就是谁
                        if (StringUtils.isNotEmpty(os.getIdCooporg()) && "1".equals(os.getIdCooporg()) && ObjectUtils.isNotEmpty(adiconSignatureConfig)) {
                            setDoctor(itemMap, adiconSignatureConfig.getAuditName(), "jy_audit_path", "P_NAME", prefix);
                            setDoctor(itemMap, adiconSignatureConfig.getInspectName(), "jy_rummager_path", "P_NAME_LAB", prefix);
                        } else {
                            //审核人
                            setDoctor(itemMap, os.getAuditName() == null ? null : os.getAuditName(), "jy_audit_path", "P_NAME", prefix);
                            //检查人
                            setDoctor(itemMap, os.getInspectName() == null ? null : os.getInspectName(), "jy_rummager_path", "P_NAME_LAB", prefix);
                        }

                        itemMap.put("part", 1);
                        itemMap.put("sjbggs", sjbggs);
                        examCol = new Vector<Map<String, String>>();
                        itemMap.put("EXAMLIST", examCol);
                        itemMap.put("inspectionFooter", inspectionFooter);
                        itemMapList.add(itemMap);
                        depList.add(itemMap);
                        fItemName = itemName;
                    } else {
                        itemMap.put("TXT_VALUES", itemMap.get("TXT_VALUES") + "\n" + txt);
                    }

                    Map<String, String> examMap = new HashMap<String, String>();
                    examMap.put("ITEM",
                            os.getExamitemNameprn() == null ? "" : os.getExamitemNameprn().toString());
                    examMap.put("RESULT",
                            os.getExamitemvaluesreport() == null ? "" : os.getExamitemvaluesreport().toString());
                    examMap.put("SIGN",
                            os.getUnits() == null ? "" : os.getUnits().toString());
                    //箭头去重,因为潍坊有多个箭头的数据，现在报告模板里只判断了一个,可能会造成报告不标红的情况
                    examMap.put("CONSULT",
                            os.getStatus() == null ? "" : arrowDeduplication(os.getStatus().toString()));
                    examMap.put("UNIT",
                            os.getRefrange() == null ? "" : os.getRefrange().toString());
                    String examId = os.getIdExamitem() == null ? "" : os.getIdExamitem().toString();
                    examMap.put("red",
                            (speciallList.contains(examId)
                                    && os.getExamitemvaluesreport() != null
                                    && !"阴性".equals(os.getExamitemvaluesreport().toString().trim())
                                    && !"未见".equals(os.getExamitemvaluesreport().toString().trim())
                            )
                                    ? "1" : "0");
                    examCol.add(examMap);
                }
                dto.setItem(itemMapList);

                /** 小结 */
                Map<String, Object> part2 = new HashMap<String, Object>();
                part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                part2.put("part", 2);
                part2.put("sjbggs", sjbggs);
                part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                part2.put("inspectionFooter", inspectionFooter);
                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", dept.getDeptNo()));
                if (section != null) {
                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                section.getZyConclusions() == null ? ""
                                        : section.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                section.getConclusions() == null ? ""
                                        : section.getConclusions());
                    }
                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
                }
                dto.setSummary(part2);
                depList.add(part2);
            } else if (sjbggs == 3) {// 一般检查
                /** 头部 */
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", 0);
                part0.put("sjbggs", sjbggs);
                part0.put("DEP_NAME", depName);
                dto.setHead(part0);
                depList.add(part0);

                /** 收费项目 */
                Map<String, Object> part1 = new HashMap<String, Object>();
                part1.put("part", 1);
                part1.put("sjbggs", sjbggs);
                Collection<Map<String, String>> examList = getFairCheckData(dh, patientcode, dept, isHealth);
                part1.put("EXAMLIST", examList);
                itemMapList.add(part1);
                dto.setItem(itemMapList);
                depList.add(part1);

                /** 小结 */
                Map<String, Object> part2 = new HashMap<String, Object>();
                part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                part2.put("part", 2);
                part2.put("sjbggs", sjbggs);
                part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", dept.getDeptNo()));
                if (section != null) {
                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                section.getZyConclusions() == null ? ""
                                        : section.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                section.getConclusions() == null ? ""
                                        : section.getConclusions());
                    }
                    part2.put("audit_time",
                            auditFormat.format(section.getAuditTime()));
                    if (noInspectorFound){
                        //某些科室只要单签名
                    }else {
                        setRummager(part2, section.getRummagerName(), prefix,dept.getDeptNo(),dh);
                    }

                    setAudit(part2, section.getAuditName(), prefix,dept.getDeptNo(),dh);

                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
                }
                dto.setSummary(part2);
                depList.add(part2);
            } else if (sjbggs == 7 || sjbggs == 10) {// 图像、骨密度
                List<SummaryAndPicturesDto> summaryAndPictures = new ArrayList<>();
                if (StringUtils.equals(loadProperties.name, "hn")) {
                    //获取小结及图片数据
                    summaryAndPictures = describeMapper.getSummaryAndPictures(patientcode,dept.getDeptNo());
                    String rn = "\n";//换行符
                    List<String> separateDepts = Arrays.asList("143","24","171","173","402848e3625a920201625ff99a3404a5");
                    for (SummaryAndPicturesDto pr : summaryAndPictures) {
                        if (pr.getIdKs() != null && separateDepts.contains(pr.getIdKs())) {
                            StringBuilder conclusions = new StringBuilder();
                            //重新生成健康小结和职业小结
                            if (pr.getExamresultdesc() != null) {
                                conclusions.append("[" + pr.getItemName() + "]所见：" + rn);
                                conclusions.append(pr.getExamresultdesc() + rn);
                            }
                            if (pr.getExamresultsummary() != null) {
                                conclusions.append("[" + pr.getItemName() + "]提示：" + rn);
                                conclusions.append(pr.getExamresultsummary() + rn);
                            }
                            //都一样的直接设置进去就行
                            pr.setConclusions(conclusions.toString());
                            pr.setZyConclusions(conclusions.toString());
                            //把pacsResult的检查人和审核人设置进去
                            pr.setRummagerName(pr.getExamdoctor());
                            pr.setAuditName(pr.getAuditDoctor());
                        }
                    }
                }else{
                    summaryAndPictures = describeMapper.getOldSummaryAndPictures(patientcode,dept.getDeptNo());
                }

                for (SummaryAndPicturesDto pr : summaryAndPictures) {
                    /** 头部 */
                    Map<String, Object> part0 = new HashMap<String, Object>();
                    part0.put("part", 0);
                    part0.put("sjbggs", sjbggs);
                    part0.put("DEP_NAME", depName+ (StringUtils.isEmpty(pr.getExamfeeitemNameprn()) ? "" : (":" + pr.getExamfeeitemNameprn())));
                    dto.setHead(part0);
                    depList.add(part0);
                    boolean showPic = true;

                    /** 图片 */
                    if (dept.getAddPicBefore() != null
                            && "1".equals(dept.getAddPicBefore())
                            && showPic) {
                        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<Attachment>().orderByAsc("fee_item_id", "file_path")
                                .eq("patientCode", patientcode).eq("dep_id", dept.getDeptNo());
                        if (StringUtils.isNotEmpty(pr.getId())){
                            queryWrapper.eq("fee_item_id", pr.getId());
                        }
                        List<String> cc = Arrays.asList("huaou", "jiaozhou","jiaonan","bazhou");
                        List<String> pickingPicture = Arrays.asList("173", "402848e3625a920201625ff99a3404a5");
                        List<String> yx = Arrays.asList("hn", "pingdu");
                        //彩超科室，不是华欧、胶州、胶南的取进报告的图片。
                        //淮南、平度磁共振和CT也取进报告的图片
                        if (("143".equals(dept.getDeptNo()) && !cc.contains(loadProperties.name)) ||
                                (pickingPicture.contains(dept.getDeptNo()) && yx.contains(loadProperties.name))) {
                            //只有彩超才取进报告的图片,华欧取所有的图片不需要勾选.淮南ct也只取进报告的图片
                            queryWrapper.eq("in_report", 1);
                        }

                        List<Attachment> atts = attachmentMapper.selectList(queryWrapper);
                        //长沙ct不要图
                        if ("173".equals(dept.getDeptNo()) && StringUtils.equals(loadProperties.name, "changsha")){
                            atts = new ArrayList<>();
                        }
                        if (pacs) {//如果是综合职业报告
                            atts = getZyAtts(atts, jhysStr, medicalType);
                        }

                        int size = atts.size();
                        //dr 和 彩超 不限制图片数量
                        if (!CCKS.equals(dept.getDeptNo()) && !"24".equals(dept.getDeptNo())) {
                            size = maxImageNum > size ? size : maxImageNum;
                        }

                        //一行显示几张图片,默认一张
                        int wid = 1;
                        //显示三张图片的
                        if ("173".equals(dept.getDeptNo())
                                || "402848e3625a920201625ff99a3404a5".equals(dept.getDeptNo())
                                || ("143".equals(dept.getDeptNo()) && StringUtils.equals(loadProperties.name, "huaou"))
                        ) {
                            wid = 3;
                        }else if ("176".equals(dept.getDeptNo())
                                || "143".equals(dept.getDeptNo())
                                || "24".equals(dept.getDeptNo())
                                || "171".equals(dept.getDeptNo())
                                || "165".equals(dept.getDeptNo())
                                || "163".equals(dept.getDeptNo())
                        ) {
                            //显示两张图片的
                            wid = 2;
                        }


                        Map<String, Object> part3 = null;
                        for (int a = 0; a < size; a++) {
                            Attachment att = atts.get(a);
                            String iamgePath = att.getFilePath();

                            reportPicList.add(iamgePath);

                            if (wid == 1) {
                                part3 = new HashMap<String, Object>();// 一页显示一张图片
                                part3.put("part", 3);
                                part3.put("image", prefix + iamgePath);
                                part3.put("picSize", wid);
                                depList.add(part3);
                                picList.add(part3);
                            } else if (wid == 2) {// 一页显示两张图片
                                if (part3 == null
                                        || (ObjectUtils.isNotEmpty(part3.get("picSize"))
                                        && "2".equals(part3.get("picSize").toString()))) {
                                    part3 = new HashMap<String, Object>();
                                    depList.add(part3);
                                    picList.add(part3);
                                    part3.put("picSize", 0);//不被2整除时  最后一行一个图(单独detail band)
                                    part3.put("image", prefix + iamgePath);
                                    part3.put("part", 3);
                                } else {
                                    part3.put("picSize", 2);
                                    part3.put("image2", prefix + iamgePath);
                                }
                            } else if (wid == 3) { //一页显示三张图片
                                int ys = a % 3;
                                if (ys == 0) {
                                    part3 = new HashMap<String, Object>();
                                    depList.add(part3);
                                    picList.add(part3);
                                    part3.put("image", prefix + iamgePath);
                                    part3.put("part", 3);
                                    part3.put("picSize", 3);
                                } else if (ys == 1) {
                                    part3.put("image2", prefix + iamgePath);
                                } else {
                                    part3.put("image3", prefix + iamgePath);
                                }
                            }
                        }

                    }
                    dto.setPicture(picList);

                    /** 小结 */
                    Map<String, Object> part2 = new HashMap<String, Object>();
                    part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                    part2.put("part", 2);
                    part2.put("sjbggs", sjbggs);
                    part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                    part2.put("ksId", dh == 0 ? depId : "");


                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                pr.getZyConclusions() == null ? ""
                                        : pr.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                pr.getConclusions() == null ? ""
                                        : pr.getConclusions());
                    }
                    part2.put("audit_time",
                            pr.getAuditTime() == null ? ""
                                    : auditFormat.format(pr
                                    .getAuditTime()));
                    if (noInspectorFound){
                        //内科，耳科，放射，心电图 不需要检查者
                    }else {
                        setRummager(part2, pr.getRummagerName(), prefix,dept.getDeptNo(),dh);
                    }
                    setAudit(part2, pr.getAuditName(), prefix,dept.getDeptNo(),dh);

                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());

                    dto.setSummary(part2);
                    depList.add(part2);

                }

                //添加易影二维码
                if(depId.equals(lastYiyingDeptNo) && yiyingQrCodeBase64s!=null && yiyingQrCodeBase64s.size()>0){
                    Map<String, Object> part4 =null;
                    //如果只有一个，放在中间
                    if(yiyingQrCodeBase64s.size()==1){
                        part4 = new HashMap<>();
                        depList.add(part4);
                        part4.put("part", 4);
                        //base64+ 0:左 1:中 2:右
                        part4.put("base641",yiyingQrCodeBase64s.get(0));
                    }else{
                        //如果有多个，从左到右，每行3个
                        for(int i=0;i<yiyingQrCodeBase64s.size();i++){
                            if(i%3==0){
                                part4 = new HashMap<>();
                                depList.add(part4);
                                part4.put("part", 4);
                            }
                            part4.put("base64"+(i%3),yiyingQrCodeBase64s.get(i));
                        }
                    }
                }

            } else if (sjbggs == 5) {// 肺功能
                /** 头部 */
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", 0);
                part0.put("sjbggs", sjbggs);
                part0.put("DEP_NAME", dept.getDeptName());
                dto.setHead(part0);
                depList.add(part0);

                /** 项目 */
                Lung lung = lungMapper.selectOne(new QueryWrapper<Lung>().eq("patientcode", patientcode));
                if (lung != null) {
                    Map<String, Object> part1 = new HashMap<String, Object>();
                    part1.put("part", 1);
                    part1.put("sjbggs", sjbggs);
                    part1.put("dh", dh);
                    part1.put("FVC", lung.getFvc());
                    part1.put("PREFVC", lung.getPredictFvc());
                    part1.put("PERFVC", lung.getPercentageFvc());
                    part1.put("FEV", lung.getFev());
                    part1.put("PREFEV", lung.getPredictFev());
                    part1.put("PERFEV", lung.getPercentageFev());
                    part1.put("FEV_FVC", lung.getFevFvc());
                    part1.put("PREFEV_FVC", lung.getPredictFevFvc());
                    part1.put("PERFEV_FVC", lung.getPercentageFevFvc());
                    part1.put("MMEF", lung.getMmef());
                    part1.put("PREMMEF", lung.getPredictMmef());
                    part1.put("PERMMEF", lung.getPercentageMmef());
                    part1.put("FEFFC", lung.getFeffc());
                    part1.put("PREFEFFC", lung.getPredictFeffc());
                    part1.put("PERFEFFC", lung.getPercentageFeffc());
                    part1.put("FEFFB", lung.getFeffb());
                    part1.put("PREFEFFB", lung.getPredictFeffb());
                    part1.put("PERFEFFB", lung.getPercentageFeffb());
                    part1.put("FEFFA", lung.getFeffa());
                    part1.put("PREFEFFA", lung.getPredictFeffa());
                    part1.put("PERFEFFA", lung.getPercentageFeffa());
                    itemMapList.add(part1);
                    depList.add(part1);
                }
                dto.setItem(itemMapList);

                /** 图片 肺功能图片改为尾部显示
                 * */
//                if (dept.getAddPicBefore() != null && "1".equals(dept.getAddPicBefore())) {
//                    List<Attachment> atts = attachmentMapper.selectList(new QueryWrapper<Attachment>().orderByAsc("fee_item_id", "file_path")
//                            .eq("patientcode", patientcode)
//                            .eq("dep_id", dept.getDeptNo()));
//
//                    if (pacs) {//如果是综合职业报告
//                        atts = getZyAtts(atts, jhysStr, medicalType);
//                    }
//
//                    int size = atts.size();
//                    if (!CCKS.equals(dept.getDeptNo()) && maxImageNum != null) {
//                        size = maxImageNum > size ? size : maxImageNum;
//                    }
//
//                    String depSrm = dept.getInputCode();
//                    for (int a = 0; a < size; a++) {
//                        Attachment att = atts.get(a);
//                        String iamgePath = att.getFilePath();
//                        reportPicList.add(iamgePath);
//
//                        Map<String, Object> part3 = new HashMap<String, Object>();
//                        part3.put("image", prefix + iamgePath);
//                        part3.put("part", 3);
//                        part3.put("picSize", 1);
//                        depList.add(part3);
//                        picList.add(part3);
//                        depList.add(part3);
//                    }
//                }
//                dto.setPicture(picList);

                /** 小结 */
                Map<String, Object> part2 = new HashMap<String, Object>();
                part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                part2.put("part", 2);
                part2.put("sjbggs", sjbggs);
                part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", dept.getDeptNo()));
                if (section != null) {
                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                section.getZyConclusions() == null ? ""
                                        : section.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                section.getConclusions() == null ? ""
                                        : section.getConclusions());
                    }
                    part2.put("audit_time",
                            auditFormat.format(section.getAuditTime()));
                    setAudit(part2, section.getAuditName(), prefix,dept.getDeptNo(),dh);
                    setRummager(part2, section.getRummagerName(), prefix,dept.getDeptNo(),dh);

                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
                }
                dto.setSummary(part2);
                depList.add(part2);
            } else if (sjbggs == 9) {// 电测听(职业不测骨导)
                /** 头部 */
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", 0);
                part0.put("sjbggs", sjbggs);
                part0.put("DEP_NAME", dept.getDeptName());
                dto.setHead(part0);
                depList.add(part0);

                /** 项目 */
                ElectroAudiometer ele = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>().eq("patientcode", patientcode));
                if (ele != null) {
                    Map<String, Object> part1 = new HashMap<String, Object>();
                    part1.put("part", 1);
                    part1.put("sjbggs", sjbggs);
                    part1.put("tjlx", Integer.parseInt(ele.getIdExamtype()));// 体检者体检类型
                    Collection<Map<String, String>> examList = new Vector<Map<String, String>>();
                    Map<String, Object> elemap = BeanUtil.beanToMap(ele);
                    boolean boneFlag = false;
                    for (String key : elemap.keySet()) {
                        if (key.contains("boneLeft") || key.contains("boneRight")) {
                            if (ObjectUtils.isNotEmpty(elemap.get(key))) {
                                boneFlag = true;
                                break;
                            }
                        }
                    }
                    if ("0".equals(ele.getIdExamtype())) {
                        Map<String, String> row = new HashMap<String, String>();
                        row.put("series", "");
                        row.put("125HZ", "125HZ");
                        row.put("250HZ", "250HZ");
                        row.put("500HZ", "500HZ");
                        row.put("1KHZ", "1KHZ");
                        row.put("2KHZ", "2KHZ");
                        row.put("4KHZ", "4KHZ");
                        row.put("6KHZ", "6KHZ");
                        row.put("8KHZ", "8KHZ");
//                        if (ObjectUtils.isNotEmpty(ele.getAirLeft8000()) ||  ObjectUtils.isNotEmpty(ele.getAirRight8000())){
//                            row.put("8KHZ", "8KHZ");
//                        }
                        examList.add(row);
                        row = new HashMap<String, String>();
                        row.put("series", "气导左耳");
                        row.put("125HZ", getString(ele.getAirLeft125()));
                        row.put("250HZ", getString(ele.getAirLeft250()));
                        row.put("500HZ", getString(ele.getAirLeft500()));
                        row.put("1KHZ", getString(ele.getAirLeft1000()));
                        row.put("2KHZ", getString(ele.getAirLeft2000()));
                        row.put("4KHZ", getString(ele.getAirLeft4000()));
                        row.put("6KHZ", getString(ele.getAirLeft6000()));
                        row.put("8KHZ", getString(ele.getAirLeft8000()));
//                        if (ObjectUtils.isNotEmpty(ele.getAirLeft8000())){
//                            row.put("8KHZ", getString(ele.getAirLeft8000()));
//                        }
                        examList.add(row);
                        row = new HashMap<String, String>();
                        row.put("series", "气导右耳");
                        row.put("125HZ", getString(ele.getAirRight125()));
                        row.put("250HZ", getString(ele.getAirRight250()));
                        row.put("500HZ", getString(ele.getAirRight500()));
                        row.put("1KHZ", getString(ele.getAirRight1000()));
                        row.put("2KHZ", getString(ele.getAirRight2000()));
                        row.put("4KHZ", getString(ele.getAirRight4000()));
                        row.put("6KHZ", getString(ele.getAirRight6000()));
                        row.put("8KHZ", getString(ele.getAirRight8000()));
//                        if (ObjectUtils.isNotEmpty(ele.getAirRight8000())){
//                            row.put("8KHZ", getString(ele.getAirRight8000()));
//                        }
                        examList.add(row);
                        if (boneFlag) {
                            row = new HashMap<String, String>();
                            row.put("series", "骨导左耳");
                            row.put("125HZ", "");
                            row.put("250HZ", getString(ele.getBoneLeft250()));
                            row.put("500HZ", getString(ele.getBoneLeft500()));
                            row.put("1KHZ", getString(ele.getBoneLeft1000()));
                            row.put("2KHZ", getString(ele.getBoneLeft2000()));
                            row.put("4KHZ", getString(ele.getBoneLeft4000()));
                            row.put("6KHZ", getString(ele.getBoneLeft6000()));
                            row.put("8KHZ", getString(ele.getBoneLeft8000()));
                            examList.add(row);
                            row = new HashMap<String, String>();
                            row.put("series", "骨导右耳");
                            row.put("125HZ", "");
                            row.put("250HZ", getString(ele.getBoneRight250()));
                            row.put("500HZ", getString(ele.getBoneRight500()));
                            row.put("1KHZ", getString(ele.getBoneRight1000()));
                            row.put("2KHZ", getString(ele.getBoneRight2000()));
                            row.put("4KHZ", getString(ele.getBoneRight4000()));
                            row.put("6KHZ", getString(ele.getBoneRight6000()));
                            row.put("8KHZ", getString(ele.getBoneRight8000()));
                            examList.add(row);
                        }
                    } else {
                        int age = patient.getAge().intValue();
                        String sex = patient.getIdSex().toString();
                        String sexPre = ("0".equals(sex) ? "M" : "F") + "_List_";
                        Map<String, String> dctPro = Constants.DCT;
                        Map<String, String> row = new HashMap<String, String>();
                        row.put("series", "气导左耳");
                        row.put("500HZ", getString(ele.getAirLeft500()));
                        row.put("1KHZ", getString(ele.getAirLeft1000()));
                        row.put("2KHZ", getString(ele.getAirLeft2000()));
                        row.put("3KHZ", getString(ele.getAirLeft3000()));
                        row.put("4KHZ", getString(ele.getAirLeft4000()));
                        row.put("6KHZ", getString(ele.getAirLeft6000()));
                        row.put("8KHZ", getString(ele.getAirLeft8000()));
                        row.put("500HZC", getCorrectValue(ele.getAirLeft500(), sexPre + "500", dctPro, age));
                        row.put("1KHZC", getCorrectValue(ele.getAirLeft1000(), sexPre + "1k", dctPro, age));
                        row.put("2KHZC", getCorrectValue(ele.getAirLeft2000(), sexPre + "2k", dctPro, age));
                        row.put("3KHZC", getCorrectValue(ele.getAirLeft3000(), sexPre + "3k", dctPro, age));
                        row.put("4KHZC", getCorrectValue(ele.getAirLeft4000(), sexPre + "4k", dctPro, age));
                        row.put("6KHZC", getCorrectValue(ele.getAirLeft6000(), sexPre + "6k", dctPro, age));
                        row.put("8KHZC", getCorrectValue(ele.getAirLeft8000(), sexPre + "8k", dctPro, age));
                        examList.add(row);
                        row = new HashMap<String, String>();
                        row.put("series", "气导右耳");
                        row.put("500HZ", getString(ele.getAirRight500()));
                        row.put("1KHZ", getString(ele.getAirRight1000()));
                        row.put("2KHZ", getString(ele.getAirRight2000()));
                        row.put("3KHZ", getString(ele.getAirRight3000()));
                        row.put("4KHZ", getString(ele.getAirRight4000()));
                        row.put("6KHZ", getString(ele.getAirRight6000()));
                        row.put("500HZC", getCorrectValue(ele.getAirRight500(), sexPre + "500", dctPro, age));
                        row.put("1KHZC", getCorrectValue(ele.getAirRight1000(), sexPre + "1k", dctPro, age));
                        row.put("2KHZC", getCorrectValue(ele.getAirRight2000(), sexPre + "2k", dctPro, age));
                        row.put("3KHZC", getCorrectValue(ele.getAirRight3000(), sexPre + "3k", dctPro, age));
                        row.put("4KHZC", getCorrectValue(ele.getAirRight4000(), sexPre + "4k", dctPro, age));
                        row.put("6KHZC", getCorrectValue(ele.getAirRight6000(), sexPre + "6k", dctPro, age));
                        row.put("8KHZC", getCorrectValue(ele.getAirRight8000(), sexPre + "8k", dctPro, age));
                        examList.add(row);
                        if (ele.getBoneLeft500() != null) {
                            row = new HashMap<String, String>();
                            row.put("series", "骨导左耳");
                            row.put("500HZ", getString(ele.getBoneLeft500()));
                            row.put("1KHZ", getString(ele.getBoneLeft1000()));
                            row.put("2KHZ", getString(ele.getBoneLeft2000()));
                            row.put("3KHZ", getString(ele.getBoneLeft3000()));
                            row.put("4KHZ", getString(ele.getBoneLeft4000()));
                            row.put("6KHZ", getString(ele.getBoneLeft6000()));
                            row.put("8KHZ", getString(ele.getBoneLeft8000()));
                            row.put("500HZC", "");
                            row.put("1KHZC", "");
                            row.put("2KHZC", "");
                            row.put("3KHZC", "");
                            row.put("4KHZC", "");
                            row.put("6KHZC", "");
                            row.put("8KHZC", "");
                            examList.add(row);

                            row = new HashMap<String, String>();
                            row.put("series", "骨导右耳");
                            row.put("500HZ", getString(ele.getBoneRight500()));
                            row.put("1KHZ", getString(ele.getBoneRight1000()));
                            row.put("2KHZ", getString(ele.getBoneRight2000()));
                            row.put("3KHZ", getString(ele.getBoneRight3000()));
                            row.put("4KHZ", getString(ele.getBoneRight4000()));
                            row.put("6KHZ", getString(ele.getBoneRight6000()));
                            row.put("8KHZ", getString(ele.getBoneRight8000()));
                            row.put("500HZC", "");
                            row.put("1KHZC", "");
                            row.put("2KHZC", "");
                            row.put("3KHZC", "");
                            row.put("4KHZC", "");
                            row.put("6KHZC", "");
                            row.put("8KHZC", "");
                            examList.add(row);
                        }
                    }

                    part1.put("EXAMLIST", examList);
                    //电测听图片移动到科室内
                    if (ele.getAirImgPath() != null && ele.getBoneImgPath() != null) {
                        String airPath = prefix + ele.getAirImgPath();
                        String bonePath = prefix + ele.getBoneImgPath();
                        part1.put("image", airPath);//左耳
                        part1.put("image2", bonePath);//右耳
                        part1.put("image25", local+"file/wordmodel/ks-i/legend.jpg");//图例
                    }
                    //电测听科室生成图像修改 ，气导骨导放在一起
                    //part1.put("boneFlag", ele.getBoneImgPath() != null);// 是否有骨导
                    itemMapList.add(part1);
                    depList.add(part1);
                }
                dto.setItem(itemMapList);

                /** 小结 */
                Map<String, Object> part2 = new HashMap<String, Object>();
                part2.put("timeName", StringUtils.equals(loadProperties.name, "weifang")? 1:0);//潍坊单独用检查日期，其他分中心用体检时间
                part2.put("part", 2);
                part2.put("sjbggs", sjbggs);
                part2.put("reviewer", reviewerDept.contains(depId)? 1 : examinerDept.contains(depId)? 2 : 0);
                SectionResultMain section = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", dept.getDeptNo()));
                if (section != null) {
                    if (dh == 1) {
                        part2.put("P_CONCLUSIONS",
                                section.getZyConclusions() == null ? ""
                                        : section.getZyConclusions());
                    } else {
                        part2.put("P_CONCLUSIONS",
                                section.getConclusions() == null ? ""
                                        : section.getConclusions());
                    }
                    part2.put("audit_time",
                            auditFormat.format(section.getAuditTime()));
                    setAudit(part2, section.getAuditName(), prefix,dept.getDeptNo(),dh);
                    setRummager(part2, section.getRummagerName(), prefix,dept.getDeptNo(),dh);
                    setConclusion3(part2, part2.get("P_CONCLUSIONS").toString());
                }
                dto.setSummary(part2);
                depList.add(part2);
//                // TODO: 2024/7/16 电测听图片放在科室后面，不放在最后了
//                if (dept.getAddPicBefore() != null && "1".equals(dept.getAddPicBefore())) {
//                    //每次必生成两张图片
//                    if (ele.getAirImgPath() != null && ele.getBoneImgPath() != null) {
//                        String airPath = prefix + ele.getAirImgPath();
//                        String bonePath = prefix + ele.getBoneImgPath();
//                        Map<String, Object> part3 = new HashMap<String, Object>();
//                        part3.put("part", 3);
//                        part3.put("image", airPath);//左耳
//                        part3.put("image2", bonePath);//右耳
//                        part3.put("image25", local+"file/wordmodel/ks-i/legend.jpg");//图例
//                        part3.put("picSize", 25);
//                        part3.put("isDct", 1);
//                        depList.add(part3);
//                        picList.add(part3);
//                    }
//                }
            } else {
                continue;
            }
            dto.setPicture(picList);
            iReportDtoList.add(dto);
        }
        vo.setKsList(iReportDtoList);

        /**职业必查拒检项目*/
        if (dh.intValue() == 1) {
            Collection<Map<String, String>> examList = getJjData(patientcode);
            if (examList.size() > 0) {
                Map<String, Object> part0 = new HashMap<String, Object>();
                part0.put("part", -2);
                part0.put("SYMBOL_LIST", examList);
                vo.setJjxm(part0);
            }
        }


        /** 尾部图片 */
        //ADD_PIC_BEFORE为null的不会显示出来
        List<Map> endPic = new ArrayList<>();
        List<SysDept> endPicDeps = findAllPicDep(patientcode, dh);
        for (SysDept dep : endPicDeps) {
            log.info(dep.getDeptName());
            boolean showPic = true;
            if (CT.equals(dep.getDeptNo())) {
                String ctconf = Constants.CT_IMG;
                if (ctconf != null && "1".equals(ctconf)) {
                    showPic = false;
                }
            }
            if (!showPic) continue;

            int sjbggs = dep.getSjbggs() == null ? 0 : Integer.valueOf(dep.getSjbggs());

            if (sjbggs == 1) {
                // 外送
                List<OutsidePictrue> pics = getOutSidePicture(patientcode, dh);
                if (CollectionUtils.isEmpty(pics)) {
                    continue;
                }
                for (OutsidePictrue pic : pics) {
                    String imagePath = pic.getPictruePosition();
                    Map<String, Object> part3 = new HashMap<String, Object>();
                    part3.put("part", 3);
                    part3.put("image", prefix + imagePath);
                    part3.put("picSize", 1);
                    depList.add(part3);

                    reportPicList.add(imagePath);
                    endPic.add(part3);
                }
            } else if (sjbggs == 9) {
                // 电测听图片跟在科室后面，所以这个不体现了
//                ElectroAudiometer ele = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>()
//                        .eq("patientCode", patientcode));
//                if (ele == null) {
//                    continue;
//                }
//                if (ele.getAirImgPath() == null && ele.getBoneImgPath() == null) {
//                    continue;
//                }
//
//                /**显示图片*/
//                if (ele.getAirImgPath() != null && ele.getBoneImgPath() != null) {
//                    String airPath = prefix + ele.getAirImgPath();
//                    String bonePath = prefix + ele.getBoneImgPath();
//                    Map<String, Object> part3 = new HashMap<String, Object>();
//                    part3.put("part", 3);
//                    part3.put("image", bonePath);
//                    part3.put("image2", airPath);
//                    part3.put("picSize", 2);
//                    part3.put("isDct", 1);
//
//                    endPic.add(part3);
//                    reportPicList.add(bonePath);
//                    reportPicList.add(airPath);
//                    depList.add(part3);
//                }

            } else {
                // 不需要尾部图片，在这跳过
                //dr、彩超、放射科(CT)、磁共振
                if ("24".equals(dep.getDeptNo()) || "143".equals(dep.getDeptNo())
                        || "173".equals(dep.getDeptNo()) || "402848e3625a920201625ff99a3404a5".equals(dep.getDeptNo())) {
                    continue;
                }
                // 图像科室
                List<Attachment> atts = attachmentMapper.selectList(new QueryWrapper<Attachment>().orderByAsc("fee_item_id", "file_path")
                        .eq("patientcode", patientcode).eq("dep_id", dep.getDeptNo()));
                if (pacs) {
                    atts = getZyAtts(atts, jhysStr, medicalType);
                }
                int size = atts.size();
                if (!CCKS.equals(dep.getDeptNo()) && maxImageNum != null) {
                    size = maxImageNum > size ? size : maxImageNum;
                }

                Map<String, Object> part3 = null;
                for (int a = 0; a < size; a++) {
                    Attachment att = atts.get(a);

                    //一行显示几张图片,默认一张
                    part3 = new HashMap<String, Object>();// 一页显示一张图片
                    part3.put("part", 3);
                    part3.put("image", prefix + att.getFilePath());
                    part3.put("picSize", 1);
                    reportPicList.add(att.getFilePath());
                    depList.add(part3);
                    endPic.add(part3);

                }

            }
        }
        vo.setEndPicture(endPic);

        //第三方系统报告,只有健康报告有
        if (dh == 0) {
            List<String> threeReportList = new ArrayList<>();
            //艾迪康自动上传的图片
            List<Attachment> attachments = attachmentMapper.selectList(new LambdaQueryWrapper<Attachment>()
                    .eq(Attachment::getPatientcode, patientcode)
                    .eq(Attachment::getType, 1)
                    .eq(Attachment::getFileType, "艾迪康报告")
                    .eq(Attachment::getInReport, 1)
            );
            if (ObjectUtils.isNotEmpty(attachments)) {
                for (Attachment attachment : attachments) {
                    threeReportList.add(attachment.getFilePath());

                    Map<String, Object> part3 = new HashMap<String, Object>();// 一页显示一张图片
                    part3.put("part", 3);
                    String image = prefix + attachment.getFilePath();
                    //校验图片是否存在
                    if (!isImageUrlExists(image)){
                        throw new ServiceException(image + "艾迪康报告图片不存在!");
                    }
                    part3.put("image", image);
                    part3.put("picSize", 1);
                    depList.add(part3);

                }
            }

            //第三方报告
            List<Attachment> threeReport = attachmentMapper.selectList(new QueryWrapper<Attachment>()
                    .eq("patientcode", patientcode)
                    .eq("type", 1)
                    .eq("file_type", "第三方报告图片")
                    .eq("in_report", 1)
                    .orderByAsc("createdate", "CAST(file_sort AS unsigned)")//字符串转成数组排序
            );

            if (ObjectUtils.isNotEmpty(threeReport)) {
                for (Attachment attachment : threeReport) {
                    threeReportList.add(attachment.getFilePath());

                    Map<String, Object> part3 = new HashMap<String, Object>();// 一页显示一张图片
                    part3.put("part", 3);
                    String image = prefix + attachment.getFilePath();
                    //校验图片是否存在
                    if (!isImageUrlExists(image)){
                        throw new ServiceException(image + "第三方报告图片不存在!");
                    }
                    part3.put("image", image);
                    part3.put("picSize", 1);
                    depList.add(part3);

                }
            }
            vo.setThreeReport(threeReportList);
            reportPicList.addAll(threeReportList);
        }

        ksList.addAll(depList);

        // 报告预览不支持中文文件名
        String modelPath = local + "file/wordmodel/ks-i/ks_main6.jasper";
        String savePdfPath = local + patientcode + "/" + "report_"
                + patientcode + "_" + nameFormat.format(new Date()) + ".pdf";

        JasperPrint jp = null;
        try {
            jp = ru.getJasperPrint(modelPath, parameters,
                    new JRMapCollectionDataSource(ksList));
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        jasperPrintList.add(jp);

        SysBranch branch = sysBranchMapper.getDefaultBranch();
        String content = reportConfigService.getBranchConfig(branch.getBranchId());
        if (StringUtils.isEmpty(content)) throw new ServiceException("请先设置报告配置！");
        ReportConfigVo reportConfigVo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;


        parameters = null;
        /** 职业尾页 */
        if (dh == 1) {
            pageNum = pageNum + jp.getPages().size();
            parameters = getDiseaseEndData(patientcode, prefix);
            parameters.put("pageNum", pageNum);
            parameters.put("patientcode", patientcode);
            parameters.put("patientname", patient.getPatientname());
            parameters.put("end_title", "个体报告");
            parameters.put("end_org", branch.getFzx());


            //职业盖章图片
            if (ObjectUtils.isNotEmpty(reportConfigVo) && StringUtils.isNotEmpty(reportConfigVo.getProfessionalSeal())) {
                parameters.put("professionalSeal", prefix + reportConfigVo.getProfessionalSeal());
            }
            try {
                jasperPrintList.add(ru.getJasperPrint(local + DISEASE_END_PATH_1,
                        parameters, new JREmptyDataSource()));
            } catch (JRException e) {
                throw new RuntimeException(e);
            }

            if (!isfc) {
                //默认配置
                if (ObjectUtils.isNotEmpty(reportConfigVo)) {
                    parameters.put("org_address", reportConfigVo.getAddress());
                    parameters.put("org_phone", reportConfigVo.getPhone());
                    parameters.put("org_zipcode", reportConfigVo.getPostalCode());
                    //资质证书
                    parameters.put("org_zzzs", reportConfigVo.getCertificateNo());
                    parameters.put("org_fax", reportConfigVo.getFax());
                    parameters.put("org_email", reportConfigVo.getEmail());
                    //所属组织名称
                    parameters.put("org_orgName", reportConfigVo.getProducer());
                    //logo和小程序码
                    // TODO: 2024/7/10 锦都只留logo
                    if (StringUtils.equals(loadProperties.name, "jindu")){
                        parameters.put("jinduLogo", prefix + reportConfigVo.getLogo());
                    }else {
                        parameters.put("end_logo", prefix + reportConfigVo.getLogo());
                        parameters.put("end_qrcode", prefix + reportConfigVo.getQRCode());
                        parameters.put("logo", prefix + reportConfigVo.getLogo());
                        parameters.put("qrcode", prefix + reportConfigVo.getQRCode());
                        parameters.put("miniProgramCode", prefix + reportConfigVo.getMiniProgramCode());
                    }
                }


                try {
                    jasperPrintList.add(ru.getJasperPrint(
                            local + DISEASE_END_PATH_2, parameters,
                            new JREmptyDataSource()));
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            //健康尾页
            if (!"1".equals(isJy)) {
                String idPatientClass = patient.getIdPatientclass();
                // TODO: 2024/7/10 锦都的贵宾报告尾页也不要了
                if ("3".equals(idPatientClass)) {
                    if (dh == 0 && !StringUtils.equals(loadProperties.name, "jindu")) {
                        try {
                            Map stringObjectMap = (Map<String, Object>) head.get("enddata");
                            jasperPrintList.add(ru.getJasperPrint(
                                    local + "file/wordmodel/ks-i/healthEnd_vvip.jasper", (Map<String, Object>) head.get("enddata"),
                                    new JREmptyDataSource()));
                            //删除多余数据
                            stringObjectMap = deleteExcessData(stringObjectMap);
                        } catch (JRException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (isHealth) {

                } else {
                    if ("1".equals(ReportConstants.hide_health_common_end_page)) {
                        //隐藏尾页
                    } else {
                        //默认配置
                        parameters = new HashMap<String, Object>();
                        // TODO: 2024/7/10 锦都去除沃德logo
                        if (StringUtils.equals(loadProperties.name, "jindu")) {
                            //锦都健康报告需要地址等
                            parameters.put("org_address", reportConfigVo.getAddress());
                            parameters.put("org_phone", reportConfigVo.getPhone());
                            parameters.put("org_email", reportConfigVo.getEmail());
                            parameters.put("org_zipcode", reportConfigVo.getPostalCode());
                            parameters.put("org_fax", reportConfigVo.getFax());
                            parameters.put("jinduLogo", prefix + reportConfigVo.getLogo());
                        } else {
                            parameters.put("logo", prefix + reportConfigVo.getLogo());
                            parameters.put("qrcode", prefix + reportConfigVo.getQRCode());
                            parameters.put("miniProgramCode", prefix + reportConfigVo.getMiniProgramCode());
                        }



                        if (dh == 0) {
                            try {
                                jasperPrintList.add(ru.getJasperPrint(
                                        local + HEALTH_END_PATH, parameters,
                                        new JREmptyDataSource()));
                            } catch (JRException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
        //删除多余数据
        if (ObjectUtils.isNotEmpty(parameters)) {
            parameters = deleteExcessData(parameters);
        }

        vo.setEnd(parameters);
        createReportVo.setIReportVo(vo);


        //只有健康的才生成隐私报告
        Integer integer = reportPrintingMapper.containsPrivate(patientcode);
        Boolean generatePrivacyReport = StringUtils.isNotEmpty(reportConfigVo.getGeneratePrivacyReport()) ? "1".equals(reportConfigVo.getGeneratePrivacyReport()) : false;
        if (dh == 0 && integer > 0 && type == 0 && generatePrivacyReport) {
            //查询隐私报告数据
            PrivateReportVo ysbg = privateReportService.create(patientcode);
            vo.setYsbg(ysbg);


            String picdir = local + "file/temp/pdf/" + auditFormat.format(new Date()) + "/";

            File tempDir = new File(picdir);
            if (!tempDir.exists()) {
                System.out.println(picdir + "文件夹创建中");
                tempDir.mkdirs();
            }
            String tempPath = picdir + UUID.randomUUID() + ".pdf";
            System.out.println("临时报告生成地址:" + tempPath);
            try {
                ru.createPdfReport(tempPath, jasperPrintList);// 导出pdf文件
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File file = new File(savePdfPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //生成隐私报告pdf
            String path = null;
            try {
                path = privateReportService.createPdf(patientcode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<HashMap<String, String>> beforePath = new ArrayList<>();
            HashMap<String, String> r1 = new HashMap<String, String>();
            r1.put("odd", "1");
            r1.put("path", tempPath);

            HashMap<String, String> r2 = new HashMap<String, String>();
            r2.put("odd", "1");
            r2.put("path", path);

            beforePath.add(r1);
            beforePath.add(r2);

            //合并pdf
            try {
                File mergePdf = new File(savePdfPath);
                if (!mergePdf.getParentFile().exists()) {
                    mergePdf.getParentFile().mkdirs();
                }
                WordConvertPDF.mergePdfFilesForReport(beforePath, savePdfPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //删除临时报告
            File privateReport = new File(path);
            privateReport.delete();

            //没有隐私报告的正式报告
            File tempReport = new File(tempPath);
            tempReport.delete();
        } else {
            //生成pdf
            try {
                ru.createPdfReport(savePdfPath, jasperPrintList);// 导出pdf文件
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        File file = new File(savePdfPath);

        String originalFilename = file.getName();  // 获取文件原始名称
        String contentType = "application/pdf";  // 设置文件类型，根据实际情况进行设置
        // 将File对象转换为MultipartFile
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(originalFilename, file.getName(), contentType, FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        String extName = FileUtil.extName(file.getName());
        Attachment att = new Attachment();
        att.setType(3);
        att.setPatientcode(patientcode);
        att.setFileType("pdf报告");
        try {
            attachmentService.uploadFile(multipartFile, att, baseDir, extName, null, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //删除正式报告 临时文件
        file.delete();


        //放入数据
        reportPicList.add(att.getFilePath());
        createReportVo.setReportPicList(reportPicList);
        createReportVo.setReportPath(att.getFilePath());

        return createReportVo;
    }

    /**
     * 箭头去重
     * @param string
     * @return
     */
    private static String arrowDeduplication(String text) {
        text = text.replaceAll("↑+", "↑"); // 将多个上箭头替换为一个上箭头
        text = text.replaceAll("↓+", "↓"); // 将多个下箭头替换为一个下箭头
        return text;
    }




    /**
     * 职业报告尾页数据
     *
     * @param patientcode
     * @return
     */
    public Map<String, Object> getDiseaseEndData(String patientcode, String prefix) {
        String path = FileTypePath.REAL_PATH;
        Map<String, Object> params = new HashMap<String, Object>();
        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        List<String> signPicList = new ArrayList<>();
        int o = 1;
        if (patient != null) {
            params.put(
                    "end_patientname",
                    patient.getPatientname()
                            + " "
                            + (patient.getIdSex() == 0 ? "先生" :
                            patient.getIdSex() == 1 ? "女士" : ""));
            String zyzjys = patient.getPatientcodehiden();
            SysUser zjys = sysUserMapper.getUserByNo(zyzjys);
            if (zjys != null && zjys.getSignPic() != null) {
                String signPic = zjys.getSignPic();
                signPicList.add(signPic);
                params.put("signPic_" + (o++), prefix + signPic);
            }
            //职业总检医生签名
            params.put("doctorSignPic", signPicList);
        } else {
            params.put("end_patientname", "");
        }
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientcode).eq("disease_health", 1).eq("is_delete", 0));
        if (section != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");

            String end_offer = ObjectUtils.isEmpty(section.getSummaryId()) ?
                    section.getOffer() :
                    section.getReportConclusions();
            //阳性结果
            String posistive = section.getPosistive();
            if (StringUtils.equals(loadProperties.name, "jiaonan")) posistive = removeDept(posistive);
            params.put("positive", posistive);

            //结论
            String[] parts = end_offer.split("\n", 2); // 用换行符分割，分割为最多两部分
            if (ObjectUtils.isEmpty(parts[1])) {
                throw new ServiceException("请确认总检处最终结论和建议是否正确！");
            }
            params.put("end_conclusion", parts[0]);
            //建议
            String end_proposal = parts[1];
            if (StringUtils.equals(loadProperties.name, "jiaonan") || StringUtils.equals(loadProperties.name, "pingdu")) end_proposal = removeProposal(end_proposal);
            params.put("end_proposal", end_proposal);//处理意见
            params.put("end_date", ObjectUtils.isNotEmpty(section.getTotalTime()) ? sdf.format(section.getTotalTime()) : "");
            //健康建议
//            params.put("end_jkoffer",
//                    section.getJkoffer());
            //结论
//            params.put("end_summary", section.getVerdict());

            //总检-医生 关联表
            List<TotalDoctor> tds = totalDoctorMapper.selectList(new QueryWrapper<TotalDoctor>()
                    .eq("type", 1).eq("total_id", section.getId()));

            for (TotalDoctor td : tds) {
                SysUser us = sysUserMapper.getUserByNo(td.getUserId());
                if (us != null && us.getSignPic() != null) {
                    String signPic = us.getSignPic();
                    if (signPicList.contains(signPic)) {
                        continue;
                    } else {
                        signPicList.add(signPic);
                        params.put("signPic_" + (o++), prefix + signPic);
                    }
                }
            }
            params.put("signPic", signPicList);
        } else {
            params.put("end_offer", "");
            params.put("end_jkoffer", "");
            params.put("end_summary", "");
            params.put("end_date", "");
            params.put("positive", "");
        }
        return params;
    }

    /**
     * 删除建议建议
     * @param endProposal
     * @return
     */
    private static String removeProposal(String data) {
        // 去掉括号及其中的内容
        String cleanedData = data.replaceAll("体检类别（[^）]+）", "");
        cleanedData = cleanedData.replace("可以从事当前作业", "可以从事现岗位工作");
        return cleanedData;
    }

    /**
     * 去除阳性结果的科室
     *
     * @param data
     * @return
     */
    private static String removeDept(String data) {
        // 每条记录以换行分隔
        String[] records = data.split("\n\r");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < records.length; i++) {
            // 去掉科室名，即去掉数字+顿号后的第一个部分
            String cleaned = records[i].replaceAll("^\\d、[^\\n]+\\n", "");
            // 重新加上序号
            result.append((i + 1)).append("、").append(cleaned);
            if (i != records.length - 1) {
                result.append("\n\n"); // 保留原来的记录间隔
            }
        }
        return result.toString();
    }






    /**
     * 外送图片
     *
     * @param patientcode
     * @param dh
     * @return
     */
    public List<OutsidePictrue> getOutSidePicture(String patientcode, int dh) {
        List<OutsidePictrue> ops = outsidePictrueMapper.getOutSidePicture(patientcode);
        if (CollectionUtils.isEmpty(ops)) {
            return null;
        }
        if (dh == 1) {
            //职业
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            String harmIds = patient.getJhys();
            String medicalType = patient.getMedicaltype();
            List<OutsidePictrue> ops2 = new ArrayList<OutsidePictrue>();
            for (OutsidePictrue op : ops) {
                String chargeId = op.getChargeId();
                if (chargeId != null) {
                    //多个收费项目id
                    String[] chargeIds = chargeId.split(",");
                    for (String id : chargeIds) {
                        List<String> list = iPersonalReportMapper.getOutSidePicture(id, harmIds.split(","), medicalType);
                        if (list.size() > 0) {
                            ops2.add(op);
                        }
                    }
                }
            }
            return ops2;
        } else {
            return ops;
        }
    }


    /**
     * 图片科室,一页显示几张图片
     *
     * @param depSrm
     * @return
     */
    private String picSizeWidth(String depSrm) {
        String i = "1";
        //彩超
        if ("CC".equals(depSrm)) {
            i = "3";
        }
        //电测听
        if ("CC".equals(depSrm)) {
            i = "2";
        }
        return i;

    }


    public String getNameByUname(String username) {
        if (StringUtils.isEmpty(username)) {
            return "";
        }
        SysUser user = sysUserMapper.selectUserByUserName(username);
        return user == null ? "" : user.getUserName();
    }


    public void setAudit(Map<String, Object> part, String username, String prefix,String ksId,Integer dh) {
        if (StringUtils.isEmpty(username)) {
            return;
        }
        SysUser user = null;
        String id = reportDefaultDoctorService.getinfoByKsIdAndType(ksId,dh,1);
        if (StringUtils.isNotEmpty(id)){
            user = sysUserMapper.getUserByNo(id);
        }else {
            user = sysUserMapper.getUserByUserName(username);
        }
        if (user == null) {
            return;
        }
        String signPic = user.getSignPic();
        if (StringUtils.isNotBlank(signPic)) {
            String auditPath = prefix + signPic;
            //校验图片是否存在
            if (!isImageUrlExists(auditPath)){
                throw new ServiceException(username + " 签名图片不存在!");
            }
            part.put("audit_path", auditPath);
        } else {
            part.put("audit_name", user.getUserName());
        }
    }


    public static boolean isImageUrlExists(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)){
            return Boolean.FALSE;
        }
        try {
            // 创建一个 URL 对象
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 HEAD，以减少数据传输
            connection.setRequestMethod("HEAD");
            // 设置连接超时和读取超时
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            // 发送请求
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();
            // 检查响应码是否为 200 (HTTP_OK) 或 206 (HTTP_PARTIAL)
            return (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL);
        } catch (IOException e) {
            // 处理异常，返回 false 表示图片不存在
            return false;
        }
    }



    /**
     * 综合职业报告
     *
     * @param atts
     * @param jhys
     * @param medicaltype
     * @return
     */
    public List<Attachment> getZyAtts(List<Attachment> atts, String jhys, String medicaltype) {
        List<Attachment> result = new ArrayList<Attachment>();
        Set<String> zyFeeItemIds = new HashSet<String>();
        for (Attachment att : atts) {
            String feeItemId = att.getFeeItemId();
            if (feeItemId == null) {//非pacs科室
                result.add(att);
                continue;
            }
            if (zyFeeItemIds.contains(feeItemId)) {
                result.add(att);
                continue;
            }
            //加上distinct，否则可能发生结果不止一条的错误
            String dto = iPersonalReportMapper.getZyAtts(feeItemId, jhys.split(","), medicaltype);
            if (StringUtils.isNotEmpty(dto)) {
                result.add(att);
                zyFeeItemIds.add(feeItemId);
            }
        }
        return result;
    }


    public void setRummager(Map<String, Object> part, String username, String prefix,String ksId ,int dh) {
        if (StringUtils.isEmpty(username)) {
            return;
        }
        SysUser user = null;
        String id = reportDefaultDoctorService.getinfoByKsIdAndType(ksId,dh,0);
        if (StringUtils.isNotEmpty(id)){
            user = sysUserMapper.getUserByNo(id);
        }else {
            user = sysUserMapper.getUserByUserName(username);
        }
        if (ObjectUtils.isEmpty(user)) {
            return;
        }
        String signPic = user.getSignPic();
        if (StringUtils.isNotBlank(signPic)) {
            String auditPath = prefix + signPic;
            //校验图片是否存在
            if (!isImageUrlExists(auditPath)){
                throw new ServiceException(username + " 签名图片不存在!");
            }
            part.put("rummager_path", auditPath);
        } else {
            part.put("rummager_name", user.getUserName());
        }

    }


    /**
     * 根据公式计算 校正值
     *
     * @param original
     * @param key
     * @param dctPro
     * @param age
     * @return
     */
    public String getCorrectValue(Double original, String key, Map<String, String> dctPro, int age) {
        Double correct = null;
        if (original != null) {
            String value = dctPro.get(key);
            String[] valueArr = value.split(",");
            for (String itemStr : valueArr) {
                String[] itemArr = itemStr.split(":");
                String[] ageArr = itemArr[0].split("-");
                if (age >= Integer.parseInt(ageArr[0].trim())
                        && age <= Integer.parseInt(ageArr[1].trim())) {
                    correct = original - Double.parseDouble(itemArr[1]);
                }
            }
        }
        return correct == null ? null : String.valueOf(correct.intValue());

    }


    static String getString(Double obj) {
        return obj == null ? "" : String.valueOf(obj.intValue());
    }


    public String replacePath(String path, String target, String replace) {
        return path.replace(target, replace);
    }

    /**
     * 获取一般检查数据
     *
     * @param dh
     * @param patientcode
     * @param dept
     * @param isgwct
     * @return
     * @throws Exception
     */
    public Collection<Map<String, String>> getFairCheckData(Integer dh, String patientcode, SysDept dept, boolean isgwct) {
        Collection<Map<String, String>> col = new Vector<Map<String, String>>();
        //KS一般检查
        Tjreg general = tjregMapper.selectOne(new QueryWrapper<Tjreg>().eq("patientcode", patientcode));
        if (general != null) {
            if (dh == 0) {
                if (isgwct) {
                    if ("402848e37337828401733b9da758031c".equals(dept.getDeptNo())) {
                        if (!StringUtils.isEmpty(general.getSg())) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "身高（cm）");
                            hs.put("SIGN", general.getSg().replaceAll(" ", ""));
                            col.add(hs);
                        }
                        if (!StringUtils.isEmpty(general.getTz())) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "体重（kg）");
                            hs.put("SIGN", general.getTz().replaceAll(" ", ""));
                            col.add(hs);
                        }
                        if (general.getBmi() != null) {
                            if (general.getBmims() != null) {
                                Map<String, String> hs = new HashMap<String, String>();
                                hs.put("ITEM", "体重指数");
                                hs.put("SIGN", general.getBmi() + ","
                                        + general.getBmims().replaceAll("<[^>]*>", ""));
                                col.add(hs);
                            }
                        }
                        if (general.getWaist() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "腰围(cm)");
                            hs.put("SIGN",
                                    String.valueOf(general.getWaist())
                                            .replaceAll(" ", ""));
                            col.add(hs);
                        }
                    } else {
                        if (general.getSsy() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "收缩压（mmHg）");
                            hs.put("SIGN",
                                    String.valueOf(general.getSsy().intValue())
                                            .replaceAll(" ", ""));
                            col.add(hs);
                        }
                        if (general.getSzy() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "舒张压（mmHg）");
                            hs.put("SIGN",
                                    String.valueOf(general.getSzy().intValue())
                                            .replaceAll(" ", ""));
                            col.add(hs);
                        }
                        if (!StringUtils.isEmpty(general.getXy())) {
                            if (general.getXyms() != null) {
                                Map<String, String> hs = new HashMap<String, String>();
                                hs.put("ITEM", "血压结论");
                                hs.put("SIGN",
                                        general.getXyms().replaceAll("<[^>]*>", ""));
                                col.add(hs);
                            }
                        }
                        if (general.getMb() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "脉搏（次/分钟）");
                            hs.put("SIGN",
                                    String.valueOf(general.getMb().intValue()).replaceAll(" ", ""));
                            col.add(hs);
                        }
                    }
                } else {
                    if (general.getSsy() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "收缩压（mmHg）");
                        hs.put("SIGN",
                                String.valueOf(general.getSsy().intValue())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (general.getSzy() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "舒张压（mmHg）");
                        hs.put("SIGN",
                                String.valueOf(general.getSzy().intValue())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (!StringUtils.isEmpty(general.getXy())) {
                        if (general.getXyms() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "血压结论");
                            hs.put("SIGN",
                                    general.getXyms().replaceAll("<[^>]*>", ""));
                            col.add(hs);
                        }
                    }
                    if (general.getMb() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "脉搏（次/分钟）");
                        hs.put("SIGN",
                                String.valueOf(general.getMb().intValue()).replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (!StringUtils.isEmpty(general.getSg())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "身高（cm）");
                        hs.put("SIGN", general.getSg().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (!StringUtils.isEmpty(general.getTz())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "体重（kg）");
                        hs.put("SIGN", general.getTz().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (general.getBmi() != null) {
                        if (general.getBmims() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "体重指数");
                            hs.put("SIGN", general.getBmi() + ","
                                    + general.getBmims().replaceAll("<[^>]*>", ""));
                            col.add(hs);
                        }
                    }
                    if (general.getRespiratoryRate() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "呼吸频率（次/分钟）");
                        hs.put("SIGN", String.valueOf(general.getRespiratoryRate())
                                .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (!StringUtils.isEmpty(general.getCommonState())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "营养状况");
                        hs.put("SIGN", general.getCommonState().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (general.getTemperature() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "体温测量(℃)");
                        hs.put("SIGN", String.valueOf(general.getTemperature())
                                .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (general.getWaist() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "腰围(cm)");
                        hs.put("SIGN",
                                String.valueOf(general.getWaist())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (general.getBust() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "胸围(cm)");
                        hs.put("SIGN",
                                String.valueOf(general.getBust())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                }
            } else {
                //KS科室描述
                List<Describe> dess = describeMapper.selectList(new QueryWrapper<Describe>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", dept.getDeptNo())
                        .eq("tjlx", 1));
                if (dess.size() > 0) {
                    List<String> zyIds = new ArrayList<String>();
                    for (Describe des : dess) {
                        zyIds.add(des.getItemId());
                    }
                    if (zyIds.contains("434") && general.getSsy() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "收缩压（mmHg）");
                        hs.put("SIGN", String.valueOf(general.getSsy().intValue())
                                .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("435") && general.getSzy() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "舒张压（mmHg）");
                        hs.put("SIGN", String.valueOf(general.getSzy().intValue())
                                .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("436")
                            && !StringUtils.isEmpty(general.getXy())) {
                        if (general.getXyms() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "血压结论");
                            hs.put("SIGN",
                                    general.getXyms().replaceAll("<[^>]*>", ""));
                            col.add(hs);
                        }
                    }
                    if (zyIds.contains("15") && general.getMb() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "脉搏（次/分钟）");
                        hs.put("SIGN", String.valueOf(general.getMb().intValue())
                                .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("12")
                            && !StringUtils.isEmpty(general.getSg())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "身高（cm）");
                        hs.put("SIGN", general.getSg().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("13")
                            && !StringUtils.isEmpty(general.getTz())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "体重（kg）");
                        hs.put("SIGN", general.getTz().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("14") && general.getBmi() != null) {
                        if (general.getBmims() != null) {
                            Map<String, String> hs = new HashMap<String, String>();
                            hs.put("ITEM", "体重指数");
                            hs.put("SIGN",
                                    general.getBmi()
                                            + ","
                                            + general.getBmims().replaceAll(
                                            "<[^>]*>", ""));
                            col.add(hs);
                        }
                    }
                    if (zyIds.contains("10000")
                            && general.getRespiratoryRate() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "呼吸频率（次/分钟）");
                        hs.put("SIGN",
                                String.valueOf(general.getRespiratoryRate())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("1163")
                            && !StringUtils.isEmpty(general.getCommonState())) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "营养状况");
                        hs.put("SIGN",
                                general.getCommonState().replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("402848e3700dfffe01704675865e0dfe")
                            && general.getTemperature() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "体温测量（℃）");
                        hs.put("SIGN",
                                String.valueOf(general.getTemperature())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                    if (zyIds.contains("ff8080818214499d0182c862ea5d1156")
                            && general.getBust() != null) {
                        Map<String, String> hs = new HashMap<String, String>();
                        hs.put("ITEM", "胸围");
                        hs.put("SIGN",
                                String.valueOf(general.getBust())
                                        .replaceAll(" ", ""));
                        col.add(hs);
                    }
                }
            }
        }
        return col;
    }


    public static void main(String[] args) {
        String data = "↓↓↓";
        String string = arrowDeduplication(data);
        System.out.println(string);

    }




    public static void setConclusion3(Map<String, Object> map, String conclusion) {
        /*
         * 小结一行可容纳33个文字或标点符号。如果存在 1.3x1.2cm 这种字符，计算出现误差，可能出现吃字。故当超过33字时，每33字一换行
         * 而且jasper似乎会将  1.3x1.2cm   或   ，，，，   这样的组合强行放在一行
         *
         * 2025.4.29 通过设置main6的属性以后，可以换行，但是mm这种似乎还是强行放在一起，但是影响不大
         * 但是如果不加这个换行符的话，可能会出现吃字的情况，例如-充盈缺损。 -> 充盈缺 或 充盈缺损
         *
         */
        int sub = 33;
        if (conclusion == null) {
            map.put("P_CONCLUSIONS", "");
            return;
        }

        // 替换换行符
        conclusion = conclusion.replaceAll("\r\n", "\n").replaceAll("\r", "\n");

        // 按行拆分
        String[] strs = conclusion.split("\n");
        Collection<Map<String, String>> col = new Vector<Map<String, String>>();

        for (String str : strs) {
            List<String> wrappedLines = wrapText(str, sub);
            for (String line : wrappedLines) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("conclu", line);
                col.add(m);
            }
        }

        map.put("CONCLUSIONS", col);
    }

    /**
     * 按照字符宽度限制，将字符串分成多行
     * @param text 需要分行的文本
     * @param maxLength 最大字符数（33个字符的长度）
     * @return 分割后的行
     */
    private static List<String> wrapText(String text, int maxLength) {
        List<String> result = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        double currentLength = 0;  // 当前行的字符长度（汉字、中文符号 1，其他字符 0.7）

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            double charLength = isChineseOrSymbol(c) ? 1 : 0.8;  // 判断是否是汉字

            // 如果加上当前字符超过最大长度，则换行
            if (currentLength + charLength > maxLength) {
                result.add(currentLine.toString());
                currentLine.setLength(0);  // 清空当前行
                currentLength = 0;
            }

            // 将字符加入当前行
            currentLine.append(c);
            currentLength += charLength;
        }

        // 最后一行
        if (currentLine.length() > 0) {
            result.add(currentLine.toString());
        }

        return result;
    }

    /**
     * 判断字符是否是汉字或中文符号
     * @param c 字符
     * @return 是否是汉字或中文符号
     */
    private static boolean isChineseOrSymbol(char c) {
        // 判断是否为汉字
        if (c >= 0x4e00 && c <= 0x9fa5) {
            return true;
        }

        // 判断是否为中文标点符号（常见的中文标点符号范围）
        String chinesePunctuation = "，。！？；：、〝〞「」『』【】《》『』";
        return chinesePunctuation.indexOf(c) >= 0;
    }



    public void setDoctor(Map<String, Object> part, String username
            , String pathkey, String namekey, String prefix) {
        SysUser user = null;
        String id = reportDefaultDoctorService.getinfoByKsIdAndType("19",2,"jy_rummager_path".equals(pathkey)? 0 : 1);
        if (StringUtils.isNotEmpty(id)){
            user = sysUserMapper.getUserByNo(id);
        }else {
            user = sysUserMapper.getUserByUserName(username);
        }
        if (user == null) {
            //检验科老是没有签名,如果没有的话就直接取传过来的名字
            part.put(namekey, username);
            return;
        }
        String signPic = user.getSignPic();
        if (StringUtils.isNotEmpty(signPic)) {
            String auditPath = signPic;
            part.put(pathkey, prefix + auditPath);
        } else {
            part.put(namekey, user.getUserName());
        }

    }


    /**
     * @param part
     * @param auditor   配置的审核人用户名
     * @param auditor2  配置的审核人用户名2
     * @param auditName 检查者
     * @param prefix    前缀
     */
    private void setRummagerConfig(Map<String, Object> part, SysUser auditor, SysUser auditor2, String auditName, String prefix) {
        SysUser user = auditName.equals(auditor.getUserName()) ? auditor2 : auditor;
        String signPic = user.getSignPic();
        if (StringUtils.isNotEmpty(signPic)) {
            String auditPath = signPic;
            part.put("rummager_path", prefix + auditPath);
        }
        part.put("rummager_name", user.getUserName());
    }

    /**
     * 生成职业个检头部文件
     *
     * @param patientcode
     * @return
     */
    private HashMap<String, Object> professionHeadPage(String patientcode) throws JRException {
        String local = System.getProperty("user.dir") + "/temp/";
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> parameters = new HashMap<String, Object>();

        Peispatient pei = peispatientMapper.getByPatientCode(patientcode);
        int pageNum = 2;
        /** 第一页 */
        String first_zjz = Constants.FIRST_ZJZ;
        first_zjz = StringUtils.isNotEmpty(first_zjz) ? first_zjz : "鲁卫计职检字\r\n" +
                "(2016)第(096)号";
        parameters.put("first_zjz", first_zjz);
        String first_org = Constants.FIRST_ORG;
        first_org = StringUtils.isNotEmpty(first_org) ? first_org : "沃德国际健管中心";
        parameters.put("first_org", first_org);
        String patientname = pei.getPatientname() != null ? pei
                .getPatientname() : " ";
        parameters.put("orgDepart", pei.getOrgDepart());
        //base64的条形码
        BASE64Encoder encode = new BASE64Encoder();
        String barcode = encode.encode(CreateBarcodeDefault
                .createBarcodeDefault(patientcode));
        parameters.put("barcode", barcode);
        //体检者头像
        String picture = peispatientPhotoService.getPicture(pei);
        if (StringUtils.isNotEmpty(picture)) {
            parameters.put("head", prefix + picture);
        }
        parameters.put("jobno", pei.getWorkno() != null ? pei.getWorkno()
                : " ");// 工号
        parameters.put("name", patientname);// 姓名
        parameters.put("company",
                pei.getOrgName() != null ? pei.getOrgName() : " ");// 单位名称
        parameters.put("number",
                pei.getPatientcode() != null ? pei.getPatientcode() : " ");// 体检号:模板中的编号位置

        //监制
        SysBranch branch = sysBranchMapper.getDefaultBranch();
        String content = reportConfigService.getBranchConfig(branch.getBranchId());
        if (StringUtils.isNotBlank(content)) {
            ReportConfigVo vo = JSONObject.parseObject(content, ReportConfigVo.class);
            parameters.put("producer", vo.getProducer()+"印制");
        }


        //可能没有单位电话，改成联系电话
        if (pei.getPhone() == null) {
            parameters.put("groupphone", " ");
        } else {
            parameters.put("groupphone", pei.getPhone());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(pei.getDateregister());
        parameters.put("date", !StringUtils.isEmpty(date) ? date : " ");// 填表日期（登记日期）
        String type = pei.getMedicaltype();
        if (!StringUtils.isEmpty(type) && !"null".equals(type)) {// 检查类别
            if ("0".equals(type)) {
                parameters.put("sgq", "√");
                parameters.put("before",
                        "上岗前（√）       在岗期间（  ）       离岗时（  ）");
                parameters.put("back", "离岗后（  ）       应急检查（  ）");
            } else if ("1".equals(type)) {
                parameters.put("zgqj", "√");
                parameters.put("before",
                        "上岗前（ ）       在岗期间（√）       离岗时（  ）");
                parameters.put("back", "离岗后（  ）       应急检查（  ）");
            } else if ("2".equals(type)) {
                parameters.put("lgs", "√");
                parameters.put("before",
                        "上岗前（ ）       在岗期间（ ）       离岗时（√）");
                parameters.put("back", "离岗后（  ）       应急检查（  ）");
            } else if ("3".equals(type)) {
                parameters.put("lgh", "√");
                parameters.put("before",
                        "上岗前（ ）       在岗期间（ ）       离岗时（ ）");
                parameters.put("back", "离岗后（√）       应急检查（  ）");
            } else if ("4".equals(type)) {
                parameters.put("yj", "√");
                parameters.put("before",
                        "上岗前（ ）       在岗期间（ ）       离岗时（ ）");
                parameters.put("back", "离岗后（ ）       应急检查（√）");
            }
        } else {
            parameters.put("before", "上岗前（ ）       在岗期间（  ）       离岗时（  ）");
            parameters.put("back", "离岗后（  ）       应急检查（  ）");
        }
        //赋码图片
        Attachment reportCoding = attachmentService.getOne(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientcode).eq(Attachment::getFileType, "赋码"));
        if (ObjectUtils.isNotEmpty(reportCoding)){
            parameters.put("reportCoding",prefix + reportCoding.getFilePath());
        }


        jasperPrintList.add(ru.getJasperPrint(
                local + professionModelUrls[0], parameters,
                new JREmptyDataSource()));

        parameters = deleteExcessData(parameters);
        resultMap.put("one", parameters);
        /** 第二页 */
        jasperPrintList.add(ru.getJasperPrint(
                local + professionModelUrls[4],
                new HashMap<String, Object>(), new JREmptyDataSource()));

        /** 第三页 */
        parameters = new HashMap<String, Object>();
        //职业问诊
        PeispatientConsultation pc = peispatientConsultationMapper.selectOne(new QueryWrapper<PeispatientConsultation>()
                .eq("patientcode", pei.getPatientcode()));
        //职业问诊签名图片
        PeispatientConsultationPic pcp = peispatientConsultationPicMapper.selectOne(new QueryWrapper<PeispatientConsultationPic>()
                .eq("patientcode", pei.getPatientcode()));
        //签名base64
        String signPic = pcp == null ? null : pcp.getSignPicture();
        parameters.put("handwriting", signPic == null ?
                null : prefix + signPic);
        parameters.put("patientname", patientname);
        parameters.put("topImg", local + Constants.TOPIMG);
        parameters.put("age", pei.getAge());
        parameters.put("pageNum", pageNum);
        parameters.put("patientcode", patientcode);
        parameters.put("name", patientname);// 姓名
        parameters.put("sex", Render.getSex(pei.getIdSex()));// 性别
        parameters.put("birthDate",
                pei.getBirthdate() != null ? pei.getBirthdate() : " ");// 出生日期
        parameters.put("bornAddress",
                pei.getResarea() != null ? pei.getResarea() : " ");// 出生地
        parameters.put("nation", pei.getNation() != null ? pei.getNation()
                : " ");// 民族
        parameters.put("phone", pei.getPhone() != null ? pei.getPhone()
                : " ");// 个人联系电话
        parameters.put("identityCard",
                pei.getIdcardno() != null ? pei.getIdcardno() : " ");// 身份证号
        parameters.put("postCode", pei.getYzbm() != null ? pei.getYzbm()
                : " ");// 邮编
        parameters.put("address",
                pei.getAddress() != null ? pei.getAddress() : " ");// 居住地址
        String h = pei.getJhys();// 用作获取所有逗号间隔的接害因素字符串
        if (!StringUtils.isEmpty(h)) {
            String[] hagards = h.split(",");
            if (hagards != null && hagards.length > 0) {
                String names = "";
                for (int i = 0; i < hagards.length; i++) {
                    Harm harm = harmMapper.getInfoById(hagards[i]);
                    String str = null;
                    if (harm != null) {
                        str = harm.getHarmName();
                    } else {
                        str = " ";
                    }
                    if (i == 0) {
                        names = names + str;
                    } else {
                        names = names + "、" + str;// 这里用顿号连接
                    }
                }
                if (names.length() > 25) {
                    parameters.put("hagard", names.substring(0, 25));// 接害因素
                    parameters.put("hagard1", names.substring(25));// 接害因素
                } else {
                    parameters.put("hagard", names);// 接害因素
                    parameters.put("hagard1", "");
                }

            } else {
                parameters.put("hagard", " ");// 接害因素
                parameters.put("hagard1", "");
            }

        } else {
            parameters.put("hagard", " ");// 接害因素
            parameters.put("hagard1", "");
        }
        //2020.8.12由月改为年
        parameters.put(
                "totalAge",
                (pei.getZgl() != null ? String.format("%.1f",
                        pei.getZgl() * 1.0 / 12) : " ") + "年");// 总工龄
        parameters.put(
                "hagardAge",
                (pei.getJhgl() != null ? String.format("%.1f",
                        pei.getJhgl() * 1.0 / 12) : " ")
                        + "年");// 接害工龄

        // 职业史
        List<HashMap> zysList = getProfessionHead2(patientcode);
        Collection<Map> zysCollection = new Vector<Map>();
        zysCollection.addAll(zysList);
        parameters.put("zys", zysCollection);
        // 职业病史
        List<HashMap> zybsList = getProfessionHead3(patientcode);
        Collection<Map> zybsCollection = new Vector<Map>();
        zybsCollection.addAll(zybsList);
        parameters.put("zybs", zybsCollection);
        // 签字时间
        parameters.put("rummagerTime", pcp == null || pcp.getDatecreated() == null
                ? "" : RUMFORMAT.format(pcp.getDatecreated()));

        JasperPrint jp2 = ru.getJasperPrint(local + professionModelUrls[1],
                parameters, new JREmptyDataSource());
        jasperPrintList.add(jp2);
        pageNum += jp2.getPages().size();
        parameters = deleteExcessData(parameters);
        resultMap.put("two", parameters);

        /** 第四页 */
        parameters = new HashMap<String, Object>();
        parameters.put("pageNum", pageNum);
        parameters.put("patientcode", patientcode);
        parameters.put("patientname", patientname);
        String duty = dateFormat.format(pei.getDateregister());
        parameters.put("duty", !StringUtils.isEmpty(duty) ? duty : " ");// 检查日期
        String evd = "";
        String menstrualHistoryDefaults = 0 == pei.getIdSex() ? "/" : " ";//月经史默认值
        if (pc != null) {
            if (pc.getEverOfDisease() != null) {
                List<WzJwb> jwbs = wzJwbMapper.selectList(new QueryWrapper<WzJwb>().in("id", pc.getEverOfDisease().split(",")));
                for (int i = 0, s = jwbs.size(); i < s; i++) {
                    evd += jwbs.get(i).getOccupationDiseast();
                    if (i != s - 1) {
                        evd += "、";
                    }
                }
            } else {
                evd = "无";// 默认无
            }
            if (evd.length() > 30) {
                parameters.put("anamnesis", evd.substring(0, 30));// 既往病史
                parameters.put("anamnesis2", evd.substring(30));// 既往病史
            } else {
                parameters.put("anamnesis", evd);// 既往病史
            }
            parameters.put("beginAge", pc.getCcnl() != null ? pc.getCcnl()
                    : menstrualHistoryDefaults);// 初潮年龄
            parameters.put("period", pc.getJq() != null ? pc.getJq() : menstrualHistoryDefaults);// 经期
            parameters.put("cycle", pc.getZq() != null ? pc.getZq() : menstrualHistoryDefaults);// 周期
            parameters.put("menolipsisAge",
                    pc.getTjnl() != null ? pc.getTjnl() : menstrualHistoryDefaults);// 停经年龄
            parameters.put("babyNum",
                    pc.getFamilyNumber() != null ? pc
                            .getFamilyNumber() : menstrualHistoryDefaults);// 子女数量
            parameters.put("misbirth", pc.getLc() != null ? pc.getLc() : menstrualHistoryDefaults);// 流产
            parameters.put("prematureBirth", pc.getZc() != null ? pc.getZc()
                    : menstrualHistoryDefaults);// 早产
            parameters
                    .put("deadBirth", pc.getSc() != null ? pc.getSc() : menstrualHistoryDefaults);// 死产
            parameters.put("fetalAnomalies",
                    pc.getYwrc() != null ? pc.getYwrc() : menstrualHistoryDefaults);// 异常胎
            parameters.put("malformation", pc.getJt() != null ? pc.getJt()
                    : menstrualHistoryDefaults);// 先天畸形

            String smok = pc.getAbstainSmokeNote();
            if (!StringUtils.isEmpty(smok) && !"null".equals(smok)) {
                if ("0".equals(smok)) {
                    parameters.put("neverSmoking", "√");
                    parameters.put("occasionally", "□");
                    parameters.put("former", "□");
                    parameters.put("often", "□");
                    parameters.put("bagNum", "    ");
                    parameters.put("smokeYear", "    ");
                    parameters.put("smokeMonth", "    ");
                } else if ("1".equals(smok)) {
                    parameters.put("neverSmoking", "□");
                    parameters.put("occasionally", "√");
                    parameters.put("former", "□");
                    parameters.put("often", "□");
                    parameters.put("bagNum", "    ");
                    parameters.put("smokeYear", "    ");
                    parameters.put("smokeMonth", "    ");
                } else if ("2".equals(smok)) {
                    parameters.put("neverSmoking", "□");
                    parameters.put("occasionally", "□");
                    parameters.put("former", "√");
                    parameters.put("often", "□");
                    parameters.put("bagNum", "    ");
                    parameters.put("smokeYear", "    ");
                    parameters.put("smokeMonth", "    ");
                } else if ("3".equals(smok)) {
                    parameters.put("neverSmoking", "□");
                    parameters.put("occasionally", "□");
                    parameters.put("former", "□");
                    parameters.put("often", "√");
                    parameters.put("bagNum", pc.getEverydaySmokeN());
                    parameters.put("smokeYear", pc.getSmokeYear());
                    parameters.put("smokeMonth", pc.getSmokeMonth());
                } else {
                    parameters.put("neverSmoking", "□");
                    parameters.put("occasionally", "□");
                    parameters.put("former", "□");
                    parameters.put("often", "□");
                    parameters.put("bagNum", "    ");
                    parameters.put("smokeYear", "    ");
                    parameters.put("smokeMonth", "    ");
                }

            } else {
                parameters.put("neverSmoking", "□");
                parameters.put("occasionally", "□");
                parameters.put("former", "□");
                parameters.put("often", "□");
                parameters.put("bagNum", "    ");
                parameters.put("smokeYear", "    ");
                parameters.put("smokeMonth", "    ");
            }
            String noKissTheCup = pc.getNoKissTheCup();// 不饮酒
            String betweenKissTheCup = pc.getBetweenKissTheCup();// 偶尔饮酒
            String abstainLostKiss = pc.getAbstainLostKiss();// 是否戒酒
            String evermoreKiss = pc.getEvermoreKiss();// 是否经常饮酒
            if (!"0".equals(noKissTheCup)) {
                parameters.put("neverDrank", "√");
                parameters.put("occasionallyDrank", "□");
                parameters.put("formerDrank", "□");
                parameters.put("oftenDrank", "□");
                parameters.put("ml", "   ");
                parameters.put("types", "   ");
                parameters.put("drankYear", "   ");
                parameters.put("drankMonth", "   ");
            } else if (!"0".equals(betweenKissTheCup)) {
                parameters.put("neverDrank", "□");
                parameters.put("occasionallyDrank", "√");
                parameters.put("formerDrank", "□");
                parameters.put("oftenDrank", "□");
                parameters.put("ml", "   ");
                parameters.put("types", "   ");
                parameters.put("drankYear", "   ");
                parameters.put("drankMonth", "   ");
            } else if (!"0".equals(abstainLostKiss)) {
                parameters.put("neverDrank", "□");
                parameters.put("occasionallyDrank", "□");
                parameters.put("formerDrank", "√");
                parameters.put("oftenDrank", "□");
                parameters.put("ml", "   ");
                parameters.put("types", "   ");
                parameters.put("drankYear", "   ");
                parameters.put("drankMonth", "   ");
            } else if (!"0".equals(evermoreKiss)) {
                parameters.put("neverDrank", "□");
                parameters.put("occasionallyDrank", "□");
                parameters.put("formerDrank", "□");
                parameters.put("oftenDrank", "√");
                parameters.put("ml",
                        pc.getKissAmount() != null ? pc.getKissAmount() : "");
                parameters.put("types",
                        pc.getKissType() != null ? pc.getKissType() : "");
                parameters.put("drankYear",
                        pc.getKissYearN() != null ? pc.getKissYearN() : "");
                parameters.put("drankMonth",
                        pc.getKissMonth() != null ? pc.getKissMonth() : "");
            } else {
                parameters.put("neverDrank", "□");
                parameters.put("occasionallyDrank", "□");
                parameters.put("formerDrank", "□");
                parameters.put("oftenDrank", "□");
                parameters.put("ml", "   ");
                parameters.put("types", "   ");
                parameters.put("drankYear", "   ");
                parameters.put("drankMonth", "   ");

            }
            if (pc.getFamilyOfDisease() != null
                    && !"".equals(pc.getFamilyOfDisease().trim())) {
                String fod = pc.getFamilyOfDisease();
                if (fod.length() > 30) {
                    parameters.put("familyHistory", fod.substring(0, 30));
                    parameters.put("familyHistory2", fod.substring(30));
                } else {
                    parameters.put("familyHistory", fod);
                }
            } else {
                parameters.put("familyHistory", "无");
            }
            parameters.put("other", StringUtils.isEmpty(pc.getOther()) ? "无" : pc.getOther());
        } else {
            evd = "无";// 默认无
            parameters.put("other", "无");
            parameters.put("anamnesis", evd);// 既往病史
            parameters.put("neverSmoking", "□");
            parameters.put("occasionally", "□");
            parameters.put("former", "□");
            parameters.put("often", "□");
            parameters.put("bagNum", "    ");
            parameters.put("smokeYear", "    ");
            parameters.put("familyHistory", "无");
            parameters.put("neverDrank", "□");
            parameters.put("occasionallyDrank", "□");
            parameters.put("formerDrank", "□");
            parameters.put("oftenDrank", "□");
            parameters.put("ml", "   ");
            parameters.put("types", "   ");
            parameters.put("drankYear", "   ");
            parameters.put("neverSmoking", "□");
            parameters.put("occasionally", "□");
            parameters.put("former", "□");
            parameters.put("often", "□");
            parameters.put("bagNum", "    ");
            parameters.put("smokeYear", "    ");
            parameters.put("beginAge", menstrualHistoryDefaults);// 初潮年龄
            parameters.put("period", menstrualHistoryDefaults);// 经期
            parameters.put("cycle", menstrualHistoryDefaults);// 周期
            parameters.put("menolipsisAge", menstrualHistoryDefaults);// 停经年龄
            parameters.put("babyNum", menstrualHistoryDefaults);// 子女数量
            parameters.put("misbirth", menstrualHistoryDefaults);// 流产
            parameters.put("prematureBirth", menstrualHistoryDefaults);// 早产
            parameters.put("deadBirth", menstrualHistoryDefaults);// 死产
            parameters.put("fetalAnomalies", menstrualHistoryDefaults);// 异常胎
            parameters.put("malformation", menstrualHistoryDefaults);// 先天畸形
            parameters.put("smokeMonth", menstrualHistoryDefaults);
            parameters.put("drankMonth", menstrualHistoryDefaults);
        }


        JasperPrint jp3 = ru.getJasperPrint(local + professionModelUrls[2],
                parameters, new JREmptyDataSource());
        pageNum += jp3.getPages().size();
        jasperPrintList.add(jp3);

        //删除没用的字段
        parameters = deleteExcessData(parameters);
        resultMap.put("three", parameters);

        resultMap.put("pageNum", pageNum);
        resultMap.put("patientname", patientname);
        resultMap.put("jasperPrintList", jasperPrintList);

        return resultMap;
    }

    /**
     * 获取图片的base64
     *
     * @param signPic
     * @return
     */
    private String getPicBase64(String signPic) {
        String base64Image = "";
        try {
            URL url = new URL(signPic);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            byte[] bytes = baos.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(bytes);

            is.close();
            baos.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }

    /**
     * 获取职业病史
     *
     * @param patientcode
     * @return
     */
    private List<HashMap> getProfessionHead3(String patientcode) {
        List<ProfessionHead3Dto> list = iPersonalReportMapper.getProfessionHead3(patientcode);
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (list != null) {
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    ProfessionHead3Dto obj = list.get(i);
                    String diseaseName = String.valueOf(obj.getOccupationDiseast());
                    String diagnosisDate = obj.getDiagnosisDate();
                    String diagnosisUnit = String.valueOf(obj.getDiagnosisDept());
                    String isCure = (obj.getStatus() != null && obj.getStatus().toString()
                            .equals("1")) ? "是" : "否";
                    String status = null;
                    status = isCure;
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("diseaseName", !StringUtils.isEmpty(diseaseName)
                            && !"null".equals(diseaseName) ? diseaseName : "");
                    result.put(
                            "diagnosisDate",
                            !StringUtils.isEmpty(diagnosisDate)
                                    && !"null".equals(diagnosisDate) ? diagnosisDate
                                    : "");
                    result.put(
                            "diagnosisUnit",
                            !StringUtils.isEmpty(diagnosisUnit)
                                    && !"null".equals(diagnosisUnit) ? diagnosisUnit
                                    : "");
                    result.put("isCure", status != null ? status : "未知");

                    mapList.add(result);
                }
            } else {// 默认无
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("diseaseName", "无");
                result.put("diagnosisDate", "无");
                result.put("diagnosisUnit", "无");
                result.put("isCure", "");
                mapList.add(result);
            }

        }
        return mapList;
    }


    /**
     * 获取职业史信息
     *
     * @param patientcode
     * @return
     */
    private List<HashMap> getProfessionHead2(String patientcode) {
        List<ProfessionHead2Dto> list = iPersonalReportMapper.getProfessionHead2(patientcode);
        List<HashMap> mapList = new ArrayList<HashMap>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ProfessionHead2Dto obj = list.get(i);
                HashMap<String, String> result = new HashMap<String, String>();
                String begin = obj.getStartDate();
                String end = obj.getEndDate();
                String startDate = null;
                if (!StringUtils.isEmpty(begin) && !"null".equals(begin)
                        && !StringUtils.isEmpty(end) && !"null".equals(end)) {
                    startDate = begin + "至" + end;
                } else if (!StringUtils.isEmpty(begin) && !"null".equals(begin)) {
                    startDate = begin + "至今";
                } else if (!StringUtils.isEmpty(end) && !"null".equals(end)) {
                    startDate = "至" + end;
                }
                String workUnit = String.valueOf(obj.getDept());
                String plant = String.valueOf(obj.getBranch());
                String craft = String.valueOf(obj.getTypeOfWork());
                Object harm = obj.getOcupationHarm();
                String fence = obj.getOccupationDefend() == null ? "" : obj.getOccupationDefend().toString().equals(
                        "1") ? "有" : "无";
                result.put("startDate", startDate != null ? startDate : " ");
                result.put(
                        "workUnit",
                        !"null".equals(workUnit)
                                && !StringUtils.isEmpty(workUnit) ? workUnit
                                : " ");
                result.put(
                        "plant",
                        !"null".equals(plant) && !StringUtils.isEmpty(plant) ? plant
                                : " ");
                result.put(
                        "craft",
                        !"null".equals(craft) && !StringUtils.isEmpty(craft) ? craft
                                : " ");
                result.put("harm",
                        ToolUtil.ClobToString(harm).replaceAll(",", "、"));
                result.put(
                        "fence",
                        !"null".equals(fence) && !StringUtils.isEmpty(fence) ? fence
                                : " ");
                mapList.add(result);

                //2019-4-30  要求改回显示多条
                //break;//只显示最新一条，以前的体检号不管
            }
            int l = 6 - list.size();
            if (l > 0) {
                for (int i = 0; i < l; i++) {
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("startDate", " ");
                    result.put("workUnit", " ");
                    result.put("plant", " ");
                    result.put("craft", " ");
                    result.put("harm", " ");
                    result.put("fence", " ");
                    mapList.add(result);
                }
            }

        }
        return mapList;
    }


    /**
     * 生成健康个检头部文件
     *
     * @param patientcode
     * @param type
     * @param lrctType
     * @return
     */
    private HashMap<String, Object> healthHeadPage(String patientcode, Integer type, Integer lrctType) {
        String local = System.getProperty("user.dir") + "/temp/";
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> parameters2 = new HashMap<String, Object>();//第二页参数
        //获取数据
        AllOneHealthDto dto = iPersonalReportMapper.findAllOneHealth(patientcode, lrctType);

        //对象转map
        Map<String, Object> parameters = BeanUtil.beanToMap(dto);
        parameters.put("sex", "0".equals(dto.getSex()) ? "男" : "女");
        parameters.put("marriage", StringUtils.isBlank(dto.getMarriage()) ? "" : MarriageType.getName(Integer.valueOf(dto.getMarriage())));

        //设置在参数设置里单位名称要使用分组名称
        String workunit = StringUtils.isBlank(dto.getWorkunit()) ? "" : dto.getWorkunit();
        String reportGroupNameConfig = iSysConfigService.selectConfigByKey(Constants.REPORT_GROUPNAME_CONFIG);
        if (ObjectUtils.isNotEmpty(reportGroupNameConfig)) {
            List<String> stringList = JSONArray.parseArray(reportGroupNameConfig, String.class);
            if (stringList.contains(dto.getNumorgresv())) {
                workunit = dto.getOrgreservationgroupname();
            }
        }
        parameters.put("workunit", workunit);
        // TODO: 2024/7/10 锦都去除沃德logo
        String background = StringUtils.equals(loadProperties.name, "jindu") ? "file/wordmodel/ks-i/health_common_cover_noLogo.jpg" : "file/wordmodel/ks-i/health_common_cover.jpg";
        parameters.put("background", local + background);
        parameters.put("org", workunit);
        parameters.put("dateregister", dto.getDateregister());

        //首页数据
        Collection<Map<String, Object>> titleList = new Vector<Map<String, Object>>();
        BASE64Encoder encode = new BASE64Encoder();
        boolean islrct = lrctType != 0;

        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("title", "体检单位：");
//        map.put("value", dto.getWorkunit());
//        titleList.add(map);


        map = new HashMap<String, Object>();
        map.put("title", islrct ? "所属机构：" : "部    门：");
        map.put("value", StringUtils.isEmpty(dto.getDepartment()) ? "" : dto.getDepartment());
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("value", parameters.get("patientcode"));
        map.put("title", "体检号码：");
        try {
            map.put("barcode", encode.encode(CreateBarcodeDefault.getHomePageBarcode((String) parameters.get("patientcode"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("value", dto.getPatientname());
        map.put("title", "姓    名：");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("value", "0".equals(dto.getSex()) ? "男" : "女");
        map.put("title", "性    别：");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("value", dto.getAge());
        map.put("title", "年    龄：");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("value", dto.getPhone());
        map.put("title", "电    话：");
        titleList.add(map);

//        map = new HashMap<String, Object>();
//        map.put("value", dto.getDateregister());
//        map.put("title", "体检日期：");
//        titleList.add(map);

        parameters.put("titleList", titleList);
        //头像和体检码
        if (StringUtils.isNotNull(dto.getHead())) {
            parameters.put("head", prefix + dto.getHead());
        }
        String barcode = encode.encode(CreateBarcodeDefault
                .createBarcodeDefault(patientcode));
        parameters.put("barcode", barcode);


        //默认分中心的地址和电话
        SysBranch branch = sysBranchMapper.getDefaultBranch();
        if (branch != null) {
            //默认配置
            String content = reportConfigService.getBranchConfig(branch.getBranchId());
            if (StringUtils.isNotBlank(content)) {
                ReportConfigVo vo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
                parameters.put("org_address", vo.getAddress());
                parameters.put("org_phone", vo.getPhone());
                parameters.put("org_zipcode", vo.getPostalCode());
                //职业电话
                parameters.put("zxdh", vo.getPhone());
                if (StringUtils.isNotEmpty(vo.getStampPic())) {
                    parameters.put("stampPic", prefix + vo.getStampPic());
                }
            }
        }

        //如果是VVIP
        String idPatientClass = dto.getIdPatientclass();

        if (islrct) {

        } else if ("3".equals(idPatientClass)) {
            // TODO: 2024/7/10 锦都去除沃德logo
            String vipBackground = StringUtils.equals(loadProperties.name, "jindu") ? "file/wordmodel/ks-i/health_vviphead_noLogo.jpg" : "file/wordmodel/ks-i/health_vviphead.jpg";
            parameters.put("background", local + vipBackground);
            try {
                jasperPrintList.add(ru.getJasperPrint(local + "file/wordmodel/ks-i/healthone_vvip.jasper",
                        parameters, new JREmptyDataSource()));
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
            Map<String, Object> enddata = new HashMap<String, Object>();
            enddata.put("barcode", barcode);
            enddata.put("patientname", parameters.get("patientname"));
            enddata.put("age", parameters.get("age"));
            enddata.put("background", local + "file/wordmodel/ks-i/health_vvipend.jpg");
            if (branch != null) {
                //默认配置
                String content = reportConfigService.getBranchConfig(branch.getBranchId());
                if (StringUtils.isNotBlank(content)) {
                    ReportConfigVo vo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
                    enddata.put("org_address", vo.getAddress());
                    enddata.put("org_phone", vo.getPhone());
                    enddata.put("org_zipcode", vo.getPostalCode());
                    enddata.put("fzx", branch.getFzx());
                    parameters.put("zxdh", vo.getPhone());
                }
            }
            resultMap.put("enddata", enddata);
        } else {
            try {
                jasperPrintList.add(ru.getJasperPrint(local + healthModelUrls[0],
                        parameters, new JREmptyDataSource()));
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }

        if (type == 0) {// 临时报告 不生成此页
            if (lrctType == 0) {
                try {
                    jasperPrintList.add(ru.getJasperPrint(local + healthModelUrls[1],
                            parameters2, new JREmptyDataSource()));
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
            }
            parameters.put("pageNum", 2);
            // TODO: 2024/7/10 锦都去除沃德logo
            if (!StringUtils.equals(loadProperties.name, "jindu")){
                parameters.put("topImg", local + Constants.TOPIMG);
                parameters.put("header", local + Constants.HEADER);
            }

            //是否启用电子签名
            if ("1".equals(Constants.ELECTRONIC_SIGN)) {
                String totaldoctor = dto.getTotaldoctor();//name  不是username
                String totalidobj = dto.getTotaldoctorid();
                String doctorName = totaldoctor == null ? "" : totaldoctor.toString();
                if (totalidobj != null && totalidobj.toString().length() > 0) {
                    String totalid = totalidobj.toString();
                    SysUser user = sysUserMapper.getUserByNo(totalid);
                    if (user != null && StringUtils.isNotBlank(user.getSignPic())) {
                        //校验图片是否存在
                        String defaultSignPic = prefix + user.getSignPic();
                        if (!isImageUrlExists(defaultSignPic)){
                            throw new ServiceException(user.getUserName() + " 签名图片不存在!");
                        }
                        //医生签字
                        parameters.put("signPic", defaultSignPic);
                    }
                }
                if (parameters.get("signPic") == null) {
                    parameters.put("signName", doctorName);
                }
            }
            //查询有没有设置默认总检医生签名id
            String defaultDoctorNo = iSysConfigService.getSysConfigObject(Constants.DEFAULT_DOCTOR_NO, String.class);
            SysUser sysUser = sysUserMapper.getUserByNo(defaultDoctorNo);
            if (ObjectUtils.isNotEmpty(sysUser)) {

                if (StringUtils.isNotBlank(sysUser.getSignPic())) {
                    String defaultSignPic = prefix + sysUser.getSignPic();
                    //校验图片是否存在
                    if (!isImageUrlExists(defaultSignPic)){
                        throw new ServiceException(sysUser.getUserName() + " + 签名图片不存在!");
                    }
                    parameters.put("defaultSignPic", defaultSignPic);
                } else {
                    parameters.put("defaultTotaldoctor", sysUser.getUserName());
                }
            }

            JasperPrint jp3 = null;
            try {
                jp3 = ru.getJasperPrint(local + healthModelUrls[2],
                        parameters, new JREmptyDataSource());
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
            jasperPrintList.add(jp3);
            resultMap.put("pageNum", jp3.getPages().size() + 2);
        } else {
            resultMap.put("pageNum", 1);
        }


        if (ObjectUtils.isNotEmpty(parameters)) {
            //删除多余数据
            parameters = deleteExcessData(parameters);
        }


        resultMap.put("parameters", parameters);
        resultMap.put("jasperPrintList", jasperPrintList);
        resultMap.put("patientname", dto.getPatientname());
        resultMap.put("idPatientClass", idPatientClass);
        return resultMap;
    }


    /**
     * 删除多余数据
     *
     * @param parameters
     * @return
     */
    private Map<String, Object> deleteExcessData(Map<String, Object> parameters) {
        //删除多余的东西,要不然存不到数据库中（含$影响同步报错）
        if (parameters.containsKey("JASPER_REPORTS_CONTEXT")) {
            parameters.remove("JASPER_REPORTS_CONTEXT");
        }
        if (parameters.containsKey("REPORT_FORMAT_FACTORY")) {
            parameters.remove("REPORT_FORMAT_FACTORY");
        }
        if (parameters.containsKey("REPORT_PARAMETERS_MAP")) {
            parameters.remove("REPORT_PARAMETERS_MAP");
        }
        if (parameters.containsKey("JASPER_REPORT")) {
            parameters.remove("JASPER_REPORT");
        }
        if (parameters.containsKey("REPORT_LOCALE")) {
            parameters.remove("REPORT_LOCALE");
        }
        if (parameters.containsKey("REPORT_DATA_SOURCE")) {
            parameters.remove("REPORT_DATA_SOURCE");
        }
        if (parameters.containsKey("REPORT_TIME_ZONE")) {
            parameters.remove("REPORT_TIME_ZONE");
        }
        if (parameters.containsKey("REPORT_VIRTUALIZER")) {
            parameters.remove("REPORT_VIRTUALIZER");
        }
        if (parameters.containsKey("IS_IGNORE_PAGINATION")) {
            parameters.remove("IS_IGNORE_PAGINATION");
        }
        if (parameters.containsKey("IS_IGNORE_PAGINATION")) {
            parameters.remove("IS_IGNORE_PAGINATION");
        }
        //删除二维码，太大了占地方
        if (parameters.containsKey("barcode")) {
            parameters.remove("barcode");
        }
        if (parameters.containsKey("titleList")) {
            Collection<Map<String, Object>> titleList =(Collection<Map<String, Object>>) parameters.get("titleList");
            for (Map<String, Object> map : titleList) {
                if (map.containsKey("barcode")) {
                    map.remove("barcode");
                }
            }
        }
        return parameters;
    }


    /**
     * 获取所有的图片科室
     *
     * @param patientcode
     * @param dh
     * @return
     */
    private List<SysDept> findAllPicDep(String patientcode, Integer dh) {
        List<SysDept> list = new ArrayList<>();
        if (dh == 0) {
            //健康
            list = iPersonalReportMapper.findAllPicDepH(patientcode);

        } else if (dh == 1) {
            //职业
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            String[] harmIds = patient.getJhys().split(",");
            String medicalType = patient.getMedicaltype();
            list = iPersonalReportMapper.findAllPicDepD(patientcode, harmIds, medicalType);
        }
        List<SysDept> depts = new ArrayList<SysDept>();
        for (int i = 0, s = list.size(); i < s; i++) {
            SysDept os = list.get(i);
            SysDept dep = sysDeptMapper.getByDeptNo(os.getDeptNo());
            if (dep != null) {
                depts.add(dep);
            }
        }
        return depts;
    }


    /**
     * 根据体检号查询出某人本次都需要在哪些科室中做检查
     *
     * @param patientcode List<QxDepartment>
     * @return
     * @Title: findAllDep
     * @author yinzl
     * @since 2016年9月9日 V 1.0
     */
    private List<SysDept> findAllDep(String patientcode, int isContainPacs) {
        String cid = isContainPacs != 2 ? sysBranchMapper.getDefaultCid() : null;
        List<String> pacsDids = Arrays.asList(new String[]{"US", "DR", "CR", "CT", "MR", "DX"});
        List<SysDept> deps = new ArrayList<SysDept>();
        List<String> depIds = iPersonalReportMapper.findAllDep(patientcode);
        if (depIds != null && depIds.size() > 0) {
            for (String d : depIds) {
                if (!StringUtils.isEmpty(d) && !"null".equals(d)) {
                    SysDept dep = sysDeptMapper.getByDeptNo(d);
                    if (dep != null) {
                        if (isContainPacs == 0) {
                            SysCenDep qcd = sysCenDepMapper.selectOne(new QueryWrapper<SysCenDep>()
                                    .eq("cid", cid).eq("did", d)
                            );
                            if (pacsDids.contains(qcd.getJklx())) {
                                continue;
                            }
                        } else if (isContainPacs == 1) {
                            SysCenDep qcd = sysCenDepMapper.selectOne(new QueryWrapper<SysCenDep>()
                                    .eq("cid", cid).eq("did", d)
                            );
                            if (!pacsDids.contains(qcd.getJklx())) {
                                continue;
                            }
                        }
                        deps.add(dep);
                    }
                }
            }
        }
        return deps;

    }


    /**
     * 职业报告不显示无职业结果的科室
     *
     * @param patientcode
     * @return List<QxDepartment>
     * @Title: findAllDepD
     * @author xuhp
     * @since 2017-4-18 V 1.0
     * <p>
     * isContainPacs 0不包含pacs科室     1只包含pacs科室  2包含所有
     */
    public List<SysDept> findAllDepD(String patientcode, int isContainPacs) {
        String cid = isContainPacs != 2 ? sysBranchMapper.getDefaultCid() : null;
        List<String> pacsDids = Arrays.asList(new String[]{"US", "DR", "CR", "CT", "MR", "DX"});
        List<SysDept> deps = new ArrayList<SysDept>();
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        if (patient != null) {
            List<String> depIds = iPersonalReportMapper.findAllDepD(patientcode, patient.getJhys().split(","), patient.getMedicaltype());
            if (depIds != null && depIds.size() > 0) {
                for (String d : depIds) {
                    if (!StringUtils.isEmpty(d) && !"null".equals(d)) {
                        SysDept dep = sysDeptMapper.getByDeptNo(d);
                        if (dep != null) {
                            if (isContainPacs == 0) {
                                SysCenDep qcd = sysCenDepMapper.selectOne(new QueryWrapper<SysCenDep>()
                                        .eq("cid", cid).eq("did", d)
                                );
                                if (pacsDids.contains(qcd.getJklx())) {
                                    continue;
                                }

                            } else if (isContainPacs == 1) {
                                SysCenDep qcd = sysCenDepMapper.selectOne(new QueryWrapper<SysCenDep>()
                                        .eq("cid", cid).eq("did", d)
                                );
                                if (!pacsDids.contains(qcd.getJklx())) {
                                    continue;
                                }
                            }
                            deps.add(dep);
                        }
                    }
                }
            }
        }
        return deps;
    }

    /**
     * 返回范围内随机数   start<=result<start+increment
     */
    public static int getRandom(int start, int increment) {
        return start + (int) (increment * Math.random());
    }

    /**
     * 职业必查拒检项目
     *
     * @param patientcode
     * @return
     */
    private Collection<Map<String, String>> getJjData(String patientcode) {
        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        String harmIds = patient.getJhys();
        String medicalType = patient.getMedicaltype();
        List<JjDataDto> list = iPersonalReportMapper.getJjData(patientcode, harmIds.split(","), medicalType);
        Collection<Map<String, String>> col = new Vector<Map<String, String>>();
        if (list.size() > 0) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("dept", "拒检项目科室");
            m.put("item", "拒检项目名称");
            m.put("jjr", "拒检人签字");
            m.put("jjrqm", null);
            m.put("jjsj", "拒检时间");
            col.add(m);
            String realPath = FileTypePath.REAL_PATH;
            for (int i = 0; i < list.size(); i++) {
                JjDataDto os = list.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("dept", os.getDeptName() == null ? null : os.getDeptName().toString());
                map.put("item", os.getExamfeeitemName() == null ? null : os.getExamfeeitemName().toString());
                map.put("jjr", os.getJjr() == null ? null : os.getJjr().toString());
                map.put("jjrqm", null);
                if (os.getJjrqm() != null) {
                    String jjrqm = realPath + os.getJjrqm().toString();
                    File f = new File(jjrqm);
                    if (f.exists()) {
                        map.put("jjrqm", jjrqm);
                    } else {
                        System.out.println("拒检人签名不存在：" + jjrqm);
                    }
                }
                map.put("jjsj", os.getJjsj() == null ? null : os.getJjsj().toString());
                col.add(map);
            }
        }
        return col;
    }

    /**
     * 按配置文件缩放图片
     *
     * @param path
     * @return Image
     * @Title: getImage
     * @author xuhp
     * @since 2017-6-26 V 1.0
     */
    @SuppressWarnings("unused")
    private String getImage(String path, int width, int height, String realPath) {
        String suffix = path.substring(path.lastIndexOf("."));
        //海关pacs图片以GOLD结尾，不改变会报错
        if (".GOLD".equals(suffix)) {
            suffix = ".jpg";
        }
        //必须存在image目录
        String result = realPath + "file/temp/image/"
                + timeFormat.format(new Date()) + UUID.randomUUID()
                + suffix;

        //生成临时文件
        File file = new File(result);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            Thumbnails.of(path).scale(1.0).rotate(90.0).toFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }




    /**
     * 生成复查名单
     * @param id
     * @param username
     * @return
     */
    @Override
    public void createPdf(String id, String username) {
        String local = System.getProperty("user.dir") + "/temp/";
        SimpleDateFormat taday = new SimpleDateFormat("yyyyMMdd");
        //文件所在目录相对路径
        String parentPath = local
                +File.separator
                +"wordexp"
                +File.separator
                +"group_review"
                +File.separator
                + taday.format(new Date())
                +File.separator
                ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //保存到数据库的文件路径
        String savePath = parentPath + "group_review_"
                + formatter.format(new Date()) + ".pdf";
        //pdf输出位置
        String expPdfPath = savePath;
        File folder=new File(expPdfPath).getParentFile();
        if(!folder.exists())folder.mkdirs();
        //word输出位置
        String expWordPath=expPdfPath.substring(0,
                savePath.lastIndexOf(".")) + ".docx";
        //模板位置
        String modelPath = local + "file/wordmodel/review/group_review.docx";
        //准备数据
        Map<String, Object> data =new HashMap<String,Object>();
        GroupReviewNotice grn = groupReviewNoticeMapper.getInfoById(id);
        try {
            Sellcustomer sc = sellcustomerMapper.getInfoById(grn.getCustomerId());
            data.put("customer", StringUtils.isEmpty(sc.getLicenseName())?sc.getKhdwmc()
                    :sc.getLicenseName().equals(sc.getKhdwmc())?sc.getLicenseName()
                    :(sc.getLicenseName()+"("+sc.getKhdwmc()+")"));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
            Date createDate=new Date();
            data.put("createDate", sdf.format(createDate));
            SimpleDateFormat sdf2=new SimpleDateFormat("yyyy'.'MM'.'dd");
            data.put("startDate", sdf2.format(grn.getStartDate()));
            data.put("endDate", sdf2.format(grn.getEndDate()));
            Calendar c=Calendar.getInstance();
            c.setTime(createDate);
            c.add(Calendar.MONTH, 1);
            SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd");
            String dateStr=sdf3.format(c.getTime());
            String[] dateArr=dateStr.split("-");
            data.put("year", dateArr[0]);
            data.put("month", dateArr[1]);
            data.put("day", dateArr[2]);
            List<RowRenderData> griddata=new ArrayList<RowRenderData>();
            data.put("grid", griddata);

            ReviewNoticeListParam param = new ReviewNoticeListParam(grn.getCustomerId(),grn.getDdh(),grn.getStartDate(),grn.getEndDate());
            SysBranch branch = sysBranchMapper.getByBranchId(grn.getConfigId());
            if (ObjectUtils.isEmpty(branch)) branch = sysBranchMapper.getDefaultBranch();
            String content = reportConfigService.getBranchConfig(branch.getBranchId());
            ReportConfigVo configVo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
            if (ObjectUtils.isNotEmpty(configVo) && StringUtils.isNotBlank(configVo.getName())){
                data.put("reviewCompony", configVo.getName());
            }else {
                data.put("reviewCompony", "青岛沃德国际医疗健康产业股份有限公司综合门诊部");
            }
            List<ReviewNoticeListDto> list = iPersonalReportMapper.getReviewNoticeList(param);
            Set<String> allItemIds = new HashSet<>();
            if(CollectionUtils.isEmpty(list)) {
                throw new ServiceException("生成失败，没有需要复查的人员");
            }
            List<GroupReviewNoticePatient> grnps = new ArrayList<>();
            for(int i=0;i<list.size();i++) {
                ReviewNoticeListDto os = list.get(i);
                grnps.add(new GroupReviewNoticePatient(grn.getId(),os.getPatientcode()));
                String jhysIds = os.getJhys();
                List<Harm> harms = harmMapper.selectList(new LambdaQueryWrapper<Harm>().in(Harm::getId,jhysIds.split(",")));
                Set<String> harmNames=new HashSet<>();
                for(Harm harm:harms) {
                    harmNames.add(harm.getHarmName());
                }
                String harmName= StringUtils.join(harmNames, "、");
                String itemIds=os.getId();
                if(StringUtils.isNotEmpty(itemIds)) {
                    allItemIds.addAll(Arrays.asList(itemIds.split("、")));
                }
                //太长，去掉
//				SectionTotal st=get(SectionTotal.class
//						,Restrictions.eq("patientcode",os[11].toString())
//						,Restrictions.eq("diseaseHealth",1)
//						);
                Style style = new Style();
                style.setFontSize(8);
                griddata.add(RowRenderData.build(
                                new TextRenderData((i+1)+"",style)
                                ,new TextRenderData(os.getPatientname(),style)
                                ,new TextRenderData(os.getPatientcode(),style)
                                ,new TextRenderData(os.getIdcardno(),style)
                                ,new TextRenderData(Render.getSex(os.getIdSex()),style)
                                ,new TextRenderData(os.getAge(),style)
                                ,new TextRenderData(Render.getMedicalType(os.getMedicaltype()),style)
                                ,new TextRenderData(os.getJhgl(),style)
                                ,new TextRenderData(os.getOrgDepart(),style)
                                ,new TextRenderData(os.getTrades(),style)
                                ,new TextRenderData(harmName,style)
//						,new TextRenderData(getString(st.getPosistive()))
                                ,new TextRenderData(os.getExamfeeitemName(),style)
                        )
                );
            }

            if(allItemIds.size()>0) {
                List<Items> items = itemsMapper.selectList(new LambdaQueryWrapper<Items>().in(Items::getId, allItemIds));
                Set<String > reviewNotes=new LinkedHashSet<>();
                int noteIdx=0;
                for(Items item:items) {
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(item.getReviewMatters())) {
                        reviewNotes.add("    "+(noteIdx+1)+"、"
                                +item.getExamfeeitemNameprn()
                                +"："
                                +item.getReviewMatters());
                        noteIdx++;
                    }
                }
                String notice= org.apache.commons.lang3.StringUtils.join(reviewNotes,"\n");
                data.put("notice",notice);
            }else {
                data.put("notice","");
            }

            Configure cc = Configure.newBuilder()
                    .customPolicy("grid"
                            , new GroupReviewTablePolicy())
                    .build();
            XWPFTemplate template = XWPFTemplate.compile(modelPath
                    , cc).render(data);
            try(FileOutputStream out = new FileOutputStream(expWordPath);){
                template.write(out);
                out.flush();
            }
            template.close();
            WordConvertPDF conver = new WordConvertPDF();
            conver.wordToPDF(expWordPath,expPdfPath);
            //修改状态
            grn.setErrorMsg("");
            grn.setCreator(SecurityUtils.getUsername());
//            grn.setConfigId(configId);
            grn.setStatus(1);
            String attPath = uploadGroupReviewNotice(savePath,grn.getId());
            grn.setUrl(attPath);
            groupReviewNoticeMapper.updateById(grn);
            //保存人员信息
            groupReviewNoticePatientService.remove(new LambdaQueryWrapper<GroupReviewNoticePatient>().eq(GroupReviewNoticePatient::getMainId,grn.getId()));
            groupReviewNoticePatientService.saveBatch(grnps);
        }catch(Exception e) {
            e.printStackTrace();
            grn.setErrorMsg(e.getMessage());
            grn.setCreator(SecurityUtils.getUsername());
            grn.setConfigId(null);
            grn.setStatus(-1);
            groupReviewNoticeMapper.updateById(grn);
        }
    }

    /**
     *
     * @param savePath
     * @return
     */
    private String uploadGroupReviewNotice(String savePath,String id) {
        File file = new File(savePath);

        String originalFilename = file.getName();  // 获取文件原始名称
        String contentType = "application/pdf";  // 设置文件类型，根据实际情况进行设置
        // 将File对象转换为MultipartFile
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(originalFilename, file.getName(), contentType, FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        String extName = FileUtil.extName(file.getName());
        Attachment att = new Attachment();
        att.setType(3);
        att.setPatientcode(id);
        att.setFileType("复查人员名单");
        try {
            attachmentService.uploadFile(multipartFile, att, baseDir, extName, null, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        //删除临时文件
//        file.delete();
        return att.getFilePath();
    }

    /**
     * 隐私报告生成
     * @param patientcode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createTiming(List<String> patientcode) {
        //批量查询体检者
        List<Peispatient> patients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                .in("patientcode", patientcode));
        for (Peispatient patient : patients) {
            //健康体检状态
            if (patient.getJktjzt() != null && patient.getJktjzt().intValue() == 11) {
                throw new ServiceException("体检号" + patientcode + "报告已领取，不能生成隐私报告。");
            }
            Integer count = reportPrintingMapper.containsAllPrivate(patient.getPatientcode());
            if (count == 0){
                throw new ServiceException("体检号" + patientcode + "没有隐私项目!");
            }
            //生成隐私报告pdf
            String path = null;
            try {
                path = privateReportService.createPdf(patient.getPatientcode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("打印一下隐私报告的路径:{}",path);

            //上传
            File file = new File(path);
            String originalFilename = file.getName();  // 获取文件原始名称
            String contentType = "application/pdf";  // 设置文件类型，根据实际情况进行设置
            // 将File对象转换为MultipartFile
            MultipartFile multipartFile = null;
            try {
                multipartFile = new MockMultipartFile(originalFilename, file.getName(), contentType, FileUtils.readFileToByteArray(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
            String extName = FileUtil.extName(file.getName());
            Attachment att = new Attachment();
            att.setType(3);
            att.setPatientcode(patient.getPatientcode());
            att.setFileType("隐私报告");
            try {
                attachmentService.uploadFile(multipartFile, att, baseDir, extName, null, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //其他报告
            OtherReport or = otherReportMapper.getInfoByCode(patient.getPatientcode(), "2");
            if (or == null) {
                or = new OtherReport();
                or.setCreateStatus(1);
                or.setReportType(2);
                or.setPatientcode(patient.getPatientcode());
                or.setCreateTime(new Date());
                or.setCreater(SecurityUtils.getUsername());
                or.setPdfUrl(att.getFilePath());
                otherReportMapper.insert(or);
            } else {
                or.setPdfUrl(att.getFilePath());
                or.setCreateStatus(1);
                or.setCreateTime(new Date());
                or.setCreater(SecurityUtils.getUsername());
                otherReportMapper.updateById(or);
            }

            //查询隐私报告数据
            PrivateReportVo vo = privateReportService.create(patient.getPatientcode());
            //生成的报告,插入到报告内容表中
            String jsonString = JSON.toJSONString(vo);
            reportContentService.createReportContent(jsonString, 2, patient.getPatientcode(), String.valueOf(patient.getIdExamtype()), null, null, null, null);

        }
        return Boolean.TRUE;
    }




    /**
     * 生成以前的老系统报告
     *
     * @param param
     * @return
     */
    @Override
    public Boolean createOldReport(IReportParam param) {

        //取出属性
        List<String> patientcode = param.getPatientcode();
        //当前登录用户名
        String username = SecurityUtils.getUsername();
        String isJy = param.getIsJy();
        Integer dh = param.getDh();

        if (CollectionUtils.isNotEmpty(patientcode)) {
            for (int i = 0; i < patientcode.size(); i++) {
                String code = patientcode.get(i);
                Peispatient patient = peispatientMapper.getByPatientCode(code);
                if (ObjectUtils.isEmpty(patient)) {
                    continue;
                }
                //不是检验报告
                if (!"1".equals(isJy)) {
                    //报告主表
                    Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                            .eq("disease_health", dh).eq("patientcode", code));
                    if (report == null) {
                        continue;
                    }

                    //删除原始文件地址
                    if (StringUtils.isNotEmpty(report.getUrlPdf())) {
                        attachmentService.deleteFile(report.getUrlPdf());
                    }

                    //报告数据
                    param.setLrctType(0);
                    param.setType(0);
                    param.setCode(code);
                    if ("3".equals(patient.getIdExamtype())) {
                        param.setDh(3);
                    }
                    CreateReportVo vo = null;
                    try {
                        vo = createReport(param);
                        //更新报告内容
                        //生成的报告,插入到报告内容表中
                        String jsonString = JSON.toJSONString(vo.getIReportVo());
                        //把前缀都替换掉
                        jsonString = jsonString.replace(vo.getPrefix(), "");
                        List<String> reportPicList = vo.getReportPicList();
                        reportContentService.createReportContent(jsonString, 5, code, String.valueOf(param.getDh()), null, null, null, reportPicList);


                        //更新报告表
                        Integer a = report.getCreateNum();
                        if (a != null) {
                            report.setCreateNum(report.getCreateNum() + 1);
                        } else {
                            report.setCreateNum(1);
                        }
                        //设置报告地址
                        if (StringUtils.isNotBlank(vo.getReportPath())) {
                            report.setUrlPdf(vo.getReportPath());
                        }
                        report.setGenerateName(username);
                        report.setGenerateDate(new Date());
                        reportMapper.updateById(report);
                    } catch (Exception e) {
                        log.error("体检号{}生成报告失败！", code);
                        log.error("错误原因{}", e.getMessage());
                    }
                } else {
                    //其他报告
                    OtherReport ot = otherReportMapper.selectOne(new QueryWrapper<OtherReport>()
                            .eq("patientcode", code).eq("report_type", 0));
                    if (ot == null) {
                        ot = new OtherReport(code
                                , null
                                , null
                                , 1
                                , null
                                , null
                                , 0
                                , null);
                        otherReportMapper.insert(ot);
                    } else {
                        //已生成
                        ot.setCreateStatus(1);
                        otherReportMapper.updateById(ot);
                    }

                    // TODO: 2023/11/13 统一使用检验报告的模板
                    inspectReportsParam inspectReportsParam = new inspectReportsParam();
                    inspectReportsParam.setPatientCodes(patientcode);
                    inspectReportsService.create(inspectReportsParam);
                }
            }
        } else {
            throw new ServiceException("请选择体检者:");
        }
        return Boolean.TRUE;
    }
}