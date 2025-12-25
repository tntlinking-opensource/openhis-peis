package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsPeispatientfeeitemMapper;
import com.center.medical.datamove.oracle.bean.model.PacsPeispatientfeeitem;
import com.center.medical.datamove.oracle.service.PacsPeispatientfeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsPeispatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:50
 */
@Slf4j
@Service("pacsPeispatientfeeitemService")
@RequiredArgsConstructor
public class PacsPeispatientfeeitemServiceImpl extends ServiceImpl<PacsPeispatientfeeitemMapper, PacsPeispatientfeeitem> implements PacsPeispatientfeeitemService {

    private final PacsPeispatientfeeitemMapper pacsPeispatientfeeitemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsPeispatientfeeitem> getPage(PageParam<PacsPeispatientfeeitem> page, PacsPeispatientfeeitem param) {
        return pacsPeispatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsPeispatientfeeitem getInfoById(String id) {
        return pacsPeispatientfeeitemMapper.getInfoById(id);
    }

}


