package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzTjdwxxMxMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjdwxxMx;
import com.center.medical.datamove.common.service.MdWzTjdwxxMxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——单位明细信息(MdWzTjdwxxMx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
@Slf4j
@Service("mdWzTjdwxxMxService")
@RequiredArgsConstructor
public class MdWzTjdwxxMxServiceImpl extends ServiceImpl<MdWzTjdwxxMxMapper, MdWzTjdwxxMx> implements MdWzTjdwxxMxService {

    private final MdWzTjdwxxMxMapper mdWzTjdwxxMxMapper;

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param MdWzTjdwxxMx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzTjdwxxMx> getPage(PageParam<MdWzTjdwxxMx> page, MdWzTjdwxxMx param) {
        return mdWzTjdwxxMxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzTjdwxxMx getInfoById(String id) {
        return mdWzTjdwxxMxMapper.getInfoById(id);
    }

}


