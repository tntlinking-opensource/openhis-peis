package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdVisitMainMapper;
import com.center.medical.datamove.common.bean.model.MdVisitMain;
import com.center.medical.datamove.common.service.MdVisitMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(MdVisitMain)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:30
 */
@Slf4j
@Service("mdVisitMainService")
@RequiredArgsConstructor
public class MdVisitMainServiceImpl extends ServiceImpl<MdVisitMainMapper, MdVisitMain> implements MdVisitMainService {

    private final MdVisitMainMapper mdVisitMainMapper;

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param MdVisitMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdVisitMain> getPage(PageParam<MdVisitMain> page, MdVisitMain param) {
        return mdVisitMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdVisitMain getInfoById(String id) {
        return mdVisitMainMapper.getInfoById(id);
    }

    ;

}


