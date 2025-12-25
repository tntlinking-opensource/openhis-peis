package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzJzbMapper;
import com.center.medical.datamove.oracle.bean.model.WzJzb;
import com.center.medical.datamove.oracle.service.WzJzbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——家族病(WzJzb)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:50
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
    public IPage<WzJzb> getPage(PageParam<WzJzb> page, WzJzb param) {
        return wzJzbMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzJzb getInfoById(String id) {
        return wzJzbMapper.getInfoById(id);
    }

}


