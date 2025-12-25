package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzJwbMapper;
import com.center.medical.bean.model.WzJwb;
import com.center.medical.service.WzJwbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——既往病(WzJwb)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
@Slf4j
@Service("wzJwbService")
@RequiredArgsConstructor
public class WzJwbServiceImpl extends ServiceImpl<WzJwbMapper, WzJwb> implements WzJwbService {

    private final WzJwbMapper wzJwbMapper;

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param WzJwb查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzJwb> getList(PageParam<WzJwb> page, WzJwb param) {
        return wzJwbMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzJwb getInfoById(String id) {
        return wzJwbMapper.getInfoById(id);
    }

}

