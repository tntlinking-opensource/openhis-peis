package com.center.medical.pacslis.controller.lis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.param.DivisionParam;
import com.center.medical.pacslis.bean.vo.DivisionVo;
import com.center.medical.pacslis.service.PacsPeispatientService;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 彩超审核查看
 *
 * @author ay
 * @since 2022-12-29 11:39:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS-彩超审核查看")
@RequestMapping("/pacs/Divisions")
public class DivisionsController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsPeispatientService pacsPeispatientService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;


    /**
     * 【PACS-PACS登记信息查询】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【PACS-PACS登记信息查询】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/pacs/Divisions/page", "11.pacs/lis->PACS-彩超审核查看->分页查询", null),
                new InterfaceVo("详情", "GET", "/pacs/Divisions/page", "11.pacs/lis->PACS-彩超审核查看->详情", null)
        );
        return R.ok(new FunctionVo("11.pacs/lis", "PACS-PACS登记信息查询", interfaceVos, "11.pacs/lis"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询PACS-体检者表")
    public R<IPage<DivisionVo>> getPage(PageParamSimple pageParamSimple, DivisionParam param) {
        PageParam<PacsPeispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.pacsPeispatientService.getDivPage(page, param));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<PacsPeispatient> selectOne(@PathVariable String id) {
        return R.ok(this.pacsPeispatientService.getInfoById(id));
    }


}

