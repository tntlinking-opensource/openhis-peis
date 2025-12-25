package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.SellRemind;
import com.center.medical.sellcrm.bean.vo.SellRemindVo;
import com.center.medical.sellcrm.service.SellRemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 销售提醒(SellRemind)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "备忘提醒")
@RequestMapping("sell/remind")
public class SellRemindController extends BaseController {
    /**
     * 服务对象
     */
    private final SellRemindService sellRemindService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询销售提醒")
    public R<IPage<SellRemindVo>> getPage(PageParamSimple pageParamSimple) {
        PageParam<SellRemindVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //看自己的提醒
        String username = SecurityUtils.getUsername();
        return R.ok(this.sellRemindService.getPage(page, username));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查销售提醒详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SellRemind> selectOne(@PathVariable String id) {
        return R.ok(this.sellRemindService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sellRemind 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加销售提醒")
    @Log(title = "销售提醒", businessType = BusinessType.INSERT)
    public R insert(@RequestBody SellRemind sellRemind) {
        sellRemind.setStatus(0);
        sellRemind.setCreatedate(new Date());
        sellRemind.setCreateName(SecurityUtils.getUsername());
        return R.toResult(this.sellRemindService.save(sellRemind));
    }

    /**
     * 修改数据
     *
     * @param sellRemind 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新销售提醒")
    @Log(title = "销售提醒", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SellRemind sellRemind) {
        SellRemind itemDb = sellRemindService.getInfoById(sellRemind.getId());
        if (Objects.isNull(itemDb)) {
            throw new ServiceException("记录已被删除！");
        }
        sellRemind.setModifydate(new Date());
        return R.toResult(this.sellRemindService.updateById(sellRemind));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除销售提醒")
    @Log(title = "销售提醒", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sellRemindService.removeByIds(ids));
    }

    /**
     * 结束提醒
     *
     * @param ids 操作的对象主键id集合
     * @return
     */
    @PutMapping("/handle")
    @ApiOperation(value = "结束提醒", notes = "结束提醒")
    @ApiImplicitParam(name = "ids", value = "要操作的对象主键id集合，多个以英文逗号隔开")
    public R<Boolean> handle(@RequestParam(name = "ids", required = true) List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        SellRemind sellRemind = new SellRemind();
        sellRemind.setStatus(1);
        sellRemind.setModifydate(new Date());
        return R.ok(this.sellRemindService.update(sellRemind, new LambdaUpdateWrapper<SellRemind>()
                .in(SellRemind::getId, ids)));
    }
}

