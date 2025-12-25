package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdLimitOperationMapper;
import com.center.medical.datamove.common.bean.model.MdLimitOperation;
import com.center.medical.datamove.common.service.MdLimitOperationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CW卡额度操作表(MdLimitOperation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
@Slf4j
@Service("mdLimitOperationService")
@RequiredArgsConstructor
public class MdLimitOperationServiceImpl extends ServiceImpl<MdLimitOperationMapper, MdLimitOperation> implements MdLimitOperationService {

    private final MdLimitOperationMapper mdLimitOperationMapper;

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param MdLimitOperation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdLimitOperation> getPage(PageParam<MdLimitOperation> page, MdLimitOperation param) {
        return mdLimitOperationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdLimitOperation getInfoById(String id) {
        return mdLimitOperationMapper.getInfoById(id);
    }

}


