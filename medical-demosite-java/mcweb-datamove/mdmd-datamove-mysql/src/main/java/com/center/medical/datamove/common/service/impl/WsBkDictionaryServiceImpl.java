package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsBkDictionaryMapper;
import com.center.medical.datamove.common.bean.model.WsBkDictionary;
import com.center.medical.datamove.common.service.WsBkDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站字典(WsBkDictionary)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Slf4j
@Service("wsBkDictionaryService")
@RequiredArgsConstructor
public class WsBkDictionaryServiceImpl extends ServiceImpl<WsBkDictionaryMapper, WsBkDictionary> implements WsBkDictionaryService {

    private final WsBkDictionaryMapper wsBkDictionaryMapper;

    /**
     * 分页查询[网站字典]列表
     *
     * @param page  分页参数
     * @param param WsBkDictionary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsBkDictionary> getPage(PageParam<WsBkDictionary> page, WsBkDictionary param) {
        return wsBkDictionaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsBkDictionary getInfoById(String id) {
        return wsBkDictionaryMapper.getInfoById(id);
    }

}


