package com.center.medical.system.controller.system;

/**
 * 版本控制-更新记录(SysVersionDeploy1)接口
 *
 * @author makejava
 * @since 2024-01-23 10:36:54
 */
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@Api(tags = "版本控制-更新记录")
//@RequestMapping("sysVersionDeploy1")
//public class SysVersionDeploy1Controller extends BaseController {
//    /**
//     * 服务对象
//     */
//    private final SysVersionDeploy1Service sysVersionDeploy1Service;
//    private final MapperFacade mapperFacade;
//
//    /**
//     * 分页查询所有数据
//     *
//     * @param pageParamSimple   分页参数
//     * @param sysVersionDeploy1 查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/page")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "分页查询", notes = "分页查询版本控制-更新记录")
//    public R<IPage<SysVersionDeploy1>> getPage(PageParamSimple pageParamSimple, SysVersionDeploy1 sysVersionDeploy1) {
//        PageParam<SysVersionDeploy1> page = mapperFacade.map(pageParamSimple, PageParam.class);
//        return R.ok(this.sysVersionDeploy1Service.getPage(page, sysVersionDeploy1));
//    }
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("{id}")
//    //@PreAuthorize("@ss.hasPermi('::info')")
//    @ApiOperation(value = "详情", notes = "根据id查版本控制-更新记录详情")
//    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
//    public R<SysVersionDeploy1> selectOne(@PathVariable String id) {
//        return R.ok(this.sysVersionDeploy1Service.getInfoById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param sysVersionDeploy1 实体对象
//     * @return 新增结果
//     */
//    @PostMapping
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "添加", notes = "添加版本控制-更新记录")
//    @Log(title = "版本控制-更新记录", businessType = BusinessType.INSERT)
//    @ApiOperationSupport(ignoreParameters = {"sysVersionDeploy1.id"})
//    public R insert(@RequestBody SysVersionDeploy1 sysVersionDeploy1) {
//        return R.toResult(this.sysVersionDeploy1Service.save(sysVersionDeploy1));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param sysVersionDeploy1 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "更新", notes = "更新版本控制-更新记录")
//    @Log(title = "版本控制-更新记录", businessType = BusinessType.UPDATE)
//    public R update(@RequestBody SysVersionDeploy1 sysVersionDeploy1) {
//        return R.toResult(this.sysVersionDeploy1Service.updateById(sysVersionDeploy1));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
//     * @return 删除结果
//     */
//    @DeleteMapping("/{ids}")
//    //@PreAuthorize("@ss.hasPermi('::remove')")
//    @ApiOperation(value = "删除", notes = "删除版本控制-更新记录")
//    @Log(title = "版本控制-更新记录", businessType = BusinessType.DELETE)
//    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
//    public R delete(@PathVariable List<String> ids) {
//        return R.toResult(this.sysVersionDeploy1Service.removeByIds(ids));
//    }
//}


