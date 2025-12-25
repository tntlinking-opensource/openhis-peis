package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.sellcrm.service.RiskclientconService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 高危人员关联表(Riskclientcon)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:11
 */
@Slf4j
@Service("riskclientconService")
@RequiredArgsConstructor
public class RiskclientconServiceImpl extends ServiceImpl<RiskclientconMapper, Riskclientcon> implements RiskclientconService {

    private final RiskclientconMapper riskclientconMapper;

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param Riskclientcon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Riskclientcon> getPage(PageParam<Riskclientcon> page, Riskclientcon param) {
        return riskclientconMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Riskclientcon getInfoById(String id) {
        return riskclientconMapper.getInfoById(id);
    }

}

