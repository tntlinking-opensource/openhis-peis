package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Createmeal;
import com.center.medical.datamove.oracle.dao.CreatemealMapper;
import com.center.medical.datamove.oracle.service.CreatemealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 普通套餐表(Createmeal)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:58
 */
@Slf4j
@Service("createmealsService")
@RequiredArgsConstructor
public class CreatemealServiceImpl extends ServiceImpl<CreatemealMapper, Createmeal> implements CreatemealService {

    private final CreatemealMapper createmealMapper;

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createmeal> getPage(PageParam<Createmeal> page, Createmeal param) {
        return createmealMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createmeal getInfoById(String id) {
        return createmealMapper.getInfoById(id);
    }

}


