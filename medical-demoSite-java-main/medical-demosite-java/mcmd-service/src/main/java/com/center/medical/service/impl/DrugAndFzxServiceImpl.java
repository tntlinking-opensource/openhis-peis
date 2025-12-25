package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DrugAndFzxMapper;
import com.center.medical.bean.model.DrugAndFzx;
import com.center.medical.service.DrugAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 药品分中心映射(DrugAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:34
 */
@Slf4j
@Service("drugAndFzxService")
@RequiredArgsConstructor
public class DrugAndFzxServiceImpl extends ServiceImpl<DrugAndFzxMapper, DrugAndFzx> implements DrugAndFzxService {

    private final DrugAndFzxMapper drugAndFzxMapper;

    /**
     * 分页查询[药品分中心映射]列表
     *
     * @param page  分页参数
     * @param param DrugAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugAndFzx> getList(PageParam<DrugAndFzx> page, DrugAndFzx param) {
        return drugAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugAndFzx getInfoById(String id) {
        return drugAndFzxMapper.getInfoById(id);
    }

    ;

}

