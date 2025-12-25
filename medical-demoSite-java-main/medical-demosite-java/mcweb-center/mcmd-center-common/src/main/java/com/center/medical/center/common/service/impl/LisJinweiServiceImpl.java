package com.center.medical.center.common.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisJinweiDto;
import com.center.medical.center.common.dao.LisJinweiMapper;
import com.center.medical.center.common.service.LisService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 金卫Lis（华欧）
 * @author xhp
 * @since 2024-05-06 10:28
 */
@Slf4j
@Service("lisJinweiService")
@RequiredArgsConstructor
public class LisJinweiServiceImpl extends ServiceImpl<LisJinweiMapper, Peispatientexamitem> implements LisService {

    /**
     * 没有中间库，给金卫提供视图
     * @param middleDbDto
     */
    @Override
    public void save(MiddleDbDto middleDbDto) {

    }

    @Override
    public void delete(MiddleDbDto middleDbDto) {

    }

    /**
     * 读取lis结果
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(DataSourceType.JINWEI)
    public List<LisDto> selectList(String patientcode) {
        log.info("读取lis结果:"+patientcode);
        //lis库中使用8位短号
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<LisJinweiDto> lisJinweiDtos=baseMapper.selectList(patientcode8);
        List<LisDto> lisDtoS=new ArrayList<>();
        //每个小项只取最近一条结果
        Set<String> examKeys=new HashSet<>();
        for(LisJinweiDto lisJinweiDto:lisJinweiDtos){
            String key=lisJinweiDto.getItemNo()+"";
            if(examKeys.contains(key)){
                continue;
            }else{
                examKeys.add(key);
            }
            LisDto lisDto=new LisDto();
            lisDto.setExamCode(lisJinweiDto.getItemNo());
            lisDto.setExamDateTime(getDateTimeByStr(lisJinweiDto.getTestDate()));
            String sampleno=lisJinweiDto.getSampleno();
            if(NumberUtil.isInteger(sampleno)){
                lisDto.setLisybbh(Integer.parseInt(sampleno));
            }
            String examItemValuesReport=lisJinweiDto.getReportDesc();
            lisDto.setExamItemValuesReport(examItemValuesReport);
            if(NumberUtil.isNumber(examItemValuesReport)){
                lisDto.setExamItemValuesNumber(Double.parseDouble(examItemValuesReport));
            }else{
                lisDto.setExamItemValuesShort(examItemValuesReport);
            }
            lisDto.setRefRange(lisJinweiDto.getRefRange());
            lisDto.setStatus(getStatus(lisJinweiDto));
            lisDto.setUnits(lisJinweiDto.getUnit());
            lisDto.setLisCode(lisJinweiDto.getItemNo());
            lisDto.setExamDoctor(lisJinweiDto.getTechnician());
            lisDto.setAuditName(lisJinweiDto.getRechkUsername());
            lisDto.setInspectName(lisJinweiDto.getTechnician());
            lisDto.setInspectCode(lisJinweiDto.getReportUser());
            lisDto.setAuditDate(getDateTimeByStr(lisJinweiDto.getRechkDt()));
            lisDtoS.add(lisDto);
        }
        log.info(JSONUtil.toJsonStr(lisDtoS));
        return lisDtoS;
    }

    LocalDateTime getDateTimeByStr(String dateTimeStr){
        if(StrUtil.isEmpty(dateTimeStr))return null;
        switch (dateTimeStr.length()){
            case 19:
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            case 21:
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
            case 22:
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS"));
            case 23:
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            default:
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        }
    }

    String getStatus(LisJinweiDto lisJinweiDto){
        String status=lisJinweiDto.getReportStatus();
        status=status==null?null:status.trim();
        String result= StrUtil.isEmpty(status)
                ?"":"H".equals(status)?"↑":"L".equals(status)?"↓":status;//N正常H↑L↓
        return result;
    }
}
