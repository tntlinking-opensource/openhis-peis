package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisOlMapper;
import com.center.medical.datamove.oracle.bean.model.PeisOl;
import com.center.medical.datamove.oracle.service.PeisOlService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者线上信息(PeisOl)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:58
 */
@Slf4j
@Service("peisOlService")
@RequiredArgsConstructor
public class PeisOlServiceImpl extends ServiceImpl<PeisOlMapper, PeisOl> implements PeisOlService {

    private final PeisOlMapper peisOlMapper;

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param PeisOl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisOl> getPage(PageParam<PeisOl> page, PeisOl param) {
        return peisOlMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisOl getInfoById(String id) {
        return peisOlMapper.getInfoById(id);
    }

}


