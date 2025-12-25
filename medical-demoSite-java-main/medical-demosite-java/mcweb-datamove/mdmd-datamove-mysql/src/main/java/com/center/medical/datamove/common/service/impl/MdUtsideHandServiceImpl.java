package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUtsideHandMapper;
import com.center.medical.datamove.common.bean.model.MdUtsideHand;
import com.center.medical.datamove.common.service.MdUtsideHandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送手动结果表(MdUtsideHand)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:28
 */
@Slf4j
@Service("mdUtsideHandService")
@RequiredArgsConstructor
public class MdUtsideHandServiceImpl extends ServiceImpl<MdUtsideHandMapper, MdUtsideHand> implements MdUtsideHandService {

    private final MdUtsideHandMapper mdUtsideHandMapper;

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param MdUtsideHand查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUtsideHand> getPage(PageParam<MdUtsideHand> page, MdUtsideHand param) {
        return mdUtsideHandMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdUtsideHand getInfoById(String id) {
        return mdUtsideHandMapper.getInfoById(id);
    }

    ;

}


