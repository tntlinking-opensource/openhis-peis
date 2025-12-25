package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzZybsMapper;
import com.center.medical.bean.model.WzZybs;
import com.center.medical.service.WzZybsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——职业病史(WzZybs)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
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
    public IPage<WzZybs> getList(PageParam<WzZybs> page, WzZybs param) {
        return wzZybsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzZybs getInfoById(String id) {
        return wzZybsMapper.getInfoById(id);
    }

}

