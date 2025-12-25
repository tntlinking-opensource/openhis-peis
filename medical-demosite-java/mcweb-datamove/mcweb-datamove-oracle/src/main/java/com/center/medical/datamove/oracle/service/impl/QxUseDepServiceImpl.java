package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxUseDepMapper;
import com.center.medical.datamove.oracle.bean.model.QxUseDep;
import com.center.medical.datamove.oracle.service.QxUseDepService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxUseDep)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:52
 */
@Slf4j
@Service("qxUseDepService")
@RequiredArgsConstructor
public class QxUseDepServiceImpl extends ServiceImpl<QxUseDepMapper, QxUseDep> implements QxUseDepService {

    private final QxUseDepMapper qxUseDepMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUseDep查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxUseDep> getPage(PageParam<QxUseDep> page, QxUseDep param) {
        return qxUseDepMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxUseDep getInfoById(String id) {
        return qxUseDepMapper.getInfoById(id);
    }

}


