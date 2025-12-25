package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasexamltemSignMapper;
import com.center.medical.datamove.oracle.bean.model.BasexamltemSign;
import com.center.medical.datamove.oracle.service.BasexamltemSignService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目体证词关联表(BasexamltemSign)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:45
 */
@Slf4j
@Service("basexamltemSignService")
@RequiredArgsConstructor
public class BasexamltemSignServiceImpl extends ServiceImpl<BasexamltemSignMapper, BasexamltemSign> implements BasexamltemSignService {

    private final BasexamltemSignMapper basexamltemSignMapper;

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param BasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasexamltemSign> getPage(PageParam<BasexamltemSign> page, BasexamltemSign param) {
        return basexamltemSignMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BasexamltemSign getInfoById(String id) {
        return basexamltemSignMapper.getInfoById(id);
    }

}


