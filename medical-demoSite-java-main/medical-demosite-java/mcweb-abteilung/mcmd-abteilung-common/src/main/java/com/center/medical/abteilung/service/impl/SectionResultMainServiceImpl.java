package com.center.medical.abteilung.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.DivisionFaircheckParam;
import com.center.medical.abteilung.bean.vo.AdiconGridDataVo;
import com.center.medical.abteilung.bean.vo.DivisionFaircheckVo;
import com.center.medical.abteilung.bean.vo.DivisionInspectionVo;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.enums.KsId;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.PatienttypeMapper;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.reception.bean.model.JlcGrid;
import com.center.medical.reception.bean.model.OutsideHand;
import com.center.medical.reception.bean.model.TestGrid;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import com.center.medical.reception.bean.param.SetAdiconParam;
import com.center.medical.reception.dao.OutsideHandMapper;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * KS科室检查结果主表(SectionResultMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
@Slf4j
@Service("sectionResultMainService")
@RequiredArgsConstructor
public class SectionResultMainServiceImpl extends ServiceImpl<SectionResultMainMapper, SectionResultMain> implements SectionResultMainService {

    private final SectionResultMainMapper sectionResultMainMapper;
    private final PeispatientMapper peispatientMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final PatienttypeMapper patienttypeMapper;
    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final Snowflake snowflake;
    private final OutsideHandMapper outsideHandMapper;
    private final OutsidePictrueMapper outsidePictrueMapper;
    private final DescribeMapper describeMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final SysUserMapper sysUserMapper;
    private final TjregMapper tjregMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final MapperFacade mapperFacade;
    private final BasexamltemService basexamltemService;


    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultMain> getPage(PageParam<SectionResultMain> page, SectionResultMain param) {
        return sectionResultMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultMain getInfoById(String id) {
        return sectionResultMainMapper.getInfoById(id);
    }

    /**
     * 检验科读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Transactional(readOnly = true) //只读事务
    @Override
    public DivisionInspectionVo search(String patientCode, String ksId) {
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode).eq("f_registered", 1));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        //科室id
        if (StringUtils.isEmpty(ksId)) {
            SysDept dept = sysDeptMapper.selectDeptByName("检验科");
            ksId = String.valueOf(dept.getDeptId());
        }
        //体检者收费项目表
        Long feeitem = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("id_ks", ksId).eq("sfjj", 0)
                .eq("f_giveup", 0).eq("change_item", 0).isNull("f_transferedhl7")
        );
        if (feeitem == 0) {
            throw new ServiceException("error@体检号" + patientCode + "没有本科室收费项目！");
        }

        //科室检查结果
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode)
        );

        //设置返回对象
        DivisionInspectionVo divisionInspectionVo = new DivisionInspectionVo();
        divisionInspectionVo.setSectionResultMain(main);
        divisionInspectionVo.setPeispatient(patient);
        divisionInspectionVo.setPicture(getPicture(patient));
        divisionInspectionVo.setIsVIP(getIdPatientClass(patient));
        return divisionInspectionVo;
    }


    /**
     * 获取相片
     *
     * @param patient
     * @return
     */
    public String getPicture(Peispatient patient) {
        if (ObjectUtils.isEmpty(patient)) {
            return "";
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patient.getPatientcode()));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
    }


    /**
     * 获取体检者名称
     *
     * @param patient
     * @return
     */
    public final String getIdPatientClass(Peispatient patient) {
        String result = "";
        if (ObjectUtils.isNotEmpty(patient)) {
            Patienttype pt = patienttypeMapper.selectOne(new QueryWrapper<Patienttype>()
                    .eq("id", patient.getIdPatientclass()));
            if (ObjectUtils.isNotEmpty(pt)) {
                result = ObjectUtils.isEmpty(pt.getPatientName()) ? "" : pt.getPatientName();
            }
        }
        return result;
    }


    /**
     * 反审核
     *
     * @param divisionInspectionParam
     * @return
     */
    @Override
    public Boolean reverse(DivisionInspectionParam divisionInspectionParam) {
        String patientCode = divisionInspectionParam.getPatientCode();
        String ksId = divisionInspectionParam.getKsId();
        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", divisionInspectionParam.getPatientCode()));
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("反审核失败，该体检号尚未登记！");
        }
        if (ObjectUtils.isNotEmpty(peispatient.getFFinallocked()) && peispatient.getFFinallocked() == 1) {
            throw new ServiceException("反审核失败，该体检号总检已锁定！");
        }
        if ((ObjectUtils.isNotEmpty(peispatient.getZytjzt()) && peispatient.getZytjzt() == 1)
                || (ObjectUtils.isNotEmpty(peispatient.getJktjzt()) && peispatient.getJktjzt() == 1)) {
            throw new ServiceException("反审核失败，该体检号总检已完成！");
        }
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("id_ks", ksId)
                .eq("sfjj", 0).eq("f_giveup", 0).isNull("f_giveup")
                .eq("change_item", 0).eq("f_feecharged", 1));
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(main) || ObjectUtils.isEmpty(main.getIsAudit()) || main.getIsAudit() != 1) {
            throw new ServiceException("反审核失败，该体检号未审核！");
        }
        for (Peispatientfeeitem feeitem : feeitems) {
            feeitem.setFExaminated(0);
            peispatientfeeitemMapper.updateById(feeitem);
        }
        peispatient.setFReadytofinal(0);
        peispatientMapper.updateById(peispatient);
        //设置为未审核状态
        main.setIsAudit(0);
        sectionResultMainMapper.updateById(main);
        return Boolean.TRUE;
    }

    /**
     * 审核
     *
     * @param divisionInspectionParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(DivisionInspectionParam divisionInspectionParam) {
        String patientCode = divisionInspectionParam.getPatientCode();
        String ksId = divisionInspectionParam.getKsId();
        String conclusions = divisionInspectionParam.getConclusions();
        //体检者
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(patient) || patient.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        //检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode).eq("dep_id", ksId));
        main.setConclusions(conclusions);
        //设置为审核状态
        main.setIsAudit(1);
        sectionResultMainMapper.updateById(main);

        //LIS表
        List<TestGrid> testGrids = divisionInspectionParam.getTestGrids();
        for (TestGrid testGrid : testGrids) {
            Peispatientexamitem item = peispatientexamitemMapper.getInfoById(testGrid.getId());
            item.setReportRange(testGrid.getReportRange());
            item.setExamitemvaluesreport(testGrid.getExamitemvaluesreport());
            peispatientexamitemMapper.updateById(item);
            //体检者收费项目表
            Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", patientCode)
                    .eq("id_examfeeitem", item.getIdExamfeeitem()));
            feeitem.setFExaminated(1);
            peispatientfeeitemMapper.updateById(feeitem);
        }
        //检查结果子表
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>().eq("main_id", main.getId()));
        List<JlcGrid> jlcGrids = divisionInspectionParam.getJlcGrids();
        for (JlcGrid jlcGrid : jlcGrids) {
            SectionResultTwo two = new SectionResultTwo();
            two.setMainId(main.getId());
            two.setPatientcode(patientCode);
            two.setBasconclusionId(jlcGrid.getJlcId());
            two.setIsUnchecked(0);
            two.setVerdictId(jlcGrid.getVerdictId());
            two.setChargesId(jlcGrid.getChargesId());
            two.setDivisionId(ksId);
            two.setId(String.valueOf(snowflake.nextId()));
            two.setCreatedate(new Date());
            sectionResultTwoMapper.insert(two);
        }
        //体检者表
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>().eq("change_item", 0)
                .eq("id_patient", patientCode)
                .eq("sfjj", 0).ne("f_giveup", 1)
                .isNotNull("id_ks")
                .isNull("f_examinated").or().ne("f_examinated", 1));
        if (CollectionUtils.isEmpty(peispatientfeeitems)) {
            patient.setFReadytofinal(1);
            List<Peispatientfeeitem> otherItems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientCode).eq("change_item", 0)
                    .ne("f_giveup", 1).eq("sfjj", 0)
                    .isNull("id_ks")
            );
            //设置未关联科室项目为已检
            for (Peispatientfeeitem other : otherItems) {
                other.setFExaminated(1);
                peispatientfeeitemMapper.updateById(other);
            }
        }
        patient.setFExamstarted(1);
        peispatientMapper.updateById(patient);
        return Boolean.TRUE;
    }


    /**
     * 保存结伦词
     *
     * @param divisionInspectionParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveJlc(DivisionInspectionParam divisionInspectionParam) {
        //取出属性
        List<JlcGrid> list = divisionInspectionParam.getJlcGrids();
        String patientCode = divisionInspectionParam.getPatientCode();
        String ksId = divisionInspectionParam.getKsId();

        SectionResultMain stm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode).eq("dep_id", ksId));
        //主表id
        if (ObjectUtils.isNotEmpty(stm)) {
            String mainId = stm.getId();
            sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode).eq("division_id", ksId));
            //设置属性，插入
            for (JlcGrid jlcGrid : list) {
                SectionResultTwo two = new SectionResultTwo();
                two.setMainId(mainId);
                two.setPatientcode(patientCode);
                two.setDivisionId(ksId);
                two.setBasconclusionId(jlcGrid.getJlcId());
                two.setIsUnchecked(0);
                two.setVerdictId(jlcGrid.getVerdictId());
                two.setChargesId(jlcGrid.getChargesId());
                two.setId(String.valueOf(snowflake.nextId()));
                two.setCreatedate(new Date());
                sectionResultTwoMapper.insert(two);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 清除数据
     *
     * @param patientcode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean clear(String patientcode) {
        //外送项目结果不清除,状态不改变
        List<OutsideHand> ohs = outsideHandMapper.selectList(new QueryWrapper<OutsideHand>().eq("patientcode", patientcode));
        List<OutsidePictrue> ops = outsidePictrueMapper.selectList(new QueryWrapper<OutsidePictrue>().eq("patientcode", patientcode));

        Set<String> outsideItemIds = new HashSet<String>();
        for (OutsideHand oh : ohs) {
            if (ObjectUtils.isNotEmpty(oh.getIdCharge())) {
                outsideItemIds.add(oh.getIdCharge());
            }
        }
        for (OutsidePictrue op : ops) {
            if (op.getChargeId() != null) {
                outsideItemIds.addAll(Arrays.asList(op.getChargeId().split(",")));
            }
        }
        //项目已检标志
        List<Peispatientfeeitem> pis = null;
        if (outsideItemIds.size() > 0) {
            pis = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode).eq("id_ks", KsId.JYKID.value())
                    .notIn("id_examfeeitem", outsideItemIds)
            );
        } else {
            pis = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode).eq("id_ks", KsId.JYKID.value())
            );
        }
        //如果没有本科室项目，返回
        if (pis.size() == 0) {
            return Boolean.TRUE;
        }

        for (Peispatientfeeitem pi : pis) {
            pi.setFExaminated(0);
            peispatientfeeitemMapper.updateById(pi);
        }
        //分检完成标志
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("清除失败，体检号" + patientcode + "不存在！");
        }
        if (ObjectUtils.isNotEmpty(patient.getJktjzt()) && patient.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (ObjectUtils.isEmpty(patient.getDoctorfinalNameR()) ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (ObjectUtils.isNotEmpty(patient.getZytjzt()) && patient.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (ObjectUtils.isEmpty(patient.getPatientnameencoded()) ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (ObjectUtils.isNotEmpty(patient.getFFinallocked()) && patient.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (ObjectUtils.isEmpty(patient.getIdDoctorapply()) ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (ObjectUtils.isNotEmpty(patient.getIdGuidenurse()) && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (ObjectUtils.isEmpty(patient.getParsedassignedsuiteandfi()) ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        patient.setFReadytofinal(0);
        peispatientMapper.updateById(patient);
        //删除体检数据
        sectionResultMainMapper.delete(new QueryWrapper<SectionResultMain>().eq("patientcode", patientcode)
                .eq("dep_id", String.valueOf(KsId.JYKID.value())));
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientcode)
                .eq("division_id", String.valueOf(KsId.JYKID.value())));
        describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientcode)
                .eq("dep_id", String.valueOf(KsId.JYKID.value())));
        riskclientconMapper.delete(new QueryWrapper<Riskclientcon>().eq("patientcode", patientcode)
                .eq("division_id", String.valueOf(KsId.JYKID.value())));
        if (outsideItemIds.size() > 0) {
            peispatientexamitemMapper.delete(new QueryWrapper<Peispatientexamitem>()
                    .eq("patientcode", patientcode)
                    .eq("dep_id", KsId.JYKID.value())
                    .notIn("id_examFeeItem", outsideItemIds)
            );
        } else {
            peispatientexamitemMapper.delete(new QueryWrapper<Peispatientexamitem>()
                    .eq("patientcode", patientcode)
                    .eq("dep_id", KsId.JYKID.value())
            );
        }
        return Boolean.TRUE;
    }

    /**
     * 设置艾迪康代码
     *
     * @param testGrids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setAdicon(List<SetAdiconParam> testGrids) {
        List<Basexamltem> basexamltems = mapperFacade.mapAsList(testGrids, Basexamltem.class);
        basexamltemService.updateBatchById(basexamltems);
        return Boolean.TRUE;
    }

    /**
     * 一般检查读卡
     *
     * @param divisionFaircheckParam
     * @return
     */
    @Override
    public DivisionFaircheckVo searchFaircheck(DivisionFaircheckParam divisionFaircheckParam) {
        String patientCode = divisionFaircheckParam.getPatientCode();
        String ksId = divisionFaircheckParam.getKsId();
        DivisionFaircheckVo divisionFaircheckVo = new DivisionFaircheckVo();
        Peispatient patient = null;
        if (StringUtils.isNotBlank(patientCode)) {
            //已登记
            patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode)
                    .eq("f_registered", 1));
        }

        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记");
        }
        if (ObjectUtils.isNotEmpty(patient.getFPaused()) && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("error@该体检号已被禁检");
        }
        //任务分组ID
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (ObjectUtils.isNotEmpty(idOrgreservationgroup)) {
            Peisorgreservationgroup org = peisorgreservationgroupMapper.selectOne(new QueryWrapper<Peisorgreservationgroup>()
                    .eq("id", idOrgreservationgroup).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(org) && ObjectUtils.isNotEmpty(org.getFGrouppaused()) && org.getFGrouppaused().intValue() == 1) {
                throw new ServiceException("error@该体检号已被禁检");
            }
        }

        //体检者收费项目
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("id_ks", ksId).isNull("f_transferedhl7")
                .eq("sfjj", 0).eq("f_giveup", 0).eq("change_item", 0));
        if (CollectionUtils.isEmpty(feeitems)) {
            throw new ServiceException("error@该体检号没有一般检查项目");
        }
        Peispatientfeeitem feeitem = null;
        StringBuilder unchargeItems = new StringBuilder();
        for (Peispatientfeeitem item : feeitems) {
            if (ObjectUtils.isNotEmpty(item.getFFeecharged()) && item.getFFeecharged() == 1) {
                feeitem = item;
            } else {
                unchargeItems.append(item.getExamfeeitemName() + ",");
            }
        }
        if (ObjectUtils.isEmpty(feeitem)) {
            throw new ServiceException("error@该体检号尚未缴费");
        }
        if (unchargeItems.length() > 0) {
            divisionFaircheckVo.setUncharged(unchargeItems.substring(0, unchargeItems.length() - 1));
        }
        //设置属性
        divisionFaircheckVo.setPatient(patient);
        divisionFaircheckVo.setPicture(getPicture(patient));
        divisionFaircheckVo.setIsVIP(getIdPatientClass(patient));
        divisionFaircheckVo.setFeeitem(feeitem);
        //科室检查结果
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode));

        //审核过
        if (ObjectUtils.isNotEmpty(main)) {
            //检查人ID
            String rummagerId = main.getRummagerId();
            main.setRummagerId(rummagerId);
            //检查人姓名
            if (ObjectUtils.isNotEmpty(rummagerId)) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(rummagerId);
                if (ObjectUtils.isNotEmpty(rummager)) {
                    main.setRummagerName(rummager.getUserName());
                }
            }
            //录入人ID
            String writeId = main.getWriteId();
            if (ObjectUtils.isNotEmpty(writeId)) {
                SysUser writer = sysUserMapper.selectUserByUserNo(writeId);
                if (ObjectUtils.isNotEmpty(writer)) {
                    main.setWriteName(writer.getUserName());
                }
            }
            divisionFaircheckVo.setSectionResultMain(main);
            //一般检查表
            Tjreg reg = tjregMapper.selectOne(new QueryWrapper<Tjreg>().eq("patientCode", patientCode));
            divisionFaircheckVo.setTjreg(reg);
        }
        return divisionFaircheckVo;
    }

    /**
     * 根据体检号获取检查结果
     *
     * @param patientCode 体检号
     * @return
     */
    @Override
    public String getHealthSummarize(String patientCode) {
        List<SectionResultMain> list = sectionResultMainMapper.getHealthSummarize(patientCode);
        StringBuilder summary = new StringBuilder();
        int j = 1;
        for (SectionResultMain main : list) {
            String con = main.getConclusions();
            if (StringUtils.isNotEmpty(con) && StringUtils.isNotEmpty(main.getDeptName())) {
                summary.append(j + "、" + main.getDeptName());
                summary.append("\n");
                summary.append(con + Constants.BR);
                j++;
            }
        }
        return summary.toString();
    }

    /**
     * 获取检验科艾迪康列表
     * @param patientCode
     * @return
     */
    @Override
    public List<AdiconGridDataVo> getAdiconGridData(String patientCode) {
        return sectionResultMainMapper.getAdiconGridData(patientCode);
    }
}

