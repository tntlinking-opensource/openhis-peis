package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FinanceinputMapper;
import com.center.medical.datamove.oracle.bean.model.Financeinput;
import com.center.medical.datamove.oracle.service.FinanceinputService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 财务录入表(Financeinput)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:45
 */
@Slf4j
@Service("financeinputService")
@RequiredArgsConstructor
public class FinanceinputServiceImpl extends ServiceImpl<FinanceinputMapper, Financeinput> implements FinanceinputService {

    private final FinanceinputMapper financeinputMapper;

    /**
     * 分页查询[财务录入表]列表
     *
     * @param page  分页参数
     * @param param Financeinput查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Financeinput> getPage(PageParam<Financeinput> page, Financeinput param) {
        return financeinputMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Financeinput getInfoById(String id) {
        return financeinputMapper.getInfoById(id);
    }

    ;

}


