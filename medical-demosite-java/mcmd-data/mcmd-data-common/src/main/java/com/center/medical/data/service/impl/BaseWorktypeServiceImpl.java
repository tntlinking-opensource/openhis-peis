package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseWorktype;
import com.center.medical.data.dao.BaseWorktypeMapper;
import com.center.medical.data.service.BaseWorktypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 工种(BaseWorktype)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
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
    public IPage<BaseWorktype> getList(PageParam<BaseWorktype> page, BaseWorktype param) {
        return baseWorktypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseWorktype getInfoById(String id) {
        return baseWorktypeMapper.getInfoById(id);
    }

    /**
     * 工种信息查询
     *
     * @param typeName
     * @return
     */
    @Override
    public IPage<BaseWorktype> getBaseWorktypeSql(PageParam<BaseWorktype> page, String typeName) {
        if (ObjectUtils.isNotEmpty(typeName)) {
            typeName = typeName.trim();
        }
        return baseWorktypeMapper.getBaseWorktypeSql(page, typeName);
    }
}

