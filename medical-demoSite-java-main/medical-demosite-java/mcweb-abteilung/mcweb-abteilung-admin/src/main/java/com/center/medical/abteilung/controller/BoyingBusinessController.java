package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import com.center.medical.outside.constant.BoyingConstant;
import com.center.medical.outside.service.BoyingBusinessService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 博英心电图业务接口
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "博英心电图业务接口")
@RequestMapping("boying")
public class BoyingBusinessController extends BaseController {
    /**
     * 服务对象
     */
    private final BoyingBusinessService boyingBusinessService;
    private final ISysConfigService iSysConfigService;
    private final PeispatientService peispatientService;


    /**
     * 获取检查报告
     *
     * @return 单条数据
     */
    @GetMapping("/getResult/{patientCode}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取检查报告", notes = "获取检查报告")
    public R<List<BoyingWriteReportParam>> getResult(@PathVariable String patientCode) {
        log.info("博英心电图获取检查报告参数：{}", patientCode);
        //是否已总检完成
        //判断体检号是否存在
        Peispatient peispatient;
        //判断是否是8位
        if(patientCode.length()!=8){
            peispatient = peispatientService.getByPatientCode(patientCode);
        }else {
            peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getShortCode,patientCode)
                    .eq(Peispatient::getFPaused, 0)
                    .eq(Peispatient::getFRegistered, 1)
            );
        }

        if(Objects.isNull(peispatient)){
            return R.fail("在系统中未查到体检号为【"+ patientCode +"】的体检者！");
        }
        //已总检不能再覆盖
        if((Objects.nonNull(peispatient.getJktjzt()) && peispatient.getJktjzt()>0) || (Objects.nonNull(peispatient.getZytjzt()) && peispatient.getZytjzt()>0) ){
            return R.fail("该体检者【"+ patientCode +"】已总检完成，不能再覆盖");
        }

//        if (ZhongkangConfig.isOnline()) {
            //线上：调用博英主动请求数据的接口
            R<String> r = boyingBusinessService.againReportBack(patientCode);
            if (R.isSuccess(r)) {
                //获取成功
                Set<String> keys = RedisUtil.keys(BoyingConstant.RESULT_KEY + patientCode + "*");
                List<BoyingWriteReportParam> data = RedisUtil.multiGet(keys);
                log.info("博英心电图获取检查报告结果：{}，{}", data);
                return R.ok(data);
            } else {
                //获取失败
                return R.fail(r.getMsg());
            }
//        }else {
//            //线下：调用线上接口获取
//            log.info("博英心电图获取检查报告线下：{}", patientCode);
//            Domain domain = iSysConfigService.getDomain();
//            String resultStr = HttpUtil.post(domain.getPlatformDomain()+BoyingConstant.AGAIN_REPORT_BACK_URL+"/"+patientCode, "");
//            log.info("博英心电图获取检查报告请求线上结果：{}，{}", domain.getPlatformDomain()+BoyingConstant.AGAIN_REPORT_BACK_URL+"/"+patientCode, resultStr);
//            return JSONUtil.toBean(resultStr, R.class);
//        }

    }
}

