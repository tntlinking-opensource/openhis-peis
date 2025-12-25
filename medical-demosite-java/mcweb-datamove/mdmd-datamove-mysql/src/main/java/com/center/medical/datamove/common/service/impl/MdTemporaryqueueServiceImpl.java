package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTemporaryqueueMapper;
import com.center.medical.datamove.common.bean.model.MdTemporaryqueue;
import com.center.medical.datamove.common.service.MdTemporaryqueueService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * temporaryqueue(MdTemporaryqueue)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:57
 */
@Slf4j
@Service("mdTemporaryqueueService")
@RequiredArgsConstructor
public class MdTemporaryqueueServiceImpl extends ServiceImpl<MdTemporaryqueueMapper, MdTemporaryqueue> implements MdTemporaryqueueService {

    private final MdTemporaryqueueMapper mdTemporaryqueueMapper;

    /**
     * 分页查询[temporaryqueue]列表
     *
     * @param page  分页参数
     * @param param MdTemporaryqueue查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTemporaryqueue> getPage(PageParam<MdTemporaryqueue> page, MdTemporaryqueue param) {
        return mdTemporaryqueueMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTemporaryqueue getInfoById(String id) {
        return mdTemporaryqueueMapper.getInfoById(id);
    }

}


