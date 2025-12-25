package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsBkDictionaryMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsBkDictionary;
import com.center.medical.datamove.oracle.service.QxWsBkDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsBkDictionary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:00
 */
@Slf4j
@Service("qxWsBkDictionaryService")
@RequiredArgsConstructor
public class QxWsBkDictionaryServiceImpl extends ServiceImpl<QxWsBkDictionaryMapper, QxWsBkDictionary> implements QxWsBkDictionaryService {

    private final QxWsBkDictionaryMapper qxWsBkDictionaryMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsBkDictionary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsBkDictionary> getPage(PageParam<QxWsBkDictionary> page, QxWsBkDictionary param) {
        return qxWsBkDictionaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsBkDictionary getInfoById(Object id) {
        return qxWsBkDictionaryMapper.getInfoById(id);
    }

}


