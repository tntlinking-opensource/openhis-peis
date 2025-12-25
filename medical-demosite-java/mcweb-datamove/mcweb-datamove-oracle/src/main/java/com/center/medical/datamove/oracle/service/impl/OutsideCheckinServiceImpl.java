package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OutsideCheckinMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideCheckin;
import com.center.medical.datamove.oracle.service.OutsideCheckinService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送项目图片关联表(OutsideCheckin)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:31
 */
@Slf4j
@Service("outsideCheckinService")
@RequiredArgsConstructor
public class OutsideCheckinServiceImpl extends ServiceImpl<OutsideCheckinMapper, OutsideCheckin> implements OutsideCheckinService {

    private final OutsideCheckinMapper outsideCheckinMapper;

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param OutsideCheckin查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideCheckin> getPage(PageParam<OutsideCheckin> page, OutsideCheckin param) {
        return outsideCheckinMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OutsideCheckin getInfoById(String id) {
        return outsideCheckinMapper.getInfoById(id);
    }

}


