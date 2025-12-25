package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.DSVOptionDto;
import com.center.medical.finance.bean.dto.ISDataDto;
import com.center.medical.finance.bean.param.GetActivityMealParam;
import com.center.medical.finance.bean.param.StatementsParam;
import com.center.medical.finance.bean.vo.ActivityMealListVo;
import com.center.medical.finance.bean.vo.ActivityMealVo;
import com.center.medical.finance.bean.vo.DaySalesVolumeVo;
import com.center.medical.finance.bean.vo.FIPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 体检者费用主表(PeispatientChargeMain)表数据库访问层
 *
 * @author ay
 * @since 2023-06-20 17:50:31
 */
public interface DaySalesVolumeMapper extends BaseMapper<PeispatientChargeMain> {


    /**
     * 当日线下业绩
     * @param param
     * @return
     */
    List<DaySalesVolumeVo> getDaySalesVolume(@Param("param") StatementsParam param);

    /**
     * 活动套餐-获取活动套餐种类
     * @return
     */
    IPage<ActivityMealVo> getActivityMeal(PageParam<FIPageVo> page,@Param("param") GetActivityMealParam param);

    /**
     * 活动套餐-列表
     * @return
     */
    List<ActivityMealListVo> getActivityMealList(@Param("id") String id , @Param("now") Date now);

    /**
     * 获取选项数据
     * @param id
     * @return
     */
    List<DSVOptionDto> findOption(@Param("time")Date time , @Param("id") String id);

    /**
     * 查询来检人数
     * @param time 时间筛选
     * @param id 业务类型id
     * @return
     */
    List<DSVOptionDto> findOptionCount(@Param("time")Date time,@Param("id") String id);


    /**
     * 获取来检人数
     * @param time
     * @return
     */
    List<ISDataDto> findInCome(@Param("time") Date time);

    /**
     * 去年收入
     * @param time
     * @return
     */
    List<ISDataDto> findInComeMoney(@Param("time") Date time);
}
