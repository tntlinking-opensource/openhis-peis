package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdItemsAndFzxNewMapper;
import com.center.medical.datamove.common.bean.model.MdItemsAndFzxNew;
import com.center.medical.datamove.common.service.MdItemsAndFzxNewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC收费项目和分中心关联表(MdItemsAndFzxNew)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Slf4j
@Service("mdItemsAndFzxNewService")
@RequiredArgsConstructor
public class MdItemsAndFzxNewServiceImpl extends ServiceImpl<MdItemsAndFzxNewMapper, MdItemsAndFzxNew> implements MdItemsAndFzxNewService {

    private final MdItemsAndFzxNewMapper mdItemsAndFzxNewMapper;

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdItemsAndFzxNew查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItemsAndFzxNew> getPage(PageParam<MdItemsAndFzxNew> page, MdItemsAndFzxNew param) {
        return mdItemsAndFzxNewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItemsAndFzxNew getInfoById(String id) {
        return mdItemsAndFzxNewMapper.getInfoById(id);
    }

}


