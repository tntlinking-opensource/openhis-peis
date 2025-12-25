package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsItemsMapper;
import com.center.medical.datamove.common.bean.model.MdPacsItems;
import com.center.medical.datamove.common.service.MdPacsItemsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-收费项目(MdPacsItems)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Slf4j
@Service("mdPacsItemsService")
@RequiredArgsConstructor
public class MdPacsItemsServiceImpl extends ServiceImpl<MdPacsItemsMapper, MdPacsItems> implements MdPacsItemsService {

    private final MdPacsItemsMapper mdPacsItemsMapper;

    /**
     * 分页查询[PACS-收费项目]列表
     *
     * @param page  分页参数
     * @param param MdPacsItems查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsItems> getPage(PageParam<MdPacsItems> page, MdPacsItems param) {
        return mdPacsItemsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsItems getInfoById(String id) {
        return mdPacsItemsMapper.getInfoById(id);
    }

    ;

}


