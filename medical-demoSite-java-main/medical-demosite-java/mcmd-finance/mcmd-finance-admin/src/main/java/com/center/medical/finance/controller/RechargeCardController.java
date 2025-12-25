package com.center.medical.finance.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.bean.param.OldFamilyConParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.vo.RechargeCardVo;
import com.center.medical.finance.service.CardPaywayService;
import com.center.medical.finance.service.CardService;
import com.center.medical.finance.service.CardTypeService;
import com.center.medical.finance.service.RechargeCardService;
import com.center.medical.system.service.CodeConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 体检卡管理-卡充值(Card)表控制层
 *
 * @author ay
 * @since 2023-03-16 11:30:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检卡管理-卡充值")
@RequestMapping("finance/rechargeCard")
public class RechargeCardController extends BaseController {
    /**
     * 服务对象
     */
    private final RechargeCardService rechargeCardService;
    private final MapperFacade mapperFacade;
    private final CardPaywayService cardPaywayService;
    private final CardTypeService cardTypeService;
    private final CardService cardService;
    private final CodeConfigService codeConfigService;

    private String reservationCardFlag = Constants.RESERVATION_CARD_FLAG;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【体检卡管理-卡充值】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null),
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null),
                new InterfaceVo("领取人信息", "GET", "/finance/sendCard/getLqrData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null),
                new InterfaceVo("积分充值详情", "GET", "/member/member/recharge", "07.会员系统->客服管理-会员管理-中心会员->积分充值详情", null)
        );
        return R.ok(new FunctionVo("09.财务管理", "体检卡管理-卡充值", interfaceVos, "09.财务管理"));
    }


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
    public R<IPage<RechargeCardVo>> getPage(PageParamSimple pageParamSimple, RechargeCardParam param) {
        PageParam<RechargeCardVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.rechargeCardService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/add")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "跳转到增加页面数据", notes = "跳转到增加页面数据")
    public R add() {
        Map<String, Object> map = new HashMap<>();
        String userName = SecurityUtils.getUsername();
        String fzxId = SecurityUtils.getCId();
        map.put("userName", userName);
        map.put("fzxId", fzxId);
        return R.ok(map);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存充值", notes = "保存充值")
    @Log(title = "体检卡", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"card.id"})
    public R insert(@RequestBody RCSaOrUpParam param) {
        return R.toResult(this.rechargeCardService.saOrUp(param));
    }


    /**
     * 获取卡办理付款方式
     *
     * @param id
     * @return
     */
    @GetMapping("/getCardForChargeList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取卡办理付款方式", notes = "获取卡办理付款方式")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<List<CardPayway>> getCardForChargeList(String id) {
        List<CardPayway> list = cardPaywayService.list(new QueryWrapper<CardPayway>().eq("id_charge", id));
        return R.ok(list);
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
     * 体检卡号搜索
     *
     * @param cardId
     * @return
     */
    @GetMapping("/getCardData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "体检卡号搜索", notes = "体检卡号搜索")
    @ApiImplicitParam(name = "cardId", value = "卡号")
    public R<HashMap> getCardData(String cardId) {
        HashMap map = rechargeCardService.getCardData(cardId);
        return R.ok(map);
    }


    /**
     * 体检卡消费
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateFee")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "体检卡消费", notes = "体检卡消费")
    @Log(title = "体检卡", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 3000, message = "正在处理中，请稍等...")
    @ApiOperationSupport(ignoreParameters = {"card.id"})
    public R saveOrUpdateFee(@RequestBody SaOrUpFeeParam param) {
        return R.toResult(this.rechargeCardService.saveOrUpdateFee(param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "体检卡消费明细", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RechargeCardParam param) {
        List<RechargeCardVo> list = rechargeCardService.getExportData(param);
        ExcelUtil<RechargeCardVo> util = new ExcelUtil<RechargeCardVo>(RechargeCardVo.class);
        util.exportExcel(response, list, "体检卡消费明细");
    }


    /**
     * 老系统体检卡查询
     *
     * @param cardId
     * @return
     */
    @GetMapping("/getOldCardData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "老系统体检卡查询", notes = "老系统体检卡查询")
    @ApiImplicitParam(name = "cardId", value = "卡号")
    public Object getOldCardData(String cardId) {
        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", reservationCardFlag);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        GetOldCardParam oldCardParam = new GetOldCardParam(rsaConfig.getAuthCode(), cardId);
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(oldCardParam), rsaConfig.getPublicKey());
        RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
        log.info("param里面的数据:{}", rsaParam);
        //发送post请求
        String s = HttpUtils.sendPost(rsaConfig.getAdress() + "getCardData.action", JSONUtil.toJsonStr(rsaParam));
        return JSON.parse(s);
    }


    /**
     * 老系统体检卡保存消费记录
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateOldFee")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @RepeatSubmit(interval = 3000, message = "正在处理中，请稍等...")
    @Log(title = "体检卡保存消费记录", businessType = BusinessType.INSERT)
    @ApiOperation(value = "体检卡保存消费记录", notes = "体检卡保存消费记录")
    public R saveOrUpdateOldFee(@RequestBody CardConsumeParam param) {
        String s = cardService.saveOrUpdateFee(param);
        return R.ok(s);
    }


    /**
     * 老系统会员卡查询
     *
     * @param param
     * @return
     */
    @GetMapping("/getOldMemberCardData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "老系统会员卡查询", notes = "老系统会员卡查询")
    public Object getOldMemberCardData(OldMemberCardParam param) {
        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", reservationCardFlag);
        log.info("rsaConfig里面的数据:{}", rsaConfig);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        param.setAuthCode(rsaConfig.getAuthCode());
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), rsaConfig.getPublicKey());
        RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
        //发送post请求
        String s = HttpUtils.sendPost(rsaConfig.getAdress() + "getMemberCardData.action", JSONUtil.toJsonStr(rsaParam));
        return JSON.parse(s);
    }




    /**
     * 老系统体检卡查询
     *
     * @param idcardno
     * @return
     */
    @GetMapping("/getOldFamilyCard")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "老系统家庭卡查询", notes = "老系统家庭卡查询")
    @ApiImplicitParam(name = "idcardno", value = "身份证号")
    public Object getOldFamilyCard(String idcardno) {
        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", reservationCardFlag);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        GetOldFamilyCardParam oldCardParam = new GetOldFamilyCardParam(rsaConfig.getAuthCode(), idcardno);
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(oldCardParam), rsaConfig.getPublicKey());
        RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
        log.info("param里面的数据:{}", rsaParam);
        //发送post请求
        String s = HttpUtils.sendPost(Constants.OLD_FAMILYCARD_URL + Constants.OLD_FAMILYCARD_QUERY, JSONUtil.toJsonStr(rsaParam));
        return JSON.parse(s);
    }





    /**
     * 老系统体检卡查询
     *
     * @param param
     * @return
     */
    @PostMapping("/oldFamilyConsumption")
    @ApiOperation(value = "老系统家庭卡消费", notes = "老系统家庭卡消费")
    @RepeatSubmit(interval = 3000, message = "正在处理中，请稍等...")
    @Log(title = "老系统家庭卡消费", businessType = BusinessType.INSERT)
    public Object oldFamilyConsumption(OldFamilyConParam param) {
        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", reservationCardFlag);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        param.setAuthCode(rsaConfig.getAuthCode());
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), rsaConfig.getPublicKey());
        RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
        log.info("param里面的数据:{}", rsaParam);
        //发送post请求
        String s = HttpUtils.sendPost(Constants.OLD_FAMILYCARD_URL + Constants.OLD_FAMILYCARD_CONSUMPTION, JSONUtil.toJsonStr(rsaParam));
        return JSON.parse(s);
    }
}

