package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.bean.param.GetTempFeeitemParam;
import com.center.medical.bean.param.PaymentParam;
import com.center.medical.bean.vo.GetTempFeeitemVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import com.center.medical.pay.service.TongLianPayService;
import com.center.medical.service.DictpaywayService;
import com.center.medical.service.TempFeeitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 科室临时加项表(TempFeeitem)接口
 *
 * @author ay
 * @since 2024-04-22 15:27:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室加项")
@RequestMapping("/abteilung/tempFeeitem")
public class TempFeeitemController extends BaseController {
    /**
     * 服务对象
     */
    private final TempFeeitemService tempFeeitemService;
    private final MapperFacade mapperFacade;
    private final DictpaywayService dictpaywayService;
    private final TongLianPayService tongLianPayService;
    private  final MiddleDbInterfaceService middleDbInterfaceService;


    /**
     * 查询科室加项数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getTempFeeitem")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询科室加项数据", notes = "查询科室加项数据")
    public R<GetTempFeeitemVo> getTempFeeitem(GetTempFeeitemParam param) {
        GetTempFeeitemVo vo = tempFeeitemService.getTempFeeitem(param);
        return R.ok(vo);
    }



    /**
     * 获取全部支付方式
     *
     * @return 所有数据
     */
    @GetMapping("/getPayWayData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取全部支付方式", notes = "获取全部支付方式")
    public R<List<Dictpayway>> getPayWayData() {
        //设置条件
        QueryWrapper<Dictpayway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        List<Dictpayway> list = dictpaywayService.list(new LambdaQueryWrapper<Dictpayway>()
                .eq(Dictpayway::getIsDelete, 0)
                .in(Dictpayway::getId, "22", "24","101")
                .orderByAsc(Dictpayway::getSeq)
        );
        return R.ok(list);
    }





    /**
     * 支付
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @PostMapping("/payment")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "付款", notes = "先付钱，付钱成功后调收费接口")
    public R<PayResultDto> payment(@RequestBody PaymentParam param) {
        PayResultDto dto = tempFeeitemService.payment(param);
        return R.ok(dto);
    }



    /**
     * 支付完调用
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @PostMapping("/callBack")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "支付完调用", notes = "支付完调用")
    public R<Boolean> callBack(@RequestBody PaymentParam param) throws Exception {
        if (StringUtils.isEmpty(param.getConsumeId())){
            throw new ServiceException("请输入交易流水号!");
        }
        Map<String, String> map = tongLianPayService.query("", param.getConsumeId(),param.getType());
        //查询支付状态，只有0000是支付成功
        if (ObjectUtils.isEmpty(map) || ObjectUtils.isEmpty(map.get("trxstatus")) || !"0000".equals(map.get("trxstatus"))){
            throw new ServiceException("该流水号未支付成功!");
        }
        Boolean b = tempFeeitemService.callBack(param);
        //数据重发
        middleDbInterfaceService.insert(param.getPatientcode());
        return R.ok(b);
    }


}
