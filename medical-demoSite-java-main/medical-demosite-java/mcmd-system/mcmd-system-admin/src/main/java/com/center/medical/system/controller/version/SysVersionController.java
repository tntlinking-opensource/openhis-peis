package com.center.medical.system.controller.version;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.dto.CurVersionDto;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.model.SysVersionItem;
import com.center.medical.system.bean.model.SysVersionNotity;
import com.center.medical.system.bean.param.SysVersionParam;
import com.center.medical.system.service.SysVersionItemService;
import com.center.medical.system.service.SysVersionNotityService;
import com.center.medical.system.service.SysVersionSvService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 版本控制-版本信息(SysVersion)接口
 *
 * @author makejava
 * @since 2024-03-01 18:03:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-版本信息")
@RequestMapping("sysVersion")
public class SysVersionController extends BaseController {
    /**
     * 服务对象
     */
    private final SysVersionSvService sysVersionSvService;
    private final MapperFacade mapperFacade;
    private final SysVersionItemService sysVersionItemService;
    private final SysVersionNotityService sysVersionNotityService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询版本控制-版本信息")
    public R<IPage<SysVersion>> getPage(PageParamSimple pageParamSimple, SysVersionParam param) {
        PageParam<SysVersion> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysVersionSvService.getPage(page, param));
    }

    /**
     * 查询全部版本控制-版本信息
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询全部", notes = "查询全部版本控制-版本信息")
    public R<List<SysVersion>> getList(SysVersionParam param) {
        return R.ok(this.sysVersionSvService.getList(param));
    }

    /**
     * 获取历史版本信息列表
     *
     * @param pageParamSimple 分页信息
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/lastList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取历史版本信息列表", notes = "获取历史版本信息列表")
    public R<IPage<SysVersion>> getLastList(PageParamSimple pageParamSimple, SysVersionParam param) {
        PageParam<SysVersion> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysVersionSvService.getLastList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查版本控制-版本信息详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysVersion> selectOne(@PathVariable Integer id) {
        return R.ok(this.sysVersionSvService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysVersion 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加版本控制-版本信息")
    @Log(title = "版本控制-版本信息", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysVersion.id"})
    public R insert(@RequestBody SysVersion sysVersion) {
        return R.toResult(this.sysVersionSvService.saOrUp(sysVersion));
    }

    /**
     * 修改数据
     *
     * @param sysVersion 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新版本控制-版本信息")
    @Log(title = "版本控制-版本信息", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysVersion sysVersion) {
        return R.toResult(this.sysVersionSvService.saOrUp(sysVersion));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除版本控制-版本信息")
    @Log(title = "版本控制-版本信息", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> ids) {
        return R.toResult(this.sysVersionSvService.removeByIds(ids));
    }

    /**
     * 获取更新版本信息
     *
     * @return
     */
    @GetMapping("/getLastVersion")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "获取更新版本信息", notes = "获取更新版本信息")
    public R<SysVersion> getLastVersion() {
        //获取当前已更新的最新版本
        SysVersion version = sysVersionSvService.getlastVersion();
        //获取最新版本更新小项
        List<SysVersionItem> items = sysVersionItemService.list(new LambdaQueryWrapper<SysVersionItem>().ge(SysVersionItem::getVersionId, version.getId()));
        version.setVersionItemList(items);
        return R.ok(version);
    }

    /**
     * 检查更新
     *
     * @return
     */
    @GetMapping("/getVersion")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "检查更新", notes = "检查更新")
    public R<CurVersionDto> getVersion() {
        String versionNum = ZhongkangConfig.getVersion();
        //获取当前已更新的最新版本
        SysVersion lastVersion = sysVersionSvService.getlastVersion();
        CurVersionDto version = new CurVersionDto();
        if (lastVersion.getVersionNumber().equals(versionNum)) {
            version.setLastVersion(1);
        } else {
            version.setLastVersion(0);
        }
        //获取最新版本更新小项
        List<SysVersionItem> items = sysVersionItemService.list(new LambdaQueryWrapper<SysVersionItem>().ge(SysVersionItem::getVersionId, lastVersion.getId()));
        lastVersion.setVersionItemList(items);
        version.setVersion(lastVersion);
        version.setCurVersion(versionNum);
        //判断当前用户是否是新版版的第一次登录
        Long count = sysVersionNotityService.count(new LambdaQueryWrapper<SysVersionNotity>().eq(SysVersionNotity::getUserNo, SecurityUtils.getUserNo()).eq(SysVersionNotity::getVersionId, versionNum));
        System.out.println("count=" + count);
        if (count > 0) {
            version.setFirstShow(0);
        } else {
            version.setFirstShow(1);
        }
        return R.ok(version);
    }

    /**
     * 记录用户新版已读记录
     *
     * @return
     */
    @GetMapping("/saveVersionNotify")
    //@PreAuthorize("@ss.hasPermi('::saveVersionNotify')")
    @ApiOperation(value = "记录用户新版已读记录", notes = "记录用户新版已读记录")
    public R<String> saveVersionNotify() {
        SysVersionNotity item = new SysVersionNotity();
        item.setVersionId(ZhongkangConfig.getVersion());
        item.setStatus(1);
        item.setCreateTime(new Date());
        item.setUserNo(SecurityUtils.getUserNo());
        sysVersionNotityService.save(item);
        return R.ok("操作成功！");
    }

}


