package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OccupationDiseastClassMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDiseastClass;
import com.center.medical.datamove.oracle.service.OccupationDiseastClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病种分类(OccupationDiseastClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:18
 */
@Slf4j
@Service("occupationDiseastClassService")
@RequiredArgsConstructor
public class OccupationDiseastClassServiceImpl extends ServiceImpl<OccupationDiseastClassMapper, OccupationDiseastClass> implements OccupationDiseastClassService {

    private final OccupationDiseastClassMapper occupationDiseastClassMapper;

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseastClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDiseastClass> getPage(PageParam<OccupationDiseastClass> page, OccupationDiseastClass param) {
        return occupationDiseastClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OccupationDiseastClass getInfoById(String id) {
        return occupationDiseastClassMapper.getInfoById(id);
    }

}


