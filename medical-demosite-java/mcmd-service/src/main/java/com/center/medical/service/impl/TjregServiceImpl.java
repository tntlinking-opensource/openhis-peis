package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjregMapper;
import com.center.medical.bean.model.Tjreg;
import com.center.medical.service.TjregService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS一般检查(Tjreg)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
@Slf4j
@Service("tjregService")
@RequiredArgsConstructor
public class TjregServiceImpl extends ServiceImpl<TjregMapper, Tjreg> implements TjregService {

    private final TjregMapper tjregMapper;

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param Tjreg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Tjreg> getList(PageParam<Tjreg> page, Tjreg param) {
        return tjregMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Tjreg getInfoById(String id) {
        return tjregMapper.getInfoById(id);
    }

}

