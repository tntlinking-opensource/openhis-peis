package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFailTotalVisitMapper;
import com.center.medical.datamove.common.bean.model.MdFailTotalVisit;
import com.center.medical.datamove.common.service.MdFailTotalVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF迟捡、阳性、不合格样本回访(MdFailTotalVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Slf4j
@Service("mdFailTotalVisitService")
@RequiredArgsConstructor
public class MdFailTotalVisitServiceImpl extends ServiceImpl<MdFailTotalVisitMapper, MdFailTotalVisit> implements MdFailTotalVisitService {

    private final MdFailTotalVisitMapper mdFailTotalVisitMapper;

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param MdFailTotalVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFailTotalVisit> getPage(PageParam<MdFailTotalVisit> page, MdFailTotalVisit param) {
        return mdFailTotalVisitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFailTotalVisit getInfoById(String id) {
        return mdFailTotalVisitMapper.getInfoById(id);
    }

}


