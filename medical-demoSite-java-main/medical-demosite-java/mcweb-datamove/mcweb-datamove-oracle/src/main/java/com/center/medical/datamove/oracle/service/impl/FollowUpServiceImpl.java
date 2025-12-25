package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FollowUpMapper;
import com.center.medical.datamove.oracle.bean.model.FollowUp;
import com.center.medical.datamove.oracle.service.FollowUpService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC随访目的维护(FollowUp)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:46
 */
@Slf4j
@Service("followUpService")
@RequiredArgsConstructor
public class FollowUpServiceImpl extends ServiceImpl<FollowUpMapper, FollowUp> implements FollowUpService {

    private final FollowUpMapper followUpMapper;

    /**
     * 分页查询[JC随访目的维护]列表
     *
     * @param page  分页参数
     * @param param FollowUp查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FollowUp> getPage(PageParam<FollowUp> page, FollowUp param) {
        return followUpMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FollowUp getInfoById(String id) {
        return followUpMapper.getInfoById(id);
    }

}


