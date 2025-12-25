package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxDetectionzyMapper;
import com.center.medical.datamove.common.bean.model.MdFxDetectionzy;
import com.center.medical.datamove.common.service.MdFxDetectionzyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ综合分析-检出人数（职业）(MdFxDetectionzy)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Slf4j
@Service("mdFxDetectionzyService")
@RequiredArgsConstructor
public class MdFxDetectionzyServiceImpl extends ServiceImpl<MdFxDetectionzyMapper, MdFxDetectionzy> implements MdFxDetectionzyService {

    private final MdFxDetectionzyMapper mdFxDetectionzyMapper;

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param MdFxDetectionzy查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxDetectionzy> getPage(PageParam<MdFxDetectionzy> page, MdFxDetectionzy param) {
        return mdFxDetectionzyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxDetectionzy getInfoById(String id) {
        return mdFxDetectionzyMapper.getInfoById(id);
    }

}


