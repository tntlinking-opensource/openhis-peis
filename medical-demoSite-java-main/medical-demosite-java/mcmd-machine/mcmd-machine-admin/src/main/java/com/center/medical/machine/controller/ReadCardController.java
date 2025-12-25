package com.center.medical.machine.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.machine.bean.param.ReadCardParam;
import com.center.medical.machine.service.ReadCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "读取身份证")
@RequestMapping("machine/readCard")
public class ReadCardController extends BaseController {
    /**
     * 服务对象
     */
    private final ReadCardService readCardService;
    private final MapperFacade mapperFacade;


    /**
     * 收到客户端消息后调用的方法
     *
     * @param param 来自客户端的消息
     * @return 单条数据
     */
    @GetMapping("/onMessage")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "查询对应的卡号及体检者信息", notes = "收到客户端消息后调用的方法,查询对应的卡号及体检者信息")
    @ApiImplicitParam(name = "message", value = "来自客户端的消息,比如addItem科室加项,report报告打印,queue排队调整,register自助登记等")
    public R selectOne(ReadCardParam param) {
        Map<String, Object> map = readCardService.onMessage(param);
        return R.ok(map);
    }


}

