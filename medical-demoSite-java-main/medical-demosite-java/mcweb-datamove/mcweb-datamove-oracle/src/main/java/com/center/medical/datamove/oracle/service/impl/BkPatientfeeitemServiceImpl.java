package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BkPatientfeeitemMapper;
import com.center.medical.datamove.oracle.bean.model.BkPatientfeeitem;
import com.center.medical.datamove.oracle.service.BkPatientfeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BkPatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:51
 */
@Slf4j
@Service("bkPatientfeeitemService")
@RequiredArgsConstructor
public class BkPatientfeeitemServiceImpl extends ServiceImpl<BkPatientfeeitemMapper, BkPatientfeeitem> implements BkPatientfeeitemService {

    private final BkPatientfeeitemMapper bkPatientfeeitemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BkPatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BkPatientfeeitem> getPage(PageParam<BkPatientfeeitem> page, BkPatientfeeitem param) {
        return bkPatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BkPatientfeeitem getInfoById(String id) {
        return bkPatientfeeitemMapper.getInfoById(id);
    }

}


