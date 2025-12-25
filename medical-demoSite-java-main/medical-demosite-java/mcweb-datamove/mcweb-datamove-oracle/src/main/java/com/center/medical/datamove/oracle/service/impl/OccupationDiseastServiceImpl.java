package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OccupationDiseastMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDiseast;
import com.center.medical.datamove.oracle.service.OccupationDiseastService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病名称(OccupationDiseast)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:17
 */
@Slf4j
@Service("occupationDiseastService")
@RequiredArgsConstructor
public class OccupationDiseastServiceImpl extends ServiceImpl<OccupationDiseastMapper, OccupationDiseast> implements OccupationDiseastService {

    private final OccupationDiseastMapper occupationDiseastMapper;

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseast查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDiseast> getPage(PageParam<OccupationDiseast> page, OccupationDiseast param) {
        return occupationDiseastMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OccupationDiseast getInfoById(String id) {
        return occupationDiseastMapper.getInfoById(id);
    }

}


