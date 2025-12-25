package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxReviewInfoMapper;
import com.center.medical.bean.model.FxReviewInfo;
import com.center.medical.service.FxReviewInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(FxReviewInfo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
 */
@Slf4j
@Service("fxReviewInfoService")
@RequiredArgsConstructor
public class FxReviewInfoServiceImpl extends ServiceImpl<FxReviewInfoMapper, FxReviewInfo> implements FxReviewInfoService {

    private final FxReviewInfoMapper fxReviewInfoMapper;

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param FxReviewInfo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxReviewInfo> getList(PageParam<FxReviewInfo> page, FxReviewInfo param) {
        return fxReviewInfoMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxReviewInfo getInfoById(String id) {
        return fxReviewInfoMapper.getInfoById(id);
    }

    ;

}

