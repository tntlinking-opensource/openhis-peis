package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellprintteams;
import com.center.medical.sellcrm.dao.SellprintteamsMapper;
import com.center.medical.sellcrm.service.SellprintteamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 销售打印项目分类设置表(Sellprintteams)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:25
 */
@Slf4j
@Service("sellprintteamsService")
@RequiredArgsConstructor
public class SellprintteamsServiceImpl extends ServiceImpl<SellprintteamsMapper, Sellprintteams> implements SellprintteamsService {

    private final SellprintteamsMapper sellprintteamsMapper;

    /**
     * 分页查询[销售打印项目分类设置表]列表
     *
     * @param page  分页参数
     * @param param Sellprintteams查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellprintteams> getPage(PageParam<Sellprintteams> page, Sellprintteams param) {
        return sellprintteamsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sellprintteams getInfoById(String id) {
        return sellprintteamsMapper.getInfoById(id);
    }

}

