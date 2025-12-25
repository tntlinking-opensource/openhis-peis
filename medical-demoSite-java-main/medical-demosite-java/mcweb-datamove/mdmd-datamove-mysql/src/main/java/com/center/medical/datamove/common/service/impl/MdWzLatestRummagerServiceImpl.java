package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzLatestRummagerMapper;
import com.center.medical.datamove.common.bean.model.MdWzLatestRummager;
import com.center.medical.datamove.common.service.MdWzLatestRummagerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——最近检查人(MdWzLatestRummager)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Slf4j
@Service("mdWzLatestRummagerService")
@RequiredArgsConstructor
public class MdWzLatestRummagerServiceImpl extends ServiceImpl<MdWzLatestRummagerMapper, MdWzLatestRummager> implements MdWzLatestRummagerService {

    private final MdWzLatestRummagerMapper mdWzLatestRummagerMapper;

    /**
     * 分页查询[KS问诊——最近检查人]列表
     *
     * @param page  分页参数
     * @param param MdWzLatestRummager查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzLatestRummager> getPage(PageParam<MdWzLatestRummager> page, MdWzLatestRummager param) {
        return mdWzLatestRummagerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzLatestRummager getInfoById(String id) {
        return mdWzLatestRummagerMapper.getInfoById(id);
    }

}


