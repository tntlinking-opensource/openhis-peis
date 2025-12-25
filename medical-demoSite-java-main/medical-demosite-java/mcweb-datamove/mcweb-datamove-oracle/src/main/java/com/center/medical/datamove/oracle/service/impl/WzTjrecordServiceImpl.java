package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzTjrecordMapper;
import com.center.medical.datamove.oracle.bean.model.WzTjrecord;
import com.center.medical.datamove.oracle.service.WzTjrecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检记录(WzTjrecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:52
 */
@Slf4j
@Service("wzTjrecordService")
@RequiredArgsConstructor
public class WzTjrecordServiceImpl extends ServiceImpl<WzTjrecordMapper, WzTjrecord> implements WzTjrecordService {

    private final WzTjrecordMapper wzTjrecordMapper;

    /**
     * 分页查询[KS问诊——体检记录]列表
     *
     * @param page  分页参数
     * @param param WzTjrecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzTjrecord> getPage(PageParam<WzTjrecord> page, WzTjrecord param) {
        return wzTjrecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzTjrecord getInfoById(String id) {
        return wzTjrecordMapper.getInfoById(id);
    }

}


