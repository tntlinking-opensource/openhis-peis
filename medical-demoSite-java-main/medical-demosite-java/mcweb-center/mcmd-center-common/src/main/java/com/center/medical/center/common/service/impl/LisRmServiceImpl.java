package com.center.medical.center.common.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisRmDto;
import com.center.medical.center.common.dao.LisRmMapper;
import com.center.medical.center.common.service.LisService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 瑞美lis
 * @author xhp
 * @since 2023-07-26 11:01
 */
@Slf4j
@Service("lisRmService")
@RequiredArgsConstructor
public class LisRmServiceImpl extends ServiceImpl<LisRmMapper, Peispatientexamitem> implements LisService {
    private final LisService lisHqService;

    /**
     * 插入中间库和虹桥一样
     * @param middleDbDto
     */
    @Override
    @Transactional
    @DataSource(DataSourceType.RUIMEISLAVE)
    public void save(MiddleDbDto middleDbDto) {
        lisHqService.save(middleDbDto);
    }

    @Override
    @Transactional
    @DataSource(DataSourceType.RUIMEISLAVE)
    public void delete(MiddleDbDto middleDbDto) {
        lisHqService.delete(middleDbDto);
    }

    @Override
    @DataSource(DataSourceType.RUIMEI)
    public List<LisDto> selectList(String patientcode) {
        log.info("读取lis结果:"+patientcode);
        //lis库中使用8位短号
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<LisRmDto> lisRmDtos=baseMapper.selectList(patientcode8,patientcode);
        List<LisDto> lisDtoS=new ArrayList<>();
        //每个小项只取最近一条结果
        Set<String> examKeys=new HashSet<>();
        for(LisRmDto lisRmDto:lisRmDtos){
            String key=lisRmDto.getExamCode();
            if(examKeys.contains(key)){
                continue;
            }else{
                examKeys.add(key);
            }
            LisDto lisDto=new LisDto();
            Date testDate=lisRmDto.getTestDate();
            LocalDateTime examDateTime=null;
            if(testDate!=null){
                Instant instant = testDate.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                examDateTime = LocalDateTime.ofInstant(instant, zoneId);
            }
            lisDto.setExamDateTime(examDateTime);
            String sampleno=lisRmDto.getSampleno();
            lisDto.setLisybbh(NumberUtil.isInteger(sampleno)?Integer.parseInt(sampleno):null);
            lisDto.setExamCode(lisRmDto.getExamCode());
            String reportValue=lisRmDto.getReportDesc();
            String stringValue=null;//字符结果
            Double numberValue=null;//数字结果
            if(NumberUtil.isNumber(reportValue)){
                numberValue=Double.parseDouble(reportValue);
            }else{
                stringValue=reportValue;
            }
            lisDto.setExamItemValuesNumber(numberValue);
            lisDto.setExamItemValuesShort(stringValue);
            lisDto.setExamItemValuesReport(reportValue);
            lisDto.setRefRange(lisRmDto.getRefRange());
            lisDto.setStatus(getStatus(lisRmDto,stringValue));
            lisDto.setUnits(lisRmDto.getUnit());
            lisDto.setLisCode(lisRmDto.getExamCode());
            lisDto.setExamDoctor(lisRmDto.getTechnician());
            lisDto.setAuditName(lisRmDto.getRechkUsername());
            lisDto.setInspectName(lisRmDto.getTechnician());
            lisDto.setAuditDate(examDateTime);
            lisDto.setInspectCode(lisRmDto.getReportUser());
            lisDto.setReceiveDate(examDateTime);
            lisDtoS.add(lisDto);
        }
        log.info(JSONUtil.toJsonStr(lisDtoS));
        return lisDtoS;
    }

    String getStatus(LisRmDto lisRmDto,String stringValue){
        String status=lisRmDto.getReportStatus();
        status=status==null?null:status.trim();
        String result= StrUtil.isEmpty(status) ?
//                || StrUtil.isNotEmpty(stringValue)
                "":"H".equals(status)?"↑":"L".equals(status)?"↓":status.trim();//N正常H↑L↓
        return result;
    }
}
