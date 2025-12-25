package com.center.medical.datascreen.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datascreen.bean.dto.PlatformDataGroupPatientListDto;
import com.center.medical.datascreen.bean.dto.PlatformDataPersonPatientPageDto;
import com.center.medical.datascreen.bean.dto.PlatformDataSelectItemsListDto;
import com.center.medical.datascreen.bean.dto.PlatformDataSelectMealPageDto;
import com.center.medical.datascreen.bean.param.PlatformDataBaseParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.param.PlatformDataSelectItemsListParam;
import com.center.medical.datascreen.bean.vo.PlatformDataNumberListVo;
import com.center.medical.datascreen.bean.vo.PlatformDataNumberPageVo;
import com.center.medical.datascreen.bean.vo.PlatformDataSelectMealListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-05-30 14:49
 */
@Repository
public interface PlatformDataMapper {
    /**
     * 体检中心概况-体检总人数
     */
    int selectTotalNumber(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 体检中心概况-营业额
     * @param param
     * @return
     */
    double selectTotalAmount(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 体检中心概况-折扣率
     * @param param
     * @return
     */
    Double selectDiscountRate(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 团检/个检体检人数
     * @param param
     * @return
     */
    List<PlatformDataNumberListVo> selectNumberList(@Param("param") PlatformDataBaseParam param, @Param("fUsecodehiden") int fUsecodehiden);

    /**
     * 团检体检概况
     * @param page
     * @param param
     * @return
     */
    IPage<PlatformDataGroupPatientListDto> selectGroupPatientList(PageParam page, @Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 个检体检概况
     * @param page
     * @param param
     * @return
     */
    IPage<PlatformDataPersonPatientPageDto> selectPersonPatientPage(PageParam page, @Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 通过体检号查询所有支付方式名称
     * @param patientcode
     * @return
     */
    String selectPaywayNamesByPatientcode(@Param("patientcode") String patientcode);

    /**
     * 通过体检号查询折扣率
     * @param patientcode
     * @return
     */
    Double selectDiscountRateByPatientcode(@Param("patientcode") String patientcode);

    /**
     * 分中心列表 团检/个检
     * @param page
     * @param param
     * @param fUsecodehiden
     * @return
     */
    IPage<PlatformDataNumberPageVo> selectNumberPage(PageParam page,@Param("param") PlatformDataBaseParam param, @Param("fUsecodehiden") int fUsecodehiden);

    /**
     * 活动产品销售(套餐)
     * @param param
     * @return
     */
    List<PlatformDataSelectMealListVo> selectMealList(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 体检套餐概况
     * @param param
     * @return
     */
    IPage<PlatformDataSelectMealPageDto> selectMealPage(PageParam page,@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 活动产品销售(项目)
     * @param param
     * @return
     */
    List<PlatformDataSelectItemsListDto> selectItemsList(@Param("param") PlatformDataSelectItemsListParam param);
}
