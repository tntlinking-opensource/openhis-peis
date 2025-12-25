package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.dto.MealItemsDto;
import com.center.medical.appadmin.bean.model.CreatemealApp;
import com.center.medical.appadmin.bean.param.CreatemealAppParam;
import com.center.medical.appadmin.bean.param.GetMealDetailsParam;
import com.center.medical.appadmin.bean.param.GetMealListParam;
import com.center.medical.appadmin.bean.vo.CreatemealAppVo;
import com.center.medical.appadmin.bean.vo.GetMealDetailsVo;
import com.center.medical.appadmin.bean.vo.GetMealListVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小程序套餐表(CreatemealApp)数据库访问层
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
public interface CreatemealAppMapper extends BaseMapper<CreatemealApp> {

    /**
     * 分页查询[小程序套餐表]列表
     *
     * @param page  分页参数
     * @param param CreatemealApp查询参数
     * @return 分页数据
     */
    IPage<CreatemealAppVo> getPage(PageParam<CreatemealAppVo> page, @Param("param") CreatemealAppParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatemealApp getInfoById(@Param("id") String id);

    /**
     * 获取套餐列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetMealListVo> getMealList(PageParam<GetMealListVo> page, @Param("param") GetMealListParam param);

    /**
     * 获取套餐详情
     * @param param
     * @return
     */
    GetMealDetailsVo getMealDetails(@Param("param") GetMealDetailsParam param);

    /**
     * 获取套餐项目
     * @param id
     * @return
     */
    List<MealItemsDto> getMealItems(@Param("id") String id);
}
