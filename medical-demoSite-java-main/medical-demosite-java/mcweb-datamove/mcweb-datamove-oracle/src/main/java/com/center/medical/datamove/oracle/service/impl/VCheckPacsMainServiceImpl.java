package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VCheckPacsMainMapper;
import com.center.medical.datamove.oracle.bean.model.VCheckPacsMain;
import com.center.medical.datamove.oracle.service.VCheckPacsMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (VCheckPacsMain)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:50
 */
@Slf4j
@Service("vCheckPacsMainService")
@RequiredArgsConstructor
public class VCheckPacsMainServiceImpl extends ServiceImpl<VCheckPacsMainMapper, VCheckPacsMain> implements VCheckPacsMainService {

    private final VCheckPacsMainMapper vCheckPacsMainMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckPacsMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VCheckPacsMain> getPage(PageParam<VCheckPacsMain> page, VCheckPacsMain param) {
        return vCheckPacsMainMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键checkreqid
     * @return 详情信息
     */
    @Override
    public VCheckPacsMain getInfoById(Object id) {
        return vCheckPacsMainMapper.getInfoById(id);
    }

    ;

}


