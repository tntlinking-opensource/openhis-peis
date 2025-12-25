package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysDictDataMapper;
import com.center.medical.datamove.common.bean.model.SysDictData;
import com.center.medical.datamove.common.service.SysDictDataService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典数据表(SysDictData)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@Service("sysDictDataService")
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 分页查询[字典数据表]列表
     *
     * @param page  分页参数
     * @param param SysDictData查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysDictData> getPage(PageParam<SysDictData> page, SysDictData param) {
        return sysDictDataMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictCode
     * @return 详情信息
     */
    @Override
    public SysDictData getInfoById(Long id) {
        return sysDictDataMapper.getInfoById(id);
    }

}


