package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DmyhResultMapper;
import com.center.medical.datamove.oracle.bean.model.DmyhResult;
import com.center.medical.datamove.oracle.service.DmyhResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS动脉硬化结果(DmyhResult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:20
 */
@Slf4j
@Service("dmyhResultService")
@RequiredArgsConstructor
public class DmyhResultServiceImpl extends ServiceImpl<DmyhResultMapper, DmyhResult> implements DmyhResultService {

    private final DmyhResultMapper dmyhResultMapper;

    /**
     * 分页查询[KS动脉硬化结果]列表
     *
     * @param page  分页参数
     * @param param DmyhResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DmyhResult> getPage(PageParam<DmyhResult> page, DmyhResult param) {
        return dmyhResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DmyhResult getInfoById(String id) {
        return dmyhResultMapper.getInfoById(id);
    }

}


