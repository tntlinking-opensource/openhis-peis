package com.center.medical.center.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.center.common.bean.dto.PacsJinweiDto;
import com.center.medical.center.common.dao.PacsJinweiMapper;
import com.center.medical.center.common.service.PacsJinweiService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.enums.PacsType;
import com.center.medical.pacslis.bean.dto.PacsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 金卫Pacs
 * @author xhp
 * @since 2024-05-27 7:47
 */
@Slf4j
@Service("pacsJinweiService")
@RequiredArgsConstructor
public class PacsJinweiServiceImpl extends ServiceImpl<PacsJinweiMapper, PacsResult> implements PacsJinweiService {


    /**
     * 读取pacs结果
     * @param patientcode
     * @return
     */
    @DataSource(DataSourceType.JINWEI)
    @Override
    public List<PacsDto> selectList(String patientcode) {
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<PacsJinweiDto> pacsJinweiDtos=baseMapper.selectList(patientcode8);
        List<PacsDto> pacsDtos=new ArrayList<>();
        //一个收费项目只取一条结果
        Set<String> examfeeitemCodes=new HashSet<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(PacsJinweiDto pacsJinweiDto:pacsJinweiDtos){
            String examfeeitemCode=pacsJinweiDto.getExamfeeitemCode();
            if(examfeeitemCodes.contains(examfeeitemCode)){
                continue;
            }
            examfeeitemCodes.add(examfeeitemCode);
            PacsDto pacsDto=new PacsDto();
            String examDate=pacsJinweiDto.getExamDate();
            String examTime=pacsJinweiDto.getExamTime();
            if(StrUtil.isNotEmpty(examDate)&&StrUtil.isNotEmpty(examTime)){
                try {
                    pacsDto.setExamdatetime(sdf.parse(examDate+" "+examTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            pacsDto.setExamfeeitemCode(examfeeitemCode);
            pacsDto.setExamdoctor(pacsJinweiDto.getExamDoctor());
            pacsDto.setExamresultdesc(pacsJinweiDto.getExamResultDesc());
            pacsDto.setExamresultsummary(pacsJinweiDto.getExamResultSummary());
            pacsDto.setImagefullpath(pacsJinweiDto.getImageFullPath());
            pacsDto.setUserName(pacsJinweiDto.getUserName());
            pacsDto.setType(PacsType.JINWEI.name());
            pacsDtos.add(pacsDto);
        }
        return pacsDtos;
    }
}
