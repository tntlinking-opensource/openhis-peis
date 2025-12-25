package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdHarmPackageMatchMapper;
import com.center.medical.datamove.common.bean.model.MdHarmPackageMatch;
import com.center.medical.datamove.common.service.MdHarmPackageMatchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 危害因素-套餐匹配表(MdHarmPackageMatch)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Slf4j
@Service("mdHarmPackageMatchService")
@RequiredArgsConstructor
public class MdHarmPackageMatchServiceImpl extends ServiceImpl<MdHarmPackageMatchMapper, MdHarmPackageMatch> implements MdHarmPackageMatchService {

    private final MdHarmPackageMatchMapper mdHarmPackageMatchMapper;

    /**
     * 分页查询[危害因素-套餐匹配表]列表
     *
     * @param page  分页参数
     * @param param MdHarmPackageMatch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdHarmPackageMatch> getPage(PageParam<MdHarmPackageMatch> page, MdHarmPackageMatch param) {
        return mdHarmPackageMatchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdHarmPackageMatch getInfoById(String id) {
        return mdHarmPackageMatchMapper.getInfoById(id);
    }

}


