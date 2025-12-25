package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugstoreClassMapper;
import com.center.medical.datamove.oracle.bean.model.DrugstoreClass;
import com.center.medical.datamove.oracle.service.DrugstoreClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DrugstoreClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:28
 */
@Slf4j
@Service("drugstoreClassService")
@RequiredArgsConstructor
public class DrugstoreClassServiceImpl extends ServiceImpl<DrugstoreClassMapper, DrugstoreClass> implements DrugstoreClassService {

    private final DrugstoreClassMapper drugstoreClassMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrugstoreClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstoreClass> getPage(PageParam<DrugstoreClass> page, DrugstoreClass param) {
        return drugstoreClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DrugstoreClass getInfoById(String id) {
        return drugstoreClassMapper.getInfoById(id);
    }

}


