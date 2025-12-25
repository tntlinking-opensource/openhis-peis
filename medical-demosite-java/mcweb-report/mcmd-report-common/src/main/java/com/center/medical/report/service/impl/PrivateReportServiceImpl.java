package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.model.Report;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.dao.OtherReportMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.BaseAttachmentConfig;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.report.bean.dto.GetPrXjdataDto;
import com.center.medical.report.bean.dto.PrYsxmDto;
import com.center.medical.report.bean.vo.PrivateReportVo;
import com.center.medical.report.dao.PrivateReportMapper;
import com.center.medical.report.service.PrivateReportService;
import com.center.medical.report.utils.CreateBarcodeDefault;
import com.center.medical.report.utils.WordConvertPDF;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.service.ISysConfigService;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 隐私报告(Report)表服务实现类
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
@Slf4j
@Service("privateReportService")
@RequiredArgsConstructor
public class PrivateReportServiceImpl extends ServiceImpl<PrivateReportMapper, Report> implements PrivateReportService {

    private final PrivateReportMapper privateReportMapper;
    private final PeispatientMapper peispatientMapper;
    private final OtherReportMapper otherReportMapper;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final ItemsMapper itemsMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final ReportContentService reportContentService;

    private final ISysConfigService iSysConfigService;
    //乙肝五项收费项目id
    // 乙肝三系定性5项_CS----乙肝三系定性6项_CS---乙肝三系定性5项_YH--乙肝三系定量5项_CS---乙肝三系定性6项(GWY)--乙肝三系6项定量检测--乙肝五项定性---CY乙肝五项定性---乙肝三对检查_ZK
    public static String[] YGIDS = new String[]{"669","01414497081674669433768510067912",
            "ff8080817aae36f6017b7fd02bf5569a",
            "ff80808171b04e0d0171e8ce59732f3d",
            "ff80808194e36cfb01957dddbfb90543",
            "ff80808171b04e0d0171e8bdadfb2e75",
            "ff80808176178f2c01763579ea8226de",
            "520",
            "ff80808173713d0e01738e3eaa554768",
            "ff8080818519845501869b13f4ef2f34"};
    public final static String YGID="669";//乙肝五项收费项目id
    public final static String[] YGEIDS = new String[]{"186","187","188","189","190"};//乙肝五项检查项目id
    public final static String YGLDBID="505";//乙肝两对半id(所有体征词都属于乙肝两对半检查项目)

    /**
     * 隐私报告生成
     * @param patientcode
     * @return
     */
    @Override
    public PrivateReportVo create(String patientcode) {

        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        //健康体检状态，报告已领取状态
//        if(patient.getJktjzt()!=null&&patient.getJktjzt()==11) {
//            throw new ServiceException("该报告已领取!");
//        }
        //其他报告
        OtherReport or = otherReportMapper.selectOne(new QueryWrapper<OtherReport>()
                .eq("patientcode",patient.getPatientcode()).eq("report_type",2));
        if(or==null){
            or = new OtherReport();
            //2.隐私报告
            or.setReportType(2);
            or.setPatientcode(patientcode);
            otherReportMapper.insert(or);
        }
        //基础附件配置
        BaseAttachmentConfig config = baseAttachmentConfigService.getLatestConfig();
        String configId=config==null?null:config.getId();
        or.setConfigId(configId);
        or.setCreateStatus(1);
        //查询隐私报告数据
        PrivateReportVo vo = createPrivateReport(patient);
        //更新
        otherReportMapper.updateById(or);

        return vo;
    }

    /**
     * 查询隐私报告数据
     * @param patient
     * @return
     */
    private PrivateReportVo createPrivateReport(Peispatient patient) {
        List<Peispatientfeeitem> ygs = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                .eq("f_examinated",1)
                .eq("id_patient",patient.getPatientcode())
                .in("id_examfeeitem", YGIDS)
        );

        String modelName = ygs.size()>0?"private_report.docx":"private_report_noyg.docx";
        PrivateReportVo vo= new PrivateReportVo();
        vo.setModelName(modelName);
        //头部数据
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("patientcode", patient.getPatientcode());
        data.put("patientname", patient.getPatientname());
        data.put("sex", patient.getIdSex() == 0 ? "男" : "女");
        data.put("age", patient.getAge()==null?"":(patient.getAge().intValue()+"岁"));
        data.put("orgName", patient.getOrgName()==null?"":patient.getOrgName());
        data.put("marry", patient.getMarriage()==null?"":patient.getMarriage());
        data.put("dateregister", patient.getDateregister()==null?""
                :new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateregister()));
        data.put("archiveno", patient.getPatientarchiveno()==null?""
                :patient.getPatientarchiveno());
        String picture=peispatientPhotoService.getPicture(patient);
        //图片
        data.put("picture",picture);

        //条形码
        data.put("barcode", patient.getPatientcode());
        //生成隐私小结，正常小结中不包含隐私项目
        List<GetPrXjdataDto> xjdata = privateReportMapper.getPrXjdata(patient.getPatientcode());
        StringBuilder conclusions = new StringBuilder();
        Set<String> itemNames = new HashSet<String>();
        List<GetPrXjdataDto> ygdata = new ArrayList<GetPrXjdataDto>();
        //收费项目
        Items ygitem = itemsMapper.getInfoById(YGID);
        //收费项目打印名称
        String ygname = ygitem==null?"":ygitem.getExamfeeitemNameprn();
        List<String> specialList = getSpecialList();
        for(int i=0;i<xjdata.size();i++){
            GetPrXjdataDto osxj = xjdata.get(i);
            String itemName=osxj.getExamfeeitem()==null?null:osxj.getExamfeeitem().toString();
            if(ygname.equals(itemName)){
                ygdata.add(osxj);
                continue;
            }
            String examName=osxj.getExamItemNameR()==null?null:osxj.getExamItemNameR().toString();
            String reportValue=osxj.getExamitemvaluesreport()==null?"":osxj.getExamitemvaluesreport().toString();
            String status=(osxj.getStatus()==null||"N".equals(osxj.getStatus().toString()))?"":osxj.getStatus().toString();
            int isDesc=osxj.getIsDesc()==null?0:Integer.parseInt(osxj.getIsDesc().toString());
            String units=osxj.getUnits()==null?null:osxj.getUnits().toString();
            String examId=osxj.getIdExamitem().toString();
            if(isDesc==1
                    ||isAbnormal(status, reportValue, examId, specialList)
                    ||testPlus(reportValue)
                    ||reportValue.indexOf("阳性")!=-1){
                if(itemNames.contains(itemName)){
                    conclusions.append("、");
                }else{
                    itemNames.add(itemName);
                    if(conclusions.length()>0){
                        conclusions.append(";");
                    }
                }
                conclusions.append(examName+":"+reportValue+ ToolUtil.getUnit(units)+(("↑".equals(status)||"↓".equals(status))?status:""));
            }
        }
        for(GetPrXjdataDto osxj:ygdata){
            String itemName=osxj.getExamfeeitem()==null?null:osxj.getExamfeeitem().toString();
            String examName=osxj.getExamItemNameR()==null?null:osxj.getExamItemNameR().toString();
            String reportValue=osxj.getExamitemvaluesreport()==null?"":osxj.getExamitemvaluesreport().toString();
            String status=(osxj.getStatus()==null||(!"↑".equals(osxj.getStatus().toString())&&!"↓".equals(osxj.getStatus().toString())))?"":osxj.getStatus().toString();
            int isDesc=osxj.getType2()==null?0:Integer.parseInt(osxj.getType2().toString());
            String units=osxj.getUnits()==null?null:osxj.getUnits().toString();
            String examId=osxj.getIdExamitem().toString();
            if(isDesc==1
                    ||isAbnormal(status, reportValue, examId, specialList)
                    ||testPlus(reportValue)
                    ||reportValue.indexOf("阳性")!=-1){
                if(itemNames.contains(itemName)){
                    conclusions.append("、");
                }else{
                    itemNames.add(itemName);
                    if(conclusions.length()>0){
                        conclusions.append(";");
                    }
                }
                conclusions.append(examName+":"+reportValue+ToolUtil.getUnit(units)+(("↑".equals(status)||"↓".equals(status))?status:""));
            }
        }
        SectionResultTwo old = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode",patient.getPatientcode()).eq("verdict_id",YGLDBID));
        //检查描述
        String yg_describe=old==null?"":old.getMs();
        if(yg_describe.length()>0){
            if(itemNames.contains(ygname)){
                conclusions.append("、");
            }else{
                conclusions.append(";");
            }
            conclusions.append(yg_describe);
        }
        String con=conclusions.toString();
        data.put("conclusions",conclusions.length()==0?"未见异常":con.endsWith(";")?con:(con+";"));
        vo.setHead(data);


        //表格数据
        //获取隐私项目
        List<PrYsxmDto> list = privateReportMapper.getPrYsxm(patient.getPatientcode());
        if(list.size()>0) {
            List<Map<String,Object>> examdata=new ArrayList<Map<String,Object>>();
            Map<String, Object> itemMap = null;
            String fItemName = null;
            Collection<Map<String, String>> examCol = null;
            for (int i = 0; i < list.size(); i++) {
                PrYsxmDto os = list.get(i);
                String itemName = os.getExamfeeitemNameprn() != null ? String.valueOf(os.getExamfeeitemNameprn())
                        : "";
                if (!itemName.equals(fItemName)) {
                    itemMap = new HashMap<String, Object>();
                    itemMap.put("itemName", itemName);
                    itemMap.put(
                            "auditDate",
                            os.getAuditDate() != null ?os.getAuditDate() : "");
                    itemMap.put(
                            "auditName",
                            os.getInspectName() != null ? String
                                    .valueOf(os.getInspectName()) : "");
                    examCol = new Vector<Map<String, String>>();
                    itemMap.put("exams", examCol);
                    examdata.add(itemMap);
                    fItemName = itemName;
                }

                Map<String, String> examMap = new HashMap<String, String>();
                examMap.put("ITEM",
                        os.getExamitemNameprn() == null ? "" : os.getExamitemNameprn().toString());
                examMap.put("RESULT",
                        os.getExamitemvaluesreport() == null ? "" : os.getExamitemvaluesreport().toString());
                examMap.put("SIGN",
                        os.getUnits() == null ? "" : os.getUnits().toString());
                examMap.put("CONSULT",
                        os.getStatus() == null ? "" : os.getStatus().toString());
                examMap.put("UNIT",
                        os.getRefrange() == null ? "" : os.getRefrange().toString());
                examCol.add(examMap);
            }
            vo.setExamdata(examdata);
        }
        return vo;
    }


    /**
     * 特殊检查项目
     * @return
     */
    public List<String> getSpecialList(){
        String special_inspection_exam_items = Constants.SPECIAL_INSPECTION_EXAM_ITEMS;
        return StringUtils.isEmpty(special_inspection_exam_items)?
                new ArrayList<String>()
                :Arrays.asList(special_inspection_exam_items.split(","));
    }

    public boolean isAbnormal(String status,String result,String examId,List<String> arrss) {
        if("↑".equals(status)||"↓".equals(status)||"阳性".equals(status)) {
            return true;
        }
        return isSpecialNormal(result, examId, arrss);
    }

    public boolean isSpecialNormal(String result,String examId,List<String> arrss) {
        if(StringUtils.isNotEmpty(result)&&arrss.contains(examId)) {
            return !("未见".equals(result)||"阴性".equals(result));
        }
        return false;
    }

    public boolean testPlus(String str) {
        return str.replaceAll("[Ee]\\+","").indexOf("+")!=-1;
    }


    /**
     * 生成隐私报告pdf
     * @param patientcode
     * @return
     */
    @Override
    public String createPdf(String patientcode) throws IOException {
        String local = System.getProperty("user.dir") + "/temp/" ;
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();

        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        List<Peispatientfeeitem> ygs = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                .eq("f_examinated",1)
                .eq("id_patient",patient.getPatientcode())
                .in("id_examfeeitem", YGIDS)
        );

        String modelName=ygs.size()>0?"private_report.docx":"private_report_noyg.docx";

        String modelPath2 = local + "file/wordmodel/private_report_info.docx";
        String modelPath1 = local + "file/wordmodel/" + modelName;
        log.info("打印一下modelPath1:" + modelPath1);
        String dir = "file"+File.separator+"wordexp"+File.separator+"private"+File.separator
                +new SimpleDateFormat("yyyyMMdd").format(new Date())
                +File.separator+patientcode;
        log.info("打印一下dir:" + dir);
        String orgDepartsubb = dir
                +File.separator+ new SimpleDateFormat("HH_mm_ss_SSS").format(new Date());
        log.info("打印一下orgDepartsubb:" + orgDepartsubb);
        String wordPath = local + orgDepartsubb+".docx";
        String pdfPath = local + orgDepartsubb+".pdf";
        File folder = new File(local + orgDepartsubb);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        PrivateReportVo vo = new PrivateReportVo();
        vo.setModelName(modelName);
        //头部数据
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("patientcode", patient.getPatientcode());
        data.put("patientname", patient.getPatientname());
        data.put("sex", patient.getIdSex() == 0 ? "男" : "女");
        data.put("age", patient.getAge()==null?"":(patient.getAge().intValue()+"岁"));
        data.put("orgName", patient.getOrgName()==null?"":patient.getOrgName());
        data.put("marry", patient.getMarriage()==null?"":patient.getMarriage());
        data.put("dateregister", patient.getDateregister()==null?""
                :new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateregister()));
        data.put("archiveno", patient.getPatientarchiveno()==null?""
                :patient.getPatientarchiveno());
        String picture=peispatientPhotoService.getPicture(patient);
        //图片
//        data.put("picture",picture);
        if(StringUtils.isNotBlank(picture)) {
            URL url = new URL(prefix + picture);
            BufferedImage read = ImageIO.read(url);
            data.put("picture"
                    , new PictureRenderData(100, 120, ".png", read));
        }


        //条形码
        data.put("barcode", patient.getPatientcode());
        byte[] barcode= CreateBarcodeDefault.createBarcodeDefault(patient.getPatientcode());
        data.put("barcode"
                , new PictureRenderData(200, 50, ".png", barcode));

        //生成隐私小结，正常小结中不包含隐私项目
        List<GetPrXjdataDto> xjdata = privateReportMapper.getPrXjdata(patient.getPatientcode());
        StringBuilder conclusions = new StringBuilder();
        Set<String> itemNames = new HashSet<String>();
        List<GetPrXjdataDto> ygdata = new ArrayList<GetPrXjdataDto>();
        //收费项目
        Items ygitem = itemsMapper.getInfoById(YGID);
        //收费项目打印名称
        String ygname = ygitem==null?"":ygitem.getExamfeeitemNameprn();
        List<String> specialList = getSpecialList();
        for(int i=0;i<xjdata.size();i++){
            GetPrXjdataDto osxj = xjdata.get(i);
            String itemName=osxj.getExamfeeitem()==null?null:osxj.getExamfeeitem().toString();
            if(ygname.equals(itemName)){
                ygdata.add(osxj);
                continue;
            }
            String examName=osxj.getExamItemNameR()==null?null:osxj.getExamItemNameR().toString();
            String reportValue=osxj.getExamitemvaluesreport()==null?"":osxj.getExamitemvaluesreport().toString();
            String status=(osxj.getStatus()==null||"N".equals(osxj.getStatus().toString()))?"":osxj.getStatus().toString();
            int isDesc=osxj.getIsDesc()==null?0:Integer.parseInt(osxj.getIsDesc().toString());
            String units=osxj.getUnits()==null?null:osxj.getUnits().toString();
            String examId=osxj.getIdExamitem().toString();
            if(isDesc==1
                    ||isAbnormal(status, reportValue, examId, specialList)
                    ||testPlus(reportValue)
                    ||reportValue.indexOf("阳性")!=-1){
                if(itemNames.contains(itemName)){
                    conclusions.append("、");
                }else{
                    itemNames.add(itemName);
                    if(conclusions.length()>0){
                        conclusions.append(";");
                    }
                }
                conclusions.append(examName+":"+reportValue+ ToolUtil.getUnit(units)+(("↑".equals(status)||"↓".equals(status))?status:""));
            }
        }
        for(GetPrXjdataDto osxj:ygdata){
            String itemName=osxj.getExamfeeitem()==null?null:osxj.getExamfeeitem().toString();
            String examName=osxj.getExamItemNameR()==null?null:osxj.getExamItemNameR().toString();
            String reportValue=osxj.getExamitemvaluesreport()==null?"":osxj.getExamitemvaluesreport().toString();
            String status=(osxj.getStatus()==null||(!"↑".equals(osxj.getStatus().toString())&&!"↓".equals(osxj.getStatus().toString())))?"":osxj.getStatus().toString();
            int isDesc=osxj.getType2()==null?0:Integer.parseInt(osxj.getType2().toString());
            String units=osxj.getUnits()==null?null:osxj.getUnits().toString();
            String examId=osxj.getIdExamitem().toString();
            if(isDesc==1
                    ||isAbnormal(status, reportValue, examId, specialList)
                    ||testPlus(reportValue)
                    ||reportValue.indexOf("阳性")!=-1){
                if(itemNames.contains(itemName)){
                    conclusions.append("、");
                }else{
                    itemNames.add(itemName);
                    if(conclusions.length()>0){
                        conclusions.append(";");
                    }
                }
                conclusions.append(examName+":"+reportValue+ToolUtil.getUnit(units)+(("↑".equals(status)||"↓".equals(status))?status:""));
            }
        }
        SectionResultTwo old = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode",patient.getPatientcode()).eq("verdict_id",YGLDBID));
        //检查描述
        String yg_describe=old==null?"":old.getMs();
        if(yg_describe.length()>0){
            if(itemNames.contains(ygname)){
                conclusions.append("、");
            }else{
                conclusions.append(";");
            }
            conclusions.append(yg_describe);
        }
        String con=conclusions.toString();
        data.put("conclusions",conclusions.length()==0?"未见异常":con.endsWith(";")?con:(con+";"));
        vo.setHead(data);


        //表格数据
        //获取隐私项目
        List<PrYsxmDto> list = privateReportMapper.getPrYsxm(patient.getPatientcode());
        if(list.size()>0) {
            List<Map<String,Object>> examdata=new ArrayList<Map<String,Object>>();
            Map<String, Object> itemMap = null;
            String fItemName = null;
            Collection<Map<String, String>> examCol = null;
            for (int i = 0; i < list.size(); i++) {
                PrYsxmDto os = list.get(i);
                String itemName = os.getExamfeeitemNameprn() != null ? String.valueOf(os.getExamfeeitemNameprn())
                        : "";
                if (!itemName.equals(fItemName)) {
                    itemMap = new HashMap<String, Object>();
                    itemMap.put("itemName", itemName);
                    itemMap.put(
                            "auditDate",
                            os.getAuditDate() != null ?os.getAuditDate() : "");
                    itemMap.put(
                            "auditName",
                            os.getInspectName() != null ? String
                                    .valueOf(os.getInspectName()) : "");
                    examCol = new Vector<Map<String, String>>();
                    itemMap.put("exams", examCol);
                    examdata.add(itemMap);
                    fItemName = itemName;
                }

                Map<String, String> examMap = new HashMap<String, String>();
                examMap.put("ITEM",
                        os.getExamitemNameprn() == null ? "" : os.getExamitemNameprn().toString());
                examMap.put("RESULT",
                        os.getExamitemvaluesreport() == null ? "" : os.getExamitemvaluesreport().toString());
                examMap.put("SIGN",
                        os.getUnits() == null ? "" : os.getUnits().toString());
                examMap.put("CONSULT",
                        os.getStatus() == null ? "" : os.getStatus().toString());
                examMap.put("UNIT",
                        os.getRefrange() == null ? "" : os.getRefrange().toString());
                examCol.add(examMap);
            }
            vo.setExamdata(examdata);

            for(Map<String,Object> map:examdata) {
                Vector<Map<String, String>> vec
                        =(Vector<Map<String, String>>) map.get("exams");
                List<RowRenderData> bodydata=new ArrayList<RowRenderData>();
                for(Map<String, String> vmap:vec) {
                    String consult=vmap.get("CONSULT");
                    String result=vmap.get("RESULT");
                    RowRenderData r = RowRenderData.build(
                            vmap.get("ITEM")
                            ,vmap.get("RESULT")
                            ,vmap.get("SIGN")
                            ,vmap.get("CONSULT")
                            ,vmap.get("UNIT"));
                    if(consult!=null
                            &&("↑".equals(consult)
                            ||"↓".equals(consult)
                            ||"阳性".equals(consult)
                            ||testPlus(result)
                            ||result.indexOf("阳性")!=-1)){
                        r.getRowStyle().setBackgroundColor("FFDAB9");
                    }
                    bodydata.add(r);
                }
                map.put("exams", new MiniTableRenderData(null, bodydata));
            }

            data.put("examdata",new DocxRenderData(new File(modelPath2), examdata) );
        }else {

        }



        XWPFTemplate template = XWPFTemplate.compile(modelPath1).render(data);
        try(FileOutputStream out = new FileOutputStream(wordPath);){
            template.write(out);
            out.flush();
        }
        template.close();
        WordConvertPDF conver = new WordConvertPDF();
        conver.wordToPDF(wordPath,pdfPath);
        log.info("隐私报告生成成功，地址是:"+pdfPath);
        //删除临时word文件
        File wordFile = new File(wordPath);
        wordFile.delete();
        return pdfPath;
    }
}

