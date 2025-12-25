package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdRiskclientconMapper;
import com.center.medical.datamove.common.bean.model.MdRiskclientcon;
import com.center.medical.datamove.common.service.MdRiskclientconService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 高危人员关联表(MdRiskclientcon)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Slf4j
@Service("mdRiskclientconService")
@RequiredArgsConstructor
public class MdRiskclientconServiceImpl extends ServiceImpl<MdRiskclientconMapper, MdRiskclientcon> implements MdRiskclientconService {

    private final MdRiskclientconMapper mdRiskclientconMapper;

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param MdRiskclientcon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdRiskclientcon> getPage(PageParam<MdRiskclientcon> page, MdRiskclientcon param) {
        return mdRiskclientconMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdRiskclientcon getInfoById(String id) {
        return mdRiskclientconMapper.getInfoById(id);
    }

}


