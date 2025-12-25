package com.center.medical.system.controller.system;

/**
 * 版本控制-版本分中心关联接口
 *
 * @author makejava
 * @since 2024-01-23 10:36:54
 */
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@Api(tags = "版本控制-版本分中心关联")
//@RequestMapping("sysVersionBranch1")
//public class SysVersionBranch1Controller extends BaseController {
//    /**
//     * 服务对象
//     */
//    private final SysVersionBranch1Service sysVersionBranch1Service;
//    private final MapperFacade mapperFacade;
//
//    /**
//     * 分页查询所有数据
//     *
//     * @param pageParamSimple   分页参数
//     * @param sysVersionBranch1 查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/page")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "分页查询", notes = "分页查询自动部署-版本更新分中心关联表")
//    public R<IPage<SysVersionBranch1>> getPage(PageParamSimple pageParamSimple, SysVersionBranch1 sysVersionBranch1) {
//        PageParam<SysVersionBranch1> page = mapperFacade.map(pageParamSimple, PageParam.class);
//        return R.ok(this.sysVersionBranch1Service.getPage(page, sysVersionBranch1));
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
//    @ApiOperation(value = "详情", notes = "根据id查自动部署-版本更新分中心关联表详情")
//    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
//    public R<SysVersionBranch1> selectOne(@PathVariable Integer id) {
//        return R.ok(this.sysVersionBranch1Service.getInfoById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param sysVersionBranch1 实体对象
//     * @return 新增结果
//     */
//    @PostMapping
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "添加", notes = "添加自动部署-版本更新分中心关联表")
//    @Log(title = "自动部署-版本更新分中心关联表", businessType = BusinessType.INSERT)
//    @ApiOperationSupport(ignoreParameters = {"sysVersionBranch1.id"})
//    public R insert(@RequestBody SysVersionBranch1 sysVersionBranch1) {
//        return R.toResult(this.sysVersionBranch1Service.save(sysVersionBranch1));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param sysVersionBranch1 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "更新", notes = "更新自动部署-版本更新分中心关联表")
//    @Log(title = "自动部署-版本更新分中心关联表", businessType = BusinessType.UPDATE)
//    public R update(@RequestBody SysVersionBranch1 sysVersionBranch1) {
//        return R.toResult(this.sysVersionBranch1Service.updateById(sysVersionBranch1));
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
//    @ApiOperation(value = "删除", notes = "删除自动部署-版本更新分中心关联表")
//    @Log(title = "自动部署-版本更新分中心关联表", businessType = BusinessType.DELETE)
//    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
//    public R delete(@PathVariable List<Integer> ids) {
//        return R.toResult(this.sysVersionBranch1Service.removeByIds(ids));
//    }
//}


