package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.bean.param.SCsaOrUpParam;
import com.center.medical.finance.bean.param.SendCardParam;
import com.center.medical.finance.bean.vo.ChangeDataVo;
import com.center.medical.finance.bean.vo.SendCardVo;
import com.center.medical.finance.service.CardService;
import com.center.medical.finance.service.CardTypeService;
import com.center.medical.sellcrm.bean.vo.AllOrderDataVo;
import com.center.medical.sellcrm.bean.vo.BasicDataVo;
import com.center.medical.sellcrm.bean.vo.OrderMealVo;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.OrderandcomboService;
import com.center.medical.system.bean.vo.LqrDataVo;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 体检卡管理-卡发放(Card)表控制层
 *
 * @author ay
 * @since 2023-02-23 17:48:13
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检卡管理-卡发放")
@RequestMapping("finance/sendCard")
public class SendCardController extends BaseController {
    /**
     * 服务对象
     */
    private final CardService cardService;
    private final MapperFacade mapperFacade;
    private final CardTypeService cardTypeService;
    private final ISysUserService sysUserService;
    private final CreateorderService createorderService;
    private final OrderandcomboService orderandcomboService;
    private final CreatecomboService createcomboService;

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
    public R<IPage<SendCardVo>> getPage(PageParamSimple pageParamSimple, SendCardParam param) {
        // TODO: 2023/2/23 缺少app的表
        PageParam<SendCardVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.cardService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检卡详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Card> selectOne(@PathVariable String id) {
        return R.ok(this.cardService.getInfoById(id));
    }

    /**
     * 新增发卡保存
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增发卡保存", notes = "新增发卡保存")
    @Log(title = "体检卡", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"card.id"})
    public R insert(@RequestBody SCsaOrUpParam param) {
        return R.toResult(this.cardService.saOrUp(param));
    }


    /**
     * 获取体检卡类型
     *
     * @param type
     * @param key
     * @return
     */
    @GetMapping("/getTypeData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取体检卡类型", notes = "获取体检卡类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "卡类型判断 0：体检卡 1：会员卡"),
            @ApiImplicitParam(name = "key", value = "group团检专属发卡，add普通发卡")
    })
    public R getTypeData(Integer type, String key) {
        QueryWrapper<CardType> and = new QueryWrapper<>();
        if (0 == type) {
            // 体检卡
            and.eq("type", 0);
        } else if (1 == type) {
            // 会员卡
            and.eq("type", 1);
        }
        if ("group".equals(key)) {
            and.eq("id", Card.GROUP);
        }
        List<HashMap> mapList = new ArrayList<HashMap>();
        List<CardType> cty = cardTypeService.list(and);
        if (CollectionUtils.isNotEmpty(cty)) {
            for (CardType bean : cty) {
                HashMap map = new HashMap();
                if (bean != null && !"".equals(bean)) {
                    map.put("id", bean.getId());
                    map.put("text", bean.getTypeName());
                    mapList.add(map);
                }
            }
        }
        return R.ok(mapList);
    }


    /**
     * getChangeData
     *
     * @param typeId
     * @return
     */
    @GetMapping("/getChangeData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "卡类型改变后获取卡前缀等字段", notes = "卡类型改变后获取卡前缀等字段")
    @ApiImplicitParam(name = "typeId", value = "体检卡类型id")
    public R<ChangeDataVo> getChangeData(String typeId) {
        ChangeDataVo vo = cardService.getChangeData(typeId);
        return R.ok(vo);
    }

    /**
     * 承送人名处搜索
     *
     * @param pageParamSimple
     * @param srm
     * @return
     */
    @GetMapping("/getLqrData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领取人处搜索", notes = "领取人处搜索")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R<IPage<LqrDataVo>> getLqrData(PageParamSimple pageParamSimple, String srm) {
        PageParam<LqrDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<LqrDataVo> iPage = sysUserService.getLqrData(page, srm);
        return R.ok(iPage);
    }


    /**
     * 体检卡团检专属卡选择订单
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllOrderData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团检专属卡-订单搜索", notes = "体检卡团检专属卡选择订单")
    @ApiImplicitParam(name = "key", value = "输入码或名称")
    public R<List<AllOrderDataVo>> getAllOrderData(String key) {
        List<AllOrderDataVo> list = createorderService.getAllOrderData(key);
        return R.ok(list);
    }


    /**
     * 团检专属卡-套餐搜索
     *
     * @param id
     * @param key
     * @return
     */
    @GetMapping("/getOrderMealData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团检专属卡-套餐搜索", notes = "团检专属卡-套餐搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "key", value = "输入码或名称")
    })
    public R<List<OrderMealVo>> getOrderMealData(String id, String key) {
        List<OrderMealVo> list = orderandcomboService.getOrderMealData(id, key);
        return R.ok(list);
    }


    /**
     * 修改领取人
     *
     * @param ids
     * @param id
     * @return
     */
    @PutMapping("/updateGotMan")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "修改领取人", notes = "修改领取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "卡id集合"),
            @ApiImplicitParam(name = "id", value = "领取人id")
    })
    public R updateGotMan(@RequestParam List<String> ids, @RequestParam String id) {
        Boolean b = cardService.updateGotMan(ids, id);
        return R.toResult(b);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/remove")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParam(name = "ids", value = "卡id集合")
    public R remove(@RequestParam List<String> ids) {
        Boolean b = cardService.removeCard(ids);
        return R.toResult(b);
    }


    /**
     * 绑定时按手机号查询APP用户
     *
     * @param key
     * @return
     */
    @GetMapping("/getAutoCompleteData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "绑定-绑定用户下拉", notes = "绑定-绑定用户下拉")
    @ApiImplicitParam(name = "key", value = "手机号")
    public R getAutoCompleteData(PageParamSimple pageParamSimple, String key) {
        // TODO: 2023/2/24 涉及到app表，未完成
        PageParam<SendCardVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok();
    }


    /**
     * 绑定
     *
     * @param ids
     * @param id
     * @return
     */
    @PutMapping("/saveBind")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "绑定", notes = "体检卡绑定手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "卡id集合"),
            @ApiImplicitParam(name = "id", value = "绑定人id")
    })
    public R saveBind(@RequestParam List<String> ids, @RequestParam String id) {
        Boolean b = cardService.saveBind(ids, id);
        return R.toResult(b);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "卡发放", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SendCardParam param) {
        List<SendCardVo> list = cardService.getExportData(param);
        ExcelUtil<SendCardVo> util = new ExcelUtil<SendCardVo>(SendCardVo.class);
        util.exportExcel(response, list, "体检卡发放");
    }


    /**
     * 获取所有基础套餐
     *
     * @param key
     * @return
     */
    @GetMapping("/getBasicData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有基础套餐", notes = "获取所有基础套餐")
    @ApiImplicitParam(name = "key", value = "输入码或名称，模糊查询")
    public R<List<BasicDataVo>> getBasicData(String key) {
        List<BasicDataVo> list = createcomboService.getBasicData(key);
        return R.ok(list);
    }

}

