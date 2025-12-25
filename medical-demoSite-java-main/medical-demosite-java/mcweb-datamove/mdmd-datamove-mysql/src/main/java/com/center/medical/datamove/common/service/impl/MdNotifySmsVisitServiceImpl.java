package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdNotifySmsVisitMapper;
import com.center.medical.datamove.common.bean.model.MdNotifySmsVisit;
import com.center.medical.datamove.common.service.MdNotifySmsVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信回访表(MdNotifySmsVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
@Slf4j
@Service("mdNotifySmsVisitService")
@RequiredArgsConstructor
public class MdNotifySmsVisitServiceImpl extends ServiceImpl<MdNotifySmsVisitMapper, MdNotifySmsVisit> implements MdNotifySmsVisitService {

    private final MdNotifySmsVisitMapper mdNotifySmsVisitMapper;

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param MdNotifySmsVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdNotifySmsVisit> getPage(PageParam<MdNotifySmsVisit> page, MdNotifySmsVisit param) {
        return mdNotifySmsVisitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdNotifySmsVisit getInfoById(String id) {
        return mdNotifySmsVisitMapper.getInfoById(id);
    }

}


