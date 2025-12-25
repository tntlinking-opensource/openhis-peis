package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseWorktypeMapper;
import com.center.medical.datamove.oracle.bean.model.BaseWorktype;
import com.center.medical.datamove.oracle.service.BaseWorktypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 工种(BaseWorktype)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:39
 */
@Slf4j
@Service("baseWorktypeService")
@RequiredArgsConstructor
public class BaseWorktypeServiceImpl extends ServiceImpl<BaseWorktypeMapper, BaseWorktype> implements BaseWorktypeService {

    private final BaseWorktypeMapper baseWorktypeMapper;

    /**
     * 分页查询[工种]列表
     *
     * @param page  分页参数
     * @param param BaseWorktype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseWorktype> getPage(PageParam<BaseWorktype> page, BaseWorktype param) {
        return baseWorktypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseWorktype getInfoById(String id) {
        return baseWorktypeMapper.getInfoById(id);
    }

}


