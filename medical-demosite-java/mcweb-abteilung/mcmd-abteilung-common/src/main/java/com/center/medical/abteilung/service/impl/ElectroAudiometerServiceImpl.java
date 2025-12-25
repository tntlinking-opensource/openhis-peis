package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.EADataDto;
import com.center.medical.abteilung.bean.dto.EAFormDataDTO;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.EAsaOrUpParam;
import com.center.medical.abteilung.dao.ElectroAudiometerMapper;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.service.ElectroAudiometerService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Describe;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ReflectionUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AttachmentMapper;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KS电测听(ElectroAudiometer)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-09 11:43:19
 */
@Slf4j
@Service("electroAudiometerService")
@RequiredArgsConstructor
public class ElectroAudiometerServiceImpl extends ServiceImpl<ElectroAudiometerMapper, ElectroAudiometer> implements ElectroAudiometerService {

    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PatienttypeService patienttypeService;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final SysUserMapper sysUserMapper;
    private final AttachmentMapper attachmentMapper;
    private final MapperFacade mapperFacade;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final SysDeptMapper sysDeptMapper;
    private final DescribeMapper describeMapper;
    private final ItemsMapper itemsMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ISysConfigService iSysConfigService;
    private final SectionResultMainService sectionResultMainService;
    private final ISysBranchService iSysBranchService;


    public static final String jl_id = "1197";//电测听结论检查项目id
    public static final Map<String, String> id_map = new HashMap<String, String>() {
        private static final long serialVersionUID = 3929188938194818826L;

        {
            put("boneLeft250", "1187");
            put("airLeft125", "923");
            put("airRight125", "944");
            put("boneRight250", "1192");
            put("boneLeft4000", "1190");
            put("boneRight4000", "1196");
            put("boneLeft2000", "1191");
            put("boneRight2000", "1195");
            put("boneLeft1000", "940");
            put("boneRight1000", "1194");
            put("boneLeft500", "1188");
            put("boneRight500", "1193");
            put("airLeft250", "938");
            put("airRight250", "945");
            put("airLeft500", "939");
            put("airRight500", "946");
            put("airLeft1000", "940");
            put("airRight1000", "947");
            put("airLeft2000", "941");
            put("airRight2000", "948");
            put("airLeft4000", "942");
            put("airRight4000", "949");
            put("airLeft8000", "943");
            put("airRight8000", "944");
            put("airLeft3000", "1200");
            put("airRight3000", "1201");
            put("airLeft6000", "1202");
            put("airRight6000", "1203");
            put("boneLeft3000", "1205");
            put("boneRight3000", "1204");
            put("电测听结论", "1197");
        }
    };

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param ElectroAudiometer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ElectroAudiometer> getPage(PageParam<ElectroAudiometer> page, ElectroAudiometer param) {
        return electroAudiometerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ElectroAudiometer getInfoById(String id) {
        return electroAudiometerMapper.getInfoById(id);
    }

    /**
     * 读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public HashMap<String, Object> search(String patientCode, String ksId) {
        //体检者表,f_registered是否已登记：0或null.否 1.是
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode).eq("f_registered", 1));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        //禁检
        if (patient.getFPaused() != null && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("error@该体检号已被禁检！");
        }
        //任务分组ID
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (StringUtils.isNotEmpty(idOrgreservationgroup)) {
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
        //1=已收费
        if (feeitem.getFFeecharged() == null || feeitem.getFFeecharged() != 1) {
            throw new ServiceException("error@该体检号尚未缴费！");
        }
        //返回数据
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("patient", patient);
        data.put("picture", peispatientPhotoService.getPicture(patient));
        data.put("isVIP", patienttypeService.getIdPatientClass(patient));
        data.put("feeitem", feeitem);

        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId)
                .eq("patientcode", patientCode)
        );

        /*电测听表*/
        ElectroAudiometer audiometer = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>().eq("patientCode", patientCode));
        data.put("audiometry", audiometer);

        //审核过
        if (ObjectUtils.isNotEmpty(main)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<String, String> main_map = new HashMap<String, String>();
            main_map.put("id", main.getId());
            //健康小结
            main_map.put("conclusions", main.getConclusions());
            //是否已审核
            main_map.put("isAudit", main.getIsAudit() == null ? "0" : (main.getIsAudit() == 1 ? "1" : "0"));

            //检查人ID
            String rummagerId = main.getRummagerId();
            main_map.put("rummagerId", rummagerId);
            if (rummagerId != null) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(rummagerId);
                if (rummager != null) {
                    //检查人姓名
                    main_map.put("rummager", rummager.getUserName());
                }
            }
            //检查时间
            main_map.put("rummagerTime", main.getRummagerTime() == null ? "" : sdf.format(main.getRummagerTime()));
            //录入人ID
            String writeId = main.getWriteId();
            if (StringUtils.isNotEmpty(writeId)) {
                SysUser writer = sysUserMapper.selectUserByUserNo(writeId);
                if (writer != null) {
                    //录入人姓名
                    main_map.put("writeName", writer.getUserName());
                }
            }
            //录入时间
            main_map.put("writeTime", main.getWriteTime() == null ? "" : sdf.format(main.getWriteTime()));
            data.put("main", main_map);
        }
        return data;
    }


    /**
     * 审核
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(EAsaOrUpParam param) {
        //取出属性
        String patientCode = param.getPatientCode();
        String ksId = param.getKsId();
        EADataDto main_map = param.getData();
        EAFormDataDTO formdata = param.getFormdata();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //审核时间
        Date auditTime = new Date();
        //审核人Id
        String auditId = SecurityUtils.getUserNo();
        //分科检查医师
        String examdoctorNameR = SecurityUtils.getUsername();

        //体检者
        Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
        //登记
        if (ObjectUtils.isEmpty(patient) || patient.getFRegistered() != 1) {
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
        //短号体检号
        Integer shortCode = patient.getShortCode();
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
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


        //科室检查结果主表
        SectionResultMain new_main = new SectionResultMain();
        String main_id = main_map.getId();
        new_main.setId(main_id);
        Date rummagerTime = main_map.getRummagerTime();
        //检查人
        new_main.setRummagerId(main_map.getRummagerId());
        new_main.setRummagerTime(main_map.getRummagerTime());
        new_main.setRummagerName(main_map.getRummager());
        //录入人
        new_main.setWriteTime(main_map.getWriteTime());
        new_main.setWriteId(main_map.getWriteId());
        new_main.setWriteName(main_map.getWriteName());
        //审核人
        new_main.setAuditName(examdoctorNameR);
        new_main.setAuditId(auditId);
        new_main.setAuditTime(auditTime);

        new_main.setDepId(ksId);
        new_main.setPatientcode(patientCode);
        new_main.setShortCode(shortCode);
        new_main.setIsAudit(1);
        //电测听职业小结=健康小结
        log.info("电测听打印一下小结:{},{}",main_map.getConclusions(),patientCode);
        new_main.setConclusions(main_map.getConclusions());
        new_main.setZyConclusions(main_map.getConclusions());
        //关联表名
        new_main.setAssociativeTable("ELECTRO_AUDIOMETER");

        //科室检查结果主表
        SectionResultMain old_main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientCode).eq("dep_id", ksId));
        //第一次审核
        if (ObjectUtils.isEmpty(old_main)) {
            //分检完成
            new_main.setIsFinish(0);
            //插入
            sectionResultMainMapper.insert(new_main);
            main_id = new_main.getId();
        } else {
            if (old_main.getIsAudit() != null && old_main.getIsAudit() == 1) {
                throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
            }
            //删除附件
            attachmentMapper.delete(new QueryWrapper<Attachment>().eq("patientcode", patientCode).eq("file_type", "ELE"));

            //更新
            BeanUtils.copyProperties(new_main, old_main, new String[]{"isDelete", "isFinish", "id"});
            old_main.setIsFinish(0);
            sectionResultMainMapper.updateById(old_main);
            new_main = old_main;
        }

        //图片路径
        String airImgPath = param.getAir_img();
        String boneImgPath = param.getBone_img();
        //体检类型
        String idExamtype = patient.getIdExamtype();
        //电测听表
        ElectroAudiometer audimeter = mapperFacade.map(formdata, ElectroAudiometer.class);
        audimeter.setIdExamtype(idExamtype);
        audimeter.setPatientcode(patientCode);
        audimeter.setShortCode(shortCode);
        audimeter.setDepId(ksId);
        audimeter.setAirImgPath(airImgPath);
        audimeter.setBoneImgPath(boneImgPath);

        ElectroAudiometer electroAudiometer = electroAudiometerMapper.selectOne(new LambdaQueryWrapper<ElectroAudiometer>()
                .eq(ElectroAudiometer::getDepId, ksId)
                .eq(ElectroAudiometer::getPatientcode, patientCode)
        );
        if (ObjectUtils.isEmpty(electroAudiometer)) {
            //插入
            save(audimeter);
        } else {
            //更新
            audimeter.setId(electroAudiometer.getId());
            updateById(audimeter);
        }
        //附件表
        if (StringUtils.isNotEmpty(airImgPath)) {
            Attachment att = new Attachment();
            att.setFileType("ELE");
            att.setFilePath(airImgPath);
            att.setPatientcode(patientCode);
            att.setShortCode(shortCode);
            att.setDepId(ksId);
            att.setFeeItemId(feeitem.getIdExamfeeitem());
            attachmentMapper.insert(att);
        }
        if (StringUtils.isNotEmpty(boneImgPath)) {
            Attachment att2 = new Attachment();
            att2.setFileType("ELE");
            att2.setDcmPath(boneImgPath);
            att2.setPatientcode(patientCode);
            att2.setShortCode(shortCode);
            att2.setDepId(ksId);
            att2.setFeeItemId(feeitem.getIdExamfeeitem());
            attachmentMapper.insert(att2);
        }
        //收费项目
        feeitem.setIdPatientexamdepart(ksId);
        feeitem.setIdExamdoctor(auditId);
        feeitem.setExamdoctorNameR(examdoctorNameR);
        feeitem.setExaminatetime(rummagerTime);
        feeitem.setFExaminated(1);
        peispatientfeeitemMapper.updateById(feeitem);
        //体检者表
        if (outsideMainService.getAllSfxmtzjStatus(patientCode)) {
            //0:已备单 1:分检完成
            patient.setFReadytofinal(1);
            peisStateService.setScbs(patient.getPatientcode(), 0);
            patient.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
            for (Peispatientfeeitem other : other_items) {
                //设置未关联科室项目为已检
                other.setFExaminated(1);
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        patient.setFExamstarted(1);
        peispatientMapper.updateById(patient);

        //DESCRIBE
        SysDept dept = sysDeptMapper.getByDeptNo(ksId);
        //描述
        String depDes = dept == null ? null : dept.getDescription();
        //删除科室描述、检查结果表
        describeMapper.delete(new QueryWrapper<Describe>()
                .eq("patientcode", patientCode)
                .in("item_id", id_map.values().toArray()));
        //体检者收费项目ID
        String feeId = feeitem.getIdExamfeeitem();

        //收费项目表
        Items items = itemsMapper.getInfoById(feeId);
        //收费项目打印名称
        String feeName = items == null ? null : items.getExamfeeitemNameprn();
        for (Map.Entry<String, String> entry : id_map.entrySet()) {
            String field = entry.getKey();
            String id = entry.getValue();
            Object value = ReflectionUtil.invokeGetterMethod(audimeter, field);
            if (value != null) {
                //科室描述、检查结果表
                Describe des = new Describe();
                des.setPatientcode(patientCode);
                des.setShortCode(shortCode);
                des.setItemId(id);
                des.setSignList(value.toString());

                //检查项目表
                Basexamltem exam_ltem = basexamltemMapper.getInfoById(id);
                //des.setItemName(exam_ltem.getExamitemName());
                des.setItemName(exam_ltem == null ? null : exam_ltem.getExamitemName());
                des.setDepId(ksId);
                des.setDepDescription(depDes);
                des.setFeeId(feeId);
                des.setFeeName(feeName);
                describeMapper.insert(des);
            }
        }
        //检查项目表
        Describe des = new Describe();
        des.setPatientcode(patientCode);
        des.setShortCode(shortCode);
        des.setItemId(jl_id);
        des.setSignList(new_main.getConclusions());
        //JC检查项目表
        Basexamltem exam_ltem = basexamltemMapper.getInfoById(jl_id);
        des.setItemName(exam_ltem == null ? null : exam_ltem.getExamitemName());
        //des.setItemName("电测听结论");
        des.setDepId(ksId);
        des.setDepDescription(depDes);
        des.setFeeId(feeId);
        des.setFeeName(feeName);
        describeMapper.insert(des);

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
        //体检者表
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
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

        //收费项目
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .eq("f_feecharged", 1)
        );
        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId)
                .eq("patientcode", patientCode)
        );
        //分检是否已审核 0和null.未审核 1.已审核
        if (main == null || main.getIsAudit() == null || !(main.getIsAudit() == 1)) {
            throw new ServiceException("反审核失败，该体检号未审核！");
        }
        //更新
        feeitem.setFExaminated(0);
        peispatientfeeitemMapper.updateById(feeitem);
        peispatient.setFReadytofinal(0);
        peisStateService.setScbs(peispatient.getPatientcode(), 0);
        main.setIsAudit(0);
        peispatientMapper.updateById(peispatient);
        sectionResultMainMapper.updateById(main);
        return Boolean.TRUE;
    }


    /**
     * 上传图片
     *
     * @param param
     * @return
     */
    @Override
    public Boolean UploadImg(ElectroAudiometer param) {
        ElectroAudiometer electroAudiometer = electroAudiometerMapper.selectOne(new LambdaQueryWrapper<ElectroAudiometer>()
                .eq(ElectroAudiometer::getDepId, param.getDepId())
                .eq(ElectroAudiometer::getPatientcode, param.getPatientcode())
        );
        if (ObjectUtils.isEmpty(electroAudiometer)) {
            //插入
            save(param);
        } else {
            updateById(param);
        }
        return Boolean.TRUE;
    }

    /**
     * 保存并审核数据
     * @param electroAudiometers
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadEle(List<ElectroAudiometer> electroAudiometers) {
        String rn = "\n";//换行符
        StringBuilder msg = new StringBuilder();
        String ksId = "163";
        for (ElectroAudiometer audimeter : electroAudiometers) {
            String patientCode = ToolUtil.patientCode(audimeter.getPatientcode(), iSysBranchService.getBranchFlag(null));

            Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
            if (ObjectUtils.isEmpty(patient)){
                msg.append("体检号:"+patientCode+"不存在， 上传失败!" + rn);
                continue;
            }
            //科室检查结果主表
            SectionResultMain new_main = sectionResultMainMapper.selectOne(new LambdaQueryWrapper<SectionResultMain>()
                    .eq(SectionResultMain::getPatientcode, patientCode)
                    .eq(SectionResultMain::getDepId,ksId)
            );
            if (ObjectUtils.isNotEmpty(new_main) && ObjectUtils.isNotEmpty(new_main.getIsAudit()) && new_main.getIsAudit()==1){
                msg.append("体检号:" + patientCode + "已审核， 不能上传!" + rn);
                continue;
            }
            if (ObjectUtils.isEmpty(new_main)){
                new_main = new SectionResultMain();
                new_main.setDepId(ksId);
                new_main.setIsAudit(0);
                new_main.setPatientcode(patientCode);
                new_main.setShortCode(patient.getShortCode());
            }


            //电测听职业小结=健康小结
            String conclusions = generateConclusions(audimeter,patient.getAge(),patient.getIdSex());
            log.info("电测听打印一下小结:{},{}",conclusions,patientCode);
            new_main.setConclusions(conclusions);
            new_main.setZyConclusions(conclusions);
            audimeter.setTestResult(conclusions);
            //关联表名
            new_main.setAssociativeTable("ELECTRO_AUDIOMETER");
            new_main.setIsFinish(0);
            sectionResultMainService.saveOrUpdate(new_main);


            //体检类型
            String idExamtype = patient.getIdExamtype();
            //电测听表
            audimeter.setIdExamtype(idExamtype);
            audimeter.setPatientcode(patientCode);
            audimeter.setShortCode(patient.getShortCode());
            audimeter.setDepId(ksId);


            ElectroAudiometer electroAudiometer = electroAudiometerMapper.selectOne(new LambdaQueryWrapper<ElectroAudiometer>()
                    .eq(ElectroAudiometer::getDepId, ksId)
                    .eq(ElectroAudiometer::getPatientcode, patientCode)
            );
            if (ObjectUtils.isEmpty(electroAudiometer)) {
                //插入
                save(audimeter);
            } else {
                //更新
                audimeter.setId(electroAudiometer.getId());
                updateById(audimeter);
            }
            msg.append("体检号:"+patientCode+ "上传成功!" + rn);
        }
        return msg.toString();
    }




    /**
     * 生成电测听折线图
     * @param audimeter
     * @return
     */
    private static List<String> generateLineChart(ElectroAudiometer audimeter) {
        // 创建数据集
        DefaultCategoryDataset datasetLeftEar = new DefaultCategoryDataset();
        datasetLeftEar.addValue(10, "气左", "125");
        datasetLeftEar.addValue(20, "气左", "250");
        DefaultCategoryDataset datasetRightEar = new DefaultCategoryDataset();
        datasetRightEar.addValue(10, "气右", "125");
        datasetRightEar.addValue(20, "气右", "250");
        // 创建折线图
        JFreeChart chartLeftEar = createChart(datasetLeftEar, "左耳听力检测图");
        JFreeChart chartRightEar = createChart(datasetRightEar, "右耳听力检测图");

        // 保存图表
        saveChart(chartLeftEar, "leftEarChart.png");
        saveChart(chartRightEar, "rightEarChart.png");
        return null;
    }

    private static JFreeChart createChart(DefaultCategoryDataset dataset, String title) {
        return ChartFactory.createLineChart(
                title,
                "频率 (Hz)", // X轴标签
                "分贝 (dB)", // Y轴标签
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private static void saveChart(JFreeChart chart, String fileName) {
        try {
            ChartUtilities.saveChartAsPNG(new File(fileName), chart, 800, 600);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 电测听生成小结
     * @param audimeter
     * @return
     */
    private String generateConclusions(ElectroAudiometer audimeter,Integer age,Integer idSex) {
        String sex = idSex==0?"M":"F";
        int dev_3000 = getDev(sex + "_List_3k",age);
        int dev_4000 = getDev(sex + "_List_4k",age);
        int dev_6000 = getDev(sex + "_List_6k",age);
        Double l3k = (ObjectUtils.isNotEmpty(audimeter.getAirLeft3000())?audimeter.getAirLeft3000():0.0) - dev_3000;
        Double l4k = (ObjectUtils.isNotEmpty(audimeter.getAirLeft4000())?audimeter.getAirLeft4000():0.0) - dev_4000;
        Double l6k = (ObjectUtils.isNotEmpty(audimeter.getAirLeft6000())?audimeter.getAirLeft6000():0.0) - dev_6000;
        Double r3k = (ObjectUtils.isNotEmpty(audimeter.getAirRight3000())?audimeter.getAirRight3000():0.0) - dev_3000;
        Double r4k = (ObjectUtils.isNotEmpty(audimeter.getAirRight4000())?audimeter.getAirRight4000():0.0) - dev_4000;
        Double r6k = (ObjectUtils.isNotEmpty(audimeter.getAirRight6000())?audimeter.getAirRight6000():0.0) - dev_6000;
        double v = (l3k + l4k + l6k + r3k + r4k + r6k) / 6;
        int intValue = (int) Math.round(v);
        //处理-0的情况
        if (intValue == -0) {
            intValue = 0;
        }
        String result = "双耳高频平均听阈" + intValue + "dB。";


        /*针对噪声作业*/
        int dev_500 = getDev(sex + "_List_500",age);
        int dev_1000 = getDev(sex + "_List_1k",age);
        int dev_2000 = getDev(sex + "_List_2k",age);
        Double l500 = (ObjectUtils.isNotEmpty(audimeter.getAirLeft500())?audimeter.getAirLeft500():0.0) - dev_500;
        Double l1k = (ObjectUtils.isNotEmpty(audimeter.getAirLeft1000())?audimeter.getAirLeft1000():0.0) - dev_1000;
        Double l2k = (ObjectUtils.isNotEmpty(audimeter.getAirLeft2000())?audimeter.getAirLeft2000():0.0) - dev_2000;
        Double r500 = (ObjectUtils.isNotEmpty(audimeter.getAirRight500())?audimeter.getAirRight500():0.0) - dev_500;
        Double r1k = (ObjectUtils.isNotEmpty(audimeter.getAirRight1000())?audimeter.getAirRight1000():0.0) - dev_1000;
        Double r2k = (ObjectUtils.isNotEmpty(audimeter.getAirRight2000())?audimeter.getAirRight2000():0.0) - dev_2000;
        int HLL = (int) Math.round((((l1k + l2k + l500) * 0.9) / 3 + l4k * 0.1));
        int HLR = (int) Math.round((((r1k + r2k + r500) * 0.9) / 3 + r4k * 0.1));
        int YPL = (int) Math.round(((l500 + l1k + l2k) / 3));
        int YPR = (int) Math.round(((r500 + r1k + r2k) / 3));
        result += "\r左耳语频平均听阈" + YPL + "dB，左耳听阈加权值" + HLL + "dB。";
        result += "\r右耳语频平均听阈" + YPR + "dB，右耳听阈加权值" + HLR + "dB。";
        return result;
    }

    /**
     * 获取电测听配置
     * @param sex
     * @param age
     * @return
     */
    private int getDev(String sex, Integer age) {
        Map<String, String> dct = Constants.DCT;
        String str = dct.get(sex);
        if (StringUtils.isNotEmpty(str)){
            String[] split = str.split(",");
            for (String rangeWithValue : split) {
                //12-19:0
                String[] parts = rangeWithValue.split(":");
                String ageRange = parts[0].trim(); // 获取年龄范围部分
                String valueString = parts[1].trim(); // 获取冒号后的值部分
                // 解析年龄范围
                String[] ageRangeParts = ageRange.split("-");
                int minAge = Integer.parseInt(ageRangeParts[0].trim());
                int maxAge = Integer.parseInt(ageRangeParts[1].trim());

                // 检查年龄是否在范围内
                if (age >= minAge && age <= maxAge) {
                    // 如果年龄在范围内，输出冒号后的值
                    int value = Integer.parseInt(valueString);
                    return value;
                }
            }
        }
        return 0;
    }







}

