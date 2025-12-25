package com.center.medical.datascreen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemPageParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.constant.DatascreenConstant;
import com.center.medical.datascreen.service.BranchDataService;
import com.center.medical.datascreen.service.PlatformDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xhp
 * @since 2023-06-06 9:05
 */
@RestController
@RequestMapping("datascreen/branch")
@Api(tags = "中心数据")
@RequiredArgsConstructor
@Validated
public class BranchDataController extends BaseController {
    private final BranchDataService branchDataService;
    private final MapperFacade mapperFacade;
    private final PlatformDataService platformDataService;

    @GetMapping("/branch/list")
    @ApiOperation(value = "中心分区", notes = "中心分区")
    public R<List<BranchDataBranchVo>> selectBranchList(){
        return R.ok(branchDataService.selectBranchList());
    }

    @GetMapping("/meal/list")
    @ApiOperation(value = "促销活动套餐", notes = "促销活动套餐")
    public R<List<PlatformDataSelectMealListVo>> selectMealList(DatascreenBaseTimeAndBranchParam param){
        return R.ok(platformDataService.selectMealList(param));
    }

    @GetMapping("/meal/page")
    @ApiOperation(value = "体检套餐概况", notes = "体检套餐概况")
    public R<IPage<PlatformDataSelectMealPageVo>> selectMealPage(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectMealPage(page,param));
    }

    @GetMapping("/group/patient/page")
    @ApiOperation(value = "团检体检概况", notes = "团检体检概况")
    public R<IPage<PlatformDataGroupPatientListVo>> selectGroupPatientList(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectGroupPatientList(page,param,DatascreenConstant.GROUP));
    }

    @GetMapping("/person/patient/page")
    @ApiOperation(value = "个检体检概况", notes = "个检体检概况")
    public R<IPage<BranchDataPersonPatientPageVo>> selectPersonPatientPage(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(branchDataService.selectPersonPatientPage(page,param));
    }

    @GetMapping("/all/patient/page")
    @ApiOperation(value = "体检总概况", notes = "体检总概况")
    public R<IPage<PlatformDataGroupPatientListVo>> selectPatientPage(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectGroupPatientList(page,param,null));
    }

    @GetMapping("/total/all")
    @ApiOperation(value = "体检总人数", notes = "体检总人数")
    public R<BranchDataTotalNumberVo> selectTotalNumber(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectTotalNumber(param,null));
    }

    @GetMapping("/total/group")
    @ApiOperation(value = "团检总人数", notes = "团检总人数")
    public R<BranchDataTotalNumberVo> selectGroupTotalNumber(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectTotalNumber(param,DatascreenConstant.GROUP));
    }

    @GetMapping("/total/person")
    @ApiOperation(value = "个检总人数", notes = "个检总人数")
    public R<BranchDataTotalNumberVo> selectPersonTotalNumber(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectTotalNumber(param, DatascreenConstant.PERSON));
    }

    @GetMapping("/dept")
    @ApiOperation(value = "科室工作量", notes = "科室工作量")
    public R<BranchDataSelectDeptVo> selectDept(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectDept(param));
    }

    @GetMapping("/item/list")
    @ApiOperation(value = "项目推广统计", notes = "项目推广统计")
    public R<List<DatascreenBaseListVo>> selectItemList(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectItemList(param));
    }

    @GetMapping("/reservation")
    @ApiOperation(value = "销售部预约", notes = "销售部预约")
    public R<BranchDataSelectReservationVo> selectReservation(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectReservation(param));
    }

    @GetMapping("/criticalValue")
    @ApiOperation(value = "危急值", notes = "危急值")
    public R<BranchDataSelectCriticalValueVo> selectCriticalValue(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectCriticalValue(param));
    }

    @GetMapping("/followUp")
    @ApiOperation(value = "回访率", notes = "回访率")
    public R<BranchDataSelectFollowUpVo> selectFollowUp(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectFollowUp(param));
    }

    @GetMapping("/addItem")
    @ApiOperation(value = "加项数据统计", notes = "加项数据统计")
    public R<BranchDataSelectAddItemVo> selectAddItem(BranchDataSelectAddItemParam param){
        return R.ok(branchDataService.selectAddItem(param));
    }

    @GetMapping("/addItem/page")
    @ApiOperation(value = "加项数据统计列表", notes = "加项数据统计列表")
    public R<IPage<BranchDataSelectAddItemPageVo>> selectAddItemPage(PageParamSimple pageParamSimple,BranchDataSelectAddItemPageParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(branchDataService.selectAddItemPage(page,param));
    }

    @GetMapping("/dept/list")
    @ApiOperation(value = "科室工作量列表", notes = "科室工作量列表")
    public R<List<BranchDataSelectDeptListVo>> selectDeptList(DatascreenBaseTimeAndBranchParam param){
        return R.ok(branchDataService.selectDeptList(param));
    }
}
