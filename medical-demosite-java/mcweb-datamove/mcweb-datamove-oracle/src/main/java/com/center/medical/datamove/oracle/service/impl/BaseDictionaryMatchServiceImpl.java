package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseDictionaryMatchMapper;
import com.center.medical.datamove.oracle.bean.model.BaseDictionaryMatch;
import com.center.medical.datamove.oracle.service.BaseDictionaryMatchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:32
 */
@Slf4j
@Service("baseDictionaryMatchService")
@RequiredArgsConstructor
public class BaseDictionaryMatchServiceImpl extends ServiceImpl<BaseDictionaryMatchMapper, BaseDictionaryMatch> implements BaseDictionaryMatchService {

    private final BaseDictionaryMatchMapper baseDictionaryMatchMapper;

    /**
     * 分页查询[体检系统-职业卫生管理平台 字典匹配]列表
     *
     * @param page  分页参数
     * @param param BaseDictionaryMatch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseDictionaryMatch> getPage(PageParam<BaseDictionaryMatch> page, BaseDictionaryMatch param) {
        return baseDictionaryMatchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseDictionaryMatch getInfoById(String id) {
        return baseDictionaryMatchMapper.getInfoById(id);
    }

}


