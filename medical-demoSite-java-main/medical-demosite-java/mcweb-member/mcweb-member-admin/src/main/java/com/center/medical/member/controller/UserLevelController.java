package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.member.bean.model.UserLevel;
import com.center.medical.member.service.UserLevelService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员管理-会员等级
 *
 * @author 路飞船长
 * @since 2023-03-14 11:20:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-会员等级")
@RequestMapping("member/userLevel")
public class UserLevelController extends BaseController {
    /**
     * 服务对象
     */
    private final UserLevelService userLevelService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param userLevel       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询会员等级表")
    public R<IPage<UserLevel>> getPage(PageParamSimple pageParamSimple, UserLevel userLevel) {
        PageParam<UserLevel> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.userLevelService.getPage(page, userLevel));
    }


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取全部会员类型", notes = "获取全部会员类型")
    public R<List<UserLevel>> getList() {
        List<UserLevel> list = userLevelService.list(new LambdaQueryWrapper<UserLevel>().eq(UserLevel::getStatus, 1));
        return R.ok(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param levelId 主键
     * @return 单条数据
     */
    @GetMapping("{levelId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据levelId查会员等级表详情")
    @ApiImplicitParam(name = "levelId", value = "要查询的对象的主键{levelId}")
    public R<UserLevel> selectOne(@PathVariable Integer levelId) {
        return R.ok(this.userLevelService.getInfoById(levelId));
    }

    /**
     * 新增数据
     *
     * @param userLevel 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"userLevel.levelId"})
    public R insert(@RequestBody UserLevel userLevel) {
        //TODO wait 添加完会员等级后需要为每个分中心生成一条该会员等级的默认预约设置
        return R.toResult(this.userLevelService.save(userLevel));
    }

    /**
     * 修改数据
     *
     * @param userLevel 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody UserLevel userLevel) {
        return R.toResult(this.userLevelService.updateById(userLevel));
    }

    /**
     * 删除数据
     *
     * @param levelIds 删除的对象主键{levelId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{levelId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> levelIds) {
        //TODO wait 删除完会员等级后需要删除每个分中心对应的默认预约设置
        return R.toResult(this.userLevelService.removeByIds(levelIds));
    }
}

