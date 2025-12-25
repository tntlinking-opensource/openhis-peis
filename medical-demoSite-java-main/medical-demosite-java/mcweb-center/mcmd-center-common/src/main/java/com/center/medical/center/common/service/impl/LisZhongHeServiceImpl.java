package com.center.medical.center.common.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisZhongHeDto;
import com.center.medical.center.common.dao.LisZhongHeMapper;
import com.center.medical.center.common.service.LisService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Service("lisZhongHeService")
@RequiredArgsConstructor
public class LisZhongHeServiceImpl extends ServiceImpl<LisZhongHeMapper, Peispatientexamitem> implements LisService {


    /**
     * 没有中间库，给中核提供视图
     * @param middleDbDto
     */
    @Override
    public void save(MiddleDbDto middleDbDto) {

    }

    @Override
    public void delete(MiddleDbDto middleDbDto) {

    }

    @Override
    @DataSource(DataSourceType.ZHONGHE)
    public List<LisDto> selectList(String patientcode) {
        log.info("读取lis结果:"+patientcode);
        //lis库中使用8位短号
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<LisZhongHeDto> lisZhongHeDtos=baseMapper.selectList(patientcode8);
        List<LisDto> lisDtoS=new ArrayList<>();
        //每个小项只取最近一条结果
        Set<String> examKeys=new HashSet<>();
        for(LisZhongHeDto lisZhongHeDto:lisZhongHeDtos){
            String key=lisZhongHeDto.getHospTestId();
            if(examKeys.contains(key)){
                continue;
            }else{
                examKeys.add(key);
            }
            LisDto lisDto=new LisDto();
            Date testDate=lisZhongHeDto.getJyrqq();
            LocalDateTime examDateTime=null;
            if(testDate!=null){
                Instant instant = testDate.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                examDateTime = LocalDateTime.ofInstant(instant, zoneId);
            }
            lisDto.setExamDateTime(examDateTime);
            String sampleno=lisZhongHeDto.getSampleno();
            lisDto.setLisybbh(NumberUtil.isInteger(sampleno)?Integer.parseInt(sampleno):null);
            lisDto.setExamCode(key);
            String reportValue=lisZhongHeDto.getReportResult();
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
            lisDto.setRefRange(lisZhongHeDto.getReference());
            lisDto.setStatus(getStatus(lisZhongHeDto,stringValue));
            lisDto.setUnits(lisZhongHeDto.getItemunit());
            lisDto.setLisCode(key);
            lisDto.setExamCode(key);
            lisDto.setExamDoctor(lisZhongHeDto.getReportDoctor());
            lisDto.setAuditName(lisZhongHeDto.getVerifiedDoctor());
            lisDto.setInspectName(lisZhongHeDto.getReportDoctor());
            lisDto.setAuditDate(DateUtil.parseLocalDateTime(lisZhongHeDto.getCheckdatetime()));
            lisDto.setReceiveDate(examDateTime);
            lisDtoS.add(lisDto);
        }
        log.info(JSONUtil.toJsonStr(lisDtoS));
        return lisDtoS;
    }

    String getStatus(LisZhongHeDto lisZhongHeDto,String stringValue){
        String status=lisZhongHeDto.getTestState();
        status=status==null?null:status.trim();
        String result= StrUtil.isNotEmpty(stringValue)|| StrUtil.isEmpty(status)
                ?"":"H".equals(status)?"↑":"L".equals(status)?"↓":status.trim();//N正常H↑L↓
        return result;
    }
}
