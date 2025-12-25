package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VCheckLisMainMapper;
import com.center.medical.datamove.oracle.bean.model.VCheckLisMain;
import com.center.medical.datamove.oracle.service.VCheckLisMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (VCheckLisMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:49
 */
@Slf4j
@Service("vCheckLisMainService")
@RequiredArgsConstructor
public class VCheckLisMainServiceImpl extends ServiceImpl<VCheckLisMainMapper, VCheckLisMain> implements VCheckLisMainService {

    private final VCheckLisMainMapper vCheckLisMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckLisMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VCheckLisMain> getPage(PageParam<VCheckLisMain> page, VCheckLisMain param) {
        return vCheckLisMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    @Override
    public VCheckLisMain getInfoById(Object id) {
        return vCheckLisMainMapper.getInfoById(id);
    }

    ;

}


