package com.center.medical.finance.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.event.DeApprovalEvent;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.service.PeisStateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 加项科室反审核(检验科除外)
 */
@Slf4j
@Component("deApprovalListener")
@AllArgsConstructor
public class DeApprovalListener {
    private final SectionResultMainService sectionResultMainService;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final PeisStateService peisStateService;



    @EventListener(DeApprovalEvent.class)
    public void cardCosumeListener(DeApprovalEvent event) {
        Peispatientfeeitem item = event.getParam();
        String ksId = item.getIdKs();
        String patientCode = item.getIdPatient();
        //判断是否需要反审核(之前有重复的科室,并且已经审核过了)
        Boolean b = NeedToDeApprove(item);
        if (b){
            log.info("加项科室反审核处理：{}", JSONUtil.toJsonStr(event));
            //反审核
            Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            peispatient.setFReadytofinal(0);
            peispatientMapper.updateById(peispatient);

            SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                    .eq("dep_id", ksId).eq("patientcode", patientCode));
            main.setIsAudit(0);
            sectionResultMainMapper.updateById(main);
        }
    }


    /**
     * 判断是否需要反审核(之前有重复的科室,并且已经审核过了)
     * @param item
     * @return
     */
    private Boolean NeedToDeApprove(Peispatientfeeitem item) {
        //查询科室检查主表,是否审核
        SectionResultMain sectionResultMain = sectionResultMainService.getOne(new LambdaQueryWrapper<SectionResultMain>()
                .eq(SectionResultMain::getPatientcode,item.getIdPatient())
                .eq(SectionResultMain::getDepId, item.getIdKs())
                .eq(SectionResultMain::getIsAudit, 1));
        if (ObjectUtil.isNotEmpty(sectionResultMain)){
            return true;
        }else {
            return false;
        }


    }


}
