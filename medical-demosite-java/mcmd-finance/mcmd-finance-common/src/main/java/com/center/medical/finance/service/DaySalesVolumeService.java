package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ActivityMealListParam;
import com.center.medical.finance.bean.param.GetActivityMealParam;
import com.center.medical.finance.bean.param.StatementsParam;
import com.center.medical.finance.bean.vo.ActivityMealListVo;
import com.center.medical.finance.bean.vo.ActivityMealVo;
import com.center.medical.finance.bean.vo.DaySalesVolumeListVo;
import com.center.medical.finance.bean.vo.FIPageVo;

import java.util.List;

/**
 * 体检者费用主表(PeispatientChargeMain)表服务接口
 *
 * @author ay
 * @since 2023-06-20 17:50:32
 */
public interface DaySalesVolumeService extends IService<PeispatientChargeMain> {

    /**
     * 当日线下业绩
     * @param param
     * @return
     */
    DaySalesVolumeListVo getDaySalesVolume(StatementsParam param);

    /**
     * 活动套餐-获取活动套餐种类
     * @return
     */
    IPage<ActivityMealVo> getActivityMeal(PageParam<FIPageVo> page, GetActivityMealParam param);

    /**
     * 活动套餐-列表
     * @param param
     * @return
     */
    List<List<ActivityMealListVo>> getActivityMealList(ActivityMealListParam param);
}

