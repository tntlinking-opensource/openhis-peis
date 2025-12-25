package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxCompletionMapper;
import com.center.medical.datamove.oracle.bean.model.FxCompletion;
import com.center.medical.datamove.oracle.service.FxCompletionService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:47
 */
@Slf4j
@Service("fxCompletionService")
@RequiredArgsConstructor
public class FxCompletionServiceImpl extends ServiceImpl<FxCompletionMapper, FxCompletion> implements FxCompletionService {

    private final FxCompletionMapper fxCompletionMapper;

    /**
     * 分页查询[本次职业健康检查漏检人员及漏检项目人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxCompletion查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxCompletion> getPage(PageParam<FxCompletion> page, FxCompletion param) {
        return fxCompletionMapper.getPage(page, param);
    }


}


