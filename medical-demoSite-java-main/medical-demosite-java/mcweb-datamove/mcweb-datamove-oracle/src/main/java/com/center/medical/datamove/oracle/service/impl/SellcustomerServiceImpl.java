package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SellcustomerMapper;
import com.center.medical.datamove.oracle.bean.model.Sellcustomer;
import com.center.medical.datamove.oracle.service.SellcustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 我的客户表(Sellcustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:52
 */
@Slf4j
@Service("sellcustomerService")
@RequiredArgsConstructor
public class SellcustomerServiceImpl extends ServiceImpl<SellcustomerMapper, Sellcustomer> implements SellcustomerService {

    private final SellcustomerMapper sellcustomerMapper;

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, Sellcustomer param) {
        return sellcustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sellcustomer getInfoById(String id) {
        return sellcustomerMapper.getInfoById(id);
    }

    ;

}


