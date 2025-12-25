package com.center.medical.reception.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.machine.bean.param.ModelParam;
import com.center.medical.machine.service.RegistrationMachineService;
import com.center.medical.reception.bean.param.BillModelDataParam;
import com.center.medical.reception.service.GuideSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-20 15:01:14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "条码和导引单")
@RequestMapping("/reception/guideSheet")
public class GuideSheetController extends BaseController {
    /**
     * 服务对象
     */
    private final GuideSheetService guideSheetService;
    private final MapperFacade mapperFacade;
    private final RegistrationMachineService registrationMachineService;


    /**
     * 导引单数据打印
     *
     * @return
     */
    @GetMapping("/getBillModelData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "导引单数据打印(弃用)", notes = "导引单数据打印数据(弃用)")
    public R<List<Map<String, Object>>> getBillModelData(BillModelDataParam param) {
        List<Map<String, Object>> list = guideSheetService.getBillModelData(param);
        return R.ok(list);
    }


    /**
     * 自助登记-打印导引单
     * @param param
     * @return
     */
    @PostMapping("/model")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印导引单数据", notes = "打印导引单数据")
    public R model(@RequestBody ModelParam param) {
        List<Map<String, Object>> result = registrationMachineService.getBillModelData(param);
        return R.ok(result);
    }


    /**
     * 获取logo图片地址
     * @return
     * @throws IOException
     */
    @GetMapping("/getSrc")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取logo图片地址", notes = "存在前端就行了，后端返回的太复杂了，还要加密")
    public R getSrc(){
        return R.ok();
    }


    /**
     * 根据id获取多位体检者
     *
     * @return
     */
    @GetMapping("/getPatientsForID")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据id获取多位体检者", notes = "根据id获取多位体检者，confirm返回的是边距长宽高等,就不返回了")
    public R getPatientsForID(BillModelDataParam param) {
        Map<String,Object> map = guideSheetService.getPatientsForID(param);
        return R.ok(map);
    }



    /**
     * 引导单模板、条码展示页数据
     * @param cid
     * @return
     */
    @GetMapping("/all")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "引导单模板、条码展示页数据", notes = "引导单模板、条码展示页数据")
    @ApiImplicitParam(name = "cid", value = "分中心id")
    public R all(String cid) {
        Map<String,Object> result = registrationMachineService.all(cid);
        return R.ok(result);
    }
}

