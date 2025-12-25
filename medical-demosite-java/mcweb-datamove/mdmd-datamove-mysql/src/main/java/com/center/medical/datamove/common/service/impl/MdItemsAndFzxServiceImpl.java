package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdItemsAndFzxMapper;
import com.center.medical.datamove.common.bean.model.MdItemsAndFzx;
import com.center.medical.datamove.common.service.MdItemsAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC收费项目和分中心关联表(MdItemsAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Slf4j
@Service("mdItemsAndFzxService")
@RequiredArgsConstructor
public class MdItemsAndFzxServiceImpl extends ServiceImpl<MdItemsAndFzxMapper, MdItemsAndFzx> implements MdItemsAndFzxService {

    private final MdItemsAndFzxMapper mdItemsAndFzxMapper;

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdItemsAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItemsAndFzx> getPage(PageParam<MdItemsAndFzx> page, MdItemsAndFzx param) {
        return mdItemsAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItemsAndFzx getInfoById(String id) {
        return mdItemsAndFzxMapper.getInfoById(id);
    }

}


