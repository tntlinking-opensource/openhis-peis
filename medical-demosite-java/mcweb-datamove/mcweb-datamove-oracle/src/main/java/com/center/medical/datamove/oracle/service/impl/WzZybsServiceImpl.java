package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzZybsMapper;
import com.center.medical.datamove.oracle.bean.model.WzZybs;
import com.center.medical.datamove.oracle.service.WzZybsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——职业病史(WzZybs)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
@Slf4j
@Service("wzZybsService")
@RequiredArgsConstructor
public class WzZybsServiceImpl extends ServiceImpl<WzZybsMapper, WzZybs> implements WzZybsService {

    private final WzZybsMapper wzZybsMapper;

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param WzZybs查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzZybs> getPage(PageParam<WzZybs> page, WzZybs param) {
        return wzZybsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzZybs getInfoById(String id) {
        return wzZybsMapper.getInfoById(id);
    }

}


