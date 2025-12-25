package com.center.medical.pacs.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.AttachmentConfigMapper;
import com.center.medical.dao.AttachmentMapper;
import com.center.medical.dao.PacsResultMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.pacs.bean.param.CreateRadParam;
import com.center.medical.pacs.dao.PacsReportMapper;
import com.center.medical.pacs.service.PacsReportService;
import com.center.medical.pacslis.bean.model.PacsBasePart;
import com.center.medical.pacslis.dao.PacsBasePartMapper;
import com.center.medical.pacslis.dao.PacsPeispatientMapper;
import com.center.medical.pacslis.service.PacsBasePartService;
import com.center.medical.report.bean.model.ReportUrl;
import com.center.medical.report.service.ReportUrlService;
import com.center.medical.service.PacsItemsService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * PACS-数据(PacsResult)表服务实现类
 *
 * @author ay
 * @since 2023-05-19 15:22:04
 */
@Slf4j
@Service("pacsReportService")
@RequiredArgsConstructor
public class PacsReportServiceImpl extends ServiceImpl<PacsReportMapper, PacsResult> implements PacsReportService {

    private final PacsReportMapper pacsReportMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PacsPeispatientMapper pacsPeispatientMapper;
    private final PeispatientMapper peispatientMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final SysUserMapper sysUserMapper;
    private final PacsResultMapper pacsResultMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentConfigMapper attachmentConfigMapper;
    private final PacsItemsService pacsItemsService;
    private final PacsBasePartService pacsBasePartService;
    private final ItemsMapper itemsMapper;
    private final PacsBasePartMapper pacsBasePartMapper;
    private final ReportContentService reportContentService;
    private final ReportUrlService reportUrlService;


    private final static String FORMAT = "yyyy'年'MM'月'dd'日 'HH:mm";


    /**
     * 生成放射科室报告
     *
     * @param param
     * @return
     */
    @Override
    public Boolean createRad(CreateRadParam param) {
        //取出属性
        String patientcode = param.getPatientcode();
        Integer source = param.getSource();
        String ksId = param.getKsId();
        Integer type = param.getType();
        String addpic = param.getAddpic();
        String tech = param.getTech();

        Map<String, Object> datas = new HashMap<String, Object>();
        SysDept dept = sysDeptMapper.getByDeptNo(ksId);
        String dep = dept.getDeptName();
        datas.put("dep", dep);
        if (source == 0) {
            //新pacs系统
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            datas.put("patientname", patient.getPatientname());
            datas.put("patientcode", patientcode);
            datas.put("sex", patient.getIdSex() == 0 ? "男" : "女");
            datas.put("age", patient.getAge() == null ? "" : patient.getAge());
            datas.put("idExamtype", patient.getIdExamtype());
        } else if (source == 1) {
            //体检软件生成彩超报告
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            datas.put("patientname", patient.getPatientname());
            datas.put("patientcode", patientcode);
            datas.put("sex", patient.getIdSex() == 0 ? "男" : "女");
            datas.put("age", patient.getAge() == null ? "" : patient.getAge());
            datas.put("idExamtype", patient.getIdExamtype());
            if (source == 1) {
                //体检软件需要显示医生名字
                SectionResultMain tjmain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode).eq("dep_id", ksId));
                if (tjmain != null) {
                    //审核人姓名
                    String examDoctor = tjmain.getAuditName();//username
                    if (examDoctor != null) {
                        SysUser user = sysUserMapper.getUserByUserName(examDoctor);
                        if (user != null) {
                            datas.put("rn", user.getUserName());
                            datas.put("an", user.getUserName());
                        }
                    }
                }
            }
        }

        datas.put("kb", "");
        datas.put("djh", "");

        List<PacsResult> prs = pacsResultMapper.selectList(new QueryWrapper<PacsResult>()
                .eq("patientcode", patientcode).eq("dep_id", ksId));
        StringBuilder parts = type == 1 ? new StringBuilder("检查部位、方法、技术：") : new StringBuilder("检查部位：");
        int orgLen = parts.length();
        if (prs.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);

            datas.put("examdate", sdf.format(prs.get(0).getExamdatetime()));
            datas.put("auditdate", sdf.format(prs.get(0).getExamdatetime()));

            List<Object> content = new ArrayList<Object>();
            if ("true".equals(addpic)) {
                //检查附件
                List<Attachment> atts = null;
                if (type == 0) {
                    atts = attachmentMapper.selectList(new QueryWrapper<Attachment>()
                            .orderByAsc("createdate")
                            .eq("patientcode", patientcode)
                            .eq("dep_id", ksId)
                            .eq("in_Report", 1));//是否进报告
                } else {
                    atts = attachmentMapper.selectList(new QueryWrapper<Attachment>()
                            .eq("patientcode", patientcode)
                            .eq("dep_id", ksId));
                }
                if (atts.size() > 0) {
                    List<String> prds = new ArrayList<>();
                    for (int i = 0; i < atts.size(); i++) {
                        //图片地址
                        prds.add(getPath(atts.get(i)));
                    }
                    datas.put("pic", prds);
                }

            }


            /**描述小结*/
            StringBuilder desTitle = new StringBuilder();
            desTitle.append(dep + "所见：");
            List<Object> dess = new ArrayList<Object>();
            dess.add(desTitle);

            List<Object> cons = new ArrayList<Object>();
            StringBuilder conTitle = new StringBuilder();
            conTitle.append(dep + "表现意见：");
            cons.add(conTitle);
            if (prs.size() == 1) {
                PacsResult p = prs.get(0);
                String partName = source == 0 ? getPartName(p.getPacsItemId()) : getPartNameByTj(p.getItemId());
                if (partName != null) {
                    parts.append(partName);
                }
                //检查结果描述
                dess.add(new StringBuilder(p.getExamresultdesc()));
                //检查结果总结
                cons.add(new StringBuilder(p.getExamresultsummary()));
            } else {
                if (type == 0) {//彩超不会录入部位，需要重新设置编号
                    Set<String> check = new HashSet<String>();
                    int d = 1;
                    for (int i = 0; i < prs.size(); i++) {
                        PacsResult p = prs.get(i);
                        String partName = source == 0 ? getPartName(p.getPacsItemId()) : getPartNameByTj(p.getItemId());
                        if (partName != null) {
                            if (!check.contains(partName)) {
                                parts.append(partName + "、");
                                check.add(partName);
                            }
                        }
                        dess.add(new StringBuilder((partName == null ? "" : (partName + "：")) + p.getExamresultdesc()));
                        Object[] os = getSummary(p.getExamresultsummary(), d);
                        d = (int) os[0];
                        cons.add(new StringBuilder(String.valueOf(os[1])));
                    }
                } else {
                    Set<String> check = new HashSet<String>();
                    for (int i = 0; i < prs.size(); i++) {
                        PacsResult p = prs.get(i);
                        String partName = source == 0 ? getPartName(p.getPacsItemId()) : getPartNameByTj(p.getItemId());
                        if (partName != null) {
                            if (!check.contains(partName)) {
                                parts.append(partName + "、");
                                check.add(partName);
                            }
                        }
                        dess.add(new StringBuilder((partName == null ? "" : (partName + "：")) + p.getExamresultdesc()));
                        cons.add(new StringBuilder((partName == null ? "" : (partName + "：")) + p.getExamresultsummary()));
                    }
                }
            }

            content.addAll(dess);
            content.addAll(cons);
            datas.put("content", content);

        } else {
            List<Object> li = new ArrayList<Object>();

            StringBuilder desTitle = new StringBuilder(dep + "所见：");
            li.add(desTitle);

            StringBuilder conTitle = new StringBuilder(dep + "表现意见：");
            li.add(conTitle);
            datas.put("content", li);

        }

        if (parts.length() > orgLen) {
            String p = parts.toString();
            if (StringUtils.isNotEmpty(tech)) {
                tech = tech.replaceAll(",", "、");
                datas.put("parts", p.endsWith("、") ? (p + tech) : (p + "、" + tech));
            } else {
                datas.put("parts", p.endsWith("、") ? parts.substring(0, parts.length() - 1) : (p));
            }
        } else {
            datas.put("parts", parts + (tech == null ? "" : tech.replaceAll(",", "、")));
        }

        //保存报告路径
        saveOrUpdate(patientcode, ksId, null, null, null);

        //生成的报告,插入到报告内容表中
        String jsonString = JSON.toJSONString(datas);
        reportContentService.createReportContent(jsonString, 7, patientcode, String.valueOf(datas.get("idExamtype")), null, null, ksId, null);
        return Boolean.TRUE;
    }


    /**
     * 保存
     *
     * @param patientcode
     * @param ksId
     * @param wordUrl
     * @param pdfUrl
     * @param creator
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(String patientcode, String ksId, String wordUrl, String pdfUrl, String creator) {
        ReportUrl url = reportUrlService.getOne(new QueryWrapper<ReportUrl>()
                .eq("patientcode", patientcode).eq("dep_id", ksId));
        if (ObjectUtils.isEmpty(url)) {
            url = new ReportUrl();
            url.setDepId(ksId);
            url.setPatientcode(patientcode);
            url.setIsHead(1);
        }
        url.setWordUrlHead(wordUrl);
        url.setCreator(creator);
        url.setPdfUrlHead(pdfUrl);
        url.setConfigId(ObjectUtils.isNotEmpty(getLatestConfig()) ? getLatestConfig().getId() : null);
        reportUrlService.saveOrUpdate(url);
        return url.getConfigId();
    }


    public String getPath(Attachment att) {
        String configId = att.getConfigId();
        AttachmentConfig config = configId == null ? getLatestConfig() : attachmentConfigMapper.getInfoById(configId);
        return config.getMappingPath() + "/" + att.getFilePath();
    }


    public AttachmentConfig getLatestConfig() {
        List<AttachmentConfig> pacs = attachmentConfigMapper.selectList(new QueryWrapper<AttachmentConfig>().orderByDesc("flag"));
        return pacs.size() == 0 ? null : pacs.get(0);
    }


    /**
     * 获取收费项目部位名称
     *
     * @param pacsItemId
     * @return
     */
    public String getPartName(String pacsItemId) {
        PacsItems pi = pacsItemsService.getInfoById(pacsItemId);
        //部位IDs
        String partId = pi.getExamfeeitemCodehm();
        if (partId != null) {
            //PACS-部位
            List<PacsBasePart> parts = pacsBasePartService.list(new QueryWrapper<PacsBasePart>().in("id", partId.split(",")));
            Set<String> partNames = new HashSet<String>();
            for (PacsBasePart part : parts) {
                //部位名称
                partNames.add(part.getPartName());
            }
            return Render.join(partNames, "、");
        } else {
            return null;
        }
    }


    /**
     * 用体检软件收费项目ID获取收费项目部位名称
     *
     * @param itemId
     * @return
     */
    public String getPartNameByTj(String itemId) {
        Items items = itemsMapper.getInfoById(itemId);
        //收费项目代码为空
        if (items == null || items.getExamfeeitemCode() == null) {
            return null;
        }
        //PACS-收费项目
        PacsItems pi = pacsItemsService.getOne(new QueryWrapper<PacsItems>()
                .eq("examfeeitem_code", items.getExamfeeitemCode()));
        if (pi == null) {
            return null;
        }
        String partId = pi.getExamfeeitemCodehm();
        if (partId != null) {
            //PACS-部位
            List<PacsBasePart> parts = pacsBasePartMapper.selectList(new QueryWrapper<PacsBasePart>().in("id", partId.split(",")));
            Set<String> partNames = new HashSet<String>();
            for (PacsBasePart part : parts) {
                //部位名称
                partNames.add(part.getPartName());
            }
            return Render.join(partNames, "、");
        } else {
            return null;
        }
    }

    public Object[] getSummary(String summary, int d) {
        Pattern pattern = Pattern.compile("\\d+、");
        String[] arr = pattern.split(summary);
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            builder.append((d++) + "、" + str);
        }
        return new Object[]{d, builder.toString()};
    }


}

