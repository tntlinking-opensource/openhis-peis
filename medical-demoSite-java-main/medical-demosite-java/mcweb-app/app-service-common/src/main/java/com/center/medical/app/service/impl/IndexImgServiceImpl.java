/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.IndexImg;
import com.center.medical.app.dao.IndexImgMapper;
import com.center.medical.app.service.IndexImgService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lgh on 2018/11/26.
 */
@Service
@AllArgsConstructor
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    private final IndexImgMapper indexImgMapper;

    @Override
    public void deleteIndexImgsByIds(Long[] ids, String branchId) {
        indexImgMapper.deleteIndexImgsByIds(ids, branchId);
    }

    @Override
    @Cacheable(cacheNames = "indexImg", key = "#branchId")
    public List<IndexImg> listIndexImgsByBranchId(String branchId) {
        return indexImgMapper.selectList(new LambdaQueryWrapper<IndexImg>()
                .eq(IndexImg::getBranchId, branchId)
                .eq(IndexImg::getStatus, 1)
                .orderByDesc(IndexImg::getSeq));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "indexImg", key = "#branchId + ':1'"),
            @CacheEvict(cacheNames = "indexImg", key = "#branchId + ':0'")
    })
    public void removeIndexImgCacheByBranchId(String branchId) {

    }

    @Override
    public void updateImgProd(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        indexImgMapper.updateImgProd(ids);
    }

}
