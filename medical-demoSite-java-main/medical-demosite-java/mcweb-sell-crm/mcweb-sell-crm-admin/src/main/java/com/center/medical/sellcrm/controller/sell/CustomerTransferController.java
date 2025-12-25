package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.CustomerTransfer;
import com.center.medical.sellcrm.bean.param.SaveTransferParam;
import com.center.medical.system.bean.param.XsryDataParam;
import com.center.medical.sellcrm.service.CustomerTransferService;
import com.center.medical.system.bean.vo.XsryVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CustomerTransfer)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-21 09:03:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。")
@RequestMapping("sell/customerTransfer")
public class CustomerTransferController extends BaseController {
    /**
     * 服务对象
     */
    private final CustomerTransferService customerTransferService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/getXsryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。")
    public R<IPage<XsryVo>> getPage(PageParamSimple pageParamSimple, XsryDataParam param) {
        PageParam<XsryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customerTransferService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。详情")
    @ApiImplicitParam(name = "{id}", value = "要删除的对象{id}集合，多个以英文逗号（,）隔开")
    public R<CustomerTransfer> selectOne(@PathVariable String id) {
        return R.ok(this.customerTransferService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveTransfer")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "客户转移-转移参数", notes = "添加客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。")
    @Log(title = "客户转移-转移参数", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"customerTransfer.id"})
    public R saveTransfer(@RequestBody SaveTransferParam param) {
        return R.toResult(this.customerTransferService.saveTransfer(param));
    }


}

