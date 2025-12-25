package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisStateMapper;
import com.center.medical.datamove.oracle.bean.model.PeisState;
import com.center.medical.datamove.oracle.service.PeisStateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者上传状态(PeisState)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:03
 */
@Slf4j
@Service("peisStateService")
@RequiredArgsConstructor
public class PeisStateServiceImpl extends ServiceImpl<PeisStateMapper, PeisState> implements PeisStateService {

    private final PeisStateMapper peisStateMapper;

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param PeisState查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisState> getPage(PageParam<PeisState> page, PeisState param) {
        return peisStateMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisState getInfoById(String id) {
        return peisStateMapper.getInfoById(id);
    }

}


