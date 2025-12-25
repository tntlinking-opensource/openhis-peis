package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.CreatemealApp;
import com.center.medical.appadmin.bean.param.*;
import com.center.medical.appadmin.bean.vo.CreatemealAppVo;
import com.center.medical.appadmin.bean.vo.GetMealDetailsVo;
import com.center.medical.appadmin.bean.vo.GetMealListVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 小程序套餐表(CreatemealApp)服务接口
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
public interface CreatemealAppService extends IService<CreatemealApp> {

    /**
     * 分页查询[小程序套餐表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CreatemealAppVo> getPage(PageParam<CreatemealAppVo> page, CreatemealAppParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatemealApp getInfoById(String id);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteIds(List<String> ids);

    /**
     * 添加或更新
     * @param param
     * @return
     */
    Boolean saOrUp(CMAppSaOrUpParam param);

    /**
     * 设为app套餐
     * @param id
     * @return
     */
    Boolean setAppMeal(String id);

    /**
     * 获取套餐列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetMealListVo> getMealList(PageParam<GetMealListVo> page, GetMealListParam param);

    /**
     * 获取套餐详情
     * @param param
     * @return
     */
    GetMealDetailsVo getMealDetails(GetMealDetailsParam param);

    /**
     * 上线或下线
     * @param param
     * @return
     */
    Boolean goLive(GoLiveParam param);
}

