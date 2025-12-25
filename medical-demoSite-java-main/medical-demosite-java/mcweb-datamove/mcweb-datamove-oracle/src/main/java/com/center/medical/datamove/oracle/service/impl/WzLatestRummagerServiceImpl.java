package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzLatestRummagerMapper;
import com.center.medical.datamove.oracle.bean.model.WzLatestRummager;
import com.center.medical.datamove.oracle.service.WzLatestRummagerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——最近检查人(WzLatestRummager)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
@Slf4j
@Service("wzLatestRummagerService")
@RequiredArgsConstructor
public class WzLatestRummagerServiceImpl extends ServiceImpl<WzLatestRummagerMapper, WzLatestRummager> implements WzLatestRummagerService {

    private final WzLatestRummagerMapper wzLatestRummagerMapper;

    /**
     * 分页查询[KS问诊——最近检查人]列表
     *
     * @param page  分页参数
     * @param param WzLatestRummager查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzLatestRummager> getPage(PageParam<WzLatestRummager> page, WzLatestRummager param) {
        return wzLatestRummagerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzLatestRummager getInfoById(String id) {
        return wzLatestRummagerMapper.getInfoById(id);
    }

}


