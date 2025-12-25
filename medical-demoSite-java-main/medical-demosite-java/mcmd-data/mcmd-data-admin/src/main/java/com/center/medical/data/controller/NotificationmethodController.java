package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.service.NotificationmethodService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报告发放方式表(Notificationmethod)表控制层
 *
 * @author ay
 * @since 2022-11-18 10:55:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告发放方式")
@RequestMapping("notificationmethod")
public class NotificationmethodController extends BaseController {
    /**
     * 服务对象
     */
    private final NotificationmethodService notificationmethodService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param notificationmethod 查询实体
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询通知方式（领取方式）表")
    public R<IPage<Notificationmethod>> getListData(PageParamSimple pageParamSimple, Notificationmethod notificationmethod) {
        PageParam<Notificationmethod> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.notificationmethodService.getList(page, notificationmethod));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查通知方式（领取方式）表详情")
    public R<Notificationmethod> selectOne(@PathVariable String id) {
        return R.ok(this.notificationmethodService.getInfoById(id));
    }

    /**
     * 添加或修改数据
     *
     * @param notificationmethod 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改数据", notes = "添加或修改通知方式（领取方式）表")
    @Log(title = "通知方式（领取方式）表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"notificationmethod.id"})
    public R saveOrUpdate(@RequestBody Notificationmethod notificationmethod) {
        String s = notificationmethodService.saveOrUpdateNotificationmethod(notificationmethod);
        return R.ok(s);
    }


    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除通知方式（领取方式）表")
    @Log(title = "通知方式（领取方式）表", businessType = BusinessType.DELETE)
    public R remove(@PathVariable String ids) {
        String s = notificationmethodService.removeNotificationmethod(ids);
        return R.ok(s);
    }

    /**
     * 下拉框数据
     *
     * @return
     */
    @GetMapping("/getBoxData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "下拉框数据", notes = "下拉框数据")
    public R getBoxData() {
        List<Notificationmethod> list = notificationmethodService.list(new QueryWrapper<Notificationmethod>().eq("is_delete", 0));
        return R.ok(list);
    }

}

