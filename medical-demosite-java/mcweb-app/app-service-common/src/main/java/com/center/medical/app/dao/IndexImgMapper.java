/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.IndexImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yami
 */
public interface IndexImgMapper extends BaseMapper<IndexImg> {

    /**
     * 根据店铺id与主页轮播图id列表批量删除主页轮播图
     *
     * @param ids      主页轮播图id列表
     * @param branchId 分中心ID
     */
    void deleteIndexImgsByIds(@Param("ids") Long[] ids, @Param("branchId") String branchId);

    void updateImgProd(@Param("ids") List<Long> ids);
}
