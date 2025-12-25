package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdLaboratoryResultMapper;
import com.center.medical.datamove.common.bean.model.MdLaboratoryResult;
import com.center.medical.datamove.common.service.MdLaboratoryResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS检验科接收数据(MdLaboratoryResult)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Slf4j
@Service("mdLaboratoryResultService")
@RequiredArgsConstructor
public class MdLaboratoryResultServiceImpl extends ServiceImpl<MdLaboratoryResultMapper, MdLaboratoryResult> implements MdLaboratoryResultService {

    private final MdLaboratoryResultMapper mdLaboratoryResultMapper;

    /**
     * 分页查询[KS检验科接收数据]列表
     *
     * @param page  分页参数
     * @param param MdLaboratoryResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdLaboratoryResult> getPage(PageParam<MdLaboratoryResult> page, MdLaboratoryResult param) {
        return mdLaboratoryResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdLaboratoryResult getInfoById(String id) {
        return mdLaboratoryResultMapper.getInfoById(id);
    }

}


