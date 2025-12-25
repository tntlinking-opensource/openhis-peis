package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxBkDictionaryMapper;
import com.center.medical.datamove.oracle.bean.model.QxBkDictionary;
import com.center.medical.datamove.oracle.service.QxBkDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxBkDictionary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:43
 */
@Slf4j
@Service("qxBkDictionaryService")
@RequiredArgsConstructor
public class QxBkDictionaryServiceImpl extends ServiceImpl<QxBkDictionaryMapper, QxBkDictionary> implements QxBkDictionaryService {

    private final QxBkDictionaryMapper qxBkDictionaryMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxBkDictionary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxBkDictionary> getPage(PageParam<QxBkDictionary> page, QxBkDictionary param) {
        return qxBkDictionaryMapper.getPage(page, param);
    }


}


