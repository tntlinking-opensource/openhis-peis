package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzZysWhysMapper;
import com.center.medical.datamove.oracle.bean.model.WzZysWhys;
import com.center.medical.datamove.oracle.service.WzZysWhysService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
@Slf4j
@Service("wzZysWhysService")
@RequiredArgsConstructor
public class WzZysWhysServiceImpl extends ServiceImpl<WzZysWhysMapper, WzZysWhys> implements WzZysWhysService {

    private final WzZysWhysMapper wzZysWhysMapper;

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param WzZysWhys查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzZysWhys> getPage(PageParam<WzZysWhys> page, WzZysWhys param) {
        return wzZysWhysMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzZysWhys getInfoById(String id) {
        return wzZysWhysMapper.getInfoById(id);
    }

}


