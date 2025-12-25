package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.sellcrm.service.ComboexamitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用于判断职业小结(Comboexamitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:26
 */
@Slf4j
@Service("comboexamitemService")
@RequiredArgsConstructor
public class ComboexamitemServiceImpl extends ServiceImpl<ComboexamitemMapper, Comboexamitem> implements ComboexamitemService {

    private final ComboexamitemMapper comboexamitemMapper;

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param Comboexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Comboexamitem> getList(PageParam<Comboexamitem> page, Comboexamitem param) {
        return comboexamitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Comboexamitem getInfoById(String id) {
        return comboexamitemMapper.getInfoById(id);
    }

    ;

}

