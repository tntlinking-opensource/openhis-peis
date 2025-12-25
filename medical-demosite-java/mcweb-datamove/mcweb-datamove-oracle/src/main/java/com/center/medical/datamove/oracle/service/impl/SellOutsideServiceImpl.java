package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SellOutsideMapper;
import com.center.medical.datamove.oracle.bean.model.SellOutside;
import com.center.medical.datamove.oracle.service.SellOutsideService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (SellOutside)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:49
 */
@Slf4j
@Service("sellOutsideService")
@RequiredArgsConstructor
public class SellOutsideServiceImpl extends ServiceImpl<SellOutsideMapper, SellOutside> implements SellOutsideService {

    private final SellOutsideMapper sellOutsideMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SellOutside查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SellOutside> getPage(PageParam<SellOutside> page, SellOutside param) {
        return sellOutsideMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SellOutside getInfoById(String id) {
        return sellOutsideMapper.getInfoById(id);
    }

    ;

}


