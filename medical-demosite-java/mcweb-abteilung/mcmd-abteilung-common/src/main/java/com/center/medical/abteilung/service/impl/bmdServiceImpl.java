package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.data.dao.*;
import com.center.medical.abteilung.bean.dto.BmdJsonDataDto;
import com.center.medical.abteilung.bean.dto.DmdGriddata;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.DmbShenHeParam;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.dao.bmdMapper;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.bmdService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Describe;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.BaseAttachmentConfig;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KS科室检查结果主表(SectionResultMain)表服务实现类
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@Service("bmdService")
@RequiredArgsConstructor
public class bmdServiceImpl extends ServiceImpl<bmdMapper, SectionResultMain> implements bmdService {

    private final static String tz = "886";//检查项目 T值Id
    private final static String gmdzd = "887";//骨密度诊断ID
    private final static String zc = "3913";//骨密度正常
    private final static String gzss = "5592";//骨质疏松
    private final static String gljs = "3915";//骨量减少

    private final bmdMapper bmdMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PatienttypeService patienttypeService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final DivisionService divisionService;
    private final SysUserMapper sysUserMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final BaseAttachmentConfigMapper baseAttachmentConfigMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final DescribeMapper describeMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ItemsMapper itemsMapper;
    private final RiskclientMapper riskclientMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final PeispatientfeeitemService peispatientfeeitemService;

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultMain> getList(PageParam<SectionResultMain> page, SectionResultMain param) {
        return bmdMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultMain getInfoById(String id) {
        return bmdMapper.getInfoById(id);
    }

    ;

    /**
     * 读卡
     *
     * @param patientcode
     * @return
     */
    @Override
    public Map<String, Object> bmd(String patientcode, String gridSeclect, String autoFill, String ksID) {
        //存放返回数据map
        Map<String, Object> data = new HashMap<>();
        //初始化
        String create_url = Constants.CREATE_IP;
        String lrrId = SecurityUtils.getUserNo();
        String lrr = SecurityUtils.getUsername();
        Date lrsj = new Date();
        data.put("create_url", create_url);
        data.put("lrrId", lrrId);
        data.put("lrr", lrr);
        data.put("lrsj", lrsj);
        String flag = "";

        String gmd_high = Constants.GMD_HIGH;
        String gmd_low = Constants.GMD_LOW;
        data.put("gmd_high", gmd_high);
        data.put("gmd_low", gmd_low);

        if (StringUtils.isNotBlank(patientcode)) {
            if (gridSeclect == null || "false".equals(gridSeclect)) {
                if (autoFill != null && "true".equals(autoFill)) {
                    //体检号补0
                    patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
                } else {
                    patientcode = patientcode.trim().toUpperCase();
                }
            }
            //体检者表
            Peispatient user = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", patientcode).eq("f_registered", 1));
            if (user == null) {
                throw new ServiceException("该体检号尚未登记!");
            }
            data.put("peispatient", user);
            String picture = peispatientPhotoService.getPicture(user);
            if (user.getFPaused() != null && user.getFPaused().intValue() == 1) {
                throw new ServiceException("该体检号已被禁检!");
            }
            data.put("picture", picture);
            //任务分组ID
            String idOrgreservationgroup = user.getIdOrgreservationgroup();
            if (idOrgreservationgroup != null) {
                //体检者任务分组
                Peisorgreservationgroup org = peisorgreservationgroupMapper.getInfoById(idOrgreservationgroup);
                if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                    //组禁用
                    throw new ServiceException("该体检号已被禁检!");
                }
            }
            String isVIP = patienttypeService.getIdPatientClass(user);
            data.put("isVIP", isVIP);
            //体检者收费项目表
            List<Peispatientfeeitem> list1 = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode)
                    .eq("id_ks", ksID)
                    .isNull("f_transferedhl7")
                    .eq("sfjj", 0)
                    .eq("f_giveup", 0)
                    .eq("change_item", 0));
            if (list1 != null && list1.size() > 0) {
                boolean charged = false;// 判断是否有已缴费的费用项目
                boolean paid = true;// 是否全部缴费
                StringBuilder unpaid = new StringBuilder();
                List<Peispatientfeeitem> tjzsfxm = new ArrayList<Peispatientfeeitem>();
                for (Peispatientfeeitem feeitem : list1) {
                    if (feeitem.getFFeecharged() != null && feeitem.getFFeecharged() == 1) {
                        charged = true;
                        tjzsfxm.add(feeitem);
                    } else {
                        paid = false;
                        if (unpaid.length() == 0) {
                            unpaid.append("该体检号存在未缴费的费用项目：" + feeitem.getExamfeeitemName());
                        } else {
                            unpaid.append("," + feeitem.getExamfeeitemName());
                        }
                    }
                }
                if (charged) {
                    if (!paid) {
                        flag = unpaid.toString();
                    }
                    //科室检查结果主表
                    SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                            .eq("patientcode", patientcode).eq("dep_id", ksID));
                    if (main != null && main.getIsAudit() != null && main.getIsAudit() == 1) {
                        flag = "audit";// 已审核 不能修改
                    }
                    Map<String, Object> bmdData = getDmbData();
                    List<HashMap<String, String>> griddata = divisionService.jlcData(patientcode, ksID);
                    data.put("bmdData", bmdData);
                    data.put("griddata", griddata);
                } else {
                    flag = "该体检号尚未缴费!";
                }
            } else {
                flag = "该体检号没有本科室收费项目!";
            }
        }
        data.put("flag", flag);
        return data;
    }


    public Map<String, Object> getDmbData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("tz", basexamltemMapper.getInfoById(tz));
        data.put("gmdzd", basexamltemMapper.getInfoById(gmdzd));
        data.put("zc", basexamltemSignMapper.getInfoById(zc));
        BasexamltemSign gzss_map = basexamltemSignMapper.getInfoById(gzss);
        //结论词ID
        gzss_map.setName(basconclusionMapper.getInfoById(gzss_map.getResultId()).getName());
        data.put("gzss", gzss_map);
        BasexamltemSign gljs_map = basexamltemSignMapper.getInfoById(gljs);
        gljs_map.setName(basconclusionMapper.getInfoById(gljs_map.getResultId()).getName());
        data.put("gljs", gljs_map);
        //z值检查项目id
        String zz = Constants.ZZ_EXAM_ID;
        if (StringUtils.isNotEmpty(zz)) {
            data.put("zz", basexamltemMapper.getInfoById(zz));
        }
        return data;
    }


    /**
     * 获取骨密度审核数据
     *
     * @param patientcode
     * @param ksId
     * @return
     */
    @Override
    public Object getBmdCheckedData(String patientcode, String ksId) {
        Map<String, Object> result = new HashMap<String, Object>();
        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientcode).eq("dep_id", ksId));
        BufferedReader br = null;
        if (main != null) {
            result.put("main", main);
            //检查人ID
            String rummagerId = main.getRummagerId();
            if (rummagerId != null) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(rummagerId);
                if (rummager != null) {
                    //监察人姓名
                    result.put("rummager", rummager.getUserName());
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //检查时间
            result.put("rummagerTime", main.getRummagerTime());
            //录入人ID
            String lrr = main.getWriteId();
            result.put("lrrId", lrr);
            String lrrName = null;
            if (lrr != null) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(lrr);
                if (rummager != null) {
                    //录入人名称
                    lrrName = rummager.getUserName();
                }
            }
            result.put("writeTime", main.getWriteTime());
            result.put("lrr", lrrName);
            //科室检查结果表
            SectionResultTwo tz_two = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("main_id", main.getId())
                    .eq("verdict_id", tz));
            if (ObjectUtils.isNotEmpty(tz_two)) {
                Map<String, String> tz_map = new HashMap<String, String>();
                tz_map.put("inputResult", tz_two.getInputResult());
                tz_map.put("isUnchecked", tz_two.getIsUnchecked() == null ? null : tz_two.getIsUnchecked().toString());
                tz_map.put("isDanger", tz_two.getIsDanger() == null ? null : tz_two.getIsDanger().toString());
                tz_map.put("ms", tz_two.getMs());
                result.put("tz", tz_map);
            }

            String zz = Constants.ZZ_EXAM_ID;
            if (StringUtils.isNotEmpty(zz)) {
                SectionResultTwo zz_two = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("main_id", main.getId())
                        .eq("verdict_id", zz));
                if (zz_two != null) {
                    Map<String, String> zz_map = new HashMap<String, String>();
                    zz_map.put("inputResult", zz_two.getInputResult());
                    zz_map.put("isUnchecked", zz_two.getIsUnchecked() == null ? null : zz_two.getIsUnchecked().toString());
                    zz_map.put("isDanger", zz_two.getIsDanger() == null ? null : zz_two.getIsDanger().toString());
                    zz_map.put("ms", zz_two.getMs());
                    result.put("zz", zz_map);
                }
            }
            //骨密度诊断ID
            SectionResultTwo gmdzd_two = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("main_id", main.getId())
                    .eq("verdict_id", gmdzd));
            if (gmdzd_two != null) {
                Map<String, String> gmdzd_map = new HashMap<String, String>();
                gmdzd_map.put("ms", gmdzd_two.getMs());
                gmdzd_map.put("nodule", gmdzd_two.getNodule());
                result.put("gmdzd", gmdzd_map);
            }

        }
        return result;
    }

    /**
     * 获取地址
     *
     * @param att
     * @return
     */
    @Override
    public String getPath(Attachment att) {
        String configId = att.getConfigId();
        BaseAttachmentConfig config = configId == null ? getLatestConfig() : baseAttachmentConfigMapper.getInfoById(configId);
        return config.getVisitPath() + "/" + att.getFilePath();
    }

    public BaseAttachmentConfig getLatestConfig() {
        List<BaseAttachmentConfig> pacs = baseAttachmentConfigMapper.selectList(new QueryWrapper<BaseAttachmentConfig>().orderByDesc("flag"));
        return pacs.size() == 0 ? null : pacs.get(0);
    }

    /**
     * 骨密度审核
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean dmbshenhe(DmbShenHeParam param) {
        //取出属性
        String patientCode = param.getPatientCode();
        String ksID = param.getKsID();
        Date jcsj = param.getJcsj();
        String xjdata = param.getXjdata();
        String jcr = param.getJcr();
        String tz_ms = param.getTz_ms();
        String zz_ms = param.getZz_ms();
        String gmdzd_ms = param.getGmdzd_ms();
        Date lrsj = param.getLrsj();
        List<DmdGriddata> griddata = param.getGriddata();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<BmdJsonDataDto> jsondata = param.getJsondata();
        //key及value放进map中
        Map<String, String> examData = new HashMap<>();
        if (CollectionUtils.isNotEmpty(jsondata)) {
            for (BmdJsonDataDto dto : jsondata) {
                examData.put(dto.getKey(), dto.getValue());
            }
        }
        int wjz = StringUtils.isEmpty(examData.get(tz + "wjz")) ? 0 : Integer.parseInt(examData.get(tz + "wjz"));
        int qj = StringUtils.isEmpty(examData.get(tz + "qj")) ? 0 : Integer.parseInt(examData.get(tz + "qj"));
        //体检者表
        Peispatient gwry = peispatientMapper.getByPatientCode(patientCode);//体检者
        if (gwry == null || gwry.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (gwry.getJktjzt() != null && gwry.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getDoctorfinalNameR() == null ? "" : gwry.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getZytjzt() != null && gwry.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getPatientnameencoded() == null ? "" : gwry.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getFFinallocked() != null && gwry.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getIdDoctorapply() == null ? "" : gwry.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getIdGuidenurse() != null && gwry.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getParsedassignedsuiteandfi() == null ? "" : gwry.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        //@sqlOrder
        gwry.setModifydate(new Date());

        //体检者收费项目表
        Peispatientfeeitem user = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksID)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0));
        //0：未检；1：已检；
        user.setFExaminated(1);
        //分科检查时间
        Date rummagerTime = jcsj;
        user.setExaminatetime(rummagerTime);

        //体检类型
        String tjlx = gwry.getIdExamtype();
        Integer shortCode = gwry.getShortCode();
        //key:检查项目ID value：ComboExamItem
        Map<String, Comboexamitem> ceis = null;
        String jhys = gwry.getJhys();//接害因素
        String medicaltype = gwry.getMedicaltype();//职业检查类型
        if ("2".equals(tjlx)) {//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type", medicaltype));
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                //检查项目Id,实体类
                ceis.put(cei.getExamId(), cei);
            }
        }

        String signId = null;
        if ("true".equals(examData.get(zc))) {
            signId = zc;
        } else if ("true".equals(examData.get(gljs))) {
            signId = gljs;
        } else {
            signId = gzss;
        }
        //检查项目体证词关联表
        BasexamltemSign sign = basexamltemSignMapper.getInfoById(signId);
        //体检者收费项目ID
        String itemId = user.getIdExamfeeitem();
        //检查项目表
        Basexamltem tz_exam = basexamltemMapper.getInfoById(tz);
        Basexamltem gmdzd_exam = basexamltemMapper.getInfoById(gmdzd);
        StringBuilder zy_concolusion = new StringBuilder();
        String zz = Constants.ZZ_EXAM_ID;
        Basexamltem zz_exam = StringUtils.isEmpty(zz) ? null : basexamltemMapper.getInfoById(zz);
        boolean iszz = zz_exam != null;
        if ("2".equals(tjlx)) {
            if (ceis.get(tz) != null) {
                zy_concolusion.append(examData.get(tz + "textarea"));
            }
            //z值 骨密度
            if (iszz) {
                if (ceis.get(zz) != null) {
                    zy_concolusion.append(examData.get(zz + "textarea"));
                }
            }
            if (ceis.get(gmdzd) != null) {
                zy_concolusion.append(examData.get(gmdzd + "textarea"));
            }
            if (!(sign.getIsDefault() == null || sign.getIsDefault() == 0)) {
                zy_concolusion = new StringBuilder("未见异常；");
            }
        } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
            zy_concolusion.append(xjdata);
        }

        //科室检查结果主表
        SectionResultMain srm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode)
                .eq("dep_id", ksID));
        if (srm == null || srm.equals("")) {
            srm = new SectionResultMain();
        }
        srm.setIsFinish(0);
        srm.setZyConclusions(zy_concolusion.toString());
        srm.setDepId(ksID);//科室Id
        srm.setPatientcode(patientCode);
        srm.setShortCode(shortCode);
        //录入人
        srm.setWriteId(param.getLrr());
        srm.setWriteTime(lrsj);
        SysUser writeer = sysUserMapper.selectUserByUserNo(param.getLrr());
        if (writeer != null) {
            srm.setWriteName(writeer.getUserName());
        }
        //检查人
        srm.setRummagerId(jcr);
        srm.setRummagerTime(rummagerTime);
        SysUser rummager = sysUserMapper.selectUserByUserNo(jcr);
        if (rummager != null) {
            srm.setRummagerName(rummager.getUserName());
        }
        String userId = SecurityUtils.getUserNo();
        //审核人
        srm.setAuditId(userId);
        srm.setAuditTime(new Date());
        srm.setAuditName(SecurityUtils.getUsername());
        srm.setIsAudit(1);
        srm.setConclusions(xjdata);//小结
        SysUser rummagers = sysUserMapper.selectUserByUserNo(jcr);
        if (rummagers != null) {
            //检查人姓名
            srm.setRummagerName(rummagers.getUserName());
        }
        //审核人姓名
        srm.setAuditName(SecurityUtils.getUsername());
        if (wjz >= 1) {
            srm.setIsDanager(1);
            srm.setDanagerLevel(wjz);//危急值级别
        } else {
            srm.setIsDanager(0);
            srm.setDanagerLevel(0);//危急值级别
        }
        if (srm.getId() == null) {
            sectionResultMainMapper.insert(srm);
        } else {
            sectionResultMainMapper.updateById(srm);
        }

        //主表Id
        String mainId = srm.getId();
        //删除结果子表
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode)
                .eq("division_id", ksID));
        describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientCode).eq("dep_id", ksID));
        //部门表
        SysDept dept = sysDeptMapper.getByDeptNo(ksID);
        String depDes = dept == null ? null : dept.getDescription();
        //收费项目表
        Items items = itemsMapper.getInfoById(itemId);
        //收费项目打印名称
        String feeName = items == null ? null : items.getExamfeeitemNameprn();
        if (qj != 1) {//未弃检
            SectionResultTwo two = new SectionResultTwo();
            //KS科室描述、检查结果表
            Describe des = new Describe();
            two.setChargesId(itemId);
            two.setMainId(mainId);
            two.setVerdictId(tz);//检查项目id
            two.setPatientcode(patientCode);
            two.setShortCode(shortCode);
            two.setIsUnchecked(0);
            //科室Id
            two.setDivisionId(ksID);
            two.setInputResult(examData.get(tz));
            two.setIsDanger(wjz);
            two.setMs(tz_ms);
            if ("2".equals(tjlx)) {
                if (ceis.get(tz) != null) {
                    two.setTjlx(1);
                    des.setTjlx(1);
                } else {
                    two.setTjlx(0);
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                two.setTjlx(0);
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                two.setTjlx(1);
                des.setTjlx(1);
            }
            sectionResultTwoMapper.insert(two);

            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(tz);
            des.setDepId(ksID);
            des.setInspectDescribe(two.getMs());
            des.setSignList(two.getInputResult() == null ? null : tz_exam.getExamitemName() + ":" + two.getInputResult() + ";");
            des.setDepDescription(depDes);
            des.setItemName(tz_exam.getExamitemNameprn());
            des.setFeeId(itemId);
            des.setFeeName(feeName);
            describeMapper.insert(des);
            if (iszz) {
                SectionResultTwo twozz = new SectionResultTwo();
                Describe deszz = new Describe();
                twozz.setChargesId(itemId);
                twozz.setMainId(mainId);
                twozz.setVerdictId(zz);//检查项目id
                twozz.setPatientcode(patientCode);
                twozz.setShortCode(shortCode);
                twozz.setIsUnchecked(0);
                //科室Id
                twozz.setDivisionId(ksID);
                twozz.setInputResult(examData.get(zz));
                twozz.setIsDanger(wjz);
                twozz.setMs(zz_ms);
                if ("2".equals(tjlx)) {
                    if (ceis.get(zz) != null) {
                        twozz.setTjlx(1);
                        deszz.setTjlx(1);
                    } else {
                        twozz.setTjlx(0);
                        deszz.setTjlx(0);
                    }
                } else if ("0".equals(tjlx)) {
                    twozz.setTjlx(0);
                    deszz.setTjlx(0);
                } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                    twozz.setTjlx(1);
                    deszz.setTjlx(1);
                }
                sectionResultTwoMapper.insert(twozz);
                deszz.setPatientcode(patientCode);
                deszz.setShortCode(shortCode);
                deszz.setItemId(zz);
                deszz.setDepId(ksID);
                deszz.setInspectDescribe(twozz.getMs());
                deszz.setSignList(two.getInputResult() == null ? null : zz_exam.getExamitemName() + ":" + twozz.getInputResult() + ";");
                deszz.setDepDescription(depDes);
                deszz.setItemName(zz_exam.getExamitemNameprn());
                deszz.setFeeId(itemId);
                deszz.setFeeName(feeName);
                describeMapper.insert(deszz);
            }

            //结论词、小结
            SectionResultTwo three = new SectionResultTwo();
            des = new Describe();
            three.setChargesId(itemId);
            three.setMainId(mainId);
            three.setVerdictId(gmdzd);//检查项目id
            three.setPatientcode(patientCode);
            three.setShortCode(shortCode);
            three.setIsUnchecked(0);
            three.setDivisionId(ksID);//科室Id
            three.setMs(gmdzd_ms);
            three.setIntensiveLevel(sign.getIntensiveLevel());
            three.setIsDanger(wjz);
            three.setBasconclusionId(sign.getResultId());
            three.setPosistive(sign.getIsPositive());
            three.setNodule(sign.getId());
            if ("2".equals(tjlx)) {
                if (ceis.get(gmdzd) != null) {
                    two.setTjlx(1);
                    des.setTjlx(1);
                } else {
                    two.setTjlx(0);
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                two.setTjlx(0);
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                two.setTjlx(1);
                des.setTjlx(1);
            }
            sectionResultTwoMapper.insert(three);

            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(gmdzd);
            des.setDepId(ksID);
            des.setInspectDescribe(three.getMs());
            des.setSignList(gmdzd_exam.getExamitemName() + ":" + sign.getName() + ";");
            des.setDepDescription(depDes);
            des.setItemName(gmdzd_exam.getExamitemNameprn());
            des.setFeeId(itemId);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            if (wjz >= 1) {
                user.setPositivesummary(tz_exam.getExamitemName() + ":" + (two.getInputResult() == null ? "" : two.getInputResult()) + ";"
                        + gmdzd_exam.getExamitemName() + ":" + sign.getName() + ";");

                riskclientMapper.delete(new QueryWrapper<Riskclient>().eq("tjid", patientCode));
                riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientCode).eq("division_id", ksID));
                //高危人员管理表
                Riskclient rc = new Riskclient();
                rc.setCid(gwry.getHospitalcode());
                rc.setTjid(patientCode);
                rc.setGwrymc(gwry.getPatientname());//高危人员名称
                rc.setNl(gwry.getAge() == null ? null : gwry.getAge().toString());
                rc.setXb(gwry.getIdSex());
                rc.setLxdh(gwry.getPhone());
                rc.setTjlx(Integer.valueOf(gwry.getIdExamtype()));//体检类型
                rc.setTjzt(0);
                rc.setCid(SecurityUtils.getCId());
                rc.setIdOrg(gwry.getIdOrg());
                rc.setIdOpendoctor(gwry.getIdOpendoctor());
                rc.setTirq(jcsj);//检查时间
                riskclientMapper.insert(rc);
                String riskId = rc.getId();
                //高危人员关联表
                Riskclientcon rlc = new Riskclientcon();
                rlc.setGwxm(tz);//高危项目
                rlc.setWjzxj(sign.getName());//危急值小结
                rlc.setDivisionId(ksID);//科室Id
                rlc.setWjzjb(wjz);//危急值级别
                rlc.setCheckTime(lrsj);//检查时间

                rlc.setDoctorId(jcr);//医生id
                rlc.setPatientcode(patientCode);//体检号
                rlc.setSfxm(user.getExamfeeitemName());
                rlc.setRiskid(riskId);
                riskclientconMapper.insert(rlc);

                rlc = new Riskclientcon();//高危人员关联表
                rlc.setGwxm(gmdzd);//高危项目
                rlc.setWjzxj(sign.getName());//危急值小结
                rlc.setDivisionId(ksID);//科室Id
                rlc.setWjzjb(wjz);//危急值级别
                rlc.setCheckTime(lrsj);//检查时间

                rlc.setDoctorId(jcr);//医生id
                rlc.setPatientcode(patientCode);//体检号
                rlc.setSfxm(user.getExamfeeitemName());
                rlc.setRiskid(riskId);
                riskclientconMapper.insert(rlc);

            }
        } else {

            SectionResultTwo two = new SectionResultTwo();
            two.setMainId(mainId);
            two.setVerdictId(tz);//检查项目id
            two.setChargesId(itemId);
            two.setPatientcode(patientCode);//体检号
            two.setShortCode(shortCode);
            two.setIsUnchecked(1);
            two.setDivisionId(ksID);//科室Id
            sectionResultTwoMapper.insert(two);

            two = new SectionResultTwo();
            two.setMainId(mainId);
            two.setVerdictId(gmdzd);//检查项目id
            two.setChargesId(itemId);
            two.setPatientcode(patientCode);//体检号
            two.setShortCode(shortCode);
            two.setIsUnchecked(1);
            two.setDivisionId(ksID);//科室Id
            sectionResultTwoMapper.insert(two);
            //弃检
            user.setFGiveup(1);
        }



        peispatientfeeitemMapper.updateById(user);

        if (outsideMainService.getAllSfxmtzjStatus(patientCode)) {
            gwry.setFReadytofinal(1);//0:已备单 1:分检完成
            peisStateService.setScbs(gwry.getPatientcode(), 0);
            gwry.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
            for (Peispatientfeeitem other : other_items) {
                other.setFExaminated(1);//设置未关联科室项目为已检
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        peispatientMapper.updateById(gwry);
        //保存手动添加的结论词
        ArrayList<String> array = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(griddata)) {

            for (DmdGriddata d : griddata) {
                String jlcId = d.getJlcId();
                if (StringUtils.isNotBlank(jlcId) && !"\"null\"".equals(jlcId)) {//如果有结论词
                    array.add(jlcId);
                    SectionResultTwo two = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                            .eq("patientcode", patientCode)
                            .eq("basconclusion_id", jlcId)
                            .eq("is_unchecked", 0)
                            .eq("division_id", ksID));
                    if (two != null) {
                        continue;
                    } else {
                        two = new SectionResultTwo();
                        two.setMainId(mainId);
                        two.setVerdictId(d.getJcxmId());//检查项目id
                        two.setNodule(d.getTzcId());//体征词id
                        two.setPatientcode(patientCode);//体检号
                        two.setShortCode(shortCode);
                        two.setIsUnchecked(0);
                        two.setBasconclusionId(jlcId);
                        two.setDivisionId(ksID);//科室Id
                        two.setTjlx(0);
                        sectionResultTwoMapper.insert(two);
                    }
                }
            }
        }

        //将不在griddata中的检查结果子表 结论词Id设为Null
        List<SectionResultTwo> twolist = null;
        if (array.size() > 0) {//griddata中有结伦词的
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientCode)
                    .notIn("basconclusion_id", array)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        } else {
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientCode)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        }
        if (twolist != null && twolist.size() > 0) {
            for (SectionResultTwo two : twolist) {
                two.setBasconclusionId(null);
                sectionResultTwoMapper.updateById(two);
            }
        }

        gwry.setFExamstarted(1);//检查开始（反审核不用改）
        peispatientMapper.updateById(gwry);

        return Boolean.TRUE;
    }
}

