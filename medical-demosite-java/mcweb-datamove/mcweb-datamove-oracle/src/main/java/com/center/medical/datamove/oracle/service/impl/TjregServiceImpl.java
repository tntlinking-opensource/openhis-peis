package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjregMapper;
import com.center.medical.datamove.oracle.bean.model.Tjreg;
import com.center.medical.datamove.oracle.service.TjregService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS一般检查(Tjreg)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:33
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
    public IPage<Tjreg> getPage(PageParam<Tjreg> page, Tjreg param) {
        return tjregMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Tjreg getInfoById(String id) {
        return tjregMapper.getInfoById(id);
    }

    ;

}


