package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.service.BusinessCatService;
import com.center.medical.finance.bean.param.ActivityMealListParam;
import com.center.medical.finance.bean.param.GetActivityMealParam;
import com.center.medical.finance.bean.param.StatementsParam;
import com.center.medical.finance.bean.vo.ActivityMealListVo;
import com.center.medical.finance.bean.vo.ActivityMealVo;
import com.center.medical.finance.bean.vo.DaySalesVolumeListVo;
import com.center.medical.finance.bean.vo.FIPageVo;
import com.center.medical.finance.service.DaySalesVolumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检者费用主表(PeispatientChargeMain)表控制层
 *
 * @author ay
 * @since 2023-06-20 17:50:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "当日线下业绩及活动套餐追踪进度")
@RequestMapping("finance/statements")
public class DaySalesVolumeController extends BaseController {
    /**
     * 服务对象
     */
    private final DaySalesVolumeService daySalesVolumeService;
    private final MapperFacade mapperFacade;
    private final BusinessCatService businessCatService;


    /**
     * 当日线下业绩-筛选条件
     * @return
     */
    @GetMapping("daySalesVolume/filterCriteria")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "当日线下业绩-筛选条件", notes = "当日线下业绩-筛选条件")
    public R<List<BusinessCat>> getList() {
        List<BusinessCat> list = businessCatService.list(new LambdaQueryWrapper<BusinessCat>().eq(BusinessCat::getStatus, 1));
        return R.ok(list);
    }


    /**
     * 分页查询所有数据
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("daySalesVolume/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "当日线下业绩-列表", notes = "当日线下业绩-列表")
    public R<DaySalesVolumeListVo> getlist(StatementsParam param) {
        DaySalesVolumeListVo list = daySalesVolumeService.getDaySalesVolume(param);
        return R.ok(list);
    }


    /**
     * 活动套餐-获取活动套餐种类
     * @return
     */
    @GetMapping("activityMeal/getActivityMeal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "活动套餐-获取活动套餐种类", notes = "倒序排列,")
    public R<IPage<ActivityMealVo>> getActivityMeal(PageParamSimple pageParamSimple,GetActivityMealParam param) {
        PageParam<FIPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ActivityMealVo> list = daySalesVolumeService.getActivityMeal(page,param);
        return R.ok(list);
    }


    /**
     * 活动套餐-列表
     * @param param
     * @return
     */
    @GetMapping("activityMeal/getActivityMealList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "活动套餐-列表", notes = "活动套餐-列表")
    public R<List<List<ActivityMealListVo>>> getActivityMealList(ActivityMealListParam param) {
        //最多只显示3个
        List<List<ActivityMealListVo>> list = daySalesVolumeService.getActivityMealList(param);
        return R.ok(list);
    }
}

