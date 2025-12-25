package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdZyOccupationOrgMapper;
import com.center.medical.datamove.common.bean.model.MdZyOccupationOrg;
import com.center.medical.datamove.common.service.MdZyOccupationOrgService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病体检机构(MdZyOccupationOrg)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
@Slf4j
@Service("mdZyOccupationOrgService")
@RequiredArgsConstructor
public class MdZyOccupationOrgServiceImpl extends ServiceImpl<MdZyOccupationOrgMapper, MdZyOccupationOrg> implements MdZyOccupationOrgService {

    private final MdZyOccupationOrgMapper mdZyOccupationOrgMapper;

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param MdZyOccupationOrg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdZyOccupationOrg> getPage(PageParam<MdZyOccupationOrg> page, MdZyOccupationOrg param) {
        return mdZyOccupationOrgMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    @Override
    public MdZyOccupationOrg getInfoById(String id) {
        return mdZyOccupationOrgMapper.getInfoById(id);
    }

}


