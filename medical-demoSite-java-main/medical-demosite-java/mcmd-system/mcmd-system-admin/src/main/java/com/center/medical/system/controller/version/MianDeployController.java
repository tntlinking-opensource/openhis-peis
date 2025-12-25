package com.center.medical.system.controller.version;

import com.center.medical.common.core.domain.R;
import com.center.medical.system.bean.dto.DeloyDto;
import com.center.medical.system.config.DeloyExecutor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xhp
 * @since 2023-11-15 9:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-更新部署")
@RequestMapping("/system/deploy")
@Validated
public class MianDeployController {
    private final DeloyExecutor deloyExecutor;

    /**
     * 立即执行更新部署操作
     *
     * @return
     */
    @PostMapping("/execute")
    @ApiOperation(value = "执行更新部署", notes = "立即执行更新部署操作")
    public R<String> getVersion(DeloyDto param) {
//        param.setSqlFiles(Arrays.asList("https://XXX.XXX.XXX.XXXm/newimage/files/system/version/v100/sql/update.sql"));
        //deloyExecutor.run(param);
        return R.ok("执行成功！");
    }
}
