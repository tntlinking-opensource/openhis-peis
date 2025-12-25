package com.center.medical.system.controller.system;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.model.RegisterBody;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.framework.web.service.SysRegisterService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 注册验证
 *
 * @author 路飞
 */
@Api(tags = "注册验证")
@RestController
public class SysRegisterController extends BaseController {
    @Resource
    private SysRegisterService sysRegisterService;

    @Resource
    private ISysConfigService configService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册")
    public AjaxResult register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = sysRegisterService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
