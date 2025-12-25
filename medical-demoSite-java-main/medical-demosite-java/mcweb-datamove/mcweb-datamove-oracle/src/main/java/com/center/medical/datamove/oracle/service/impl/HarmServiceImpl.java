package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.HarmMapper;
import com.center.medical.datamove.oracle.bean.model.Harm;
import com.center.medical.datamove.oracle.service.HarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC危害因素(Harm)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:11
 */
@Slf4j
@Service("harmService")
@RequiredArgsConstructor
public class HarmServiceImpl extends ServiceImpl<HarmMapper, Harm> implements HarmService {

    private final HarmMapper harmMapper;

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Harm> getPage(PageParam<Harm> page, Harm param) {
        return harmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Harm getInfoById(String id) {
        return harmMapper.getInfoById(id);
    }

}


