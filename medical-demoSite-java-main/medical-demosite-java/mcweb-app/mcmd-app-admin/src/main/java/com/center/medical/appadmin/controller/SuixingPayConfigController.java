package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.SuixingPayConfig;
import com.center.medical.appadmin.bean.param.SuixingPayConfigParam;
import com.center.medical.appadmin.bean.param.SuixingSaOrUpParam;
import com.center.medical.appadmin.bean.vo.SuixingPayConfigVo;
import com.center.medical.appadmin.service.SuixingPayConfigService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 随行支付配置参数(SuixingPayConfig)接口
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "随行支付配置参数")
@RequestMapping("app/suixingPayConfig")
public class SuixingPayConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final SuixingPayConfigService suixingPayConfigService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询随行支付配置参数")
    public R<IPage<SuixingPayConfigVo>> getPage(PageParamSimple pageParamSimple, SuixingPayConfigParam param) {
        PageParam<SuixingPayConfigVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.suixingPayConfigService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "详情", notes = "根据id查随行支付配置参数详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SuixingPayConfig> selectOne(@PathVariable String id) {
        return R.ok(this.suixingPayConfigService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @Log(title = "随行支付配置参数", businessType = BusinessType.INSERT)
    public R saOrUp(@RequestBody @Valid SuixingSaOrUpParam param) {
        return R.ok(this.suixingPayConfigService.saOrUp(param));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除", notes = "删除随行支付配置参数")
    @Log(title = "随行支付配置参数", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.ok(this.suixingPayConfigService.deleteByIds(ids));
    }

}
