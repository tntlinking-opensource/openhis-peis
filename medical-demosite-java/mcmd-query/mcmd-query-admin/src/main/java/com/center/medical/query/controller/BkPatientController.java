package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.BkPatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.bean.param.BkPatientParam;
import com.center.medical.service.BkPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 体检者(BkPatient)表控制层
 *
 * @author ay
 * @since 2023-04-13 16:16:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "历史档案查询")
@RequestMapping("query/bkPatient")
public class BkPatientController extends BaseController {
    /**
     * 服务对象
     */
    private final BkPatientService bkPatientService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者")
    public R<IPage<BkPatient>> getPage(PageParamSimple pageParamSimple, BkPatientParam param) {
        PageParam<BkPatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.bkPatientService.getList(page, param));
    }


}

