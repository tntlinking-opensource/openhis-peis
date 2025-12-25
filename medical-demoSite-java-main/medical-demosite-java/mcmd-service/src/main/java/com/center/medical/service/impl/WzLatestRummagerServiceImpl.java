package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzLatestRummagerMapper;
import com.center.medical.bean.model.WzLatestRummager;
import com.center.medical.service.WzLatestRummagerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——最近检查人(WzLatestRummager)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
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
    public IPage<WzLatestRummager> getList(PageParam<WzLatestRummager> page, WzLatestRummager param) {
        return wzLatestRummagerMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzLatestRummager getInfoById(String id) {
        return wzLatestRummagerMapper.getInfoById(id);
    }

    ;

}

