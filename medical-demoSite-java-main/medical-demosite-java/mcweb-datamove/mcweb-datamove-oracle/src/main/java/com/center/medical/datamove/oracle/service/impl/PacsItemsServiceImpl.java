package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsItemsMapper;
import com.center.medical.datamove.oracle.bean.model.PacsItems;
import com.center.medical.datamove.oracle.service.PacsItemsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsItems)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:46
 */
@Slf4j
@Service("pacsItemsService")
@RequiredArgsConstructor
public class PacsItemsServiceImpl extends ServiceImpl<PacsItemsMapper, PacsItems> implements PacsItemsService {

    private final PacsItemsMapper pacsItemsMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsItems查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsItems> getPage(PageParam<PacsItems> page, PacsItems param) {
        return pacsItemsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsItems getInfoById(String id) {
        return pacsItemsMapper.getInfoById(id);
    }

}


