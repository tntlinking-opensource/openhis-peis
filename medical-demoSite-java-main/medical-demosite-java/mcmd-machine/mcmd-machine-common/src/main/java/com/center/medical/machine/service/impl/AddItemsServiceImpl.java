package com.center.medical.machine.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.machine.dao.AddItemsMapper;
import com.center.medical.machine.service.AddItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@Service("addItemsService")
@RequiredArgsConstructor
public class AddItemsServiceImpl extends ServiceImpl<AddItemsMapper, Peispatient> implements AddItemsService {

    private final AddItemsMapper addItemsMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getList(PageParam<Peispatient> page, Peispatient param) {
        return addItemsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return addItemsMapper.getInfoById(id);
    }

}

