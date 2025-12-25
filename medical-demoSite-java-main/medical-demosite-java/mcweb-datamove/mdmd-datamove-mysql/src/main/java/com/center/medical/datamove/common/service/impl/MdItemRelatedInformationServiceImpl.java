package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdItemRelatedInformationMapper;
import com.center.medical.datamove.common.bean.model.MdItemRelatedInformation;
import com.center.medical.datamove.common.service.MdItemRelatedInformationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 第三方接口关联记录(MdItemRelatedInformation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Slf4j
@Service("mdItemRelatedInformationService")
@RequiredArgsConstructor
public class MdItemRelatedInformationServiceImpl extends ServiceImpl<MdItemRelatedInformationMapper, MdItemRelatedInformation> implements MdItemRelatedInformationService {

    private final MdItemRelatedInformationMapper mdItemRelatedInformationMapper;

    /**
     * 分页查询[第三方接口关联记录]列表
     *
     * @param page  分页参数
     * @param param MdItemRelatedInformation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdItemRelatedInformation> getPage(PageParam<MdItemRelatedInformation> page, MdItemRelatedInformation param) {
        return mdItemRelatedInformationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdItemRelatedInformation getInfoById(String id) {
        return mdItemRelatedInformationMapper.getInfoById(id);
    }

}


