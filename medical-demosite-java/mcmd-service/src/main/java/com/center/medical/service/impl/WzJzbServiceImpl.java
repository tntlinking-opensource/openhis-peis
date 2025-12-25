package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzJzbMapper;
import com.center.medical.bean.model.WzJzb;
import com.center.medical.service.WzJzbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——家族病(WzJzb)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:02
 */
@Slf4j
@Service("wzJzbService")
@RequiredArgsConstructor
public class WzJzbServiceImpl extends ServiceImpl<WzJzbMapper, WzJzb> implements WzJzbService {

    private final WzJzbMapper wzJzbMapper;

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param WzJzb查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzJzb> getList(PageParam<WzJzb> page, WzJzb param) {
        return wzJzbMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzJzb getInfoById(String id) {
        return wzJzbMapper.getInfoById(id);
    }

}

