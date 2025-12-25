package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ZyOccupationOrgMapper;
import com.center.medical.bean.model.ZyOccupationOrg;
import com.center.medical.service.ZyOccupationOrgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病体检机构(ZyOccupationOrg)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:23
 */
@Slf4j
@Service("zyOccupationOrgService")
@RequiredArgsConstructor
public class ZyOccupationOrgServiceImpl extends ServiceImpl<ZyOccupationOrgMapper, ZyOccupationOrg> implements ZyOccupationOrgService {

    private final ZyOccupationOrgMapper zyOccupationOrgMapper;

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param ZyOccupationOrg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyOccupationOrg> getList(PageParam<ZyOccupationOrg> page, ZyOccupationOrg param) {
        return zyOccupationOrgMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id orgCode主键
     */
    @Override
    public ZyOccupationOrg getInfoById(String id) {
        return zyOccupationOrgMapper.getInfoById(id);
    }

}

