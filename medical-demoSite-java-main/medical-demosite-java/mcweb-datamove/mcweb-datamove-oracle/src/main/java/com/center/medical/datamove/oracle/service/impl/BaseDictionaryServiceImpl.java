package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseDictionaryMapper;
import com.center.medical.datamove.oracle.bean.model.BaseDictionary;
import com.center.medical.datamove.oracle.service.BaseDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典(BaseDictionary)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:29
 */
@Slf4j
@Service("baseDictionaryService")
@RequiredArgsConstructor
public class BaseDictionaryServiceImpl extends ServiceImpl<BaseDictionaryMapper, BaseDictionary> implements BaseDictionaryService {

    private final BaseDictionaryMapper baseDictionaryMapper;

    /**
     * 分页查询[字典]列表
     *
     * @param page  分页参数
     * @param param BaseDictionary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseDictionary> getPage(PageParam<BaseDictionary> page, BaseDictionary param) {
        return baseDictionaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseDictionary getInfoById(String id) {
        return baseDictionaryMapper.getInfoById(id);
    }

}


