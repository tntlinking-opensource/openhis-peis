package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.HarmPackageMatchMapper;
import com.center.medical.datamove.oracle.bean.model.HarmPackageMatch;
import com.center.medical.datamove.oracle.service.HarmPackageMatchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 接害因素、套餐匹配表(HarmPackageMatch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:14
 */
@Slf4j
@Service("harmPackageMatchService")
@RequiredArgsConstructor
public class HarmPackageMatchServiceImpl extends ServiceImpl<HarmPackageMatchMapper, HarmPackageMatch> implements HarmPackageMatchService {

    private final HarmPackageMatchMapper harmPackageMatchMapper;

    /**
     * 分页查询[接害因素、套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param HarmPackageMatch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HarmPackageMatch> getPage(PageParam<HarmPackageMatch> page, HarmPackageMatch param) {
        return harmPackageMatchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public HarmPackageMatch getInfoById(String id) {
        return harmPackageMatchMapper.getInfoById(id);
    }

    ;

}


