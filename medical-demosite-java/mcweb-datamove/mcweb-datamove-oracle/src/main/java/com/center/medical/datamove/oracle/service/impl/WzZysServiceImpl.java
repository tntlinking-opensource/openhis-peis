package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzZysMapper;
import com.center.medical.datamove.oracle.bean.model.WzZys;
import com.center.medical.datamove.oracle.service.WzZysService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——职业史(WzZys)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
@Slf4j
@Service("wzZysService")
@RequiredArgsConstructor
public class WzZysServiceImpl extends ServiceImpl<WzZysMapper, WzZys> implements WzZysService {

    private final WzZysMapper wzZysMapper;

    /**
     * 分页查询[KS问诊——职业史]列表
     *
     * @param page  分页参数
     * @param param WzZys查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzZys> getPage(PageParam<WzZys> page, WzZys param) {
        return wzZysMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzZys getInfoById(String id) {
        return wzZysMapper.getInfoById(id);
    }

}


