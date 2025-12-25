package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Createcombo;
import com.center.medical.datamove.oracle.dao.CreatecomboMapper;
import com.center.medical.datamove.oracle.service.CreatecomboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 套餐表(Createcombo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:55
 */
@Slf4j
@Service("createcombosService")
@RequiredArgsConstructor
public class CreatecomboServiceImpl extends ServiceImpl<CreatecomboMapper, Createcombo> implements CreatecomboService {

    private final CreatecomboMapper createcomboMapper;

    /**
     * 分页查询[套餐表]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createcombo> getPage(PageParam<Createcombo> page, Createcombo param) {
        return createcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createcombo getInfoById(String id) {
        return createcomboMapper.getInfoById(id);
    }

}


