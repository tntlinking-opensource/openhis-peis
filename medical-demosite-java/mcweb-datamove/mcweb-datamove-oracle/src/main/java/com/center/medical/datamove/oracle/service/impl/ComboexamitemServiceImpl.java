package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ComboexamitemMapper;
import com.center.medical.datamove.oracle.bean.model.Comboexamitem;
import com.center.medical.datamove.oracle.service.ComboexamitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Comboexamitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:43
 */
@Slf4j
@Service("comboexamitemService")
@RequiredArgsConstructor
public class ComboexamitemServiceImpl extends ServiceImpl<ComboexamitemMapper, Comboexamitem> implements ComboexamitemService {

    private final ComboexamitemMapper comboexamitemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Comboexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboexamitem> getPage(PageParam<Comboexamitem> page, Comboexamitem param) {
        return comboexamitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Comboexamitem getInfoById(String id) {
        return comboexamitemMapper.getInfoById(id);
    }

}


