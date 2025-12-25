package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.UtsideHandMapper;
import com.center.medical.bean.model.UtsideHand;
import com.center.medical.service.UtsideHandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送手动结果表(UtsideHand)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
@Slf4j
@Service("utsideHandService")
@RequiredArgsConstructor
public class UtsideHandServiceImpl extends ServiceImpl<UtsideHandMapper, UtsideHand> implements UtsideHandService {

    private final UtsideHandMapper utsideHandMapper;

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param UtsideHand查询参数
     * @return 分页数据
     */
    @Override
    public IPage<UtsideHand> getList(PageParam<UtsideHand> page, UtsideHand param) {
        return utsideHandMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public UtsideHand getInfoById(String id) {
        return utsideHandMapper.getInfoById(id);
    }

}

