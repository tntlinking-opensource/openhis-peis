package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdCreatemeal;
import com.center.medical.olddata.dao.MdCreatemealMapper;
import com.center.medical.olddata.service.MdCreatemealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐表(MdCreatemeal)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:57:53
 */
@Slf4j
@Service("mdCreatemealService")
@RequiredArgsConstructor
public class MdCreatemealServiceImpl extends ServiceImpl<MdCreatemealMapper, MdCreatemeal> implements MdCreatemealService {

    private final MdCreatemealMapper mdCreatemealMapper;

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param MdCreatemeal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreatemeal> getPage(PageParam<MdCreatemeal> page, MdCreatemeal param) {
        return mdCreatemealMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdCreatemeal getInfoById(String id) {
        return mdCreatemealMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     *
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdCreatemeal map) {
        saveOrUpdate(map);
    }


    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdCreatemeal> mdCreatemealList) {
        saveOrUpdateBatch(mdCreatemealList);
    }
}


