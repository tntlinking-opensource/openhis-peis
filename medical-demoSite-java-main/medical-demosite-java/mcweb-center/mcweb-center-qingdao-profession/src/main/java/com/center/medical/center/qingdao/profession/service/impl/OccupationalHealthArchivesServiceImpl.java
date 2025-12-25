package com.center.medical.center.qingdao.profession.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.center.qingdao.profession.command.*;
import com.center.medical.center.qingdao.profession.constant.Constants;
import com.center.medical.center.qingdao.profession.entity.dto.*;
import com.center.medical.center.qingdao.profession.entity.excel.SendPatientcode;
import com.center.medical.center.qingdao.profession.entity.oracle.OrPeispatient;
import com.center.medical.center.qingdao.profession.entity.oracle.OrPeispatientConsultation;
import com.center.medical.center.qingdao.profession.entity.persistent.*;
import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.entity.properties.ElectricalAudiometryProperties;
import com.center.medical.center.qingdao.profession.entity.properties.ExcelProperties;
import com.center.medical.center.qingdao.profession.entity.properties.JzjkProperties;
import com.center.medical.center.qingdao.profession.exception.ErrorRuntimeException;
import com.center.medical.center.qingdao.profession.mapper.QjkMapper;
import com.center.medical.center.qingdao.profession.orrepository.OrPeispatientConsultationRepository;
import com.center.medical.center.qingdao.profession.orrepository.OrPeispatientRepository;
import com.center.medical.center.qingdao.profession.repository.*;
import com.center.medical.center.qingdao.profession.service.*;
import com.center.medical.center.qingdao.profession.utils.Base64ToMultipartFile;
import com.center.medical.center.qingdao.profession.utils.MultipartFileUtil;
import com.center.medical.center.qingdao.profession.utils.RequestService;
import com.center.medical.center.qingdao.profession.utils.SpecialTreatmentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.center.medical.center.qingdao.profession.configuration.ThreadPoolConfiguration.corePoolSize;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OccupationalHealthArchivesServiceImpl implements OccupationalHealthArchivesService {
    private final static String orderNo = "16533";
    private final ConfigProperties properties;
    private final ItemQueryRepository itemQueryRepository;
    private final PeispatientRepository peispatientRepository;
    private final OrPeispatientRepository orPeispatientRepository;
    private final RequestService requestService;
    private final NationCache nationCache;
    private final SellcustomerRepository sellcustomerRepository;
    private final ConclusionRepository conclusionRepository;
    private final ZoneCache zoneCache;
    private final DictionaryCache dictionaryCache;
    private final ObjectMapper objectMapper;
    private final PeisStateExtRepository peisStateExtRepository;
    private final BaseZoneQdRepository baseZoneQdRepository;
    private final PeispatientConsultationRepository peispatientConsultationRepository;
    private final OrPeispatientConsultationRepository orPeispatientConsultationRepository;
    private final LogService logService;
    private final ExportService exportService;
    private final HarmPackageMatchQueryRepository harmPackageMatchQueryRepository;
    private final ElectricalAudiometryProperties electricalAudiometryProperties;
    private final LungRepository lungRepository;
    private final HarmRepository harmRepository;
    private final WzZysRepository wzZysRepository;
    private final ThreadPoolTaskExecutor taskExecutor;
    private final LoginService loginService;
    private final SpecialHazardReferenceCache specialHazardReferenceCache;
    private final CheckItemReferenceCache checkItemReferenceCache;
    private final PeispatientexamitemRepository peispatientexamitemRepository;
    private final PeispatientfeeitemRepository peispatientfeeitemRepository;
    private ExcelProperties excelProperties;
    private SpecialTreatmentService specialTreatmentService;
    private final DescribeRepository describeRepository;
    private final CheckItemRepository checkItemRepository;
    private final QjkMapper qjkMapper;
    private final AttachmentService attachmentService;
    private final JzjkProperties jzjkProperties;
    private final static List<String> otherRiskReason = Arrays.asList("1099", "2999", "3099", "4009", "5009", "6099", "9");


    @SneakyThrows
    @Override
    @Transactional(noRollbackFor = Exception.class)//让运行时异常抛出时不回滚
    public void uploadCondition(Date startDate, Date endDate) {
        if (StringUtils.isEmpty(properties.getRequestUrl())){
            return;
        }

        String token = loginService.getToken();
        ArrayList<String> strings1 = new ArrayList<>();


        /**上传职业、综合类型*/
        List<String> patientCodes = peisStateExtRepository.findByCreateDateRangle(startDate, endDate);
        if (patientCodes.size() < 1) {
            log.info("已全部上传");
        }
        for (String patientCode : patientCodes) {
            logUpload(token, patientCode);
        }
        strings1.addAll(patientCodes);


        /**上传复查类型 2024-06-12 体检类型是复查的不需要上传
         List<ReviewPatientDTO> reviewPatientDTOS = peisStateExtRepository.findReviewByDateRangle(startDate, endDate);

         //        List<ReviewPatientDTO> reviewPatientDTOS=new ArrayList<>();
         //        ReviewPatientDTO reviewPatientDTO2=new ReviewPatientDTO();
         //        reviewPatientDTO2.setPatientCode("030000433871");
         //        reviewPatientDTO2.setPrePatientCode("030000428052");
         //        reviewPatientDTOS.add(reviewPatientDTO2);

         for (ReviewPatientDTO reviewPatientDTO : reviewPatientDTOS) {
         logUpload(token, reviewPatientDTO);
         }
         if (ObjectUtil.isNotEmpty(reviewPatientDTOS)) {
         reviewPatientDTOS.stream().map(ReviewPatientDTO::getPatientCode).distinct().forEach(strings1::add);
         } else {
         log.info("复查已经全部上传");
         }
         */

        /**导出*/
        //暂时不导出了，部署新中心的时候，能导出好几万份，需要的时候再打开
//        if (ObjectUtil.isNotEmpty(strings1)) {
//            if (strings1.size() < 5000) {
//                exportService.export(strings1);
//            } else {
//                List<List<String>> partition = Lists.partition(strings1, 5000);
//                for (List<String> strings : partition) {
//                    exportService.export(strings);
//                }
//            }
//        }

    }

    @SneakyThrows
    @Override
    public void uploadByOrder(String orderNo) {
        String token = loginService.getToken();
        int size = corePoolSize;
        List<String> patientCodes = peisStateExtRepository.queryByOrderNo(orderNo);
        List<List<String>> partition = Lists.partition(patientCodes, size);
        for (List<String> strings : partition) {
            for (String patientCode : strings) {
                logUpload(token, patientCode);
                token = loginService.getToken();
            }
        }

        exportService.export(patientCodes);
    }


    @SneakyThrows
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void uploadBatch() {
        String token = loginService.getToken();
        List<String> patientCodes = peisStateExtRepository.findPatientCodes();
        int size = corePoolSize;
        if (patientCodes.size() < 1) {
            log.info("已全部上传");
            return;
        }
        List<List<String>> partition = Lists.partition(patientCodes, size);
        for (List<String> strings : partition) {
            for (String patientCode : strings) {
                logUpload(token, patientCode);
                token = loginService.getToken();
            }
        }
        exportService.export(patientCodes);
    }

    @SneakyThrows
    @Override
    public void uploadBatch(List<String> patientCodes) {
        String token = loginService.getToken();
        int size = corePoolSize;
        if (patientCodes.size() < 1) {
            log.info("已全部上传");
            return;
        }
        List<List<String>> partition = Lists.partition(patientCodes, size);
        for (List<String> strings : partition) {
            for (String patientCode : strings) {
                logUpload(token, patientCode);
                token = loginService.getToken();
            }
        }
        exportService.export(patientCodes);
    }

    @Override
    public void uploadSingle(String patientCode) {
        String token = loginService.getToken(properties.getUserCode(), properties.getPassword());
        logUpload(token, patientCode);
//        try {
//            Result result = upload(patientCode, token);
//            log.info("上传结果:" + JSONUtil.toJsonStr(result));
//            List<InfoDTO> data = result.getData();
//            for (InfoDTO datum : data) {
//                String code = datum.getCode();
//                String msg = datum.getMsg();
//                logService.saveLog(patientCode, code, msg);
//            }
//        } catch (Exception e) {
//            String message;
//            if (e instanceof ErrorRuntimeException) {
//                message = e.getCause().getMessage();
//            } else {
//                message = "其他错误";
//            }
//            log.error(patientCode + ":" + message, e);
//            logService.saveLogError(patientCode, message);
//        }
        exportService.export(Collections.singletonList(patientCode));
    }

    @Override
    public void uploadReviewSingle(String patientCode) {
        String token = loginService.getToken(properties.getUserCode(), properties.getPassword());
        log.error("上传体检号:" + patientCode);
        Peispatient peispatient = peispatientRepository.findByPatientcode(patientCode);
        ReviewPatientDTO reviewPatientDTO = new ReviewPatientDTO();
        reviewPatientDTO.setPatientCode(peispatient.getPatientcode());
        reviewPatientDTO.setPrePatientCode(peispatient.getInpatientno());
        logUpload(token, reviewPatientDTO);
//        try {
//            Result result = upload(patientCode, token);
//            log.info("上传结果:" + JSONUtil.toJsonStr(result));
//            List<InfoDTO> data = result.getData();
//            for (InfoDTO datum : data) {
//                String code = datum.getCode();
//                String msg = datum.getMsg();
//                logService.saveLog(patientCode, code, msg);
//            }
//        } catch (Exception e) {
//            String message;
//            if (e instanceof ErrorRuntimeException) {
//                message = e.getCause().getMessage();
//            } else {
//                message = "其他错误";
//            }
//            log.error(patientCode + ":" + message, e);
//            logService.saveLogError(patientCode, message);
//        }
        exportService.export(Collections.singletonList(patientCode));
    }

    /**
     * 上传非复查
     * @param patientCode
     * @param token
     * @return
     * @throws JsonProcessingException
     * @throws ErrorRuntimeException
     */
    @SneakyThrows(ErrorRuntimeException.class)
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public Result upload(String patientCode, String token) throws JsonProcessingException, ErrorRuntimeException {
        log.error(patientCode + "开始上传");
        Peispatient peispatient = peispatientRepository.findByPatientcode(patientCode);
        log.error(patientCode + "获取体检人");
        if(peispatient==null){
            throw new ErrorRuntimeException("体检号不存在"+patientCode);
        }
        String idOrg = peispatient.getIdOrg();
        log.error(patientCode + "获取用人单位");
        if(StrUtil.isEmpty(idOrg)){
            throw new ErrorRuntimeException("单位Id为空");
        }
        Sellcustomer sellcustomer = sellcustomerRepository.findById(idOrg).orElse(new Sellcustomer());
        log.error(patientCode + "获取结论");
        //职业总检下的总检处理意见
        List<ConclusionDto> conclusionDtos = conclusionRepository.queryByPatientCode(patientCode);
        //前台管理-危害因素匹配，匹配的套餐里的危害因素
        List<HarmPackageMatchDto> harmPackageMatchDtos = harmPackageMatchQueryRepository.query(peispatient.getIdTjtc(), peispatient.getJhys());
        List<WzZys> wzZysList = wzZysRepository.findAllByDjls(peispatient.getPatientcode());
        StringBuilder stringBuilder = new StringBuilder();
        RDataDTO dto = new RDataDTO();
        dto.setType("health");
        setData(dto, peispatient, sellcustomer, conclusionDtos, harmPackageMatchDtos, wzZysList, stringBuilder);

//            log.info("JSONUtil.toJsonPrettyStr(dto) = " + JSONUtil.toJsonPrettyStr(dto));
        if (ObjectUtils.isNotEmpty(stringBuilder)) {
            throw new ErrorRuntimeException(stringBuilder.toString());
        }
        return upload(dto, token);
    }

    /**
     * 上传复查
     * @param patientCode 本次复查体检号
     * @param prePatientCode 上次体检号
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    @SneakyThrows(ErrorRuntimeException.class)
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public Result upload(String patientCode, String prePatientCode, String token) throws JsonProcessingException {
        log.info(patientCode + "开始上传");
        Peispatient reviewPeipatient = peispatientRepository.findByPatientcode(patientCode);
        Peispatient peispatient = peispatientRepository.findByPatientcode(prePatientCode);
        //这里如果查不到，需要查询老系统
        if (ObjectUtils.isEmpty(peispatient)){
            OrPeispatient orPeispatient = orPeispatientRepository.findByPatientcode(prePatientCode);
            log.info("查询老系统数据:{}",orPeispatient);
            peispatient = new Peispatient();
            //从orPeispatient复制到peispatient
            BeanUtils.copyProperties(orPeispatient,peispatient);
        }
        log.info(patientCode + "获取体检人");
        String idOrg = peispatient.getIdOrg();
        log.info(patientCode + "获取用人单位");
        Sellcustomer sellcustomer = sellcustomerRepository.findById(idOrg).orElse(new Sellcustomer());
        log.info(patientCode + "获取结论");
        List<ConclusionDto> conclusionDtos = conclusionRepository.queryByPatientCode(patientCode);
        List<ConclusionDto> reviewConclusionDtos = conclusionRepository.queryByPatientCode(prePatientCode);
        List<HarmPackageMatchDto> harmPackageMatchDtos = harmPackageMatchQueryRepository.query(peispatient.getIdTjtc(), peispatient.getJhys());
        List<WzZys> wzZysList = wzZysRepository.findAllByDjls(peispatient.getPatientcode());
        //用于记录错误信息
        StringBuilder stringBuilder = new StringBuilder();
        RDataDTO dto = new RDataDTO();
        dto.setType("health");
        setData(dto, peispatient, reviewPeipatient, sellcustomer, conclusionDtos, reviewConclusionDtos, harmPackageMatchDtos, wzZysList, stringBuilder);
//            log.info("JSONUtil.toJsonPrettyStr(dto) = " + JSONUtil.toJsonPrettyStr(dto));
        //错误信息不为空，报错并记录日志
        if (ObjectUtils.isNotEmpty(stringBuilder)) {
            throw new ErrorRuntimeException(stringBuilder.toString());
        }
        return upload(dto, token);
    }


    @Override
    public void uploadSingle(String patientCode, String token, HttpServerResponse response) {
        uploadSingle(patientCode);
    }

    @Override
    public void uploadCondition(String token, Date startDate, Date endDate, HttpServerResponse response) {

    }

    /**
     * 上传非复查
     * @param token
     * @param patientCode
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void logUpload(String token, String patientCode) {
        log.error("上传体检号:" + patientCode);
        try {
            Result result = upload(patientCode, token);
            log.error("上传结果:" + JSONUtil.toJsonStr(result));
            List<InfoDTO> data = result.getData();
            for (InfoDTO datum : data) {
                String code = datum.getCode();
                String msg = datum.getMsg();
                logService.saveLog(patientCode, code, msg);
            }
        } catch (Exception e) {
            String message = "";
            if (e instanceof ErrorRuntimeException) {
                message += e.getMessage();
                message += ((ErrorRuntimeException) e).getMsg();
                if (message.contains("null")) {
                    message.replaceAll("null", "");
                }
                List<Info> infos = ((ErrorRuntimeException) e).getInfos();
                if (ObjectUtils.isNotEmpty(infos)) {
                    message += infos.stream().map(info -> {
                        StringJoiner joiner = new StringJoiner(",");
                        String table = info.getTable();
                        String field = info.getField();
                        String codeX = info.getCode();
                        String msgX = info.getMsg();
                        joiner.add(table);
                        joiner.add(field);
                        joiner.add(msgX);
                        return joiner.toString();
                    }).collect(Collectors.joining(","));
                }
            } else {
                log.error("其他错误",e);
                message = "其他错误";
            }
            log.error(patientCode + ":" + message, e);
            if (message.toUpperCase().contains("TOKEN错误验证失败")) {
                log.error("正在重新获取TOKEN,之前的token是{}",token);
                loginService.refreshToken();
                token = loginService.getToken();
                log.error("重新获取TOKEN成功,新的token是{}",token);
                logUpload(token, patientCode);
                return;
            }
            logService.saveLogError(patientCode, message);

        }
    }

    /**
     * 上传复查
     * @param token
     * @param reviewPatientDTO
     */
    private void logUpload(String token, ReviewPatientDTO reviewPatientDTO) {
        log.error("上传复查体检号:" + reviewPatientDTO.getPatientCode());
        try {
            Result result = upload(reviewPatientDTO.getPatientCode(), reviewPatientDTO.getPrePatientCode(), token);
            log.error("上传结果:" + JSONUtil.toJsonStr(result));
            List<InfoDTO> data = result.getData();
            for (InfoDTO datum : data) {
                String code = datum.getCode();
                String msg = datum.getMsg();
                logService.saveLog(reviewPatientDTO.getPatientCode(), code, msg);
            }
        } catch (Exception e) {
            String message = "";
            if (e instanceof ErrorRuntimeException) {
                message += e.getMessage();
                message += ((ErrorRuntimeException) e).getMsg();
                if (message.contains("null")) {
                    message.replaceAll("null", "");
                }
                List<Info> infos = ((ErrorRuntimeException) e).getInfos();
                if (ObjectUtils.isNotEmpty(infos)) {
                    message += infos.stream().map(info -> {
                        StringJoiner joiner = new StringJoiner(",");
                        String table = info.getTable();
                        String field = info.getField();
                        String codeX = info.getCode();
                        String msgX = info.getMsg();
                        joiner.add(table);
                        joiner.add(field);
                        joiner.add(msgX);
                        return joiner.toString();
                    }).collect(Collectors.joining(","));
                }
            } else {
                log.error("其他错误",e);
                message = "其他错误";
            }
            log.info(reviewPatientDTO.getPatientCode() + ":" + message, e);
            if (message.toUpperCase().contains("TOKEN错误验证失败")) {
                log.error("正在重新获取TOKEN,之前的token是{}",token);
                loginService.refreshToken();
                token = loginService.getToken();
                log.error("重新获取TOKEN成功,新的token是{}",token);
                logUpload(token, reviewPatientDTO);
                return;
            }
            logService.saveLogError(reviewPatientDTO.getPatientCode(), message);

        }
    }

    /**
     *
     * @param dto
     * @param peispatient
     * @param sellcustomer
     * @param conclusionDtos 总检结论
     * @param harmPackageMatchDtos  套餐危害因素匹配
     * @param wzZysList 职业史
     * @param stringBuilder 记录错误信息，不为空会报错并记录到日志
     */
    private void setData(RDataDTO dto, Peispatient peispatient, Sellcustomer sellcustomer, List<ConclusionDto> conclusionDtos
                , List<HarmPackageMatchDto> harmPackageMatchDtos, List<WzZys> wzZysList, StringBuilder stringBuilder) {
        ArrayList<RDataDTO.DataDTO> data = new ArrayList<>();
        //用于保存所有结果
        RDataDTO.DataDTO dataDTO = new RDataDTO.DataDTO();
        StringJoiner personJoiner = new StringJoiner(",", "", "");
        StringJoiner enterPriceJoiner = new StringJoiner(",", "", "");
        StringJoiner examResultJoiner = new StringJoiner(",", "", "");
        StringJoiner conclusionJoiner = new StringJoiner(",");
//        List<String> otherRiskReason = Arrays.asList("9", "1099", "2999", "3099", "4009", "5009");
//        List<String> otherRiskReason = Arrays.asList("1099", "2999", "3099", "4009", "5009", "6099", "9");
        setHealthPhysicalReport(dataDTO, peispatient, sellcustomer, harmPackageMatchDtos, personJoiner, enterPriceJoiner, otherRiskReason,null);
        setCareerHistoryList(dataDTO, wzZysList, harmPackageMatchDtos, otherRiskReason, stringBuilder);
        setPastMedicalHistoryList(dataDTO);
        //将检查项目、收费项目结果塞入dataDTO
        setExamItemResult(dataDTO, peispatient, harmPackageMatchDtos, examResultJoiner);
        setExamConclusionList(dataDTO, peispatient, conclusionDtos, harmPackageMatchDtos, conclusionJoiner);
        if (personJoiner.length() > 1) {
            stringBuilder.append(",").append("个人信息:").append(personJoiner);
        }
        if (enterPriceJoiner.length() > 1) {
            stringBuilder.append(",").append("企业信息:").append(enterPriceJoiner);
        }
        if (examResultJoiner.length() > 1) {
            stringBuilder.append(",").append("项目信息:").append(examResultJoiner);
        }
        if (conclusionJoiner.length() > 1) {
            stringBuilder.append(",").append("结论信息:").append(conclusionJoiner);
        }
        if (stringBuilder.length() > 2) {
            String string = stringBuilder.toString();
            stringBuilder = new StringBuilder(string.substring(string.indexOf(",")));
        }
        data.add(dataDTO);
        dto.setData(data);
    }

    /**
     * 复查setData
     * @param dto
     * @param peispatient 上一次体检者
     * @param reviewPeipatient 本次复查体检者
     * @param sellcustomer
     * @param conclusionDtos
     * @param reviewConclusionDtos
     * @param harmPackageMatchDtos
     * @param wzZysList
     * @param stringBuilder 记录错误信息，不为空会报错并记录到日志
     */
    private void setData(RDataDTO dto, Peispatient peispatient, Peispatient reviewPeipatient, Sellcustomer sellcustomer, List<ConclusionDto> conclusionDtos
            , List<ConclusionDto> reviewConclusionDtos, List<HarmPackageMatchDto> harmPackageMatchDtos, List<WzZys> wzZysList, StringBuilder stringBuilder) {
        ArrayList<RDataDTO.DataDTO> data = new ArrayList<>();
        RDataDTO.DataDTO dataDTO = new RDataDTO.DataDTO();
        StringJoiner personJoiner = new StringJoiner(",", "", "");
        StringJoiner enterPriceJoiner = new StringJoiner(",", "", "");
        StringJoiner examResultJoiner = new StringJoiner(",", "", "");
        StringJoiner conclusionJoiner = new StringJoiner(",");
//        List<String> otherRiskReason = Arrays.asList("9", "1099", "2999", "3099", "4009", "5009");
//        List<String> otherRiskReason = Arrays.asList("1099", "2999", "3099", "4009", "5009", "6099", "9");
        //文档中HealthPhysicalReport表格
        setHealthPhysicalReport(dataDTO, reviewPeipatient, sellcustomer, harmPackageMatchDtos, personJoiner, enterPriceJoiner, otherRiskReason,peispatient);
        setCareerHistoryList(dataDTO, wzZysList, harmPackageMatchDtos, otherRiskReason, stringBuilder);
        setPastMedicalHistoryList(dataDTO);
        setExamItemResult(dataDTO, peispatient, reviewPeipatient, harmPackageMatchDtos, examResultJoiner);
        setExamConclusionList(dataDTO, peispatient, reviewPeipatient, conclusionDtos, reviewConclusionDtos, harmPackageMatchDtos, conclusionJoiner);

        data.add(dataDTO);
        dto.setData(data);

        if (personJoiner.length() > 1) {
            stringBuilder.append(",").append("个人信息:").append(personJoiner);
        }
        if (enterPriceJoiner.length() > 1) {
            stringBuilder.append(",").append("企业信息:").append(enterPriceJoiner);
        }
        if (examResultJoiner.length() > 1) {
            stringBuilder.append(",").append("项目信息:").append(examResultJoiner);
        }
        if (conclusionJoiner.length() > 1) {
            stringBuilder.append(",").append("结论信息:").append(conclusionJoiner);
        }
        if (stringBuilder.length() > 2) {
            String string = stringBuilder.toString();
            stringBuilder = new StringBuilder(string.substring(string.indexOf(",")));
        }
    }


    private void setExamItemResult(RDataDTO.DataDTO dataDTO, Peispatient peispatient, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner stringBuilder) {
        String patientcode = peispatient.getPatientcode();
        StringJoiner errorMessage = new StringJoiner(",");
        StringJoiner joiner = new StringJoiner(",");
        //查询此体检号所有已检或已的职业项目(按收费项目id查，如果不是同一个项目但有职业小项就查不出来)
        List<FeeItemDTO> feeItemDTOS = itemQueryRepository.queryFeeItem(patientcode);
        specialTreatmentService.treatItemId(feeItemDTOS);
        //查询COMBOEXAMITEM表，获取所有职业项目(包含非必检)
        List<NeedFeeItemDTO> needFeeItemDTOS = itemQueryRepository.queryNeedFeeItem(peispatient.getMedicaltype(), peispatient.getJhys());
//        List<String> list = Arrays.asList("网织红细胞(职业)", "血清葡萄糖-6-磷酸脱氢酶缺乏症筛查试验");
//        List<MissingFeeItemDTO> missingItems = itemQueryRepository.queryMissingFeeItemsDTOS(patientcode, list);
        //所有职业项目所属的职业基础套餐id
        Set<String> comboIds = needFeeItemDTOS.stream().map(NeedFeeItemDTO::getComboId).collect(Collectors.toSet());
        //所有职业项目id(包含非必检)
        Set<String> needFeeIds = needFeeItemDTOS.stream().map(NeedFeeItemDTO::getItemId).collect(Collectors.toSet());
        //所有职业基础套餐中的所有（必检）收费项目id
        Set<String> finalNeedFeeIds = itemQueryRepository.queryNeedFeeItemMust(comboIds);
        //此体检号所有已检或已补检的必检职业项目
        Set<String> needContainsFeeItemIds = feeItemDTOS.stream().filter(feeItemDTO -> finalNeedFeeIds.contains(feeItemDTO.getItemId())).map(FeeItemDTO::getItemId).collect(Collectors.toSet());
        ///获取选检项目，似乎没有什么意义
//        needFeeIds.removeAll(finalNeedFeeIds);
        //去掉此体检号的项目
        finalNeedFeeIds.removeIf(needContainsFeeItemIds::contains);
        //最终转换为EXAM_ITEM_RESULT
        Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet = new LinkedHashSet<>();
        //最终转换为EXAM_ITEM_RESULT_LIST
        Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet = new LinkedHashSet<>();
        HashSet<String> harmSet = new HashSet<>();
        //获取已检项目数据,放入checkItemSet feeItemDTOS.
        //只要是已检或者已补检，就会将结果放入checkItemSet,无论是否是职业项目
        doneItem(dataDTO, harmPackageMatchDtos, patientcode, errorMessage, joiner, feeItemDTOS, feeItemSet, checkItemSet, harmSet);
        log.error("checkItemSet数据:{}", checkItemSet);
        //生成未检项目数据 finalNeedFeeIds-》finalNeedFeeIds-feeItemDTO
        mustItem(dataDTO, peispatient, harmPackageMatchDtos, errorMessage, joiner, needFeeItemDTOS, finalNeedFeeIds, feeItemSet, checkItemSet, harmSet);
//        selectedItem(dataDTO, peispatient, harmPackageMatchDtos, errorMessage, joiner, needFeeItemDTOS, needFeeIds, feeItemSet, checkItemSet, harmSet);
//        职业问诊
        occupationalConsultation(dataDTO, patientcode, joiner, feeItemSet, checkItemSet);

        //一般检查
        //查询一般检查科室结果
        List<OrdinaryCheckItemDto> ordinaryCheckItemDtos = itemQueryRepository.queryOrdinaryCheckItem(patientcode);
        HashSet<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> ordinaryCheckItemDtoList = new HashSet<>();
        //只要一般检查有结果，就上传
        if (ObjectUtils.isNotEmpty(ordinaryCheckItemDtos)) {
            Set<String> strings = new HashSet<>(Arrays.asList(joiner.toString().split(",")));
            String collect = strings.stream().filter(ObjectUtils::isNotEmpty).sorted(String::compareTo).collect(Collectors.joining(","));
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            examitemresultdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultdto.setExamNum(dataDTO.getExamNum());
            examitemresultdto.setItamCode(dataDTO.getContactFactorCode());
            examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
            examitemresultdto.setExamItemPcode("2010000");
            examitemresultdto.setExamItemPname("一般情况");
            examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examitemresultdto.setOperatetype("Add");
            feeItemSet.add(examitemresultdto);
            Double height = 0D;
            Double weight = 0D;
            boolean heightIsCorrect = true;
            boolean weightIsCorrect = true;
            try {
                height = ordinaryCheckItemDtos.stream().filter(data -> "身高".equals(data.getHsExamItemName())).findFirst().map(data -> Double.valueOf(data.getExamResult())).orElse(1D);
            } catch (Exception e) {
                log.info("身高错误", e);
                heightIsCorrect = false;
            }
            try {
                weight = ordinaryCheckItemDtos.stream().filter(data -> "体重".equals(data.getHsExamItemName())).findFirst().map(data -> Double.valueOf(data.getExamResult())).orElse(1D);
            } catch (Exception e) {
                log.info("体重错误", e);
                weightIsCorrect = false;
            }
            //将被add到checkitemset
            ordinaryCheckItemDtoList = new HashSet<>();
            for (OrdinaryCheckItemDto ordinaryCheckItemDto : ordinaryCheckItemDtos) {
                if ("血压结论".equals(ordinaryCheckItemDto.getHsExamItemName()) || "营养状况".equals(ordinaryCheckItemDto.getHsExamItemName())) {
                    continue;
                }
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setExamItemUnitCode("%");
//                examitemresultlistdto.setReferenceRangeMin("无");
//                examitemresultlistdto.setReferenceRangeMax("无");
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                examitemresultlistdto.setExamItemPcode(examitemresultdto.getExamItemPcode());
                examitemresultlistdto.setExamItemPname(examitemresultdto.getExamItemPname());
                BaseDictionaryDto examItem = dictionaryCache.getCheckItem(ordinaryCheckItemDto.getHsExamItemCode());
                if (examItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemCode(examItem.getDictionaryCode());
                examitemresultlistdto.setExamItemName(examItem.getDictionaryName());
                examitemresultlistdto.setHsExamItemCode(ordinaryCheckItemDto.getHsExamItemCode());
                examitemresultlistdto.setHsExamItemName(ordinaryCheckItemDto.getHsExamItemName());
                examitemresultlistdto.setExamResultType("01");
//                if ("身高".equals(ordinaryCheckItemDto.getHsExamItemName())) {
//                    height = Double.valueOf(ordinaryCheckItemDto.getExamResult());
//                } else if ("体重".equals(ordinaryCheckItemDto.getHsExamItemName())) {
//                    weight = Double.valueOf(ordinaryCheckItemDto.getExamResult());
//                }
                String hsExamItemName = ordinaryCheckItemDto.getHsExamItemName();
                if ("体重指数".equals(hsExamItemName)) {
                    if ((weightIsCorrect && heightIsCorrect)) {
                        String s = BigDecimal.valueOf(weight / Math.pow(height / 100, 2)).setScale(1, RoundingMode.HALF_UP).toString();
                        examitemresultlistdto.setExamResult(s);
                        if ("正常".equals(ordinaryCheckItemDto.getExamResult())) {
                            examitemresultlistdto.setAbnormal("0");
                        } else {
                            examitemresultlistdto.setAbnormal("1");
                        }
                    } else {
                        continue;
                    }
                } else if ("收缩压".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemCode("2010501");
                    examitemresultlistdto.setExamItemName("收缩压");
                    examitemresultlistdto.setExamResult(examResult);
                    Double aDouble = Double.valueOf(examResult);
                    if (aDouble < 90 || aDouble > 140) {
                        examitemresultlistdto.setAbnormal("1");
                    } else {
                        examitemresultlistdto.setAbnormal("0");
                    }
                    examitemresultlistdto.setExamResultType("01");
                    examitemresultlistdto.setExamItemUnitCode("mmHg");
                } else if ("舒张压".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemCode("2010502");
                    examitemresultlistdto.setExamItemName("舒张压");
                    examitemresultlistdto.setExamResult(examResult);
                    Double aDouble = Double.valueOf(examResult);
                    if (aDouble < 60 || aDouble > 90) {
                        examitemresultlistdto.setAbnormal("1");
                    } else {
                        examitemresultlistdto.setAbnormal("0");
                    }
                    examitemresultlistdto.setExamResultType("01");
                    examitemresultlistdto.setExamItemUnitCode("mmHg");
                } else if ("身高".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isNotEmpty(examResult)) {
                        examitemresultlistdto.setExamResult(examResult);
                    } else {
                        continue;
                    }
                    examitemresultlistdto.setExamItemUnitCode("cm");
                    examitemresultlistdto.setAbnormal("0");
                } else if ("体重".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isNotEmpty(examResult)) {
                        examitemresultlistdto.setExamResult(examResult);
                    } else {
                        continue;
                    }
                    examitemresultlistdto.setExamItemUnitCode("Kg");
                    examitemresultlistdto.setAbnormal("0");
                } else if ("脉搏".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamResult(examResult);
                    examitemresultlistdto.setExamItemUnitCode("次/分");
                    examitemresultlistdto.setAbnormal("0");
                }


                examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultlistdto.setOperatetype("Add");
                ordinaryCheckItemDtoList.add(examitemresultlistdto);
            }
        }
        //收集ordinaryCheckItemDtoList一般检查项目代码
        Set<String> stringSet = ordinaryCheckItemDtoList.stream().map(RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO::getExamItemCode).collect(Collectors.toSet());
        //将ordinaryCheckItemDtoList里面有的项目代码从checkItemSet里删除
        checkItemSet = checkItemSet.stream().filter(examitemresultlistdto -> !stringSet.contains(examitemresultlistdto.getExamItemCode())).collect(Collectors.toCollection(HashSet::new));
        //将ordinaryCheckItemDtoList全部添加到checkItemSet
        checkItemSet.addAll(ordinaryCheckItemDtoList);
        //特殊处理一般检查
        checkItemSet=processFaircheck(checkItemSet);
        //2010000=一般情况   如果检查项目里没有一般检查，那么就删除收费项目里的一般检查
        if (checkItemSet.stream().noneMatch(data -> "2010000".equals(data.getExamItemPcode()))) {
            feeItemSet = feeItemSet.stream().filter(data -> !"2010000".equals(data.getExamItemPcode())).collect(Collectors.toSet());
        }
//        handleElectricityHearing(dataDTO, feeItemSet, checkItemSet);
        //电测听
        handleElectricityHearing2(dataDTO, feeItemSet, checkItemSet, peispatient);
        //肺功能
        pulmonaryFunctionProcessing(dataDTO, feeItemSet, checkItemSet);
        //尿
        urineChemicalExaminationAndTreatment(dataDTO, feeItemSet, checkItemSet);
        List<RDataDTO.DataDTO.EXAMITEMRESULTDTO> examItemResult = new ArrayList<>(feeItemSet);

        examItemResult = examItemResult.stream().sorted(Comparator.comparing(RDataDTO.DataDTO.EXAMITEMRESULTDTO::getExamItemPcode)).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(examItemResult)) {
            String message = "检查项目错误";
            errorMessage.add(message);
//            throw new ErrorRuntimrException(message);
        }
        dataDTO.setExamItemResult(examItemResult);
        Set<String> examItemCode = new HashSet<String>();
        checkItemSet = processLeftAndRightItem(checkItemSet);
        checkItemSet = processSpecialItem(checkItemSet);
        checkItemSet = processDust(dataDTO, checkItemSet, peispatient, harmSet,needFeeIds);
        checkItemSet = removeItem(checkItemSet);
        checkItemSet = processErrorItem(checkItemSet);
        checkItemSet = processLiverFunctionItem(checkItemSet, dataDTO);
        //单位和范围处理  最一开始查询的时候，只有检验科带着lis的单位，这个方法给没有单位的设置上检查项目的单位
        checkItemSet = processUnitAndMinMax(checkItemSet, dataDTO, checkItemReferenceCache.getCheckItemReferences());
        checkItemSet = processReference(checkItemSet, harmSet, specialHazardReferenceCache.getSpecialHazardReferences(), dataDTO.getGenderCode());
        //心电图处理  如果心电图检查小项在describe表中有结果，就会添加一条3060101心电图数据。返回检查项目确实，12导联心电图未找到的，检查describe表心电图数据是否存在。
        addECG(dataDTO,checkItemSet);
        //又一个单位处理  按项目代码写死一些单位 强制修改单位，无论是否为空
        checkItemSet = unitProcess(checkItemSet);
        checkItemSet = processNotChecked(checkItemSet);
        checkItemSet = addReticulocytesItem(patientcode, checkItemSet, dataDTO);
        checkItemSet = removeUrineMicroscopy(checkItemSet);
        checkItemSet = processBlood(checkItemSet);
        List<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examItemResultList = new ArrayList<>(checkItemSet);
        examItemResultList = examItemResultList.stream().filter(examitemresultlistdto -> examItemCode.add(examitemresultlistdto.getExamItemCode())).collect(Collectors.toList());
        examItemResultList = examItemResultList.stream().sorted((o1, o2) -> {
            int i = o1.getExamItemPcode().compareTo(o2.getExamItemPcode());
            if (i == 0) {
                return o1.getExamItemCode().compareTo(o2.getExamItemCode());
            } else {
                return i;
            }
        }).collect(Collectors.toList());
//        for (RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto : examItemResult) {
//            if (examitemresultdto.getExamItemPname().contains("其他")) {
//                Stream<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examitemresultlistdtoStream = examItemResultList.stream().filter(data -> data.getExamItemPcode().equals(examitemresultdto.getExamItemPcode()));
//                if (examitemresultlistdtoStream.allMatch(data -> "T".equals(data.getAbnormal()))) {
//                    examItemResult.remove(examItemResult);
//                    examItemResultList.removeAll(examitemresultlistdtoStream.collect(Collectors.toList()));
//                }
//            }
//        }
        if (ObjectUtils.isEmpty(examItemResultList)) {
            String message = "检查项目明细错误";
            errorMessage.add(message);
//            throw new ErrorRuntimrException(message);
        }
        specialTreatmentService.treatDefaultRange(examItemResultList);
        dataDTO.setExamItemResultList(examItemResultList);
        LinkedHashSet<String> strings = new LinkedHashSet<>(Arrays.asList(errorMessage.toString().split(",")));
        for (String string : strings) {
            errorMessage.add(string);
        }

    }

    private void setExamItemResult(RDataDTO.DataDTO dataDTO, Peispatient peispatient, Peispatient reviewPeipatient, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner examResultJoiner) {

        String patientcode = peispatient.getPatientcode();
        String reviewPatientCode = reviewPeipatient.getPatientcode();
        StringJoiner errorMessage = new StringJoiner(",");
        StringJoiner joiner = new StringJoiner(",");
        List<FeeItemDTO> feeItemDTOS = itemQueryRepository.queryFeeItem(patientcode);
        specialTreatmentService.treatItemId(feeItemDTOS);
        List<FeeItemDTO> reviewFeeItemDTOS = itemQueryRepository.queryFeeItem(reviewPatientCode);
        List<NeedFeeItemDTO> needFeeItemDTOS = itemQueryRepository.queryNeedFeeItem(peispatient.getMedicaltype(), peispatient.getJhys());
//        List<String> list = Arrays.asList("网织红细胞(职业)", "血清葡萄糖-6-磷酸脱氢酶缺乏症筛查试验");
//        List<MissingFeeItemDTO> missingItems = itemQueryRepository.queryMissingFeeItemsDTOS(patientcode, list);
        Set<String> comboIds = needFeeItemDTOS.stream().map(NeedFeeItemDTO::getComboId).collect(Collectors.toSet());
        Set<String> needFeeIds = needFeeItemDTOS.stream().map(NeedFeeItemDTO::getItemId).collect(Collectors.toSet());
        Set<String> finalNeedFeeIds = itemQueryRepository.queryNeedFeeItemMust(comboIds);
        Set<String> needContainsFeeItemIds = feeItemDTOS.stream().filter(feeItemDTO -> finalNeedFeeIds.contains(feeItemDTO.getItemId())).map(FeeItemDTO::getItemId).collect(Collectors.toSet());
//        needFeeIds.removeAll(finalNeedFeeIds);
        finalNeedFeeIds.removeIf(needContainsFeeItemIds::contains);
        feeItemDTOS.removeAll(reviewFeeItemDTOS);
        Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet = new LinkedHashSet<>();
        Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet = new LinkedHashSet<>();
        HashSet<String> harmSet = new HashSet<>();
        doneItem(dataDTO, harmPackageMatchDtos, patientcode, errorMessage, joiner, feeItemDTOS, feeItemSet, checkItemSet, harmSet);
        doneItem(dataDTO, harmPackageMatchDtos, reviewPatientCode, errorMessage, joiner, reviewFeeItemDTOS, feeItemSet, checkItemSet, harmSet);
        mustItem(dataDTO, peispatient, harmPackageMatchDtos, errorMessage, joiner, needFeeItemDTOS, finalNeedFeeIds, feeItemSet, checkItemSet, harmSet);
//        selectedItem(dataDTO, peispatient, harmPackageMatchDtos, errorMessage, joiner, needFeeItemDTOS, needFeeIds, feeItemSet, checkItemSet, harmSet);
//        职业问诊
        occupationalConsultation(dataDTO, patientcode, joiner, feeItemSet, checkItemSet);
        List<OrdinaryCheckItemDto> ordinaryCheckItemDtos = itemQueryRepository.queryOrdinaryCheckItem(patientcode);
        HashSet<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> ordinaryCheckItemDtoList = new HashSet<>();
        if (ObjectUtils.isNotEmpty(ordinaryCheckItemDtos)) {
            Set<String> strings = new HashSet<>(Arrays.asList(joiner.toString().split(",")));
            String collect = strings.stream().filter(ObjectUtils::isNotEmpty).sorted(String::compareTo).collect(Collectors.joining(","));
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            examitemresultdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultdto.setExamNum(dataDTO.getExamNum());
            examitemresultdto.setItamCode(dataDTO.getContactFactorCode());
            examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
            examitemresultdto.setExamItemPcode("2010000");
            examitemresultdto.setExamItemPname("一般情况");
            examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examitemresultdto.setOperatetype("Add");
            feeItemSet.add(examitemresultdto);
            Double height = 0D;
            Double weight = 0D;
            boolean heightIsCorrect = true;
            boolean weightIsCorrect = true;
            try {
                height = ordinaryCheckItemDtos.stream().filter(data -> "身高".equals(data.getHsExamItemName())).findFirst().map(data -> Double.valueOf(data.getExamResult())).orElse(1D);
            } catch (Exception e) {
                log.info("身高错误", e);
                heightIsCorrect = false;
            }
            try {
                weight = ordinaryCheckItemDtos.stream().filter(data -> "体重".equals(data.getHsExamItemName())).findFirst().map(data -> Double.valueOf(data.getExamResult())).orElse(1D);
            } catch (Exception e) {
                log.info("体重错误", e);
                weightIsCorrect = false;
            }
            ordinaryCheckItemDtoList = new HashSet<>();
            for (OrdinaryCheckItemDto ordinaryCheckItemDto : ordinaryCheckItemDtos) {
                if ("血压结论".equals(ordinaryCheckItemDto.getHsExamItemName()) || "营养状况".equals(ordinaryCheckItemDto.getHsExamItemName())) {
                    continue;
                }
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setExamItemUnitCode("%");
//                examitemresultlistdto.setReferenceRangeMin("无");
//                examitemresultlistdto.setReferenceRangeMax("无");
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                examitemresultlistdto.setExamItemPcode(examitemresultdto.getExamItemPcode());
                examitemresultlistdto.setExamItemPname(examitemresultdto.getExamItemPname());
                BaseDictionaryDto examItem = dictionaryCache.getCheckItem(ordinaryCheckItemDto.getHsExamItemCode());
                if (examItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemCode(examItem.getDictionaryCode());
                examitemresultlistdto.setExamItemName(examItem.getDictionaryName());
                examitemresultlistdto.setHsExamItemCode(ordinaryCheckItemDto.getHsExamItemCode());
                examitemresultlistdto.setHsExamItemName(ordinaryCheckItemDto.getHsExamItemName());
                examitemresultlistdto.setExamResultType("01");
//                if ("身高".equals(ordinaryCheckItemDto.getHsExamItemName())) {
//                    height = Double.valueOf(ordinaryCheckItemDto.getExamResult());
//                } else if ("体重".equals(ordinaryCheckItemDto.getHsExamItemName())) {
//                    weight = Double.valueOf(ordinaryCheckItemDto.getExamResult());
//                }
                String hsExamItemName = ordinaryCheckItemDto.getHsExamItemName();
                if ("体重指数".equals(hsExamItemName)) {
                    if ((weightIsCorrect && heightIsCorrect)) {
                        String s = BigDecimal.valueOf(weight / Math.pow(height / 100, 2)).setScale(1, RoundingMode.HALF_UP).toString();
                        examitemresultlistdto.setExamResult(s);
                        if ("正常".equals(ordinaryCheckItemDto.getExamResult())) {
                            examitemresultlistdto.setAbnormal("0");
                        } else {
                            examitemresultlistdto.setAbnormal("1");
                        }
                    } else {
                        continue;
                    }
                } else if ("收缩压".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemCode("2010501");
                    examitemresultlistdto.setExamItemName("收缩压");
                    examitemresultlistdto.setExamResult(examResult);
                    Double aDouble = Double.valueOf(examResult);
                    if (aDouble < 90 || aDouble > 140) {
                        examitemresultlistdto.setAbnormal("1");
                    } else {
                        examitemresultlistdto.setAbnormal("0");
                    }
                    examitemresultlistdto.setExamResultType("01");
                    examitemresultlistdto.setExamItemUnitCode("mmHg");
                } else if ("舒张压".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemCode("2010502");
                    examitemresultlistdto.setExamItemName("舒张压");
                    examitemresultlistdto.setExamResult(examResult);
                    Double aDouble = Double.valueOf(examResult);
                    if (aDouble < 60 || aDouble > 90) {
                        examitemresultlistdto.setAbnormal("1");
                    } else {
                        examitemresultlistdto.setAbnormal("0");
                    }
                    examitemresultlistdto.setExamResultType("01");
                    examitemresultlistdto.setExamItemUnitCode("mmHg");
                } else if ("身高".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isNotEmpty(examResult)) {
                        examitemresultlistdto.setExamResult(examResult);
                    } else {
                        continue;
                    }
                    examitemresultlistdto.setExamItemUnitCode("cm");
                    examitemresultlistdto.setAbnormal("0");
                } else if ("体重".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isNotEmpty(examResult)) {
                        examitemresultlistdto.setExamResult(examResult);
                    } else {
                        continue;
                    }
                    examitemresultlistdto.setExamItemUnitCode("Kg");
                    examitemresultlistdto.setAbnormal("0");
                } else if ("脉搏".equals(hsExamItemName)) {
                    String examResult = ordinaryCheckItemDto.getExamResult();
                    if (ObjectUtils.isEmpty(examResult)) {
                        continue;
                    }
                    examitemresultlistdto.setExamResult(examResult);
                    examitemresultlistdto.setExamItemUnitCode("次/分");
                    examitemresultlistdto.setAbnormal("0");
                }


                examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultlistdto.setOperatetype("Add");
                ordinaryCheckItemDtoList.add(examitemresultlistdto);
            }
        }
        Set<String> stringSet = ordinaryCheckItemDtoList.stream().map(RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO::getExamItemCode).collect(Collectors.toSet());
        checkItemSet = checkItemSet.stream().filter(examitemresultlistdto -> !stringSet.contains(examitemresultlistdto.getExamItemCode())).collect(Collectors.toCollection(HashSet::new));
        checkItemSet.addAll(ordinaryCheckItemDtoList);
        if (checkItemSet.stream().noneMatch(data -> "2010000".equals(data.getExamItemPcode()))) {
            feeItemSet = feeItemSet.stream().filter(data -> !"2010000".equals(data.getExamItemPcode())).collect(Collectors.toSet());
        }
//        handleElectricityHearing(dataDTO, feeItemSet, checkItemSet);
        handleElectricityHearing2(dataDTO, feeItemSet, checkItemSet, peispatient);
        pulmonaryFunctionProcessing(dataDTO, feeItemSet, checkItemSet, peispatient.getPatientcode());
        urineChemicalExaminationAndTreatment(dataDTO, feeItemSet, checkItemSet);
        List<RDataDTO.DataDTO.EXAMITEMRESULTDTO> examItemResult = new ArrayList<>(feeItemSet);

        examItemResult = examItemResult.stream().sorted(Comparator.comparing(RDataDTO.DataDTO.EXAMITEMRESULTDTO::getExamItemPcode)).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(examItemResult)) {
            String message = "检查项目错误";
            errorMessage.add(message);
//            throw new ErrorRuntimrException(message);
        }
        dataDTO.setExamItemResult(examItemResult);
        Set<String> examItemCode = new HashSet<String>();
        checkItemSet = processLeftAndRightItem(checkItemSet);
        checkItemSet = processSpecialItem(checkItemSet);
        checkItemSet = processDust(dataDTO, checkItemSet, peispatient, harmSet,needFeeIds);
        checkItemSet = removeItem(checkItemSet);
        checkItemSet = processErrorItem(checkItemSet);
        checkItemSet = processLiverFunctionItem(checkItemSet, dataDTO);
        checkItemSet = processUnitAndMinMax(checkItemSet, dataDTO, checkItemReferenceCache.getCheckItemReferences());
        checkItemSet = processReference(checkItemSet, harmSet, specialHazardReferenceCache.getSpecialHazardReferences(), dataDTO.getGenderCode());
        addECG(dataDTO,checkItemSet);
        checkItemSet = unitProcess(checkItemSet);
        checkItemSet = processNotChecked(checkItemSet);
        checkItemSet = addReticulocytesItem(patientcode, checkItemSet, dataDTO);
        checkItemSet = removeUrineMicroscopy(checkItemSet);
        checkItemSet = processBlood(checkItemSet);

        processElectricityAbnormal(dataDTO,checkItemSet,peispatient);

        List<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examItemResultList = new ArrayList<>(checkItemSet);
        examItemResultList = examItemResultList.stream().filter(examitemresultlistdto -> examItemCode.add(examitemresultlistdto.getExamItemCode())).collect(Collectors.toList());
        examItemResultList = examItemResultList.stream().sorted((o1, o2) -> {
            int i = o1.getExamItemPcode().compareTo(o2.getExamItemPcode());
            if (i == 0) {
                return o1.getExamItemCode().compareTo(o2.getExamItemCode());
            } else {
                return i;
            }
        }).collect(Collectors.toList());
//        for (RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto : examItemResult) {
//            if (examitemresultdto.getExamItemPname().contains("其他")) {
//                Stream<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examitemresultlistdtoStream = examItemResultList.stream().filter(data -> data.getExamItemPcode().equals(examitemresultdto.getExamItemPcode()));
//                if (examitemresultlistdtoStream.allMatch(data -> "T".equals(data.getAbnormal()))) {
//                    examItemResult.remove(examItemResult);
//                    examItemResultList.removeAll(examitemresultlistdtoStream.collect(Collectors.toList()));
//                }
//            }
//        }
        if (ObjectUtils.isEmpty(examItemResultList)) {
            String message = "检查项目明细错误";
            errorMessage.add(message);
//            throw new ErrorRuntimrException(message);
        }
        specialTreatmentService.treatDefaultRange(examItemResultList);
        dataDTO.setExamItemResultList(examItemResultList);
        LinkedHashSet<String> strings = new LinkedHashSet<>(Arrays.asList(errorMessage.toString().split(",")));
        for (String string : strings) {
            errorMessage.add(string);
        }


    }


    private void occupationalConsultation(RDataDTO.DataDTO dataDTO, String patientcode, StringJoiner joiner, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        PeispatientConsultation peispatientConsultation = peispatientConsultationRepository.findByPatientcode(patientcode);
        if (peispatientConsultation != null) {
//        有问诊
            Set<String> strings = new HashSet<>(Arrays.asList(joiner.toString().split(",")));
            String collect = strings.stream().filter(ObjectUtils::isNotEmpty).sorted(String::compareTo).collect(Collectors.joining(","));
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            examitemresultdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultdto.setExamNum(dataDTO.getExamNum());
            examitemresultdto.setItamCode(dataDTO.getContactFactorCode());
            examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
            examitemresultdto.setExamItemPcode("1000000");
            examitemresultdto.setExamItemPname("症状询问");
            examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examitemresultdto.setOperatetype("Add");
            feeItemSet.add(examitemresultdto);
            String symptom = peispatientConsultation.getSymptom();
            String[] symptoms = symptom.split(",");
            for (String s : symptoms) {
                String[] split = s.split(":");
                String name = split[0];
                String result = split[1];
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setExamItemUnitCode("%");
//                examitemresultlistdto.setReferenceRangeMin("无");
//                examitemresultlistdto.setReferenceRangeMax("无");
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                examitemresultlistdto.setExamItemPcode(examitemresultdto.getExamItemPcode());
                examitemresultlistdto.setExamItemPname(examitemresultdto.getExamItemPname());
                SymptomDictionaryDto symptomDictionaryDto = dictionaryCache.getSymptom(name);
                if (ObjectUtils.isEmpty(symptomDictionaryDto)) {
                    continue;
                }
                examitemresultlistdto.setExamItemCode(symptomDictionaryDto.getDictionaryCode());
                examitemresultlistdto.setExamItemName(symptomDictionaryDto.getDictionaryName());
                examitemresultlistdto.setHsExamItemCode(symptomDictionaryDto.getSymptomCode());
                examitemresultlistdto.setHsExamItemName(symptomDictionaryDto.getSymptom());
                examitemresultlistdto.setExamResultType("02");
                if ("+".equals(result)) {
                    examitemresultlistdto.setExamResult("异常");
                    examitemresultlistdto.setAbnormal("1");
                } else {
                    examitemresultlistdto.setExamResult("无");
                    examitemresultlistdto.setAbnormal("0");
                }
                examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultlistdto.setOperatetype("Add");
                examitemresultlistdto.setExamItemUnitCode("%");
                checkItemSet.add(examitemresultlistdto);
            }
        }
    }

    private void doneItem(RDataDTO.DataDTO dataDTO, List<HarmPackageMatchDto> harmPackageMatchDtos, String patientcode, StringJoiner errorMessage
            , StringJoiner joiner, List<FeeItemDTO> feeItemDTOS, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet
            , HashSet<String> harmSet) {
        log.error("打印一下doneItem的feeItemDTOS数据：{}", feeItemDTOS);
        for (FeeItemDTO feeItemDTO : feeItemDTOS) {
            Set<String> strings = new HashSet<>(Arrays.asList(feeItemDTO.getHarmIds().split(",")));
            BaseDictionaryDto examItem = dictionaryCache.getExamItem(feeItemDTO.getItemId());
            log.error("处理ItemId：{},examItem:{}", feeItemDTO.getItemId(), examItem);
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            examitemresultdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultdto.setExamNum(dataDTO.getExamNum());
            if (examItem == null) {
                continue;
            } else {
                if ("症状询问".equals(examItem.getDictionaryName())) {
                    continue;
                } else if ("一般情况".equals(examItem.getDictionaryName())) {
                    continue;
                }
            }
            String collect = null;
            log.error("harmPackageMatchDtos的数据:{}", harmPackageMatchDtos);
            if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                HashSet<String> set = new HashSet<>();
                for (String string : strings) {
                    List<String> collect1 = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(string)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                    if (ObjectUtils.isNotEmpty(collect1)) {
                        set.addAll(collect1);
                    } else {
                        set.add(string);
                    }
                }
                collect = set.stream().map(data -> {
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                    if (hazards == null) {
                        String message = "因素ID:" + data + ":未能正确匹配危害因素,请检查是否在【省市级平台数据对照】功能匹配危害因素、危害因素是否已被删除。";
//                        errorMessage.add(message);
                        throw new ErrorRuntimeException(message);
                    }
                    return hazards;
                }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
            } else {
                collect = strings.stream().map(data -> {
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                    if (hazards == null) {
                        String message = "因素ID:" + data + ":未能正确匹配危害因素,请检查是否在【省市级平台数据对照】功能匹配危害因素、危害因素是否已被删除。";
//                        errorMessage.add(message);
                        throw new ErrorRuntimeException(message);
                    }
                    return hazards;
                }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
            }


            if (ObjectUtils.isEmpty(collect)) {
                String message = "危害因素错误";
                errorMessage.add(message);
//                throw new ErrorRuntimrException (message);
            } else {
                harmSet.addAll(Arrays.asList(collect.split(",")));
            }
            joiner.add(collect);
            examitemresultdto.setItamCode(collect);
            examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
            examitemresultdto.setExamItemPcode(examItem.getDictionaryCode());
            examitemresultdto.setExamItemPname(examItem.getDictionaryName());
            examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examitemresultdto.setOperatetype("Add");
            log.error("examitemresultdto的数据:{}", examitemresultdto);
            feeItemSet.add(examitemresultdto);
            String itemId = feeItemDTO.getItemId();
            List<CheckItemDTO> checkItemDTOS = itemQueryRepository.queryCheckItemDTO(feeItemDTO.getPatientcode(), itemId);
            log.error("checkItemDTOS的数据:{}", checkItemDTOS);
            for (CheckItemDTO checkItemDTO : checkItemDTOS) {
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setExamItemUnitCode(ObjectUtils.isNotEmpty(checkItemDTO.getExamItemUnitCode()) ? checkItemDTO.getExamItemUnitCode() : "%");
//                examitemresultlistdto.setReferenceRangeMin(ObjectUtils.isNotEmpty(checkItemDTO.getReferenceRangeMin()) ? checkItemDTO.getReferenceRangeMin() : "无");
//                examitemresultlistdto.setReferenceRangeMax(ObjectUtils.isNotEmpty(checkItemDTO.getReferenceRangeMax()) ? checkItemDTO.getReferenceRangeMax() : "无");
                examitemresultlistdto.setReferenceRangeMin(ObjectUtils.isNotEmpty(checkItemDTO.getReferenceRangeMin()) ? checkItemDTO.getReferenceRangeMin() : "");
                examitemresultlistdto.setReferenceRangeMax(ObjectUtils.isNotEmpty(checkItemDTO.getReferenceRangeMax()) ? checkItemDTO.getReferenceRangeMax() : "");
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                if (examItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemPcode(examItem.getDictionaryCode());
                examitemresultlistdto.setExamItemPname(examItem.getDictionaryName());
                BaseDictionaryDto checkItem = dictionaryCache.getCheckItem(checkItemDTO.getHsExamItemCode());
                if (checkItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemCode(checkItem.getDictionaryCode());
                examitemresultlistdto.setExamItemName(checkItem.getDictionaryName());
                examitemresultlistdto.setHsExamItemCode(checkItemDTO.getHsExamItemCode());
                examitemresultlistdto.setHsExamItemName(checkItemDTO.getHsExamItemName());
                if (checkItemDTO.getExamResult()!=null&&
                        (
                                (checkItemDTO.getExamResult().equals("-")) || checkItemDTO.getExamResult().equals("+") || checkItemDTO.getExamResult().equals("阴性") || checkItemDTO.getExamResult().equals("阳性")
                        )
                ) {
                    examitemresultlistdto.setExamResultType("03");
                    if (checkItemDTO.getExamResult().contains("-")) {
                        examitemresultlistdto.setExamResult("阴性");
                    } else if (checkItemDTO.getExamResult().contains("+")) {
                        examitemresultlistdto.setExamResult("阳性");
                    } else {
                        examitemresultlistdto.setExamResult(checkItemDTO.getExamResult());
                    }
                } else {
//                    examitemresultlistdto.setExamResultType(checkItemDTO.getExamResultType());
                    examitemresultlistdto.setExamResult(checkItemDTO.getExamResult());
                    if (NumberUtil.isNumber(checkItemDTO.getExamResult())) {
                        examitemresultlistdto.setExamResultType("01");
                    } else {
                        examitemresultlistdto.setExamResultType("02");
                    }
                }
                examitemresultlistdto.setAbnormal("T".equals(checkItemDTO.getAbnormal()) ? "1" : "0");
                examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultlistdto.setOperatetype("Add");
                checkItemSet.add(examitemresultlistdto);
            }
        }
    }

    //数值数据转化失败->系统中有结果但上传是未检
    private void mustItem(RDataDTO.DataDTO dataDTO, Peispatient peispatient, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner errorMessage, StringJoiner joiner, List<NeedFeeItemDTO> needFeeItemDTOS
            , Set<String> finalNeedFeeIds, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, HashSet<String> harmSet) {
        for (String needFeeId : finalNeedFeeIds) {
            List<String> harmIds = processNotDoItem(needFeeId, needFeeItemDTOS, peispatient);
            List<String> examItemIds = processNotDoExamItem(needFeeId, needFeeItemDTOS);//有血小板体积
            if (ObjectUtils.isNotEmpty(examItemIds)) {
                Set<String> strings = new HashSet<>(harmIds);
                BaseDictionaryDto examItem = dictionaryCache.getExamItem(needFeeId);
                RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
                examitemresultdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultdto.setExamNum(dataDTO.getExamNum());
                if (examItem == null) {
                    continue;
                } else {
                    if ("症状询问".equals(examItem.getDictionaryName())) {
                        continue;
                    } else if ("一般情况".equals(examItem.getDictionaryName())) {
                        continue;
                    }
                }
                String collect = null;
                if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                    HashSet<String> set = new HashSet<>();
                    for (String string : strings) {
                        List<String> collect1 = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(string)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                        if (ObjectUtils.isNotEmpty(collect1)) {
                            set.addAll(collect1);
                        } else {
                            set.add(string);
                        }
                    }
                    collect = set.stream().map(data -> {
                        BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                        if (hazards == null) {
                            String message = "因素ID:" + data + ":未能正确匹配危害因素";
                            //                        errorMessage.add(message);
                            throw new ErrorRuntimeException(message);
                        }
                        return hazards;
                    }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
                } else {
                    collect = strings.stream().map(data -> {
                        BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                        if (hazards == null) {
                            String message = "因素ID:" + data + ":未能正确匹配危害因素";
                            //                        errorMessage.add(message);
                            throw new ErrorRuntimeException(message);
                        }
                        return hazards;
                    }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
                }


                if (ObjectUtils.isEmpty(collect)) {
                    String message = "危害因素错误";
                    errorMessage.add(message);
                    //                throw new ErrorRuntimrException (message);
                } else {
                    harmSet.addAll(Arrays.asList(collect.split(",")));
                }
                joiner.add(collect);
                examitemresultdto.setItamCode(collect);
                examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
                examitemresultdto.setExamItemPcode(examItem.getDictionaryCode());
                examitemresultdto.setExamItemPname(examItem.getDictionaryName());
                examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultdto.setOperatetype("Add");
                feeItemSet.add(examitemresultdto);
//                String itemId = needFeeId;
                //设置了未检，有血小板体积
                List<CheckItemDTO> checkItemDTOS = itemQueryRepository.queryNotCheckItemDTO(examItemIds);
                for (CheckItemDTO checkItemDTO : checkItemDTOS) {
                    RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                    examitemresultlistdto.setExamItemUnitCode("%");
//                    examitemresultlistdto.setReferenceRangeMin("无");
//                    examitemresultlistdto.setReferenceRangeMax("无");
                    examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                    examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                    examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                    examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                    if (examItem == null) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemPcode(examItem.getDictionaryCode());
                    examitemresultlistdto.setExamItemPname(examItem.getDictionaryName());
                    BaseDictionaryDto checkItem = dictionaryCache.getCheckItem(checkItemDTO.getHsExamItemCode());
                    if (checkItem == null) {
                        continue;
                    }
                    examitemresultlistdto.setExamItemCode(checkItem.getDictionaryCode());
                    examitemresultlistdto.setExamItemName(checkItem.getDictionaryName());
                    examitemresultlistdto.setHsExamItemCode(checkItemDTO.getHsExamItemCode());
                    examitemresultlistdto.setHsExamItemName(checkItemDTO.getHsExamItemName());
                    if ((checkItemDTO.getExamResult().contains("-") && !NumberUtil.isNumber(checkItemDTO.getExamResult())) || checkItemDTO.getExamResult().contains("+") || checkItemDTO.getExamResult().contains("阴性") || checkItemDTO.getExamResult().contains("阳性")) {
                        examitemresultlistdto.setExamResultType("03");
                        if (checkItemDTO.getExamResult().contains("-")) {
                            examitemresultlistdto.setExamResult("阴性");
                        } else {
                            examitemresultlistdto.setExamResult("阳性");
                        }
                    } else {
                        //                    examitemresultlistdto.setExamResultType(checkItemDTO.getExamResultType());
                        examitemresultlistdto.setExamResult(checkItemDTO.getExamResult());
                        if (NumberUtil.isNumber(checkItemDTO.getExamResult())) {
                            examitemresultlistdto.setExamResultType("01");
                        } else {
                            examitemresultlistdto.setExamResultType("02");
                        }
                    }
                    examitemresultlistdto.setAbnormal("T".equals(checkItemDTO.getAbnormal()) ? "1" : "0");
                    examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                    examitemresultlistdto.setOperatetype("Add");
                    checkItemSet.add(examitemresultlistdto);
                }
            }
        }
    }

    private void selectedItem(RDataDTO.DataDTO dataDTO, Peispatient peispatient, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner errorMessage, StringJoiner joiner, List<NeedFeeItemDTO> needFeeItemDTOS, Set<String> needFeeIds, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, HashSet<String> harmSet) {
        for (String needFeeId : needFeeIds) {
            List<String> harmIds = processNotDoItem(needFeeId, needFeeItemDTOS, peispatient);
            List<String> examItemIds = processNotDoExamItem(needFeeId, needFeeItemDTOS);
            Set<String> strings = new HashSet<>(harmIds);
            BaseDictionaryDto examItem = dictionaryCache.getExamItem(needFeeId);
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            examitemresultdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultdto.setExamNum(dataDTO.getExamNum());
            if (examItem == null) {
                continue;
            } else {
                if ("症状询问".equals(examItem.getDictionaryName())) {
                    continue;
                } else if ("一般情况".equals(examItem.getDictionaryName())) {
                    continue;
                }
            }
            String collect = null;
            if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                HashSet<String> set = new HashSet<>();
                for (String string : strings) {
                    List<String> collect1 = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(string)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                    if (ObjectUtils.isNotEmpty(collect1)) {
                        set.addAll(collect1);
                    } else {
                        set.add(string);
                    }
                }
                collect = set.stream().map(data -> {
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                    if (hazards == null) {
                        String message = "因素ID:" + data + ":未能正确匹配危害因素";
//                        errorMessage.add(message);
                        throw new ErrorRuntimeException(message);
                    }
                    return hazards;
                }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
            } else {
                collect = strings.stream().map(data -> {
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(data);
                    if (hazards == null) {
                        String message = "因素ID:" + data + ":未能正确匹配危害因素";
//                        errorMessage.add(message);
                        throw new ErrorRuntimeException(message);
                    }
                    return hazards;
                }).map(BaseDictionary::getDictionaryCode).sorted(String::compareTo).collect(Collectors.joining(","));
            }


            if (ObjectUtils.isEmpty(collect)) {
                String message = "危害因素错误";
                errorMessage.add(message);
//                throw new ErrorRuntimrException (message);
            } else {
                harmSet.addAll(Arrays.asList(collect.split(",")));
            }
            joiner.add(collect);
            examitemresultdto.setItamCode(collect);
            examitemresultdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examitemresultdto.getItamCode()));
            examitemresultdto.setExamItemPcode(examItem.getDictionaryCode());
            examitemresultdto.setExamItemPname(examItem.getDictionaryName());
            examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examitemresultdto.setOperatetype("Add");
            feeItemSet.add(examitemresultdto);
            List<CheckItemDTO> checkItemDTOS = itemQueryRepository.queryNotCheckItemDTO(examItemIds);
            for (CheckItemDTO checkItemDTO : checkItemDTOS) {
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setExamItemUnitCode("%");
//                examitemresultlistdto.setReferenceRangeMin("无");
//                examitemresultlistdto.setReferenceRangeMax("无");
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                if (examItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemPcode(examItem.getDictionaryCode());
                examitemresultlistdto.setExamItemPname(examItem.getDictionaryName());
                BaseDictionaryDto checkItem = dictionaryCache.getCheckItem(checkItemDTO.getHsExamItemCode());
                if (checkItem == null) {
                    continue;
                }
                examitemresultlistdto.setExamItemCode(checkItem.getDictionaryCode());
                examitemresultlistdto.setExamItemName(checkItem.getDictionaryName());
                examitemresultlistdto.setHsExamItemCode(checkItemDTO.getHsExamItemCode());
                examitemresultlistdto.setHsExamItemName(checkItemDTO.getHsExamItemName());
                if ((checkItemDTO.getExamResult().contains("-") && !NumberUtil.isNumber(checkItemDTO.getExamResult())) || checkItemDTO.getExamResult().contains("+") || checkItemDTO.getExamResult().contains("阴性") || checkItemDTO.getExamResult().contains("阳性")) {
                    examitemresultlistdto.setExamResultType("03");
                    if (checkItemDTO.getExamResult().contains("-")) {
                        examitemresultlistdto.setExamResult("阴性");
                    } else if (checkItemDTO.getExamResult().contains("+")) {
                        examitemresultlistdto.setExamResult("阳性");
                    } else {
                        examitemresultlistdto.setExamResult(checkItemDTO.getExamResult());
                    }
                } else {
//                    examitemresultlistdto.setExamResultType(checkItemDTO.getExamResultType());
                    examitemresultlistdto.setExamResult(checkItemDTO.getExamResult());
                    if (NumberUtil.isNumber(checkItemDTO.getExamResult())) {
                        examitemresultlistdto.setExamResultType("01");
                    } else {
                        examitemresultlistdto.setExamResultType("02");
                    }
                }
                examitemresultlistdto.setAbnormal("T".equals(checkItemDTO.getAbnormal()) ? "1" : "0");
                examitemresultlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultlistdto.setOperatetype("Add");
                checkItemSet.add(examitemresultlistdto);
            }
        }
    }


    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processNotChecked(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        return checkItemSet.stream().peek(examitemresultlistdto -> {
            switch (examitemresultlistdto.getExamItemCode()) {
                default:
                    break;
                case "4010100":
                case "4010200":
                case "4010300":
                case "4010400":
                case "4010500":
                case "4010600":
                case "4010700":
                case "4010800":
                case "4010900":
                case "4011100":
                case "4011600":
                case "4012000":
                case "4012100":
                case "4012200":
                case "4012600":
                case "4013700":
                case "4014300":
                case "4014900":
                case "4020100":
//                case "4090300":
                case "4040900":
                    if (!NumberUtil.isNumber(examitemresultlistdto.getExamResult())) {
                        examitemresultlistdto.setExamResult("0");
                    }
                    break;

            }
        }).collect(Collectors.toSet());
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processBlood(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        return checkItemSet;
    }

    /**
     * 添加网织红细胞
     *
     * @param patientcode  体检号
     * @param checkItemSet
     * @param dataDTO
     * @return
     */
    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> addReticulocytesItem(String patientcode, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, RDataDTO.DataDTO dataDTO) {
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemRepository.findAllByIdPatientAndExamfeeitemNameContaining(patientcode, "网织红细胞");
        List<Peispatientexamitem> peispatientexamitemList = peispatientexamitemRepository.findAllByPatientcodeAndExamitemNameRContaining(patientcode, "网织红细胞");
        List<Peispatientexamitem> hbxsms = peispatientexamitemRepository.findAllByPatientcodeAndExamitemNameRContaining(patientcode, "红细胞数目");
        if (ObjectUtil.isNotEmpty(peispatientfeeitems)) if (ObjectUtil.isNotEmpty(peispatientexamitemList)) {
            for (Peispatientexamitem peispatientexamitem : peispatientexamitemList) {
                boolean weifang = peispatientexamitem.getPatientcode().startsWith("06");
                RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultlistdto.setExamNum(dataDTO.getExamNum());
                examitemresultlistdto.setExamItemPname("血液一般检查");
                examitemresultlistdto.setExamItemPcode("4010000");
                examitemresultlistdto.setExamItemName("网织红细胞计数(RC)");
                examitemresultlistdto.setExamItemCode("4013000");
                examitemresultlistdto.setHsExamItemName("网织红细胞(职业)");
                examitemresultlistdto.setHsExamItemCode("Z0106");
                examitemresultlistdto.setExamResultType("01");
                if (NumberUtil.isNumber(peispatientexamitem.getExamitemvaluesreport())) {
                    /** 外送原来传的不是传百分比，改了
                     double v = Double.parseDouble(peispatientexamitem.getExamitemvaluesreport()) * 1000;
                     if (v >= 0.0535 * 1000 || v <= 0.169 * 1000) {
                     examitemresultlistdto.setAbnormal("0");
                     } else {
                     examitemresultlistdto.setAbnormal("1");
                     }
                     */

                    /**
                     * 范围   0-150   来源于云平台-国际体检组合项目查看
                     *
                     */
                    if(hbxsms.size()>0&&hbxsms.get(0).getExamitemvaluesreport()!=null&&NumberUtil.isNumber(hbxsms.get(0).getExamitemvaluesreport())){
                        double v = Double.parseDouble(peispatientexamitem.getExamitemvaluesreport()) * 10 *  Double.parseDouble(hbxsms.get(0).getExamitemvaluesreport());
                        if (v >= 0 || v <= 150) {
                            examitemresultlistdto.setAbnormal("0");
                        } else {
                            examitemresultlistdto.setAbnormal("1");
                        }
                        examitemresultlistdto.setExamResult(String.valueOf(v));
                    }

                }
                examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                examitemresultlistdto.setReferenceRangeMin("0");
                examitemresultlistdto.setReferenceRangeMax("150");
//                examitemresultlistdto.setReferenceRangeMin("0.0535");
//                examitemresultlistdto.setReferenceRangeMax("0.169");
                examitemresultlistdto.setSystemTime(dataDTO.getSystemTime());
                examitemresultlistdto.setOperatetype("Add");
                checkItemSet.add(examitemresultlistdto);
            }

        }
        return checkItemSet;
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> removeUrineMicroscopy(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        return checkItemSet.stream().filter(examitemresultlistdto -> !(examitemresultlistdto.getExamItemCode().startsWith("409"))).collect(Collectors.toSet());
    }

    /**
     * 单位处理 单位匹配
     */
    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> unitProcess(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
//        return checkItemSet.stream().peek(examitemresultlistdto -> {
//            checkASSAY(examitemresultlistdto);
//            checkElectricalAudiometry(examitemresultlistdto);
//        }).collect(Collectors.toSet());
        ArrayList<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examitemresultlistdtos = new ArrayList<>();
        for (RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto : checkItemSet) {
            if (examitemresultlistdto.getExamItemCode().startsWith("304")) {
                examitemresultlistdto.setExamItemUnitCode("dBHL");
            } else if (examitemresultlistdto.getExamItemCode().equals("3060101")) {
                examitemresultlistdto.setExamResultType("02");
            } else {
                boolean weifang = examitemresultlistdto.getExamNum().startsWith("06");
//                log.error("是否是潍坊：{}",weifang);
                switch (examitemresultlistdto.getExamItemCode().trim()) {
                    default:
                        if(StrUtil.isEmpty(examitemresultlistdto.getExamItemUnitCode())){
                            examitemresultlistdto.setExamItemUnitCode("%");
                        }
                        break;
                    //                4010300,4010500,4010700,4010900,4011100,4013700,4014300,4014900,4020100,4090100,4090300
                    case "4010300":
                    case "4010500":
                    case "4010700":
                    case "4010900":
                    case "4011100":
                    case "4012400":
                        examitemresultlistdto.setExamItemUnitCode("%");
                        break;
                    case "4013700":
                    case "4014300":
                    case "4014900":
                    case "4020100":
//                    case "4090300":
                    case "4090100":
                        examitemresultlistdto.setExamResultType("01");
//                        if (examitemresultlistdto.getExamResult().equals("未见") || !NumberUtil.isNumber(examitemresultlistdto.getExamResult())) {
//                            examitemresultlistdto.setExamResult("0");
//                        }
                        break;
                    case "4040900":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("U/L");
                        break;

                    case "4011800":
                    case "4012700":
                    case "4012800":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("fL");
                        break;
                    case "4012000":
                    case "4012200":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("g/L");
                        break;
                    case "2010501":
                    case "2010502":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("mmHg");
                        break;
                    case "4012100":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("pg");
                        break;
                    case "3010100":
                    case "3010300":
                    case "3060101":
                        examitemresultlistdto.setExamResultType("02");
                        break;
                    case "4021100":
                        examitemresultlistdto.setExamResultType("03");
                        examitemresultlistdto.setExamItemUnitCode("Cells/μL");
                        break;
                    case "2010300":
                        examitemresultlistdto.setExamItemUnitCode("Kg");
                        break;
                    case "4020200":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("PH");
                        if (!NumberUtil.isNumber(examitemresultlistdto.getExamResult())) {
                            examitemresultlistdto.setExamResult("6.0");
                        }
                        break;
                    case "4020300":
                        examitemresultlistdto.setExamResultType("03");
                        examitemresultlistdto.setExamItemUnitCode("U/L");
                        break;
                    case "2010200":
                        examitemresultlistdto.setExamItemUnitCode("cm");
                        break;
                    case "4020500":
                        examitemresultlistdto.setExamResultType("03");
                        examitemresultlistdto.setExamItemUnitCode("g/L");
                        break;
                    case "4020400":
                    case "4020600":
                    case "4020700":
                    case "4110500":
                        examitemresultlistdto.setExamResultType("03");
                        examitemresultlistdto.setExamItemUnitCode("mmol/L");
                        break;
                    case "4020800":
                    case "4020900":
                        examitemresultlistdto.setExamResultType("03");
                        examitemresultlistdto.setExamItemUnitCode("umol/L");
                        break;
                    case "2010400":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("次/分");
                        break;
                    case "4011600":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^12/L":"×10^12/L");
                        break;
                    case "4012600":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "3020100":
                    case "3020200":
                    case "3020300":
                        examitemresultlistdto.setExamResultType("01");
                        break;
                    case "4070500":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("mmol/L");
                        break;
                    case "4042800":
                        examitemresultlistdto.setExamItemUnitCode("μmol/L");
                        break;
                    case "4043400":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode("U/L");
                        break;
                    case "4021200":
                        examitemresultlistdto.setExamResultType("02");
                        examitemresultlistdto.setExamItemUnitCode("%");
                        break;
                    case "4070800":
                        examitemresultlistdto.setExamItemUnitCode("U/L");
                        break;

                    //2022.9.19 在这里添加新的匹配
                    case "4010800":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "4010600":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "4010400":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "4010200":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "4010100":
                        examitemresultlistdto.setExamResultType("01");
                        examitemresultlistdto.setExamItemUnitCode(weifang?"10^9/L":"×10^9/L");
                        break;
                    case "4040100":
                        examitemresultlistdto.setExamItemUnitCode("g/L");
                        break;
                    case "4040200":
                        examitemresultlistdto.setExamItemUnitCode("g/L");
                        break;
                    case "4040300":
                        examitemresultlistdto.setExamItemUnitCode("g/L");
                        break;
                    case "4320100":
                        examitemresultlistdto.setExamItemUnitCode("mmol/L");
                        break;
                    case "4320200":
                        examitemresultlistdto.setExamItemUnitCode("umol/L");
                        break;
                }
            }

            examitemresultlistdtos.add(examitemresultlistdto);
        }
//        Set<String> collect = examitemresultlistdtos.stream().map(RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO::getExamItemCode).collect(Collectors.toSet());
//        checkItemSet = checkItemSet.stream().filter(examitemresultlistdto -> !collect.contains(examitemresultlistdto.getExamItemCode())).collect(Collectors.toCollection(HashSet::new));
//        checkItemSet.addAll(examitemresultlistdtos);
        return new LinkedHashSet<>(examitemresultlistdtos);

    }

    /**
     * 心电图
     *
     * @param examItemResultList
     */
    private void addECG(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> examItemResultList) {
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto2 = null;
//        for (RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto : examItemResultList) {
//            if (ObjectUtil.isNotEmpty(examitemresultlistdto) && ObjectUtil.isNotEmpty(examitemresultlistdto.getExamItemCode()) && examitemresultlistdto.getExamItemCode().equals("3060100")) {
//                examitemresultlistdto2 = examitemresultlistdto;
//                break;
//            }
//        }
        log.error("心电图examItemResultList:{}",examItemResultList);
        Iterator<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> iterator = examItemResultList.iterator();
        while (iterator.hasNext()) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = iterator.next();
            if (ObjectUtil.isNotEmpty(examitemresultlistdto) && ObjectUtil.isNotEmpty(examitemresultlistdto.getExamItemCode()) && examitemresultlistdto.getExamItemCode().equals("3060100")) {
                examitemresultlistdto2 = examitemresultlistdto;
                iterator.remove();
            }
        }
        if (examitemresultlistdto2 != null) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto1 = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(examitemresultlistdto2, examitemresultlistdto1);
            examitemresultlistdto1.setExamItemName("12 导联心电图");
            examitemresultlistdto1.setExamItemCode("3060101");
            if("未检".equals(examitemresultlistdto1.getExamResult())){
                //心电图职业和12导联同步心电图都用的这个小项
                Describe describe=describeRepository.findByPatientcodeAndItemId(dataDTO.getExamNum(),"276");
                if(describe!=null){
                    examitemresultlistdto1.setExamResult(describe.getSignList());
                }
            }
            examItemResultList.add(examitemresultlistdto1);
        }
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processUnitAndMinMax(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, RDataDTO.DataDTO dataDTO, List<CheckItemReference> checkItemReferences) {
        return checkItemSet.stream().peek(examitemresultlistdto -> {
            for (CheckItemReference checkItemReference : checkItemReferences) {
                if (checkItemReference.getDictionaryCode().equals(examitemresultlistdto.getExamItemCode())) {
//                    if("3040000".equals(checkItemReference.getDictionaryCode())){
//                        System.out.println("1");
//                    }
                    boolean hasMin = true;
                    boolean hasMax = true;
                    boolean hasReference = true;
                    boolean hasUnit = true;
                    boolean isNumType = true;
                    if (!examitemresultlistdto.getExamResultType().equals("01")) {
                        isNumType = false;
                    }
                    if (isNumType) {
                        if (ObjectUtils.isEmpty(examitemresultlistdto.getReferenceRangeMin()) || examitemresultlistdto.getReferenceRangeMin().equals("无")) {
                            hasMin = false;
                        }
                        if (ObjectUtils.isEmpty(examitemresultlistdto.getReferenceRangeMax()) || examitemresultlistdto.getReferenceRangeMax().equals("无")) {
                            hasMax = false;
                        }
                        if (!hasMax && !hasMin
                                && examitemresultlistdto.getReferenceRangeMin()!=null&&examitemresultlistdto.getReferenceRangeMin().equals("0.0")
                                && examitemresultlistdto.getReferenceRangeMax()!=null&&examitemresultlistdto.getReferenceRangeMax().equals("0.0")) {
                            hasReference = false;
                        }
                        if (ObjectUtils.isEmpty(examitemresultlistdto.getExamItemUnitCode())) {
                            hasUnit = false;
                        }
                        if (!hasUnit) {
                            examitemresultlistdto.setExamItemUnitCode(checkItemReference.getValueunit());
                        }
                        if (hasReference) {
                            if (dataDTO.getGenderCode().equals("1")) {
                                if (!hasMin) {
                                    examitemresultlistdto.setReferenceRangeMin(StringUtils.isNotEmpty(checkItemReference.getValuemalemin())?checkItemReference.getValuemalemin():"0");
                                } else {
//                                    examitemresultlistdto.setReferenceRangeMin("无");
                                    examitemresultlistdto.setReferenceRangeMin("");
                                }
                                if (!hasMax) {
                                    examitemresultlistdto.setReferenceRangeMax(StringUtils.isNotEmpty(checkItemReference.getValuemalemax())?checkItemReference.getValuemalemax():"0");
                                } else {
//                                    examitemresultlistdto.setReferenceRangeMax("无");
                                    examitemresultlistdto.setReferenceRangeMax("");
                                }
                            } else {
                                if (!hasMin) {
                                    examitemresultlistdto.setReferenceRangeMin(checkItemReference.getValuefemalemin());
                                } else {
//                                    examitemresultlistdto.setReferenceRangeMin("无");
                                    examitemresultlistdto.setReferenceRangeMin("");
                                }
                                if (!hasMax) {
                                    examitemresultlistdto.setReferenceRangeMax(checkItemReference.getValuefemalemax());
                                } else {
//                                    examitemresultlistdto.setReferenceRangeMax("无");
                                    examitemresultlistdto.setReferenceRangeMax("");
                                }
                            }
                        } else {
                            examitemresultlistdto.setExamItemName("无");
//                            examitemresultlistdto.setReferenceRangeMax("无");
                            examitemresultlistdto.setReferenceRangeMax("");
                        }
                    } else {
                        examitemresultlistdto.setExamItemUnitCode("%");
//                        examitemresultlistdto.setReferenceRangeMin("无");
//                        examitemresultlistdto.setReferenceRangeMax("无");
                        examitemresultlistdto.setReferenceRangeMin("");
                        examitemresultlistdto.setReferenceRangeMax("");
                    }
                }
            }
        }).collect(Collectors.toSet());
    }

    /**
     * 处理肝功能
     *
     * @param checkItemSet
     * @param dataDTO
     * @return
     */
    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processLiverFunctionItem(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, RDataDTO.DataDTO dataDTO) {
        if (checkItemSet.stream().noneMatch(examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("4040300"))) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto1 = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            examitemresultlistdto1.setDocumentid(dataDTO.getDocumentid());
            examitemresultlistdto1.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultlistdto1.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultlistdto1.setExamNum(dataDTO.getExamNum());
            examitemresultlistdto1.setExamItemPname("肝胆功能检查");
            examitemresultlistdto1.setExamItemPcode("4040000");
            examitemresultlistdto1.setExamItemName("球蛋白");
            examitemresultlistdto1.setExamItemCode("4040300");
            examitemresultlistdto1.setHsExamItemName("球蛋白");
            examitemresultlistdto1.setHsExamItemCode("4040300");
            examitemresultlistdto1.setExamResultType("01");
            Optional<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> totalProteinExamResult = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("4040100")).findFirst();
            Optional<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> albuminExamResult = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("4040200")).findFirst();
            if (totalProteinExamResult.isPresent() && albuminExamResult.isPresent()) {
                if (!totalProteinExamResult.get().getExamResult().equals("未检") && !totalProteinExamResult.get().getExamResult().equals("") && !albuminExamResult.get().getExamResult().equals("未检") && !albuminExamResult.get().getExamResult().equals("")) {
                    examitemresultlistdto1.setExamResult(new BigDecimal(totalProteinExamResult.get().getExamResult()).subtract(new BigDecimal(albuminExamResult.get().getExamResult())).toString());
                } else {
                    examitemresultlistdto1.setExamResult("未检");
                }
                examitemresultlistdto1.setExamItemUnitCode(totalProteinExamResult.get().getExamItemUnitCode());
                String referenceRangeMin = totalProteinExamResult.get().getReferenceRangeMin();
                String referenceRangeMin1 = albuminExamResult.get().getReferenceRangeMin();
                BigDecimal min;
                if (ObjectUtil.isAllNotEmpty(referenceRangeMin, referenceRangeMin1) && NumberUtil.isNumber(referenceRangeMin) && NumberUtil.isNumber(referenceRangeMin1)) {
                    min = new BigDecimal(referenceRangeMin).subtract(new BigDecimal(referenceRangeMin1));
                } else {
                    min = BigDecimal.ZERO;
                }
                String referenceRangeMax = totalProteinExamResult.get().getReferenceRangeMax();
                String referenceRangeMax1 = albuminExamResult.get().getReferenceRangeMax();
                BigDecimal max;
                if (ObjectUtil.isAllNotEmpty(referenceRangeMax, referenceRangeMax1) && NumberUtil.isNumber(referenceRangeMax) && NumberUtil.isNumber(referenceRangeMax1)) {
                    max = new BigDecimal(referenceRangeMax).subtract(new BigDecimal(referenceRangeMax1));
                } else {
                    max = BigDecimal.ZERO;
                }
                examitemresultlistdto1.setReferenceRangeMin(min.toString());
                examitemresultlistdto1.setReferenceRangeMax(max.toString());
                if (examitemresultlistdto1.getExamResult().equals("未检")) {

                    examitemresultlistdto1.setAbnormal("0");
                } else {
                    boolean lessThanMin = min.compareTo(new BigDecimal(examitemresultlistdto1.getExamResult())) > 0;
                    boolean moreThanMax = max.compareTo(new BigDecimal(examitemresultlistdto1.getExamResult())) > 0;
                    if (lessThanMin | moreThanMax) {
                        examitemresultlistdto1.setExamResult("1");
                    }
                }
                examitemresultlistdto1.setSystemTime(dataDTO.getSystemTime());
                examitemresultlistdto1.setOperatetype(dataDTO.getOperatetype());
                checkItemSet.add(examitemresultlistdto1);

            }
        }
        return checkItemSet;
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processReference(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, HashSet<String> harmSet, List<SpecialHazardReference> specialHazardReferences, String genderCode) {
        Set<SpecialHazardReference> specHarmCode = specialHazardReferences.stream().filter(specialHazardReference -> harmSet.contains(specialHazardReference.getHarmCode())).collect(Collectors.toSet());

        return checkItemSet.stream().peek(examitemresultlistdto -> {
            for (SpecialHazardReference specialHazardReference : specHarmCode) {
                if (specialHazardReference.getJcCdoe().equals(examitemresultlistdto.getExamItemCode())) {
                    examitemresultlistdto.setExamItemUnitCode(specialHazardReference.getUnit());
                    if (genderCode.equals("1")) {
//男
                        examitemresultlistdto.setReferenceRangeMin(specialHazardReference.getScoperFloor().toString());
                        examitemresultlistdto.setReferenceRangeMax(specialHazardReference.getScopeUpper().toString());
                        examitemresultlistdto.setExamItemUnitCode(specialHazardReference.getUnit());
                    } else {
//                        女
                        examitemresultlistdto.setReferenceRangeMin(specialHazardReference.getGscoperfloor().toString());
                        examitemresultlistdto.setReferenceRangeMax(specialHazardReference.getGscopeupper().toString());
                        examitemresultlistdto.setExamItemUnitCode(specialHazardReference.getUnit());
                    }
                }
            }
        }).collect(Collectors.toSet());
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processErrorItem(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        List<String> strings = Arrays.asList("血尿", "咽部疼痛");
        checkItemSet = checkItemSet.stream().filter(examitemresultlistdto -> {
            return !strings.contains(examitemresultlistdto.getExamItemName());
        }).collect(Collectors.toSet());
        return checkItemSet;
    }

    private List<String> processNotDoExamItem(String needFeeId, List<NeedFeeItemDTO> needFeeItemDTOS) {
        Set<String> collect = needFeeItemDTOS.stream().filter(needFeeItemDTO -> needFeeItemDTO.getItemId().equals(needFeeId)).map(NeedFeeItemDTO::getExamId).collect(Collectors.toSet());
        return new ArrayList<>(collect);
    }

    private List<String> processNotDoItem(String needFeeId, List<NeedFeeItemDTO> needFeeItemDTOS, Peispatient peispatient) {
        ArrayList<String> strings = new ArrayList<>();
        List<String> harmIds = Arrays.asList(peispatient.getJhys().split(","));
        for (NeedFeeItemDTO needFeeItemDTO : needFeeItemDTOS) {
            if (needFeeItemDTO.getItemId().equals(needFeeId) && harmIds.contains(needFeeItemDTO.getHarmId())) {
                strings.add(needFeeItemDTO.getHarmId());
            }
        }
        return strings;
    }


    /**
     * 处理一般检查
     * 关于身高体重项缺失，如查询不到原始记录，则全部用999代码替代（代表不祥），后面查体过程中一定要有身高、体重数据。
     * 暂时处理办法：只要有一般检查其他小项结果，但却没有身高体重的，把身高体重加上。（有的收费项目就只做血压不做身高体重）
     * @param checkItemSet
     * @return
     */
    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processFaircheck(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet){


        return checkItemSet;
    }


    /**
     * 处理胸片
     * @param dataDTO
     * @param checkItemSet
     * @param peispatient
     * @param harmSet
     * @return
     */
    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processDust(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, Peispatient peispatient, HashSet<String> harmSet
            ,Set<String> needFeeIds) {
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO chestRadiograph = checkItemSet.stream().filter(
                examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("3080200") || examitemresultlistdto.getExamItemCode().equals("3081500")
        ).findFirst().orElse(null);
//        boolean hasDustHarm = validateHasDustHarm(harmSet);
        boolean hasDustHarm = (ObjectUtils.isNotEmpty(chestRadiograph) && needFeeIds.contains(chestRadiograph.getHsExamItemCode())) || validateHasDustHarm(harmSet);
        boolean hasChestRadiograph = chestRadiograph!=null;
        //validateHasChestRadiograph(checkItemSet);
        if (hasDustHarm && hasChestRadiograph) {
            checkItemSet.remove(chestRadiograph);
            //为什么在本来就有的情况另外加上一个？
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultlistdto.setExamNum(dataDTO.getExamNum());
            examitemresultlistdto.setExamItemPname("摄片");
            examitemresultlistdto.setExamItemPcode("3080000");
            examitemresultlistdto.setExamItemName("数字化摄影胸片（DR 胸片）");
            examitemresultlistdto.setExamItemCode("3080200");
            examitemresultlistdto.setHsExamItemName(chestRadiograph.getHsExamItemName());
            examitemresultlistdto.setHsExamItemCode(chestRadiograph.getHsExamItemCode());
            examitemresultlistdto.setExamResultType(chestRadiograph.getExamResultType());
            examitemresultlistdto.setExamResult(chestRadiograph.getExamResult());
            examitemresultlistdto.setExamItemUnitCode(chestRadiograph.getExamItemUnitCode());
            examitemresultlistdto.setReferenceRangeMin(chestRadiograph.getReferenceRangeMin());
            examitemresultlistdto.setReferenceRangeMax(chestRadiograph.getReferenceRangeMax());

            //当检查项目是胸片时，见胸片检查结果代码表
            String abnormal;
            if(checkItemRepository.hasPacsSign(peispatient.getPatientcode(), Constants.CHENFEI_SIGN_ID)){
                abnormal="1";
            }else if(!chestRadiograph.getExamResult().contains("未见明显异常")){
                //不包含未见明显异常就是其他异常
                abnormal="2";
            }else{
                abnormal="0";
            }
            examitemresultlistdto.setAbnormal(abnormal);
//            examitemresultlistdto.setAbnormal(chestRadiograph.getAbnormal());

            examitemresultlistdto.setSystemTime(chestRadiograph.getSystemTime());
            examitemresultlistdto.setOperatetype(chestRadiograph.getOperatetype());
            checkItemSet.add(examitemresultlistdto);


            //2024-06-17 3081500 胸片 未找到
            examitemresultlistdto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            examitemresultlistdto.setDocumentid(dataDTO.getDocumentid());
            examitemresultlistdto.setPhyexamname(dataDTO.getPhyexamname());
            examitemresultlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examitemresultlistdto.setExamNum(dataDTO.getExamNum());
            examitemresultlistdto.setExamItemPname("摄片");
            examitemresultlistdto.setExamItemPcode("3080000");
            examitemresultlistdto.setExamItemName("胸片");
            examitemresultlistdto.setExamItemCode("3081500");
            examitemresultlistdto.setHsExamItemName(chestRadiograph.getHsExamItemName());
            examitemresultlistdto.setHsExamItemCode(chestRadiograph.getHsExamItemCode());
            examitemresultlistdto.setExamResultType(chestRadiograph.getExamResultType());
            examitemresultlistdto.setExamResult(chestRadiograph.getExamResult());
            examitemresultlistdto.setExamItemUnitCode(chestRadiograph.getExamItemUnitCode());
            examitemresultlistdto.setReferenceRangeMin(chestRadiograph.getReferenceRangeMin());
            examitemresultlistdto.setReferenceRangeMax(chestRadiograph.getReferenceRangeMax());
            examitemresultlistdto.setAbnormal(abnormal);
            examitemresultlistdto.setSystemTime(chestRadiograph.getSystemTime());
            examitemresultlistdto.setOperatetype(chestRadiograph.getOperatetype());
            checkItemSet.add(examitemresultlistdto);

            //城阳上传说没有这个不行2024.5.8
            //这里不需要删除是因为，这是个set，似乎可以去重复，实测，如果添加相同的项目，会自动去重复
            //@Data注解自动生成equals hashCode toString getter setter方法
//            checkItemSet.remove(chestRadiograph);
        }
        return checkItemSet;
    }

    /**
     * 是否有胸片项目
     * @param checkItemSet
     * @return
     */
    private boolean validateHasChestRadiograph(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        return checkItemSet.stream().anyMatch(examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("3081500") || examitemresultlistdto.getExamItemCode().equals("3080200"));
    }

    /**
     * 危害因素粉尘的dictionary_code都以1开头
     * 其实氮氧化物也要检查胸片，这个方法不能准确判断是否必检胸片
     * @param harmSet
     * @return
     */
    private boolean validateHasDustHarm(HashSet<String> harmSet) {
        for (String s : harmSet) {
            if (s.startsWith("1")) {
                return true;
            }
        }
        return false;
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processSpecialItem(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO liverAndSpleen = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getHsExamItemName().contains("肝脏彩超")).findFirst().orElse(null);
        if (ObjectUtils.isNotEmpty(liverAndSpleen)) {
            checkItemSet.remove(liverAndSpleen);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO liver = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(liverAndSpleen, liver);
            liver.setExamItemCode("3010100");
            liver.setExamItemName("肝");
            checkItemSet.add(liver);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO spleen = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(liverAndSpleen, spleen);
            spleen.setExamItemCode("3010300");
            spleen.setExamItemName("脾");
            checkItemSet.add(spleen);
        }
        return checkItemSet;
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> removeItem(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        List<String> strings = Arrays.asList("内科其他", "五官科其他检查", "外科其他", "血压", "病理反射");
        return checkItemSet.stream().filter(examitemresultlistdto -> {
            return !strings.contains(examitemresultlistdto.getExamItemName());
        }).collect(Collectors.toSet());
    }

    private Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> processLeftAndRightItem(Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO ear = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getExamItemCode().equals("2020900")).findFirst().orElse(null);
        if (ObjectUtils.isNotEmpty(ear)) {
            checkItemSet = checkItemSet.stream().filter(examitemresultlistdto -> !examitemresultlistdto.getExamItemCode().equals("2020900")).collect(Collectors.toSet());
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(ear, right);
            right.setExamItemCode("2020901");
            right.setExamItemName("外耳(右)");
            checkItemSet.add(right);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(ear, left);
            left.setExamItemCode("2020902");
            left.setExamItemName("外耳(左)");
            checkItemSet.add(left);
        }
        Optional<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> corneaOpt = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getHsExamItemName().equals("角膜")).findFirst();
        if (corneaOpt.isPresent()) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = corneaOpt.get();
            checkItemSet.remove(examitemresultlistdto);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(examitemresultlistdto, right);
            right.setExamItemCode("2020401");
            right.setExamItemName("角膜(右)");
            checkItemSet.add(right);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(examitemresultlistdto, left);
            left.setExamItemCode("2020402");
            left.setExamItemName("角膜(左)");
            checkItemSet.add(left);
        }
        Optional<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> conjunctivaOpt = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getHsExamItemName().equals("结膜")).findFirst();
        if (conjunctivaOpt.isPresent()) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = conjunctivaOpt.get();
            checkItemSet.removeIf(examitemresultlistdto1 -> examitemresultlistdto1.getHsExamItemName().equals("结膜"));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(examitemresultlistdto, right);
            right.setExamItemCode("2020601");
            right.setExamItemName("结膜(右)");
            checkItemSet.add(right);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            BeanUtil.copyProperties(examitemresultlistdto, left);
            left.setExamItemCode("2020602");
            left.setExamItemName("结膜(左)");
            checkItemSet.add(left);
        }
//        Optional<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> bloodPressureOpt = checkItemSet.stream().filter(examitemresultlistdto -> examitemresultlistdto.getHsExamItemName().equals("角膜")).findFirst();
//        if (bloodPressureOpt.isPresent()) {
//            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = bloodPressureOpt.get();
//            checkItemSet.remove(examitemresultlistdto);
//            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
//            BeanUtil.copyProperties(examitemresultlistdto, right);
//            right.setExamItemCode("2020601");
//            right.setExamItemName("结膜(右)");
//            checkItemSet.add(right);
//            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
//            BeanUtil.copyProperties(examitemresultlistdto, left);
//            left.setExamItemCode("2020602");
//            left.setExamItemName("结膜(左)");
//            checkItemSet.add(left);
//        }
        return checkItemSet;
    }

    /**
     * 尿化学检查
     *
     * @param dataDTO
     * @param feeItemDTOS
     * @param checkItemSet
     */
    private void urineChemicalExaminationAndTreatment(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemDTOS, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        if (checkItemSet.stream().anyMatch(data -> "4110500".equals(data.getExamItemCode()))) {
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = feeItemDTOS.stream().filter(data -> "4020000".equals(data.getExamItemPcode())).findFirst().get();
            RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto1 = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
            BeanUtil.copyProperties(examitemresultdto, examitemresultdto1);
            examitemresultdto1.setExamItemPcode("4110000");
            examitemresultdto1.setExamItemPname("尿液化学检查");
            feeItemDTOS.add(examitemresultdto1);
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = checkItemSet.stream().filter(data -> "4110500".equals(data.getExamItemCode())).findFirst().get();
            examitemresultlistdto.setExamItemUnitCode("%");
//            examitemresultlistdto.setReferenceRangeMin("无");
//            examitemresultlistdto.setReferenceRangeMax("无");
            checkItemSet.removeIf(data -> "4110500".equals(data.getExamItemCode()));
            examitemresultlistdto.setExamItemPcode("4110000");
            examitemresultlistdto.setExamItemPname("尿液化学检查");
            checkItemSet.add(examitemresultlistdto);
        }
    }

    //获取满足状态的补检体检者
    private Peispatient getBujianByPatientcode(String patientcode){
        Peispatient bujian=peispatientRepository.findByInsuranceno(patientcode);
        if(bujian==null || bujian.getZytjzt() == null || bujian.getZytjzt().intValue()<7)return null;
        return bujian;
    }

    /**
     * 肺功能处理
     * 通过这个方法，即使选择了健康的肺功能检测，部分小项也是健康的，也可以正常上传
     * @param dataDTO
     * @param feeItemDTOS
     * @param checkItemSet
     */
    private void pulmonaryFunctionProcessing(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemDTOS, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        Lung lung = lungRepository.findByPatientcode(dataDTO.getExamNum());
        boolean isBujian=false;
        String bujianCode=null;
        if(lung==null){
            Peispatient bujian=getBujianByPatientcode(dataDTO.getExamNum());
            if(bujian!=null){
                bujianCode=bujian.getPatientcode();
                lung = lungRepository.findByPatientcode(bujianCode);
                isBujian=lung!=null;
            }
        }
        if (checkItemSet.stream().anyMatch(data -> "3020000".equals(data.getExamItemCode()))) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = checkItemSet.stream().filter(data -> "3020000".equals(data.getExamItemCode())).findFirst().get();
            checkItemSet.removeIf(data -> "3020000".equals(data.getExamItemCode()));
            examitemresultlistdto.setExamItemCode("3021700");
            examitemresultlistdto.setExamItemName("肺功能结论");
            if("未检".equals(examitemresultlistdto.getExamResult())){
                Describe describe = isBujian?describeRepository.findByPatientcodeAndItemId(bujianCode,"515"):describeRepository.findByPatientcodeAndItemId(dataDTO.getExamNum(),"515");
                if(describe!=null){
                    examitemresultlistdto.setExamResult(describe.getSignList());
                }
            }
            checkItemSet.add(examitemresultlistdto);
        }
        if (lung != null) {
            Double fvc = lung.getPercentageFvc();
            Double fev = lung.getPercentageFev();
            Double fevFvc = lung.getFevFvc();
            //fevfvc不能超过100
            if(fevFvc!=null&&fevFvc.doubleValue()>100.0)fevFvc=100.0;

            checkItemSet.removeIf(data -> "3020100".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fvcData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fvcData.setDocumentid(dataDTO.getDocumentid());
            fvcData.setPhyexamcode(dataDTO.getPhyexamcode());
            fvcData.setPhyexamname(dataDTO.getPhyexamname());
            fvcData.setExamNum(dataDTO.getExamNum());
            fvcData.setExamItemPcode("3020000");
            fvcData.setExamItemPname("肺功能");
            fvcData.setExamItemCode("3020100");
            fvcData.setExamItemName("用力肺活量 FVC（%）");
            fvcData.setHsExamItemCode("3020100");
            fvcData.setHsExamItemName("用力肺活量 FVC（%）");
            fvcData.setExamResultType("01");
            fvcData.setExamResult(ObjectUtils.isEmpty(fvc)?"":fvc.toString());
            fvcData.setAbnormal("0");
            fvcData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fvcData.setOperatetype("Add");
            fvcData.setExamItemUnitCode("%");
//            fvcData.setReferenceRangeMin("无");
//            fvcData.setReferenceRangeMax("无");
            checkItemSet.add(fvcData);

            checkItemSet.removeIf(data -> "3020200".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fevData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fevData.setDocumentid(dataDTO.getDocumentid());
            fevData.setPhyexamcode(dataDTO.getPhyexamcode());
            fevData.setPhyexamname(dataDTO.getPhyexamname());
            fevData.setExamNum(dataDTO.getExamNum());
            fevData.setExamItemPcode("3020000");
            fevData.setExamItemPname("肺功能");
            fevData.setExamItemCode("3020200");
            fevData.setExamItemName("第一秒时间肺活量 FEV1（%）");
            fevData.setHsExamItemCode("3020200");
            fevData.setHsExamItemName("第一秒时间肺活量 FEV1（%）");
            fevData.setExamResultType("01");
            fevData.setExamResult(fev.toString());
            fevData.setAbnormal("0");
            fevData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fevData.setOperatetype("Add");
//            fevData.setReferenceRangeMin("无");
//            fevData.setReferenceRangeMax("无");
            checkItemSet.add(fevData);

            checkItemSet.removeIf(data -> "3020300".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fevFvcData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fevFvcData.setDocumentid(dataDTO.getDocumentid());
            fevFvcData.setPhyexamcode(dataDTO.getPhyexamcode());
            fevFvcData.setPhyexamname(dataDTO.getPhyexamname());
            fevFvcData.setExamNum(dataDTO.getExamNum());
            fevFvcData.setExamItemPcode("3020000");
            fevFvcData.setExamItemPname("肺功能");
            fevFvcData.setExamItemCode("3020300");
            fevFvcData.setExamItemName("FEV1/FVC");
            fevFvcData.setHsExamItemCode("3020300");
            fevFvcData.setHsExamItemName("FEV1/FVC");
            fevFvcData.setExamResultType("01");
            fevFvcData.setExamResult(fevFvc.toString());
            fevFvcData.setAbnormal("0");
            fevFvcData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fevFvcData.setOperatetype("Add");
//            fevFvcData.setReferenceRangeMin("无");
//            fevFvcData.setReferenceRangeMax("无");
            checkItemSet.add(fevFvcData);

        }

    }

    private void pulmonaryFunctionProcessing(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, String patientcode) {
        if (checkItemSet.stream().anyMatch(data -> "3020000".equals(data.getExamItemCode()))) {
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto = checkItemSet.stream().filter(data -> "3020000".equals(data.getExamItemCode())).findFirst().get();
            checkItemSet.removeIf(data -> "3020000".equals(data.getExamItemCode()));
            examitemresultlistdto.setExamItemCode("3021700");
            examitemresultlistdto.setExamItemName("肺功能结论");
            if("未检".equals(examitemresultlistdto.getExamResult())){
                Describe describe =describeRepository.findByPatientcodeAndItemId(dataDTO.getExamNum(),"515");
                if(describe!=null){
                    examitemresultlistdto.setExamResult(describe.getSignList());
                }
            }
            checkItemSet.add(examitemresultlistdto);
        }
        Lung lung = lungRepository.findByPatientcode(dataDTO.getExamNum());
        if (ObjectUtil.isEmpty(lung)) {
            lung = lungRepository.findByPatientcode(patientcode);
        }
        if (lung != null) {
            Double fvc = lung.getPercentageFvc();
            Double fev = lung.getPercentageFev();
            Double fevFvc = lung.getFevFvc();
            //fevfvc不能超过100
            if(fevFvc!=null&&fevFvc.doubleValue()>100.0)fevFvc=100.0;

            checkItemSet.removeIf(data -> "3020100".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fvcData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fvcData.setDocumentid(dataDTO.getDocumentid());
            fvcData.setPhyexamcode(dataDTO.getPhyexamcode());
            fvcData.setPhyexamname(dataDTO.getPhyexamname());
            fvcData.setExamNum(dataDTO.getExamNum());
            fvcData.setExamItemPcode("3020000");
            fvcData.setExamItemPname("肺功能");
            fvcData.setExamItemCode("3020100");
            fvcData.setExamItemName("用力肺活量 FVC（%）");
            fvcData.setHsExamItemCode("3020100");
            fvcData.setHsExamItemName("用力肺活量 FVC（%）");
            fvcData.setExamResultType("01");
            fvcData.setExamResult(fvc.toString());
            fvcData.setAbnormal("0");
            fvcData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fvcData.setOperatetype("Add");
            fvcData.setExamItemUnitCode("%");
//            fvcData.setReferenceRangeMin("无");
//            fvcData.setReferenceRangeMax("无");
            checkItemSet.add(fvcData);

            checkItemSet.removeIf(data -> "3020200".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fevData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fevData.setDocumentid(dataDTO.getDocumentid());
            fevData.setPhyexamcode(dataDTO.getPhyexamcode());
            fevData.setPhyexamname(dataDTO.getPhyexamname());
            fevData.setExamNum(dataDTO.getExamNum());
            fevData.setExamItemPcode("3020000");
            fevData.setExamItemPname("肺功能");
            fevData.setExamItemCode("3020200");
            fevData.setExamItemName("第一秒时间肺活量 FEV1（%）");
            fevData.setHsExamItemCode("3020200");
            fevData.setHsExamItemName("第一秒时间肺活量 FEV1（%）");
            fevData.setExamResultType("01");
            fevData.setExamResult(fev.toString());
            fevData.setAbnormal("0");
            fevData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fevData.setOperatetype("Add");
//            fevData.setReferenceRangeMin("无");
//            fevData.setReferenceRangeMax("无");
            checkItemSet.add(fevData);

            checkItemSet.removeIf(data -> "3020300".equals(data.getExamItemCode()));
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO fevFvcData = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
            fevFvcData.setDocumentid(dataDTO.getDocumentid());
            fevFvcData.setPhyexamcode(dataDTO.getPhyexamcode());
            fevFvcData.setPhyexamname(dataDTO.getPhyexamname());
            fevFvcData.setExamNum(dataDTO.getExamNum());
            fevFvcData.setExamItemPcode("3020000");
            fevFvcData.setExamItemPname("肺功能");
            fevFvcData.setExamItemCode("3020300");
            fevFvcData.setExamItemName("FEV1/FVC");
            fevFvcData.setHsExamItemCode("3020300");
            fevFvcData.setHsExamItemName("FEV1/FVC");
            fevFvcData.setExamResultType("01");
            fevFvcData.setExamResult(fevFvc.toString());
            fevFvcData.setAbnormal("0");
            fevFvcData.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            fevFvcData.setOperatetype("Add");
//            fevFvcData.setReferenceRangeMin("无");
//            fevFvcData.setReferenceRangeMax("无");
            checkItemSet.add(fevFvcData);

        }
    }

    /**
     * 删除骨导
     * 电测听处理
     *
     * @param dataDTO
     * @param feeItemSet
     * @param checkItemSet
     * @param peispatient
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void handleElectricityHearing2(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, Peispatient peispatient) {
        if (feeItemSet.stream().noneMatch(examitemresultdto -> "3040000".equals(examitemresultdto.getExamItemPcode()))) {
            return;
        }
        int age = peispatient.getAge();
        String genderCode = dataDTO.getGenderCode();
        checkItemSet.removeIf(examitemresultlistdto -> examitemresultlistdto.getExamItemName().contains("骨导"));
        /**
         * 2022.9.19
         * 如果这个人没有电测听结果会报错的问题处理
         * 由于骨导已被剔除，职业和综合电测听都只包含这几个气导值500 1000 2000 3000 4000 6000
         * 所以判断没有左耳气导500的就直接返回
         *
         * 但是平台那边要求必须有电测听的值。
         */
        Optional optional=checkItemSet.stream().filter(examitemresultlistdto -> "3040100".equals(examitemresultlistdto.getExamItemCode())).findFirst();
        if(!optional.isPresent())return;

//        语频
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left500 = checkItemSet.stream().filter(examitemresultlistdto -> "3040100".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left500ExamResult = Double.valueOf(left500.getExamResult());
        Double left500Value = correction(genderCode, left500, electricalAudiometryProperties.getMList500Value(age), electricalAudiometryProperties.getFList500Value(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left1000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040200".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left1000ExamResult = Double.valueOf(left1000.getExamResult());
        Double left1000Value = correction(genderCode, left1000, electricalAudiometryProperties.getMList1KValue(age), electricalAudiometryProperties.getFList1KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left2000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040300".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left2000ExamResult = Double.valueOf(left2000.getExamResult());
        Double left2000Value = correction(genderCode, left2000, electricalAudiometryProperties.getMList2KValue(age), electricalAudiometryProperties.getFList2KValue(age));
//        高频
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left3000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040400".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left3000ExamResult = Double.valueOf(left3000.getExamResult());
        Double left3000Value = correction(genderCode, left3000, electricalAudiometryProperties.getMList3KValue(age), electricalAudiometryProperties.getFList3KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left4000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040500".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left4000ExamResult = Double.valueOf(left4000.getExamResult());
        Double left4000Value = correction(genderCode, left4000, electricalAudiometryProperties.getMList4KValue(age), electricalAudiometryProperties.getFList4KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left6000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040600".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left6000ExamResult = Double.valueOf(left6000.getExamResult());
        Double left6000Value = correction(genderCode, left6000, electricalAudiometryProperties.getMList6KValue(age), electricalAudiometryProperties.getFList6KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right500 = checkItemSet.stream().filter(examitemresultlistdto -> "3040800".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right500ExamResult = Double.valueOf(right500.getExamResult());
        Double right500Value = correction(genderCode, right500, electricalAudiometryProperties.getMList500Value(age), electricalAudiometryProperties.getFList500Value(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right1000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040900".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right1000ExamResult = Double.valueOf(right1000.getExamResult());
        Double right1000Value = correction(genderCode, right1000, electricalAudiometryProperties.getMList1KValue(age), electricalAudiometryProperties.getFList1KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right2000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041000".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right2000ExamResult = Double.valueOf(right2000.getExamResult());
        Double right2000Value = correction(genderCode, right2000, electricalAudiometryProperties.getMList2KValue(age), electricalAudiometryProperties.getFList2KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right3000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041100".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right3000Value = correction(genderCode, right3000, electricalAudiometryProperties.getMList3KValue(age), electricalAudiometryProperties.getFList3KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right4000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041200".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right4000Value = correction(genderCode, right4000, electricalAudiometryProperties.getMList4KValue(age), electricalAudiometryProperties.getFList4KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right6000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041300".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right6000Value = correction(genderCode, right6000, electricalAudiometryProperties.getMList6KValue(age), electricalAudiometryProperties.getFList6KValue(age));
        Integer leftLowNum = BigDecimal.valueOf((left500Value + left1000Value + left2000Value) / 3 * 0.9 + left4000Value * 0.1).setScale(0, RoundingMode.HALF_UP).intValue();
        Integer rightLowNum = BigDecimal.valueOf((right500Value + right1000Value + right2000Value) / 3 * 0.9 + right4000Value * 0.1).setScale(0, RoundingMode.HALF_UP).intValue();
        Integer binauralLowNum = BigDecimal.valueOf((leftLowNum + rightLowNum) / 2).setScale(0, RoundingMode.HALF_UP).intValue();
//        Integer leftHighNum = BigDecimal.valueOf((left3000Value + left4000Value + left6000Value) / 3).setScale(0, RoundingMode.HALF_UP).intValue();
//        Integer rightHighNum = BigDecimal.valueOf((right3000Value + right4000Value + right6000Value) / 3).setScale(0, RoundingMode.HALF_UP).intValue();
        Integer binauralHighNum = BigDecimal.valueOf((left3000Value + left4000Value + left6000Value + right3000Value + right4000Value + right6000Value) / 6).setScale(0, RoundingMode.HALF_UP).intValue();
        setElectricalAudiometryResults(dataDTO, checkItemSet, leftLowNum, "3041700", "左耳语频平均听阈","0");
        setElectricalAudiometryResults(dataDTO, checkItemSet, rightLowNum, "3041800", "右耳语频平均听阈","0");
        //电测听双耳高频平均听域大于40者,判定为异常
        setElectricalAudiometryResults(dataDTO, checkItemSet, binauralHighNum, "3041900", "双耳高频平均听阈"
                ,binauralHighNum!=null&&binauralHighNum.intValue()>40?"1":"0");
        setElectricalAudiometryResults(dataDTO, checkItemSet, binauralLowNum, "3042000", "双耳语频平均听阈","0");

        //2024.5.20 上岗前，低频500HZ、1000HZ、2000HZ，左右耳任意频率大于25则判定为异常
        //骨导一般是复查时候测试的，500、1000和2000如果大于25也是异常。
        if("0".equals(peispatient.getMedicaltype())){
            if(left500ExamResult!=null&&left500ExamResult.doubleValue()>25){
                left500.setAbnormal("1");
            }
            if(left1000ExamResult!=null&&left1000ExamResult.doubleValue()>25){
                left1000.setAbnormal("1");
            }
            if(left2000ExamResult!=null&&left2000ExamResult.doubleValue()>25){
                left2000.setAbnormal("1");
            }
            if(right500ExamResult!=null&&right500ExamResult.doubleValue()>25){
                right500.setAbnormal("1");
            }
            if(right1000ExamResult!=null&&right1000ExamResult.doubleValue()>25){
                right1000.setAbnormal("1");
            }
            if(right2000ExamResult!=null&&right2000ExamResult.doubleValue()>25){
                right2000.setAbnormal("1");
            }
            setBoneAbnormal("3050100",checkItemSet);
            setBoneAbnormal("3050200",checkItemSet);
            setBoneAbnormal("3050300",checkItemSet);
            setBoneAbnormal("3050800",checkItemSet);
            setBoneAbnormal("3050900",checkItemSet);
            setBoneAbnormal("3051000",checkItemSet);

        }


        //电测听任意频率矫正数值>25,则传输的时候结果标识为1,小于等于25结果为正常，标识为0
        if(left500Value>25){
            left500.setAbnormal("1");
        }
        if(left1000Value>25){
            left1000.setAbnormal("1");
        }
        if(left2000Value>25){
            left2000.setAbnormal("1");
        }
        if(left3000Value>25){
            left3000.setAbnormal("1");
        }
        if(left4000Value>25){
            left4000.setAbnormal("1");
        }
        if(left6000Value>25){
            left6000.setAbnormal("1");
        }
        if(right500Value>25){
            right500.setAbnormal("1");
        }
        if(right1000Value>25){
            right1000.setAbnormal("1");
        }
        if(right2000Value>25){
            right2000.setAbnormal("1");
        }
        if(right3000Value>25){
            right3000.setAbnormal("1");
        }
        if(right4000Value>25){
            right4000.setAbnormal("1");
        }
        if(right6000Value>25){
            right6000.setAbnormal("1");
        }


    }

    private void processElectricityAbnormal(RDataDTO.DataDTO dataDTO,Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, Peispatient peispatient){
        Optional optional=checkItemSet.stream().filter(examitemresultlistdto -> "3040100".equals(examitemresultlistdto.getExamItemCode())).findFirst();
        if(!optional.isPresent())return;
        int age = peispatient.getAge();

        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left500 = checkItemSet.stream().filter(examitemresultlistdto -> "3040100".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left500ExamResult = Double.valueOf(left500.getExamResult());
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left1000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040200".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left1000ExamResult = Double.valueOf(left1000.getExamResult());
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left2000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040300".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left2000ExamResult = Double.valueOf(left2000.getExamResult());

        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right500 = checkItemSet.stream().filter(examitemresultlistdto -> "3040800".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right500ExamResult = Double.valueOf(right500.getExamResult());
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right1000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040900".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right1000ExamResult = Double.valueOf(right1000.getExamResult());
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right2000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041000".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right2000ExamResult = Double.valueOf(right2000.getExamResult());

        String genderCode = dataDTO.getGenderCode();
        Double left500Value = correction(genderCode, left500, electricalAudiometryProperties.getMList500Value(age), electricalAudiometryProperties.getFList500Value(age));
        Double left1000Value = correction(genderCode, left1000, electricalAudiometryProperties.getMList1KValue(age), electricalAudiometryProperties.getFList1KValue(age));
        Double left2000Value = correction(genderCode, left2000, electricalAudiometryProperties.getMList2KValue(age), electricalAudiometryProperties.getFList2KValue(age));
//        高频
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left3000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040400".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left3000ExamResult = Double.valueOf(left3000.getExamResult());
        Double left3000Value = correction(genderCode, left3000, electricalAudiometryProperties.getMList3KValue(age), electricalAudiometryProperties.getFList3KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left4000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040500".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left4000ExamResult = Double.valueOf(left4000.getExamResult());
        Double left4000Value = correction(genderCode, left4000, electricalAudiometryProperties.getMList4KValue(age), electricalAudiometryProperties.getFList4KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left6000 = checkItemSet.stream().filter(examitemresultlistdto -> "3040600".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double left6000ExamResult = Double.valueOf(left6000.getExamResult());
        Double left6000Value = correction(genderCode, left6000, electricalAudiometryProperties.getMList6KValue(age), electricalAudiometryProperties.getFList6KValue(age));

        Double right500Value = correction(genderCode, right500, electricalAudiometryProperties.getMList500Value(age), electricalAudiometryProperties.getFList500Value(age));
        Double right1000Value = correction(genderCode, right1000, electricalAudiometryProperties.getMList1KValue(age), electricalAudiometryProperties.getFList1KValue(age));
        Double right2000Value = correction(genderCode, right2000, electricalAudiometryProperties.getMList2KValue(age), electricalAudiometryProperties.getFList2KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right3000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041100".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right3000Value = correction(genderCode, right3000, electricalAudiometryProperties.getMList3KValue(age), electricalAudiometryProperties.getFList3KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right4000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041200".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right4000Value = correction(genderCode, right4000, electricalAudiometryProperties.getMList4KValue(age), electricalAudiometryProperties.getFList4KValue(age));
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO right6000 = checkItemSet.stream().filter(examitemresultlistdto -> "3041300".equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
        Double right6000Value = correction(genderCode, right6000, electricalAudiometryProperties.getMList6KValue(age), electricalAudiometryProperties.getFList6KValue(age));


        //2024.5.20 上岗前，低频500HZ、1000HZ、2000HZ，左右耳任意频率大于25则判定为异常
        //骨导一般是复查时候测试的，500、1000和2000如果大于25也是异常。
        if("0".equals(peispatient.getMedicaltype())){
            if(left500ExamResult!=null&&left500ExamResult.doubleValue()>25){
                left500.setAbnormal("1");
            }
            if(left1000ExamResult!=null&&left1000ExamResult.doubleValue()>25){
                left1000.setAbnormal("1");
            }
            if(left2000ExamResult!=null&&left2000ExamResult.doubleValue()>25){
                left2000.setAbnormal("1");
            }
            if(right500ExamResult!=null&&right500ExamResult.doubleValue()>25){
                right500.setAbnormal("1");
            }
            if(right1000ExamResult!=null&&right1000ExamResult.doubleValue()>25){
                right1000.setAbnormal("1");
            }
            if(right2000ExamResult!=null&&right2000ExamResult.doubleValue()>25){
                right2000.setAbnormal("1");
            }
            setBoneAbnormal("3050100",checkItemSet);
            setBoneAbnormal("3050200",checkItemSet);
            setBoneAbnormal("3050300",checkItemSet);
            setBoneAbnormal("3050800",checkItemSet);
            setBoneAbnormal("3050900",checkItemSet);
            setBoneAbnormal("3051000",checkItemSet);
        }



        //电测听任意频率矫正数值>25,则传输的时候结果标识为1,小于等于25结果为正常，标识为0
        if(left500Value>25){
            left500.setAbnormal("1");
        }
        if(left1000Value>25){
            left1000.setAbnormal("1");
        }
        if(left2000Value>25){
            left2000.setAbnormal("1");
        }
        if(left3000Value>25){
            left3000.setAbnormal("1");
        }
        if(left4000Value>25){
            left4000.setAbnormal("1");
        }
        if(left6000Value>25){
            left6000.setAbnormal("1");
        }
        if(right500Value>25){
            right500.setAbnormal("1");
        }
        if(right1000Value>25){
            right1000.setAbnormal("1");
        }
        if(right2000Value>25){
            right2000.setAbnormal("1");
        }
        if(right3000Value>25){
            right3000.setAbnormal("1");
        }
        if(right4000Value>25){
            right4000.setAbnormal("1");
        }
        if(right6000Value>25){
            right6000.setAbnormal("1");
        }



    }

    private void setBoneAbnormal(String code,Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet){
        Optional optional= checkItemSet.stream().filter(examitemresultlistdto -> code.equals(examitemresultlistdto.getExamItemCode())).findFirst();
        if(optional.isPresent()){
            RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO dto= checkItemSet.stream().filter(examitemresultlistdto -> code.equals(examitemresultlistdto.getExamItemCode())).findFirst().get();
            Double value=Double.valueOf(dto.getExamResult());
            if(value!=null&&value.doubleValue()>25){
                dto.setAbnormal("1");
            }
        }
    }

    private Double correction(String genderCode, RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO left500, Integer mValue, Integer fValue) {
        Double left500Value = Double.valueOf(left500.getExamResult());
        mValue = mValue == null ? 0 : mValue;
        fValue = fValue == null ? 0 : fValue;
        if ("1".equals(genderCode)) {
//            男
            left500Value -= mValue;
        } else {
//            女
            left500Value -= fValue;
        }
        return left500Value;
    }

    private void setElectricalAudiometryResults(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet, Integer leftLowNum, String examItemCode, String examItemName
            ,String abnormal) {
        RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO leftLow = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
        leftLow.setDocumentid(dataDTO.getDocumentid());
        leftLow.setPhyexamcode(dataDTO.getPhyexamcode());
        leftLow.setPhyexamname(dataDTO.getPhyexamname());
        leftLow.setExamNum(dataDTO.getExamNum());
        leftLow.setExamItemPcode("3040000");
        leftLow.setExamItemPname("纯音气导听阈测试");
        leftLow.setExamItemCode(examItemCode);
        leftLow.setExamItemName(examItemName);
        leftLow.setHsExamItemCode(examItemCode);
        leftLow.setHsExamItemName(examItemName);
        leftLow.setExamResultType("01");
        leftLow.setExamResult(leftLowNum.toString());
        leftLow.setAbnormal(abnormal);
        leftLow.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        leftLow.setOperatetype("Add");
        leftLow.setExamItemUnitCode("%");
        //由于不是体检系统内实际存在的检查项目，在这里设置不会被Processunit设置正确范围,在specialtreatment里单独设置范围
//        leftLow.setReferenceRangeMin("0");
//        leftLow.setReferenceRangeMax("200");
        checkItemSet.add(leftLow);
    }

    /**
     * 处理电测听
     *
     * @param dataDTO
     * @param feeItemSet   收费项目（检查项目）
     * @param checkItemSet 检查项目（项目明细）
     */
    private void handleElectricityHearing(RDataDTO.DataDTO dataDTO, Set<RDataDTO.DataDTO.EXAMITEMRESULTDTO> feeItemSet, Set<RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO> checkItemSet) {
        List<String> airConductionCodes = Arrays.asList("3040100,3040200,3040300,3040400,3040500,3040600,3040700,3040800,3040900,3041000,3041100,3041200,3041300,3041400,3041500,3041600,3041700,3041800,3041900,3042000".split(","));
        List<String> boneConductionCodes = Arrays.asList("3050100,3050200,3050300,3050400,3050500,3050600,3050700,3050800,3050900,3051000,3051100,3051200,3051300,3051400,3051500,3051600,3051700,3051800,3051900,3052000".split(","));
        boolean hasAir = false;
        boolean hasBone = false;
        for (RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO examitemresultlistdto : checkItemSet) {
            if (boneConductionCodes.contains(examitemresultlistdto.getExamItemCode()) || examitemresultlistdto.getExamItemPname().contains("骨导")) {
                hasBone = true;
                if (!"3050000".equals(examitemresultlistdto.getExamItemPcode()) || !examitemresultlistdto.getExamItemName().contains("骨导")) {
                    checkItemSet.remove(examitemresultlistdto);

                    RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO boneDto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                    BeanUtil.copyProperties(examitemresultlistdto, boneDto);
                    boneDto.setExamItemPcode("3050000");
                    boneDto.setExamItemPname("纯音骨导听阈测试");
                    log.info(boneDto.toString());
                    checkItemSet.add(boneDto);
                }
            }
            if (airConductionCodes.contains(examitemresultlistdto.getExamItemCode()) || !examitemresultlistdto.getExamItemName().contains("气导")) {
                hasAir = true;
                if (!"3040000".equals(examitemresultlistdto.getExamItemPcode()) || !examitemresultlistdto.getExamItemName().contains("气导")) {
                    checkItemSet.remove(examitemresultlistdto);

                    RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO boneDto = new RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO();
                    BeanUtil.copyProperties(examitemresultlistdto, boneDto);
                    boneDto.setExamItemPcode("3040000");
                    boneDto.setExamItemPname("纯音气导听阈测试");
                    log.info(boneDto.toString());
                    checkItemSet.add(boneDto);
                }
            }
        }
        String jhys = feeItemSet.stream().filter(examitemresultdto -> {
            return "3050000".equals(examitemresultdto.getExamItemPcode()) || "3040000".equals(examitemresultdto.getExamItemPcode());
        }).map(RDataDTO.DataDTO.EXAMITEMRESULTDTO::getItamCode).collect(Collectors.joining(","));
        if (hasAir) {
            if (feeItemSet.stream().noneMatch(examitemresultdto -> "3040000".equals(examitemresultdto.getExamItemPcode()))) {
                RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
                examitemresultdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultdto.setExamNum(dataDTO.getExamNum());
                examitemresultdto.setItamCode(jhys);
                examitemresultdto.setExamItemPcode("3040000");
                examitemresultdto.setExamItemPname("纯音气导听阈测试");
                examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultdto.setOperatetype("Add");
                feeItemSet.add(examitemresultdto);
            }
        }

        if (hasBone) {
            if (feeItemSet.stream().noneMatch(examitemresultdto -> "3050000".equals(examitemresultdto.getExamItemPcode()))) {
                RDataDTO.DataDTO.EXAMITEMRESULTDTO examitemresultdto = new RDataDTO.DataDTO.EXAMITEMRESULTDTO();
                examitemresultdto.setDocumentid(dataDTO.getDocumentid());
                examitemresultdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examitemresultdto.setPhyexamname(dataDTO.getPhyexamname());
                examitemresultdto.setExamNum(dataDTO.getExamNum());
                examitemresultdto.setItamCode(jhys);
                examitemresultdto.setExamItemPcode("3050000");
                examitemresultdto.setExamItemPname("纯音骨导听阈测试");
                examitemresultdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examitemresultdto.setOperatetype("Add");
                feeItemSet.add(examitemresultdto);
            }
        }

    }


    private void setPastMedicalHistoryList(RDataDTO.DataDTO dataDTO) {
        ArrayList<RDataDTO.DataDTO.PASTMEDICALHISTORYLISTDTO> pastMedicalHistoryList = new ArrayList<>();
        RDataDTO.DataDTO.PASTMEDICALHISTORYLISTDTO pastmedicalhistorylistdto = new RDataDTO.DataDTO.PASTMEDICALHISTORYLISTDTO();
        pastmedicalhistorylistdto.setDocumentid(dataDTO.getDocumentid());
        pastmedicalhistorylistdto.setPhyexamcode(dataDTO.getPhyexamcode());
        pastmedicalhistorylistdto.setPhyexamname(dataDTO.getPhyexamname());
        pastmedicalhistorylistdto.setExamNum(dataDTO.getExamNum());
        pastmedicalhistorylistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        pastMedicalHistoryList.add(pastmedicalhistorylistdto);
        dataDTO.setPastMedicalHistoryList(pastMedicalHistoryList);
    }

    private void setCareerHistoryList(RDataDTO.DataDTO dataDTO, List<WzZys> wzZysList, List<HarmPackageMatchDto> harmPackageMatchDtos, List<String> otherRiskReason
            , StringBuilder stringBuilder) {
        if (ObjectUtils.isNotEmpty(wzZysList)) {
            ArrayList<RDataDTO.DataDTO.CAREERHISTORYLISTDTO> careerHistoryList1 = new ArrayList<>();
            for (WzZys wzZys : wzZysList) {
                RDataDTO.DataDTO.CAREERHISTORYLISTDTO careerhistorylistdto = new RDataDTO.DataDTO.CAREERHISTORYLISTDTO();
                careerhistorylistdto.setDocumentid(dataDTO.getDocumentid());
                careerhistorylistdto.setPhyexamname(dataDTO.getPhyexamname());
                careerhistorylistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                careerhistorylistdto.setExamNum(dataDTO.getExamNum());
                String date = DateUtil.format(wzZys.getStartDate(), "yyyyMMdd") + "-" + DateUtil.format(wzZys.getStopDate(), "yyyyMMdd");
                careerhistorylistdto.setSEndTime(date);
//                if (!dataDTO.getExamTypeCode().equals("01")) {
                careerhistorylistdto.setWorkUnit(dataDTO.getEnterpriseName());
//                } else {
//                    careerhistorylistdto.setWorkUnit("无");
//                }
                String branch = wzZys.getBranch();
                if (ObjectUtil.isNotEmpty(branch)) {
                    careerhistorylistdto.setWorkshop(branch.trim());
                } else {
                    careerhistorylistdto.setWorkshop("生产车间");
                }
                careerhistorylistdto.setWorkType(dataDTO.getWorkType());
                careerhistorylistdto.setWorkTypeCode(dataDTO.getWorkTypeCode());
                String[] strings = wzZys.getOccupationHarm().split(",");
                HashMap<String, List<String>> otherReasonMap = new HashMap<>();
                Set<String> originRiskList = new HashSet<>();
                HashSet<RiskDTO> objects = new HashSet<>();
                if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                    for (String string : strings) {
                        List<String> collect = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(string)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                        if (ObjectUtils.isNotEmpty(collect)) {
                            originRiskList.addAll(collect);
                        } else {
                            originRiskList.add(string);
                        }
                    }
                } else {
                    originRiskList.addAll(Arrays.asList(strings));
                }
                HashSet<String> arrayList = new HashSet<>();
                for (String originRisk : originRiskList) {
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(originRisk);
                    //异丁烷在匹配表中没有，会直接跳过
                    if (ObjectUtils.isEmpty(hazards)) {
                        continue;
                    }
                    String dictionaryCode = hazards.getDictionaryCode();
                    if (ObjectUtils.isNotEmpty(otherRiskReason) && otherRiskReason.contains(dictionaryCode)) {
                        setOtherReason(otherReasonMap, dictionaryCode, originRisk);
                    }
                    arrayList.add(dictionaryCode);
                    objects.add(new RiskDTO(dictionaryCode, hazards.getDictionaryName()));
                }
                if (ObjectUtils.isNotEmpty(otherReasonMap)) {
                    Set<String> collect = otherReasonMap.keySet().stream().sorted().collect(Collectors.toSet());
                    for (String s : collect) {
                        List<String> value = otherReasonMap.get(s);
                        List<Harm> harms = harmRepository.findAllById(value);
                        for (Harm harm : harms) {
                            objects.add(new RiskDTO(s, harm.getHarmName()));
                        }
                    }
                }
                List<String> collect = arrayList.stream().sorted().collect(Collectors.toList());
                String itamCode = String.join(",", collect);
//                List<String> collect1 = objects.stream().sorted(Comparator.comparing(RiskDTO::getCode)).map(RiskDTO::getName).collect(Collectors.toList());
                List<String> strings1 = Arrays.asList("其他粉尘", "其他化学有害因素", "其他物理有害因素", "其他生物有害因素", "其他", "其他特殊作业", "其他因素");
                LinkedHashSet<String> riskNames = new LinkedHashSet<>();
                for (String s : collect) {
                    for (RiskDTO object : objects) {
                        if (object.getCode().equals(s) && !strings1.contains(object.getName())) {
                            riskNames.add(object.getName());
                        }
                    }
                }
                String itamName = String.join(",", riskNames);

                //危害因素
                careerhistorylistdto.setItamName(itamName);
                careerhistorylistdto.setItamCode(itamCode);

                careerhistorylistdto.setProtectiveMeasures("无");
                careerhistorylistdto.setOperatetype("Add");
                careerhistorylistdto.setRadiationType("");
                careerhistorylistdto.setDailyWorkload("");
                careerhistorylistdto.setCumulativeExposure("");
                careerhistorylistdto.setExcessiveExposure("");
                careerhistorylistdto.setRemarks("");
                careerhistorylistdto.setSystemTime(dataDTO.getSystemTime());


                careerHistoryList1.add(careerhistorylistdto);
            }
            dataDTO.setCareerHistoryList(careerHistoryList1);
        } else {
            ArrayList<RDataDTO.DataDTO.CAREERHISTORYLISTDTO> careerHistoryList = new ArrayList<>();
            RDataDTO.DataDTO.CAREERHISTORYLISTDTO careerhistorylistdto = new RDataDTO.DataDTO.CAREERHISTORYLISTDTO();
            careerhistorylistdto.setDocumentid(dataDTO.getDocumentid());
            careerhistorylistdto.setPhyexamcode(dataDTO.getPhyexamcode());
            careerhistorylistdto.setPhyexamname(dataDTO.getPhyexamname());
            careerhistorylistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            careerhistorylistdto.setExamNum(dataDTO.getExamNum());
            careerHistoryList.add(careerhistorylistdto);
            dataDTO.setCareerHistoryList(careerHistoryList);
        }
    }

    private void setExamConclusionList(RDataDTO.DataDTO dataDTO, Peispatient peispatient, List<ConclusionDto> conclusionDtos, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner stringBuilder) {

        StringJoiner joiner = new StringJoiner(",");
        List<RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO> examConclusionList = new ArrayList<>();
        String peispatientJhys = peispatient.getJhys();
//        if (ObjectUtils.isEmpty(peispatientJhys)) {
//            return;
//        }
        //健康体检表里的危害因素的编码和数量，必须和危害因素表里的危害因素编码值、数量前后一致对应。
        String[] jhys = peispatientJhys.split(",");
        Set<String> strings = conclusionDtos.stream().map(ConclusionDto::getOccupationDiagnosisCode).filter(ObjectUtils::isNotEmpty).collect(Collectors.toSet());
        Set<String> originRiskList = new LinkedHashSet<>();
        //key 匹配后的接害因素id    value体检者原始的接害因素id  总检的结论都是用的体检者原始的接害因素
        Map<String,String> harmMap=new HashMap<>();
        if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
            for (String jhy : jhys) {
                List<String> collect = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(jhy)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                if (ObjectUtils.isNotEmpty(collect)) {
                    originRiskList.addAll(collect);
                    for(String c:collect){
                        harmMap.put(c,jhy);
                    }
                } else {
                    originRiskList.add(jhy);
                }
            }
        } else {
            originRiskList.addAll(Arrays.asList(jhys));
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (String originRisk : originRiskList) {
            BaseDictionaryDto hazards = dictionaryCache.getHazards(originRisk);
            if (ObjectUtils.isEmpty(hazards)) {
                continue;
            }
            arrayList.add(originRisk);
        }
        arrayList=new ArrayList<>(Arrays.asList(specialTreatmentService.treatJhys(arrayList.toArray(new String[arrayList.size()]))));
        for (String s : arrayList) {
            RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO examconclusionlistdto = new RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO();
            BaseDictionaryDto hazards = dictionaryCache.getHazards(s);
            if (ObjectUtils.isNotEmpty(hazards.getDictionaryCode()) && ObjectUtils.isNotEmpty(hazards.getDictionaryName())) {
                examconclusionlistdto.setItamCode(hazards.getDictionaryCode());
                examconclusionlistdto.setItamCode(SpecialTreatmentUtil.treatItamCode(examconclusionlistdto.getItamCode()));
                //如果是其他危害因素，取具体的危害因素名称
                examconclusionlistdto.setItamName(otherRiskReason.contains(examconclusionlistdto.getItamCode())?dataDTO.getFactorOther():hazards.getDictionaryName());
            } else {
                continue;
            }
            examconclusionlistdto.setDocumentid(dataDTO.getDocumentid());
            examconclusionlistdto.setPhyexamname(dataDTO.getPhyexamname());
            examconclusionlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
            examconclusionlistdto.setExamNum(dataDTO.getExamNum());
            String jhy=StrUtil.isEmpty(harmMap.get(s))?s:harmMap.get(s);
            ConclusionDto conclusionDto1 = conclusionDtos.stream().filter(conclusionDto -> conclusionDto.getOccupationDiagnosis().equals(jhy)).findFirst().orElse(null);
            if (ObjectUtils.isEmpty(conclusionDto1)) {
                examconclusionlistdto.setExamConclusionCode("01");
            } else {
//            todo 测试替换 结论缺失
//            examconclusionlistdto1.setExamConclusionCode("01");
                //2024-06-13
//                    String id = conclusionDto1.getSummaryId();
                String id=conclusionDto1.getOccupationSummary();
                if ("7".equals(id) || "8".equals(id) || "9".equals(id) || "10".equals(id)) {
                    if (ObjectUtils.isEmpty(conclusionDto1.getVerdict()) || "无".equals(conclusionDto1.getVerdict())) {
                        id = "1";
                    } else {
                        id = "5";
                    }
                }
                if (ObjectUtils.isEmpty(id)) {
                    examconclusionlistdto.setExamConclusionCode("01");
                } else {
                    BaseDictionaryDto conclusionCode = dictionaryCache.getConclusionCode(id);
                    String examConclusionCode = conclusionCode.getDictionaryCode();
                    examconclusionlistdto.setExamConclusionCode(examConclusionCode);
                    if (ObjectUtils.isNotEmpty(examConclusionCode)) {
                        if ("03".equals(examConclusionCode)) {
                            BaseDictionaryDto diseases = strings.stream().map(dictionaryCache::getDiseases).filter(ObjectUtils::isNotEmpty).findFirst().orElseThrow(() -> new ErrorRuntimeException("职业病错误，或者为空"));
//                                BaseDictionaryDto diseases = dictionaryCache.getDiseases(conclusionDto1.getOccupationDiagnosisCode());
                            if (ObjectUtils.isEmpty(diseases)) {
                                String message = "职业病错误，或者为空";
                                joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                            }
                            examconclusionlistdto.setYszybCode(ObjectUtils.isNotEmpty(diseases.getDictionaryCode()) ? diseases.getDictionaryCode() : "无");
                        } else if ("04".equals(examConclusionCode)) {
                            String diagnosis = conclusionDto1.getDiagnosis();
                            if (ObjectUtils.isEmpty(diagnosis)) {
                                String message = "职业禁忌症不能为空";
                                joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                            }
                            examconclusionlistdto.setZyjjzName(ObjectUtils.isNotEmpty(diagnosis) ? diagnosis : "无");
                        } else if ("05".equals(examConclusionCode)) {
//                                String qtjbName = conclusionDto1.getVerdict().replace("[\\s|\\\n]", "");
                            String qtjbName = conclusionDto1.getPosistive().replace("[\\s|\\\n]", "").replaceAll("\\\\n","").trim();
                            if (ObjectUtils.isEmpty(qtjbName)) {
                                String message = "其他疾病不能为空";
                                joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                            }
                            examconclusionlistdto.setQtjbName(ObjectUtils.isNotEmpty(qtjbName) ? qtjbName : "无");
                        } else if ("02".equals(examConclusionCode)) {
                            //有最终结论时，isreview=0
//                            dataDTO.setIsReview("1");

                            //复查只上传第一次体检,复查类型的都属于体检过程，不用上传
                            List<ConclusionDto> finalCons=conclusionRepository.queryFinalByPatientCode(peispatient.getPatientcode(),s);
                            if(finalCons.size()==0){
                                finalCons=conclusionRepository.queryByFirstPatientCode(peispatient.getPatientcode(),s);
                            }
                            //如果下了最终结论,上传
                            if(finalCons.size()>0){
                                ConclusionDto finalCon=finalCons.get(0);
                                String finalId= finalCon.getOccupationSummary();
                                BaseDictionaryDto finalConclusionCode = dictionaryCache.getConclusionCode(finalId);
                                String finalExamConclusionCode = finalConclusionCode.getDictionaryCode();
                                examconclusionlistdto.setExamConclusionCode(finalExamConclusionCode);
                                if ("03".equals(finalExamConclusionCode)) {
//                                    BaseDictionaryDto diseases = strings.stream().map(dictionaryCache::getDiseases).filter(ObjectUtils::isNotEmpty).findFirst().orElseThrow(() -> new ErrorRuntimeException("职业病错误，或者为空"));
                                    BaseDictionaryDto diseases = dictionaryCache.getDiseases(finalCon.getOccupationDiagnosisCode());
                                    if (ObjectUtils.isEmpty(diseases)) {
                                        String message = "职业病错误，或者为空";
                                        joiner.add(message);
                                    }
                                    examconclusionlistdto.setYszybCode(ObjectUtils.isNotEmpty(diseases.getDictionaryCode()) ? diseases.getDictionaryCode() : "无");
                                } else if ("04".equals(finalExamConclusionCode)) {
                                    String diagnosis = finalCon.getDiagnosis();
                                    if (ObjectUtils.isEmpty(diagnosis)) {
                                        String message = "职业禁忌症不能为空";
                                        joiner.add(message);
                                    }
                                    examconclusionlistdto.setZyjjzName(ObjectUtils.isNotEmpty(diagnosis) ? diagnosis : "无");
                                } else if ("05".equals(finalExamConclusionCode)) {
                                    String qtjbName = conclusionDto1.getPosistive().replace("[\\s|\\\n]", "").replaceAll("\\\\n","").trim();
                                    if (ObjectUtils.isEmpty(qtjbName)) {
                                        String message = "其他疾病不能为空";
                                        joiner.add(message);
                                    }
                                    examconclusionlistdto.setQtjbName(ObjectUtils.isNotEmpty(qtjbName) ? qtjbName : "无");
                                }
                            }else{
                                //如果总检10天后，即使没有最终结论，也上传
                                //因为现在要赋码，所以不需要十天就可以上传
                                Date dateregisternotime=peispatient.getDateregisternotime();
                                if(dateregisternotime!=null&&DateUtil.between(dateregisternotime,new Date(), DateUnit.DAY)>=10){
                                    log.info(examconclusionlistdto.getItamName()+"缺少最终结论，但总检后已超过10天，继续上传");
                                    dataDTO.setIsReview("1");
                                }else{
                                    String message = examconclusionlistdto.getItamName()+"缺少最终结论";
                                    joiner.add(message);
                                    throw new ErrorRuntimeException(message);
                                }
                            }
                        }
                    } else {
//                        examconclusionlistdto.setExamConclusionCode("01");
                        String message = "结论错误";
                        joiner.add(message);
                        throw new ErrorRuntimeException(message);
                    }
                }
            }
            examconclusionlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
            examconclusionlistdto.setOperatetype("Add");
            examConclusionList.add(examconclusionlistdto);
        }
        examConclusionList = examConclusionList.stream().sorted(Comparator.comparing(RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO::getItamCode)).collect(Collectors.toList());
        dataDTO.setExamConclusionList(examConclusionList);
        LinkedHashSet<String> strings1 = new LinkedHashSet<>(Arrays.asList(joiner.toString().split(",")));
        for (String s : strings1) {
            stringBuilder.add(s);
        }
    }

    private void setExamConclusionList(RDataDTO.DataDTO dataDTO, Peispatient peispatient, Peispatient reviewPeipatient, List<ConclusionDto> conclusionDtos, List<ConclusionDto> reviewConclusionDtos, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner stringBuilder) {
        StringJoiner joiner = new StringJoiner(",");
        List<RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO> examConclusionList = new ArrayList<>();
        String peispatientJhys = peispatient.getJhys();
        String reviewPeipatientJhys = reviewPeipatient.getJhys();
//        todo 接害因素
        if (ObjectUtils.isEmpty(peispatientJhys) && ObjectUtil.isEmpty(reviewPeipatientJhys)) {
            return;
        }
        String[] jhys = peispatientJhys.split(",");
        String[] reviewJhys = reviewPeipatientJhys.split(",");
        Set<String> strings = conclusionDtos.stream().map(ConclusionDto::getOccupationDiagnosisCode).filter(ObjectUtils::isNotEmpty).collect(Collectors.toSet());
        HashSet<String> strings2 = new HashSet<>();
        for (String jhy : jhys) {
            for (String reviewJhy : reviewJhys) {
                boolean isReview = false;
                if (reviewJhy.equals(jhy)) {
                    isReview = true;
                }
                if (isReview) {
                    dataDTO.setIsReview("1");
                }
                HashSet<String> set = new HashSet<>();
                if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                    List<String> collect = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(jhy)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                    if (ObjectUtils.isNotEmpty(collect)) {
                        set.addAll(collect);
                    } else {
                        set.add(jhy);
                    }
                } else {
                    set.add(jhy);
                }
                for (String s : set) {
                    RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO examconclusionlistdto = new RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO();
                    BaseDictionaryDto hazards = dictionaryCache.getHazards(s);
                    if (ObjectUtils.isEmpty(hazards)){
                        Harm harms = harmRepository.getOne(s);
                        throw new ErrorRuntimeException("未维护危害因素:" + harms.getHarmName() + ",请到省市级平台数据对照维护！");
                    }
                    if (ObjectUtils.isNotEmpty(hazards.getDictionaryCode()) && ObjectUtils.isNotEmpty(hazards.getDictionaryName())) {
                        examconclusionlistdto.setItamCode(hazards.getDictionaryCode());
                        //如果是其他危害因素，取具体的危害因素名称
                        examconclusionlistdto.setItamName(otherRiskReason.contains(examconclusionlistdto.getItamCode())?dataDTO.getFactorOther():hazards.getDictionaryName());
                    } else {
                        continue;
                    }
                    examconclusionlistdto.setDocumentid(dataDTO.getDocumentid());
                    examconclusionlistdto.setPhyexamname(dataDTO.getPhyexamname());
                    examconclusionlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                    examconclusionlistdto.setExamNum(dataDTO.getExamNum());
                    ConclusionDto conclusionDto1;
                    if (!isReview) {
                        conclusionDto1 = conclusionDtos.stream().filter(conclusionDto -> conclusionDto.getOccupationDiagnosis().equals(jhy)).findFirst().orElse(null);
                    } else {
                        conclusionDto1 = conclusionDtos.stream().filter(conclusionDto -> conclusionDto.getOccupationDiagnosis().equals(jhy)).findFirst().orElse(null);
                    }
                    if (ObjectUtils.isEmpty(conclusionDto1)) {
                        //未见异常
                        examconclusionlistdto.setExamConclusionCode("01");
                    } else {
                        //            todo 测试替换 结论缺失
                        //            examconclusionlistdto1.setExamConclusionCode("01");
                        String id = conclusionDto1.getSummaryId();
                        if ("7".equals(id) || "8".equals(id) || "9".equals(id) || "10".equals(id)) {
                            if (ObjectUtils.isEmpty(conclusionDto1.getVerdict()) || "无".equals(conclusionDto1.getVerdict())) {
                                id = "1";
                            } else {
                                id = "5";
                            }
                        }
                        if (ObjectUtils.isEmpty(id)) {
                            examconclusionlistdto.setExamConclusionCode("01");
                        } else {
                            BaseDictionaryDto conclusionCode = dictionaryCache.getConclusionCode(id);
                            String examConclusionCode = conclusionCode.getDictionaryCode();
                            examconclusionlistdto.setExamConclusionCode(examConclusionCode);
                            if (ObjectUtils.isNotEmpty(examConclusionCode)) {
                                if ("03".equals(examConclusionCode)) {
                                    BaseDictionaryDto diseases = strings.stream().map(dictionaryCache::getDiseases).filter(ObjectUtils::isNotEmpty).findFirst().orElseThrow(() -> new ErrorRuntimeException("职业病错误，或者为空"));
                                    //                                BaseDictionaryDto diseases = dictionaryCache.getDiseases(conclusionDto1.getOccupationDiagnosisCode());
                                    if (ObjectUtils.isEmpty(diseases)) {
                                        String message = "职业病错误，或者为空";
                                        joiner.add(message);
                                        //                                    throw new ErrorRuntimrException(message);
                                    }
                                    examconclusionlistdto.setYszybCode(ObjectUtils.isNotEmpty(diseases.getDictionaryCode()) ? diseases.getDictionaryCode() : "无");
                                } else if ("04".equals(examConclusionCode)) {
                                    String diagnosis = conclusionDto1.getDiagnosis();
                                    if (ObjectUtils.isEmpty(diagnosis)) {
                                        String message = "职业禁忌症不能为空";
                                        joiner.add(message);
                                        //                                    throw new ErrorRuntimrException(message);
                                    }
                                    examconclusionlistdto.setZyjjzName(ObjectUtils.isNotEmpty(diagnosis) ? diagnosis : "无");
                                } else if ("05".equals(examConclusionCode)) {
                                    String qtjbName = conclusionDto1.getVerdict().replace("[\\s|\\\n]", "").replaceAll("\\\\n","").trim();
                                    if (ObjectUtils.isEmpty(qtjbName)) {
                                        String message = "其他疾病不能为空";
                                        joiner.add(message);
                                        //                                    throw new ErrorRuntimrException(message);
                                    }
                                    examconclusionlistdto.setQtjbName(ObjectUtils.isNotEmpty(qtjbName) ? qtjbName : "无");
                                }
//                                else if ("02".equals(examConclusionCode)) {
//                                    dataDTO.setIsReview("1");
//                                }
                            } else {
                                //                        examconclusionlistdto.setExamConclusionCode("01");
                                String message = "结论错误";
                                joiner.add(message);
                                throw new ErrorRuntimeException(message);
                            }
                        }
                    }
                    examconclusionlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                    examconclusionlistdto.setOperatetype("Add");
                    if (strings2.add(examconclusionlistdto.getItamCode() + "-" + examconclusionlistdto.getItamName())) {
                        examConclusionList.add(examconclusionlistdto);
                    }
                }
            }

        }

        examConclusionList = examConclusionList.stream().sorted(Comparator.comparing(RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO::getItamCode)).collect(Collectors.toList());
        dataDTO.setExamConclusionList(examConclusionList);
        LinkedHashSet<String> strings1 = new LinkedHashSet<>(Arrays.asList(joiner.toString().split(",")));
        for (String s : strings1) {
            stringBuilder.add(s);
        }
    }

    private void setExamConclusionList(RDataDTO.DataDTO dataDTO, Peispatient peispatient, Peispatient reviewPeipatient, List<ConclusionDto> conclusionDtos, List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner stringJoiner) {
        StringJoiner joiner = new StringJoiner(",");
        List<RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO> examConclusionList = new ArrayList<>();
        String peispatientJhys = peispatient.getJhys();
//        todo 接害因素
        if (ObjectUtils.isEmpty(peispatientJhys)) {
            return;
        }
        String[] jhys = peispatientJhys.split(",");
        Set<String> strings = conclusionDtos.stream().map(ConclusionDto::getOccupationDiagnosisCode).filter(ObjectUtils::isNotEmpty).collect(Collectors.toSet());
        for (String jhy : jhys) {
            HashSet<String> set = new HashSet<>();
            if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
                List<String> collect = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(jhy)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                if (ObjectUtils.isNotEmpty(collect)) {
                    set.addAll(collect);
                } else {
                    set.add(jhy);
                }
            } else {
                set.add(jhy);
            }
            for (String s : set) {
                RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO examconclusionlistdto = new RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO();
                BaseDictionaryDto hazards = dictionaryCache.getHazards(s);
                if (ObjectUtils.isNotEmpty(hazards.getDictionaryCode()) && ObjectUtils.isNotEmpty(hazards.getDictionaryName())) {
                    examconclusionlistdto.setItamCode(hazards.getDictionaryCode());
                    //如果是其他危害因素，取具体的危害因素名称
                    examconclusionlistdto.setItamName(otherRiskReason.contains(examconclusionlistdto.getItamCode())?dataDTO.getFactorOther():hazards.getDictionaryName());
                } else {
                    continue;
                }
                examconclusionlistdto.setDocumentid(dataDTO.getDocumentid());
                examconclusionlistdto.setPhyexamname(dataDTO.getPhyexamname());
                examconclusionlistdto.setPhyexamcode(dataDTO.getPhyexamcode());
                examconclusionlistdto.setExamNum(peispatient.getPatientcode());
                ConclusionDto conclusionDto1 = conclusionDtos.stream().filter(conclusionDto -> conclusionDto.getOccupationDiagnosis().equals(jhy)).findFirst().orElse(null);
                if (ObjectUtils.isEmpty(conclusionDto1)) {
                    examconclusionlistdto.setExamConclusionCode("01");
                } else {
//            todo 测试替换 结论缺失
//            examconclusionlistdto1.setExamConclusionCode("01");
                    String id = conclusionDto1.getSummaryId();
                    if ("7".equals(id) || "8".equals(id) || "9".equals(id) || "10".equals(id)) {
                        if (ObjectUtils.isEmpty(conclusionDto1.getVerdict()) || "无".equals(conclusionDto1.getVerdict())) {
                            id = "1";
                        } else {
                            id = "5";
                        }
                    }
                    if (ObjectUtils.isEmpty(id)) {
                        examconclusionlistdto.setExamConclusionCode("01");
                    } else {
                        BaseDictionaryDto conclusionCode = dictionaryCache.getConclusionCode(id);
                        String examConclusionCode = conclusionCode.getDictionaryCode();
                        examconclusionlistdto.setExamConclusionCode(examConclusionCode);
                        if (ObjectUtils.isNotEmpty(examConclusionCode)) {
                            if ("03".equals(examConclusionCode)) {
                                BaseDictionaryDto diseases = strings.stream().map(dictionaryCache::getDiseases).filter(ObjectUtils::isNotEmpty).findFirst().orElseThrow(() -> new ErrorRuntimeException("职业病错误，或者为空"));
//                                BaseDictionaryDto diseases = dictionaryCache.getDiseases(conclusionDto1.getOccupationDiagnosisCode());
                                if (ObjectUtils.isEmpty(diseases)) {
                                    String message = "职业病错误，或者为空";
                                    joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                                }
                                examconclusionlistdto.setYszybCode(ObjectUtils.isNotEmpty(diseases.getDictionaryCode()) ? diseases.getDictionaryCode() : "无");
                            } else if ("04".equals(examConclusionCode)) {
                                String diagnosis = conclusionDto1.getDiagnosis();
                                if (ObjectUtils.isEmpty(diagnosis)) {
                                    String message = "职业禁忌症不能为空";
                                    joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                                }
                                examconclusionlistdto.setZyjjzName(ObjectUtils.isNotEmpty(diagnosis) ? diagnosis : "无");
                            } else if ("05".equals(examConclusionCode)) {
                                String qtjbName = conclusionDto1.getVerdict().replace("[\\s|\\\n]", "").replaceAll("\\\\n","").trim();
                                if (ObjectUtils.isEmpty(qtjbName)) {
                                    String message = "其他疾病不能为空";
                                    joiner.add(message);
//                                    throw new ErrorRuntimrException(message);
                                }
                                examconclusionlistdto.setQtjbName(ObjectUtils.isNotEmpty(qtjbName) ? qtjbName : "无");
                            } else if ("02".equals(examConclusionCode)) {
                                dataDTO.setIsReview("1");
                            }
                        } else {
//                        examconclusionlistdto.setExamConclusionCode("01");
                            String message = "结论错误";
                            joiner.add(message);
                            throw new ErrorRuntimeException(message);
                        }
                    }
                }
                examconclusionlistdto.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
                examconclusionlistdto.setOperatetype("Add");
                examConclusionList.add(examconclusionlistdto);
            }

        }
        examConclusionList = examConclusionList.stream().sorted(Comparator.comparing(RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO::getItamCode)).collect(Collectors.toList());
        dataDTO.setExamConclusionList(examConclusionList);
        LinkedHashSet<String> strings1 = new LinkedHashSet<>(Arrays.asList(joiner.toString().split(",")));
        for (String s : strings1) {
            stringJoiner.add(s);
        }
    }

    private void setExamTypeCode(RDataDTO.DataDTO dataDTO, Peispatient peispatient) {
        switch (peispatient.getMedicaltype()) {
            case "0":
                dataDTO.setExamTypeCode("01");
                break;
            case "1":
                dataDTO.setExamTypeCode("02");
                break;
            case "2":
                dataDTO.setExamTypeCode("03");
                break;
            case "3":
                dataDTO.setExamTypeCode("05");
                break;
            case "4":
                dataDTO.setExamTypeCode("04");
                break;
        }
    }

    private void setNation(RDataDTO.DataDTO dataDTO, Peispatient peispatient) {
        String idNation = peispatient.getIdNation();
        if (ObjectUtils.isEmpty(idNation)) {
            dataDTO.setNation("汉族");
            dataDTO.setNationCode("1");
        } else {
            Nation nation = nationCache.get(idNation);
            if (nation != null) {
                dataDTO.setNation(nation.getName());
                dataDTO.setNationCode(nation.getNumberCode().startsWith("0") ? nation.getNumberCode().substring(1) : nation.getNumberCode());
            } else {
                dataDTO.setNation("汉族");
                dataDTO.setNationCode("1");
            }

        }
    }

    private void setGenderCode(RDataDTO.DataDTO dataDTO, String idSex) {
        switch (idSex) {
            case "0":
                dataDTO.setGenderCode("1");//男
                break;
            case "1":
                dataDTO.setGenderCode("2");//女
                break;
        }
    }


    public Result upload(RDataDTO dto, String token) throws JsonProcessingException, ErrorRuntimeException {
//        return requestService.requestPostJson(properties.getRequestUrl() + "/index/HealthProvince/receiveHealth", dto);
        log.error("开始上传");
        log.error("上传参数：{}", JSON.toJSONString(dto));
        HashMap<String, String> params = new HashMap<>();
//        params.put("token", token);
        String value = objectMapper.writeValueAsString(dto);
        log.error("value = " + value);
        params.put("content", value);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", token);
        Result result = requestService.post(properties.getRequestUrl() + "/index/HealthProvince/receiveHealth", params, headers);
//        Result result = new Result();
        log.error("上传结果" + result);
        StringJoiner stringJoiner = new StringJoiner(",");
        String code = result.getCode();
        if ("200".equals(code)) {
            //上传胶州平台
            if (jzjkProperties.getOpen()){
                log.error("开始上传胶州平台");
                String jzToken = loginService.getJzToken();
                String msg = requestService.doPostForm(jzjkProperties.getUrl() + "/index/HealthProvince/receiveHealth", params, jzToken);
                log.error("胶州平台上传结果" + msg);
            }
            return result;
        } else if ("202".equals(code)) {
            throw new ErrorRuntimeException(result.getCode(), result.getMsg(), result.getInfo());
        } else {
            List<Info> infoDTO = result.getInfo();
            throw new ErrorRuntimeException(infoDTO);
        }



//        return result;
    }


    private void setIdCardTypeCode(RDataDTO.DataDTO dataDTO, Peispatient peispatient) {
        if (ObjectUtils.isNotEmpty(peispatient.getCountreportoccupationxml())) {
            switch (peispatient.getCountreportoccupationxml().intValue()) {
                case 1:
                    dataDTO.setIdCardTypeCode("01");
                    break;
                case 2:
                    dataDTO.setIdCardTypeCode("03");
                    break;
                case 3:
                    dataDTO.setIdCardTypeCode("04");
                    break;
                case 6:
                    dataDTO.setIdCardTypeCode("06");
                    break;
            }
        } else {
//            throw new ErrorRuntimrException("证件类型缺失");
            dataDTO.setIdCardTypeCode("01");
        }
    }

    /**
     * 复查与非复查共用
     * @param dataDTO
     * @param peispatient
     * @param sellcustomer
     * @param harmPackageMatchDtos
     * @param personJoiner 记录个人信息错误原因
     * @param enterPriceJoiner 记录企业信息错误原因
     * @param otherRiskReason
     * @param prePeispatient 如果是复查，这是上一次体检者。如果不是复查，这是Null
     */
    private void setHealthPhysicalReport(RDataDTO.DataDTO dataDTO, Peispatient peispatient, Sellcustomer sellcustomer
            , List<HarmPackageMatchDto> harmPackageMatchDtos, StringJoiner personJoiner, StringJoiner enterPriceJoiner
            , List<String> otherRiskReason,Peispatient prePeispatient) {
        dataDTO.setPhysicaltypes("1");
        dataDTO.setDocumentid(properties.getInstitutionCode() + DateUtil.format(peispatient.getDateregister(), "yyyyMMdd") + peispatient.getPatientcode());
        dataDTO.setPhyexamcode(properties.getInstitutionCode());
        dataDTO.setPhyexamname(properties.getInstitutionName());
        dataDTO.setPhyexamaddressCode(properties.getPhyexamaddressCode());
        dataDTO.setPhyexamaddressName(properties.getPhyexamaddressName());
        dataDTO.setExamDate(DateUtil.format(peispatient.getDateregister(), "yyyyMMdd"));
        dataDTO.setExamNum(peispatient.getPatientcode());
        dataDTO.setWorkerName(peispatient.getPatientname());
        //        身份证：1，护照：2，军人证：  6港澳通行证/回乡证或台胞证
        setIdCardTypeCode(dataDTO, peispatient);
        if (ObjectUtils.isNotEmpty(peispatient.getIdcardno())) {
            dataDTO.setIdCard(peispatient.getIdcardno());
        } else {
            String message = "证件号码缺失";
            personJoiner.add(message);
        }
        setGenderCode(dataDTO, peispatient.getIdSex());
        dataDTO.setBirthDate(DateUtil.format(peispatient.getBirthdate(), "yyyyMMdd"));
        int age = getAgeByIdCard(peispatient.getIdcardno(), peispatient.getDateregister());
        dataDTO.setAge(String.valueOf(age));
        setNation(dataDTO, peispatient);
        if (ObjectUtils.isEmpty(peispatient.getPhone())) {
            String message = "体检人联系电话错误";
            personJoiner.add(message);
        }else{
            dataDTO.setWorkerTelphone(peispatient.getPhone());
        }
        setExamTypeCode(dataDTO, peispatient);
        String[] strings = peispatient.getJhys().split(",");
        HashMap<String, List<String>> otherReasonMap = new HashMap<>();
        Set<String> originRiskList = new LinkedHashSet<>();
        if (ObjectUtils.isNotEmpty(harmPackageMatchDtos)) {
            for (String string : strings) {
                List<String> collect = harmPackageMatchDtos.stream().filter(harmPackageMatchDto -> harmPackageMatchDto.getPharmId().equals(string)).map(harmPackageMatchDto -> harmPackageMatchDto.getHarmId().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
                if (ObjectUtils.isNotEmpty(collect)) {
                    originRiskList.addAll(collect);
                } else {
                    originRiskList.add(string);
                }
            }
        } else {
            originRiskList.addAll(Arrays.asList(strings));
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (String originRisk : originRiskList) {
            BaseDictionaryDto hazards = dictionaryCache.getHazards(originRisk);
            if (ObjectUtils.isEmpty(hazards)) {
                continue;
            }
            String dictionaryCode = hazards.getDictionaryCode();
            if (ObjectUtils.isNotEmpty(otherRiskReason) && otherRiskReason.contains(dictionaryCode)) {
                setOtherReason(otherReasonMap, dictionaryCode, originRisk);
            }
            arrayList.add(dictionaryCode);
        }
        arrayList=specialTreatmentService.treatJhys(arrayList);
        String jhys = arrayList.stream().sorted().collect(Collectors.joining(","));
        dataDTO.setFactorCode(jhys);
        if (ObjectUtils.isNotEmpty(otherReasonMap)) {
            StringJoiner stringJoiner = new StringJoiner(",");
            Set<String> collect = otherReasonMap.keySet().stream().sorted().collect(Collectors.toSet());
            for (String s : collect) {
                List<String> value = otherReasonMap.get(s);
                List<Harm> harms = harmRepository.findAllById(value);
                for (Harm harm : harms) {
                    stringJoiner.add(harm.getHarmName());
                }
            }
            dataDTO.setFactorOther(stringJoiner.toString());
        }
        if (ObjectUtils.isNotEmpty(sellcustomer.getLicenseName())) {
            dataDTO.setEnterpriseName(sellcustomer.getLicenseName());
        } else {
            String message = "用人单位名称缺失";
            enterPriceJoiner.add(message);
        }
//        统一征信代码
        if (ObjectUtils.isEmpty(sellcustomer.getSocialCreditCode())) {
            String message = "用人单位企业统一征信代码缺失";
            enterPriceJoiner.add(message);
        } else {
            dataDTO.setCreditCode(sellcustomer.getSocialCreditCode());
        }
        BaseDictionaryDto economyType = dictionaryCache.getEconomyType(sellcustomer.getEconomyCode());
        if (ObjectUtils.isNotEmpty(economyType)) {
            dataDTO.setEconomicTypeCode(economyType.getShandongCode());
        } else {
            dataDTO.setEconomicTypeCode("129");
        }
        dataDTO.setIndustryCategoryCode(sellcustomer.getIndusTypeCode());
        if (ObjectUtils.isEmpty(dataDTO.getIndustryCategoryCode())) {
            String message = "用人单位行业类别缺失";
            enterPriceJoiner.add(message);
        }
        setQygmyr(dataDTO, sellcustomer, enterPriceJoiner);
        String street = sellcustomer.getStreet();
        if (StringUtils.isEmpty(street)) {
            String message = "用人单位企业所属地区代码错误";
            enterPriceJoiner.add(message);
        } else {
            String qingdaoCode = zoneCache.getQingdaoCode(street);
            dataDTO.setAddressCode(qingdaoCode);
        }

        String fullName = zoneCache.getFullName(sellcustomer.getStreet());
        if (ObjectUtils.isEmpty(fullName)) {
            String message = "用人单位企业所属地区名称错误";
            enterPriceJoiner.add(message);
        } else {
            String addressName = fullName.contains("山东省") ? fullName : "山东省" + fullName;
            //潍坊以3707开头
            if (sellcustomer.getStreet().startsWith("3707")){
                addressName = addressName.contains("潍坊市") ? addressName : addressName.replaceAll("山东省", "山东省潍坊市");
            }else {
                addressName = addressName.contains("青岛市") ? addressName : addressName.replaceAll("山东省", "山东省青岛市");
            }
            dataDTO.setAddressName(addressName);
        }
        if (ObjectUtils.isNotEmpty(sellcustomer.getKhdwzcdz())) {
            dataDTO.setAddressDetail(sellcustomer.getKhdwzcdz());
        } else {
            String message = "用人单位详细地址错误";
            enterPriceJoiner.add(message);
        }
//        详细地址
        if (ObjectUtils.isNotEmpty(sellcustomer.getKhdwlxr())) {
            dataDTO.setEnterpriseContact(sellcustomer.getKhdwlxr());
        } else {
            String message = "用人单位联系人错误";
            enterPriceJoiner.add(message);
        }
        if (ObjectUtils.isNotEmpty(sellcustomer.getKhdh())) {
            dataDTO.setContactTelphone(sellcustomer.getKhdh());
        } else {
            String message = "用人单位联系电话错误";
            enterPriceJoiner.add(message);
        }
        String unitarea = sellcustomer.getUnitarea();
        if (ObjectUtils.isNotEmpty(unitarea)) {
            BaseZoneQd baseZoneQd=baseZoneQdRepository.findByZoneCode(unitarea);
            if(baseZoneQd==null){
                String message = "用人单位所在区id不存在，请联系管理员检查MD_BASE_ZONE_QD表数据";
                enterPriceJoiner.add(message);
            }else{
                String allName=baseZoneQd.getQingdaoZoneName();
                if(StrUtil.isEmpty(allName)){
                    String message = "客户的用人单位所属区全名称，对应的MD_BASE_ZONE_QD表QINGDAO_ZONE_NAME字段为空";
                    enterPriceJoiner.add(message);
                }else{
                    if(StrUtil.isEmpty(baseZoneQd.getQingdaoZoneName())){
                        String message = "用人单位所在区全名称【"+baseZoneQd.getZoneName()+"】无法上传，BASE_ZONE_QD表中没有维护QINGDAO_ZONE_NAME";
                        enterPriceJoiner.add(message);
                    }else{
                        dataDTO.setAllName(baseZoneQd.getQingdaoZoneName());
                    }
                }
            }
        } else {
            String message = "客户没有维护没有维护用人单位所属区全名称";
            enterPriceJoiner.add(message);
//            dataDTO.setAllName("青岛市市辖区黄岛区");
        }
        if (ObjectUtils.isNotEmpty(sellcustomer.getRauSocialCreditCode()) && !sellcustomer.getSocialCreditCode().equals(sellcustomer.getRauSocialCreditCode())) {
            String rauKhdwmc = sellcustomer.getRauKhdwmc();
            if (ObjectUtils.isEmpty(rauKhdwmc)) {
                String message = "用工单位缺失";
                enterPriceJoiner.add(message);
            }else{
                dataDTO.setEnterpriseNameEmployer(rauKhdwmc.replaceAll("\\.", "").trim());
            }
            String rauStreet = sellcustomer.getRauStreet();
            if (ObjectUtils.isEmpty(rauStreet)) {
                String message = "用工单位街道缺失";
                enterPriceJoiner.add(message);
            }else{
                dataDTO.setAllNameEmployer(zoneCache.getFullName(rauStreet));
                String qingdaoCode = zoneCache.getQingdaoCode(rauStreet);
                dataDTO.setAddressCodeEmployer(qingdaoCode);
            }

            dataDTO.setCreditCodeEmployer(sellcustomer.getRauSocialCreditCode());
            BaseDictionaryDto economyType1 = dictionaryCache.getEconomyType(sellcustomer.getRauEconomyCode());
            if (ObjectUtils.isEmpty(economyType1)) {
                String message = "用工单位经济类型缺失";
                enterPriceJoiner.add(message);
            }
            dataDTO.setEconocTypeCodeEmoyer(ObjectUtils.isEmpty(economyType1)? "" : economyType1.getShandongCode());
            dataDTO.setIndtryCatryCodeEmoyer(sellcustomer.getRauIndusTypeCode3());
            setQygm(dataDTO, sellcustomer, enterPriceJoiner);
            String rauUnitarea = sellcustomer.getRauUnitarea();
//            BaseZoneQd baseZoneQd = baseZoneQdRepository.findByZoneCode(rauUnitarea);
//            if (ObjectUtils.isEmpty(baseZoneQd)) {
//                String message = "用工单位所在地全名称缺失";
//                enterPriceJoiner.add(message);
//            }
        } else {
            dataDTO.setAddressCodeEmployer(dataDTO.getAddressCode());
            dataDTO.setEnterpriseNameEmployer(dataDTO.getEnterpriseName());
            dataDTO.setCreditCodeEmployer(dataDTO.getCreditCode());
            dataDTO.setEconocTypeCodeEmoyer(dataDTO.getEconomicTypeCode());
            dataDTO.setIndtryCatryCodeEmoyer(dataDTO.getIndustryCategoryCode());
            dataDTO.setBusssScaleCodeEmoyer(dataDTO.getBusinessScaleCode());
//            String fullName1 = zoneCache.getNameQingDao(dataDTO.getAddressCodeEmployer());
            dataDTO.setAllNameEmployer(dataDTO.getAddressName());
        }
        dataDTO.setWritePerson(properties.getWritePerson());
        dataDTO.setWritePersonTelphone(properties.getWritePersonPhone());
        dataDTO.setWriteDate(DateUtil.format(peispatient.getDateregister(), "yyyyMMdd"));
        dataDTO.setReportOrganCreditCode(properties.getInstitutionName());
        String worktypeId = peispatient.getWorktypeId();
        if (ObjectUtils.isEmpty(worktypeId)) {
            String message = "工种缺失";
            personJoiner.add(message);
            enterPriceJoiner.add(message);
        } else {
            BaseWorktype worktype = dictionaryCache.getWorktype(worktypeId);
            if (worktype == null) {
                String message = "工种名称错误";
                personJoiner.add(message);
                enterPriceJoiner.add(message);
            }else{
                dataDTO.setWorkTypeCode(worktype.getQingdaoCode());
                dataDTO.setWorkType(worktype.getTypeName());
            }
        }
        dataDTO.setJcType("01");
//        if (!"01".equals(dataDTO.getExamTypeCode()) || (ObjectUtils.isNotEmpty(peispatient.getJhgl()) && peispatient.getJhgl() > 0)) {
//            dataDTO.setHarmStartDate(DateUtil.format(peispatient.getDateregister(), "yyyyMMdd"));
//        } else {
        dataDTO.setHarmStartDate(DateUtil.format(peispatient.getHarmDate(), "yyyyMMdd"));
//        }
        Long jhgl = peispatient.getJhgl();
        if (jhgl == null) {
            String message = "接害工龄缺失";
            personJoiner.add(message);
        }else if (jhgl > 12) {
            Long l = jhgl / 12L;
            Long l1 = jhgl % 12;
            dataDTO.setHarmAgeYear(l.toString());
            dataDTO.setHarmAgeMonth(l1.toString());
        } else {
            dataDTO.setHarmAgeYear("0");
            dataDTO.setHarmAgeMonth(jhgl.toString());
        }
        dataDTO.setIsReview("0");
        dataDTO.setReportPerson(properties.getReportPerson());
        dataDTO.setReportPersonTel(properties.getReportPersonPhone());
        Date datefinalexamed = peispatient.getDateregisternotime();
        datefinalexamed = datefinalexamed == null ? new Date() : datefinalexamed;
        dataDTO.setReportDate(DateUtil.format(datefinalexamed, "yyyyMMdd"));
        dataDTO.setContactFactorCode(dataDTO.getFactorCode());
        dataDTO.setContactFactorOther(dataDTO.getFactorOther());
        dataDTO.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        dataDTO.setOperatetype("Add");

        //职业问诊   职业问诊不会复查，如果是上传复查体检号，直接上传上一次的体检号
        PeispatientConsultation peispatientConsultation = peispatientConsultationRepository.findByPatientcode(
                prePeispatient==null?peispatient.getPatientcode():prePeispatient.getPatientcode()
        );
        //如果查不到，查询老系统数据
        if (ObjectUtils.isEmpty(peispatientConsultation)){
            OrPeispatientConsultation orPeispatientConsultation = orPeispatientConsultationRepository.findByPatientcode(prePeispatient==null?peispatient.getPatientcode():prePeispatient.getPatientcode());
            peispatientConsultation = new PeispatientConsultation();
            BeanUtils.copyProperties(orPeispatientConsultation,peispatientConsultation);
        }
        if(peispatientConsultation==null){
            String message = "职业问诊结果缺失";
            personJoiner.add(message);
        }else{
            String abstainSmokeNote=peispatientConsultation.getAbstainSmokeNote();
            if("0".equals(abstainSmokeNote)){
                dataDTO.setSmokingStatus("4");
            }else if("1".equals(abstainSmokeNote)){
                dataDTO.setSmokingStatus("2");
            }else if("2".equals(abstainSmokeNote)){
                dataDTO.setSmokingStatus("3");
            }else if("3".equals(abstainSmokeNote)){
                dataDTO.setSmokingStatus("1");
            }else{
                String message = "目前吸烟情况缺失";
                personJoiner.add(message);
            }

            if(!"3".equals(abstainSmokeNote)){
                dataDTO.setPersonalHistoryYear("0");
                dataDTO.setPersonalHistoryMonth("0");
                dataDTO.setDailySmokingVolume("0");
            }else{
                //吸烟年数,可能有小数,前端有校验必然是数字
                String smokeYear=peispatientConsultation.getSmokeYear();
                if(StrUtil.isBlank(smokeYear)){
                    smokeYear="0";
//                    String message = "吸烟史-年缺失";
//                    personJoiner.add(message);
                }
//                    double doubleYear=Double.parseDouble(smokeYear);
//                    double personalHistoryYear=Math.ceil(doubleYear);
//                    dataDTO.setPersonalHistoryYear((int)personalHistoryYear+"");
//                    double month= NumberUtil.sub(doubleYear,personalHistoryYear);
//                    int personalHistoryMonth=(int)NumberUtil.mul(month,12.0);
//                    dataDTO.setPersonalHistoryMonth(personalHistoryMonth+"");
                double doubleYear=Double.parseDouble(smokeYear.trim());
                double personalHistoryYear=Math.ceil(doubleYear);
                dataDTO.setPersonalHistoryYear((int)personalHistoryYear+"");

                String smokeMonth=peispatientConsultation.getSmokeMonth();
                if(StrUtil.isEmpty(smokeMonth))smokeMonth="0";
                double doubleMonth=Double.parseDouble(smokeMonth.trim());
                double personalHistoryMonth=Math.ceil(doubleMonth);
                dataDTO.setPersonalHistoryMonth((int)personalHistoryMonth+"");


                String everydaySmokeN=peispatientConsultation.getEverydaySmokeN();
                if(StrUtil.isEmpty(everydaySmokeN)){
                    String message = "平均每天吸烟量缺失";
                    personJoiner.add(message);
                }else{
                    dataDTO.setDailySmokingVolume(everydaySmokeN);
                }
            }
        }


    }

    private int getAgeByIdCard(String idcardno, Date dateregister) {
        DateTime birthDate = IdcardUtil.getBirthDate(idcardno);
        return Math.abs(DateUtil.age(birthDate, dateregister));
    }

    private void setOtherReason(Map<String, List<String>> stringListHashMap, String dictionaryCode, String risk) {
        if (stringListHashMap.containsKey(dictionaryCode)) {
            List<String> strings = stringListHashMap.get(dictionaryCode);
            strings.add(risk);
            stringListHashMap.replace(dictionaryCode, strings);
        } else {
            ArrayList<String> value = new ArrayList<>();
            value.add(risk);
            stringListHashMap.put(dictionaryCode, value);
        }
    }

    private void setQygmyr(RDataDTO.DataDTO dataDTO, Sellcustomer sellcustomer, StringJoiner enterPriceJoiner) {
        String businessScaleCode = null;
        if (ObjectUtils.isNotEmpty(sellcustomer.getCrptSizeCode())) {
            if (sellcustomer.getCrptSizeCode().length() > 2) {
                switch (sellcustomer.getCrptSizeCode()) {
                    case "10000":
                        businessScaleCode = "01";
                        dataDTO.setBusinessScaleCode("01");
                        break;
                    case "10001":
                        businessScaleCode = "02";
                        dataDTO.setBusinessScaleCode("02");
                        break;
                    case "10002":
                        businessScaleCode = "03";
                        dataDTO.setBusinessScaleCode("03");
                        break;
                    case "10003":
//                        dataDTO.setBusinessScaleCode("99");
                        String message = "用人单位企业规模代码错误，不能是不详";
                        enterPriceJoiner.add(message);
                        break;
                    case "10004":
                        businessScaleCode = "04";
                        dataDTO.setBusinessScaleCode("04");
                        break;
                }
            } else {
                businessScaleCode = sellcustomer.getCrptSizeCode();
            }
        } else {
            String message = "用人单位企业规模代码错误";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        }
        if (ObjectUtils.isEmpty(businessScaleCode)) {
            String message = "用人单位企业规模代码错误";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        } else if ("99".equals(businessScaleCode)) {
            String message = "用人单位企业规模代码不能为不详";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        }
        dataDTO.setBusinessScaleCode(businessScaleCode);
    }

    private void setQygm(RDataDTO.DataDTO dataDTO, Sellcustomer sellcustomer, StringJoiner enterPriceJoiner) {
        String businessScaleCode = null;
        if (ObjectUtils.isNotEmpty(sellcustomer.getRauQygm())) {
            if (sellcustomer.getCrptSizeCode().length() > 2) {
                switch (sellcustomer.getCrptSizeCode()) {
                    case "10000":
                        businessScaleCode = "01";
                        dataDTO.setBusinessScaleCode("01");
                        break;
                    case "10001":
                        businessScaleCode = "02";
                        dataDTO.setBusinessScaleCode("02");
                        break;
                    case "10002":
                        businessScaleCode = "03";
                        dataDTO.setBusinessScaleCode("03");
                        break;
//                    case "10003":
//                        dataDTO.setBusinessScaleCode("99");
//                        break;
                    case "10004":
                        businessScaleCode = "04";
                        dataDTO.setBusinessScaleCode("04");
                        break;
                }
            } else {
                businessScaleCode = sellcustomer.getCrptSizeCode();
            }
        } else {
            String message = "用工单位企业规模代码错误";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        }
        if (ObjectUtils.isEmpty(businessScaleCode)) {
            String message = "用工单位企业规模代码错误";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        } else if ("99".equals(businessScaleCode)) {
            String message = "用工单位企业规模代码不能为不详";
            enterPriceJoiner.add(message);
//            throw new ErrorRuntimrException(message);
        }
        dataDTO.setBusssScaleCodeEmoyer(businessScaleCode);
    }

    @Override
    public void uploadExcel(String token,String patientcode){
        Peispatient p=peispatientRepository.findByPatientcode(patientcode);
        if("3".equals(p.getIdExamtype())){
            log.info("体检号"+patientcode+"属于复查,不上传");
//
//            Peispatient peispatient = peispatientRepository.findByPatientcode(patientcode);
//            ReviewPatientDTO reviewPatientDTO = new ReviewPatientDTO();
//            reviewPatientDTO.setPatientCode(peispatient.getPatientcode());
//            reviewPatientDTO.setPrePatientCode(peispatient.getInpatientno());
//            logUpload(token, reviewPatientDTO);
        }else{
            logUpload(token, patientcode);
        }
    }

    @Override
    public void uploadExcel(int fileIdx){
        String filePath=fileIdx==1?excelProperties.getPath1():
                fileIdx==2?excelProperties.getPath2():
                        excelProperties.getPath3();
        // 创建一个easypoi使用的配置类
        ImportParams params = new ImportParams();
        // 设置表格坐标
        params.setStartSheetIndex(excelProperties.getSheetIndex());
        // 校验Excel文件，去掉空行
        params.setNeedVerify(true);
        File file=new File(filePath);

        List<SendPatientcode> sendPatientcodes= ExcelImportUtil.importExcel(file, SendPatientcode.class,params);
        String token = loginService.getToken();
        List<String> patientCodes=new ArrayList<>();
        int i=0;
        for(SendPatientcode sendPatientcode:sendPatientcodes){
            patientCodes.add(sendPatientcode.getPatientcode());
            log.info(i+"从excel文件"+file.getPath()+"中读取体检号："+sendPatientcode.getPatientcode());
            i++;
            try{
                uploadExcel(token,sendPatientcode.getPatientcode());
            }catch(Exception e){
                log.info("其他错误",e);
            }
            log.info("从excel文件"+file.getPath()+"中读取体检号结束："+sendPatientcode.getPatientcode());
        }
        log.info("excel获取完毕");
        exportService.export(patientCodes);
    }

    /**
     * 报告赋码
     * @param token
     */
    @Override
    public void reportCoding(String token) {
        log.error("开始报告赋码任务");
        List<NeedReportCodingDataDto> list = qjkMapper.getNeedReportCodingData();
        for (NeedReportCodingDataDto dto : list) {
            String patientCode = dto.getPatientcode();
            log.error("报告赋码开始获取:{}",patientCode);
            String documentid = properties.getInstitutionCode() + DateUtil.format(dto.getDateregister(), "yyyyMMdd") + patientCode;
            log.error("报告赋码开始获取documentid:{}",documentid);
            HashMap<String, String> params = new HashMap<>();
            params.put("documentid", documentid);
            HashMap<String, String> headers = new HashMap<>();
            headers.put("token", token);
            Result result = requestService.post(properties.getRequestUrl() + "/index/FileNo/getFileNO", params, headers);
            log.error("报告赋码{},获取结果:{}",patientCode,result);
            String code = result.getCode();
            if ("200".equals(code)) {
                log.error("报告赋码获取成功:{}",patientCode);
                //base64 转 MultipartFile 并上传
                Base64ToMultipartFile multipartFile = MultipartFileUtil.base64ConvertMultipartFile(result.getQrcode());
                uploadAttFile(multipartFile,patientCode);
                log.error(patientCode + "赋码上传成功！");
            }else {
                log.error("报告赋码获取失败:{},错误信息:{}",patientCode,result.getMsg());
            }
        }
        log.error("报告赋码任务结束");
    }

    /**
     * 上传
     * @param files
     * @param patientCode
     */
    private void uploadAttFile(MultipartFile files, String patientCode) {
        //删除之前的附件
        attachmentService.remove(new LambdaQueryWrapper<Attachment>().eq(Attachment::getPatientcode,patientCode)
                .in(Attachment::getFileType,"赋码","赋码未生成报告"));
        //插入新附件
        DefaultBranchDto defaultBranch = qjkMapper.getDefaultBranch();
        String baseDir = "files/material/" + defaultBranch.getJm();
        Attachment attachment = new Attachment();
        String originalFilename = files.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        attachment.setFileType("赋码未生成报告");
        attachment.setType(1);
        attachment.setBranchId(defaultBranch.getBranchId());
        attachment.setCreatedate(new Date());
        attachment.setStatus(0);
        attachment.setPatientcode(patientCode);
        attachmentService.save(attachment);

        try {
            attachmentService.uploadFile(files, attachment, baseDir, extName, null, true);
        } catch (IOException e) {
            log.info(patientCode + "赋码上传失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());
    }


    /**
     * 获取上传数据
     * @param patientCode
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public RDataDTO getUploadData(String patientCode){
        log.info(patientCode + "开始上传");
        Peispatient peispatient = peispatientRepository.findByPatientcode(patientCode);
        log.info(patientCode + "获取体检人");
        if(peispatient==null){
            throw new ErrorRuntimeException("体检号不存在"+patientCode);
        }
        String idOrg = peispatient.getIdOrg();
        log.info(patientCode + "获取用人单位");
        if(StrUtil.isEmpty(idOrg)){
            throw new ErrorRuntimeException("单位Id为空");
        }
        Sellcustomer sellcustomer = sellcustomerRepository.findById(idOrg).orElse(new Sellcustomer());
        log.info(patientCode + "获取结论");
        //职业总检下的总检处理意见
        List<ConclusionDto> conclusionDtos = conclusionRepository.queryByPatientCode(patientCode);
        //前台管理-危害因素匹配，匹配的套餐里的危害因素
        List<HarmPackageMatchDto> harmPackageMatchDtos = harmPackageMatchQueryRepository.query(peispatient.getIdTjtc(), peispatient.getJhys());
        List<WzZys> wzZysList = wzZysRepository.findAllByDjls(peispatient.getPatientcode());
        StringBuilder stringBuilder = new StringBuilder();
        RDataDTO dto = new RDataDTO();
        dto.setType("health");
        setData(dto, peispatient, sellcustomer, conclusionDtos, harmPackageMatchDtos, wzZysList, stringBuilder);

//            log.info("JSONUtil.toJsonPrettyStr(dto) = " + JSONUtil.toJsonPrettyStr(dto));
        if (ObjectUtils.isNotEmpty(stringBuilder)) {
            throw new ErrorRuntimeException(stringBuilder.toString());
        }
        return dto;
    }
}
