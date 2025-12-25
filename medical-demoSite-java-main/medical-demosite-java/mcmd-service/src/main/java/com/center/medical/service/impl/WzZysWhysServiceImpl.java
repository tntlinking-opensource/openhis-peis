package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzZysWhysMapper;
import com.center.medical.bean.model.WzZysWhys;
import com.center.medical.service.WzZysWhysService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
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
    public IPage<WzZysWhys> getList(PageParam<WzZysWhys> page, WzZysWhys param) {
        return wzZysWhysMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzZysWhys getInfoById(String id) {
        return wzZysWhysMapper.getInfoById(id);
    }

}

