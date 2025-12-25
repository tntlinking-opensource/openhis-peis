package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Mealandfzx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 普通套餐与分中心关联表(Mealandfzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:48
 */
public interface MealandfzxMapper extends BaseMapper<Mealandfzx> {

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Mealandfzx查询参数
     * @return 分页数据
     */
    IPage<Mealandfzx> getPage(PageParam<Mealandfzx> page, @Param("param") Mealandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Mealandfzx getInfoById(@Param("id") String id);

    /**
     * 获取拼接的分中心id
     *
     * @param id
     * @return
     */
    String getJointfzxid(@Param("id") String id);

    /**
     * 普通套餐与分中心关联表
     * @param orderId
     * @param cid
     * @return
     */
    List<Mealandfzx> findMealAndFzx(@Param("orderId") String orderId,@Param("cid") String cid);

    /**
     * 根据主键id和分中心id查询记录条数
     * @param id
     * @param fzxIds
     * @return
     */
    int countByIdAndFzx(@Param("id") String id,@Param("fzxIds")  String[] fzxIds);
}
