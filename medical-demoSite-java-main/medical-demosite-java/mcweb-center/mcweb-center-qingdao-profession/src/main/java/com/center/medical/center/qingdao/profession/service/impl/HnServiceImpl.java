package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.center.qingdao.profession.cache.TokenCache;
import com.center.medical.center.qingdao.profession.entity.dto.*;
import com.center.medical.center.qingdao.profession.entity.oracle.OrPeispatientConsultation;
import com.center.medical.center.qingdao.profession.entity.persistent.PeispatientConsultation;
import com.center.medical.center.qingdao.profession.entity.properties.HnjkProperties;
import com.center.medical.center.qingdao.profession.entity.properties.QjkProperties;
import com.center.medical.center.qingdao.profession.mapper.HnMapper;
import com.center.medical.center.qingdao.profession.mapper.QjkMapper;
import com.center.medical.center.qingdao.profession.orrepository.OrPeispatientConsultationRepository;
import com.center.medical.center.qingdao.profession.repository.PeisStateExtRepository;
import com.center.medical.center.qingdao.profession.repository.PeispatientConsultationRepository;
import com.center.medical.center.qingdao.profession.service.HnService;
import com.center.medical.center.qingdao.profession.service.LogService;
import com.center.medical.center.qingdao.profession.service.QjkService;
import com.center.medical.center.qingdao.profession.utils.JDBCUtil;
import com.center.medical.center.qingdao.profession.utils.Render;
import com.center.medical.center.qingdao.profession.utils.RequestService;
import com.center.medical.center.qingdao.profession.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
@Service
@AllArgsConstructor
@Slf4j
public class HnServiceImpl implements HnService {

    private final HnMapper hnMapper;
    private final TokenCache cache;
    private final RequestService requestService;
    private final HnjkProperties HnjkProperties;
    private final PeisStateExtRepository peisStateExtRepository;
    private final PeispatientConsultationRepository peispatientConsultationRepository;
    private final OrPeispatientConsultationRepository orPeispatientConsultationRepository;
    private final LogService logService;



    /**
     * 上传
     * @param startDate
     * @param endDate
     */
    @Override
    public void upload(Date startDate, Date endDate) {
        log.info("开始上传淮南疾控!");
        //登录获取token
        String token = getToken();
        //体检基本信息
        List<String> patientCodes = peisStateExtRepository.findByCreateDateRangle(startDate, endDate);
        for (String patientCode : patientCodes) {
            try {
                log.info("开始查询体检者基本信息:{}",patientCode);
                GetOhEmployeesDto getOhEmployeesDto = hnMapper.getOhEmployees(patientCode);
                log.info("开始查询个人生活史:{}",patientCode);
                PersonalHistoryDto personalHistoryDto =  getPersonalHistoryDto(StringUtils.isNotEmpty(getOhEmployeesDto.getInpatientno()) ? getOhEmployeesDto.getInpatientno() : getOhEmployeesDto.getPatientcode());
                log.info("开始查询接触相关危害因素信息:{}",patientCode);

            } catch (Exception e) {
                log.error(patientCode + ":" + e.getMessage(), e);
                logService.saveLogError(patientCode, e.getMessage());
            }

        }

    }

    /**
     * 获取个人生活史
     * @param s
     * @return
     */
    private PersonalHistoryDto getPersonalHistoryDto(String patientCode) {
        //职业问诊   职业问诊不会复查，如果是上传复查体检号，直接上传上一次的体检号
        PeispatientConsultation peispatientConsultation = peispatientConsultationRepository.findByPatientcode(patientCode);
        //如果查不到，查询老系统数据
        if (ObjectUtils.isEmpty(peispatientConsultation)) {
            OrPeispatientConsultation orPeispatientConsultation = orPeispatientConsultationRepository.findByPatientcode(patientCode);
            peispatientConsultation = new PeispatientConsultation();
            BeanUtils.copyProperties(orPeispatientConsultation, peispatientConsultation);
        }
        if (peispatientConsultation == null) {
            throw new ServiceException("职业问诊结果缺失");
        } else {
            PersonalHistoryDto dataDTO = new PersonalHistoryDto();
            String abstainSmokeNote = peispatientConsultation.getAbstainSmokeNote();
            if ("0".equals(abstainSmokeNote)) {
                dataDTO.setSmokingStatus("4");
            } else if ("1".equals(abstainSmokeNote)) {
                dataDTO.setSmokingStatus("2");
            } else if ("2".equals(abstainSmokeNote)) {
                dataDTO.setSmokingStatus("3");
            } else if ("3".equals(abstainSmokeNote)) {
                dataDTO.setSmokingStatus("1");
            } else {
                throw new ServiceException("目前吸烟情况缺失");
            }

            if (!"3".equals(abstainSmokeNote)) {
                dataDTO.setPersonalHistoryYear(0);
                dataDTO.setPersonalHistoryMonth(0);
                dataDTO.setDailySmokingVolum(0);
            } else {
                //吸烟年数,可能有小数,前端有校验必然是数字
                String smokeYear = peispatientConsultation.getSmokeYear();
                if (StrUtil.isBlank(smokeYear)) {
                    smokeYear = "0";
                }

                double doubleYear = Double.parseDouble(smokeYear.trim());
                double personalHistoryYear = Math.ceil(doubleYear);
                dataDTO.setPersonalHistoryYear((int) personalHistoryYear);

                String smokeMonth = peispatientConsultation.getSmokeMonth();
                if (StrUtil.isEmpty(smokeMonth)) smokeMonth = "0";
                double doubleMonth = Double.parseDouble(smokeMonth.trim());
                double personalHistoryMonth = Math.ceil(doubleMonth);
                dataDTO.setPersonalHistoryMonth((int) personalHistoryMonth);

                String everydaySmokeN = peispatientConsultation.getEverydaySmokeN();
                if (StrUtil.isEmpty(everydaySmokeN)) {
                    throw new ServiceException("平均每天吸烟量缺失");
                } else {
                    dataDTO.setDailySmokingVolum(Integer.valueOf(everydaySmokeN));
                }
            }
            return dataDTO;
        }
    }


    public String getToken() {
        TokenCache.TokenInfo token = cache.getToken();
        //token有效期2小时，提前5分钟刷新
        if (token == null || (ObjectUtil.isEmpty(token.getToken()) || Math.abs(DateUtil.between(token.getExpiration(), new Date(), DateUnit.MINUTE)) >= 115)) {
            HnResult result = getResult(HnjkProperties.getUserCode(), HnjkProperties.getPassword());
            cache.setToken(new TokenCache.TokenInfo(result.getData()));
        }
        return cache.getToken().getToken();
    }

    private HnResult getResult(String userCode, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", userCode);
        params.put("password", password);
        params.put("orgName", "安徽省职业病防治院");
        HnResult result = requestService.doHnPost(HnjkProperties.getUrl(), params);
        log.info("result.getToken() : " + result.getData());
        if(!result.getSuccess()){
            log.info("获取token失败", JSONUtil.toJsonStr(result));
            throw new RuntimeException("获取token失败,"+JSONUtil.toJsonStr(result));
        }
        return result;
    }
}

