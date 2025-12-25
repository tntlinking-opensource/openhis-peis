package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasexamltemtypeMapper;
import com.center.medical.datamove.oracle.bean.model.Basexamltemtype;
import com.center.medical.datamove.oracle.service.BasexamltemtypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目类型表(Basexamltemtype)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:46
 */
@Slf4j
@Service("basexamltemtypeService")
@RequiredArgsConstructor
public class BasexamltemtypeServiceImpl extends ServiceImpl<BasexamltemtypeMapper, Basexamltemtype> implements BasexamltemtypeService {

    private final BasexamltemtypeMapper basexamltemtypeMapper;

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param Basexamltemtype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basexamltemtype> getPage(PageParam<Basexamltemtype> page, Basexamltemtype param) {
        return basexamltemtypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Basexamltemtype getInfoById(String id) {
        return basexamltemtypeMapper.getInfoById(id);
    }

}


