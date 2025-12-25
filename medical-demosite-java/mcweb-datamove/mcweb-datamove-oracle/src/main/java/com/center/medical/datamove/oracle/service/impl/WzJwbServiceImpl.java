package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzJwbMapper;
import com.center.medical.datamove.oracle.bean.model.WzJwb;
import com.center.medical.datamove.oracle.service.WzJwbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——既往病(WzJwb)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:49
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
    public IPage<WzJwb> getPage(PageParam<WzJwb> page, WzJwb param) {
        return wzJwbMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzJwb getInfoById(String id) {
        return wzJwbMapper.getInfoById(id);
    }

}


