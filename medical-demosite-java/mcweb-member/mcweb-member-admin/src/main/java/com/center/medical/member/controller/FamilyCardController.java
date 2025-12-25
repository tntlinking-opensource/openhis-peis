package com.center.medical.member.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.service.CardService;
import com.center.medical.finance.service.CardTypeService;
import com.center.medical.member.bean.param.FamilyCardParam;
import com.center.medical.member.bean.param.SaveConParam;
import com.center.medical.member.service.FamilyMemberService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.service.ISysConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 家庭会员-家庭卡消费(Peispatientarchive)表控制层
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "家庭会员-家庭卡消费")
@RequestMapping("/member/familyCard")
public class FamilyCardController extends BaseController {
    /**
     * 服务对象
     */
    private final FamilyMemberService familyMemberService;
    private final MapperFacade mapperFacade;
    private final CardService cardService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final CardTypeService cardTypeService;
    private final ISysConfigService iSysConfigService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【家庭会员-家庭卡消费】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null)

        );
        return R.ok(new FunctionVo("07.会员系统", "咨询与随访", interfaceVos, "07.会员系统"));
    }

    /**
     * 分页查询所有数据
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询", notes = "家庭会员-家庭卡消费")
    public R<Card> getPage(FamilyCardParam param) {
        Card card = familyMemberService.familyCardList(param);
        return R.ok(card);
    }


    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveConsumex")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "家庭卡消费", notes = "家庭卡消费")
    @Log(title = "家庭卡消费", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R saveConsumex(@RequestBody SaveConParam param) {
        //TODO wait 消费操作整合到卡消费统一处理
        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
        Map<String, Object> result = familyMemberService.saveConsumex(param);
        JSONObject obj= JSONUtil.parseObj(result);
        if("success".equals(obj.getStr("status"))) {
            String phone=obj.getStr("phone");
            if(StringUtils.isNotEmpty(phone)) {
                try {
                    SDKTestSendTemplateSMS.sendFamilyCardMsg(smsConfig,phone
                            , obj.getJSONArray("data").toList(String.class).toArray(new String[4])
                            , false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return R.ok();
        }else {
            return R.fail(obj.getStr("msg"));
        }
    }


    /**
     * 加载卡号
     *
     * @param cardno
     * @return
     */
    @GetMapping("/searchCardno")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "加载卡号", notes = "卡办理页面-加载卡号")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R searchCardno(String cardno) {
        //体检卡
        Card card = cardService.getOne(new QueryWrapper<Card>()
                .eq("card_no", cardno).eq("is_delete", 0));
        Map<String, Object> result = new HashMap<String, Object>();
        if (card == null) {
            result.put("status", "failed");
            result.put("msg", "卡号不存在");
            return R.ok(result);
        }
        //家庭卡卡类型id
        CardType type = cardTypeService.getInfoById(Card.JTK);
        if (!Card.JTK.equals(card.getTypeId())) {
            result.put("status", "failed");
            result.put("msg", "此卡号不是" + type.getTypeName());
            return R.ok(result);
        }
        //体检者档案表
        Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>().eq("patientcardno", cardno).eq("is_main", 1));
        if (archive != null) {
            result.put("status", "failed");
            result.put("msg", "卡号已绑定档案：" + archive.getPatientarchiveno());
            return R.ok(result);
        }
        card.setTypeName(type.getTypeName());
        result.put("status", "success");
        result.put("msg", card);
        return R.ok(result);
    }


}

