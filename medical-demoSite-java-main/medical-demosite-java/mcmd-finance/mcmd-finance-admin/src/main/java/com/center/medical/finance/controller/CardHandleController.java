package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.bean.param.CHPageParam;
import com.center.medical.finance.bean.param.CHSaOrUpParam;
import com.center.medical.finance.bean.vo.CHPageVo;
import com.center.medical.finance.service.CardHandleService;
import com.center.medical.finance.service.CardPaywayService;
import com.center.medical.finance.service.CardService;
import com.center.medical.service.DictpaywayService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 体检卡(Card)表控制层
 *
 * @author ay
 * @since 2023-03-30 18:47:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检卡管理-体检卡办理")
@RequestMapping("finance/cardHandle")
public class CardHandleController extends BaseController {
    /**
     * 服务对象
     */
    private final CardHandleService cardHandleService;
    private final MapperFacade mapperFacade;
    private final CardService cardService;
    private final CardPaywayService cardPaywayService;
    private final DictpaywayService dictpaywayService;
    

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检卡")
    public R<IPage<CHPageVo>> getPage(PageParamSimple pageParamSimple, CHPageParam param) {
        PageParam<CHPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.cardHandleService.getList(page, param));
    }


    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "体检卡办理保存或修改", notes = "添加体检卡")
    @Log(title = "体检卡", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"card.id"})
    public R insert(@RequestBody CHSaOrUpParam param) {
        Boolean b = cardHandleService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * @param response
     * @param param
     */
    @Log(title = "体检卡管理", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出体检卡管理", notes = "导出体检卡管理")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CHPageParam param) {
        List<CHPageVo> list = cardHandleService.getExportData(param);
        ExcelUtil<CHPageVo> util = new ExcelUtil<CHPageVo>(CHPageVo.class);
        util.exportExcel(response, list, "体检卡管理");
    }


    /**
     * 获取右侧卡办理收款方式
     *
     * @param id
     * @return
     */
    @GetMapping("/getChargeListData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取右侧卡办理收款方式", notes = "获取右侧卡办理收款方式")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<List<CardPayway>> getChargeListData(String id) {
        //体检卡
        Card c = cardService.getInfoById(id);
        List<CardPayway> list = null;
        //卡办理id等于空
        if (c == null || c.getProcessId() == null) {
            list = new ArrayList<>();
        } else {
            //根据卡办理id获取付款方式和收费员
            list = cardPaywayService.selectFeechargerList(c.getProcessId());
        }
        return R.ok(list);
    }


    /**
     * 获取全部支付方式
     *
     * @param pageParamSimple 分页参数
     * @param key             inputCode
     * @return 所有数据
     */
    @GetMapping("/getPayWayData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取全部支付方式", notes = "获取全部支付方式")
    public R<IPage<Dictpayway>> getPayWayData(PageParamSimple pageParamSimple, String key, String type) {
        PageParam<Dictpayway> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //设置条件
        QueryWrapper<Dictpayway> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("input_code", key.trim().toUpperCase());
        }
        //预收不能选择记账
        if ("1".equals(type)) {
            queryWrapper.ne("id", "4");
        }
        PageParam<Dictpayway> pageParam = dictpaywayService.page(page, queryWrapper);
        return R.ok(pageParam);
    }



}

