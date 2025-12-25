package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TestMapper;
import com.center.medical.datamove.oracle.bean.model.Test;
import com.center.medical.datamove.oracle.service.TestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Test)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:22
 */
@Slf4j
@Service("testService")
@RequiredArgsConstructor
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    private final TestMapper testMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Test查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Test> getPage(PageParam<Test> page, Test param) {
        return testMapper.getPage(page, param);
    }


}


