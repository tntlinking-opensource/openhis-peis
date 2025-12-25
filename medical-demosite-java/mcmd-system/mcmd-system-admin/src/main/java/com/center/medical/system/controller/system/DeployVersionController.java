package com.center.medical.system.controller.system;

/**
 * 版本信息(DeployVersion)接口
 *
 * @author makejava
 * @since 2023-12-01 08:14:38
 */
//@Slf4j
//@Validated
//@RestController
//@RequiredArgsConstructor
//@Api(tags = "版本-版本管理")
//@RequestMapping("system/version")
//public class DeployVersionController extends BaseController {
//    private final DeployVersionService deployVersionService;
//    private final MapperFacade mapperFacade;
//    private final DeployVersionBranchService deployVersionBranchService;
//    private final DeployRecordService deployRecordService;
//
//    /**
//     * 分页查询所有数据
//     *
//     * @param pageParamSimple 分页参数
//     * @param param           查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/page")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "分页查询", notes = "分页查询版本信息")
//    public R<IPage<DeployVersion>> getPage(PageParamSimple pageParamSimple, DeployVersionListParam param) {
//        PageParam<DeployVersion> page = mapperFacade.map(pageParamSimple, PageParam.class);
//        return R.ok(this.deployVersionService.getPage(page, param));
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
//    @ApiOperation(value = "详情", notes = "根据id查版本信息详情")
//    @ApiImplicitParam(name = "id", value = "版本信息id", required = true)
//    public R<DeployVersion> selectOne(@PathVariable int id) {
//        return R.ok(this.deployVersionService.getInfoById(id));
//    }
//
//    /**
//     * 根据版本信息id查询各分中心更新状态
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/branch/list/{id}")
//    //@PreAuthorize("@ss.hasPermi('::info')")
//    @ApiOperation(value = "分中心更新状态列表", notes = "根据版本信息id查询各分中心更新状态")
//    @ApiImplicitParam(name = "id", value = "版本信息id", required = true)
//    public R<List<DeployVersionBranch>> selectBranchListByVersionId(@PathVariable int id) {
//        return R.ok(deployVersionBranchService.selectBranchListByVersionId(id));
//    }
//
//    /**
//     * 根据版本信息id查询中心各服务更新状态
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/record/list/{id}")
//    //@PreAuthorize("@ss.hasPermi('::info')")
//    @ApiOperation(value = "中心各服务更新状态列表", notes = "根据版本信息分中心关联表id查询此中心各服务更新状态")
//    @ApiImplicitParam(name = "id", value = "版本信息分中心关联表id", required = true)
//    public R<List<DeployRecordListVo>> selectRecordListByBranchId(@PathVariable int id) {
//        return R.ok(deployRecordService.selectRecordListByBranchId(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @return 新增结果
//     */
//    @PostMapping
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "添加", notes = "添加版本信息")
//    @Log(title = "自动部署-添加版本信息", businessType = BusinessType.INSERT)
//    @ApiOperationSupport(ignoreParameters = {"deployVersion.id"})
//    public R insert(@Validated @RequestBody DeployVersionSaveParam param) {
//        this.deployVersionService.saveVersion(param);
//        return R.ok();
//    }
//
//    /**
//     * 修改数据
//     *
//     * @return 修改结果
//     */
//    @PutMapping
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "更新", notes = "更新版本信息")
//    @Log(title = "自动部署-更新版本信息", businessType = BusinessType.UPDATE)
//    public R update(@RequestBody DeployVersionUpdateParam param) {
//        this.deployVersionService.updateVersion(param);
//        return R.ok();
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
//    @ApiOperation(value = "删除", notes = "删除自动部署-更新版本信息")
//    @Log(title = "自动部署-删除版本信息", businessType = BusinessType.DELETE)
//    @ApiImplicitParam(name = "ids", value = "要删除的版本信息{id}集合，多个以英文逗号（,）隔开")
//    public R delete(@PathVariable List<Integer> ids) {
//        deployVersionService.deleteVersion(ids);
//        return R.ok();
//    }
//
//
//    /**
//     * 人工处理
//     */
//    @PutMapping("/manual")
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "人工处理", notes = "自动更新失败，人工更新，更新后修改更新状态")
//    @Log(title = "自动部署-人工处理", businessType = BusinessType.UPDATE)
//    public R manual(@Validated @RequestBody DeployRecordManualParam param) {
//        deployRecordService.manual(param);
//        return R.ok();
//    }
//
//
//}
