package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTemporaryqueuetestMapper;
import com.center.medical.datamove.common.bean.model.MdTemporaryqueuetest;
import com.center.medical.datamove.common.service.MdTemporaryqueuetestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * temporaryqueuetest(MdTemporaryqueuetest)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:58
 */
@Slf4j
@Service("mdTemporaryqueuetestService")
@RequiredArgsConstructor
public class MdTemporaryqueuetestServiceImpl extends ServiceImpl<MdTemporaryqueuetestMapper, MdTemporaryqueuetest> implements MdTemporaryqueuetestService {

    private final MdTemporaryqueuetestMapper mdTemporaryqueuetestMapper;

    /**
     * 分页查询[temporaryqueuetest]列表
     *
     * @param page  分页参数
     * @param param MdTemporaryqueuetest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTemporaryqueuetest> getPage(PageParam<MdTemporaryqueuetest> page, MdTemporaryqueuetest param) {
        return mdTemporaryqueuetestMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTemporaryqueuetest getInfoById(String id) {
        return mdTemporaryqueuetestMapper.getInfoById(id);
    }

}


