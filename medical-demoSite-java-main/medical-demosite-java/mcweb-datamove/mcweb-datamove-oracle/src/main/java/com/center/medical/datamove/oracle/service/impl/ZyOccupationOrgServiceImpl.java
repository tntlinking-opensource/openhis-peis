package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZyOccupationOrgMapper;
import com.center.medical.datamove.oracle.bean.model.ZyOccupationOrg;
import com.center.medical.datamove.oracle.service.ZyOccupationOrgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病体检机构(ZyOccupationOrg)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:31:00
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
    public IPage<ZyOccupationOrg> getPage(PageParam<ZyOccupationOrg> page, ZyOccupationOrg param) {
        return zyOccupationOrgMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    @Override
    public ZyOccupationOrg getInfoById(Object id) {
        return zyOccupationOrgMapper.getInfoById(id);
    }

}


