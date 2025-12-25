package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FinanceinputMapper;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.service.FinanceinputService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售财务录入表(Financeinput)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
@Slf4j
@Service("financeinputService")
@RequiredArgsConstructor
public class FinanceinputServiceImpl extends ServiceImpl<FinanceinputMapper, Financeinput> implements FinanceinputService {

    private final FinanceinputMapper financeinputMapper;

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param Financeinput查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Financeinput> getList(PageParam<Financeinput> page, Financeinput param) {
        return financeinputMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Financeinput getInfoById(String id) {
        return financeinputMapper.getInfoById(id);
    }

}

