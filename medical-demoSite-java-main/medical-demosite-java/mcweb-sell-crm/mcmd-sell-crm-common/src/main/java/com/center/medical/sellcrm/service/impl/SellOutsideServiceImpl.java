package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.SellOutside;
import com.center.medical.sellcrm.dao.SellOutsideMapper;
import com.center.medical.sellcrm.service.SellOutsideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 外出沟通(SellOutside)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:24
 */
@Slf4j
@Service("sellOutsideService")
@RequiredArgsConstructor
public class SellOutsideServiceImpl extends ServiceImpl<SellOutsideMapper, SellOutside> implements SellOutsideService {

    private final SellOutsideMapper sellOutsideMapper;

    /**
     * 分页查询[外出沟通]列表
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

}

