package com.center.medical.system.controller.system;

/**
 * 系统服务(记录系统服务种类)(SysServiceType1)接口
 *
 * @author makejava
 * @since 2024-01-23 11:03:22
 */
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@Api(tags = "系统服务(记录系统服务种类)")
//@RequestMapping("sysServiceType1")
//public class SysServiceType1Controller extends BaseController {
//    /**
//     * 服务对象
//     */
//    private final SysServiceType1Service sysServiceType1Service;
//    private final MapperFacade mapperFacade;
//
//    /**
//     * 分页查询所有数据
//     *
//     * @param pageParamSimple 分页参数
//     * @param sysServiceType1 查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/page")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "分页查询", notes = "分页查询系统服务(记录系统服务种类)")
//    public R<IPage<SysServiceType1>> getPage(PageParamSimple pageParamSimple, SysServiceType1 sysServiceType1) {
//        PageParam<SysServiceType1> page = mapperFacade.map(pageParamSimple, PageParam.class);
//        return R.ok(this.sysServiceType1Service.getPage(page, sysServiceType1));
//    }
//
//    /**
//     * 查询所有数据
//     *
//     * @param sysServiceType1 查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/list")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
//    public R<List<SysServiceType1>> getList(SysServiceType1 sysServiceType1) {
//        List<SysServiceType1> list = this.sysServiceType1Service.list(new LambdaQueryWrapper<SysServiceType1>().eq(SysServiceType1::getStatus, 1));
//        return R.ok(list);
//    }
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param serviceId 主键
//     * @return 单条数据
//     */
//    @GetMapping("{serviceId}")
//    //@PreAuthorize("@ss.hasPermi('::info')")
//    @ApiOperation(value = "详情", notes = "根据serviceId查系统服务(记录系统服务种类)详情")
//    @ApiImplicitParam(name = "serviceId", value = "要查询的对象的主键{serviceId}")
//    public R<SysServiceType1> selectOne(@PathVariable Integer serviceId) {
//        return R.ok(this.sysServiceType1Service.getInfoById(serviceId));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param sysServiceType1 实体对象
//     * @return 新增结果
//     */
//    @PostMapping
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "添加", notes = "添加系统服务(记录系统服务种类)")
//    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.INSERT)
//    @ApiOperationSupport(ignoreParameters = {"sysServiceType1.serviceId"})
//    public R insert(@RequestBody SysServiceType1 sysServiceType1) {
//        return R.toResult(this.sysServiceType1Service.save(sysServiceType1));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param sysServiceType1 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "更新", notes = "更新系统服务(记录系统服务种类)")
//    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.UPDATE)
//    public R update(@RequestBody SysServiceType1 sysServiceType1) {
//        return R.toResult(this.sysServiceType1Service.updateById(sysServiceType1));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param serviceIds 删除的对象主键{serviceId}集合，多个以英文逗号（,）隔开
//     * @return 删除结果
//     */
//    @DeleteMapping("/{ids}")
//    //@PreAuthorize("@ss.hasPermi('::remove')")
//    @ApiOperation(value = "删除", notes = "删除系统服务(记录系统服务种类)")
//    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.DELETE)
//    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{serviceId}集合，多个以英文逗号（,）隔开")
//    public R delete(@PathVariable List<Integer> serviceIds) {
//        return R.toResult(this.sysServiceType1Service.removeByIds(serviceIds));
//    }
//}


