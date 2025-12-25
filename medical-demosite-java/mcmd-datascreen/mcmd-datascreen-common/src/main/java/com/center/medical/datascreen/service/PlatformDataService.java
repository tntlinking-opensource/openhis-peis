package com.center.medical.datascreen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseTimeNullableParam;
import com.center.medical.datascreen.bean.param.PlatformDataSelectItemsListParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.bean.vo.*;

import java.util.List;

/**
 * 沃德云平台可视化大屏-平台数据
 * @author xhp
 * @since 2023-05-30 14:43
 */
public interface PlatformDataService {
    /**
     * 体检中心概况
     */
    PlatformDataOverviewVo getOverview(PlatformDataBaseParam param);

    /**
     * 团检体检人数
     * @param param
     * @return
     */
    List<PlatformDataNumberListVo>  selectGroupNumberList(PlatformDataBaseParam param);

    /**
     * 个检体检人数
     * @param param
     * @return
     */
    List<PlatformDataNumberListVo>  selectPersonNumberList(PlatformDataBaseParam param);

    /**
     * 团检体检概况
     * @param page
     * @param param
     * @return
     */
    IPage<PlatformDataGroupPatientListVo> selectGroupPatientList(PageParam page, DatascreenBaseTimeAndBranchParam param,Integer fUsecodehiden);

    /**
     * 个检体检概况
     * @param page
     * @param param
     * @return
     */
    IPage<PlatformDataPersonPatientPageVo> selectPersonPatientPage(PageParam page, DatascreenBaseTimeAndBranchParam param);

    /**
     * 分中心列表(团检)
     * @param param
     * @return
     */
    IPage<PlatformDataNumberPageVo>  selectGroupNumberPage(PageParam page, PlatformDataBaseParam param);

    /**
     * 分中心列表(个检)
     * @param param
     * @return
     */
    IPage<PlatformDataNumberPageVo>  selectPersonNumberPage(PageParam page,PlatformDataBaseParam param);

    /**
     * 活动产品销售(套餐)
     * @param param
     * @return
     */
    List<PlatformDataSelectMealListVo> selectMealList(DatascreenBaseTimeAndBranchParam param);

    /**
     * 体检套餐概况
     * @param param
     * @return
     */
    IPage<PlatformDataSelectMealPageVo> selectMealPage(PageParam page,DatascreenBaseTimeAndBranchParam param);

    /**
     * 成本展示
     * @return
     */
    PlatformDataGetCostVo getCost();

    /**
     *常见病选中展示项
     * @return
     */
    List<String> getCommonDiseaseItems();

    /**
     * 常见病占比
     * @return
     */
    List<PlatformDataGetCommonDiseaseDataVo> getCommonDiseaseData();

    /**
     * 活动产品销售(项目)
     * @param param
     * @return
     */
    List<PlatformDataSelectItemsListVo> selectItemsList(PlatformDataSelectItemsListParam param);

    /**
     * 总营业额
     * @param param
     * @return
     */
    PlatformDataTotalAmountVo selectTotalAmount(PlatformDataBaseTimeNullableParam param);

    /**
     * 分中心经纬度
     * @return
     */
    List<PlatformDataSelectBranchPositionVo> selectBranchPositionList();

    /**
     * 总会员
     * @param param
     * @return
     */
    PlatformDataTotalNumberVo selectTotalNumber(PlatformDataBaseTimeNullableParam param);
}
