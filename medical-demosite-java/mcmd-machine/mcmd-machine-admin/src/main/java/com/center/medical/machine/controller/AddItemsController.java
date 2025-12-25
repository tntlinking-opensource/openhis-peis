package com.center.medical.machine.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.model.TempFeeitem;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.ItemsService;
import com.center.medical.machine.bean.vo.APItemsVo;
import com.center.medical.machine.service.AddItemsService;
import com.center.medical.machine.service.RegistrationMachineService;
import com.center.medical.machine.service.impl.ReadCardServiceImpl;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.TempFeeitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "加项缴费")
@RequestMapping("machine/additionalPay")
public class AddItemsController extends BaseController {
    /**
     * 服务对象
     */
    private final AddItemsService addItemsService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final TempFeeitemService tempFeeitemService;
    private final ItemsService itemsService;
    private final RegistrationMachineService registrationMachineService;


    /**
     * 通过主键查询单条数据
     *
     * @param patientcode 主键
     * @return 单条数据
     */
    @GetMapping("/items")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "选择体检项目", notes = "选择体检项目")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<APItemsVo> selectOne(String patientcode) {
        List<Map> list = new ArrayList<>();
        //科室临时加项表
        List<TempFeeitem> tfs = tempFeeitemService.list(new QueryWrapper<TempFeeitem>()
                .eq("patientcode", patientcode).eq("is_delete", 0));
        List<Map<String, Object>> fitems = new ArrayList<Map<String, Object>>();
        BigDecimal temporaryProjectTotalPrice = new BigDecimal(0);
        for (TempFeeitem tf : tfs) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", tf.getId());
            map.put("factprice", tf.getPrice());
            Items items = itemsService.getInfoById(tf.getItemId());
            map.put("itemName", items.getExamfeeitemNameprn());
            fitems.add(map);
            temporaryProjectTotalPrice = temporaryProjectTotalPrice.add(BigDecimal.valueOf(tf.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
            list.add(map);
        }
        APItemsVo vo = new APItemsVo();
        vo.setTemporaryProjectTotalPrice(temporaryProjectTotalPrice);
        vo.setItems(list);
        vo.setPatientcode(patientcode);
        return R.ok(vo);
    }


    /**
     * 支付完成
     * @param patientcode
     * @return
     */
    @PostMapping("/complete")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "支付完成", notes = "支付完成")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R complete(String patientcode) {
        Map<String, Object> complete = registrationMachineService.complete(patientcode);
        ReadCardServiceImpl.nameList.clear();
        ReadCardServiceImpl.photo.clear();
        return R.ok(complete);
    }

}

