package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.dto.DFDataDto;
import com.center.medical.abteilung.bean.dto.DFFormdataDto;
import com.center.medical.abteilung.bean.dto.DFGriddataDto;
import com.center.medical.abteilung.bean.model.JlcGridFaircheck;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.DFsaOrUpParam;
import com.center.medical.abteilung.bean.param.DivisionFaircheckParam;
import com.center.medical.abteilung.bean.param.GetSignParam;
import com.center.medical.abteilung.bean.vo.GetSignVo;
import com.center.medical.abteilung.dao.DivisionFaircheckMapper;
import com.center.medical.abteilung.dao.DivisionMapper;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.DivisionFaircheckService;
import com.center.medical.bean.enums.JcxmIds;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.BasconclusionMapper;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.BasexamltemSignMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("divisionFaircheckService")
@RequiredArgsConstructor
public class DivisionFaircheckServiceImpl extends ServiceImpl<DivisionFaircheckMapper, Peispatient> implements DivisionFaircheckService {

    private final DivisionFaircheckMapper divisionFaircheckMapper;
    private final PeispatientMapper peispatientMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final TjregMapper tjregMapper;
    private final RiskclientMapper riskclientMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final DescribeMapper describeMapper;
    private final SysUserMapper sysUserMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeisStateMapper peisStateMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final ItemsMapper itemsMapper;
    private final DivisionMapper divisionMapper;
    private final MapperFacade mapperFacade;
    private final OutsideMainService outsideMainService;
    private final PeispatientfeeitemService peispatientfeeitemService;

    /**
     * 体检者表格数据
     *
     * @param page
     * @param ksId
     * @return
     */
    @Override
    public IPage<Peispatient> listData(PageParam<Peispatient> page, String ksId) {
        //今天的开始和结束时间
        DateTime start = DateUtil.beginOfDay(new Date());
        DateTime end = DateUtil.endOfDay(new Date());
        IPage<Peispatient> iPage = divisionFaircheckMapper.listData(page, ksId, start, end);
        return iPage;
    }


    /**
     * 审核
     *
     * @param divisionFaircheckParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(DFsaOrUpParam divisionFaircheckParam) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ksId = divisionFaircheckParam.getKsId();
        String patientCode = divisionFaircheckParam.getPatientCode();
        //审核时间
        Date auditTime = new Date();
        //审核人Id
        String auditId = SecurityUtils.getUserNo();
        //分科检查医师
        String examdoctorNameR = SecurityUtils.getUsername();
        DFFormdataDto tjregs = divisionFaircheckParam.getFormdata();
        ////危急值(用于判断是否插入高危人员)
        int isDanger = StringUtils.isEmpty(tjregs.getIsDanger()) ? 0 : Integer.parseInt(tjregs.getIsDanger());
        //弃检
        String isUnchecked = tjregs.getIsUnchecked();
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));//体检者
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
        patient.setModifydate(new Date());
        Integer shortCode = patient.getShortCode();
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
                    .in("harm_id", jhys.split(",")).eq("medical_type", medicaltype));
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getExamId(), cei);
            }
        }
        Integer dangerFlag = null;
        Integer dangerLevel = null;
        if (isDanger >= 1) {
            dangerFlag = 1;
            dangerLevel = isDanger;
        } else {
            dangerFlag = 0;
            dangerLevel = 0;
        }

        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("id_ks", ksId).eq("f_feecharged", 1).isNull("f_transferedhl7")
                .eq("sfjj", 0).eq("f_giveup", 0).eq("change_item", 0));

        SectionResultMain newMain = new SectionResultMain();
        DFDataDto xjForm = divisionFaircheckParam.getData();

        //如果弃检
        if ("1".equals(isUnchecked)) {
            if (StringUtils.isNotBlank(xjForm.getId())) {
                SectionResultMain oldMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>().eq("id", xjForm.getId()));
                if (oldMain.getIsAudit() == 1) {
                    throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
                }
                sectionResultMainMapper.delete(new QueryWrapper<SectionResultMain>().eq("dep_id", ksId)
                        .eq("patientcode", patientCode));
                sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                        .eq("division_id", ksId).eq("patientcode", patientCode));
                tjregMapper.delete(new QueryWrapper<Tjreg>().eq("patientCode", patientCode));
                riskclientMapper.delete(new QueryWrapper<Riskclient>().eq("tjid", patientCode).eq("gwxm", "血压结论"));
                riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientCode).eq("gwxm", "血压结论"));
                describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientCode).in("item_id",
                        new String[]{JcxmIds.SSYID.value(), JcxmIds.SXYID.value(), JcxmIds.XYJLID.value(),
                                JcxmIds.SGID.value(), JcxmIds.TZID.value(), JcxmIds.TZZSID.value(), JcxmIds.MBID.value(),
                                JcxmIds.YBZKID.value(), JcxmIds.HXPLID.value(), JcxmIds.TEMPERATURE_EXAM_ID.value()}));
            }
            for (Peispatientfeeitem feeitem : feeitems) {
                feeitem.setFGiveup(1);
                peispatientfeeitemMapper.updateById(feeitem);
            }
        } else {
            List<Items> eis = divisionFaircheckMapper.selectItemAndBase(patientCode, ksId);

            Map<String, Map<String, String>> eidata = new LinkedHashMap<String, Map<String, String>>();
            for (Items os : eis) {
                Map<String, String> in_map = new HashMap<String, String>();
                in_map.put("itemId", os.getId() == null ? null : os.getId().toString());
                in_map.put("itemName", os.getExamfeeitemName() == null ? null : os.getExamfeeitemName().toString());
                in_map.put("feeName", os.getExamfeeitemNameprn() == null ? null : os.getExamfeeitemNameprn().toString());
                in_map.put("examNamePrn", os.getExamitemNameprn() == null ? (null) : os.getExamitemNameprn().toString());
                eidata.put(os.getBasexamltemId().toString(), in_map);
            }

            //检查结果主表
            String mainId = xjForm.getId();
            newMain.setId(mainId);
            newMain.setRummagerId(xjForm.getRummagerId());
            Date rummagerTime = xjForm.getRummagerTime();
            newMain.setRummagerTime(rummagerTime);
            SysUser rummager = sysUserMapper.selectUserByUserNo(xjForm.getRummagerId());
            if (ObjectUtils.isNotEmpty(rummager)) {
                newMain.setRummagerName(rummager.getUserName());
            }
            newMain.setIsFinish(0);
            newMain.setAuditName(examdoctorNameR);
            newMain.setWriteTime(xjForm.getWriteTime());
            newMain.setWriteId(xjForm.getWriteId());
            newMain.setDepId(ksId);
            newMain.setPatientcode(patientCode);
            newMain.setShortCode(shortCode);
            newMain.setAuditId(auditId);
            newMain.setAuditTime(auditTime);
            newMain.setIsDanager(dangerFlag);
            newMain.setDanagerLevel(dangerLevel);
            newMain.setIsAudit(1);
            newMain.setConclusions(xjForm.getConclusions());
            newMain.setAssociativeTable("TJREG");
            SectionResultMain oldMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                    .eq("patientcode", patientCode).eq("dep_id", ksId));
            //第一次审核
            if (ObjectUtils.isEmpty(oldMain)) {
                sectionResultMainMapper.insert(newMain);
                mainId = newMain.getId();
            } else {
                if (ObjectUtils.isNotEmpty(oldMain.getIsAudit()) && oldMain.getIsAudit() == 1) {
                    throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
                }
                sectionResultMainMapper.updateById(newMain);
            }

            //删除子表
            sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                    .eq("division_id", ksId).eq("patientcode", patientCode));

            //一般检查表
            Tjreg tjreg = mapperFacade.map(tjregs, Tjreg.class);
            tjreg.setPatientcode(patientCode);
            tjreg.setShortCode(shortCode);
            tjreg.setTjrq(auditTime);
            tjreg.setXj(newMain.getConclusions());
            tjreg.setCreateId(auditId);
            tjreg.setDepId(ksId);
            tjreg.setScbz(0);
            tjreg.setIsUnchecked(tjregs.getIsUnchecked());
            if (StringUtils.isBlank(tjreg.getId())) {
                tjregMapper.insert(tjreg);
            } else {
                tjreg.setModifyId(auditId);
                Tjreg oldTjreg = tjregMapper.getInfoById(tjreg.getId());
                BeanUtils.copyProperties(tjreg, oldTjreg, new String[]{"createId"});
                tjregMapper.updateById(oldTjreg);
            }
            //所有阳性小结
            StringBuilder positivesummary = new StringBuilder();
            //危急值级别
            Integer main_danagerLevel = null;

            //检查结果子表
            String xy_sign = null;
            Integer xy_wjz = null;
            String xy_signList = null;
            String tzzs_singList = null;
            List<DFGriddataDto> jlcGridFairchecks = divisionFaircheckParam.getGriddata();

            for (DFGriddataDto jlcGrid : jlcGridFairchecks) {
                SectionResultTwo two = new SectionResultTwo();
                two.setMainId(mainId);
                String verdictId = null;
                StringBuilder ms = new StringBuilder();
                Basexamltem ltem = null;
                if (StringUtils.isNotBlank(verdictId)) {
                    ltem = basexamltemMapper.getInfoById(verdictId);
                    ms.append(ltem.getExamitemName() + ":");
                }
                if (ObjectUtils.isEmpty(jlcGrid.getExamType())) {
                    two.setTjlx(0);
                    verdictId = null;
                } else if ("xy".equals(jlcGrid.getExamType())) {
                    verdictId = JcxmIds.XYJLID.value();
                    xy_signList = jlcGrid.getTzc();
                    ms.append(tjreg.getXy() + ",");
                } else if ("bmi".equals(jlcGrid.getExamType())) {
                    verdictId = JcxmIds.TZZSID.value();
                    tzzs_singList = jlcGrid.getTzc();
                    ms.append(tjreg.getBmi() + ",");
                } else if ("yyzk".equals(jlcGrid.getExamType())) {
                    verdictId = JcxmIds.YBZKID.value();
                    tzzs_singList = jlcGrid.getTzc();
                }
                if (ObjectUtils.isNotEmpty(jlcGrid.getExamType())) {
                    if ("2".equals(tjlx)) {
                        if (ceis.get(verdictId) != null) {
                            two.setTjlx(1);
                        } else {
                            two.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        two.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        two.setTjlx(1);
                    }
                }
                two.setVerdictId(verdictId);
                two.setNodule(jlcGrid.getTzcId());
                //体征词Id
                if (StringUtils.isNotBlank(jlcGrid.getTzcId())) {
                    //检查项目体证词关联表
                    BasexamltemSign sign = basexamltemSignMapper.getInfoById(jlcGrid.getTzcId());
                    if (ObjectUtils.isNotEmpty(sign)) {
                        if ("xy".equals(jlcGrid.getExamType())) {
                            xy_sign = sign.getName();
                            xy_wjz = sign.getIntensiveLevel();
                        }
                        two.setPosistive(sign.getIsPositive());
                        if (ObjectUtils.isNotEmpty(sign.getIsPositive()) && sign.getIsPositive() == 1 && ObjectUtils.isNotEmpty(ltem)) {
                            positivesummary.append(ltem.getExamitemName() + ":" + sign.getName() + ";");
                        }
                        //子表为体征词重症级别
                        if (ObjectUtils.isNotEmpty(sign.getIntensiveLevel()) && sign.getIntensiveLevel() >= 1) {
                            two.setIsDanger(1);
                            two.setIntensiveLevel(sign.getIntensiveLevel());
                        } else {
                            two.setIsDanger(0);
                            two.setIntensiveLevel(0);
                        }
                        if (ObjectUtils.isEmpty(main_danagerLevel) || main_danagerLevel < sign.getIntensiveLevel()) {
                            main_danagerLevel = sign.getIntensiveLevel();
                        }
                        ms.append(sign.getName());
                    }
                }
                two.setMs(ms.toString());
                two.setPatientcode(patientCode);
                two.setShortCode(shortCode);
                two.setIsUnchecked(0);
                two.setBasconclusionId(jlcGrid.getJlcId());
                two.setDivisionId(ksId);
                if (ObjectUtils.isNotEmpty(verdictId) && ObjectUtils.isNotEmpty(eidata.get(verdictId))) {
                    two.setChargesId(eidata.get(verdictId).get("itemId"));
                }
                sectionResultTwoMapper.insert(two);
            }



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
            riskclientMapper.delete(new QueryWrapper<Riskclient>().eq("tjid", patientCode).eq("gwxm", "血压结论"));
            riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientCode).eq("gwxm", "血压结论"));

            if (isDanger == 2 || isDanger == 3) {
                Riskclient rc = new Riskclient();
                rc.setTjid(patientCode);
                rc.setGwrymc(patient.getPatientname());
                rc.setNl(patient.getAge() == null ? "" : patient.getAge().toString());
                rc.setXb(patient.getIdSex());
                rc.setLxdh(patient.getPhone());
                rc.setGwxm("血压结论");//只有血压报高危
                rc.setTjlx(Integer.valueOf(patient.getIdExamtype()));
                rc.setTjzt(0);
                rc.setTirq(newMain.getRummagerTime());
                rc.setCid(SecurityUtils.getCId());
                rc.setIdOrg(patient.getIdOrg());
                rc.setIdOpendoctor(patient.getIdOpendoctor());
                rc.setCid(patient.getHospitalcode());
                riskclientMapper.insert(rc);
                String risk_mainid = rc.getId();

                Riskclientcon rcc = new Riskclientcon();
                rcc.setRiskid(risk_mainid);
                rcc.setGwxm("血压结论");
                rcc.setDivisionId(ksId);
                rcc.setWjzxj(xy_sign);
                rcc.setDoctorId(auditId);
                rcc.setCheckTime(auditTime);
                rcc.setPatientcode(patientCode);
                rcc.setWjzjb(xy_wjz);
                rcc.setSfxm(eidata.get(JcxmIds.XYJLID.value()) != null ? eidata.get(JcxmIds.XYJLID.value()).get("itemName") : null);
                riskclientconMapper.insert(rcc);
            }

            //describe表
            StringBuilder zy_conclusions = new StringBuilder();
            describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientCode)
                    .in("item_id", new String[]{JcxmIds.SSYID.value(), JcxmIds.SXYID.value(), JcxmIds.XYJLID.value(), JcxmIds.SGID.value(), JcxmIds.TZID.value(), JcxmIds.TZZSID.value(), JcxmIds.MBID.value(), JcxmIds.YBZKID.value(), JcxmIds.HXPLID.value()}));
            SysDept dept = sysDeptMapper.selectDeptById(Long.valueOf(ksId));
            String depDes = ObjectUtils.isEmpty(dept) ? null : dept.getDescription();
            for (String examId : eidata.keySet()) {
                Describe des = new Describe();
                Map<String, String> map = eidata.get(examId);
                String feeId = map.get("itemId");
                String feeName = map.get("feeName");
                String examName = map.get("examNamePrn");
                if (JcxmIds.SSYID.value().equals(examId)) {
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.SSYID.value());
                    //des.setItemName("收缩压");
                    Double ssy = tjregs.getSsy();
                    des.setSignList(ssy == null ? null : ssy.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (ssy != null) {
                                zy_conclusions.append("收缩压:" + ssy.intValue() + "mmHg;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (ssy != null) {
                            zy_conclusions.append("收缩压:" + ssy.intValue() + "mmHg;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.SXYID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.SXYID.value());
                    //des.setItemName("舒张压");
                    Double szy = tjregs.getSzy();
                    des.setSignList(ObjectUtils.isEmpty(szy) ? null : szy.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (szy != null) {
                                zy_conclusions.append("舒张压:" + szy.intValue() + "mmHg;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (szy != null) {
                            zy_conclusions.append("舒张压:" + szy.intValue() + "mmHg;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);

                } else if (JcxmIds.MBID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.MBID.value());
                    //des.setItemName("脉搏");
                    Double mb = tjregs.getMb();
                    des.setSignList(ObjectUtils.isEmpty(mb) ? null : mb.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (mb != null) {
                                zy_conclusions.append("脉搏:" + mb.intValue() + "次/分钟;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (mb != null) {
                            zy_conclusions.append("脉搏:" + mb.intValue() + "次/分钟;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);

                } else if (JcxmIds.XYJLID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.XYJLID.value());
                    des.setItemName("血压结论");
                    des.setSignList(ObjectUtils.isEmpty(xy_signList) ? "正常" : xy_signList);
                    des.setDepId(ksId);
                    String xyms = ObjectUtils.isEmpty(tjreg.getXyms()) ? null : tjreg.getXyms().replaceAll("<[^>]*>", "");
                    if ("2".equals(tjlx)) {
                        if (ObjectUtils.isNotEmpty(ceis.get(des.getItemId()))) {
                            des.setTjlx(1);
                            if (ObjectUtils.isNotEmpty(xyms)) {
                                zy_conclusions.append("血压结论:" + xyms + ";");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (xy_signList != null) {
                            zy_conclusions.append("血压结论:" + xy_signList + ";");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);

                } else if (JcxmIds.YBZKID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.YBZKID.value());
                    des.setItemName("营养状况");
                    String comState = tjreg.getCommonState();
                    des.setSignList(comState);
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (StringUtils.isNotEmpty(comState)) {
                                zy_conclusions.append("营养状况:" + comState + ";");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (StringUtils.isNotEmpty(comState)) {
                            zy_conclusions.append("营养状况:" + comState + ";");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.YBZKID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.HXPLID.value());
                    des.setItemName("呼吸频率");
                    Double rate = tjreg.getRespiratoryRate();
                    des.setSignList(ObjectUtils.isEmpty(rate) ? null : rate.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (ObjectUtils.isNotEmpty(rate)) {
                                zy_conclusions.append("呼吸频率:" + rate.intValue() + "次/分钟;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (ObjectUtils.isNotEmpty(rate)) {
                            zy_conclusions.append("呼吸频率:" + rate.intValue() + "次/分钟;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.TEMPERATURE_EXAM_ID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.TEMPERATURE_EXAM_ID.value());
                    des.setItemName("体温测量");
                    Double temperature = tjreg.getTemperature();
                    des.setSignList(temperature == null ? null : temperature.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (temperature != null) {
                                zy_conclusions.append("体温测量:" + temperature + "℃;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (temperature != null) {
                            zy_conclusions.append("体温测量:" + temperature + "℃;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.TZZSID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.TZZSID.value());
                    des.setItemName("体重指数");
                    des.setSignList(tzzs_singList == null ? "正常" : tzzs_singList);
                    des.setDepId(ksId);
                    String tzms = StringUtils.isEmpty(tjreg.getBmims()) ? null : tjreg.getBmims().replaceAll("<[^>]*>", "");
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (tzms != null) {
                                zy_conclusions.append("体重指数:" + tzms + ";");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (tzzs_singList != null) {
                            zy_conclusions.append("体重指数:" + tzzs_singList + ";");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.SGID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.SGID.value());
                    des.setItemName("身高");
                    String sg = tjreg.getSg();
                    des.setSignList(sg == null ? null : sg.toString());
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (StringUtils.isNotEmpty(sg)) {
                                zy_conclusions.append("身高:" + sg + "cm;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (StringUtils.isNotEmpty(sg)) {
                            zy_conclusions.append("身高:" + sg + "cm;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                } else if (JcxmIds.TZID.value().equals(examId)) {
                    des = new Describe();
                    des.setPatientcode(patientCode);
                    des.setShortCode(shortCode);
                    des.setItemId(JcxmIds.TZID.value());
                    des.setItemName("体重");
                    String tz = tjreg.getTz();
                    des.setSignList(tz == null ? null : tz);
                    des.setDepId(ksId);
                    if ("2".equals(tjlx)) {
                        if (ceis.get(des.getItemId()) != null) {
                            des.setTjlx(1);
                            if (StringUtils.isNotEmpty(tz)) {
                                zy_conclusions.append("体重:" + tz + "kg;");
                            }
                        } else {
                            des.setTjlx(0);
                        }
                    } else if ("0".equals(tjlx)) {
                        des.setTjlx(0);
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        des.setTjlx(1);
                        if (StringUtils.isNotEmpty(tz)) {
                            zy_conclusions.append("体重:" + tz + "kg;");
                        }
                    }
                    des.setDepDescription(depDes);
                    des.setItemName(examName);
                    des.setFeeId(feeId);
                    des.setFeeName(feeName);
                    describeMapper.insert(des);
                }
            }


            newMain.setZyConclusions(zy_conclusions.toString());
            sectionResultMainMapper.updateById(newMain);


            //设置非功能科室检查项目为已检
            boolean b = outsideMainService.getAllSfxmtzjStatus(patientCode);
            if (b) {
                patient.setFReadytofinal(1);//0:已备单 1:分检完成
                patient.setReadytofinalDate(new Date());
                setScbs(patient, 0);
                List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
                for (Peispatientfeeitem other : other_items) {
                    other.setFExaminated(1);
                }
                //批量更新
                peispatientfeeitemService.updateBatchById(other_items);
            }


            patient.setFExamstarted(1);//检查开始（反审核不用改）
            peispatientMapper.updateById(patient);
        }

        return Boolean.TRUE;
    }


    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        PeisState ps = peisStateMapper.selectOne(new QueryWrapper<PeisState>().eq("patientcode", patient.getPatientcode()));
        if (ObjectUtils.isEmpty(ps)) {
            ps = new PeisState();
            ps.setPatientcode(patient.getPatientcode());
            ps.setScbs(scbs);
            peisStateMapper.insert(ps);
        } else {
            ps.setScbs(scbs);
            peisStateMapper.updateById(ps);
        }
        return ps;
    }

    /**
     * 获取体征词
     *
     * @param getSignParam
     * @return
     */
    @Override
    public GetSignVo getSign(GetSignParam getSignParam) {
        GetSignVo getSignVo = new GetSignVo();
        String signId = "";
        //取出属性
        String examItemType = getSignParam.getExamItemType();
        Double examValue = getSignParam.getExamValue();
        Integer age = getSignParam.getAge();
        String idSex = getSignParam.getIdSex();
        //体重指数 bmi
        if ("bmi".equals(examItemType)) {
            if (examValue == -1) {
                return null;
            }
            //青少年bmi标准
            if (age >= 6 && age <= 18) {
                Map<String, String> config = Constants.MAP;
                String key = age + "_" + idSex + "_";
                if (examValue < Double.parseDouble(config.get(key + "xs"))) {
                    signId = Constants.XS;
                } else if (examValue >= Double.parseDouble(config.get(key + "fp"))) {
                    signId = Constants.FAT;
                } else if (examValue >= Double.parseDouble(config.get(key + "cz"))) {
                    signId = Constants.CZ;
                } else {
                    signId = Constants.TZZC;
                }
            } else {
                //成年bmi标准
                if (examValue < 18.5) {
                    signId = Constants.XS;
                } else if (examValue >= 18.5 && examValue < 25) {
                    signId = Constants.TZZC;
                } else if (examValue >= 25 && examValue < 30) {
                    signId = Constants.CZ;
                } else if (examValue >= 30 && examValue < 35) {
                    signId = Constants.QDFP;
                } else if (examValue >= 35 && examValue < 40) {
                    signId = Constants.ZDFP;
                } else if (examValue >= 40) {
                    signId = Constants.YZFP;
                }
            }
        } else if ("xy".equals(examItemType)) {
            //血压结论 xy
            if (examValue >= 45) {
                signId = Constants.XYZG;
            } else if (examValue < 45 && examValue >= 15) {
                signId = Constants.XYZC;
            } else if (examValue == -15) {
                signId = Constants.XYPD;
            } else if (examValue == 0) {
                return null;
            }
        }
        BasexamltemSign sign = basexamltemSignMapper.getInfoById(signId);
        Basexamltem item = basexamltemMapper.getInfoById(sign.getInspectId());
        Basconclusion con = basconclusionMapper.getInfoById(sign.getResultId());
        //设置属性
        getSignVo.setJcxm(item.getExamitemName());
        getSignVo.setTzc(sign.getName());
        getSignVo.setTzcId(signId);
        getSignVo.setJlcId(sign.getResultId());
        if (ObjectUtils.isNotEmpty(con)) {
            getSignVo.setJlcName(con.getName());
        }
        getSignVo.setIntensiveLevel(sign.getIntensiveLevel());
        return getSignVo;
    }

    /**
     * 结论词列表数据
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public List<GetSignVo> jlcData(String patientCode, String ksId) {
        //科室检查结果表
        List<SectionResultTwo> twos = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                .orderByDesc("createdate").eq("patientcode", patientCode).eq("division_id", ksId)
        );
        List<GetSignVo> result = new ArrayList<>();
        for (SectionResultTwo two : twos) {
            GetSignVo jlcDataVo = new GetSignVo();
            Basexamltem ltem = basexamltemMapper.getInfoById(two.getVerdictId());
            if (ObjectUtils.isNotEmpty(ltem)) {
                //检查项目
                jlcDataVo.setJcxm(ltem.getExamitemName());
            }
            BasexamltemSign sign = basexamltemSignMapper.getInfoById(two.getNodule());
            if (ObjectUtils.isNotEmpty(sign)) {
                jlcDataVo.setTzc(sign.getName());
            }
            jlcDataVo.setJlcId(two.getBasconclusionId());
            Basconclusion con = basconclusionMapper.getInfoById(two.getBasconclusionId());
            if (ObjectUtils.isNotEmpty(con)) {
                //结论词名称
                jlcDataVo.setJlcName(con.getName());
            }
            jlcDataVo.setTzcId(two.getNodule());
            String nodule = two.getNodule();
            String examType = "";
            if (Constants.XYPD.equals(nodule) || Constants.XYZG.equals(nodule) || Constants.XYZC.equals(nodule)) {
                examType = "xy";
            } else if (Constants.XS.equals(nodule) || Constants.TZZC.equals(nodule)
                    || Constants.QDFP.equals(nodule) || Constants.ZDFP.equals(nodule)
                    || Constants.YZFP.equals(nodule) || Constants.CZ.equals(nodule)) {
                examType = "bmi";
            } else if (ObjectUtils.isNotEmpty(two.getVerdictId()) && Constants.YBZKID.equals(two.getVerdictId())) {
                examType = "yyzk";
            }
            jlcDataVo.setExamType(examType);
            result.add(jlcDataVo);
        }
        return result;
    }

    /**
     * 保存结伦词
     *
     * @param divisionFaircheckParam
     * @return
     */
    @Override
    public Boolean saveJlc(DivisionFaircheckParam divisionFaircheckParam) {
        //保存数据
        List<JlcGridFaircheck> list = divisionFaircheckParam.getJlcGridFairchecks();
        String ksId = divisionFaircheckParam.getKsId();
        String patientCode = divisionFaircheckParam.getPatientCode();

        //KS科室检查结果主表
        SectionResultMain stm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>().eq("patientcode", patientCode).eq("dep_id", ksId));
        Integer shortCode = ToolUtil.getShortCodeByLong(patientCode);
        if (ObjectUtils.isEmpty(stm)) {
            stm = new SectionResultMain();
            stm.setDepId(ksId);//科室ID
            stm.setPatientcode(patientCode);//体检号
            stm.setShortCode(shortCode);
            sectionResultMainMapper.insert(stm);
        }
        String mainId = stm.getId();//主表id
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode", patientCode)
                .eq("division_id", ksId)
        );

        List<Items> eis = itemsMapper.selectWords(patientCode, ksId);

        Map<String, Map<String, String>> eidata = new HashMap<String, Map<String, String>>();
        for (Items os : eis) {
            Map<String, String> in_map = new HashMap<String, String>();
            in_map.put("itemId", os.getId() == null ? null : os.getId().toString());
            in_map.put("itemName", os.getExamitemName() == null ? null : os.getExamitemName().toString());
            eidata.put(os.getEId().toString(), in_map);
        }

        for (JlcGridFaircheck jlc : list) {
            SectionResultTwo two = new SectionResultTwo();
            two.setMainId(mainId);
            String verdictId = null;
            if (ObjectUtils.isEmpty(jlc.getExamType())) {
                verdictId = null;
            } else if ("xy".equals(jlc.getExamType())) {
                verdictId = Constants.XYJLID;
            } else if ("bmi".equals(jlc.getExamType())) {
                verdictId = Constants.TZZSID;
            } else if ("yyzk".equals(jlc.getExamType())) {
                verdictId = Constants.YBZKID;
            }
            two.setVerdictId(verdictId);
            two.setNodule(jlc.getTzcId());
            if (StringUtils.isNotBlank(jlc.getTzcId())) {
                BasexamltemSign sign = basexamltemSignMapper.getInfoById(jlc.getTzcId());
                if (ObjectUtils.isNotEmpty(sign)) {
                    two.setPosistive(sign.getIsPositive());
                    //子表为体征词重症级别
                    if (ObjectUtils.isNotEmpty(sign.getIntensiveLevel()) && sign.getIntensiveLevel() >= 1) {
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
            two.setBasconclusionId(jlc.getJlcId());
            two.setDivisionId(ksId);
            if (ObjectUtils.isNotEmpty(verdictId) && ObjectUtils.isNotEmpty(eidata.get(verdictId))) {
                two.setChargesId(eidata.get(verdictId).get("itemId"));
            }
            sectionResultTwoMapper.insert(two);
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
        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("反审核失败，该体检号尚未登记！");
        }
        if (peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }

        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("f_feecharged", 1)
                .eq("id_ks", ksId).eq("sfjj", 0)
                .eq("f_giveup", 0).eq("change_item", 0));

        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(main) || ObjectUtils.isEmpty(main.getIsAudit()) || main.getIsAudit() != 1) {
            throw new ServiceException("反审核失败，该体检号未审核！");
        }
        for (Peispatientfeeitem feeitem : feeitems) {
            feeitem.setFExaminated(0);
            peispatient.setFReadytofinal(0);
            setScbs(peispatient, 0);
            peispatientfeeitemMapper.updateById(feeitem);
        }
        main.setIsAudit(0);
        peispatientMapper.updateById(peispatient);
        sectionResultMainMapper.updateById(main);
        return Boolean.TRUE;
    }
}

