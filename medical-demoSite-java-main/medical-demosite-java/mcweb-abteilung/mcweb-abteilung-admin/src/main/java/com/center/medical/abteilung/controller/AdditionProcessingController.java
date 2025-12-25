package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.AdditionProcessingParam;
import com.center.medical.abteilung.bean.vo.AdditionProcessingVo;
import com.center.medical.abteilung.service.AdditionProcessingService;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 加项处理(HandleNewProjects)表控制层
 *
 * @author makejava
 * @since 2023-01-29 10:48:47
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "加项处理")
@RequestMapping("/abteilung/additionProcessing")
public class AdditionProcessingController extends BaseController {
    /**
     * 服务对象
     */
    private final AdditionProcessingService additionProcessingService;
    private final MapperFacade mapperFacade;

    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/handleNewProjects/page", "09.科室系统->科室管理-加项处理->分页查询", null),
                new InterfaceVo("详情", "GET", "/handleNewProjects/{id}", "09.科室系统->科室管理-加项处理->详情", null),
                new InterfaceVo("承送人名处搜索", "GET", "/outside/SendRegister/getLqrData", "09.科室系统->科室管理-外送管理-外送登记->承送人名处搜索", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "科室系统", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询KS检验科加项处理")
    public R<IPage<AdditionProcessingVo>> getPage(PageParamSimple pageParamSimple, AdditionProcessingParam param) {
        PageParam<HandleNewProjects> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.additionProcessingService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查KS检验科加项处理详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<HandleNewProjects> selectOne(@PathVariable String id) {
        return R.ok(this.additionProcessingService.getInfoById(id));
    }


    /**
     * 修改数据
     *
     * @param ids
     * @param type
     * @return 修改结果
     */
    @PutMapping("/saveBatch")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "批量处理", notes = "批量处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id集合"),
            @ApiImplicitParam(name = "type", value = "处理0，反处理1")
    })
    public R saveBatch(@RequestParam("ids") List<String> ids, @RequestParam("type") String type) {
        return R.toResult(this.additionProcessingService.CLSaveBatch(ids, type));
    }


}

