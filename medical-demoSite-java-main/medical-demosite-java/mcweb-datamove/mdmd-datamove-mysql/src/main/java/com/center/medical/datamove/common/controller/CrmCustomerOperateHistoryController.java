package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.CrmCustomerOperateHistory;
import com.center.medical.datamove.common.service.CrmCustomerOperateHistoryService;
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
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CrmCustomerOperateHistory)接口
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
@RequestMapping("crmCustomerOperateHistory")
public class CrmCustomerOperateHistoryController extends BaseController {
    /**
     * 服务对象
     */
    private final CrmCustomerOperateHistoryService crmCustomerOperateHistoryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple           分页参数
     * @param crmCustomerOperateHistory 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
    public R<IPage<CrmCustomerOperateHistory>> getPage(PageParamSimple pageParamSimple, CrmCustomerOperateHistory crmCustomerOperateHistory) {
        PageParam<CrmCustomerOperateHistory> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.crmCustomerOperateHistoryService.getPage(page, crmCustomerOperateHistory));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<CrmCustomerOperateHistory> selectOne(@PathVariable String id) {
        return R.ok(this.crmCustomerOperateHistoryService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param crmCustomerOperateHistory 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
    @Log(title = "客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"crmCustomerOperateHistory.id"})
    public R insert(@RequestBody CrmCustomerOperateHistory crmCustomerOperateHistory) {
        return R.toResult(this.crmCustomerOperateHistoryService.save(crmCustomerOperateHistory));
    }

    /**
     * 修改数据
     *
     * @param crmCustomerOperateHistory 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
    @Log(title = "客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放", businessType = BusinessType.UPDATE)
    public R update(@RequestBody CrmCustomerOperateHistory crmCustomerOperateHistory) {
        return R.toResult(this.crmCustomerOperateHistoryService.updateById(crmCustomerOperateHistory));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
    @Log(title = "客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.crmCustomerOperateHistoryService.removeByIds(ids));
    }
}

