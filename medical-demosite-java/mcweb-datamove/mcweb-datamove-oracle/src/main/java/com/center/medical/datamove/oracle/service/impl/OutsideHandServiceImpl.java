package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OutsideHandMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideHand;
import com.center.medical.datamove.oracle.service.OutsideHandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送手动结果表(OutsideHand)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:33
 */
@Slf4j
@Service("outsideHandService")
@RequiredArgsConstructor
public class OutsideHandServiceImpl extends ServiceImpl<OutsideHandMapper, OutsideHand> implements OutsideHandService {

    private final OutsideHandMapper outsideHandMapper;

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param OutsideHand查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideHand> getPage(PageParam<OutsideHand> page, OutsideHand param) {
        return outsideHandMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OutsideHand getInfoById(String id) {
        return outsideHandMapper.getInfoById(id);
    }

}


