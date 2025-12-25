package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPrictureMapper;
import com.center.medical.datamove.common.bean.model.MdPricture;
import com.center.medical.datamove.common.service.MdPrictureService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS图片存储表(MdPricture)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
@Slf4j
@Service("mdPrictureService")
@RequiredArgsConstructor
public class MdPrictureServiceImpl extends ServiceImpl<MdPrictureMapper, MdPricture> implements MdPrictureService {

    private final MdPrictureMapper mdPrictureMapper;

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param MdPricture查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPricture> getPage(PageParam<MdPricture> page, MdPricture param) {
        return mdPrictureMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPricture getInfoById(String id) {
        return mdPrictureMapper.getInfoById(id);
    }

}


