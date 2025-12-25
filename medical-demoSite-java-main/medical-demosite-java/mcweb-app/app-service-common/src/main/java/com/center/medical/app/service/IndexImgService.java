/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.IndexImg;

import java.util.List;

/**
 * @author lgh on 2018/11/26.
 */
public interface IndexImgService extends IService<IndexImg> {

    /**
     * 根据分中心ID与主页轮播图id列表批量删除主页轮播图
     *
     * @param ids      主页轮播图id列表
     * @param branchId 分中心ID
     */
    void deleteIndexImgsByIds(Long[] ids, String branchId);

    /**
     * 根据分中心ID与图片类型获取首页轮播图列表信息
     *
     * @param branchId 分中心ID
     * @return 轮播图列表
     */
    List<IndexImg> listIndexImgsByBranchId(String branchId);

    /**
     * 清除缓存
     *
     * @param branchId 分中心ID
     */
    void removeIndexImgCacheByBranchId(String branchId);

    void updateImgProd(List<Long> ids);
}
