package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdMealandfzx;
import com.center.medical.datamove.admin.dao.MdMealandfzxMapper;
import com.center.medical.datamove.admin.service.MdMealandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐与分中心关联表(MdMealandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:26:22
 */
@Slf4j
@Service("mdMealandfzxService")
@RequiredArgsConstructor
public class MdMealandfzxServiceImpl extends ServiceImpl<MdMealandfzxMapper, MdMealandfzx> implements MdMealandfzxService {

    private final MdMealandfzxMapper mdMealandfzxMapper;

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdMealandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMealandfzx> getPage(PageParam<MdMealandfzx> page, MdMealandfzx param) {
        return mdMealandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMealandfzx getInfoById(String id) {
        return mdMealandfzxMapper.getInfoById(id);
    }

    /**
     * 批量插入或更新
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdMealandfzx> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }
}


