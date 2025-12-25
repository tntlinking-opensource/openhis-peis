package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeedepartmentMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeedepartment;
import com.center.medical.datamove.oracle.service.KingdeedepartmentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeedepartment)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:26
 */
@Slf4j
@Service("kingdeedepartmentService")
@RequiredArgsConstructor
public class KingdeedepartmentServiceImpl extends ServiceImpl<KingdeedepartmentMapper, Kingdeedepartment> implements KingdeedepartmentService {

    private final KingdeedepartmentMapper kingdeedepartmentMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeedepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeedepartment> getPage(PageParam<Kingdeedepartment> page, Kingdeedepartment param) {
        return kingdeedepartmentMapper.getPage(page, param);
    }


}


