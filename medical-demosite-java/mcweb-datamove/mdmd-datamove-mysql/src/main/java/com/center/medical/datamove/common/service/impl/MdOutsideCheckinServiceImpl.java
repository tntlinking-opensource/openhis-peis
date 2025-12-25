package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOutsideCheckinMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideCheckin;
import com.center.medical.datamove.common.service.MdOutsideCheckinService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送项目图片关联表(MdOutsideCheckin)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Slf4j
@Service("mdOutsideCheckinService")
@RequiredArgsConstructor
public class MdOutsideCheckinServiceImpl extends ServiceImpl<MdOutsideCheckinMapper, MdOutsideCheckin> implements MdOutsideCheckinService {

    private final MdOutsideCheckinMapper mdOutsideCheckinMapper;

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideCheckin查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOutsideCheckin> getPage(PageParam<MdOutsideCheckin> page, MdOutsideCheckin param) {
        return mdOutsideCheckinMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOutsideCheckin getInfoById(String id) {
        return mdOutsideCheckinMapper.getInfoById(id);
    }

}


