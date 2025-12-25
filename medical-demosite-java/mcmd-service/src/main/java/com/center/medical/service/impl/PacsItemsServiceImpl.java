package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PacsItemMapper;
import com.center.medical.service.PacsItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * PACS-收费项目(PacsItems)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:52
 */
@Slf4j
@Service("pacsItemsService")
@RequiredArgsConstructor
public class PacsItemsServiceImpl extends ServiceImpl<PacsItemMapper, PacsItems> implements PacsItemsService {

    private final PacsItemMapper pacsItemMapper;

    /**
     * 分页查询[PACS-收费项目]列表
     *
     * @param page  分页参数
     * @param param PacsItems查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsItems> getList(PageParam<PacsItems> page, PacsItems param) {
        return pacsItemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsItems getInfoById(String id) {
        return pacsItemMapper.getInfoById(id);
    }

}

