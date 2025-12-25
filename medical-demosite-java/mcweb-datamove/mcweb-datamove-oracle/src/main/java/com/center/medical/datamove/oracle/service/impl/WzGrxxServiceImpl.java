package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzGrxxMapper;
import com.center.medical.datamove.oracle.bean.model.WzGrxx;
import com.center.medical.datamove.oracle.service.WzGrxxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——个人基本信息(WzGrxx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:49
 */
@Slf4j
@Service("wzGrxxService")
@RequiredArgsConstructor
public class WzGrxxServiceImpl extends ServiceImpl<WzGrxxMapper, WzGrxx> implements WzGrxxService {

    private final WzGrxxMapper wzGrxxMapper;

    /**
     * 分页查询[KS问诊——个人基本信息]列表
     *
     * @param page  分页参数
     * @param param WzGrxx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzGrxx> getPage(PageParam<WzGrxx> page, WzGrxx param) {
        return wzGrxxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzGrxx getInfoById(String id) {
        return wzGrxxMapper.getInfoById(id);
    }

}


