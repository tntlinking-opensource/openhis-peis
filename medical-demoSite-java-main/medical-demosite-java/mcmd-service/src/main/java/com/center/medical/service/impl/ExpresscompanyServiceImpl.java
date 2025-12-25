package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ExpresscompanyMapper;
import com.center.medical.bean.model.Expresscompany;
import com.center.medical.service.ExpresscompanyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 快递公司表(Expresscompany)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
@Slf4j
@Service("expresscompanyService")
@RequiredArgsConstructor
public class ExpresscompanyServiceImpl extends ServiceImpl<ExpresscompanyMapper, Expresscompany> implements ExpresscompanyService {

    private final ExpresscompanyMapper expresscompanyMapper;

    /**
     * 分页查询[快递公司表]列表
     *
     * @param page  分页参数
     * @param param Expresscompany查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Expresscompany> getList(PageParam<Expresscompany> page, Expresscompany param) {
        return expresscompanyMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Expresscompany getInfoById(String id) {
        return expresscompanyMapper.getInfoById(id);
    }

}

