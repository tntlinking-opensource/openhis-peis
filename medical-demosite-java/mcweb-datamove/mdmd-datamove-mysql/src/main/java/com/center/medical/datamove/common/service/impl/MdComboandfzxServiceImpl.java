package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdComboandfzxMapper;
import com.center.medical.datamove.common.bean.model.MdComboandfzx;
import com.center.medical.datamove.common.service.MdComboandfzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 最小套餐与分中心关联表(MdComboandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
@Slf4j
@Service("mdComboandfzxService")
@RequiredArgsConstructor
public class MdComboandfzxServiceImpl extends ServiceImpl<MdComboandfzxMapper, MdComboandfzx> implements MdComboandfzxService {

    private final MdComboandfzxMapper mdComboandfzxMapper;

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdComboandfzx> getPage(PageParam<MdComboandfzx> page, MdComboandfzx param) {
        return mdComboandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdComboandfzx getInfoById(String id) {
        return mdComboandfzxMapper.getInfoById(id);
    }

}


