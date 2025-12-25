package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdReserway;
import com.center.medical.finance.dao.KdReserwayMapper;
import com.center.medical.finance.service.KdReserwayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * kingdeereserway(KdReserway)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
@Slf4j
@Service("kdReserwayService")
@RequiredArgsConstructor
public class KdReserwayServiceImpl extends ServiceImpl<KdReserwayMapper, KdReserway> implements KdReserwayService {

    private final KdReserwayMapper kdReserwayMapper;

    /**
     * 分页查询[kingdeereserway]列表
     *
     * @param page  分页参数
     * @param param KdReserway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdReserway> getPage(PageParam<KdReserway> page, KdReserway param) {
        return kdReserwayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public KdReserway getInfoById(String id) {
        return kdReserwayMapper.getInfoById(id);
    }

}

