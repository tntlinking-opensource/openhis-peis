package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.param.HNSOUParam;
import com.center.medical.bean.param.HandleNewParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.HandleNewVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.service.HandleNewProjectsService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
 * 检验样品-检验加项(HandleNewProjects)表控制层
 *
 * @author ay
 * @since 2023-01-08 10:37:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验样品-检验加项")
@RequestMapping("/handle/handleNewProjects")
public class HandleNewProjectsController extends BaseController {
    /**
     * 服务对象
     */
    private final HandleNewProjectsService handleNewProjectsService;
    private final MapperFacade mapperFacade;


    /**
     * 【检验样品-检验加项】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【检验样品-检验加项】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/handle/handleNewProjects/page", "09.科室系统->科室管理-检验样本-检验加项->分页查询", null),
                new InterfaceVo("详情", "GET", "/handle/handleNewProjects/{id}", "09.科室系统->科室管理-检验样本-检验加项->详情", null),
                new InterfaceVo("处理", "GET", "/handle/handleNewProjects/saveOrUpdate", "09.科室系统->科室管理-检验样本-检验加项->处理", null),
                new InterfaceVo("批量处理", "GET", "/handle/handleNewProjects/saveBatch", "09.科室系统->科室管理-检验样本-检验加项->批量处理", null),
                new InterfaceVo("承送人名处搜索", "GET", "/outside/SendRegister/getLqrData", "09.科室系统->科室管理-外送管理-外送登记->承送人名处搜索", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "检验样品-检验加项", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<HandleNewVo>> getPage(PageParamSimple pageParamSimple, HandleNewParam param) {
        PageParam<HandleNewProjects> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.handleNewProjectsService.getList(page, param));
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
        return R.ok(this.handleNewProjectsService.getInfoById(id));
    }

    /**
     * 处理
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "处理或反处理", notes = "处理或反处理")
    @ApiOperationSupport(ignoreParameters = {"handleNewProjects.id"})
    public R saOrUp(@RequestBody HNSOUParam param) {
        return R.toResult(this.handleNewProjectsService.saOrUp(param));
    }

    /**
     * 修改数据
     *
     * @param ids
     * @param type
     * @return
     */
    @PutMapping("/saveBatch")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "批量处理", notes = "批量处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id集合"),
            @ApiImplicitParam(name = "type", value = "处理0,反处理1")
    })
    public R saveBatch(@RequestParam("ids") List<String> ids, @RequestParam("type") String type) {
        return R.toResult(this.handleNewProjectsService.CLSaveBatch(ids, type));
    }


}

