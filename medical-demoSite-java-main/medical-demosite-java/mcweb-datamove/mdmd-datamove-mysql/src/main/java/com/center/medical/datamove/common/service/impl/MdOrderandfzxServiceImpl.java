package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOrderandfzxMapper;
import com.center.medical.datamove.common.bean.model.MdOrderandfzx;
import com.center.medical.datamove.common.service.MdOrderandfzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单与分中心关联表(MdOrderandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
@Slf4j
@Service("mdOrderandfzxService")
@RequiredArgsConstructor
public class MdOrderandfzxServiceImpl extends ServiceImpl<MdOrderandfzxMapper, MdOrderandfzx> implements MdOrderandfzxService {

    private final MdOrderandfzxMapper mdOrderandfzxMapper;

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdOrderandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOrderandfzx> getPage(PageParam<MdOrderandfzx> page, MdOrderandfzx param) {
        return mdOrderandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOrderandfzx getInfoById(String id) {
        return mdOrderandfzxMapper.getInfoById(id);
    }

    ;

}


