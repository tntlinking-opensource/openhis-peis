package com.center.medical.datascreen.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseTimeNullableParam;
import com.center.medical.datascreen.bean.param.PlatformDataSelectItemsListParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.constant.DatascreenConstant;
import com.center.medical.datascreen.service.PlatformDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**沃德云平台可视化大屏
 * @author xhp
 * @since 2023-05-30 14:10
 * 右上角会默认一个时间段，所以时间段都必填
 */
@RestController
@RequestMapping("datascreen/platform")
@Api(tags = "平台数据")
@RequiredArgsConstructor
@Validated
public class PlatformDataController extends BaseController {
    private final PlatformDataService platformDataService;
    private final MapperFacade mapperFacade;

    @GetMapping("/overview")
    @ApiOperation(value = "体检中心概况", notes = "体检中心概况")
    public R<PlatformDataOverviewVo> getOverview(PlatformDataBaseParam param){
        return R.ok(platformDataService.getOverview(param));
    }

    @GetMapping("/group/list")
    @ApiOperation(value = "团检体检人数", notes = "团检体检人数")
    public R<List<PlatformDataNumberListVo>> selectGroupNumberList(PlatformDataBaseParam param){
        return R.ok(platformDataService.selectGroupNumberList(param));
    }

    @GetMapping("/person/list")
    @ApiOperation(value = "个检体检人数", notes = "个检体检人数")
    public R<List<PlatformDataNumberListVo>> selectPersonNumberList(PlatformDataBaseParam param){
        return R.ok(platformDataService.selectPersonNumberList(param));
    }

    @GetMapping("/group/patient/list")
    @ApiOperation(value = "团检体检概况", notes = "团检体检概况")
    public R<IPage<PlatformDataGroupPatientListVo>> selectGroupPatientList(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectGroupPatientList(page,param, DatascreenConstant.GROUP));
    }

    @GetMapping("/group/page")
    @ApiOperation(value = "分中心列表(团检)", notes = "团检体检人数分页")
    public R<IPage<PlatformDataNumberPageVo>> selectGroupNumberPage(PageParamSimple pageParamSimple, PlatformDataBaseParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectGroupNumberPage(page,param));
    }

    @GetMapping("/person/page")
    @ApiOperation(value = "分中心列表(个检)", notes = "个检体检人数分页")
    public R<IPage<PlatformDataNumberPageVo>> selectPersonNumberPage(PageParamSimple pageParamSimple,PlatformDataBaseParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectPersonNumberPage(page,param));
    }

    @GetMapping("/person/patient/page")
    @ApiOperation(value = "个检体检概况", notes = "个检体检概况")
    public R<IPage<PlatformDataPersonPatientPageVo>> selectPersonPatientPage(PageParamSimple pageParamSimple, DatascreenBaseTimeAndBranchParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectPersonPatientPage(page,param));
    }

    @GetMapping("/meal/list")
    @ApiOperation(value = "活动产品销售(套餐)", notes = "活动产品销售(套餐)")
    public R<List<PlatformDataSelectMealListVo>> selectMealList(PlatformDataBaseParam param){
        return R.ok(platformDataService.selectMealList(BeanUtil.toBean(param,DatascreenBaseTimeAndBranchParam.class)));
    }

    @GetMapping("/meal/page")
    @ApiOperation(value = "体检套餐概况", notes = "体检套餐概况")
    public R<IPage<PlatformDataSelectMealPageVo>> selectMealPage(PageParamSimple pageParamSimple,PlatformDataBaseParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(platformDataService.selectMealPage(page,BeanUtil.toBean(param,DatascreenBaseTimeAndBranchParam.class)));
    }

    @GetMapping("/cost")
    @ApiOperation(value = "成本展示", notes = "成本展示")
    public R<PlatformDataGetCostVo> getCost(){
        return R.ok(platformDataService.getCost());
    }

    /**
     * Deprecated by:
     * 前端：常见病选中展示项,后面不用维护了,不用这个接口了
     */
    @Deprecated
    @ApiIgnore
    @GetMapping("/commonDiseaseItem/list")
    @ApiOperation(value = "常见病选中展示项", notes = "常见病选中展示项")
    public R<List<String>> getCommonDiseaseItems(){
        return R.ok(platformDataService.getCommonDiseaseItems());
    }

    @GetMapping("/commonDiseaseData/list")
    @ApiOperation(value = "常见病占比", notes = "常见病占比")
    public R<List<PlatformDataGetCommonDiseaseDataVo>> getCommonDiseaseData(){
        return R.ok(platformDataService.getCommonDiseaseData());
    }

    /**
     * 所有展示项目直接使用BusinessCatController的接口，展示md_business_cat表中所有类型
     * @return
     */
    @GetMapping("/items/list")
    @ApiOperation(value = "活动产品销售(项目)", notes = "活动产品销售(项目)")
    public R<List<PlatformDataSelectItemsListVo>> selectItemsList(PlatformDataSelectItemsListParam param){
        return R.ok(platformDataService.selectItemsList(param));
    }

    /**
     * 可以不传时间条件，查总营业额
     * @param param
     * @return
     */
    @GetMapping("/totalAmount")
    @ApiOperation(value = "总营业额", notes = "总营业额")
    public R<PlatformDataTotalAmountVo> selectTotalAmount(PlatformDataBaseTimeNullableParam param){
        return R.ok(platformDataService.selectTotalAmount(param));
    }

    @GetMapping("/branchPosition/list")
    @ApiOperation(value = "分中心经纬度", notes = "分中心经纬度")
    public R<List<PlatformDataSelectBranchPositionVo>> selectBranchPositionList(){
        return R.ok(platformDataService.selectBranchPositionList());
    }

    /**
     * 可以不传时间条件，查总人数
     * @param param
     * @return
     */
    @GetMapping("/totalNumber")
    @ApiOperation(value = "总会员", notes = "总会员")
    public R<PlatformDataTotalNumberVo> selectTotalNumber(PlatformDataBaseTimeNullableParam param){
        return R.ok(platformDataService.selectTotalNumber(param));
    }
}
