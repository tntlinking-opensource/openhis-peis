package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.CreatemealApp;
import com.center.medical.appadmin.bean.param.CMAppSaOrUpParam;
import com.center.medical.appadmin.bean.param.CreatemealAppParam;
import com.center.medical.appadmin.bean.param.GoLiveParam;
import com.center.medical.appadmin.bean.vo.CreatemealAppVo;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.appadmin.service.CreatemealAppService;
import com.center.medical.appadmin.service.CreatemealAppTypeService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 小程序套餐表(CreatemealApp)接口
 *
 * @author ay
 * @since 2024-06-12 17:31:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序套餐")
@RequestMapping("app/createmealApp")
public class CreatemealAppController extends BaseController {
    /**
     * 服务对象
     */
    private final CreatemealAppService createmealAppService;
    private final CreatemealAppTypeService createmealAppTypeService;
    private final MapperFacade mapperFacade;


    /**
     * 【检完签到】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【小程序套餐】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取套餐的收费项目", "GET", "/sell/createmeal/getItemsData", "获取收费项目信息", null)
        );
        return R.ok(new FunctionVo("小程序套餐", "小程序套餐", interfaceVos, "小程序套餐"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询小程序套餐表")
    public R<IPage<CreatemealAppVo>> getPage(PageParamSimple pageParamSimple, CreatemealAppParam param) {
        PageParam<CreatemealAppVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createmealAppService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查小程序套餐表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<CreatemealApp> selectOne(@PathVariable String id) {
        return R.ok(this.createmealAppService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @Log(title = "小程序套餐表", businessType = BusinessType.INSERT)
    public R insert(@RequestBody CMAppSaOrUpParam param) {
        return R.ok(this.createmealAppService.saOrUp(param));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除小程序套餐表")
    @Log(title = "小程序套餐表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.ok(this.createmealAppService.deleteIds(ids));
    }


    /**
     * 新增数据
     *
     * @param id
     * @return 新增结果
     */
    @PostMapping("/setAppMeal")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "设为app套餐", notes = "从体检基础套餐维护那个页面点击这个按钮，这个套餐就进到小程序套餐表的分页里面，需要继续编辑修改后，小程序那才能看到")
    @ApiImplicitParam(name = "id", value = "套餐id")
    public R setAppMeal(String id) {
        return R.ok(this.createmealAppService.setAppMeal(id));
    }




    /**
     * 分页查询所有数据
     *
     * @param name      查询条件
     * @return 所有数据
     */
    @GetMapping("/getTypeList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取类型", notes = "获取类型")
    public R<List<GetTypeListVo>> getTypeList(String name) {
        return R.ok(this.createmealAppTypeService.getTypeList(name));
    }



    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/goLive")
    @ApiOperation(value = "上线或下线", notes = "上线或下线")
    @Log(title = "小程序文章上线或下线", businessType = BusinessType.INSERT)
    public R goLive(@RequestBody GoLiveParam param) {
        return R.ok(this.createmealAppService.goLive(param));
    }
}
