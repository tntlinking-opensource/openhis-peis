package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzTjdwxxMapper;
import com.center.medical.bean.model.WzTjdwxx;
import com.center.medical.service.WzTjdwxxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检单位信息(WzTjdwxx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
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
    public IPage<WzTjdwxx> getList(PageParam<WzTjdwxx> page, WzTjdwxx param) {
        return wzTjdwxxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzTjdwxx getInfoById(String id) {
        return wzTjdwxxMapper.getInfoById(id);
    }

    ;

}

