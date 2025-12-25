package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VisitMainMapper;
import com.center.medical.datamove.oracle.bean.model.VisitMain;
import com.center.medical.datamove.oracle.service.VisitMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:53
 */
@Slf4j
@Service("visitMainService")
@RequiredArgsConstructor
public class VisitMainServiceImpl extends ServiceImpl<VisitMainMapper, VisitMain> implements VisitMainService {

    private final VisitMainMapper visitMainMapper;

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VisitMain> getPage(PageParam<VisitMain> page, VisitMain param) {
        return visitMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public VisitMain getInfoById(String id) {
        return visitMainMapper.getInfoById(id);
    }

}


