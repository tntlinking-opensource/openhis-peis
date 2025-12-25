package com.center.medical.pacs.open;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.model.Lung;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.service.LungService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 接收电测听上传结果
 */
@Slf4j
@RestController
@RequestMapping("/lan/uploadLung")
@Api(tags = "接收肺功能上传结果")
@RequiredArgsConstructor
@Validated
public class UploadLungController {

    private final PeispatientService peispatientService;
    private final ISysBranchService iSysBranchService;
    private final SectionResultMainService sectionResultMainService;
    private final LungService lungService;


    /**
     * 接收图片
     *
     * @param jsonStr params
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "肺功能上传全部", notes = "肺功能上传全部")
    public String uploads(String jsonStr) {
        log.info("接收肺功能上传结果:{}",jsonStr);
        StringBuilder msg = new StringBuilder();
        List<Lung> list = JSON.parseArray(jsonStr, Lung.class);
        String rn = "\n";//换行符
        for (Lung lung : list) {
            String patientCode = ToolUtil.patientCode(lung.getPatientcode(), iSysBranchService.getBranchFlag(null));
            log.info("肺功能处理体检号：{}",patientCode);
            Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
            if (ObjectUtils.isEmpty(peispatient)){
                msg.append("文件" + lung.getOrigionName() + "体检号不存在， 上传失败!" + rn);
                continue;
            }
            SectionResultMain sectionResultMain = sectionResultMainService.getOne(new LambdaQueryWrapper<SectionResultMain>()
                    .eq(SectionResultMain::getPatientcode, patientCode)
                    .eq(SectionResultMain::getDepId, lung.getDepId()));
            if (ObjectUtils.isNotEmpty(sectionResultMain) && sectionResultMain.getIsAudit()==1){
                msg.append("文件" + lung.getOrigionName() + "体检号已审核， 不能上传!" + rn);
                continue;
            }
            lung.setPatientcode(patientCode);
            //插入数据
            Lung oldlung = lungService.getOne(new LambdaQueryWrapper<Lung>().eq(Lung::getPatientcode,patientCode));
            if (ObjectUtils.isEmpty(oldlung)) {
                lungService.save(lung);
            } else {
                lung.setId(oldlung.getId());
                lungService.updateById(lung);
            }
            log.info("肺功能体检号上传成功：{}",patientCode);
            msg.append("文件" + lung.getOrigionName() + "上传成功!" + rn);
        }
        return msg.toString();
    }


}
