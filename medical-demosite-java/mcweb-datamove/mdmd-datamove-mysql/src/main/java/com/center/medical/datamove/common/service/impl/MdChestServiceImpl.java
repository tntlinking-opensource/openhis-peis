package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdChestMapper;
import com.center.medical.datamove.common.bean.model.MdChest;
import com.center.medical.datamove.common.service.MdChestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单柜子信息(MdChest)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
@Slf4j
@Service("mdChestService")
@RequiredArgsConstructor
public class MdChestServiceImpl extends ServiceImpl<MdChestMapper, MdChest> implements MdChestService {

    private final MdChestMapper mdChestMapper;

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param MdChest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdChest> getPage(PageParam<MdChest> page, MdChest param) {
        return mdChestMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdChest getInfoById(String id) {
        return mdChestMapper.getInfoById(id);
    }

}


