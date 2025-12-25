package com.center.medical.pacslis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.CrisisValuesaOrUpParam;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.CrisisValueService;
import com.center.medical.bean.dto.NursingRegistration;
import com.center.medical.bean.dto.NursingRegistrationDto;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.enums.KsId;
import com.center.medical.common.config.AdiconConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.Base64ToMultipartFile;
import com.center.medical.common.utils.file.MultipartFileUtil;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.dao.OutsidePictrueMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.Whysqzfw;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.BasexamltemSignMapper;
import com.center.medical.data.dao.WhysqzfwMapper;
import com.center.medical.pacslis.bean.dto.AdiconItemDto;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.LisExamDto;
import com.center.medical.pacslis.bean.dto.LisItemDto;
import com.center.medical.pacslis.dao.LisInterfaceMapper;
import com.center.medical.pacslis.service.LisInterfaceService;
import com.center.medical.reception.bean.model.OutsideCheckin;
import com.center.medical.reception.bean.model.OutsideHand;
import com.center.medical.reception.dao.OutsideCheckinMapper;
import com.center.medical.reception.dao.OutsideHandMapper;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.reception.service.PeispatientexamitemService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.sellcrm.service.RiskclientService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.HandleNewProjectsService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xhp
 * @since 2023-02-20 9:06
 */
@Slf4j
@Service("lisInterfaceService")
@RequiredArgsConstructor
public class LisInterfaceServiceImpl extends ServiceImpl<PeispatientexamitemMapper, Peispatientexamitem> implements LisInterfaceService {
    private final PeispatientMapper peispatientMapper;
    //    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final DescribeMapper describeMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final LisInterfaceMapper lisInterfaceMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final OutsideHandMapper outsideHandMapper;
    private final WhysqzfwMapper whysqzfwMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final ISysConfigService iSysConfigService;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final SysUserMapper sysUserMapper;
    private final SystemConfig systemConfig;
    private final AttachmentService attachmentService;
    private final OutsidePictrueMapper outsidePictrueMapper;
    private final OutsideCheckinMapper outsideCheckinMapper;
    private final SysBranchMapper sysBranchMapper;

    private final MapperFacade mapperFacade;

    private final HandleNewProjectsService handleNewProjectsService;

    private final Snowflake snowflake;

    private final PeispatientexamitemService peispatientexamitemService;

    private final RiskclientService riskclientService;
    private final CrisisValueService crisisValueService;
    @Autowired
    private LoadProperties loadProperties;

    /**
     * 获取Lis结果数据
     *
     * @param patientcode
     */
    @Override
    public R<String> receive(String patientcode) {
        //调用接口获取结果
        String url = iSysConfigService.getDomain().getLisDomain() + "/open/api/lis/list";
//        String url = "http://localhost:8092" + "/open/api/lis/list";
        Map<String, Object> param = new HashMap<>();
        log.info("获取LIS结果体检号:" + patientcode);
        param.put("patientcode", patientcode);

//        //是否有tap项目
//        Long count = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>().eq("change_item", 0).eq("id_patient", patientcode).eq("sfjj", 0).eq("f_giveup", 0).isNull("f_transferedhl7").isNotNull("id_ks").ne("f_examinated", 1).eq("id_examfeeitem","ff8080818ab02d0b018b18b149c0310b"));
//        param.put("containTap", count > 0 ? 1 : 0);
//
//        //是否有外送项目
//        List<AdiconGridDataVo> adiconGridData = sectionResultMainMapper.haveOutwardDelivery(patientcode);
//        param.put("containAdicon", CollectionUtil.isNotEmpty(adiconGridData) ? 1 : 0);
        AdiconConfig adiconConfig = iSysConfigService.getSysConfigObject(Constants.ADICON_CONFIG, AdiconConfig.class);
        if (ObjectUtils.isNotEmpty(adiconConfig)) {
            param.put("loginid", adiconConfig.getLoginid());
            param.put("password", adiconConfig.getPassword());
        }

        String lisConfigStr = iSysConfigService.selectConfigByKey(Constants.LIS_CONFIG);
        param.put("lisConfig",lisConfigStr);

//        log.info("打印一下参数:" + param);
        String s = HttpUtil.get(url, param);
//        log.info("请求结果：" + s);
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("请求lis接口失败{}、{}", url, JSONUtil.toJsonStr(responseEntity.getMsg()), patientcode);
            return R.fail("获取失败！");
        }
        if (ObjectUtils.isEmpty(responseEntity.getData())) {
            return R.fail("没有体检号" + patientcode + "的数据！");
        }

//        log.info("请求lis接口成功", JSONUtil.toJsonStr(responseEntity.getData()));

        //保存结果
        List<LisDto> lisDtos = ((List<JSONObject>) responseEntity.getData()).stream().map(
                jsonObject -> jsonObject.toBean(LisDto.class)
        ).collect(Collectors.toList());
        if (lisDtos.size() == 0) {
            log.error("请求lis接口失败：{}", "没有体检号" + patientcode + "的数据！");
            return R.fail("没有体检号" + patientcode + "的数据！");
        }
        save(patientcode, lisDtos);

        //检验科加项自动处理
        handleNewProjectsService.autoProcess(patientcode);
        //危急值自动提报
        crisisvalueSubmit(patientcode);
        return R.ok("获取成功！");
    }

    /**
     * 危机值自动提报
     *
     * @param patientcode
     */
    private void crisisvalueSubmit(String patientcode) {
        //城阳危急值不自动提报
        if (StringUtils.equals(loadProperties.name, "chengyang")) {
            return;
        }
        NursingRegistration crisisValue = iSysConfigService.getSysConfigObject(Constants.CRISISVALUE_SUBMIT_CONFIG, NursingRegistration.class);
        //危急值提报项目不为空，并且检验科的所有项目都已检了
        if (ObjectUtils.isNotEmpty(crisisValue)) {
            //查询该科室未出结果的项目
            int departmentResults = peispatientfeeitemService.getDepartmentResults(patientcode);
            if (departmentResults == 0) {
                List<NursingRegistrationDto> param = crisisValue.getParam();
                //提取需要提报项目的id
                List<String> idList = param.stream()
                        .map(NursingRegistrationDto::getId)
                        .collect(Collectors.toList());
                //如果该体检者的项目是阳性的话就自动提报

                List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new LambdaQueryWrapper<SectionResultTwo>()
                        .eq(SectionResultTwo::getPatientcode, patientcode)
                        .in(SectionResultTwo::getChargesId, idList)
                        .eq(SectionResultTwo::getPosistive, 1)
                        .eq(SectionResultTwo::getDivisionId, "19")
                );
                if (CollectionUtil.isNotEmpty(list)) {
                    //先查询是否生成过
                    long riskclientCount = riskclientService.count(new LambdaQueryWrapper<Riskclient>()
                            .eq(Riskclient::getTjid, patientcode)
                            .eq(Riskclient::getReportDivision, "19")
                    );
                    //没生成过再插入，生成过就跳过
                    if (riskclientCount == 0) {
                        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
                        CrisisValuesaOrUpParam crisisValuesaOrUpParam = new CrisisValuesaOrUpParam();
                        crisisValuesaOrUpParam.setTjid(patientcode);
                        crisisValuesaOrUpParam.setDoctorapply(peispatient.getDoctorapply());
                        crisisValuesaOrUpParam.setIdOpendoctor(peispatient.getIdOpendoctor());
                        crisisValuesaOrUpParam.setGwrymc(peispatient.getPatientname());
                        crisisValuesaOrUpParam.setXb(String.valueOf(peispatient.getIdSex()));
                        crisisValuesaOrUpParam.setNl(String.valueOf(peispatient.getAge()));
                        crisisValuesaOrUpParam.setOrgName(peispatient.getOrgName());
                        crisisValuesaOrUpParam.setIdOrg(peispatient.getIdOrg());
                        crisisValuesaOrUpParam.setOrgDepart(peispatient.getOrgDepart());
                        crisisValuesaOrUpParam.setLxdh(peispatient.getPhone());
                        crisisValuesaOrUpParam.setWjzjb("1");//危急值级别，默认高
                        crisisValuesaOrUpParam.setTirq(peispatient.getDateregister());
                        crisisValuesaOrUpParam.setReportMan("危急值自动提报");
                        crisisValuesaOrUpParam.setReportDivision("19");//提报科室默认检验科
                        crisisValuesaOrUpParam.setTjlx(Integer.valueOf(peispatient.getIdExamtype()));
                        //危机值小结
                        String result = list.stream()
                                .map(SectionResultTwo -> SectionResultTwo.getMs())
                                .collect(Collectors.joining(";"));
                        crisisValuesaOrUpParam.setWjzxj(result);
                        crisisValueService.saOrUp(crisisValuesaOrUpParam);
                    }
                }
            }


        }


    }


    /**
     * 保存lis结果
     *
     * @param patientcode 体检号
     * @param lisDtoList  lis结果
     */
    @Override
    @Transactional
    public void save(String patientcode, List<LisDto> lisDtoList) {
        Peispatient peispatient = checkPeispatient(patientcode);

        //先删除之前的附件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientcode).eq(Attachment::getFileType, "艾迪康报告"));

        //乙肝配置
        String hbvConfig = iSysConfigService.selectConfigByKey(Constants.HBV_CONFIG);
        JSONObject hbvjo = JSONUtil.parseObj(hbvConfig);
        String YGID = hbvjo.getStr("itemId");
        String ygldbId = hbvjo.getStr("ygldbId");
        JSONArray YGEIDS = hbvjo.getJSONArray("examIds");
        JSONArray YGIDS = hbvjo.getJSONArray("itemIds");
        //检验科室id
        String depId = KsId.JYKID.value().toString();
        //需要获取的收费项目
        List<LisItemDto> lisItemDtos = lisInterfaceMapper.selectItemList(patientcode, depId);
        //体检短号
        Integer shortCode = peispatient.getShortCode();
        //体检类型
        String tjlx = peispatient.getIdExamtype();
        //接害因素
        String jhys = peispatient.getJhys();
        //职业检查类型
        String medicaltype = peispatient.getMedicaltype();
        //体检者性别
        Integer idSex = peispatient.getIdSex();
        String patientname = peispatient.getPatientname();
        //用于综合体检的职业范围、职业状态、职业小结  key:检查项目ID value：Comboexamitem
        Map<String, List<Comboexamitem>> ceis = getComboexamitems(jhys, medicaltype, tjlx);

        //删除上次获取的数据
        sectionResultTwoMapper.delete(
                new QueryWrapper<SectionResultTwo>()
                        .eq("patientcode", patientcode)
                        .eq("division_id", depId)
        );
        describeMapper.delete(
                new QueryWrapper<Describe>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", depId)
        );
        baseMapper.delete(
                new QueryWrapper<Peispatientexamitem>()
                        .eq("patientcode", patientcode)
        );

        //科室检查主表
        SectionResultMain main = sectionResultMainMapper.selectOne(
                new QueryWrapper<SectionResultMain>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", depId)
        );
        if (main == null) {
            main = new SectionResultMain();
            main.setDepId(depId);
            main.setPatientcode(patientcode);
            main.setShortCode(shortCode);
            main.setAssociativeTable("PEISPATIENTEXAMITEM");
            sectionResultMainMapper.insert(main);
        }
        //科室检查主表ID
        String main_id = main.getId();

        //如果没有匹配上任何一条数据，falg=false，返回没有此体检号的数据
        boolean flag = false;
        //健康小结
        StringBuilder conclusions = new StringBuilder();
        //职业小结
        StringBuilder zy_conclusions = new StringBuilder();

        //外送结果上传
        List<OutsidePictrue> outsidePictrues = outsidePictrueMapper.selectList(
                new QueryWrapper<OutsidePictrue>()
                        .eq("patientcode", patientcode)
        );
        List<OutsideCheckin> outsideCheckins = outsideCheckinMapper.selectList(
                new QueryWrapper<OutsideCheckin>()
                        .eq("patientcode", patientcode)
                        .eq("status", 1)
        );
        Set<String> checkInChargeIds = new HashSet<>();
        for (OutsideCheckin outsideCheckin : outsideCheckins) {
            String chargeId = outsideCheckin.getIdCharge();
            if (StrUtil.isNotEmpty(chargeId)) checkInChargeIds.add(chargeId);
        }
        //所有上传了外送结果的基础收费项目id
        Set<String> outsideChargeIds = new HashSet<>();
        for (OutsidePictrue outsidePictrue : outsidePictrues) {
            String[] chargeIds = outsidePictrue.getChargeId().split(",");
            for (String chargeId : chargeIds) {
                if (checkInChargeIds.contains(chargeId)) outsideChargeIds.add(chargeId);
            }
        }

        String defaultCid = sysBranchMapper.getDefaultCid();
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());

        for (LisItemDto lisItemDto : lisItemDtos) {
            String itemCode = lisItemDto.getExamfeeitemCode();
            if (StrUtil.isEmpty(itemCode)) continue;
            //收费项目ID
            String itemId = lisItemDto.getIdExamfeeitem();
            //收费项目名称
            String itemName = lisItemDto.getExamfeeitemName();
            //true:是隐私项目
            boolean isPrivate = lisItemDto.getBgdybs() != null && "1".equals(lisItemDto.getBgdybs());
            //收费项目打印名称
            String examfeeitemNameprn = lisItemDto.getExamfeeitemNameprn();
            //艾迪康代码
            String adiconCode = lisItemDto.getAdiconCode();
            //已检标志  1已检  如果所有小项都没获取到结果，也都不是手动录入，就认为未检
            int FExaminated = 1;
            //体检者收费项目
            Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(
                    new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", patientcode)
                            .eq("id_examfeeitem", itemId)
                            .eq("f_registered", 1)
                            .eq("change_item", 0)
                            .or(i -> i.eq("f_mark_feereturn", 0).isNull("f_mark_feereturn"))
            );

            //查询检查小项
            List<LisExamDto> lisExamDtos = lisInterfaceMapper.selectExamList(itemId);
            //收费项目健康小结
            StringBuilder fee_builder = new StringBuilder();
            //收费项目职业小结
            StringBuilder zy_fee_builder = new StringBuilder();
            //收费项目检查时间（任意取其中一个检查项目的检查时间。一个收费项目下 所有检查项目的检查时间肯定是相同的）
            Date examinatetime = null;
            //是否有上传外送结果 如果上传过图片，就不修改收费项目已检状态，暂时不考虑手动录入的情况，因为没有用到手动录入结果
            boolean hasOutsideResult = outsideChargeIds.contains(itemId);

            for (LisExamDto lisExamDto : lisExamDtos) {
                //检查项目ID
                String examId = lisExamDto.getId();
                //接口代码
                String examCode = lisExamDto.getInterfaceCode();
                //艾迪康项目可能没有接口代码
//                if (StrUtil.isEmpty(examCode)) continue;
                OutsideHand hand = outsideHandMapper.selectOne(
                        new QueryWrapper<OutsideHand>()
                                .eq("id_charge", itemId)
                                .eq("id_check", examId)
                                .eq("patientcode", patientcode)
                );
                //如果是手动录入的,跳过
                if (hand != null) {
                    continue;
                }
                Double maleMax = lisExamDto.getValuemalemax();//男性上线
                Double maleMin = lisExamDto.getValuemalemin();//男性下限
                Double femaleMax = lisExamDto.getValuefemalemax();//女性上限
                Double femaleMin = lisExamDto.getValuefemalemin();//女性下限
                String orderIndex = lisExamDto.getOrderIndex() == null ? null : lisExamDto.getOrderIndex().toString();//收费项目中检查项目行序
                String examName = lisExamDto.getExamitemName();
                Integer isDesc = lisExamDto.getIsDesc();//描述进小结
                int byHand = lisExamDto.getByHand() == null ? 0 : lisExamDto.getByHand();
                String examitemNameprn = lisExamDto.getExamitemNameprn();
                String examitemCodehm = lisExamDto.getExamitemCodehm();
                String idConclusionhi = lisExamDto.getIdConclusionhi();// 结论词(高)
                String idConclusionlo = lisExamDto.getIdConclusionlo();// 结论词(低)
                String idConclusionpo = lisExamDto.getIdConclusionpo();// 结论词(阳)
                Boolean itemFlag = lisExamDto.getItemFlag();
                String adiconExamCode = lisExamDto.getExamitemCode3();//检查项目艾迪康代码

                //查询结果
                LisDto lisDto = searchResult(lisDtoList, itemCode, examCode, adiconCode, adiconExamCode);

                //如果有小项都没获取到结果，也都不是手动录入，也没上传过外送结果图片，就认为未检
                if (!(lisDto != null || byHand == 1 || hasOutsideResult)) {
                    FExaminated = 0;
                }

                //处理结果
                if (lisDto != null) {
                    flag = true;
                    String reviewNotes = lisDto.getRefRange();
                    Double numberValue = lisDto.getExamItemValuesNumber();//结果数值
                    String stringValue = lisDto.getExamItemValuesShort();//结果String 数值与STring 选不是NULL的展示
                    if(stringValue!=null)stringValue=stringValue.replaceAll("＋","+");//将特殊加号转成普通加号
                    String status = conversionStatus(lisDto.getStatus());//转换状态,把字母变成箭头
                    status = hmstatus(stringValue, examitemCodehm, status);

                    //PeisPatientExamItem表
                    Peispatientexamitem item = new Peispatientexamitem();
                    item.setPatientcode(patientcode);
                    item.setShortCode(shortCode);
                    item.setIdExamfeeitem(itemId);
                    item.setExamfeeitem(itemName);
                    item.setIdExamitem(examId);
                    item.setExamitemNameR(examName);
                    item.setExamitemCodeR(examCode);
                    item.setRefrange(reviewNotes);
                    item.setExamitemvaluesshort(stringValue);
                    item.setExamitemvaluesnumber(numberValue);
                    item.setPatientName(patientname);
                    item.setLisCode(lisDto.getLisCode());
                    item.setUnits(lisDto.getUnits());
                    item.setStatus(status);
                    item.setDepId(depId);
                    item.setExamitemvaluesreport(lisDto.getExamItemValuesReport());
                    item.setAuditName(lisDto.getAuditName());
                    String inspectName = lisDto.getInspectName();
                    item.setInspectName(inspectName);
                    item.setExamDoctor(lisDto.getExamDoctor());
                    item.setLisybbh(lisDto.getLisybbh() == null ? null : lisDto.getLisybbh().doubleValue());
                    item.setBefidDisporderR(orderIndex);
                    if (lisDto.getExamDateTime() != null) {
                        Date date = localDateTimeToDate(lisDto.getExamDateTime());
                        if (examinatetime == null) {
                            examinatetime = date;
                        }
                        item.setExamDateTime(date);
                    }
                    item.setAuditDate(lisDto.getAuditDate() == null ? null : localDateTimeToDate(lisDto.getAuditDate()));
                    item.setReceiveDate(lisDto.getReceiveDate() == null ? null : localDateTimeToDate(lisDto.getReceiveDate()));
                    if (idSex != null && idSex.intValue() == 0) {
                        item.setReflow(maleMin);
                        item.setRefhigh(maleMax);
                    } else if (idSex != null && idSex.intValue() == 1) {
                        item.setReflow(femaleMin);
                        item.setRefhigh(femaleMax);
                    }
                    item.setExamitemNameprn(examitemNameprn);
                    item.setExamfeeitemNameprn(examfeeitemNameprn);
                    item.setAdiconCode(lisDto.getAdiconCode());
                    String inspectCode = lisDto.getInspectCode();
                    //虹桥和瑞美、金卫可以直接在视图中查出检查医生代码，艾迪康不能
                    if (StrUtil.isEmpty(inspectCode) && StrUtil.isNotEmpty(inspectName)) {
                        SysUser sysUser = sysUserMapper.selectUserByUserName(inspectName);
                        if (sysUser != null) inspectCode = sysUser.getUserCode();
                    }
                    item.setInspectCode(inspectCode);
                    item.setExamitemvaluesnumber3(StrUtil.isNotEmpty(lisDto.getInspectCode()) ? 1.0 : null);

                    NursingRegistration adiconImagesConfig = iSysConfigService.getSysConfigObject(Constants.ADICON_IMAGES_CONFIG, NursingRegistration.class);
                    List<String> idList = null;
                    if (ObjectUtils.isNotEmpty(adiconImagesConfig)) {
                        List<NursingRegistrationDto> param = adiconImagesConfig.getParam();
                        idList = param.stream()
                                .map(NursingRegistrationDto::getId)
                                .collect(Collectors.toList());
                    }
                    //保存艾迪康图片
                    if (ObjectUtils.isNotEmpty(lisDto.getAdiconPdfBase64()) && CollectionUtils.isNotEmpty(idList)) {
                        if (idList.contains(itemId)) {
                            log.info("保存艾迪康图片itemId:{}",itemId);
                            //霸州TCT转换有问题,换了一种获取方式jpeg，单独处理
                            if (StringUtils.equals(loadProperties.name, "bazhou") && StringUtils.equals(lisDto.getExamCode(), "B0000196")){
                                saveAdiconJpeg(lisDto.getAdiconPdfBase64(),itemId,patientcode,defaultCid,baseDir);
                            }else {
                                saveAdiconPdf(lisDto.getAdiconPdfBase64(),itemId,patientcode,defaultCid,baseDir);
                            }
                        }
                    }


                    //健康小结
                    String reportValue = item.getExamitemvaluesreport();
                    String processedUnits = getUnit(item.getUnits());
                    String describe = examitemNameprn + ":" + reportValue
                            + processedUnits
                            + (checkStatus(status) ? status : "");
                    if (
                            isDesc.intValue() == 1
                                    || isAbnormal(status, stringValue, itemFlag)
                                    || (stringValue != null && (stringValue.indexOf("阳性") != -1 || testPlus(stringValue)))
                    ) {
                        if (fee_builder.length() == 0) {
                        } else {
                            fee_builder.append("、");
                        }
                        fee_builder.append(describe);
                    }

                    //职业范围、职业状态、职业小结
                    List<Comboexamitem> cei_list = ceis == null ? null : ceis.get(examId);
                    String zyStatus = null;
                    String reportRange = null;
                    Integer zytjlx = null;
                    if ("0".equals(tjlx)) {
                        zytjlx = 0;
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        zytjlx = 1;
                    } else if ("2".equals(tjlx)) {
                        if (cei_list != null) {
                            zytjlx = 1;
                        } else {
                            zytjlx = 0;
                        }
                    }
                    item.setType(zytjlx);
                    if (cei_list != null) {
                        boolean f_in = false;
                        for (int k = 0; k < cei_list.size(); k++) {
                            Comboexamitem cei_in = cei_list.get(k);
                            Double low = null;
                            Double high = null;
                            if (idSex != null && idSex.intValue() == 0) {//男
                                low = cei_in.getScoperFloor();
                                high = cei_in.getScopeUpper();
                            } else if (idSex != null && idSex.intValue() == 1) {
                                low = cei_in.getGscoperfloor();
                                high = cei_in.getGscopeupper();
                            }
                            //维护时不会出现只有上限或只有下限的情况
                            if (StrUtil.isNotEmpty(stringValue)) {
                                zyStatus = status;
                                reportRange = reviewNotes;
                                break;
                            } else if (low != null && high != null) {
                                if (numberValue.doubleValue() > high.doubleValue()) {
                                    zyStatus = "↑";
                                } else if (numberValue.doubleValue() < low.doubleValue()) {
                                    zyStatus = "↓";
                                } else {
                                    zyStatus = "N";
                                }
                                reportRange = low + "-" + high;
                            }
                            //职业范围  如果有异常保存异常的  否则随便保存一个
                            if (zyStatus != null
                                    && checkStatus(zyStatus)//!"N".equals(zyStatus)
                                    && low != null && high != null) {
                                if (zy_fee_builder.length() == 0) {
                                } else {
                                    zy_fee_builder.append("、");
                                }
                                zy_fee_builder.append(examitemNameprn + ":" + reportValue
                                        + processedUnits + ("N".equals(zyStatus) ? "" : zyStatus)
                                );
                                f_in = true;
                                break;
                            }
                            //数字结果没有匹配到异常
                            if (k == cei_list.size() - 1) {
//                                if (!"N".equals(status) && !"".equals(status)) {
                                if (checkStatus(status)) {//瑞美有M 虹桥没有
                                    zy_fee_builder.append(examitemNameprn + ":" + reportValue
                                            + processedUnits
                                            + (checkStatus(status) ? status : ""));
                                    f_in = true;
                                }
                            }
                        }
                        if (zyStatus == null) {
                            zyStatus = status;
                            reportRange = reviewNotes;
                        }
                        //stringValue
                        if ((isDesc.intValue() == 1
                                || (stringValue != null && (stringValue.indexOf("阳性") != -1
                                || isAbnormal(zyStatus, stringValue, itemFlag)
                                || testPlus(stringValue))))
                                && !f_in) {
                            if (zy_fee_builder.length() == 0) {
                            } else {
                                zy_fee_builder.append("、");
                            }
                            zy_fee_builder.append(examitemNameprn + ":" + reportValue
                                    + processedUnits
                                    + (checkStatus(zyStatus) ? zyStatus : ""));
                        }
                    } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                        boolean f_in = false;
                        List<Whysqzfw> qzfw_list = whysqzfwMapper.selectList(
                                new QueryWrapper<Whysqzfw>()
                                        .eq("is_delete", 0)
                                        .eq("jc_id", examId)
                                        .in("harm_name", jhys.split(","))
                        );
                        for (Whysqzfw qzfw : qzfw_list) {
                            Double low = null;
                            Double high = null;
                            if (idSex != null && idSex.intValue() == 0) {//男
                                low = (double) qzfw.getScoperFloor();
                                high = (double) qzfw.getScopeUpper();
                            } else if (idSex != null && idSex.intValue() == 1) {
                                low = (double) qzfw.getGscoperfloor();
                                high = (double) qzfw.getGscopeupper();
                            }
                            //维护时不会出现只有上限或只有下限的情况
                            if (StrUtil.isNotEmpty(stringValue)) {
                                zyStatus = status;
                                reportRange = reviewNotes;
                                break;
                            } else if (low != null && high != null) {
                                if (numberValue.doubleValue() > high.doubleValue()) {
                                    zyStatus = "↑";
                                } else if (numberValue.doubleValue() < low.doubleValue()) {
                                    zyStatus = "↓";
                                } else {
                                    zyStatus = "N";
                                }
                                reportRange = low + "-" + high;
                            }
                            //职业范围  如果有异常保存异常的  否则随便保存一个
                            if (zyStatus != null
                                    && checkStatus(zyStatus)//!"N".equals(zyStatus)
                                    && low != null && high != null) {
                                if (zy_fee_builder.length() == 0) {
                                } else {
                                    zy_fee_builder.append("、");
                                }
                                zy_fee_builder.append(examitemNameprn + ":" + reportValue
                                        + processedUnits
                                        + (checkStatus(zyStatus) ? zyStatus : ""));
                                f_in = true;
                                break;
                            }
                        }
                        if (zyStatus == null) {
                            zyStatus = status;
                            reportRange = reviewNotes;
                        }
                        //stringValue
                        if ((isDesc.intValue() == 1
                                || (StrUtil.isEmpty(stringValue) && checkStatus(zyStatus))//如果qzfw_list为空
                                || (stringValue != null && (stringValue.indexOf("阳性") != -1
                                || isAbnormal(zyStatus, stringValue, itemFlag)
                                || testPlus(stringValue))))
                                && !f_in) {
                            if (zy_fee_builder.length() == 0) {
                            } else {
                                zy_fee_builder.append("、");
                            }
                            zy_fee_builder.append(examitemNameprn + ":" + reportValue
                                    + processedUnits
                                    + (checkStatus(zyStatus) ? zyStatus : ""));

                        }
                    }
                    item.setReportRange(reportRange);
                    item.setZyStatus(zyStatus);

                    //sectionResultTwo
                    if (StrUtil.isNotEmpty(stringValue)) {//字符型结果
                        if ((testPlus(stringValue)
                                || stringValue.indexOf("阳性") != -1
                                || isSpecialNormal(stringValue, itemFlag)
                        )
                                && idConclusionpo != null) {//有+号为阳性
                            SectionResultTwo two = new SectionResultTwo();
                            two.setMainId(main_id);
                            two.setVerdictId(examId);
                            two.setPosistive(1);
                            two.setPatientcode(patientcode);
                            two.setShortCode(shortCode);
                            two.setBasconclusionId(idConclusionpo);
                            two.setDivisionId(depId);
                            two.setChargesId(itemId);
                            two.setTjlx(zytjlx);
                            two.setMs(describe);
                            sectionResultTwoMapper.insert(two);
                        }
                    } else {//数字结果
                        if (zytjlx != null && zytjlx.intValue() == 1) {//职业体检或是职业项目
                            if (!"N".equals(zyStatus)) {
                                if ("↑".equals(zyStatus) && idConclusionhi != null) {
                                    SectionResultTwo two = new SectionResultTwo();
                                    two.setMainId(main_id);
                                    two.setVerdictId(examId);
                                    two.setPatientcode(patientcode);
                                    two.setShortCode(shortCode);
                                    two.setBasconclusionId(idConclusionhi);//高值
                                    two.setDivisionId(depId);
                                    two.setChargesId(itemId);
                                    two.setTjlx((!"N".equals(status) && !"M".equals(status)) ? 1 : 2);
                                    two.setPosistive(1);
                                    two.setMs(describe);
                                    sectionResultTwoMapper.insert(two);
                                } else if ("↓".equals(zyStatus) && idConclusionlo != null) {
                                    SectionResultTwo two = new SectionResultTwo();
                                    two.setMainId(main_id);
                                    two.setVerdictId(examId);
                                    two.setPatientcode(patientcode);
                                    two.setShortCode(shortCode);
                                    two.setBasconclusionId(idConclusionlo);//低值
                                    two.setDivisionId(depId);
                                    two.setChargesId(itemId);
                                    two.setTjlx((!"N".equals(status) && !"M".equals(status)) ? 1 : 2);
                                    two.setPosistive(1);
                                    two.setMs(describe);
                                    sectionResultTwoMapper.insert(two);
                                }
                            }
                        } else {
                            if (!"N".equals(status)) {
                                if ("↑".equals(status) && idConclusionhi != null) {
                                    SectionResultTwo two = new SectionResultTwo();
                                    two.setMainId(main_id);
                                    two.setVerdictId(examId);
                                    two.setPatientcode(patientcode);
                                    two.setShortCode(shortCode);
                                    two.setBasconclusionId(idConclusionhi);//高值
                                    two.setDivisionId(depId);
                                    two.setChargesId(itemId);
                                    two.setTjlx(zytjlx);
                                    two.setPosistive(1);
                                    two.setMs(describe);
                                    sectionResultTwoMapper.insert(two);
                                } else if ("↓".equals(status) && idConclusionlo != null) {
                                    SectionResultTwo two = new SectionResultTwo();
                                    two.setMainId(main_id);
                                    two.setVerdictId(examId);
                                    two.setPatientcode(patientcode);
                                    two.setShortCode(shortCode);
                                    two.setBasconclusionId(idConclusionlo);//低值
                                    two.setDivisionId(depId);
                                    two.setChargesId(itemId);
                                    two.setTjlx(zytjlx);
                                    two.setPosistive(1);
                                    two.setMs(describe);
                                    sectionResultTwoMapper.insert(two);
                                }
                            }
                        }
                    }

                    //DESCRIBE
                    Describe des = new Describe();
                    des.setFeeId(itemId);
                    des.setFeeName(examfeeitemNameprn);
                    des.setPatientcode(patientcode);
                    des.setShortCode(shortCode);
                    des.setItemId(examId);
                    des.setItemName(examitemNameprn);
                    des.setInspectDescribe(describe);
                    des.setSignList(reportValue);
                    des.setDepId(depId);
                    des.setTjlx(zytjlx);
                    describeMapper.insert(des);

                    baseMapper.insert(item);
                }


            }

            feeitem.setExaminatetime(examinatetime);
            if (FExaminated == 1) {
                feeitem.setFExaminated(1);//已检
                if (YGIDS.contains(itemId) || YGID.equals(itemId)) {//如果是乙肝五项
                    int zytjlx = isZy(jhys, medicaltype, tjlx, YGID);

                    String yg_describe = null;
                    SectionResultTwo old = sectionResultTwoMapper.selectOne(
                            new QueryWrapper<SectionResultTwo>()
                                    .eq("patientcode", patientcode)
                                    .eq("verdict_id", ygldbId)
                    );
                    if (old != null) {
                        yg_describe = old.getMs();
                    } else {
                        int[] yx = new int[YGEIDS.size()];
                        for (int i = 0; i < YGEIDS.size(); i++) {
                            String ygexamId = YGEIDS.getStr(i);
                            Peispatientexamitem pe = baseMapper.selectOne(
                                    new QueryWrapper<Peispatientexamitem>()
                                            .eq("patientcode", patientcode)
                                            .eq("id_examitem", ygexamId)
                            );
                            // 5个项目必须都有值，才会有体征词（值不为-1）
                            if (pe == null) {
                                yg_describe = "";
                                break;
                            }
                            //1表示阳性
                            int positive = 0;
                            String stringValue = pe.getExamitemvaluesshort();
                            Basexamltem basexamltem = basexamltemMapper.selectById(ygexamId);
                            if (stringValue != null && (testPlus(stringValue)
                                    || stringValue.indexOf("阳性") != -1
                                    || isSpecialNormal(stringValue, basexamltem.getItemFlag() != null && basexamltem.getItemFlag() == 1)
                            )) {
                                positive = 1;
                            } else {
                                String zyStatus = pe.getZyStatus();
                                String status = pe.getStatus();
                                if (zytjlx == 1) {
                                    if (checkStatus(zyStatus)) {
                                        positive = 1;
                                    }
                                } else {
                                    if (checkStatus(status)) {
                                        positive = 1;
                                    }
                                }
                            }
                            yx[i] = positive;
                        }
                        if (yg_describe == null) {
                            String signId = getYgwxSignId(yx);
                            if (signId == null) {
                                yg_describe = "";
                            } else {
                                BasexamltemSign sign = basexamltemSignMapper.selectById(signId);
                                if (sign == null) {
                                    yg_describe = "";
                                } else {
                                    SectionResultTwo two = new SectionResultTwo();
                                    two.setMainId(main_id);
                                    two.setVerdictId(ygldbId);
                                    two.setPosistive(sign.getIsPositive());
                                    two.setPatientcode(patientcode);
                                    two.setShortCode(shortCode);
                                    two.setBasconclusionId(sign.getResultId());
                                    two.setDivisionId(depId);
                                    two.setChargesId(itemId);
                                    two.setTjlx(zytjlx);
                                    Basexamltem exam = basexamltemMapper.selectById(sign.getInspectId());
                                    String describe = (exam == null ? "" : (exam.getExamitemNameprn() + ":")) + sign.getName();
                                    two.setMs(describe);
                                    two.setNodule(sign.getId());
                                    sectionResultTwoMapper.insert(two);
                                    yg_describe = describe;
                                }
                            }
                        }
                    }

                    if (yg_describe.length() > 0) {
                        if (fee_builder.length() == 0) {
                            fee_builder.append(yg_describe);
                        } else {
                            fee_builder.append("、" + yg_describe);
                        }
                        if (zytjlx == 1) {
                            if (zy_fee_builder.length() == 0) {
                                zy_fee_builder.append(yg_describe);
                            } else {
                                zy_fee_builder.append("、" + yg_describe);
                            }
                        }
                    }
                }
            } else {//如果用两套Lis，另一套lis获取的结果，被这一套lis给清除了，需要重置为未检，这样另一套的线程能再获取。（页面上目前只能使用其中一个的全部获取）
                feeitem.setFExaminated(0);
            }
            if (!isPrivate) {
                if (fee_builder.length() > 0) {
                    fee_builder.append(";");
                    conclusions.append(fee_builder);
                }
                if (zy_fee_builder.length() > 0) {
                    zy_fee_builder.append(";");
                    zy_conclusions.append(zy_fee_builder);
                }
            }
            peispatientfeeitemMapper.updateById(feeitem);
        }


        //检查结果表
        if (flag) {//如果有数据
            main.setConclusions(conclusions.length() == 0 ? "未见异常" : conclusions.toString());
            if ("1".equals(tjlx) || "3".equals(tjlx)) {
                main.setZyConclusions(zy_conclusions.length() == 0 ? "未见异常" : zy_conclusions.toString());
                main.setConclusions(main.getZyConclusions());//职业体检   健康小结=职业小结
            } else if ("2".equals(tjlx)) {
                main.setZyConclusions(zy_conclusions.length() == 0 ? "未见异常" : zy_conclusions.toString());
            }
            main.setIsAudit(1);//已审核
            main.setIsFinish(0);//未上传
            sectionResultMainMapper.updateById(main);
        } else {
            throw new ServiceException("没有体检号" + patientcode + "的数据!");
        }
        boolean b = outsideMainService.getAllSfxmtzjStatus(patientcode);
        if (b) {
            peispatient.setFReadytofinal(1);//0:已备单 1:分检完成
            peisStateService.setScbs(patientcode, 0);
            peispatient.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
            for (Peispatientfeeitem other : other_items) {
                other.setFExaminated(1);//设置未关联科室项目为已检
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        peispatient.setFExamstarted(1);
        peispatientMapper.updateById(peispatient);
    }

    public static void main(String[] args) {
        String data = " M";
        String value = conversionStatus(data);
        System.out.println("打印一下："+value);
    }

    /**
     * 状态转换
     * @param status
     * @return
     */
    private static String conversionStatus(String status) {
        if (StringUtils.isBlank(status)) {
           return status;
        }
        status = status.trim();
        switch (status) {
            case "P":
//            case "M":
//            case "H":
                return "↑";
//            case "L":
//                return "↓";
            default:
                return status;
        }
    }

    /**
     * 保存jpeg 的base64
     * @param base64
     * @param itemId
     * @param patientcode
     */
    private void saveAdiconJpeg(String base64, String itemId, String patientcode,String defaultCid,String baseDir) {
        try {
            if (!base64.startsWith("data:image")){
                base64="data:image/jpeg;base64,"+ base64;
            }
            Base64ToMultipartFile multipartFile = MultipartFileUtil.base64ConvertMultipartFile(base64);
            upImage(multipartFile, itemId, patientcode, 0, baseDir, defaultCid);
        } catch (Exception e) {
            log.info("保存艾迪康图片失败,错误信息：{}",e);
        }
    }

    /**
     * 保存艾迪康图片
     * @param adiconPdfBase64
     * @param itemId
     * @param patientcode
     */
    private void saveAdiconPdf(String adiconPdfBase64,String itemId,String patientcode,String defaultCid,String baseDir) {
        try {
            log.info("adiconPdfBase64的length:{}",adiconPdfBase64.length());
            byte[] bytes = Base64.getDecoder().decode(adiconPdfBase64);
            // 检查PDF文件头
            String header = new String(bytes, 0, Math.min(10, bytes.length));
            log.info("PDF文件头: {}", header);
            // 加载 PDF 文档
            PDDocument document = PDDocument.load(new ByteArrayInputStream(bytes));
            // 创建 PDF 渲染器
            PDFRenderer renderer = new PDFRenderer(document);
            // 遍历每一页，将其转换为图片
            for (int j = 0; j < document.getNumberOfPages(); j++) {
                // 渲染当前页为 BufferedImage
                BufferedImage image = renderer.renderImageWithDPI(j, 300, ImageType.RGB);
                // 生成唯一的文件名
                String filename = snowflake.nextId() + ".png";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", baos);
                byte[] imageBytes = baos.toByteArray();
                MultipartFile multipartFile = new MockMultipartFile(filename, filename, "image/png", imageBytes);
                //保存图片
                upImage(multipartFile, itemId, patientcode, j, baseDir, defaultCid);
            }
            // 关闭文档
            document.close();
        } catch (Exception e) {
            log.info("保存艾迪康图片失败,错误信息：{}",e);
        }
    }

    @Override
    @Transactional
    public Peispatient checkPeispatient(String patientcode) {
        Peispatient peispatient = peispatientMapper.selectOne(
                new QueryWrapper<Peispatient>()
                        .eq("patientcode", patientcode)
        );
        if (Objects.isNull(peispatient)) {
            throw new ServiceException("体检号" + patientcode + "不存在");
        }
        if (peispatient.getFPaused() != null && peispatient.getFPaused().intValue() == 1) {
            throw new ServiceException("体检号" + patientcode + "已被禁检");
        }
        /**老系统中已经将组禁检改成所有体检者禁检
         String idOrgreservationgroup = peispatient.getIdOrgreservationgroup();
         if (StrUtil.isNotEmpty(idOrgreservationgroup)) {
         Peisorgreservationgroup peisorgreservationgroup = peisorgreservationgroupMapper.selectOne(
         new QueryWrapper<Peisorgreservationgroup>()
         .eq("id", idOrgreservationgroup)
         .eq("is_delete", 0)
         );
         if (peisorgreservationgroup != null && peisorgreservationgroup.getFGrouppaused() != null && peisorgreservationgroup.getFGrouppaused().intValue() == 1) {
         throw new ServiceException("体检号" + patientcode + "已被禁检");
         }
         }
         */
        if (peispatient.getJktjzt() != null && peispatient.getJktjzt().intValue() == 1) {
            throw new ServiceException("获取失败，本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
            throw new ServiceException("获取失败，本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
            throw new ServiceException("获取失败，本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("获取失败，本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        return peispatient;
    }

    @Override
    @Transactional
    public Map<String, List<Comboexamitem>> getComboexamitems(String jhys, String medicaltype, String tjlx) {
        Map<String, List<Comboexamitem>> ceis = null;
        if ("2".equals(tjlx)) {
            List<Comboexamitem> eis = comboexamitemMapper.selectList(
                    new QueryWrapper<Comboexamitem>()
                            .in("harm_id", jhys.split(","))
                            .eq("medical_type", medicaltype)
            );
            ceis = new HashMap<>();
            for (Comboexamitem cei : eis) {
                String examId = cei.getExamId();
                if (ceis.get(examId) != null) {
                    ceis.get(examId).add(cei);
                } else {
                    List<Comboexamitem> list = new ArrayList<Comboexamitem>();
                    list.add(cei);
                    ceis.put(examId, list);
                }
            }
        }
        return ceis;
    }

    /**
     * 查找检查小项结果
     *
     * @param lisDtoList     所有结果
     * @param itemCode       收费项目接口代码
     * @param examCode       检查项目接口代码
     * @param adiconCode     体检者收费项目艾迪康代码
     * @param adiconExamCode 检查项目艾迪康代码
     * @return
     */
    LisDto searchResult(List<LisDto> lisDtoList, String itemCode, String examCode, String adiconCode, String adiconExamCode) {
        LisDto result = null;
        //艾迪康
        //如果在检验科中设置体检者收费项目的艾迪康代码
        if (StrUtil.isNotEmpty(adiconCode)) {
            for (LisDto lisDto : lisDtoList) {
                if (
                        adiconCode.equals(lisDto.getAdiconCode())
                                && adiconExamCode.equals(lisDto.getExamCode())
                ) {
                    result = lisDto;
                    break;
                }
            }
            return result;
        }
        if (StrUtil.isNotEmpty(adiconExamCode)) {
            for (LisDto lisDto : lisDtoList) {
                if (
                        adiconExamCode.equals(lisDto.getExamCode())
                ) {
                    result = lisDto;
                    break;
                }
            }
//            return result;
        }


        if (result == null) {
            for (LisDto lisDto : lisDtoList) {
                if (
                        (
                                itemCode.equals(lisDto.getItemCode()) //虹桥
                                        || StrUtil.isEmpty(lisDto.getItemCode())//瑞美、金卫只能获取到检查项目代码
                        )
                                && examCode != null && examCode.equals(lisDto.getExamCode())
                ) {
                    result = lisDto;
                    break;
                }
            }
        }

        if (result == null) {
            for (LisDto lisDto : lisDtoList) {
                if (itemCode.equals(lisDto.getExamCode())) {//虹桥
                    result = lisDto;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * localDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 单位处理
     *
     * @param units
     * @return
     */
    String getUnit(String units) {
        return units == null ? "" : units.matches("[0-9]+[\\s\\S]*") ? ("*" + units) : (" " + units);
    }

    /**
     * 判断结果中有加号
     *
     * @param str 字符结果
     * @return
     */
    boolean testPlus(String str) {
        return str.replaceAll("[Ee]\\+", "").indexOf("+") != -1;
    }

    /**
     * @param status
     * @param result   字符结果
     * @param itemFlag true:只要结果不是未见或阴性就要算做异常
     * @return
     */
    boolean isAbnormal(String status, String result, Boolean itemFlag) {
        if (checkStatus(status)) {
            return true;
        }
        return isSpecialNormal(result, itemFlag);
    }

    /**
     * @param result   字符结果
     * @param itemFlag true:只要结果不是未见或阴性就要算做异常
     * @return
     */
    boolean isSpecialNormal(String result, Boolean itemFlag) {
        if (StrUtil.isNotEmpty(result) && itemFlag != null && itemFlag) {
            return !("未见".equals(result) || "阴性".equals(result));
        }
        return false;
    }

    /**
     * 判断字符结果中的大于号小于号  1 < 2 > 3<>
     *
     * @param stringValue
     * @param examitemCodehm
     * @param status
     * @return
     */
    String hmstatus(String stringValue, String examitemCodehm, String status) {
        //+－弱阳性和这些，也判断为↑
        List<String> list = Arrays.asList("+－","1+","2+","3+","阳性(见图文报告)","Ⅲ°");
        if (stringValue != null) {
            if (list.contains(stringValue)){
                stringValue = ">";
                if (StringUtils.isEmpty(examitemCodehm)) examitemCodehm = "2";
            }
            if ("1".equals(examitemCodehm) || "3".equals(examitemCodehm)) {
                if (stringValue.indexOf("<") != -1) {
                    status = "↓";
                }
            }
            if ("2".equals(examitemCodehm) || "3".equals(examitemCodehm)) {
                if (stringValue.indexOf(">") != -1) {
                    status = "↑";
                }
            }
        }
        return status;
    }

    /**
     * 判断乙肝是否为职业项目
     *
     * @param jhys
     * @param medicaltype
     * @param tjlx
     * @param itemId      乙肝收费项目id
     * @return
     */
    int isZy(String jhys, String medicaltype, String tjlx, String itemId) {
        if ("0".equals(tjlx)) {
            return 0;
        } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
            return 1;
        } else if ("2".equals(tjlx)) {
            List<Comboexamitem> ceis = comboexamitemMapper.selectList(
                    new QueryWrapper<Comboexamitem>()
                            .eq("item_id", itemId)
                            .eq("harm_id", jhys.split(","))
                            .eq("medical_type", medicaltype)
            );
            if (ceis.size() > 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 乙肝结果计算
     *
     * @param yx
     * @return
     */
    String getYgwxSignId(int[] yx) {
        String signId = null;
        if (yx[0] == 0 && yx[1] == 0 && yx[2] == 0 && yx[3] == 0 && yx[4] == 0) {
            signId = "2368";
        } else if (yx[0] == 0 && yx[1] == 0 && yx[2] == 0 && yx[3] == 1 && yx[4] == 0) {
            signId = "4171";
        } else if (yx[0] == 0 && yx[1] == 0 && yx[2] == 0 && yx[3] == 1 && yx[4] == 1) {
            signId = "2369";
        } else if (yx[0] == 0 && yx[1] == 0 && yx[2] == 1 && yx[3] == 1 && yx[4] == 1) {
            signId = "4173";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 0 && yx[3] == 0 && yx[4] == 0) {
            signId = "2370";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 0 && yx[3] == 0 && yx[4] == 1) {
            signId = "2371";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 0 && yx[3] == 1 && yx[4] == 0) {
            signId = "2708";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 0 && yx[3] == 1 && yx[4] == 1) {
            signId = "2372";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 1 && yx[3] == 0 && yx[4] == 0) {
            signId = "4172";
        } else if (yx[0] == 0 && yx[1] == 1 && yx[2] == 1 && yx[3] == 0 && yx[4] == 1) {
            signId = "4167";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 0 && yx[3] == 0 && yx[4] == 0) {
            signId = "2373";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 0 && yx[3] == 0 && yx[4] == 1) {
            signId = "2374";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 0 && yx[3] == 1 && yx[4] == 1) {
            signId = "2375";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 1 && yx[3] == 0 && yx[4] == 0) {
            signId = "4190";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 1 && yx[3] == 0 && yx[4] == 1) {
            signId = "2377";
        } else if (yx[0] == 1 && yx[1] == 0 && yx[2] == 1 && yx[3] == 1 && yx[4] == 1) {
            signId = "4175";
        } else if (yx[0] == 1 && yx[1] == 1 && yx[2] == 0 && yx[3] == 0 && yx[4] == 0) {
            signId = "4168";
        } else if (yx[0] == 1 && yx[1] == 1 && yx[2] == 0 && yx[3] == 0 && yx[4] == 1) {
            signId = "4170";
        } else if (yx[0] == 1 && yx[1] == 1 && yx[2] == 0 && yx[3] == 1 && yx[4] == 1) {
            signId = "4174";
        } else if (yx[0] == 1 && yx[1] == 1 && yx[2] == 1 && yx[3] == 0 && yx[4] == 1) {
            signId = "2378";
        } else if (yx[0] == 1 && yx[1] == 1 && yx[2] == 1 && yx[3] == 1 && yx[4] == 1) {
            signId = "2379";
        } else {

        }
        return signId;
    }

    boolean checkStatus(String status) {
        //霸州中核有多个箭头的
        Set<String> validStatuses = new HashSet<>();
        validStatuses.add("↑");
        validStatuses.add("↑↑");
        validStatuses.add("↑↑↑");
        validStatuses.add("↓");
        validStatuses.add("↓↓");
        validStatuses.add("↓↓↓");
        validStatuses.add("阳性");

        return validStatuses.contains(status);
    }


    private File convertMultipartFileToFile(MultipartFile multipartFile) throws Exception {
        File file = Files.createTempFile("temp", null).toFile();
        multipartFile.transferTo(file);
        return file;
    }


    private String saveImageToTempDirectory(BufferedImage image, String filename) throws Exception {
        File tempDir = new File("temp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        File outputFile = new File(tempDir.getAbsolutePath() + File.separator + filename);
        try (OutputStream output = new FileOutputStream(outputFile)) {
            ImageIO.write(image, "png", output);
        }
        log.info("outputFile.getAbsolutePath:{}",outputFile.getAbsolutePath());
        return outputFile.getAbsolutePath();
    }

    private MultipartFile convertPngToMultipartFile(String imageUrl, String filename) throws Exception {
        File pngFile = new File(imageUrl);
        FileInputStream input = new FileInputStream(pngFile);
        ByteArrayResource resource = new ByteArrayResource(readAllBytes(input));
        return new MockMultipartFile(filename, resource.getByteArray());
    }

    private byte[] readAllBytes(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    private void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("已成功删除文件: " + filePath);
            } else {
                System.out.println("无法删除文件: " + filePath);
            }
        } else {
            System.out.println("文件不存在: " + filePath);
        }
    }


    private void upImage(MultipartFile multipartFile, String feeItemId, String patientcode, int i, String baseDir, String cId) {
        Attachment attachmentImage = new Attachment();
        String extNameImage = "png";
        attachmentImage.setFileType("艾迪康报告");
        attachmentImage.setType(AttachmentType.IMAGE.value());
        attachmentImage.setBranchId(cId);
        attachmentImage.setCreatedate(new Date());
        attachmentImage.setStatus(0);
        attachmentImage.setFeeItemId(feeItemId);
        attachmentImage.setPatientcode(patientcode);
        attachmentImage.setFileSort(i + "");
        attachmentImage.setInReport(1);
        try {
            attachmentService.uploadFile(multipartFile, attachmentImage, baseDir, extNameImage, null, true);
        } catch (IOException e) {
            throw new ServiceException("第三方报告上传保存失败！");
        }
    }


    /**
     * 获取艾迪康结果
     *
     * @param patientcode
     * @return
     */
    @Override
    public R getAdiconList(String patientcode) {
        //调用接口获取结果
        String url = iSysConfigService.getDomain().getLisDomain() + "/open/api/lis/getAdiconList";
//        String url = "http://localhost:8092" + "/open/api/lis/getAdiconList";
        Map<String, Object> param = new HashMap<>();
        log.info("获取LIS结果体检号:" + patientcode);
        param.put("patientcode", patientcode);
        AdiconConfig adiconConfig = iSysConfigService.getSysConfigObject(Constants.ADICON_CONFIG, AdiconConfig.class);
        if (ObjectUtils.isNotEmpty(adiconConfig)) {
            param.put("loginid", adiconConfig.getLoginid());
            param.put("password", adiconConfig.getPassword());
        }
        String s = HttpUtil.get(url, param);
//        log.info("请求结果：" + s);
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("请求lis接口失败{}、{}", url, JSONUtil.toJsonStr(responseEntity.getMsg()), patientcode);
            return R.fail("获取失败！");
        }
        log.info("请求lis接口成功", JSONUtil.toJsonStr(responseEntity.getData()));

        //保存结果
        List<AdiconItemDto> lisDtos = ((List<JSONObject>) responseEntity.getData()).stream().map(
                jsonObject -> jsonObject.toBean(AdiconItemDto.class)
        ).collect(Collectors.toList());
        if (lisDtos.size() == 0) {
            log.error("请求lis接口失败：{}", "没有体检号" + patientcode + "的数据！");
            return R.fail("没有体检号" + patientcode + "的数据！");
        }
        return R.ok(lisDtos);
    }

    /**
     * 获取Tap结果
     *
     * @param patientcode
     * @return
     */
    @Override
    public R getTap(String patientcode) {
        //调用接口获取结果
        String url = iSysConfigService.getDomain().getLisDomain() + "/open/api/lis/getTap";
//        String url = "http://localhost:8092" + "/open/api/lis/getAdiconList";
        Map<String, Object> param = new HashMap<>();
        log.info("获取LIS结果体检号:" + patientcode);
        param.put("patientcode", patientcode);
        String s = HttpUtil.get(url, param);
//        log.info("请求结果：" + s);
        R responseEntity = JSONUtil.toBean(s, R.class);

        if (200 != responseEntity.getCode()) {
            log.error("请求lis接口失败{}、{}", url, JSONUtil.toJsonStr(responseEntity.getMsg()), patientcode);
            return R.fail("获取失败！");
        }
        log.info("请求lis接口成功", JSONUtil.toJsonStr(responseEntity.getData()));

        //保存结果
        List<AdiconItemDto> lisDtos = ((List<JSONObject>) responseEntity.getData()).stream().map(
                jsonObject -> jsonObject.toBean(AdiconItemDto.class)
        ).collect(Collectors.toList());
        if (lisDtos.size() == 0) {
            log.error("请求lis接口失败：{}", "没有体检号" + patientcode + "的数据！");
            return R.fail("没有体检号" + patientcode + "的数据！");
        }
        return R.ok(lisDtos);
    }
}
