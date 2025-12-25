package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdItemsNewMapper;
import com.center.medical.datamove.common.bean.model.MdItemsNew;
import com.center.medical.datamove.common.service.MdItemsNewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC收费项目表(MdItemsNew)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Slf4j
@Service("mdItemsNewService")
@RequiredArgsConstructor
public class MdItemsNewServiceImpl extends ServiceImpl<MdItemsNewMapper, MdItemsNew> implements MdItemsNewService {

    private final MdItemsNewMapper mdItemsNewMapper;

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdItemsNew查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItemsNew> getPage(PageParam<MdItemsNew> page, MdItemsNew param) {
        return mdItemsNewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItemsNew getInfoById(String id) {
        return mdItemsNewMapper.getInfoById(id);
    }

}


