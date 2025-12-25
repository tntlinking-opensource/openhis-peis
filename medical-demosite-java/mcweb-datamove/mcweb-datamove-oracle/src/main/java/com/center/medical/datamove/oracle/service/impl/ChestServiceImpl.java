package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ChestMapper;
import com.center.medical.datamove.oracle.bean.model.Chest;
import com.center.medical.datamove.oracle.service.ChestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 此表为通用表，团检(Chest)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:37
 */
@Slf4j
@Service("chestService")
@RequiredArgsConstructor
public class ChestServiceImpl extends ServiceImpl<ChestMapper, Chest> implements ChestService {

    private final ChestMapper chestMapper;

    /**
     * 分页查询[此表为通用表，团检]列表
     *
     * @param page  分页参数
     * @param param Chest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Chest> getPage(PageParam<Chest> page, Chest param) {
        return chestMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Chest getInfoById(String id) {
        return chestMapper.getInfoById(id);
    }

}


