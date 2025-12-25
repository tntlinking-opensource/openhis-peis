package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysNoticeConfig;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.param.NoticeConfigPageParam;
import com.center.medical.system.bean.param.NoticeConfigSaOrUpParam;
import com.center.medical.system.service.SysNoticeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统授权记录(SysAuthLog)接口
 *
 * @author makejava
 * @since 2024-01-17 20:22:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "通知配置表")
@RequestMapping("/system/noticeConfig")
public class SysNoticeConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final SysNoticeConfigService sysNoticeConfigService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysAuthLog      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询通知配置表")
    public R<IPage<SysNoticeConfig>> getPage(PageParamSimple pageParamSimple, NoticeConfigPageParam param) {
        PageParam<SysNoticeConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysNoticeConfigService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查通知配置表")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysNoticeConfig> selectOne(@PathVariable String id) {
        return R.ok(this.sysNoticeConfigService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param params 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或更新", notes = "添加系统授权记录")
    @Log(title = "通知配置表", businessType = BusinessType.INSERT)
    public R insert(@RequestBody NoticeConfigSaOrUpParam params) {
        return R.toResult(this.sysNoticeConfigService.saOrUp(params));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除系统授权记录")
    @Log(title = "系统授权记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@RequestParam List<String> ids) {
        //逻辑删除，把操作者也给存进去
        SysNoticeConfig sysNoticeConfig = new SysNoticeConfig();
        sysNoticeConfig.setOperator(SecurityUtils.getUsername());
        sysNoticeConfig.setIsDelete(1);
        sysNoticeConfigService.update(sysNoticeConfig,new LambdaQueryWrapper<SysNoticeConfig>().in(SysNoticeConfig::getId,ids));
        return R.toResult(Boolean.TRUE);
    }
}


