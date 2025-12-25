package com.center.medical.pacs.controller;

import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.service.PacsCsharpService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 霸州悦琦接口接口
 *
 * @author ay
 * @since 2023-10-08 09:26:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "霸州悦琦接口")
@RequestMapping("/open/api/pacs/ruiQi")
public class RuiQiController extends BaseController {
    /**
     * 服务对象
     */
    private final PeispatientService peispatientService;
    private final ISysBranchService iSysBranchService;
    private final PacsCsharpService pacsCsharpService;


    /**
     * 体检者列表数据
     *
     * @param patientid 分页参数
     * @return 所有数据
     */
    @GetMapping("/getPatient")
    @ApiOperation(value = "获取体检者信息", notes = "获取体检者信息")
    public Object getPage(String patientid) {
        patientid = ToolUtil.patientCode(patientid, iSysBranchService.getBranchFlag(null));
        Peispatient peispatient = peispatientService.getByPatientCode(patientid);
        if (ObjectUtils.isEmpty(peispatient)){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Age", peispatient.getAge().toString());
        hashMap.put("Birthday", dateFormat.format(peispatient.getBirthdate()));
        hashMap.put("Gender", peispatient.getIdSex().toString());
        hashMap.put("PatientID", peispatient.getPatientcode());
        hashMap.put("RequestDate", dateFormat.format(new Date()));
        hashMap.put("Name", peispatient.getPatientname());
        List<HashMap> list = Arrays.asList(hashMap);
        return list;
    }





    /**
     * 体检者列表数据
     *
     * @param patientid 分页参数
     * @return 所有数据
     */
    @PostMapping("/postCheckExam")
    @ApiOperation(value = "接收检查数据", notes = "接收检查数据")
    public Object PostCheckExam(@RequestBody PostCheckExamParam param) {
        Boolean result = pacsCsharpService.postCheckExam(param);
        Map<String, String> map = new HashMap<>();
        map.put("Msg", result?"成功":"失败");
        map.put("Status", result?"SUCCESS":"failure");
        return map;
    }


}
