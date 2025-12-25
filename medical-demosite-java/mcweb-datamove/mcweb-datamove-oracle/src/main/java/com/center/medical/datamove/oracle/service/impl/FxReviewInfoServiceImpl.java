package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxReviewInfoMapper;
import com.center.medical.datamove.oracle.bean.model.FxReviewInfo;
import com.center.medical.datamove.oracle.service.FxReviewInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FxReviewInfo)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:59
 */
@Slf4j
@Service("fxReviewInfoService")
@RequiredArgsConstructor
public class FxReviewInfoServiceImpl extends ServiceImpl<FxReviewInfoMapper, FxReviewInfo> implements FxReviewInfoService {

    private final FxReviewInfoMapper fxReviewInfoMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FxReviewInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxReviewInfo> getPage(PageParam<FxReviewInfo> page, FxReviewInfo param) {
        return fxReviewInfoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxReviewInfo getInfoById(String id) {
        return fxReviewInfoMapper.getInfoById(id);
    }

}


