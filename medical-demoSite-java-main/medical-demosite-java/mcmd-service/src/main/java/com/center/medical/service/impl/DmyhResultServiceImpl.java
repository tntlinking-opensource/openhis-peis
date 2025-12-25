package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DmyhResultMapper;
import com.center.medical.bean.model.DmyhResult;
import com.center.medical.service.DmyhResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS动脉硬化结果(DmyhResult)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
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
    public IPage<DmyhResult> getList(PageParam<DmyhResult> page, DmyhResult param) {
        return dmyhResultMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DmyhResult getInfoById(String id) {
        return dmyhResultMapper.getInfoById(id);
    }

    ;

}

