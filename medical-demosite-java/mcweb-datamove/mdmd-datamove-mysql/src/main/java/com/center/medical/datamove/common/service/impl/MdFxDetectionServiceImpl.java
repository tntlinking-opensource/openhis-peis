package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxDetectionMapper;
import com.center.medical.datamove.common.bean.model.MdFxDetection;
import com.center.medical.datamove.common.service.MdFxDetectionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-检出统计、团体小结（健康）(MdFxDetection)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Slf4j
@Service("mdFxDetectionService")
@RequiredArgsConstructor
public class MdFxDetectionServiceImpl extends ServiceImpl<MdFxDetectionMapper, MdFxDetection> implements MdFxDetectionService {

    private final MdFxDetectionMapper mdFxDetectionMapper;

    /**
     * 分页查询[综合分析-检出统计、团体小结（健康）]列表
     *
     * @param page  分页参数
     * @param param MdFxDetection查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxDetection> getPage(PageParam<MdFxDetection> page, MdFxDetection param) {
        return mdFxDetectionMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxDetection getInfoById(String id) {
        return mdFxDetectionMapper.getInfoById(id);
    }

}


