package com.center.medical.machine.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.machine.bean.dto.GetAllItemsDto;
import com.center.medical.machine.bean.dto.PeispatientDto;
import com.center.medical.machine.bean.model.RegisteredEntity;
import com.center.medical.machine.dao.TeamRegisterMapper;
import com.center.medical.machine.service.TeamRegisterService;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import com.center.medical.pacslis.dao.PacsPeispatientMapper;
import com.center.medical.pacslis.dao.PacsPeispatientfeeitemMapper;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.service.PacsItemsService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 团体(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@Service("teamRegisterService")
@RequiredArgsConstructor
public class TeamRegisterServiceImpl extends ServiceImpl<TeamRegisterMapper, Peispatient> implements TeamRegisterService {

    private final TeamRegisterMapper teamRegisterMapper;
    private final PeispatientMapper peispatientMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewProjectMapper reviewProjectMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final CreatemealMapper createmealMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final PacsPeispatientMapper pacsPeispatientMapper;
    private final PacsPeispatientfeeitemMapper pacsPeispatientfeeitemMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ItemsMapper itemsMapper;
    private final PacsItemsService pacsItemsService;

    private final PeispatientarchiveService peispatientarchiveService;


    private final Snowflake snowflake;
    public static final String ID_PAYWAY = "5";
    public static final String PAYWAY = "统收";


    /**
     * 获取体检者数据
     *
     * @param entity
     * @return
     */
    @Override
    public List<PeispatientDto> getPeispatient(RegisteredEntity entity) {
        ArrayList<PeispatientDto> peispatientDtos = new ArrayList<>();
        List<Peispatient> list = peispatientMapper.getRegistrationMachineList(entity.getIdNumber());
        for (Peispatient peispatient : list) {
            //业务编号不为空
            if (StringUtils.isNotEmpty(peispatient.getPatientbizno())) {
                peispatientDtos.add(convertToDtoOnline(peispatient, "团检"));
            } else {
                peispatientDtos.add(convertToDtoOffline(peispatient, "团检"));
            }
        }
        return peispatientDtos;
    }

    /**
     * 获取体检者数据个人
     *
     * @param entity
     * @return
     */
    @Override
    public List<PeispatientDto> getPeispatientPersonal(RegisteredEntity entity) {
        QueryWrapper<Peispatient> conjunction = new QueryWrapper<Peispatient>();
        conjunction.eq("idcardno", entity.getIdNumber());
        conjunction.eq("f_usecodehiden", 0);
        conjunction.eq("f_registered", 0);
        conjunction.and(wrapper -> wrapper.isNull("f_paused").or().eq("f_paused", 0));
        List<Peispatient> list = peispatientMapper.selectList(conjunction);
        ArrayList<PeispatientDto> peispatientDtos = new ArrayList<>();
        for (Peispatient peispatient : list) {
            if (StringUtils.isNotEmpty(peispatient.getPatientbizno())) {
                peispatientDtos.add(convertToDtoOnline(peispatient, "个人"));
            } else {
                peispatientDtos.add(convertToDtoOffline(peispatient, "个人"));
            }

        }
        return peispatientDtos;
    }


    /**
     * 获取体检者数据复查
     *
     * @param entity
     * @return
     */
    @Override
    public List<PeispatientDto> getPeispatientRecheck(RegisteredEntity entity) {
        ArrayList<PeispatientDto> peispatientDtos = new ArrayList<>();
        Peispatient peispatient = peispatientMapper.getByPatientCode(entity.getPatientCode());
        if (peispatient != null) {
            //获取复查体检者
            Peispatient reviewPatient = getReviewPatient(peispatient);
            if ("0".equals(reviewPatient.getIdExamtype()) && null != reviewPatient.getFIsrecheck() && reviewPatient.getFIsrecheck() == 1) {
                //复查表
                Review review = reviewMapper.selectOne(new QueryWrapper<Review>()
                                .eq("patientcode", entity.getPatientCode()).ne("is_delete", 1)
                        // ,Restrictions.ne("callbackStation", 1)//加载一次后，没复查登记，下次再读卡，不会提示加载复查项目
                );
                if (null != review) {
                    //复查项目
                    List<ReviewProject> projects = reviewProjectMapper.selectList(new QueryWrapper<ReviewProject>().eq("review_id", review.getId()));
                    if (projects != null && projects.size() > 0) {
                        if (StringUtils.isNotEmpty(reviewPatient.getPatientbizno())) {
                            peispatientDtos.add(convertToDtoOnline(reviewPatient, "个人"));
                        } else {
                            peispatientDtos.add(convertToDtoOffline(reviewPatient, "个人"));
                        }
                    }
                }
            }
        }
        return peispatientDtos;
    }

    /**
     * 获取复查体检者
     *
     * @param patient
     * @return
     */
    private Peispatient getReviewPatient(Peispatient patient) {
        if (patient.getInpatientno() == null) {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createdate").eq("inpatientno", patient.getPatientcode()));
            return ps.size() > 0 ? ps.get(0) : null;
        } else {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createdate").gt("createdate", patient.getCreatedate())
                    .eq("inpatientno", patient.getInpatientno()));
            return ps.size() > 0 ? ps.get(0) : null;
        }
    }

    /**
     * 获取体检者数据补检
     *
     * @param entity
     * @return
     */
    @Override
    public List<PeispatientDto> getPeispatientMakeUp(RegisteredEntity entity) {
        ArrayList<PeispatientDto> peispatientDtos = new ArrayList<>();
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(
                new QueryWrapper<Peispatientfeeitem>()
                        .eq("f_registered", 1)
                        .eq("f_feecharged", 1)
                        .eq("id_patient", entity.getPatientCode())
                        .eq("f_transferedhl7", 0));
        if (peispatientfeeitems != null && peispatientfeeitems.size() > 0) {
            QueryWrapper<Peispatient> conjunction = new QueryWrapper<>();
            conjunction.eq("idcardno", entity.getIdNumber());
            conjunction.eq("f_registered", 0);
            conjunction.and(wrapper -> wrapper.isNull("f_paused").or().eq("f_paused", 0));
            List<Peispatient> list = peispatientMapper.selectList(conjunction);
            for (Peispatient peispatient : list) {
                if (StringUtils.isNotEmpty(peispatient.getPatientbizno())) {
                    peispatientDtos.add(convertToDtoOnline(peispatient, "个人"));
                } else {
                    peispatientDtos.add(convertToDtoOffline(peispatient, "个人"));
                }
            }
        }
        return peispatientDtos;
    }

    protected PeispatientDto convertToDtoOnline(Peispatient peispatient, String type) {
        return convertToDto(peispatient, type, 1);
    }

    protected PeispatientDto convertToDto(Peispatient peispatient, String type, Integer online) {
        PeispatientDto peispatientDto = new PeispatientDto();
        peispatientDto.setType(type);
        peispatientDto.setOnline(online);
        peispatientDto.setReviewPdf(peispatient.getReviewPdf());
        peispatientDto.setContraindicatedPdf(peispatient.getContraindicatedPdf());
        peispatientDto.setDiseasePdf(peispatient.getDiseasePdf());
        peispatientDto.setDiseasePrintNum(peispatient.getDiseasePrintNum());
        peispatientDto.setHealthPrintNum(peispatient.getHealthPrintNum());
        peispatientDto.setIdCis(peispatient.getIdCis());
        peispatientDto.setPatientcode(peispatient.getPatientcode());
        peispatientDto.setPatientarchiveno(peispatient.getPatientarchiveno());
//        peispatientDto.setPatientcardno(peispatient.getPatientcardno());
        peispatientDto.setPatientbizno(peispatient.getPatientbizno());
        peispatientDto.setIdcardno(peispatient.getIdcardno());
        peispatientDto.setNumorgresv(peispatient.getNumorgresv());
        peispatientDto.setPatientname(peispatient.getPatientname());
        peispatientDto.setInputCode(peispatient.getInputCode());
        peispatientDto.setIdOrgreservationgroup(peispatient.getIdOrgreservationgroup());
        peispatientDto.setIdOrgreservation(peispatient.getIdOrgreservation());
        peispatientDto.setIdOrg(peispatient.getIdOrg());
        peispatientDto.setOrgName(peispatient.getOrgName());
        peispatientDto.setOrgDepart(peispatient.getOrgDepart());
        peispatientDto.setHavePrivate(peispatient.getHavePrivate());
        peispatientDto.setIdPayway(peispatient.getIdPayway());
        peispatientDto.setPayway(peispatient.getPayway());
        peispatientDto.setPersonpricelimit(peispatient.getPersonpricelimit());
        peispatientDto.setIdSex(peispatient.getIdSex());
        peispatientDto.setBirthdate(peispatient.getBirthdate());
        peispatientDto.setAge(peispatient.getAge());
        peispatientDto.setIdMarriage(peispatient.getIdMarriage());
        peispatientDto.setMarriage(peispatient.getMarriage());
        peispatientDto.setIdNation(peispatient.getIdNation());
        peispatientDto.setNation(peispatient.getNation());
        peispatientDto.setAddress(peispatient.getAddress());
        peispatientDto.setIdInformway(peispatient.getIdInformway());
        peispatientDto.setIdOpendoctor(peispatient.getIdOpendoctor());
        peispatientDto.setEmail(peispatient.getEmail());
        peispatientDto.setPhone(peispatient.getPhone());
        peispatientDto.setIdPatientclass(peispatient.getIdPatientclass());
        peispatientDto.setIdResarea(peispatient.getIdResarea());
        peispatientDto.setResarea(peispatient.getResarea());
        peispatientDto.setFIsforprepare(peispatient.getFIsforprepare());
        peispatientDto.setFIsforreserve(peispatient.getFIsforreserve());
        peispatientDto.setFRegistered(peispatient.getFRegistered());
        peispatientDto.setDateregister(peispatient.getDateregister());
        peispatientDto.setMoneyamount(peispatient.getMoneyamount());
        peispatientDto.setMoneyamountpaid(peispatient.getMoneyamountpaid());
        peispatientDto.setGuidancenote(peispatient.getGuidancenote());
        peispatientDto.setWorkno(peispatient.getWorkno());
        peispatientDto.setIdDoctorreg(peispatient.getIdDoctorreg());
        peispatientDto.setDoctorreg(peispatient.getDoctorreg());
        peispatientDto.setIdExamtype(peispatient.getIdExamtype());
        peispatientDto.setExamsuiteName(peispatient.getExamsuiteName());
        peispatientDto.setExamsuiteAlias(peispatient.getExamsuiteAlias());
        peispatientDto.setIdDoctorapply(peispatient.getIdDoctorapply());
        peispatientDto.setDoctorapply(peispatient.getDoctorapply());
        peispatientDto.setFGuidanceprinted(peispatient.getFGuidanceprinted());
        peispatientDto.setFExamstarted(peispatient.getFExamstarted());
        peispatientDto.setFReadytofinal(peispatient.getFReadytofinal());
        peispatientDto.setIdDoctorreg(peispatient.getIdDoctorreg());
        peispatientDto.setFPaused(peispatient.getFPaused());
        peispatientDto.setFFinallocked(peispatient.getFFinallocked());
        peispatientDto.setFFinalexamed(peispatient.getFFinalexamed());
        peispatientDto.setIdDoctorfinal(peispatient.getIdDoctorfinal());
        peispatientDto.setDoctorfinalNameR(peispatient.getDoctorfinalNameR());
        peispatientDto.setDatefinalexamed(peispatient.getDatefinalexamed());
        peispatientDto.setFClosed(peispatient.getFClosed());
        peispatientDto.setDateclosed(peispatient.getDateclosed());
        peispatientDto.setNote(peispatient.getNote());
        peispatientDto.setKnowledge(peispatient.getKnowledge());
        peispatientDto.setTimingstartedat(peispatient.getTimingstartedat());
        peispatientDto.setHospitalcode(peispatient.getHospitalcode());
        peispatientDto.setIdExamplace(peispatient.getIdExamplace());
        peispatientDto.setParsedassignedsuiteandfi(peispatient.getParsedassignedsuiteandfi());
        peispatientDto.setParsedassignedgroupandfi(peispatient.getParsedassignedgroupandfi());
        peispatientDto.setParsedsuiteandfi(peispatient.getParsedsuiteandfi());
        peispatientDto.setParsedsuiteandfilab(peispatient.getParsedsuiteandfilab());
        peispatientDto.setIdGuidenurse(peispatient.getIdGuidenurse());
        peispatientDto.setPatientnameencoded(peispatient.getPatientnameencoded());
        peispatientDto.setPatientcodehiden(peispatient.getPatientcodehiden());
        peispatientDto.setFWordprinted(peispatient.getFWordprinted());
        peispatientDto.setGuidancenote2(peispatient.getGuidancenote2());
        peispatientDto.setFUsecodehiden(peispatient.getFUsecodehiden());
        peispatientDto.setIdPatientclass3(peispatient.getIdPatientclass3());
        peispatientDto.setDateregisternotime(peispatient.getDateregisternotime());
        peispatientDto.setCounterreportprinted(peispatient.getCounterreportprinted());
        peispatientDto.setFIsrecheck(peispatient.getFIsrecheck());
        peispatientDto.setFSettlenone(peispatient.getFSettlenone());
        peispatientDto.setDateguidancereturned(peispatient.getDateguidancereturned());
        peispatientDto.setFOutpatient(peispatient.getFOutpatient());
        peispatientDto.setPatientnamereceipt(peispatient.getPatientnamereceipt());
        peispatientDto.setPatientnamepinyin(peispatient.getPatientnamepinyin());
        peispatientDto.setStatusofhm(peispatient.getStatusofhm());
        peispatientDto.setInstancetag(peispatient.getInstancetag());
        peispatientDto.setInpatientno(peispatient.getInpatientno());
        peispatientDto.setInsuranceno(peispatient.getInsuranceno());
        peispatientDto.setCountreportxml(peispatient.getCountreportxml());
        peispatientDto.setCountreportcompare(peispatient.getCountreportcompare());
        peispatientDto.setCountreportoccupation(peispatient.getCountreportoccupation());
        peispatientDto.setCountreportoccupationpdf(peispatient.getCountreportoccupationpdf());
        peispatientDto.setCountreportoccupationxml(peispatient.getCountreportoccupationxml());
        peispatientDto.setIdTjtc(peispatient.getIdTjtc());
        peispatientDto.setJzdwr(peispatient.getJzdwr());
        peispatientDto.setSpr(peispatient.getSpr());
        peispatientDto.setTjr(peispatient.getTjr());
        peispatientDto.setLqfs(peispatient.getLqfs());
        peispatientDto.setYzbm(peispatient.getYzbm());
        peispatientDto.setYjaddress(peispatient.getYjaddress());
        peispatientDto.setQtxz(peispatient.getQtxz());
        peispatientDto.setIsHmdb(peispatient.getIsHmdb());
        peispatientDto.setIsHmd(peispatient.getIsHmd());
        peispatientDto.setIsjj(peispatient.getIsjj());
        peispatientDto.setZgl(peispatient.getZgl());
        peispatientDto.setJhgl(peispatient.getJhgl());
        peispatientDto.setJhys(peispatient.getJhys());
        peispatientDto.setJktjzt(peispatient.getJktjzt());
        peispatientDto.setZytjzt(peispatient.getZytjzt());
        peispatientDto.setTmyd(peispatient.getTmyd());
        peispatientDto.setMedicaldate(peispatient.getMedicaldate());
        peispatientDto.setTrades(peispatient.getTrades());
        peispatientDto.setJzdw(peispatient.getJzdw());
        peispatientDto.setCjjgsfyhf(peispatient.getCjjgsfyhf());
        peispatientDto.setBhgybsfyhf(peispatient.getBhgybsfyhf());
        peispatientDto.setYxjgsfyhf(peispatient.getYxjgsfyhf());
        peispatientDto.setMedicaltype(peispatient.getMedicaltype());
        peispatientDto.setPrepayment(peispatient.getPrepayment());
        peispatientDto.setCultural(peispatient.getCultural());
        peispatientDto.setTcprice(peispatient.getTcprice());
        peispatientDto.setWorkDate(peispatient.getWorkDate());
        peispatientDto.setHarmDate(peispatient.getHarmDate());
        peispatientDto.setReadytofinalDate(peispatient.getReadytofinalDate());
        peispatientDto.setShortCode(peispatient.getShortCode());
        peispatientDto.setGuideSignleCount(peispatient.getGuideSignleCount());
        peispatientDto.setIsNoticed(peispatient.getIsNoticed());
        peispatientDto.setTsLimit(peispatient.getTsLimit());
        peispatientDto.setCheckoutDate(peispatient.getCheckoutDate());
        peispatientDto.setCheckoutStatus(peispatient.getCheckoutStatus());
        peispatientDto.setId(peispatient.getId());
        peispatientDto.setCreatedate(peispatient.getCreatedate());
        peispatientDto.setModifydate(peispatient.getModifydate());
        return peispatientDto;
    }


    protected PeispatientDto convertToDtoOffline(Peispatient peispatient, String type) {
        return convertToDto(peispatient, type, 0);
    }


    /**
     * 获取价格
     *
     * @param patientCode
     * @return
     */
    @Override
    public BigDecimal getPriceAmount(String patientCode) {
        List<GetAllItemsDto> items = teamRegisterMapper.getAllItems(patientCode);
        BigDecimal decimal = new BigDecimal("0");
        if (items != null && items.size() > 0) {
            for (GetAllItemsDto item : items) {
                BigDecimal price = item.getPrice();
                decimal = decimal.add(price);
            }
        }
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(format + "体检号：" + patientCode + "价格:" + decimal);
        return decimal;
    }


    /**
     * 创建体检者
     *
     * @param registeredEntity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisteredEntity createPeispatient(RegisteredEntity registeredEntity) {
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(format + "创建体检人信息");
        Peispatient peispatient = peispatientMapper.getByPatientCode(registeredEntity.getPatientCode());
        /**
         if (StringUtils.isNotEmpty(registeredEntity.getName().trim())) {
         peispatient.setPatientname(registeredEntity.getName().trim());
         }
         */
        if (StringUtils.isNotEmpty(registeredEntity.getPhone().trim())) {
            peispatient.setPhone(registeredEntity.getPhone().trim());
        }
        peispatient.setHospitalcode(SecurityUtils.getCId());
        //绑定档案号
        peispatient.setPatientarchiveno(peispatientarchiveService.bingIArchive(peispatient, false,SecurityUtils.getUserNo()));
        peispatientMapper.updateById(peispatient);

        //图片头像等
        if (StringUtils.isNotEmpty(registeredEntity.getPicture())) {
            PeispatientPhoto photo = peispatientPhotoMapper.getByPatientCode(peispatient.getPatientcode());
            if (photo == null) {
                peispatientPhotoMapper.insert(new PeispatientPhoto(peispatient.getPatientcode()
                        , registeredEntity.getPicture()));
            } else {
                photo.setPicture(registeredEntity.getPicture());
                peispatientPhotoMapper.updateById(photo);
            }
        }
//        收费金额
        Double chargeAmount = 0d;
        updateGroupFeeItem(peispatient, peispatient.getIdPayway(), chargeAmount);
        registeredEntity.setCanReg(
                (peispatient.getTsLimit() != null ? peispatient.getTsLimit().doubleValue() : 0.0)
                        >= chargeAmount ? 1 : 0);
        savePacs(peispatient);

        registeredEntity.setPatientCode(peispatient.getPatientcode());
        return registeredEntity;
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateGroupFeeItem(Peispatient peispatient, String payWayId, Double chargeAmount) {
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String patientcode = peispatient.getPatientcode();
        System.out.println(format + "默认价格：0");
        Double price = 0d;
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode)
                .eq("f_giveup",0)
                .eq("change_item",0)
        );
        for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
            peispatientfeeitem.setFRegistered(1);
            if (peispatientfeeitem.getFFeecharged() == null || peispatientfeeitem.getFFeecharged().intValue() != 1) {
                System.out.println(format + peispatientfeeitem.getExamfeeitemName() + "修改为已收费");
                price += peispatientfeeitem.getFactprice();
            }
            //套餐付款方式是统收
            if ("5".equals(payWayId)) {
                String userNo = SecurityUtils.getUserNo();
                peispatientfeeitem.setIdPayway("5");
                peispatientfeeitem.setFFeecharged(1);
                peispatientfeeitem.setIdDoctorreg(userNo);
                peispatientfeeitem.setDoctorregR(SecurityUtils.getUsername());
                //收费人和收费时间
                peispatientfeeitem.setIdFeecharger(userNo);
                peispatientfeeitem.setFeechargetime(new Date());
            }
            peispatientfeeitemMapper.updateById(peispatientfeeitem);
        }
        //体检者费用主表
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(peispatient.getPatientcode());
        if (pcm == null) {
            pcm = new PeispatientChargeMain(
                    SecurityUtils.getUsername() + "保存于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";"
                    , price
                    , "5".equals(payWayId) ? price : 0d
                    , peispatient.getPatientcode());
            peispatientChargeMainMapper.insert(pcm);
        } else {
            pcm.setNote(SecurityUtils.getUsername() + "保存于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";");
            pcm.setMoneyamount(price);
            pcm.setMoneyamountpaid("5".equals(payWayId) ? price : 0d);
            peispatientChargeMainMapper.updateById(pcm);
        }
        //统收
        if ("5".equals(payWayId)) {
            //修改体检者表
            peispatient.setMoneyamount(price);
            peispatient.setMoneyamountpaid(price);
            peispatientMapper.updateById(peispatient);
            //修改体检者收费记录
            createPeispatientCharge(patientcode, price, peispatient.getShortCode(), String.valueOf(snowflake.nextId()));
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Peispatientcharge createPeispatientCharge(String patientCode, Double moneyamount, Integer shortCode, String tradeNo) {
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Integer numIndex = teamRegisterMapper.getChargeMaxNumIndex(patientCode);
        Peispatientcharge charge = new Peispatientcharge();
        charge.setPatientcode(patientCode);
        charge.setTradeNo(tradeNo);
        charge.setIdPayway(ID_PAYWAY);
        charge.setPayway(PAYWAY);
        charge.setMoneyamount(moneyamount);
        charge.setMoneyamountpaid(moneyamount);
        charge.setFFeeconfirmed(1);
        charge.setFFeecharged(1);
        charge.setFFeechargedinttrans(0);
        charge.setIdFeecharger(SecurityUtils.getUserNo());
        charge.setFeechargetime(new Date());
        charge.setNote("自助机扫码消费");
        charge.setFFeechargedbyinterface(0);
        charge.setIsTotalcharge(1);
        charge.setIsDelete(0);
        charge.setShortCode(shortCode);
        numIndex = numIndex == null ? 0 : numIndex;
        charge.setNumIndex(numIndex + 1);
        peispatientchargeMapper.insert(charge);
        return charge;
    }

    @Transactional(rollbackFor = Exception.class)
    public void savePacs(Peispatient peispatient) {
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (getDictionaryStatusByType("PACS")) {
            String msg = savePacss(peispatient.getPatientcode());
            System.out.println(format + "保存PACS");
            if (!"success".equals(msg)) {
                throw new RuntimeException("保存失败,插入PACS数据异常！");
            }
        }
    }


    private boolean getDictionaryStatusByType(String pacs) {
        // TODO: wait 用到了ws_bk_dictionary网站字典，暂时返回false
//        QxDictionary dic = get(QxDictionary.class, Restrictions.eq("type", pacs));
//        return (dic == null || dic.getFlg() == null) ? false : "T".equals(dic.getFlg()) ? true : false;
        return false;
    }


    /**
     * 保存pacs
     *
     * @param patientCode
     * @return
     */
    public String savePacss(String patientCode) {
        Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
        if (patient == null) {
            return "体检号不存在";
        }
        PacsPeispatient pacsPatient = pacsPeispatientMapper.selectOne(new QueryWrapper<PacsPeispatient>().eq("patientcode", patientCode));
        if (pacsPatient == null) {
            pacsPatient = new PacsPeispatient();
            BeanUtils.copyProperties(patient, pacsPatient, new String[]{"id"});
            pacsPeispatientMapper.insert(pacsPatient);
        } else {
            BeanUtils.copyProperties(patient, pacsPatient, new String[]{"id"});
            pacsPeispatientMapper.updateById(pacsPatient);
        }
        //收费项目表
        List<Peispatientfeeitem> fis = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .isNull("f_transferedhl7")
                .eq("change_item", 0)
                .eq("id_patient", patientCode)
                .isNotNull("id_ks")
        );
        //PACS收费项目
        List<PacsPeispatientfeeitem> pacFis = pacsPeispatientfeeitemMapper.selectList(new QueryWrapper<PacsPeispatientfeeitem>().eq("id_patient", patientCode));
        Set<String> pacItemIds = new HashSet<String>();
        for (Peispatientfeeitem fi : fis) {
            SysDept dept = sysDeptMapper.getByDeptNo(fi.getIdKs());
            if (dept == null || dept.getIsFunction() == null || !"1".equals(dept.getIsFunction())) {
                continue;
            }
            //检查项目
            Items item = itemsMapper.getInfoById(fi.getIdExamfeeitem());
            if (item == null || item.getExamfeeitemCode() == null) {
                continue;
            }
            //pacs检查项目
            PacsItems pacItem = pacsItemsService.getOne(new QueryWrapper<PacsItems>().eq("examfeeitem_code", item.getExamfeeitemCode()));
            if (pacItem == null) {
                continue;
            }
            //PACS收费项目
            PacsPeispatientfeeitem pacFi = pacsPeispatientfeeitemMapper.selectOne(new QueryWrapper<PacsPeispatientfeeitem>()
                    .eq("id_patient", patientCode).eq("id_examfeeitem", pacItem.getId()));
            if (pacFi == null) {
                pacFi = new PacsPeispatientfeeitem();
                BeanUtils.copyProperties(fi, pacFi, new String[]{"id"});
                pacFi.setIdExamfeeitem(pacItem.getId());
                pacFi.setExamfeeitemName(pacItem.getExamfeeitemName());
                pacsPeispatientfeeitemMapper.insert(pacFi);
            } else {
                BeanUtils.copyProperties(fi, pacFi, new String[]{"idExamfeeitem", "examfeeitemName"
                        , "FExaminated", "id"});
                pacsPeispatientfeeitemMapper.updateById(pacFi);
            }
            pacItemIds.add(pacItem.getId());
        }
        for (PacsPeispatientfeeitem pfi : pacFis) {
            if (!pacItemIds.contains(pfi.getIdExamfeeitem())) {
                pacsPeispatientfeeitemMapper.deleteById(pfi);//由于更换项目，之前上传的图片将看不到
            }
        }
        return "success";
    }
}

