package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DanagerObjectMapper;
import com.center.medical.bean.model.DanagerObject;
import com.center.medical.service.DanagerObjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ危害因素收费项目(DanagerObject)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
@Slf4j
@Service("danagerObjectService")
@RequiredArgsConstructor
public class DanagerObjectServiceImpl extends ServiceImpl<DanagerObjectMapper, DanagerObject> implements DanagerObjectService {

    private final DanagerObjectMapper danagerObjectMapper;

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param DanagerObject查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DanagerObject> getList(PageParam<DanagerObject> page, DanagerObject param) {
        return danagerObjectMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DanagerObject getInfoById(String id) {
        return danagerObjectMapper.getInfoById(id);
    }

}

