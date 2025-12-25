package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOutsideHandMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideHand;
import com.center.medical.datamove.common.service.MdOutsideHandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送手动结果表(MdOutsideHand)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Slf4j
@Service("mdOutsideHandService")
@RequiredArgsConstructor
public class MdOutsideHandServiceImpl extends ServiceImpl<MdOutsideHandMapper, MdOutsideHand> implements MdOutsideHandService {

    private final MdOutsideHandMapper mdOutsideHandMapper;

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideHand查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOutsideHand> getPage(PageParam<MdOutsideHand> page, MdOutsideHand param) {
        return mdOutsideHandMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOutsideHand getInfoById(String id) {
        return mdOutsideHandMapper.getInfoById(id);
    }

}


