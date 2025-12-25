package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.DLDataDto;
import com.center.medical.abteilung.bean.dto.DLGriddataDto;
import com.center.medical.abteilung.bean.dto.LungGridData;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.DLungParam;
import com.center.medical.abteilung.bean.param.DivisionLungParam;
import com.center.medical.abteilung.bean.param.lungSaveJlcParam;
import com.center.medical.abteilung.dao.DivisionLungMapper;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.DivisionLungService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Describe;
import com.center.medical.bean.model.Lung;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.dao.LungMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.BasconclusionMapper;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.BasexamltemSignMapper;
import com.center.medical.data.dao.ItemsMapper;
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
import com.center.medical.service.LungService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KS肺功能(Lung)表服务实现类
 *
 * @author ay
 * @since 2023-04-20 18:01:22
 */
@Slf4j
@Service("divisionLungService")
@RequiredArgsConstructor
public class DivisionLungServiceImpl extends ServiceImpl<DivisionLungMapper, Lung> implements DivisionLungService {

    private final DivisionLungMapper divisionLungMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PatienttypeService patienttypeService;
    private final SectionResultMainService sectionResultMainService;
    private final SysUserMapper sysUserMapper;
    private final LungMapper lungMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final RiskclientMapper riskclientMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final DescribeMapper describeMapper;
    private final ItemsMapper itemsMapper;
    private final MapperFacade mapperFacade;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final ISysBranchService iSysBranchService;
    private final LungService lungService;
    private final PeispatientfeeitemService peispatientfeeitemService;



    //体征词
    private static final String js = "3582";//拘束性通气功能障碍
    private static final String bs = "3583";//闭塞性通气功能障碍
    private static final String hh = "3584";//闭塞性通气功能障碍
    private static final String zc = "2474";//通气功能正常
    //收费项目
    @SuppressWarnings("unused")
    private static final String lungItemId = "184";

    //检查项目
    private static final String lungExamId = "515";//肺功能检测
    private static final String FVCId = "840";
    private static final String FEV1Id = "841";
    private static final String MMEFId = "843";
    private static final String FEF25ID = "848";
    private static final String FEF50ID = "847";
    private static final String FEF75ID = "846";


    private static final String LUNG_ID = "77";// 肺功能科室ID

    /**
     * 肺功能读卡
     *
     * @param param
     * @return
     */
    @Override
    public HashMap<String, Object> search(DivisionLungParam param) {
        //取出属性
        String patientCode = param.getPatientCode();
        String ksId = param.getKsId();

        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode).eq("f_registered", 1));
        if (patient == null) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        if (patient.getFPaused() != null && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("error@该体检号已被禁检！");
        }
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (idOrgreservationgroup != null) {
            //体检者任务分组
            Peisorgreservationgroup org = peisorgreservationgroupMapper.getInfoById(idOrgreservationgroup);
            //组禁用
            if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                throw new ServiceException("error@该体检号已被禁检！");
            }
        }
        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_Ks", ksId)
                .isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
        );
        if (feeitems.size() == 0) {
            throw new ServiceException("error@该体检号没有本科室收费项目！");
        }
        Peispatientfeeitem feeitem = null;
        for (Peispatientfeeitem item : feeitems) {
            if (item.getFFeecharged() != null && item.getFFeecharged() == 1) {
                feeitem = item;
                break;
            }
        }
        if (feeitem == null) {
            throw new ServiceException("error@该体检号尚未缴费！");
        }
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("patient", patient);
        data.put("picture", peispatientPhotoService.getPicture(patient));
        data.put("isVIP", patienttypeService.getIdPatientClass(patient));

        data.put("feeitem", feeitems.get(0));
        //科室检查结果主表
        SectionResultMain main = sectionResultMainService.getOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode)
        );
        if (main != null) {
            //审核过
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<String, String> main_map = new HashMap<String, String>();
            main_map.put("id", main.getId());
            main_map.put("conclusions", main.getConclusions());
            //审核
            main_map.put("isAudit", main.getIsAudit() == null ? "0" : (main.getIsAudit() == 1 ? "1" : "0"));
            //危急值级别
            main_map.put("danagerLevel", String.valueOf(main.getDanagerLevel()));
            String rummagerId = main.getRummagerId();
            main_map.put("rummagerId", rummagerId);
            if (rummagerId != null) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(rummagerId);
                if (rummager != null) {
                    //检查人
                    main_map.put("rummager", rummager.getUserName());
                }
            }
            main_map.put("rummagerTime", main.getRummagerTime() == null ? "" : sdf.format(main.getRummagerTime()));
            String writeId = main.getWriteId();
            if (StringUtils.isNotEmpty(writeId)) {
                SysUser writer = sysUserMapper.selectUserByUserNo(writeId);
                if (writer != null) {
                    main_map.put("writeId", writeId);
                    main_map.put("writeName", writer.getUserName());
                }
            }
            main_map.put("writeTime", main.getWriteTime() == null ? "" : sdf.format(main.getWriteTime()));
            data.put("main", main_map);
        }
        /*肺功能表 1-11修改  从main!=null中移出*/
        Lung lung = lungMapper.getInfoPatientCode(patientCode);
        data.put("lung", lung);
        return data;
    }


    /**
     * 结伦词列表数据
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public List<HashMap<String, String>> jlcData(String patientCode, String ksId) {
        //科室检查结果表
        List<SectionResultTwo> twos = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                .orderByDesc("createdate")
                .eq("patientcode", patientCode)
                .eq("division_id", ksId));
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        for (SectionResultTwo two : twos) {
            HashMap<String, String> map = new HashMap<String, String>();
            //检查项目表
            Basexamltem ltem = basexamltemMapper.getInfoById(two.getVerdictId());
            if (ltem != null) {
                map.put("jcxm", ltem.getExamitemName());
            }
            //检查项目 体证词关联表
            BasexamltemSign sign = basexamltemSignMapper.getInfoById(two.getNodule());
            if (sign != null) {
                map.put("tzc", sign.getName());
            }
            map.put("jlcId", two.getBasconclusionId());
            //总检结论词
            Basconclusion con = basconclusionMapper.getInfoById(two.getBasconclusionId());
            if (con != null) {
                map.put("jlcName", con.getName());
            }
            map.put("tzcId", two.getNodule());
            //检验科用
            map.put("verdictId", two.getVerdictId());
            map.put("chargesId", two.getChargesId());

            result.add(map);
        }
        return result;
    }


    /**
     * 获取体征词
     *
     * @param key
     * @return
     */
    @Override
    public HashMap<String, Object> getSign(String key) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String signId = "";
        if ("zc".equals(key)) {
            signId = zc;
        } else if ("hh".equals(key)) {
            signId = hh;
        } else if ("bs".equals(key)) {
            signId = bs;
        } else {
            signId = js;
        }
        //检查项目体证词关联表
        BasexamltemSign sign = basexamltemSignMapper.getInfoById(signId);
        //检查项目表
        Basexamltem item = basexamltemMapper.getInfoById(sign.getInspectId());
        //总检结论词
        Basconclusion con = basconclusionMapper.getInfoById(sign.getResultId());
        result.put("jcxm", item.getExamitemName());
        result.put("tzc", sign.getName());
        result.put("tzcId", signId);
        result.put("jlcId", sign.getResultId());
        result.put("intensiveLevel", sign.getIntensiveLevel());
        if (con != null) {
            result.put("jlcName", con.getName());
        }
        return result;
    }


    /**
     * 保存结伦词
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveJlc(lungSaveJlcParam param) {
        //取出属性
        List<LungGridData> list = param.getGriddata();
        String patientCode = param.getPatientCode();
        String ksId = param.getKsId();

        //获取短号
        Integer shortCode = CodeUtil.getShortCodeByLong(patientCode);
        //科室检查结果主表
        SectionResultMain stm = sectionResultMainService.getOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode).eq("dep_id", ksId));
        if (stm == null) {
            stm = new SectionResultMain();
            stm.setDepId(ksId);//科室ID
            stm.setPatientcode(patientCode);//体检号
            stm.setShortCode(shortCode);
            sectionResultMainService.save(stm);
        }
        //体检者收费项目表
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_Ks", ksId)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
        );
        //体检者收费项目ID
        String feeId = feeitem.getIdExamfeeitem();
        //主表id
        String main_id = stm.getId();
        //删除
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode).eq("division_id", ksId));
        for (LungGridData map : list) {
            SectionResultTwo two = new SectionResultTwo();
            two.setMainId(main_id);
            String verdictId = null;
            if (StringUtils.isBlank(map.getJcxm())) {
                verdictId = null;
            } else {
                verdictId = lungExamId;
            }
            two.setVerdictId(verdictId);
            //体征词id
            two.setNodule(map.getTzcId());
            if (StringUtils.isNotBlank(map.getTzcId())) {
                //检查项目体证词关联表
                BasexamltemSign sign = basexamltemSignMapper.getInfoById(map.getTzcId());
                if (sign != null) {
                    two.setPosistive(sign.getIsPositive());
                    if (sign.getIntensiveLevel() != null && sign.getIntensiveLevel() >= 1) {
                        //子表为体征词重症级别
                        two.setIsDanger(1);
                        two.setIntensiveLevel(sign.getIntensiveLevel());
                    } else {
                        two.setIsDanger(0);
                        two.setIntensiveLevel(0);
                    }
                }
            }
            two.setPatientcode(patientCode);
            two.setShortCode(shortCode);
            two.setIsUnchecked(0);
            two.setBasconclusionId(map.getJlcId());
            two.setDivisionId(ksId);
            two.setChargesId(feeId);
            sectionResultTwoMapper.insert(two);
        }
        return Boolean.TRUE;
    }


    /**
     * 审核
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(DLungParam param) {
        //取出属性
        String patientCode = param.getPatientCode();
        String ksId = param.getKsId();

        //审核时间
        Date auditTime = new Date();
        //审核人Id
        String auditId = SecurityUtils.getUserNo();
        ////分科检查医师
        String examdoctorNameR = SecurityUtils.getUsername();
        Lung lung_map = param.getFormdata();
        //危急值(用于判断是否插入高危人员)
        int isDanger = StringUtils.isEmpty(lung_map.getIsDanger()) ? 0 : Integer.parseInt(lung_map.getIsDanger());
        //弃检
        String isUnchecked = lung_map.getIsUnchecked();
        //体检
        Peispatient patient = peispatientMapper.getByPatientCode(patientCode);

        if (patient == null || patient.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (patient.getJktjzt() != null && patient.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getDoctorfinalNameR() == null ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getZytjzt() != null && patient.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getPatientnameencoded() == null ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getFFinallocked() != null && patient.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getIdDoctorapply() == null ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getIdGuidenurse() != null && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getParsedassignedsuiteandfi() == null ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        SysDept dept = sysDeptMapper.getByDeptNo(ksId);
        //描述
        String depDes = dept == null ? null : dept.getDescription();
        Integer shortCode = patient.getShortCode();
        Integer dangerFlag = null;
        Integer dangerLevel = null;
        if (isDanger >= 1) {
            dangerFlag = 1;
            dangerLevel = isDanger;
        } else {
            dangerFlag = 0;
            dangerLevel = 0;
        }
        //体检类型
        String tjlx = patient.getIdExamtype();
        //key:检查项目ID value：ComboExamItem
        Map<String, Comboexamitem> ceis = null;
        //接害因素
        String jhys = patient.getJhys();
        //职业检查类型
        String medicaltype = patient.getMedicaltype();
        //按接害因素、职业体检类型匹配
        if ("2".equals(tjlx)) {
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type", medicaltype));
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getExamId(), cei);
            }
        }
        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .eq("f_feecharged", 1)
                .isNull("f_transferedhl7")
        );
        if (feeitems.size() == 0) {
            throw new ServiceException("审核失败，该体检号没有本科室收费项目或尚未缴费！");
        }
        //@sqlOrder
        patient.setModifydate(new Date());
        peispatientMapper.updateById(patient);

        //收费项目ID
        String feeid = feeitems.get(0).getIdExamfeeitem();
        SectionResultMain new_main = new SectionResultMain();
        DLDataDto main_map = param.getData();
        //如果弃检
        if ("1".equals(isUnchecked)) {
            if (StringUtils.isNotBlank(main_map.getId())) {
                SectionResultMain old_main = sectionResultMainMapper.getInfoById(main_map.getId());
                if (old_main.getIsAudit() == 1) {
                    throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
                }
                //删除
                sectionResultMainMapper.delete(new QueryWrapper<SectionResultMain>()
                        .eq("dep_id", ksId).eq("patientcode", patientCode));
                sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                        .eq("division_id", ksId).eq("patientcode", patientCode));
                riskclientMapper.delete(new QueryWrapper<Riskclient>().eq("tjid", patientCode).eq("gwxm", "血压结论"));
                riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientCode).eq("gwxm", "血压结论"));

            }
            for (Peispatientfeeitem feeitem : feeitems) {
                //弃检
                feeitem.setFGiveup(1);
                peispatientfeeitemMapper.updateById(feeitem);
            }
        } else {
            //检查结果主表
            String main_id = main_map.getId();
            new_main.setId(main_id);
            //检查人
            new_main.setRummagerId(main_map.getRummagerId());
            Date rummagerTime = main_map.getRummagerTime();
            new_main.setRummagerTime(rummagerTime);
            SysUser rummager = sysUserMapper.getUserByNo(main_map.getRummagerId());
            if (rummager != null) {
                new_main.setRummagerName(rummager.getUserName());
            }
            //录入人
            new_main.setWriteTime(main_map.getWriteTime());
            new_main.setWriteId(main_map.getWriteId());
            SysUser auditer = sysUserMapper.getUserByNo(main_map.getWriteId());
            if (auditer != null) {
                new_main.setWriteName(auditer.getUserName());
            }
            new_main.setAuditName(examdoctorNameR);
            new_main.setDepId(ksId);
            new_main.setPatientcode(patientCode);
            new_main.setShortCode(shortCode);
            new_main.setAuditId(auditId);
            new_main.setAuditTime(auditTime);
            new_main.setIsDanager(dangerFlag);
            new_main.setDanagerLevel(dangerLevel);
            new_main.setIsAudit(1);
            new_main.setConclusions(main_map.getConclusions());
            new_main.setZyConclusions(main_map.getConclusions());//肺功能职业小结=健康小结
            new_main.setAssociativeTable("LUNG");
            SectionResultMain old_main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                    .eq("patientcode", patientCode).eq("dep_id", ksId));
            if (old_main == null) {
                //第一次审核
                sectionResultMainMapper.insert(new_main);
                main_id = new_main.getId();
            } else {
                if (old_main.getIsAudit() != null && old_main.getIsAudit() == 1) {
                    throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
                }
                if (old_main.getRummagerId() == null) {//保存结伦词
                    BeanUtils.copyProperties(new_main, old_main, new String[]{"isDelete", "isFinish", "id"});
                } else {
                    BeanUtils.copyProperties(new_main, old_main, new String[]{"isDelete", "isFinish", "id"});
                }
                sectionResultMainMapper.updateById(old_main);
                new_main = old_main;
            }

            //删除子表
            sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                    .eq("division_id", ksId)
                    .eq("patientcode", patientCode)
            );

            /* 肺表*/
            Lung lung = param.getFormdata();
            log.info("打印一下肺功能保存的参数：{}",lung);
            lung.setPatientcode(patientCode);
            lung.setTjrq(auditTime);
            lung.setXj(new_main.getConclusions());
            lung.setCreateId(auditId);
            lung.setDepId(ksId);
            if (StringUtils.isBlank(lung.getId())) {
                //添加
                lungMapper.insert(lung);
            } else {
                //修改
                lung.setModifyId(auditId);
                lungMapper.updateById(lung);
            }
            //所有阳性小结
            StringBuilder positivesummary = new StringBuilder();
            //危急值级别
            Integer main_danagerLevel = null;

            //检查结果子表
            String lung_sign = null;
            Integer lung_wjz = null;
            List<DLGriddataDto> two_gridValue = param.getGriddata();
            for (DLGriddataDto map : two_gridValue) {
                SectionResultTwo two = new SectionResultTwo();
                two.setMainId(main_id);
                StringBuilder ms = new StringBuilder();
                if (StringUtils.isNotBlank(map.getTzcId())) {
                    two.setNodule(map.getTzcId());
                    //肺功能检测
                    two.setVerdictId(lungExamId);
                    Basexamltem ltem = basexamltemMapper.getInfoById(lungExamId);
                    BasexamltemSign sign = basexamltemSignMapper.getInfoById(map.getTzcId());
                    if ("2".equals(tjlx)) {
                        if (ceis.get(lungExamId) != null) {
                            two.setTjlx(1);
                        } else {
                            two.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        two.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        two.setTjlx(1);
                    }
                    ms.append(ltem.getExamitemName());
                    if (sign != null) {
                        ms.append(":" + sign.getName());
                        two.setPosistive(sign.getIsPositive());
                        lung_sign = sign.getName();
                        lung_wjz = sign.getIntensiveLevel();
                        if (sign.getIsPositive() != null && sign.getIsPositive() == 1 && ltem != null) {
                            positivesummary.append(ltem.getExamitemName() + ":" + sign.getName() + ";");
                        }
                        if (sign.getIntensiveLevel() != null && sign.getIntensiveLevel() >= 1) {//子表为体征词重症级别
                            two.setIsDanger(1);
                            two.setIntensiveLevel(sign.getIntensiveLevel());
                        } else {
                            two.setIsDanger(0);
                            two.setIntensiveLevel(0);
                        }
                        if (main_danagerLevel == null || main_danagerLevel < sign.getIntensiveLevel()) {
                            main_danagerLevel = sign.getIntensiveLevel();
                        }
                    }
                } else {
                    two.setTjlx(0);
                }
                two.setMs(ms.toString());
//        		two.setMs(Hibernate.createClob(ms.toString()));/
                two.setPatientcode(patientCode);
                two.setShortCode(shortCode);
                two.setIsUnchecked(0);
                two.setBasconclusionId(map.getJlcId());
                two.setDivisionId(ksId);
                two.setChargesId(feeid);
                sectionResultTwoMapper.insert(two);
            }

            if (main_danagerLevel != null && main_danagerLevel >= 1) {
                new_main.setIsDanager(1);
                new_main.setDanagerLevel(main_danagerLevel);
            } else {
                new_main.setIsDanager(0);
                new_main.setDanagerLevel(0);
            }
            new_main.setIsFinish(0);
            sectionResultMainMapper.updateById(new_main);

            //体检者收费项目
            for (Peispatientfeeitem feeitem : feeitems) {
                feeitem.setPositivesummary(positivesummary.toString());
                feeitem.setIdPatientexamdepart(ksId);
                feeitem.setIdExamdoctor(auditId);
                feeitem.setExamdoctorNameR(examdoctorNameR);
                feeitem.setExaminatetime(rummagerTime);
                feeitem.setFExaminated(1);
                feeitem.setSeveredegree(isDanger);
                peispatientfeeitemMapper.updateById(feeitem);
            }

            //高危人员表
            //手录危急值 判断高危(选择中、高插入)
            riskclientMapper.delete(new QueryWrapper<Riskclient>().eq("tjid", patientCode).eq("gwxm", "肺功能检测"));
            riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientCode).eq("gwxm", "肺功能检测"));
            if (isDanger == 2 || isDanger == 3) {
                Riskclient rc = new Riskclient();
                rc.setCid(patient.getHospitalcode());
                rc.setTjid(patientCode);
                rc.setGwrymc(patient.getPatientname());
                rc.setNl(patient.getAge() == null ? "" : patient.getAge().toString());
                rc.setXb(patient.getIdSex());
                rc.setLxdh(patient.getPhone());
                rc.setGwxm("肺功能检测");//只有血压报高危
                rc.setTjlx(Integer.valueOf(patient.getIdExamtype()));
                rc.setTjzt(0);
                rc.setTirq(new_main.getRummagerTime());
                rc.setCid(SecurityUtils.getCId());
                rc.setIdOrg(patient.getIdOrg());
                rc.setIdOpendoctor(patient.getIdOpendoctor());
                riskclientMapper.insert(rc);
                String risk_mainid = rc.getId();


                Riskclientcon rcc = new Riskclientcon();
                rcc.setRiskid(risk_mainid);
                rcc.setGwxm("血压结论");
                rcc.setDivisionId(ksId);
                rcc.setWjzxj(lung_sign);
                rcc.setDoctorId(auditId);
                rcc.setCheckTime(auditTime);
                rcc.setPatientcode(patientCode);
                rcc.setWjzjb(lung_wjz);
                rcc.setSfxm(feeitems.get(0).getExamfeeitemName());
                riskclientconMapper.insert(rcc);
            }
            //describe表 2017-4-14修改，原来在ISdanger=2或3时才插入
            describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientCode)
                    .in("item_id", new String[]{lungExamId, FVCId, FEV1Id, FEF25ID, FEF50ID, FEF75ID, MMEFId}));
            Items items = itemsMapper.getInfoById(feeid);
            String feeName = items == null ? null : items.getExamfeeitemNameprn();
            Describe des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(lungExamId);
            //des.setItemName("肺功能检测");
            des.setSignList(lung_sign == null ? "正常" : lung_sign);
            des.setDepId(ksId);
            des.setDepDescription(depDes);
            Basexamltem ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(FVCId);
            //des.setItemName("用力肺活量(FVC)");
            des.setSignList(lung.getFvc() == null ? null : lung.getFvc().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(FEV1Id);
            //des.setItemName("1秒用力呼气容积(FEV1)");
            des.setSignList(lung.getFev() == null ? null : lung.getFev().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(FEF25ID);
            //des.setItemName("25%呼气中段流量(FEF25%)");
            des.setSignList(lung.getFeffc() == null ? null : lung.getFeffc().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(FEF50ID);
            //des.setItemName("50%呼气中段流量(FEF50%)");
            des.setSignList(lung.getFeffb() == null ? null : lung.getFeffb().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(FEF75ID);
            //des.setItemName("75%呼气中段流量(FEF75%)");
            des.setSignList(lung.getFeffa() == null ? null : lung.getFeffa().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);


            des = new Describe();
            des.setPatientcode(patientCode);
            des.setShortCode(shortCode);
            des.setItemId(MMEFId);
            //des.setItemName("最大呼气中期流速(MMEF)");
            des.setSignList(lung.getMmef() == null ? null : lung.getMmef().toString());
            des.setDepId(ksId);
            if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            }
            des.setDepDescription(depDes);
            ltem = basexamltemMapper.getInfoById(des.getItemId());
            des.setItemName(ltem == null ? null : ltem.getExamitemNameprn());
            des.setFeeId(feeid);
            des.setFeeName(feeName);
            describeMapper.insert(des);

            //体检者表
            if (outsideMainService.getAllSfxmtzjStatus(patientCode)) {
                patient.setFReadytofinal(1);//0:已备单 1:分检完成
                peisStateService.setScbs(patient.getPatientcode(), 0);
                patient.setReadytofinalDate(new Date());
                List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
                for (Peispatientfeeitem other : other_items) {
                    other.setFExaminated(1);//设置未关联科室项目为已检
                }
                //批量更新
                peispatientfeeitemService.updateBatchById(other_items);
            }
            patient.setFExamstarted(1);
            peispatientMapper.updateById(patient);
        }
        return Boolean.TRUE;
    }


    /**
     * 反审核
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reverse(String patientCode, String ksId) {
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (peispatient == null) {
            throw new ServiceException("error@反审核失败，该体检号尚未登记！");
        }
        if (peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1) {
            throw new ServiceException("error@反审核失败，本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
            throw new ServiceException("error@反审核失败，本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
            throw new ServiceException("error@反审核失败，本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("error@反审核失败，本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .eq("f_feecharged", 1));

        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode));

        if (main == null || main.getIsAudit() == null || main.getIsAudit() != 1) {
            throw new ServiceException("error@反审核失败，该体检号未审核！");
        }
        for (Peispatientfeeitem feeitem : feeitems) {
            //0：未检
            feeitem.setFExaminated(0);
            peispatientfeeitemMapper.updateById(feeitem);
        }
        peispatient.setFReadytofinal(0);
        peisStateService.setScbs(peispatient.getPatientcode(), 0);
        main.setIsAudit(0);
        peispatientMapper.updateById(peispatient);
        sectionResultMainMapper.updateById(main);
        return Boolean.TRUE;
    }


    /**
     * 肺功能即时上传
     *
     * @param data
     * @return
     */
    @Override
    public Lung uploadLungIm(String data) {
        String code = data.split(",")[10].trim();
        //补全体检号
        String patientCode = ToolUtil.patientCode(code, iSysBranchService.getBranchFlag(null));

        // 数据库中没有的体检号不能上传?
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException(patientCode + "体检号不存在， 上传失败!\\r\\n", 200);
        }
        // 审核了的不能改
        List<SectionResultMain> audits = sectionResultMainMapper.selectList(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode).eq("dep_id", LUNG_ID));
        if (audits.size() != 0 && audits.get(0).getIsAudit() == 1) {
            throw new ServiceException(patientCode + "体检号已审核， 不能上传!\\r\\n", 200);
        }


        String[] os = data.toString().split(",");
        int n = os[28].indexOf("") != -1 ? 0 : 2;// 两种格式，其中一种多两个0
        Double fVC = Double.parseDouble(os[43 + n].trim());
        Double predictFVC = Double.parseDouble(os[44 + n].trim());
        Double percentageFVC = Double.parseDouble(os[45 + n].trim());
        Double fEV1 = Double.parseDouble(os[49 + n].trim());
        Double predictFEV1 = Double.parseDouble(os[50 + n].trim());
        Double percentageFEV1 = Double.parseDouble(os[51 + n].trim());
        // Double fEV6=Double.parseDouble(os[55].trim());
        Double fEV1FVC = Double.parseDouble(os[59 + n].trim());
        Double predictFEV1FVC = Double.parseDouble(os[60 + n].trim());
        Double percentageFEV1FVC = Double.parseDouble(os[61 + n].trim());
        Double mMEF = Double.parseDouble(os[74 + n].trim());
        Double predictMMEF = Double.parseDouble(os[75 + n].trim());
        Double percentageMMEF = Double.parseDouble(os[76 + n].trim());
//				Double pEF=Double.parseDouble(os[77].trim());
//				Double predictPEF=Double.parseDouble(os[78].trim());
//				Double percentagePEF=Double.parseDouble(os[79].trim());
        Double fEF25 = Double.parseDouble(os[80 + n].trim());
        Double predictFEF25 = Double.parseDouble(os[81 + n].trim());
        Double percentageFEF25 = Double.parseDouble(os[82 + n].trim());
        Double fEF50 = Double.parseDouble(os[83 + n].trim());
        Double predictFEF50 = Double.parseDouble(os[84 + n].trim());
        Double percentageFEF50 = Double.parseDouble(os[85 + n].trim());
        Double fEF75 = Double.parseDouble(os[86 + n].trim());
        Double predictFEF75 = Double.parseDouble(os[87 + n].trim());
        Double percentageFEF75 = Double.parseDouble(os[88 + n].trim());

        // 保存LUNG(如果有则覆盖)
        Lung lung = lungMapper.selectOne(new QueryWrapper<Lung>().eq("patientcode", patientCode));
        if (lung == null) {
            lung = new Lung();
            lung.setDepId(LUNG_ID);
            lung.setPatientcode(patientCode);
        }
        lung.setFvc(fVC);
        lung.setPredictFvc(predictFVC);
        lung.setPercentageFvc(percentageFVC);
        lung.setFev(fEV1);
        lung.setPredictFev(predictFEV1);
        lung.setPercentageFev(percentageFEV1);
        lung.setFevFvc(fEV1FVC);
        lung.setPredictFevFvc(predictFEV1FVC);
        lung.setPercentageFevFvc(percentageFEV1FVC);
        lung.setMmef(mMEF);
        lung.setPredictMmef(predictMMEF);
        lung.setPercentageMmef(percentageMMEF);
        lung.setFeffc(fEF25);
        lung.setPredictFeffc(predictFEF25);
        lung.setPercentageFeffc(percentageFEF25);
        lung.setFeffb(fEF50);
        lung.setPredictFeffb(predictFEF50);
        lung.setPercentageFeffb(percentageFEF50);
        lung.setFeffa(fEF75);
        lung.setPredictFeffa(predictFEF75);
        lung.setPercentageFeffa(percentageFEF75);
        //添加或更新
        lungService.saveOrUpdate(lung);
        log.info("肺功能串口的数据：{}",lung);
        return lung;
    }


    /**
     * 淮南肺功能及时上传
     * @param data
     * @return
     */
    @Override
    public Lung uploadHnLung(String data) throws UnsupportedEncodingException {
        // 将字符串中的Unicode转义字符转换为实际字符
        String decodedString = convertUnicodeEscape(data);
        // 打印解码后的字符串
        System.out.println("Decoded Input: " + decodedString);

        String hexString = stringToHex(decodedString);
        log.info(hexString);
        int length = hexString.length();
        log.info("length:{}",length);
        boolean startWith = hexString.startsWith("AA");
        if (startWith && hexString.length() >= 296) {
            String substring = hexString.substring(hexString.indexOf("AA"),
                    hexString.lastIndexOf("FF") + 2);
            log.info("汇总数据=" + substring);
            Lung lung = createLung(substring, LUNG_ID);
            saveOrUpdate(lung);
            return lung;
        }else {
            throw new ServiceException("上传数据不符合要求！");
        }
    }



    public Lung createLung(String dataStr, String lungId) {
        if (dataStr != null && dataStr.length() >= 296) {
            dataStr = dataStr.toUpperCase();
            String fvc = getSubStr(dataStr, 26, 26 + 4);
            String fev1 = getSubStr(dataStr, 40, 40 + 4);
            String MMF = getSubStr(dataStr, 90, 90 + 4);
            String V75 = getSubStr(dataStr, 118, 118 + 4);
            String V50 = getSubStr(dataStr, 132, 132 + 4);
            String V25 = getSubStr(dataStr, 146, 146 + 4);
            String result = getSubStr(dataStr, 222, 222 + 2);
            String PFVC = getSubStr(dataStr, 30, 30 + 6);
            String PFEV1 = getSubStr(dataStr, 44, 44 + 6);
            String PMMF = getSubStr(dataStr, 94, 94 + 4);
            String PV75 = getSubStr(dataStr, 122, 122 + 4);
            String PV50 = getSubStr(dataStr, 136, 136 + 4);
            String PV25 = getSubStr(dataStr, 150, 150 + 4);

            String FEV1A = getSubStr(dataStr, 62, 62 + 6);
            String PFEV1A = getSubStr(dataStr, 68, 68 + 4);
            String other = getSubStr(dataStr, 244 + 6, dataStr.length());

            log.info("oString=" + other);
            Lung newLung = new Lung();
            newLung.setCreatedate(new Date());
            //用力肺活量(FVC)
            newLung.setFvc(Double.valueOf(fvc)/ 1000);
            newLung.setPredictFvc(Double.valueOf(PFVC) / 10000);
            newLung.setPercentageFvc(BigDecimal.valueOf(newLung.getFvc() / newLung.getPredictFvc() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            //1秒用力呼气容积(FEV1)
            newLung.setFev(Double.valueOf(fev1) / 1000);
            newLung.setPredictFev(Double.valueOf(PFEV1) / 10000);
            newLung.setPercentageFev(BigDecimal.valueOf(newLung.getFev() / newLung.getPredictFev() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            //1秒用力呼气容积/用力肺活量(FEV1%G)
            newLung.setFevFvc(Double.valueOf(FEV1A) / 100);
            newLung.setPredictFevFvc(Double.valueOf(PFEV1A) / 100);
            newLung.setPercentageFevFvc(BigDecimal.valueOf(newLung.getFevFvc() / newLung.getPredictFevFvc() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            //最大呼气中期流速(MMEF)
            newLung.setMmef(Double.valueOf(MMF) / 100);
            newLung.setPredictMmef(Double.valueOf(PMMF) / 100);
            newLung.setPercentageMmef(BigDecimal.valueOf(newLung.getMmef() / newLung.getPredictMmef() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());

            //25%呼气中段流量(FEF25%)
            newLung.setFeffc(Double.valueOf(V25) / 100);
            newLung.setPredictFeffc(Double.valueOf(PV25) / 100);
            newLung.setPercentageFeffc(BigDecimal.valueOf(newLung.getFeffc() / newLung.getPredictFeffc() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());

            //50%呼气中段流量(FEF50%)
            newLung.setFeffb(Double.valueOf(V50) / 100);
            newLung.setPredictFeffb(Double.valueOf(PV50) / 100);
            newLung.setPercentageFeffb(BigDecimal.valueOf(newLung.getFeffb() / newLung.getPredictFeffb() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());

            //75%呼气中段流量(FEF75%)
            newLung.setFeffa(Double.valueOf(V75) / 100);
            newLung.setPredictFeffa(Double.valueOf(PV75)  / 100);
            newLung.setPercentageFeffa(BigDecimal.valueOf(newLung.getFeffa() / newLung.getPredictFeffa() * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());

            newLung.setDepId(lungId);
            String xj = "";
            String xj2 = "";
            if (result.startsWith("4")) {
                xj2 = "混合性";
            } else if (result.startsWith("3")) {
                xj2 = "阻塞性";

            } else if (result.startsWith("2")) {
                xj2 = "限制性";
            } else {
                xj = "正常";
            }
            String xj1 = "";
            if (!result.startsWith("1")) {
                if (result.endsWith("3")) {
                    xj1 = "重度";
                } else if (result.endsWith("2")) {
                    xj1 = "中度";
                } else {
                    xj1 = "轻度";
                }
                xj = xj1 + xj2 + "通气功能障碍";
            }
            newLung.setXj(xj);
            String[] split = other.split("F{1,2}");
            String string = Arrays.asList(split).get(1);
            log.info("split=" + Arrays.asList(split));
//			String subSequence = code.substring(code.length() - 12, code.length());
            if (string.length() > 8) {
                string = string.substring(string.length() - 8);
            }
            newLung.setPatientcode(ToolUtil.patientCode(string, iSysBranchService.getBranchFlag(null)));
            Lung oldLung = lungMapper.getInfoPatientCode(newLung.getPatientcode());
            if (oldLung != null){
                newLung.setId(oldLung.getId());
            }
            newLung.setTjrq(new Date());
            log.info(newLung.toString());
            return newLung;
        } else {
            return new Lung();
        }
    }

    private static String getSubStr(String dataStr, int i, int j) {
        return dataStr.substring(i, j);
    }







    public static void main(String[] args) throws UnsupportedEncodingException {
        // 原始字符串
//        String input = "ª\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000)\u0017\u00047i\u0006f(\u0001\u0003sT\u0007P)\u0017)\u0017\u0000\u0002\u0001\u0013\u0011\u0001\u0000\u0000\u0001\u0000\u0000\u0004\u0005\u0003\u0001\u0002e\u0004a\t&\u0000Iv\u00047\u0007w\u0000V \u0004a\u0005\u0003\u0000p\u0002\u0002)\u0001&\u0001X\u00024\u0000gS\u0001q\u00014\u0001&\tT\u0001\u0012\bE\u0001x\u00056\u0000cB\u0000\u0000\u0000!\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000ÿ\t\u00021\u0006Rÿÿ)\u0001\u0001p\u0000p %\u0003'ÿ";
//        System.out.println(input);
//        // 转换成二进制并打印
//        System.out.println("Binary representation:");
//        String binaryString = stringToBinary(input);
//        System.out.println(binaryString);
//
//        // 转换成16进制并打印
//        System.out.println("Hexadecimal representation:");
//        System.out.println(input);
//        System.out.println("分割");
//        String hexString = stringToHex(input);
//        System.out.println(hexString);


        // 如果输入字符串是ISO-8859-1编码，则先解码
        // 将字符串按照 UTF-16 编码解码
//        byte[] bytes = input.getBytes(StandardCharsets.UTF_16);

//        // 将字节数组转换为字符串
//        String decodedString = new String(bytes, StandardCharsets.UTF_16);
//
//        // 打印解码后的字符串
//        System.out.println("Decoded Input: " + decodedString);
//
//        // 转换为16进制表示
//        String hexString = stringToHex(decodedString);
//        System.out.println("Hexadecimal representation: " + hexString);
//        String hexString = "AA00630500384600609814894408910406130219089103448802580891089101000083920119160100000100000226036700615803010899003347030107770038720301048200624700710201003557042002710154720042011800355703770103390365016302300063063103004304380048023901510287041834010469FF123456789012345F39010170005920091111FF";
//        System.out.println(hexString.length());
//        Lung lung = createLung(hexString, LUNG_ID);
//        System.out.println(lung);
    }

    // 将字符串转换为二进制
    public static String stringToBinary(String input) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            String binary = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryString.append(binary).append(" ");
        }
        return binaryString.toString().trim();
    }

    // 将字符串转换为16进制
    public static String stringToHex(String input) {
        StringBuilder hexString = new StringBuilder();
        for (char c : input.toCharArray()) {
            hexString.append(String.format("%02X", (int) c));
        }
        return hexString.toString();
    }


    // 处理Unicode转义字符的函数
    public static String convertUnicodeEscape(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            if (input.charAt(i) == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
                // 处理 Unicode 转义字符
                String unicodeValue = input.substring(i + 2, i + 6);
                char unicodeChar = (char) Integer.parseInt(unicodeValue, 16);
                result.append(unicodeChar);
                i += 6; // 跳过 "uXXXX" 部分
            } else {
                result.append(input.charAt(i));
                i++;
            }
        }

        return result.toString();
    }
}

