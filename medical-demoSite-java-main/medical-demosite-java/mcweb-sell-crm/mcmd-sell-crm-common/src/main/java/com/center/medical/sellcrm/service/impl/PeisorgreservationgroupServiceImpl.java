package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.sellcrm.service.PeisorgreservationgroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检者任务分组(Peisorgreservationgroup)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:02
 */
@Slf4j
@Service("peisorgreservationgroupService")
@RequiredArgsConstructor
public class PeisorgreservationgroupServiceImpl extends ServiceImpl<PeisorgreservationgroupMapper, Peisorgreservationgroup> implements PeisorgreservationgroupService {

    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationgroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peisorgreservationgroup> getPage(PageParam<Peisorgreservationgroup> page, Peisorgreservationgroup param) {
        return peisorgreservationgroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peisorgreservationgroup getInfoById(String id) {
        return peisorgreservationgroupMapper.getInfoById(id);
    }

}

