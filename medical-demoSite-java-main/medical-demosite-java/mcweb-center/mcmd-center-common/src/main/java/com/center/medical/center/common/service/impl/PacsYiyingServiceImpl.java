package com.center.medical.center.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.center.common.bean.dto.PacsYiyingDto;
import com.center.medical.center.common.dao.PacsYiyingMapper;
import com.center.medical.center.common.service.PacsYiyingService;
import com.center.medical.common.enums.PacsType;
import com.center.medical.pacslis.bean.dto.PacsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 易影
 * @author xhp
 * @since 2025-02-25 15:34
 */
@Slf4j
@Service("pacsYiyingService")
@RequiredArgsConstructor
public class PacsYiyingServiceImpl extends ServiceImpl<PacsYiyingMapper, PacsResult> implements PacsYiyingService {

    /**
     * 读取pacs结果
     * @param patientcode
     * @return
     */
    @Override
    public List<PacsDto> selectList(String patientcode) {
        String patientcode8=patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        List<PacsYiyingDto> pacsYiyingDtos=baseMapper.selectList(patientcode8,patientcode);
        List<PacsDto> pacsDtos=new ArrayList<>();
        //一个收费项目只取一条结果
        Set<String> feeitemIds=new HashSet<>();
        for(PacsYiyingDto pacsYiyingDto:pacsYiyingDtos){
            String feeitemId=pacsYiyingDto.getFeeitemId();
            if(feeitemIds.contains(feeitemId)){
                continue;
            }
            feeitemIds.add(feeitemId);
            PacsDto pacsDto=new PacsDto();
            pacsDto.setExamdatetime(pacsYiyingDto.getExamdatetime());
            pacsDto.setFeeitemId(pacsYiyingDto.getFeeitemId());
            pacsDto.setExamdoctor(pacsYiyingDto.getReportDoctor());
            pacsDto.setExamresultdesc(pacsYiyingDto.getExamresultdesc());
            pacsDto.setExamresultsummary(pacsYiyingDto.getExamresultsummary());
            pacsDto.setAuditDoctor(pacsYiyingDto.getAuditDoctor());
            pacsDto.setType(PacsType.YIYING.name());
            pacsDtos.add(pacsDto);
        }
        return pacsDtos;
    }
}
