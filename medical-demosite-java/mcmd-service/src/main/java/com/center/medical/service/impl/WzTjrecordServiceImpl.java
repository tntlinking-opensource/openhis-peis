package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzTjrecordMapper;
import com.center.medical.bean.model.WzTjrecord;
import com.center.medical.service.WzTjrecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检记录(WzTjrecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:07
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
    public IPage<WzTjrecord> getList(PageParam<WzTjrecord> page, WzTjrecord param) {
        return wzTjrecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzTjrecord getInfoById(String id) {
        return wzTjrecordMapper.getInfoById(id);
    }

}

