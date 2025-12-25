package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdReserMapper;
import com.center.medical.datamove.common.bean.model.KdReser;
import com.center.medical.datamove.common.service.KdReserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 每笔银行汇款结算详情(KdReser)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Slf4j
@Service("kdReserService")
@RequiredArgsConstructor
public class KdReserServiceImpl extends ServiceImpl<KdReserMapper, KdReser> implements KdReserService {

    private final KdReserMapper kdReserMapper;

    /**
     * 分页查询[每笔银行汇款结算详情]列表
     *
     * @param page  分页参数
     * @param param KdReser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdReser> getPage(PageParam<KdReser> page, KdReser param) {
        return kdReserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public KdReser getInfoById(String id) {
        return kdReserMapper.getInfoById(id);
    }

}


