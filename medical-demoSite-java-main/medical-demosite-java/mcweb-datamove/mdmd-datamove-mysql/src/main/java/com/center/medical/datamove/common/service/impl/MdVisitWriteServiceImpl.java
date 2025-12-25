package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdVisitWriteMapper;
import com.center.medical.datamove.common.bean.model.MdVisitWrite;
import com.center.medical.datamove.common.service.MdVisitWriteService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF回访记录表(MdVisitWrite)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:32
 */
@Slf4j
@Service("mdVisitWriteService")
@RequiredArgsConstructor
public class MdVisitWriteServiceImpl extends ServiceImpl<MdVisitWriteMapper, MdVisitWrite> implements MdVisitWriteService {

    private final MdVisitWriteMapper mdVisitWriteMapper;

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param MdVisitWrite查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdVisitWrite> getPage(PageParam<MdVisitWrite> page, MdVisitWrite param) {
        return mdVisitWriteMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdVisitWrite getInfoById(String id) {
        return mdVisitWriteMapper.getInfoById(id);
    }

}


