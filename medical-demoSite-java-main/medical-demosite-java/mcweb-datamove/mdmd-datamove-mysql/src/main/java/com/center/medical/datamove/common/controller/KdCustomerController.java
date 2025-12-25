package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.KdCustomer;
import com.center.medical.datamove.common.service.KdCustomerService;
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
 * 金碟账户(KdCustomer)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "金碟账户")
@RequestMapping("kdCustomer")
public class KdCustomerController extends BaseController {
    /**
     * 服务对象
     */
    private final KdCustomerService kdCustomerService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param kdCustomer      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询金碟账户")
    public R<IPage<KdCustomer>> getPage(PageParamSimple pageParamSimple, KdCustomer kdCustomer) {
        PageParam<KdCustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.kdCustomerService.getPage(page, kdCustomer));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param accountNo 主键
     * @return 单条数据
     */
    @GetMapping("{accountNo}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据accountNo查金碟账户详情")
    @ApiImplicitParam(name = "accountNo", value = "要查询的对象的主键{accountNo}")
    public R<KdCustomer> selectOne(@PathVariable String accountNo) {
        return R.ok(this.kdCustomerService.getInfoById(accountNo));
    }

    /**
     * 新增数据
     *
     * @param kdCustomer 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加金碟账户")
    @Log(title = "金碟账户", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"kdCustomer.accountNo"})
    public R insert(@RequestBody KdCustomer kdCustomer) {
        return R.toResult(this.kdCustomerService.save(kdCustomer));
    }

    /**
     * 修改数据
     *
     * @param kdCustomer 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新金碟账户")
    @Log(title = "金碟账户", businessType = BusinessType.UPDATE)
    public R update(@RequestBody KdCustomer kdCustomer) {
        return R.toResult(this.kdCustomerService.updateById(kdCustomer));
    }

    /**
     * 删除数据
     *
     * @param accountNos 删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除金碟账户")
    @Log(title = "金碟账户", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> accountNos) {
        return R.toResult(this.kdCustomerService.removeByIds(accountNos));
    }
}

