package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdItems;
import com.center.medical.olddata.dao.MdItemsMapper;
import com.center.medical.olddata.service.MdItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC收费项目表(MdItems)服务实现类
 *
 * @author ay
 * @since 2024-07-13 13:45:54
 */
@Slf4j
@Service("mdItemsService")
@RequiredArgsConstructor
public class MdItemsServiceImpl extends ServiceImpl<MdItemsMapper, MdItems> implements MdItemsService {

    private final MdItemsMapper mdItemsMapper;

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdItems查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItems> getPage(PageParam<MdItems> page, MdItems param) {
        return mdItemsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItems getInfoById(String id) {
        return mdItemsMapper.getInfoById(id);
    }

}

