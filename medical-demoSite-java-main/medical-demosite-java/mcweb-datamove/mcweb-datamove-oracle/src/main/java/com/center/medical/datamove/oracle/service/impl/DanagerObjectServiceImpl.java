package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DanagerObjectMapper;
import com.center.medical.datamove.oracle.bean.model.DanagerObject;
import com.center.medical.datamove.oracle.service.DanagerObjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ危害因素收费项目(DanagerObject)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:08
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
    public IPage<DanagerObject> getPage(PageParam<DanagerObject> page, DanagerObject param) {
        return danagerObjectMapper.getPage(page, param);
    }


}


