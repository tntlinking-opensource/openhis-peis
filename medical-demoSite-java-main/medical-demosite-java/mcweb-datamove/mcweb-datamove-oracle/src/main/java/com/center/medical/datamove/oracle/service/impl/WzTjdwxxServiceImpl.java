package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzTjdwxxMapper;
import com.center.medical.datamove.oracle.bean.model.WzTjdwxx;
import com.center.medical.datamove.oracle.service.WzTjdwxxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检单位信息(WzTjdwxx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
@Slf4j
@Service("wzTjdwxxService")
@RequiredArgsConstructor
public class WzTjdwxxServiceImpl extends ServiceImpl<WzTjdwxxMapper, WzTjdwxx> implements WzTjdwxxService {

    private final WzTjdwxxMapper wzTjdwxxMapper;

    /**
     * 分页查询[KS问诊——体检单位信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzTjdwxx> getPage(PageParam<WzTjdwxx> page, WzTjdwxx param) {
        return wzTjdwxxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzTjdwxx getInfoById(String id) {
        return wzTjdwxxMapper.getInfoById(id);
    }

}


