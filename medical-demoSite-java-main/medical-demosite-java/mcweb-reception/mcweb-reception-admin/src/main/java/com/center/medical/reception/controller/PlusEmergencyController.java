package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reception.bean.param.PlusEmParam;
import com.center.medical.reception.bean.vo.PlusEmergencyVo;
import com.center.medical.reception.service.PlusEmergencyService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加急报告(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-02 15:30:08
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "加急报告")
@RequestMapping("/reception/PlusEmergency")
public class PlusEmergencyController extends BaseController {
    /**
     * 服务对象
     */
    private final PlusEmergencyService plusEmergencyService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<PlusEmergencyVo>> getPage(PageParamSimple pageParamSimple, PlusEmParam param) {
        PageParam<PlusEmergencyVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.plusEmergencyService.getList(page, param));
    }


    /**
     * 加急-保存
     *
     * @param patientcode 体检码
     * @return 新增结果
     */
    @PutMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "加急-保存", notes = "加急-保存")
    @Log(title = "QT体检者表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatient.id"})
    public R insert(String patientcode) {
        return R.toResult(this.plusEmergencyService.SaOrUp(patientcode));
    }


}

