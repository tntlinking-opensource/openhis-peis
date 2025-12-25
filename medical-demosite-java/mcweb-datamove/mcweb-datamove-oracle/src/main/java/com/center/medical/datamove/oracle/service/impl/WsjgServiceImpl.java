package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WsjgMapper;
import com.center.medical.datamove.oracle.bean.model.Wsjg;
import com.center.medical.datamove.oracle.service.WsjgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Wsjg)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:48
 */
@Slf4j
@Service("wsjgService")
@RequiredArgsConstructor
public class WsjgServiceImpl extends ServiceImpl<WsjgMapper, Wsjg> implements WsjgService {

    private final WsjgMapper wsjgMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Wsjg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Wsjg> getPage(PageParam<Wsjg> page, Wsjg param) {
        return wsjgMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Wsjg getInfoById(String id) {
        return wsjgMapper.getInfoById(id);
    }

}


