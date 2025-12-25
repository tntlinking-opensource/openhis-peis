package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SaleerMapper;
import com.center.medical.datamove.oracle.bean.model.Saleer;
import com.center.medical.datamove.oracle.service.SaleerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Saleer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:30
 */
@Slf4j
@Service("saleerService")
@RequiredArgsConstructor
public class SaleerServiceImpl extends ServiceImpl<SaleerMapper, Saleer> implements SaleerService {

    private final SaleerMapper saleerMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Saleer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Saleer> getPage(PageParam<Saleer> page, Saleer param) {
        return saleerMapper.getPage(page, param);
    }


}


