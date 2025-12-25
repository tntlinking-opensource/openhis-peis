package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LisResultMapper;
import com.center.medical.datamove.oracle.bean.model.LisResult;
import com.center.medical.datamove.oracle.service.LisResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LIS接收结果(LisResult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:39
 */
@Slf4j
@Service("lisResultService")
@RequiredArgsConstructor
public class LisResultServiceImpl extends ServiceImpl<LisResultMapper, LisResult> implements LisResultService {

    private final LisResultMapper lisResultMapper;

    /**
     * 分页查询[LIS接收结果]列表
     *
     * @param page  分页参数
     * @param param LisResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<LisResult> getPage(PageParam<LisResult> page, LisResult param) {
        return lisResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public LisResult getInfoById(String id) {
        return lisResultMapper.getInfoById(id);
    }

}


