package com.center.medical.pacslis.controller.pacs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.param.GetItemsParam;
import com.center.medical.data.service.ItemsService;
import com.center.medical.pacslis.bean.model.PacsBasePart;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import com.center.medical.pacslis.bean.param.ComMinParam;
import com.center.medical.pacslis.bean.param.PrSaOrUpParam;
import com.center.medical.pacslis.bean.param.ReservationUserParam;
import com.center.medical.pacslis.bean.vo.ReservationUserVo;
import com.center.medical.pacslis.service.PacsBasePartService;
import com.center.medical.pacslis.service.PacsPeispatientService;
import com.center.medical.pacslis.service.PacsPeispatientfeeitemService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.DictmarriageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * PACS登记信息查询-登记
 *
 * @author ay
 * @since 2022-12-29 11:39:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS登记信息查询-登记")
@RequestMapping("/pacs/preregistration")
public class PreregistrationController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsPeispatientService pacsPeispatientService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;
    private final DictmarriageService dictmarriageService;
    private final PacsPeispatientfeeitemService peispatientfeeitemService;
    private final ItemsService itemsService;
    private final PacsBasePartService pacsBasePartService;


    /**
     * 【PACS-PACS登记信息查询】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【PACS-PACS登记信息查询】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("基本信息", "GET", "/pacs/preregistration/getPatientData", "11.pacs/lis->PACS-PACS登记信息查询-登记->基本信息", null),
                new InterfaceVo("获取会员类型", "GET", "/total/RecordManage/getPatientTypeData", "10.总检-报告系统->总检管理-对比报告->获取会员类型", null),
                new InterfaceVo("婚姻下拉", "GET", "/total/RecordManage/getMarriageData", "11.pacs/lis->PACS-PACS登记信息查询-登记->婚姻下拉", null),
                new InterfaceVo("右下角查询", "GET", "/total/RecordManage/getReservationUser", "11.pacs/lis->PACS-PACS登记信息查询-登记->右下角查询,取得已预约客户", null),
                new InterfaceVo("获取检查部位", "GET", "/total/RecordManage/getAutoCompleteData", "11.pacs/lis->PACS-PACS登记信息查询-登记->获取登记右上方检查部位", null),
                new InterfaceVo("获取收费项目", "GET", "/total/RecordManage/getItems", "11.pacs/lis->PACS-PACS登记信息查询-登记->获取登记右侧收费项目", null),
                new InterfaceVo("预约、登记、保存右侧列表", "POST", "/total/RecordManage/saveOrUpdate", "11.pacs/lis->PACS-PACS登记信息查询-登记->预约、登记、保存右侧列表  ", null)
        );
        return R.ok(new FunctionVo("11.pacs/lis", "PACS-PACS登记信息查询", interfaceVos, "11.pacs/lis"));
    }


    /**
     * 婚姻下拉
     *
     * @return
     */
    @GetMapping("/getMarriageData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "婚姻下拉", notes = "婚姻下拉")
    public R getMarriageData() {
        return R.ok(MarriageType.toMap());
    }


    /**
     * 取得已预约客户
     *
     * @return
     */
    @GetMapping("/getReservationUser")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右下角查询", notes = "右下角查询,取得已预约客户")
    @ApiImplicitParam(name = "key", value = "团体名称或输入码")
    public R getReservationUser(PageParamSimple pageParamSimple, ReservationUserParam param) {
        PageParam<PacsPeispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //用户编号和类型
        param.setUserNo(SecurityUtils.getUserNo());
        param.setType(0);
        //分页查询
        PageParam<ReservationUserVo> pageParam = pacsPeispatientService.getReservationUser(page, param);
        return R.ok(pageParam);
    }


    /**
     * 获取当前选中的已预约用户信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    @GetMapping("/getCustomerData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取当前选中的已预约用户信息", notes = "获取当前选中的已预约用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否")
    })
    public R getCustomerData(String patientCode, String type, String autoFill) {
        HashMap map = pacsPeispatientService.getCustomerData(patientCode, type, autoFill);
        return R.ok(map);
    }


    /**
     * 团检退项匹配最小套餐
     *
     * @param param
     * @return
     */
    @GetMapping("/compareMinTcContent")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团检退项匹配最小套餐", notes = "团检退项匹配最小套餐")
    public R compareMinTcContent(ComMinParam param) {
        Boolean b = pacsPeispatientService.compareMinTcContent(param);
        return R.toResult(b);
    }


    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @GetMapping("/getExamfeeByPatientCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取右侧收费项目", notes = "获取右侧收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型")
    })
    public R getExamfeeByPatientCode(String patientCode, String type) {
        List<PacsPeispatientfeeitem> list = peispatientfeeitemService.getExamfeeByPatientCode(patientCode, type);
        return R.ok(list);
    }


    /**
     * 得到备单人员的信息
     *
     * @param patientCode
     * @param id
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "基本信息 ", notes = "得到备单人员的基本信息 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "id", value = "id")
    })
    public R getPatientData(String patientCode, String id) {
        HashMap map = pacsPeispatientService.getPatientData(id, patientCode);
        return R.ok(map);
    }


    /**
     * 根据体检号查询不同状态的收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @GetMapping("/getKindItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据体检号查询不同状态的收费项目 ", notes = "根据体检号查询不同状态的收费项目 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型")
    })
    public R getKindItems(String patientCode, String type) {
        HashMap map = pacsPeispatientService.getKindItems(type, patientCode);
        return R.ok(map);
    }


    /**
     * 获取收费项目
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取收费项目 ", notes = "获取登记右侧收费项目 ")
    public R getItems(PageParamSimple pageParamSimple, GetItemsParam param) {
        PageParam<Items> page = mapperFacade.map(pageParamSimple, PageParam.class);
        PageParam<Items> items = itemsService.getItems(page, param);
        return R.ok(items);
    }


    /**
     * 获取检查部位
     *
     * @param srm
     * @return
     */
    @GetMapping("/getAutoCompleteData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取检查部位 ", notes = "获取登记右上方，检查部位")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R getAutoCompleteData(String srm) {
        QueryWrapper<PacsBasePart> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(srm)) {
            queryWrapper.orderByAsc("partSort").like("inputCode", srm.trim().toUpperCase());
        } else {
            queryWrapper.orderByAsc("partSort");
        }
        List<PacsBasePart> list = pacsBasePartService.list(queryWrapper);
        return R.ok(list);
    }


    /**
     * 登记保存右上列表
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "预约、登记、保存右侧列表  ", notes = "预约、登记、保存右侧列表  ")
    public R saOrUp(@RequestBody PrSaOrUpParam param) throws Exception {
        Boolean b = pacsPeispatientService.saOrUp(param);
        return R.toResult(b);
    }


}

