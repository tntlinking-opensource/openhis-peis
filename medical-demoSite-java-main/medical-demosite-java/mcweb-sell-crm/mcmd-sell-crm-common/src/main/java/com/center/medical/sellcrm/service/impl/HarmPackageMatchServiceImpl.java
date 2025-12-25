package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.HarmPackageMatch;
import com.center.medical.sellcrm.dao.HarmPackageMatchMapper;
import com.center.medical.sellcrm.service.HarmPackageMatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 危害因素-套餐匹配表(HarmPackageMatch)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
@Slf4j
@Service("harmPackageMatchService")
@RequiredArgsConstructor
public class HarmPackageMatchServiceImpl extends ServiceImpl<HarmPackageMatchMapper, HarmPackageMatch> implements HarmPackageMatchService {

    private final HarmPackageMatchMapper harmPackageMatchMapper;

    /**
     * 分页查询[危害因素-套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param HarmPackageMatch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HarmPackageMatch> getList(PageParam<HarmPackageMatch> page, HarmPackageMatch param) {
        return harmPackageMatchMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public HarmPackageMatch getInfoById(String id) {
        return harmPackageMatchMapper.getInfoById(id);
    }

}

