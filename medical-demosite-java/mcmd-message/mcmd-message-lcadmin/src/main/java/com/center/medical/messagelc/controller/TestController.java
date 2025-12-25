package com.center.medical.messagelc.controller;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.common.constant.RequestFlag;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 查询手机报告列表
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "测试接口")
@RequestMapping("/open/api")
public class TestController extends BaseController {

    private final PeispatientService peispatientService;

    private final OrPeispatientService orPeispatientService;

    /**
     * 测试
     *
     * @return
     */
    @PostMapping("/test")
    @ApiOperation(value = "测试", notes = "测试")
    public R test() {
        RequestParam params = new RequestParam();

        //订单id获取体检者数据参数
        UserExamDataParam param = new UserExamDataParam();
        param.setFlag("111111111");
        param.setOrderId("ff80808180bb31990180d5aa405c49ce");
        param.setCallBack("http://XXX.XXX.XXX.XXX:XXX/open/api/test");
        params.setParams(JSONUtil.toJsonStr(param));
        params.setOnline(1);
        params.setAuthCode("5B0EBB19C2498355");
        params.setBsFlag("COO_CUSTOMER");
        params.setBsFlagNum(RequestFlag.PUSH_DATA_TO_COO);
        params.setBranchId("2");

        peispatientService.pushDataToCoo(params);

        //查询推送给第三方的旧系统的体检者数据
        orPeispatientService.pushOldDataToCoo(params);
        return R.ok();
    }

}

