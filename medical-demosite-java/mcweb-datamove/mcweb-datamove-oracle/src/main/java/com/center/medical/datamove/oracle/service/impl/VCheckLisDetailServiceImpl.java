package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VCheckLisDetailMapper;
import com.center.medical.datamove.oracle.bean.model.VCheckLisDetail;
import com.center.medical.datamove.oracle.service.VCheckLisDetailService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (VCheckLisDetail)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:48
 */
@Slf4j
@Service("vCheckLisDetailService")
@RequiredArgsConstructor
public class VCheckLisDetailServiceImpl extends ServiceImpl<VCheckLisDetailMapper, VCheckLisDetail> implements VCheckLisDetailService {

    private final VCheckLisDetailMapper vCheckLisDetailMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckLisDetail查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VCheckLisDetail> getPage(PageParam<VCheckLisDetail> page, VCheckLisDetail param) {
        return vCheckLisDetailMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    @Override
    public VCheckLisDetail getInfoById(Object id) {
        return vCheckLisDetailMapper.getInfoById(id);
    }

    ;

}


