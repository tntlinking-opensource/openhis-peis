package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PrictureMapper;
import com.center.medical.bean.model.Pricture;
import com.center.medical.service.PrictureService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS图片存储表(Pricture)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:05
 */
@Slf4j
@Service("prictureService")
@RequiredArgsConstructor
public class PrictureServiceImpl extends ServiceImpl<PrictureMapper, Pricture> implements PrictureService {

    private final PrictureMapper prictureMapper;

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param Pricture查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Pricture> getList(PageParam<Pricture> page, Pricture param) {
        return prictureMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Pricture getInfoById(String id) {
        return prictureMapper.getInfoById(id);
    }

}

