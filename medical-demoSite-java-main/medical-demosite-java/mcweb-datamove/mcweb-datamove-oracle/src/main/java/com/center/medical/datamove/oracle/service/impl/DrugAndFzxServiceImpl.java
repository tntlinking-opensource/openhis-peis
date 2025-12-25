package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.DrugAndFzx;
import com.center.medical.datamove.oracle.service.DrugAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DrugAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:24
 */
@Slf4j
@Service("drugAndFzxService")
@RequiredArgsConstructor
public class DrugAndFzxServiceImpl extends ServiceImpl<DrugAndFzxMapper, DrugAndFzx> implements DrugAndFzxService {

    private final DrugAndFzxMapper drugAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrugAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugAndFzx> getPage(PageParam<DrugAndFzx> page, DrugAndFzx param) {
        return drugAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DrugAndFzx getInfoById(String id) {
        return drugAndFzxMapper.getInfoById(id);
    }

}


