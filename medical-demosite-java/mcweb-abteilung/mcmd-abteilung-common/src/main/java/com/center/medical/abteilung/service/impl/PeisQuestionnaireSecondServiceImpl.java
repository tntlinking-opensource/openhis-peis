package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.PeisQuestionnaireSecond;
import com.center.medical.abteilung.dao.PeisQuestionnaireSecondMapper;
import com.center.medical.abteilung.service.PeisQuestionnaireSecondService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
@Slf4j
@Service("peisQuestionnaireSecondService")
@RequiredArgsConstructor
public class PeisQuestionnaireSecondServiceImpl extends ServiceImpl<PeisQuestionnaireSecondMapper, PeisQuestionnaireSecond> implements PeisQuestionnaireSecondService {

    private final PeisQuestionnaireSecondMapper peisQuestionnaireSecondMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PatienttypeService patienttypeService;
    private final PeisStateService peisStateService;
    private final SysDeptMapper sysDeptMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final OutsideMainService outsideMainService;

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisQuestionnaireSecond> getList(PageParam<PeisQuestionnaireSecond> page, PeisQuestionnaireSecond param) {
        return peisQuestionnaireSecondMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeisQuestionnaireSecond getInfoById(String id) {
        return peisQuestionnaireSecondMapper.getInfoById(id);
    }

    /**
     * 查询
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public HashMap<String, Object> search(String patientCode, String ksId) {
        //补全体检号
        patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode).eq("f_registered", 1));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        if (patient.getFPaused() != null && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("error@该体检号已被禁检！");
        }
        //任务分组ID
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
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
                .isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
        );
        if (ObjectUtils.isEmpty(feeitem)) {
            throw new ServiceException("error@该体检号没有本科室收费项目！");
        }
        //收费
        if (feeitem.getFFeecharged() == null || feeitem.getFFeecharged() != 1) {
            throw new ServiceException("error@该体检号尚未缴费！");
        }

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("isVIP", patienttypeService.getIdPatientClass(patient));
        //健康体检问卷实体类
        PeisQuestionnaireSecond second = peisQuestionnaireSecondMapper.selectOne(new QueryWrapper<PeisQuestionnaireSecond>().eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(second)) {
            //把上方的参数放进去
            PeisQuestionnaireSecond sec = new PeisQuestionnaireSecond();
            sec.setPatientcode(patientCode);
            sec.setPatientname(patient.getPatientname());
            sec.setIdSex(patient.getIdSex().toString());
            sec.setBirthdate(patient.getBirthdate());
            sec.setIdcardno(patient.getIdcardno());
            sec.setIsHan(patient.getIdNation() == null ? null : "1".equals(patient.getIdNation()) ? "1" : "0");
            sec.setPhone(patient.getPhone());
            sec.setIdProvince(patient.getIdResarea());
            data.put("second", sec);
        } else {
            data.put("second", second);
        }
        return data;
    }


    /**
     * 添加健康体检问卷
     *
     * @param second
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(PeisQuestionnaireSecond second) {
        String ksId = second.getKsId();
        String patientcode = second.getPatientcode();
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(second.getPatientcode());

        if (patient == null || patient.getFRegistered() != 1) {
            throw new ServiceException("保存失败，该体检号尚未登记！");
        }
        if (patient.getJktjzt() != null && patient.getJktjzt() == 1) {
            throw new ServiceException("保存失败，本体检者检查结果已被" + (patient.getDoctorfinalNameR() == null ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getZytjzt() != null && patient.getZytjzt() == 1) {
            throw new ServiceException("保存失败，本体检者检查结果已被" + (patient.getPatientnameencoded() == null ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getFFinallocked() != null && patient.getFFinallocked() == 1) {
            throw new ServiceException("保存失败，本体检者检查结果已被" + (patient.getIdDoctorapply() == null ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getIdGuidenurse() != null && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("保存失败，本体检者检查结果已被" + (patient.getParsedassignedsuiteandfi() == null ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        //体检者收费项目表
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", second.getPatientcode())
                .eq("id_ks", second.getKsId())
                .isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .eq("f_feecharged", 1)
        );//收费项目
        if (feeitem == null) {
            throw new ServiceException("审核失败，该体检号没有本科室收费项目或尚未缴费！");
        }
        //@sqlOrder
        patient.setModifydate(new Date());
        //收费项目
        feeitem.setIdPatientexamdepart(ksId);
        feeitem.setIdExamdoctor(SecurityUtils.getUserNo());
        feeitem.setExamdoctorNameR(SecurityUtils.getUsername());
        feeitem.setExaminatetime(new Date());
        feeitem.setFExaminated(1);
        peispatientfeeitemMapper.updateById(feeitem);
        //保存问卷
        second.setCreator(SecurityUtils.getUsername());
        PeisQuestionnaireSecond seconds = peisQuestionnaireSecondMapper.selectOne(new QueryWrapper<PeisQuestionnaireSecond>()
                .eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(seconds)) {
            //保存
            peisQuestionnaireSecondMapper.insert(second);
        } else {
            //更新
            peisQuestionnaireSecondMapper.updateById(second);
        }
        //功能科室是否全部已检
        if (outsideMainService.getAllSfxmtzjStatus(patientcode)) {
            patient.setFReadytofinal(1);//0:已备单 1:分检完成
            peisStateService.setScbs(patient.getPatientcode(), 0);
            patient.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
            for (Peispatientfeeitem other : other_items) {
                other.setFExaminated(1);//设置未关联科室项目为已检
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        patient.setFExamstarted(1);
        peispatientMapper.updateById(patient);
        return Boolean.TRUE;
    }


    /**
     * 查询体检者收费项目表个数返回true或false
     *
     * @param inputCode
     * @return
     */
    private boolean getPFeeitemCount(String inputCode) {
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);
        if (depIds.size() == 0) {
            return true;
        }
        Long count = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>()
                .in("id_ks", depIds).eq("change_item", 0).eq("id_patient", inputCode).eq("sfjj", 0)
                .eq("f_giveup", 0).isNull("f_transferedhl7").isNotNull("id_ks").ne("f_examinated", 1));
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }
}

