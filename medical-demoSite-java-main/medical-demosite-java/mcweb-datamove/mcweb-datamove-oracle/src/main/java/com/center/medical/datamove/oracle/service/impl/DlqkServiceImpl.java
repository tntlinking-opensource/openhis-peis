package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DlqkMapper;
import com.center.medical.datamove.oracle.bean.model.Dlqk;
import com.center.medical.datamove.oracle.service.DlqkService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS锻炼情况(Dlqk)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:18
 */
@Slf4j
@Service("dlqkService")
@RequiredArgsConstructor
public class DlqkServiceImpl extends ServiceImpl<DlqkMapper, Dlqk> implements DlqkService {

    private final DlqkMapper dlqkMapper;

    /**
     * 分页查询[KS锻炼情况]列表
     *
     * @param page  分页参数
     * @param param Dlqk查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Dlqk> getPage(PageParam<Dlqk> page, Dlqk param) {
        return dlqkMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Dlqk getInfoById(String id) {
        return dlqkMapper.getInfoById(id);
    }

}


