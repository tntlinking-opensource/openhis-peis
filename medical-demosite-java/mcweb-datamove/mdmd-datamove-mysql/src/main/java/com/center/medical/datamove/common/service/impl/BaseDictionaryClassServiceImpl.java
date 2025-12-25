package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.BaseDictionaryClassMapper;
import com.center.medical.datamove.common.bean.model.BaseDictionaryClass;
import com.center.medical.datamove.common.service.BaseDictionaryClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典类型(BaseDictionaryClass)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:56
 */
@Slf4j
@Service("baseDictionaryClassService")
@RequiredArgsConstructor
public class BaseDictionaryClassServiceImpl extends ServiceImpl<BaseDictionaryClassMapper, BaseDictionaryClass> implements BaseDictionaryClassService {

    private final BaseDictionaryClassMapper baseDictionaryClassMapper;

    /**
     * 分页查询[字典类型]列表
     *
     * @param page  分页参数
     * @param param BaseDictionaryClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseDictionaryClass> getPage(PageParam<BaseDictionaryClass> page, BaseDictionaryClass param) {
        return baseDictionaryClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseDictionaryClass getInfoById(String id) {
        return baseDictionaryClassMapper.getInfoById(id);
    }

}


